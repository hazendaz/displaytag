/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.tags;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.displaytag.Messages;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.ExportException;
import org.displaytag.exception.FactoryInstantiationException;
import org.displaytag.exception.InvalidTagAttributeValueException;
import org.displaytag.exception.WrappedRuntimeException;
import org.displaytag.export.BinaryExportView;
import org.displaytag.export.ExportView;
import org.displaytag.export.ExportViewFactory;
import org.displaytag.export.TextExportView;
import org.displaytag.model.Cell;
import org.displaytag.model.Column;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.displaytag.pagination.PaginatedList;
import org.displaytag.pagination.PaginatedListSmartListHelper;
import org.displaytag.pagination.SmartListHelper;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.SortOrderEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.render.HtmlTableWriter;
import org.displaytag.render.TableTotaler;
import org.displaytag.util.CollectionUtil;
import org.displaytag.util.Href;
import org.displaytag.util.ParamEncoder;
import org.displaytag.util.RequestHelper;
import org.displaytag.util.RequestHelperFactory;
import org.displaytag.util.TagConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This tag takes a list of objects and creates a table to display those objects. With the help of column tags, you
 * simply provide the name of properties (get Methods) that are called against the objects in your list that gets
 * displayed. This tag works very much like the struts iterator tag, most of the attributes have the same name and
 * functionality as the struts tag.
 * @author mraible
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TableTag extends HtmlTableTag
{

    /**
     * name of the attribute added to page scope when exporting, containing an MediaTypeEnum this can be used in column
     * content to detect the output type and to return different data when exporting.
     */
    public static final String PAGE_ATTRIBUTE_MEDIA = "mediaType"; //$NON-NLS-1$

    /**
     * If this variable is found in the request, assume the export filter is enabled.
     */
    public static final String FILTER_CONTENT_OVERRIDE_BODY = //
    "org.displaytag.filter.ResponseOverrideFilter.CONTENT_OVERRIDE_BODY"; //$NON-NLS-1$

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(TableTag.class);

    /**
     * RequestHelperFactory instance used for link generation.
     */
    private static RequestHelperFactory rhf;

    /**
     * Object (collection, list) on which the table is based. This is not set directly using a tag attribute and can be
     * cleaned.
     */
    protected transient Object list;

    // -- start tag attributes --

    /**
     * Object (collection, list) on which the table is based. Set directly using the "list" attribute or evaluated from
     * expression.
     */
    protected transient Object listAttribute;

    /**
     * actual row number, updated during iteration.
     */
    private int rowNumber = 1;

    /**
     * name of the object to use for iteration. Can contain expressions.
     */
    private String name;

    /**
     * length of list to display.
     */
    private int length;

    /**
     * table decorator class name.
     */
    private String decoratorName;

    /**
     * page size.
     */
    private int pagesize;

    /**
     * list contains only viewable data.
     */
    private boolean partialList;

    /**
     * add export links.
     */
    private boolean export;

    /**
     * list offset.
     */
    private int offset;

    /** Integer containing total size of the data displaytag is paginating. */
    private transient Object size;

    /** Name of the Integer in some scope containing the size of the data displaytag is paginating. */
    private String sizeObjectName;

    /** sort the full list?. */
    private Boolean sortFullTable;

    /** are we doing any local sorting? (defaults to True). */
    private boolean localSort = true;

    /**
     * Request uri.
     */
    private String requestUri;

    /**
     * Prepend application context to generated links.
     */
    private boolean dontAppendContext;

    /**
     * the index of the column sorted by default.
     */
    private int defaultSortedColumn = -1;

    /**
     * the sorting order for the sorted column.
     */
    private SortOrderEnum defaultSortOrder;

    /**
     * Name of parameter which should not be forwarded during sorting or pagination.
     */
    private String excludedParams;

    /**
     * Unique table id.
     */
    private String uid;

    /**
     * The variable name to store totals in.
     */
    private String varTotals;

    /**
     * Preserve the current page and sort.
     */
    private boolean keepStatus;

    /**
     * Clear the current page and sort status.
     */
    private boolean clearStatus;

    /**
     * Use form post in paging/sorting links (javascript required).
     */
    private String form;

    // -- end tag attributes --

    /**
     * table model - initialized in doStartTag().
     */
    private transient TableModel tableModel;

    /**
     * current row.
     */
    private transient Row currentRow;

    /**
     * next row.
     */

    /**
     * Used by various functions when the person wants to do paging - cleaned in doEndTag().
     */
    private transient SmartListHelper listHelper;

    /**
     * base href used for links - set in initParameters().
     */
    private Href baseHref;

    /**
     * table properties - set in doStartTag().
     */
    private transient TableProperties properties;

    /**
     * page number - set in initParameters().
     */
    private int pageNumber = 1;

    /**
     * Iterator on collection.
     */
    private transient Iterator< ? > tableIterator;

    /**
     * export type - set in initParameters().
     */
    private transient MediaTypeEnum currentMediaType;

    /** daAfterBody() has been executed at least once?. */
    private boolean doAfterBodyExecuted;

    /**
     * The param encoder used to generate unique parameter names. Initialized at the first use of encodeParameter().
     */
    private ParamEncoder paramEncoder;

    /**
     * Static footer added using the footer tag.
     */
    private String footer;

    /**
     * Is this the last iteration we will be performing? We only output the footer on the last iteration.
     */
    private boolean lastIteration;

    /**
     * Static caption added using the footer tag.
     */
    private String caption;

    /**
     * Child caption tag.
     */
    private CaptionTag captionTag;

    /**
     * Included row range. If no rows can be skipped the range is from 0 to Integer.MAX_VALUE.
     */
    private Range<Integer> filteredRows;

    /**
     * The paginated list containing the external pagination and sort parameters The presence of this paginated list is
     * what determines if external pagination and sorting is used or not.
     */
    private transient PaginatedList paginatedList;

    /**
     * The classname of the totaler.
     */
    private String totalerName;

    /**
     * Is this the last iteration?.
     *
     * @return boolean <code>true</code> if this is the last iteration
     */
    public boolean isLastIteration()
    {
        return this.lastIteration;
    }

    /**
     * Sets the list of parameter which should not be forwarded during sorting or pagination.
     * @param value whitespace separated list of parameters which should not be included (* matches all parameters)
     */
    public void setExcludedParams(String value)
    {
        this.excludedParams = value;
    }

    /**
     * Sets the content of the footer. Called by a nested footer tag.
     * @param string footer content
     */
    public void setFooter(String string)
    {
        this.footer = string;
        this.tableModel.setFooter(this.footer);
    }

    /**
     * Sets the content of the caption. Called by a nested caption tag.
     * @param string caption content
     */
    public void setCaption(String string)
    {
        this.caption = string;
        this.tableModel.setCaption(this.caption);
    }

    /**
     * Set the child caption tag.
     * @param captionTag Child caption tag
     */
    public void setCaptionTag(CaptionTag captionTag)
    {
        this.captionTag = captionTag;
    }

    /**
     * Obtain the child caption tag.
     * @return The child caption tag
     */
    public CaptionTag getCaptionTag()
    {
        return this.captionTag;
    }

    /**
     * Is the current row empty?.
     *
     * @return true if the current row is empty
     */
    public boolean isEmpty()
    {
        return this.currentRow == null;
    }

    /**
     * Preserve the current page and sort across session?.
     *
     * @param keepStatus <code>true</code> to preserve paging and sorting
     */
    public void setKeepStatus(boolean keepStatus)
    {
        this.keepStatus = keepStatus;
    }

    /**
     * Setter for <code>clearStatus</code>.
     * @param clearStatus The clearStatus to set.
     */
    public void setClearStatus(boolean clearStatus)
    {
        this.clearStatus = clearStatus;
    }

    /**
     * Setter for <code>form</code>.
     * @param form The form to set.
     */
    public void setForm(String form)
    {
        this.form = form;
    }

    /**
     * set the Integer containing the total size of the data displaytag is paginating.
     *
     * @param size Integer containing the total size of the data
     */
    public void setSize(Object size)
    {
        if (size instanceof String)
        {
            this.sizeObjectName = (String) size;
        }
        else
        {
            this.size = size;
        }
    }

    /**
     * set the name of the Integer in some scope containing the total size of the data to be paginated.
     *
     * @param sizeObjectName name of the Integer containing the total size of the data to be paginated
     */
    public void setSizeObjectName(String sizeObjectName)
    {
        this.sizeObjectName = sizeObjectName;
    }

    /**
     * setter for the "sort" attribute.
     * @param value "page" (sort a single page), "list" (sort the full list), "external" (list already sorted)
     * @throws InvalidTagAttributeValueException if value is not "page", "list" or "external"
     */
    public void setSort(String value) throws InvalidTagAttributeValueException
    {
        if (TableTagParameters.SORT_AMOUNT_PAGE.equals(value))
        {
            this.sortFullTable = Boolean.FALSE;
        }
        else if (TableTagParameters.SORT_AMOUNT_LIST.equals(value))
        {
            this.sortFullTable = Boolean.TRUE;
        }
        else if (TableTagParameters.SORT_AMOUNT_EXTERNAL.equals(value))
        {
            this.localSort = false;
        }
        else
        {
            throw new InvalidTagAttributeValueException(getClass(), "sort", value); //$NON-NLS-1$
        }
    }

    /**
     * setter for the "requestURI" attribute. Context path is automatically added to path starting with "/".
     * @param value base URI for creating links
     */
    public void setRequestURI(String value)
    {
        this.requestUri = value;
    }

    /**
     * Setter for the "requestURIcontext" attribute.
     * @param value base URI for creating links
     */
    public void setRequestURIcontext(boolean value)
    {
        this.dontAppendContext = !value;
    }

    /**
     * Used to directly set a list (or any object you can iterate on).
     * @param value Object
     * @deprecated use setItems()
     */
    @Deprecated
    public void setList(Object value)
    {
        this.listAttribute = value;
    }

    /**
     * Sets the name of the object to use for iteration.
     * @param value name of the object to use for iteration (can contain expression). It also supports direct setting of
     * a list, for jsp 2.0 containers where users can set up a data source here using EL expressions.
     * @deprecated please use setItems()
     */
    @Deprecated
    public void setName(Object value)
    {
        if (value instanceof String)
        {
            // ok, assuming this is the name of the object
            this.name = (String) value;
        }
        else
        {
            // is this the list?
            this.list = value;
        }
    }

    /**
     * Sets the name of the object to use for iteration.
     * @param value the object to use for iteration (can contain expression).
     */
    public void setItems(Object value)
    {
        this.list = value;
    }

    /**
     * Sets the name of the object to use for iteration. This setter is needed for jsp 1.1 container which doesn't
     * support the String - Object conversion. The bean info class will swith to this setter.
     * @param value name of the object
     */
    public void setNameString(String value)
    {
        this.name = value;
    }

    /**
     * sets the sorting order for the sorted column.
     * @param value "ascending" or "descending"
     * @throws InvalidTagAttributeValueException if value is not one of "ascending" or "descending"
     */
    public void setDefaultorder(String value) throws InvalidTagAttributeValueException
    {
        this.defaultSortOrder = SortOrderEnum.fromName(value);
        if (this.defaultSortOrder == null)
        {
            throw new InvalidTagAttributeValueException(getClass(), "defaultorder", value); //$NON-NLS-1$
        }
    }

    /**
     * Setter for the decorator class name.
     * @param decorator fully qualified name of the table decorator to use
     */
    public void setDecorator(String decorator)
    {
        this.decoratorName = decorator;
    }

    /**
     * Is export enabled?.
     *
     * @param value <code>true</code> if export should be enabled
     */
    public void setExport(boolean value)
    {
        this.export = value;
    }

    /**
     * The variable name in which the totals map is stored.
     * @param varTotalsName the value
     */
    public void setVarTotals(String varTotalsName)
    {
        this.varTotals = varTotalsName;
    }

    /**
     * Get the name that the totals should be stored under.
     * @return the var name in pageContext
     */
    public String getVarTotals()
    {
        return this.varTotals;
    }

    /**
     * sets the number of items to be displayed in the page.
     * @param value number of items to display in a page
     */
    public void setLength(int value)
    {
        this.length = value;
    }

    /**
     * sets the index of the default sorted column.
     * @param value index of the column to sort
     */
    public void setDefaultsort(int value)
    {
        // subtract one (internal index is 0 based)
        this.defaultSortedColumn = value - 1;
    }

    /**
     * sets the number of items that should be displayed for a single page.
     * @param value number of items that should be displayed for a single page
     */
    public void setPagesize(int value)
    {
        this.pagesize = value;
    }

    /**
     * tells display tag that the values contained in the list are the viewable data only, there may be more results not
     * given to displaytag.
     *
     * @param partialList boolean value telling us there may be more data not given to displaytag
     */
    public void setPartialList(boolean partialList)
    {
        this.partialList = partialList;
    }

    /**
     * Setter for the list offset attribute.
     * @param value String
     */
    public void setOffset(int value)
    {
        if (value < 1)
        {
            // negative values has no meaning, simply treat them as 0
            this.offset = 0;
        }
        else
        {
            this.offset = value - 1;
        }
    }

    /**
     * Sets the unique id used to identify for this table.
     * @param value String
     */
    public void setUid(String value)
    {
        this.uid = value;
    }

    /**
     * Returns the unique id used to identify for this table.
     * @return id for this table
     */
    public String getUid()
    {
        return this.uid;
    }

    /**
     * Returns the properties.
     * @return TableProperties
     */
    public TableProperties getProperties()
    {
        return this.properties;
    }

    /**
     * Returns the base href with parameters. This is the instance used for links, need to be cloned before being
     * modified.
     * @return base Href with parameters
     */
    protected Href getBaseHref()
    {
        return this.baseHref;
    }

    /**
     * Called by interior column tags to help this tag figure out how it is supposed to display the information in the
     * List it is supposed to display.
     * @param column an internal tag describing a column in this tableview
     */
    public void addColumn(HeaderCell column)
    {
        if (log.isDebugEnabled())
        {
            log.debug("[{}] addColumn {}", getUid(), column);
        }

        if ((this.paginatedList != null) && (column.getSortable()))
        {
            String sortCriterion = this.paginatedList.getSortCriterion();

            String sortProperty = column.getSortProperty();
            if (sortProperty == null)
            {
                sortProperty = column.getBeanPropertyName();
            }

            if ((sortCriterion != null) && sortCriterion.equals(sortProperty))
            {
                this.tableModel.setSortedColumnNumber(this.tableModel.getNumberOfColumns());
                column.setAlreadySorted();
            }
        }

        this.tableModel.addColumnHeader(column);
    }

    /**
     * Adds a cell to the current row. This method is usually called by a contained ColumnTag
     * @param cell Cell to add to the current row
     */
    public void addCell(Cell cell)
    {
        // check if null: could be null if list is empty, we don't need to fill rows
        if (this.currentRow != null)
        {
            int columnNumber = this.currentRow.getCellList().size();
            this.currentRow.addCell(cell);

            // just be sure that the number of columns has not been altered by conditionally including column tags in
            // different rows. This is not supported, but better avoid IndexOutOfBounds...
            if (columnNumber < this.tableModel.getHeaderCellList().size())
            {
                HeaderCell header = this.tableModel.getHeaderCellList().get(columnNumber);
                header.addCell(new Column(header, cell, this.currentRow));
            }
        }
    }

    /**
     * Is this the first iteration?.
     *
     * @return boolean <code>true</code> if this is the first iteration
     */
    public boolean isFirstIteration()
    {
        if (log.isDebugEnabled())
        {
            log.debug("["
                + getUid()
                + "] first iteration="
                + (this.rowNumber == 1)
                + " (row number="
                + this.rowNumber
                + ")");
        }
        // in first iteration this.rowNumber is 1
        // (this.rowNumber is incremented in doAfterBody)
        return this.rowNumber == 1;
    }

    /**
     * When the tag starts, we just initialize some of our variables, and do a little bit of error checking to make sure
     * that the user is not trying to give us parameters that we don't expect.
     * @return int
     * @throws JspException generic exception
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException
    {

        // needed before column processing, elsewhere registered views will not be added
        ExportViewFactory.getInstance();

        if (log.isDebugEnabled())
        {
            log.debug("[{}] doStartTag called", getUid());
        }

        this.properties = TableProperties.getInstance(this.pageContext);
        this.tableModel = new TableModel(this.properties, this.pageContext.getResponse().getCharacterEncoding(), this.pageContext);

        // copying id to the table model for logging
        this.tableModel.setId(getUid());
        this.tableModel.setForm(this.form);

        initParameters();

        this.tableModel.setMedia(this.currentMediaType);

        Object previousMediaType = this.pageContext.getAttribute(PAGE_ATTRIBUTE_MEDIA);
        // set the PAGE_ATTRIBUTE_MEDIA attribute in the page scope
        if (previousMediaType == null || MediaTypeEnum.HTML.equals(previousMediaType))
        {
            if (log.isDebugEnabled())
            {
                log.debug("[{}] setting media [{}] in this.pageContext", getUid(), this.currentMediaType);
            }
            this.pageContext.setAttribute(PAGE_ATTRIBUTE_MEDIA, this.currentMediaType);
        }

        doIteration();

        // always return EVAL_BODY_TAG to get column headers also if the table is empty
        // using int to avoid deprecation error in compilation using j2ee 1.3
        return 2;
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTag#doAfterBody()
     */
    @Override
    public int doAfterBody()
    {
        // doAfterBody() has been called, body is not empty
        this.doAfterBodyExecuted = true;

        if (log.isDebugEnabled())
        {
            log.debug("[{}] doAfterBody called - iterating on row {}", getUid(), this.rowNumber);
        }

        // increment this.rowNumber
        this.rowNumber++;

        // Call doIteration() to do the common work
        return doIteration();
    }

    /**
     * Utility method that is used by both doStartTag() and doAfterBody() to perform an iteration.
     * @return <code>int</code> either EVAL_BODY_TAG or SKIP_BODY depending on whether another iteration is desired.
     */
    protected int doIteration()
    {

        if (log.isDebugEnabled())
        {
            log.debug("[{}] doIteration called", getUid());
        }

        // Row already filled?
        if (this.currentRow != null)
        {
            // if yes add to table model and remove
            this.tableModel.addRow(this.currentRow);
            this.currentRow = null;
        }

        if (this.tableIterator.hasNext())
        {

            Object iteratedObject = this.tableIterator.next();
            if (getUid() != null)
            {
                if ((iteratedObject != null))
                {
                    // set object into this.pageContext
                    if (log.isDebugEnabled())
                    {
                        log.debug("[{}] setting attribute \"{}\" in pageContext", getUid(), getUid());
                    }
                    this.pageContext.setAttribute(getUid(), iteratedObject);

                }
                else
                {
                    // if row is null remove previous object
                    this.pageContext.removeAttribute(getUid());
                }
                // set the current row number into this.pageContext
                this.pageContext.setAttribute(getUid() + TableTagExtraInfo.ROWNUM_SUFFIX, Integer.valueOf(this.rowNumber));
            }

            // Row object for Cell values
            this.currentRow = new Row(iteratedObject, this.rowNumber);

            this.lastIteration = !this.tableIterator.hasNext();

            // new iteration
            // using int to avoid deprecation error in compilation using j2ee 1.3
            return 2;
        }
        this.lastIteration = true;

        if (log.isDebugEnabled())
        {
            log.debug("[{}] doIteration() - iterator ended after {} rows", getUid(), (this.rowNumber - 1));
        }

        // end iteration
        return SKIP_BODY;
    }

    /**
     * Get the given parameter from the request or, if not avaible, look for into into the session if keepstatus is set.
     * Also takes care of storing an existing paramter into session.
     * @param request servlet request
     * @param requestHelper request helper instance
     * @param parameter parameter, will be encoded
     * @return value value taken from a request parameter or from a session attribute
     */
    private Integer getFromRequestOrSession(HttpServletRequest request, RequestHelper requestHelper, String parameter)
    {
        String encodedParam = encodeParameter(parameter);
        Integer result = requestHelper.getIntParameter(encodedParam);

        if (this.keepStatus)
        {
            if (result == null)
            {
                // get from session
                HttpSession session = request.getSession(false);
                if (session != null)
                {
                    if (this.clearStatus)
                    {
                        session.removeAttribute(encodedParam);
                    }
                    else
                    {
                        result = (Integer) session.getAttribute(encodedParam);
                    }
                }
            }
            else
            {
                // set into session
                request.getSession(true).setAttribute(encodedParam, result);
            }
        }
        return result;
    }

    /**
     * Reads parameters from the request and initialize all the needed table model attributes.
     *
     * @throws JspTagException the jsp tag exception
     */
    private void initParameters() throws JspTagException
    {

        if (rhf == null)
        {
            // first time initialization
            rhf = this.properties.getRequestHelperFactoryInstance();
        }

        String fullName = getFullObjectName();

        // only evaluate if needed, else use list attribute
        if (fullName != null)
        {
            this.list = evaluateExpression(fullName);
        }
        else if (this.list == null)
        {
            // needed to allow removing the collection of objects if not set directly
            this.list = this.listAttribute;
        }

        if (this.list instanceof PaginatedList)
        {
            this.paginatedList = (PaginatedList) this.list;
            this.list = this.paginatedList.getList();
        }

        // set the table model to perform in memory local sorting
        this.tableModel.setLocalSort(this.localSort && (this.paginatedList == null));

        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        RequestHelper requestHelper = rhf.getRequestHelperInstance(this.pageContext);

        initHref(requestHelper);

        Integer pageNumberParameter = getFromRequestOrSession(request, requestHelper, TableTagParameters.PARAMETER_PAGE);
        this.pageNumber = (pageNumberParameter == null) ? 1 : pageNumberParameter.intValue();

        int sortColumn = -1;
        if (!this.tableModel.isLocalSort())
        {
            // our sort column parameter may be a string, check that first
            String sortColumnName = requestHelper.getParameter(encodeParameter(TableTagParameters.PARAMETER_SORT));

            // if usename is not null, sortColumnName is the name, if not is the column index
            String usename = requestHelper.getParameter(encodeParameter(TableTagParameters.PARAMETER_SORTUSINGNAME));

            if (sortColumnName == null)
            {
                this.tableModel.setSortedColumnNumber(this.defaultSortedColumn);
            }
            else
            {
                if (usename != null)
                {

                    this.tableModel.setSortedColumnName(sortColumnName); // its a string, set as string
                }
                else if (NumberUtils.isCreatable(sortColumnName))
                {
                    sortColumn = Integer.parseInt(sortColumnName);
                    this.tableModel.setSortedColumnNumber(sortColumn); // its an int set as normal
                }
            }
        }
        else if (this.paginatedList == null)
        {
            Integer sortColumnParameter = getFromRequestOrSession(
                request,
                requestHelper,
                TableTagParameters.PARAMETER_SORT);
            sortColumn = (sortColumnParameter == null) ? this.defaultSortedColumn : sortColumnParameter.intValue();
            this.tableModel.setSortedColumnNumber(sortColumn);
        }
        else
        {
            sortColumn = this.defaultSortedColumn;
        }

        // default value
        boolean finalSortFull = this.properties.getSortFullList();

        // user value for this single table
        if (this.sortFullTable != null)
        {
            finalSortFull = this.sortFullTable.booleanValue();
        }

        // if a partial list is used and sort="list" is specified, assume the partial list is already sorted
        if (!this.partialList || !finalSortFull)
        {
            this.tableModel.setSortFullTable(finalSortFull);
        }

        if (this.paginatedList == null)
        {
            SortOrderEnum paramOrder = SortOrderEnum.fromCode(getFromRequestOrSession(
                request,
                requestHelper,
                TableTagParameters.PARAMETER_ORDER));

            // if no order parameter is set use default
            if (paramOrder == null)
            {
                paramOrder = this.defaultSortOrder;
            }

            boolean order = SortOrderEnum.DESCENDING != paramOrder;
            this.tableModel.setSortOrderAscending(order);
        }
        else
        {
            SortOrderEnum direction = this.paginatedList.getSortDirection();
            this.tableModel.setSortOrderAscending(direction == SortOrderEnum.ASCENDING);
        }

        Integer exportTypeParameter = requestHelper
            .getIntParameter(encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE));

        this.currentMediaType = ObjectUtils.defaultIfNull(
            MediaTypeEnum.fromCode(exportTypeParameter),
            MediaTypeEnum.HTML);

        // if we are doing partialLists then ensure we have our size object
        if (this.partialList)
        {
            if ((this.sizeObjectName == null) && (this.size == null))
            {
                // ?
            }
            if (this.sizeObjectName != null)
            {
                // retrieve the object from scope
                this.size = evaluateExpression(this.sizeObjectName);
            }
            if (this.size == null)
            {
                throw new JspTagException(Messages.getString("MissingAttributeException.msg", new Object[]{"size"}));
            }
            else if (!(this.size instanceof Integer))
            {
                throw new JspTagException(Messages.getString(
                    "InvalidTypeException.msg",
                    new Object[]{"size", "Integer"}));
            }
        }
        this.tableIterator = IteratorUtils.getIterator(this.list);

        // do we really need to skip any row?
        boolean wishOptimizedIteration = ((this.pagesize > 0 // we are paging
            || this.offset > 0 // or we are skipping some records using offset
        || this.length > 0 // or we are limiting the records using length
        ) && !this.partialList); // only optimize if we have the full list

        // can we actually skip any row?
        if (wishOptimizedIteration && (this.list instanceof Collection) // we need to know the size
            && ((sortColumn == -1 // and we are not sorting
            || !finalSortFull // or we are sorting with the "page" behaviour
            ) && (this.currentMediaType == MediaTypeEnum.HTML // and we are not exporting
            || !this.properties.getExportFullList()) // or we are exporting a single page
            ))
        {
            int start = 0;
            int end = 0;
            if (this.offset > 0)
            {
                start = this.offset;
            }
            if (this.length > 0)
            {
                end = start + this.length;
            }

            if (this.pagesize > 0)
            {
                int fullSize = ((Collection< ? >) this.list).size();
                start = (this.pageNumber - 1) * this.pagesize;

                // invalid page requested, go back to last page
                if (start >= fullSize)
                {

                    int div = fullSize / this.pagesize;
                    start = ((fullSize % this.pagesize == 0) ? div - 1 : div) * this.pagesize;
                }
                end = start + this.pagesize;
            }

            // rowNumber starts from 1
            this.filteredRows = Range.between(start + 1, end);
        }
        else
        {
            this.filteredRows = Range.between(1, Integer.MAX_VALUE);
        }
    }

    /**
     * Is the current row included in the "to-be-evaluated" range? Called by nested ColumnTags. If <code>false</code>
     * column body is skipped.
     * @return <code>true</code> if the current row must be evaluated because is included in output or because is
     * included in sorting.
     */
    public boolean isIncludedRow()
    {
        return this.filteredRows.contains(this.rowNumber);
    }

    /**
     * Create a complete string for compatibility with previous version before expression evaluation. This approach is
     * optimized for new expressions, not for previous property/scope parameters.
     * @return Expression composed by scope + name + property
     */
    private String getFullObjectName()
    {
        // only evaluate if needed, else preserve original list
        if (this.name == null)
        {
            return null;
        }

        return this.name;
    }

    /**
     * init the href object used to generate all the links for pagination, sorting, exporting.
     * @param requestHelper request helper used to extract the base Href
     */
    protected void initHref(RequestHelper requestHelper)
    {
        // get the href for this request
        this.baseHref = requestHelper.getHref();

        if (this.excludedParams != null)
        {
            String[] splittedExcludedParams = StringUtils.split(this.excludedParams);

            // handle * keyword
            if (splittedExcludedParams.length == 1 && "*".equals(splittedExcludedParams[0]))
            {
                // @todo cleanup: paramEncoder initialization should not be done here
                if (this.paramEncoder == null)
                {
                    this.paramEncoder = new ParamEncoder(getUid());
                }

                Iterator<String> paramsIterator = this.baseHref.getParameterMap().keySet().iterator();
                while (paramsIterator.hasNext())
                {
                    String key = paramsIterator.next();

                    // don't remove parameters added by the table tag
                    if (!this.paramEncoder.isParameterEncoded(key))
                    {
                        this.baseHref.removeParameter(key);
                    }
                }
            }
            else
            {
                for (int j = 0; j < splittedExcludedParams.length; j++)
                {
                    this.baseHref.removeParameter(splittedExcludedParams[j]);
                }
            }
        }

        if (this.requestUri != null)
        {
            // if user has added a requestURI create a new href
            String fullURI = this.requestUri;
            if (!this.dontAppendContext)
            {
                String contextPath = ((HttpServletRequest) this.pageContext.getRequest()).getContextPath();

                // prepend the context path if any.
                // actually checks if context path is already there for people which manually add it
                if (!StringUtils.isEmpty(contextPath)
                    && this.requestUri != null
                    && this.requestUri.startsWith("/")
                    && !this.requestUri.startsWith(contextPath))
                {
                    fullURI = contextPath + this.requestUri;
                }
            }

            // call encodeURL to preserve session id when cookies are disabled
            fullURI = ((HttpServletResponse) this.pageContext.getResponse()).encodeURL(fullURI);

            this.baseHref.setFullUrl(fullURI);

        }

    }

    /**
     * Draw the table. This is where everything happens, we figure out what values we are supposed to be showing, we
     * figure out how we are supposed to be showing them, then we draw them.
     * @return int
     * @throws JspException generic exception
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    @Override
    public int doEndTag() throws JspException
    {

        if (log.isDebugEnabled())
        {
            log.debug("[{}] doEndTag called", getUid());
        }

        if (!this.doAfterBodyExecuted)
        {
            if (log.isDebugEnabled())
            {
                log.debug("[{}] tag body is empty.", getUid());
            }

            // first row (created in doStartTag)
            if (this.currentRow != null)
            {
                // if yes add to table model and remove
                this.tableModel.addRow(this.currentRow);
            }

            // other rows
            while (this.tableIterator.hasNext())
            {
                Object iteratedObject = this.tableIterator.next();
                this.rowNumber++;

                // Row object for Cell values
                this.currentRow = new Row(iteratedObject, this.rowNumber);

                this.tableModel.addRow(this.currentRow);
            }
        }

        // if no rows are defined automatically get all properties from bean
        if (this.tableModel.isEmpty())
        {
            describeEmptyTable();
        }

        TableDecorator tableDecorator = this.properties.getDecoratorFactoryInstance().loadTableDecorator(
            this.pageContext,
            getConfiguredDecoratorName());

        if (tableDecorator != null)
        {
            tableDecorator.init(this.pageContext, this.list, this.tableModel);
            this.tableModel.setTableDecorator(tableDecorator);
        }

        setupViewableData();

        TableTotaler totaler = this.properties.getDecoratorFactoryInstance().loadTableTotaler(
            this.pageContext,
            getTotalerName());
        if (totaler != null)
        {
            totaler.init(this.tableModel);
            this.tableModel.setTotaler(totaler);

        }

        // Figure out how we should sort this data, typically we just sort
        // the data being shown, but the programmer can override this behavior
        if (this.paginatedList == null && this.tableModel.isLocalSort())
        {
            if (!this.tableModel.isSortFullTable())
            {
                this.tableModel.sortPageList();
            }
        }

        // Get the data back in the representation that the user is after, do they want HTML/XML/CSV/EXCEL/etc...
        int returnValue = EVAL_PAGE;

        // check for nested tables
        Object previousMediaType = this.pageContext.getAttribute(PAGE_ATTRIBUTE_MEDIA);
        if (MediaTypeEnum.HTML.equals(this.currentMediaType)
            && (previousMediaType == null || MediaTypeEnum.HTML.equals(previousMediaType)))
        {
            writeHTMLData();
        }
        else if (!MediaTypeEnum.HTML.equals(this.currentMediaType))
        {
            if (log.isDebugEnabled())
            {
                log.debug("[" + getUid() + "] doEndTag - exporting");
            }

            returnValue = doExport();
        }

        // do not remove media attribute! if the table is nested in other tables this is still needed

        if (log.isDebugEnabled())
        {
            log.debug("[{}] doEndTag - end", getUid());
        }

        cleanUp();
        return returnValue;
    }

    /**
     * Returns the name of the table decorator that should be applied to this table, which is either the decorator
     * configured in the property "decorator", or if none is configured in said property, a decorator configured with
     * the "decorator.media.[media type]" property, or null if none is configured.
     * @return Name of the table decorator that should be applied to this table.
     */
    private String getConfiguredDecoratorName()
    {
        String tableDecoratorName = (this.decoratorName == null) ? this.properties
            .getMediaTypeDecoratorName(this.currentMediaType) : this.decoratorName;
        return tableDecoratorName;
    }

    /**
     * Gets the totaler name.
     *
     * @return the totaler name
     */
    private String getTotalerName()
    {
        return (this.totalerName == null) ? this.properties.getTotalerName() : this.totalerName;
    }

    /**
     * clean up instance variables, but not the ones representing tag attributes.
     */
    private void cleanUp()
    {
        // reset instance variables (non attributes)
        this.currentMediaType = null;
        this.baseHref = null;
        this.caption = null;
        this.captionTag = null;
        this.currentRow = null;
        this.doAfterBodyExecuted = false;
        this.footer = null;
        this.listHelper = null;
        this.pageNumber = 0;
        this.paramEncoder = null;
        this.properties = null;
        this.rowNumber = 1;
        this.tableIterator = null;
        this.tableModel = null;
        this.list = null;
        this.paginatedList = null;
    }

    /**
     * If no columns are provided, automatically add them from bean properties. Get the first object in the list and get
     * all the properties (except the "class" property which is automatically skipped). Of course this isn't possible
     * for empty lists.
     */
    private void describeEmptyTable()
    {
        this.tableIterator = IteratorUtils.getIterator(this.list);

        if (this.tableIterator.hasNext())
        {
            Object iteratedObject = this.tableIterator.next();
            Map<String, String> objectProperties = new HashMap<String, String>();

            // if it's a String don't add the "Bytes" column
            if (iteratedObject instanceof String)
            {
                return;
            }
            // if it's a map already use key names for column headers
            if (iteratedObject instanceof Map)
            {
                objectProperties = (Map<String, String>) iteratedObject;
            }
            else
            {
                try
                {
                    objectProperties = BeanUtils.describe(iteratedObject);
                }
                catch (Exception e)
                {
                    log.warn("Unable to automatically add columns: " + e.getMessage(), e);
                }
            }

            // iterator on properties names
            Iterator<String> propertiesIterator = objectProperties.keySet().iterator();

            while (propertiesIterator.hasNext())
            {
                // get the property name
                String propertyName = propertiesIterator.next();

                // dont't want to add the standard "class" property
                if (!"class".equals(propertyName)) //$NON-NLS-1$
                {
                    // creates a new header and add to the table model
                    HeaderCell headerCell = new HeaderCell();
                    headerCell.setBeanPropertyName(propertyName);

                    // handle title i18n
                    headerCell.setTitle(this.properties.geResourceProvider().getResource(
                        null,
                        propertyName,
                        this,
                        this.pageContext));

                    this.tableModel.addColumnHeader(headerCell);
                }
            }
        }
    }

    /**
     * Called when data are not displayed in a html page but should be exported.
     * @return int SKIP_PAGE
     * @throws JspException generic exception
     */
    protected int doExport() throws JspException
    {

        boolean exportFullList = this.properties.getExportFullList();

        if (log.isDebugEnabled())
        {
            log.debug("[{}] currentMediaType={}", getUid(), this.currentMediaType);
        }

        boolean exportHeader = this.properties.getExportHeader(this.currentMediaType);
        boolean exportDecorated = this.properties.getExportDecorated();

        ExportView exportView = ExportViewFactory.getInstance().getView(
            this.currentMediaType,
            this.tableModel,
            exportFullList,
            exportHeader,
            exportDecorated);

        try
        {
            writeExport(exportView);
        }
        catch (IOException e)
        {
            throw new WrappedRuntimeException(getClass(), e);
        }

        return SKIP_PAGE;
    }

    /**
     * Will write the export. The default behavior is to write directly to the response. If the ResponseOverrideFilter
     * is configured for this request, will instead write the exported content to a map in the Request object.
     *
     * @param exportView export view
     * @throws IOException exception thrown when writing content to the response
     * @throws JspException for problem in clearing the response or for invalid export views
     */
    protected void writeExport(ExportView exportView) throws IOException, JspException
    {
        String filename = this.properties.getExportFileName(this.currentMediaType);

        HttpServletResponse response = (HttpServletResponse) this.pageContext.getResponse();
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();

        Map<String, Object> bean = (Map<String, Object>) request.getAttribute(FILTER_CONTENT_OVERRIDE_BODY);
        boolean usingFilter = bean != null;

        String mimeType = exportView.getMimeType();
        // original encoding, be sure to add it back after reset()
        String characterEncoding = response.getCharacterEncoding();

        if (usingFilter)
        {
            if (!bean.containsKey(TableTagParameters.BEAN_BUFFER))
            {
                // We are running under the export filter, call it
                log.debug("Exportfilter enabled in unbuffered mode, setting headers");
                response.addHeader(TableTagParameters.PARAMETER_EXPORTING, TagConstants.EMPTY_STRING);
            }
            else
            {
                // We are running under the export filter in buffered mode
                bean.put(TableTagParameters.BEAN_CONTENTTYPE, mimeType);
                bean.put(TableTagParameters.BEAN_FILENAME, filename);

                if (exportView instanceof TextExportView)
                {
                    StringWriter writer = new StringWriter();
                    ((TextExportView) exportView).doExport(writer, characterEncoding);
                    bean.put(TableTagParameters.BEAN_BODY, writer.toString());
                }
                else if (exportView instanceof BinaryExportView)
                {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ((BinaryExportView) exportView).doExport(stream);
                    bean.put(TableTagParameters.BEAN_BODY, stream.toByteArray());

                }
                else
                {
                    throw new JspTagException("Export view "
                        + exportView.getClass().getName()
                        + " must implement TextExportView or BinaryExportView");
                }

                return;
            }
        }
        else
        {
            log.debug("Exportfilter NOT enabled");
            // response can't be already committed at this time
            if (response.isCommitted())
            {
                throw new ExportException(getClass());
            }

            try
            {
                response.reset();
                this.pageContext.getOut().clearBuffer();
            }
            catch (Exception e)
            {
                throw new ExportException(getClass());
            }
        }

        if (!usingFilter && characterEncoding != null && !StringUtils.contains(mimeType, "charset") //$NON-NLS-1$
            && exportView instanceof TextExportView)
        {
            mimeType += "; charset=" + characterEncoding; //$NON-NLS-1$
        }

        response.setContentType(mimeType);

        if (StringUtils.isNotEmpty(filename))
        {
            response.setHeader("Content-Disposition", //$NON-NLS-1$
                "attachment; filename=\"" + filename + "\""); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (exportView instanceof TextExportView)
        {
            Writer writer;
            if (usingFilter)
            {
                writer = response.getWriter();
            }
            else
            {
                writer = this.pageContext.getOut();
            }

            ((TextExportView) exportView).doExport(writer, characterEncoding);
        }
        else if (exportView instanceof BinaryExportView)
        {
            // dealing with binary content
            // note that this is not assured to work on any application server if the filter is not enabled. According
            // to the jsp specs response.getOutputStream() should no be called in jsps.
            ((BinaryExportView) exportView).doExport(response.getOutputStream());
        }
        else
        {
            throw new JspTagException("Export view "
                + exportView.getClass().getName()
                + " must implement TextExportView or BinaryExportView");
        }

        log.debug("Export completed");

    }

    /**
     * This sets the list of all of the data that will be displayed on the page via the table tag. This might include
     * just a subset of the total data in the list due to to paging being active, or the user asking us to just show a
     * subset, etc...
     */
    protected void setupViewableData()
    {

        // If the user has changed the way our default behavior works, then we need to look for it now, and resort
        // things if needed before we ask for the viewable part. (this is a bad place for this, this should be
        // refactored and moved somewhere else).

        if (this.paginatedList == null || this.tableModel.isLocalSort())
        {
            if (this.tableModel.isSortFullTable())
            {
                // Sort the total list...
                this.tableModel.sortFullList();
            }
        }

        Object originalData = this.tableModel.getRowListFull();

        // If they have asked for a subset of the list via the length
        // attribute, then only fetch those items out of the master list.
        List fullList = CollectionUtil.getListFromObject(originalData, this.offset, this.length);

        int pageOffset = this.offset;
        // If they have asked for just a page of the data, then use the
        // SmartListHelper to figure out what page they are after, etc...
        if (this.paginatedList == null && this.pagesize > 0)
        {
            this.listHelper = new SmartListHelper(fullList, (this.partialList)
                ? ((Integer) this.size).intValue()
                : fullList.size(), this.pagesize, this.properties, this.partialList);
            this.listHelper.setCurrentPage(this.pageNumber);
            pageOffset = this.listHelper.getFirstIndexForCurrentPage();
            fullList = this.listHelper.getListForCurrentPage();
        }
        else if (this.paginatedList != null)
        {
            this.listHelper = new PaginatedListSmartListHelper(this.paginatedList, this.properties);
        }
        this.tableModel.setRowListPage(fullList);
        this.tableModel.setPageOffset(pageOffset);
    }

    /**
     * Uses HtmlTableWriter to write table called when data have to be displayed in a html page.
     * @throws JspException generic exception
     */
    protected void writeHTMLData() throws JspException
    {
        JspWriter out = this.pageContext.getOut();

        String css = this.properties.getCssTable();
        if (StringUtils.isNotBlank(css))
        {
            this.addClass(css);
        }
        // use HtmlTableWriter to write table
        new HtmlTableWriter(
            this.properties,
            this.baseHref,
            this.export,
            out,
            getCaptionTag(),
            this.paginatedList,
            this.listHelper,
            this.pagesize,
            getAttributeMap(),
            this.uid).writeTable(this.tableModel, this.getUid());

        if (this.varTotals != null)
        {
            this.pageContext.setAttribute(this.varTotals, getTotals());
        }
    }

    /**
     * Get the column totals Map. If there is no varTotals defined, there are no totals.
     * @return a Map of totals where the key is the column number and the value is the total for that column
     */
    public Map<String, Double> getTotals()
    {
        Map<String, Double> totalsMap = new HashMap<String, Double>();
        if (this.varTotals != null)
        {
            List<HeaderCell> headers = this.tableModel.getHeaderCellList();
            for (Iterator<HeaderCell> iterator = headers.iterator(); iterator.hasNext();)
            {
                HeaderCell headerCell = iterator.next();
                if (headerCell.isTotaled())
                {
                    totalsMap.put("column" + (headerCell.getColumnNumber() + 1), Double.valueOf(headerCell.getTotal()));
                }
            }
        }
        return totalsMap;
    }

    /**
     * Get the table model for this tag. Sometimes required by local tags that cooperate with DT. USE THIS METHOD WITH
     * EXTREME CAUTION; IT PROVIDES ACCESS TO THE INTERNALS OF DISPLAYTAG, WHICH ARE NOT TO BE CONSIDERED STABLE PUBLIC
     * INTERFACES.
     * @return the TableModel
     */
    public TableModel getTableModel()
    {
        return this.tableModel;
    }

    /**
     * Called by the setProperty tag to override some default behavior or text String.
     * @param propertyName String property name
     * @param propertyValue String property value
     */
    public void setProperty(String propertyName, String propertyValue)
    {
        this.properties.setProperty(propertyName, propertyValue);
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    @Override
    public void release()
    {
        if (log.isDebugEnabled())
        {
            log.debug("[{}] release() called", getUid());
        }

        super.release();

        // tag attributes
        this.decoratorName = null;
        this.defaultSortedColumn = -1;
        this.defaultSortOrder = null;
        this.export = false;
        this.length = 0;
        this.listAttribute = null;
        this.localSort = true;
        this.name = null;
        this.offset = 0;
        this.pagesize = 0;
        this.partialList = false;
        this.requestUri = null;
        this.dontAppendContext = false;
        this.sortFullTable = null;
        this.excludedParams = null;
        this.filteredRows = null;
        this.uid = null;
        this.keepStatus = false;
        this.clearStatus = false;
        this.form = null;
    }

    /**
     * Returns the name.
     * @return String
     */
    protected String getName()
    {
        return this.name;
    }

    /**
     * encode a parameter name to be unique in the page using ParamEncoder.
     * @param parameterName parameter name to encode
     * @return String encoded parameter name
     */
    private String encodeParameter(String parameterName)
    {
        // paramEncoder has been already instantiated?
        if (this.paramEncoder == null)
        {
            // use the id attribute to get the unique identifier
            this.paramEncoder = new ParamEncoder(getUid());
        }

        return this.paramEncoder.encodeParameterName(parameterName);
    }

}
