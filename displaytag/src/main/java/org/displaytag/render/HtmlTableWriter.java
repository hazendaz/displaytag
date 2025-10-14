/*
 * Copyright (C) 2002-2025 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.render;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.exception.WrappedRuntimeException;
import org.displaytag.model.Column;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.displaytag.pagination.PaginatedList;
import org.displaytag.pagination.SmartListHelper;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.SortOrderEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.tags.CaptionTag;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.Anchor;
import org.displaytag.util.Href;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.ParamEncoder;
import org.displaytag.util.PostHref;
import org.displaytag.util.TagConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A table writer that formats a table in HTML and writes it to a JSP page.
 *
 * @see org.displaytag.render.TableWriterTemplate
 *
 * @since 1.1
 */
public class HtmlTableWriter extends TableWriterAdapter {

    /** Logger. */
    private static Logger logger = LoggerFactory.getLogger(HtmlTableWriter.class);

    /** <code>TableProperties</code>. */
    private final TableProperties properties;

    /**
     * Output destination.
     */
    private final JspWriter out;

    /**
     * The param encoder used to generate unique parameter names. Initialized at the first use of encodeParameter().
     */
    private ParamEncoder paramEncoder;

    /**
     * base href used for links.
     */
    private final Href baseHref;

    /**
     * add export links.
     */
    private final boolean export;

    /** The caption tag. */
    private final CaptionTag captionTag;

    /**
     * The paginated list containing the external pagination and sort parameters The presence of this paginated list is
     * what determines if external pagination and sorting is used or not.
     */
    private final PaginatedList<Row> paginatedList;

    /**
     * Used by various functions when the person wants to do paging.
     */
    private final SmartListHelper listHelper;

    /**
     * page size.
     */
    private final int pagesize;

    /** The attribute map. */
    private final HtmlAttributeMap attributeMap;

    /**
     * Unique table id.
     */
    private final String uid;

    /**
     * This table writer uses a <code>TableTag</code> and a <code>JspWriter</code> to do its work.
     *
     * @param tableProperties
     *            the table properties
     * @param baseHref
     *            the base href
     * @param export
     *            the export
     * @param out
     *            The output destination.
     * @param captionTag
     *            the caption tag
     * @param paginatedList
     *            the paginated list
     * @param listHelper
     *            the list helper
     * @param pagesize
     *            the pagesize
     * @param attributeMap
     *            the attribute map
     * @param uid
     *            the uid
     */
    public HtmlTableWriter(final TableProperties tableProperties, final Href baseHref, final boolean export,
            final JspWriter out, final CaptionTag captionTag, final PaginatedList<Row> paginatedList,
            final SmartListHelper listHelper, final int pagesize, final HtmlAttributeMap attributeMap,
            final String uid) {
        this.properties = tableProperties;
        this.baseHref = baseHref;
        this.export = export;
        this.out = out;
        this.captionTag = captionTag;
        this.paginatedList = paginatedList;
        this.listHelper = listHelper;
        this.pagesize = pagesize;
        this.attributeMap = attributeMap;
        this.uid = uid;
    }

    /**
     * Writes a banner containing search result and paging navigation above an HTML table to a JSP page.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writeTopBanner(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTopBanner(final TableModel model) {
        if (model.getForm() != null) {

            final String js = "<script type=\"text/javascript\">\n" + "function displaytagform(formname, fields){\n"
                    + "    var objfrm = document.forms[formname];\n"
                    + "    for (j=fields.length-1;j>=0;j--){var f= objfrm.elements[fields[j].f];if (f){f.value=fields[j].v};}\n"
                    + "    objfrm.requestSubmit();\n" + "}\n" + "</script>";
            this.writeFormFields(model);
            this.write(js);
        }

        // Put the page stuff there if it needs to be there...
        if (this.properties.getAddPagingBannerTop()) {
            this.writeSearchResultAndNavigation(model);
        }

        // add export links (only if the table is not empty)
        if (this.export && this.properties.getAddExportBannerTop() && !model.getRowListPage().isEmpty()) {
            // generate export link
            this.writeExportLinks(model);
        }
    }

    /**
     * Writes an HTML table's opening tags to a JSP page.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writeTableOpener(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableOpener(final TableModel model) {
        this.write(this.getOpenTag());
    }

    /**
     * Write form fields.
     *
     * @param model
     *            the model
     */
    private void writeFormFields(final TableModel model) {
        final Map<String, String[]> parameters = this.baseHref.getParameterMap();

        final ParamEncoder pe = new ParamEncoder(model.getId());

        this.addIfMissing(parameters, pe.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
        this.addIfMissing(parameters, pe.encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        this.addIfMissing(parameters, pe.encodeParameterName(TableTagParameters.PARAMETER_SORT));

        for (final Entry<String, String[]> entry : parameters.entrySet()) {
            final Object value = entry.getValue();

            if (value != null && value.getClass().isArray()) {
                final Object[] arr = (Object[]) value;
                for (final Object element : arr) {
                    this.writeField(entry.getKey(), element);
                }
            } else {
                this.writeField(entry.getKey(), value);
            }
        }
    }

    /**
     * Write field.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    private void writeField(final String key, final Object value) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("<input type=\"hidden\" name=\"");
        buffer.append(this.esc(key));
        buffer.append("\" value=\"");
        buffer.append(value);
        buffer.append("\"/>");

        this.write(buffer.toString());
    }

    /**
     * Esc.
     *
     * @param value
     *            the value
     *
     * @return the string
     */
    private String esc(final Object value) {
        return Strings.CS.replace(value != null ? value.toString() : StringUtils.EMPTY, "\"", "\\\"");
    }

    /**
     * Adds an element to the given map if empty (use an empty string as value).
     *
     * @param parameters
     *            Map of parameters
     * @param key
     *            param key
     */
    private void addIfMissing(final Map<String, String[]> parameters, final String key) {
        parameters.computeIfAbsent(key, k -> new String[] { StringUtils.EMPTY });
    }

    /**
     * Writes an HTML table's caption to a JSP page.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writeCaption(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeCaption(final TableModel model) {
        this.write(this.captionTag.getOpenTag() + model.getCaption() + this.captionTag.getCloseTag());
    }

    /**
     * Writes an HTML table's footer to a JSP page; HTML requires tfoot to appear before tbody.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writePreBodyFooter(org.displaytag.model.TableModel)
     */
    @Override
    protected void writePreBodyFooter(final TableModel model) {
        this.write(TagConstants.TAG_TFOOTER_OPEN);
        this.write(model.getFooter());
        this.write(TagConstants.TAG_TFOOTER_CLOSE);
    }

    /**
     * Writes the start of an HTML table's body to a JSP page.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writeTableBodyOpener(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableBodyOpener(final TableModel model) {
        this.write(TagConstants.TAG_TBODY_OPEN);

    }

    /**
     * Writes the end of an HTML table's body to a JSP page.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writeTableBodyCloser(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableBodyCloser(final TableModel model) {
        this.write(TagConstants.TAG_TBODY_CLOSE);
    }

    /**
     * Writes the closing structure of an HTML table to a JSP page.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writeTableCloser(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableCloser(final TableModel model) {
        this.write(TagConstants.TAG_OPENCLOSING);
        this.write(TagConstants.TABLE_TAG_NAME);
        this.write(TagConstants.TAG_CLOSE);
    }

    /**
     * Writes a banner containing search result, paging navigation, and export links below an HTML table to a JSP page.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writeBottomBanner(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeBottomBanner(final TableModel model) {
        this.writeNavigationAndExportLinks(model);
    }

    /**
     * Write decorated table finish.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedTableFinish(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedTableFinish(final TableModel model) {
        model.getTableDecorator().finish();
    }

    /**
     * Write decorated row start.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedRowStart(final TableModel model) {
        this.write(model.getTableDecorator().startRow());
    }

    /**
     * Writes an HTML table's row-opening tag to a JSP page.
     *
     * @param row
     *            the row
     *
     * @see org.displaytag.render.TableWriterTemplate#writeRowOpener(org.displaytag.model.Row)
     */
    @Override
    protected void writeRowOpener(final Row row) {
        this.write(row.getOpenTag());
    }

    /**
     * Writes an HTML table's column-opening tag to a JSP page.
     *
     * @param column
     *            the column
     *
     * @throws ObjectLookupException
     *             the object lookup exception
     * @throws DecoratorException
     *             the decorator exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeColumnOpener(org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnOpener(final Column column) throws ObjectLookupException, DecoratorException {
        this.write(column.getOpenTag());
    }

    /**
     * Writes an HTML table's column-closing tag to a JSP page.
     *
     * @param column
     *            the column
     *
     * @see org.displaytag.render.TableWriterTemplate#writeColumnCloser(org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnCloser(final Column column) {
        this.write(column.getCloseTag());
    }

    /**
     * Writes to a JSP page an HTML table row that has no columns.
     *
     * @param rowValue
     *            the row value
     *
     * @see org.displaytag.render.TableWriterTemplate#writeRowWithNoColumns(java.lang.String)
     */
    @Override
    protected void writeRowWithNoColumns(final String rowValue) {
        this.write(TagConstants.TAG_TD_OPEN);
        this.write(rowValue);
        this.write(TagConstants.TAG_TD_CLOSE);
    }

    /**
     * Writes an HTML table's row-closing tag to a JSP page.
     *
     * @param row
     *            the row
     *
     * @see org.displaytag.render.TableWriterTemplate#writeRowCloser(org.displaytag.model.Row)
     */
    @Override
    protected void writeRowCloser(final Row row) {
        this.write(row.getCloseTag());
    }

    /**
     * Write decorated row finish.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowFinish(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedRowFinish(final TableModel model) {
        this.write(model.getTableDecorator().finishRow());
    }

    /**
     * Writes an HTML message to a JSP page explaining that the table model contains no data.
     *
     * @param emptyListMessage
     *            the empty list message
     *
     * @see org.displaytag.render.TableWriterTemplate#writeEmptyListMessage(java.lang.String)
     */
    @Override
    protected void writeEmptyListMessage(final String emptyListMessage) {
        this.write(emptyListMessage);
    }

    /**
     * Writes a HTML table column value to a JSP page.
     *
     * @param value
     *            the value
     * @param column
     *            the column
     *
     * @see org.displaytag.render.TableWriterTemplate#writeColumnValue(java.lang.Object,org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnValue(final Object value, final Column column) {
        this.write(value);
    }

    /**
     * Writes an HTML message to a JSP page explaining that the row contains no data.
     *
     * @param message
     *            the message
     *
     * @see org.displaytag.render.TableWriterTemplate#writeEmptyListRowMessage(java.lang.String)
     */
    @Override
    protected void writeEmptyListRowMessage(final String message) {
        this.write(message);
    }

    /**
     * Writes an HTML table's column header to a JSP page.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writeTableHeader(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableHeader(final TableModel model) {

        if (HtmlTableWriter.logger.isDebugEnabled()) {
            HtmlTableWriter.logger.debug("[{}] getTableHeader called", model.getId());
        }

        // open thead
        this.write(TagConstants.TAG_THEAD_OPEN);

        // open tr
        this.write(TagConstants.TAG_TR_OPEN);

        // no columns?
        if (model.isEmpty()) {
            this.write(TagConstants.TAG_TH_OPEN);
            this.write(TagConstants.TAG_TH_CLOSE);
        }

        // iterator on columns for header
        final Iterator<HeaderCell> iterator = model.getHeaderCellList().iterator();

        while (iterator.hasNext()) {
            // get the header cell
            final HeaderCell headerCell = iterator.next();

            if (headerCell.getSortable()) {
                final String cssSortable = this.properties.getCssSortable();
                headerCell.addHeaderClass(cssSortable);
            }

            // if sorted add styles
            if (headerCell.isAlreadySorted()) {
                // sorted css class
                headerCell.addHeaderClass(this.properties.getCssSorted());

                // sort order css class
                headerCell.addHeaderClass(this.properties.getCssOrder(model.isSortOrderAscending()));
            }

            // append th with html attributes
            this.write(headerCell.getHeaderOpenTag());

            // title
            String header = headerCell.getTitle();

            // column is sortable, create link
            if (headerCell.getSortable()) {
                // creates the link for sorting
                final Anchor anchor = new Anchor(this.getSortingHref(headerCell, model), header);

                // append to buffer
                header = anchor.toString();
            }

            this.write(header);
            this.write(headerCell.getHeaderCloseTag());
        }

        // close tr
        this.write(TagConstants.TAG_TR_CLOSE);

        // close thead
        this.write(TagConstants.TAG_THEAD_CLOSE);

        if (HtmlTableWriter.logger.isDebugEnabled()) {
            HtmlTableWriter.logger.debug("[{}] getTableHeader end", model.getId());
        }
    }

    /**
     * Generates the link to be added to a column header for sorting.
     *
     * @param headerCell
     *            header cell the link should be added to
     * @param model
     *            the model
     *
     * @return Href for sorting
     */
    private Href getSortingHref(final HeaderCell headerCell, final TableModel model) {
        // costruct Href from base href, preserving parameters
        Href href = (Href) this.baseHref.clone();

        if (model.getForm() != null) {
            href = new PostHref(href, model.getForm());
        }

        if (this.paginatedList == null) {
            // add column number as link parameter
            if (!model.isLocalSort() && headerCell.getSortName() != null) {
                href.addParameter(this.encodeParameter(TableTagParameters.PARAMETER_SORT, model),
                        headerCell.getSortName());
                href.addParameter(this.encodeParameter(TableTagParameters.PARAMETER_SORTUSINGNAME, model), "1");
            } else {
                href.addParameter(this.encodeParameter(TableTagParameters.PARAMETER_SORT, model),
                        headerCell.getColumnNumber());
            }

            boolean nowOrderAscending = true;

            if (headerCell.getDefaultSortOrder() != null) {
                final boolean sortAscending = SortOrderEnum.ASCENDING.equals(headerCell.getDefaultSortOrder());
                nowOrderAscending = headerCell.isAlreadySorted() ? !model.isSortOrderAscending() : sortAscending;
            } else {
                nowOrderAscending = (!headerCell.isAlreadySorted() || !model.isSortOrderAscending());
            }

            final int sortOrderParam = nowOrderAscending ? SortOrderEnum.ASCENDING.getCode()
                    : SortOrderEnum.DESCENDING.getCode();
            href.addParameter(this.encodeParameter(TableTagParameters.PARAMETER_ORDER, model), sortOrderParam);

            // If user want to sort the full table I need to reset the page number.
            // or if we aren't sorting locally we need to reset the page as well.
            if (model.isSortFullTable() || !model.isLocalSort()) {
                href.addParameter(this.encodeParameter(TableTagParameters.PARAMETER_PAGE, model), 1);
            }
        } else {
            if (this.properties.getPaginationSkipPageNumberInSort()) {
                href.removeParameter(this.properties.getPaginationPageNumberParam());
            }

            String sortProperty = headerCell.getSortProperty();
            if (sortProperty == null) {
                sortProperty = headerCell.getBeanPropertyName();
            }

            href.addParameter(this.properties.getPaginationSortParam(), sortProperty);
            String dirParam;
            if (headerCell.isAlreadySorted()) {
                dirParam = model.isSortOrderAscending() ? this.properties.getPaginationDescValue()
                        : this.properties.getPaginationAscValue();
            } else {
                dirParam = this.properties.getPaginationAscValue();
            }
            href.addParameter(this.properties.getPaginationSortDirectionParam(), dirParam);
            if (this.paginatedList.getSearchId() != null) {
                href.addParameter(this.properties.getPaginationSearchIdParam(), this.paginatedList.getSearchId());
            }
        }

        return href;
    }

    /**
     * encode a parameter name to be unique in the page using ParamEncoder.
     *
     * @param parameterName
     *            parameter name to encode
     * @param model
     *            the model
     *
     * @return String encoded parameter name
     */
    private String encodeParameter(final String parameterName, final TableModel model) {
        // paramEncoder has been already instantiated?
        if (this.paramEncoder == null) {
            // use the id attribute to get the unique identifier
            this.paramEncoder = new ParamEncoder(model.getId());
        }

        return this.paramEncoder.encodeParameterName(parameterName);
    }

    /**
     * Generates table footer with links for export commands.
     *
     * @param model
     *            the model
     */
    protected void writeNavigationAndExportLinks(final TableModel model) {
        // Put the page stuff there if it needs to be there...
        if (this.properties.getAddPagingBannerBottom()) {
            this.writeSearchResultAndNavigation(model);
        }

        // add export links (only if the table is not empty)
        if (this.export && this.properties.getAddExportBannerBottom() && !model.getRowListPage().isEmpty()) {
            this.writeExportLinks(model);
        }
    }

    /**
     * generates the search result and navigation bar.
     *
     * @param model
     *            the model
     */
    protected void writeSearchResultAndNavigation(final TableModel model) {
        if (this.paginatedList == null && this.pagesize != 0 && this.listHelper != null || this.paginatedList != null) {
            // create a new href
            Href navigationHref = (Href) this.baseHref.clone();

            if (model.getForm() != null) {
                navigationHref = new PostHref(navigationHref, model.getForm());
            }

            this.write(this.listHelper.getSearchResultsSummary());

            String pageParameter;
            if (this.paginatedList == null) {
                pageParameter = this.encodeParameter(TableTagParameters.PARAMETER_PAGE, model);
            } else {
                pageParameter = this.properties.getPaginationPageNumberParam();
                if (this.paginatedList.getSearchId() != null && !navigationHref.getParameterMap()
                        .containsKey(this.properties.getPaginationSearchIdParam())) {
                    navigationHref.addParameter(this.properties.getPaginationSearchIdParam(),
                            this.paginatedList.getSearchId());
                }
            }
            this.write(this.listHelper.getPageNavigationBar(navigationHref, pageParameter));
        }
    }

    /**
     * Writes the formatted export links section.
     *
     * @param model
     *            the model
     */
    private void writeExportLinks(final TableModel model) {
        // Figure out what formats they want to export, make up a little string
        final Href exportHref = (Href) this.baseHref.clone();

        final StringBuilder buffer = new StringBuilder(200);
        final Iterator<MediaTypeEnum> iterator = MediaTypeEnum.iterator();

        while (iterator.hasNext()) {
            final MediaTypeEnum currentExportType = iterator.next();

            if (this.properties.getAddExport(currentExportType)) {

                if (buffer.length() > 0) {
                    buffer.append(this.properties.getExportBannerSeparator());
                }

                exportHref.addParameter(this.encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE, model),
                        currentExportType.getCode());

                // export marker
                exportHref.addParameter(TableTagParameters.PARAMETER_EXPORTING, "1");

                final String exportBannerItem = Objects.toString(this.properties.getExportBannerItem(),
                        "<a href=\"{0}\">{1}</a>");

                buffer.append(MessageFormat.format(exportBannerItem, exportHref,
                        this.properties.getExportLabel(currentExportType)));
            }
        }

        final Object[] exportOptions = { buffer.toString() };
        this.write(new MessageFormat(this.properties.getExportBanner(), this.properties.getLocale())
                .format(exportOptions));
    }

    /**
     * create the open tag containing all the attributes.
     *
     * @return open tag string: <code>%lt;table attribute="value" ... &gt;</code>
     */
    public String getOpenTag() {

        if (this.uid != null && this.attributeMap.get(TagConstants.ATTRIBUTE_ID) == null) {
            // we need to clone the attribute map in order to "fix" the html id when using only the "uid" attribute
            final Map<String, String> localAttributeMap = (Map<String, String>) this.attributeMap.clone();
            localAttributeMap.put(TagConstants.ATTRIBUTE_ID, this.uid);

            final StringBuilder buffer = new StringBuilder();
            buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TABLE_TAG_NAME);
            buffer.append(localAttributeMap);
            buffer.append(TagConstants.TAG_CLOSE);

            return buffer.toString();

        }

        // fast, no clone
        final StringBuilder buffer = new StringBuilder();

        buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TABLE_TAG_NAME);
        buffer.append(this.attributeMap);
        buffer.append(TagConstants.TAG_CLOSE);

        return buffer.toString();
    }

    /**
     * Utility method.
     *
     * @param string
     *            String
     */
    public void write(final String string) {
        if (string != null) {
            try {
                this.out.write(string);
            } catch (final IOException e) {
                throw new WrappedRuntimeException(this.getClass(), e);
            }
        }

    }

    /**
     * Utility method.
     *
     * @param string
     *            String
     */
    public void write(final Object string) {
        if (string != null) {
            try {
                this.out.write(string.toString());
            } catch (final IOException e) {
                throw new WrappedRuntimeException(this.getClass(), e);
            }
        }

    }

}
