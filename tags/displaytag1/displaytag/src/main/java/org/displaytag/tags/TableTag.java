/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.tags;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.LongRange;
import org.apache.commons.lang.math.Range;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.DecoratorFactory;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ExportException;
import org.displaytag.exception.FactoryInstantiationException;
import org.displaytag.exception.InvalidTagAttributeValueException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.exception.WrappedRuntimeException;
import org.displaytag.export.BinaryExportView;
import org.displaytag.export.ExportView;
import org.displaytag.export.ExportViewFactory;
import org.displaytag.export.TextExportView;
import org.displaytag.model.Cell;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;
import org.displaytag.pagination.SmartListHelper;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.SortOrderEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.Anchor;
import org.displaytag.util.CollectionUtil;
import org.displaytag.util.DependencyChecker;
import org.displaytag.util.Href;
import org.displaytag.util.ParamEncoder;
import org.displaytag.util.RequestHelper;
import org.displaytag.util.RequestHelperFactory;
import org.displaytag.util.TagConstants;


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
    private static Log log = LogFactory.getLog(TableTag.class);

    /**
     * RequestHelperFactory instance used for link generation.
     */
    private static RequestHelperFactory rhf;

    /**
     * Object (collection, list) on which the table is based. This is not set directly using a tag attribute and can be
     * cleaned.
     */
    protected Object list;

    // -- start tag attributes --

    /**
     * Object (collection, list) on which the table is based. Set directly using the "list" attribute or evaluated from
     * expression.
     */
    protected Object listAttribute;

    /**
     * actual row number, updated during iteration.
     */
    private int rowNumber = 1;

    /**
     * name of the object to use for iteration. Can contain expressions.
     */
    private String name;

    /**
     * property to get into the bean defined by "name".
     * @deprecated Use expressions in "name" attribute
     */
    private String property;

    /**
     * scope of the bean defined by "name". Use expressions in name instead
     * @deprecated
     */
    private String scope;

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
     * add export links.
     */
    private boolean export;

    /**
     * list offset.
     */
    private int offset;

    /**
     * sort the full list?
     */
    private Boolean sortFullTable;

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

    // -- end tag attributes --

    /**
     * Map which contains previous row values. Needed for grouping
     */
    private Map previousRow;

    /**
     * table model - initialized in doStartTag().
     */
    private TableModel tableModel;

    /**
     * current row.
     */
    private Row currentRow;

    /**
     * next row.
     */
    private Map nextRow;

    /**
     * Used by various functions when the person wants to do paging - cleaned in doEndTag().
     */
    private SmartListHelper listHelper;

    /**
     * base href used for links - set in initParameters().
     */
    private Href baseHref;

    /**
     * table properties - set in doStartTag().
     */
    private TableProperties properties;

    /**
     * page number - set in initParameters().
     */
    private int pageNumber = 1;

    /**
     * Iterator on collection.
     */
    private Iterator tableIterator;

    /**
     * export type - set in initParameters().
     */
    private MediaTypeEnum currentMediaType;

    /**
     * daAfterBody() has been executed at least once?
     */
    private boolean doAfterBodyExecuted;

    /**
     * The param encoder used to generate unique parameter names. Initialized at the first use of encodeParameter().
     */
    private ParamEncoder paramEncoder;

    /**
     * static footer added using the footer tag.
     */
    private String footer;

    /**
     * static caption added using the footer tag.
     */
    private String caption;

    /**
     * Included row range. If no rows can be skipped the range is from 0 to Long.MAX_VALUE. Range check should be always
     * done using containsLong(). This is an instance of org.apache.commons.lang.math.Range, but it's declared as Object
     * to avoid runtime errors while Jasper tries to compile the page and commons lang 2.0 is not available. Commons
     * lang version will be checked in the doStartTag() method in order to provide a more user friendly message.
     */
    private Object filteredRows;

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
    }

    /**
     * Sets the content of the caption. Called by a nested caption tag.
     * @param string caption content
     */
    public void setCaption(String string)
    {
        this.caption = string;
    }

    /**
     * Is the current row empty?
     * @return true if the current row is empty
     */
    protected boolean isEmpty()
    {
        return this.currentRow == null;
    }

    /**
     * setter for the "sort" attribute.
     * @param value "page" (sort a single page) or "list" (sort the full list)
     * @throws InvalidTagAttributeValueException if value is not "page" or "list"
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
     * @deprecated use setName() to get the object from the page or request scope instead of setting it directly here
     */
    public void setList(Object value)
    {
        this.listAttribute = value;
    }

    /**
     * Sets the name of the object to use for iteration.
     * @param value name of the object to use for iteration (can contain expression). It also supports direct setting of
     * a list, for jsp 2.0 containers where users can set up a data source here using EL expressions.
     */
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
     * Sets the name of the object to use for iteration. This setter is needed for jsp 1.1 container which doesn't
     * support the String - Object conversion. The bean info class will swith to this setter.
     * @param value name of the object
     */
    public void setNameString(String value)
    {
        this.name = value;
    }

    /**
     * Sets the property to get into the bean defined by "name".
     * @param value property name
     * @deprecated Use expressions in "name" attribute
     */
    public void setProperty(String value)
    {
        this.property = value;
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
     * Setter for object scope.
     * @param value String
     * @deprecated Use expressions in "name" attribute
     */
    public void setScope(String value)
    {
        this.scope = value;
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
     * Is export enabled?
     * @param value <code>true</code> if export should be enabled
     */
    public void setExport(boolean value)
    {
        this.export = value;
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
        if (getHtmlId() == null)
        {
            setHtmlId(value); // by default id is actually used for the html id attribute, if no htmlId is added
        }

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
     * It's a getter.
     * @return the this.pageContext
     */
    public PageContext getPageContext()
    {
        return this.pageContext;
    }

    /**
     * Returns the properties.
     * @return TableProperties
     */
    protected TableProperties getProperties()
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
            log.debug("[" + getUid() + "] addColumn " + column);
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
            this.currentRow.addCell(cell);
        }
    }

    /**
     * Is this the first iteration?
     * @return boolean <code>true</code> if this is the first iteration
     */
    protected boolean isFirstIteration()
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
    public int doStartTag() throws JspException
    {
        DependencyChecker.check();

        // needed before column processing, elsewhere registered views will not be added
        ExportViewFactory.getInstance();

        if (log.isDebugEnabled())
        {
            log.debug("[" + getUid() + "] doStartTag called");
        }

        this.properties = TableProperties.getInstance((HttpServletRequest) pageContext.getRequest());
        this.tableModel = new TableModel(this.properties, pageContext.getResponse().getCharacterEncoding());

        // copying id to the table model for logging
        this.tableModel.setId(getUid());

        initParameters();

        Object previousMediaType = this.pageContext.getAttribute(PAGE_ATTRIBUTE_MEDIA);
        // set the PAGE_ATTRIBUTE_MEDIA attribute in the page scope
        if (this.currentMediaType != null
            && (previousMediaType == null || MediaTypeEnum.HTML.equals(previousMediaType)))
        {
            if (log.isDebugEnabled())
            {
                log.debug("[" + getUid() + "] setting media [" + this.currentMediaType + "] in this.pageContext");
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
    public int doAfterBody()
    {
        // doAfterBody() has been called, body is not empty
        this.doAfterBodyExecuted = true;

        if (log.isDebugEnabled())
        {
            log.debug("[" + getUid() + "] doAfterBody called - iterating on row " + this.rowNumber);
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
            log.debug("[" + getUid() + "] doIteration called");
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
                        log.debug("[" + getUid() + "] setting attribute \"" + getUid() + "\" in pageContext");
                    }
                    this.pageContext.setAttribute(getUid(), iteratedObject);

                }
                else
                {
                    // if row is null remove previous object
                    this.pageContext.removeAttribute(getUid());
                }
                // set the current row number into this.pageContext
                this.pageContext.setAttribute(getUid() + TableTagExtraInfo.ROWNUM_SUFFIX, new Integer(this.rowNumber));
            }

            // Row object for Cell values
            this.currentRow = new Row(iteratedObject, this.rowNumber);

            // new iteration
            // using int to avoid deprecation error in compilation using j2ee 1.3
            return 2;
        }

        if (log.isDebugEnabled())
        {
            log.debug("[" + getUid() + "] doIteration() - iterator ended after " + (this.rowNumber - 1) + " rows");
        }

        // end iteration
        return SKIP_BODY;
    }

    /**
     * Reads parameters from the request and initialize all the needed table model attributes.
     * @throws ObjectLookupException for problems in evaluating the expression in the "name" attribute
     * @throws FactoryInstantiationException for problems in instantiating a RequestHelperFactory
     */
    private void initParameters() throws ObjectLookupException, FactoryInstantiationException
    {
        if (rhf == null)
        {
            // first time initialization
            rhf = this.properties.getRequestHelperFactoryInstance();
        }

        RequestHelper requestHelper = rhf.getRequestHelperInstance(this.pageContext);

        initHref(requestHelper);

        Integer pageNumberParameter = requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_PAGE));
        this.pageNumber = (pageNumberParameter == null) ? 1 : pageNumberParameter.intValue();

        Integer sortColumnParameter = requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_SORT));
        int sortColumn = (sortColumnParameter == null) ? this.defaultSortedColumn : sortColumnParameter.intValue();
        this.tableModel.setSortedColumnNumber(sortColumn);

        // default value
        boolean finalSortFull = this.properties.getSortFullList();

        // user value for this single table
        if (this.sortFullTable != null)
        {
            finalSortFull = this.sortFullTable.booleanValue();
        }

        this.tableModel.setSortFullTable(finalSortFull);

        SortOrderEnum paramOrder = SortOrderEnum.fromCode(requestHelper
            .getIntParameter(encodeParameter(TableTagParameters.PARAMETER_ORDER)));

        // if no order parameter is set use default
        if (paramOrder == null)
        {
            paramOrder = this.defaultSortOrder;
        }

        boolean order = SortOrderEnum.DESCENDING != paramOrder;
        this.tableModel.setSortOrderAscending(order);

        Integer exportTypeParameter = requestHelper
            .getIntParameter(encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE));
        this.currentMediaType = MediaTypeEnum.fromCode(exportTypeParameter);
        if (this.currentMediaType == null)
        {
            this.currentMediaType = MediaTypeEnum.HTML;
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

        // do we really need to skip any row?
        boolean wishOptimizedIteration = (this.pagesize > 0 // we are paging
            || this.offset > 0 // or we are skipping some records using offset
        || this.length > 0 // or we are limiting the records using length
        );

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
            if (length > 0)
            {
                end = start + this.length;
            }

            if (this.pagesize > 0)
            {
                int fullSize = ((Collection) this.list).size();
                start = (this.pageNumber - 1) * this.pagesize;

                // invalid page requested, go back to page one
                if (start > fullSize)
                {
                    start = 0;
                }

                end = start + this.pagesize;
            }

            // rowNumber starts from 1
            filteredRows = new LongRange(start + 1, end);
        }
        else
        {
            filteredRows = new LongRange(1, Long.MAX_VALUE);
        }

        this.tableIterator = IteratorUtils.getIterator(this.list);
    }

    /**
     * Is the current row included in the "to-be-evaluated" range? Called by nested ColumnTags. If <code>false</code>
     * column body is skipped.
     * @return <code>true</code> if the current row must be evaluated because is included in output or because is
     * included in sorting.
     */
    protected boolean isIncludedRow()
    {
        return ((Range) filteredRows).containsLong(this.rowNumber);
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

        StringBuffer fullName = new StringBuffer(30);

        // append scope
        if (StringUtils.isNotBlank(this.scope))
        {
            fullName.append(this.scope).append("Scope."); //$NON-NLS-1$
        }

        // base bean name
        fullName.append(this.name);

        // append property
        if (StringUtils.isNotBlank(this.property))
        {
            fullName.append('.').append(this.property);
        }

        return fullName.toString();
    }

    /**
     * init the href object used to generate all the links for pagination, sorting, exporting.
     * @param requestHelper request helper used to extract the base Href
     */
    protected void initHref(RequestHelper requestHelper)
    {
        // get the href for this request
        Href normalHref = requestHelper.getHref();

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

                Iterator paramsIterator = normalHref.getParameterMap().keySet().iterator();
                while (paramsIterator.hasNext())
                {
                    String key = (String) paramsIterator.next();

                    // don't remove parameters added by the table tag
                    if (!this.paramEncoder.isParameterEncoded(key))
                    {
                        normalHref.removeParameter(key);
                    }
                }
            }
            else
            {
                for (int j = 0; j < splittedExcludedParams.length; j++)
                {
                    normalHref.removeParameter(splittedExcludedParams[j]);
                }
            }
        }

        if (this.requestUri != null)
        {
            // if user has added a requestURI create a new href
            String fullURI = requestUri;
            if (!this.dontAppendContext)
            {
                String contextPath = ((HttpServletRequest) this.pageContext.getRequest()).getContextPath();

                // prepend the context path if any.
                // actually checks if context path is already there for people which manually add it
                if (!StringUtils.isEmpty(contextPath)
                    && requestUri != null
                    && requestUri.startsWith("/")
                    && !requestUri.startsWith(contextPath))
                {
                    fullURI = contextPath + this.requestUri;
                }
            }

            // call encodeURL to preserve session id when cookies are disabled
            fullURI = ((HttpServletResponse) this.pageContext.getResponse()).encodeURL(fullURI);
            this.baseHref = new Href(fullURI);

            // ... and copy parameters from the current request
            Map parameterMap = normalHref.getParameterMap();
            this.baseHref.addParameterMap(parameterMap);
        }
        else
        {
            // simply copy href
            this.baseHref = normalHref;
        }
    }

    /**
     * Draw the table. This is where everything happens, we figure out what values we are supposed to be showing, we
     * figure out how we are supposed to be showing them, then we draw them.
     * @return int
     * @throws JspException generic exception
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException
    {

        if (log.isDebugEnabled())
        {
            log.debug("[" + getUid() + "] doEndTag called");
        }

        if (!this.doAfterBodyExecuted)
        {
            if (log.isDebugEnabled())
            {
                log.debug("[" + getUid() + "] tag body is empty.");
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

        TableDecorator tableDecorator = DecoratorFactory.loadTableDecorator(this.decoratorName);

        if (tableDecorator != null)
        {
            tableDecorator.init(this.pageContext, this.list);
            this.tableModel.setTableDecorator(tableDecorator);
        }

        setupViewableData();

        // Figure out how we should sort this data, typically we just sort
        // the data being shown, but the programmer can override this behavior

        if (!this.tableModel.isSortFullTable())
        {
            this.tableModel.sortPageList();
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
        // this.pageContext.removeAttribute(PAGE_ATTRIBUTE_MEDIA);

        if (log.isDebugEnabled())
        {
            log.debug("[" + getUid() + "] doEndTag - end");
        }

        cleanUp();
        return returnValue;
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
        this.currentRow = null;
        this.doAfterBodyExecuted = false;
        this.footer = null;
        this.listHelper = null;
        this.nextRow = null;
        this.pageNumber = 0;
        this.paramEncoder = null;
        this.previousRow = null;
        this.properties = null;
        this.rowNumber = 1;
        this.tableIterator = null;
        this.tableModel = null;
        this.list = null;
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
            Map objectProperties = new HashMap();

            // if it's a String don't add the "Bytes" column
            if (iteratedObject instanceof String)
            {
                return;
            }
            // if it's a map already use key names for column headers
            if (iteratedObject instanceof Map)
            {
                objectProperties = (Map) iteratedObject;
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
            Iterator propertiesIterator = objectProperties.keySet().iterator();

            while (propertiesIterator.hasNext())
            {
                // get the property name
                String propertyName = (String) propertiesIterator.next();

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
            log.debug("[" + getUid() + "] currentMediaType=" + this.currentMediaType);
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
     * @param exportView export view
     * @throws JspException for problem in clearing the response or for invalid export views
     * @throws IOException exception thrown when writing content to the response
     */
    protected void writeExport(ExportView exportView) throws IOException, JspException
    {
        String filename = properties.getExportFileName(this.currentMediaType);

        HttpServletResponse response = (HttpServletResponse) this.pageContext.getResponse();
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();

        Map bean = (Map) request.getAttribute(FILTER_CONTENT_OVERRIDE_BODY);
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
                    ((TextExportView) exportView).doExport(writer);
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
                pageContext.getOut().clearBuffer();
            }
            catch (Exception e)
            {
                throw new ExportException(getClass());
            }
        }

        if (!usingFilter && characterEncoding != null && mimeType.indexOf("charset") == -1) //$NON-NLS-1$
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
                writer = pageContext.getOut();
            }

            ((TextExportView) exportView).doExport(writer);
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

        if (this.tableModel.isSortFullTable())
        {
            // Sort the total list...
            this.tableModel.sortFullList();
        }

        Object originalData = this.tableModel.getRowListFull();

        // If they have asked for a subset of the list via the length
        // attribute, then only fetch those items out of the master list.
        List fullList = CollectionUtil.getListFromObject(originalData, this.offset, this.length);

        int pageOffset = this.offset;
        // If they have asked for just a page of the data, then use the
        // SmartListHelper to figure out what page they are after, etc...
        if (this.pagesize > 0)
        {
            this.listHelper = new SmartListHelper(fullList, fullList.size(), this.pagesize, this.properties);
            this.listHelper.setCurrentPage(this.pageNumber);
            pageOffset = this.listHelper.getFirstIndexForCurrentPage();
            fullList = this.listHelper.getListForCurrentPage();
        }

        this.tableModel.setRowListPage(fullList);
        this.tableModel.setPageOffset(pageOffset);
    }

    /**
     * called when data have to be displayed in a html page.
     * @throws JspException generic exception
     */
    private void writeHTMLData() throws JspException
    {
        JspWriter out = this.pageContext.getOut();

        if (log.isDebugEnabled())
        {
            log.debug("[" + getUid() + "] getHTMLData called for table [" + getUid() + "]");
        }

        boolean noItems = this.tableModel.getRowListPage().size() == 0;

        if (noItems && !this.properties.getEmptyListShowTable())
        {
            write(this.properties.getEmptyListMessage(), out);
            return;
        }

        // variables to hold the previous row columns values.
        this.previousRow = new Hashtable(10);

        // variables to hold next row column values.
        this.nextRow = new Hashtable(10);

        // Put the page stuff there if it needs to be there...
        if (this.properties.getAddPagingBannerTop())
        {
            // search result and navigation bar
            writeSearchResultAndNavigation();
        }

        String css = this.properties.getCssTable();
        if (StringUtils.isNotBlank(css))
        {
            this.addClass(css);
        }

        // open table
        write(getOpenTag(), out);

        // caption
        if (this.caption != null)
        {
            write(this.caption, out);
        }

        // thead
        if (this.properties.getShowHeader())
        {
            writeTableHeader();
        }

        if (this.footer != null)
        {
            write(TagConstants.TAG_TFOOTER_OPEN, out);
            write(this.footer, out);
            write(TagConstants.TAG_TFOOTER_CLOSE, out);
            // reset footer
            this.footer = null;
        }

        // open table body
        write(TagConstants.TAG_TBODY_OPEN, out);

        // write table body
        writeTableBody();

        // close table body
        write(TagConstants.TAG_TBODY_CLOSE, out);

        // close table
        write(getCloseTag(), out);

        writeTableFooter();

        if (this.tableModel.getTableDecorator() != null)
        {
            this.tableModel.getTableDecorator().finish();
        }

        if (log.isDebugEnabled())
        {
            log.debug("[" + getUid() + "] getHTMLData end");
        }
    }

    /**
     * Generates the table header, including the first row of the table which displays the titles of the columns.
     */
    private void writeTableHeader()
    {
        JspWriter out = this.pageContext.getOut();

        if (log.isDebugEnabled())
        {
            log.debug("[" + getUid() + "] getTableHeader called");
        }

        // open thead
        write(TagConstants.TAG_THEAD_OPEN, out);

        // open tr
        write(TagConstants.TAG_TR_OPEN, out);

        // no columns?
        if (this.tableModel.isEmpty())
        {
            write(TagConstants.TAG_TH_OPEN, out);
            write(TagConstants.TAG_TH_CLOSE, out);
        }

        // iterator on columns for header
        Iterator iterator = this.tableModel.getHeaderCellList().iterator();

        while (iterator.hasNext())
        {
            // get the header cell
            HeaderCell headerCell = (HeaderCell) iterator.next();

            if (headerCell.getSortable())
            {
                String cssSortable = this.properties.getCssSortable();
                headerCell.addHeaderClass(cssSortable);
            }

            // if sorted add styles
            if (headerCell.isAlreadySorted())
            {
                // sorted css class
                headerCell.addHeaderClass(this.properties.getCssSorted());

                // sort order css class
                headerCell.addHeaderClass(this.properties.getCssOrder(this.tableModel.isSortOrderAscending()));
            }

            // append th with html attributes
            write(headerCell.getHeaderOpenTag(), out);

            // title
            String header = headerCell.getTitle();

            // column is sortable, create link
            if (headerCell.getSortable())
            {
                // creates the link for sorting
                Anchor anchor = new Anchor(getSortingHref(headerCell), header);

                // append to buffer
                header = anchor.toString();
            }

            write(header, out);
            write(headerCell.getHeaderCloseTag(), out);
        }

        // close tr
        write(TagConstants.TAG_TR_CLOSE, out);

        // close thead
        write(TagConstants.TAG_THEAD_CLOSE, out);

        if (log.isDebugEnabled())
        {
            log.debug("[" + getUid() + "] getTableHeader end");
        }
    }

    /**
     * Generates the link to be added to a column header for sorting.
     * @param headerCell header cell the link should be added to
     * @return Href for sorting
     */
    private Href getSortingHref(HeaderCell headerCell)
    {
        // costruct Href from base href, preserving parameters
        Href href = new Href(this.baseHref);

        // add column number as link parameter
        href.addParameter(encodeParameter(TableTagParameters.PARAMETER_SORT), headerCell.getColumnNumber());

        boolean nowOrderAscending = !(headerCell.isAlreadySorted() && this.tableModel.isSortOrderAscending());

        int sortOrderParam = nowOrderAscending ? SortOrderEnum.ASCENDING.getCode() : SortOrderEnum.DESCENDING.getCode();
        href.addParameter(encodeParameter(TableTagParameters.PARAMETER_ORDER), sortOrderParam);

        // If user want to sort the full table I need to reset the page number.
        if (this.tableModel.isSortFullTable())
        {
            href.addParameter(encodeParameter(TableTagParameters.PARAMETER_PAGE), 1);
        }

        return href;
    }

    /**
     * This takes a column value and grouping index as the argument. It then groups the column and returns the
     * appropriate string back to the caller.
     * @param value String
     * @param group int
     * @return String
     */
    private String groupColumns(String value, int group)
    {

        if ((group == 1) && this.nextRow.size() > 0)
        {
            // we are at the begining of the next row so copy the contents from nextRow to the previousRow.
            this.previousRow.clear();
            this.previousRow.putAll(this.nextRow);
            this.nextRow.clear();
        }

        if (!this.nextRow.containsKey(new Integer(group)))
        {
            // Key not found in the nextRow so adding this key now...
            // remember all the old values.
            this.nextRow.put(new Integer(group), value);
        }

        // Start comparing the value we received, along with the grouping index.
        // if no matching value is found in the previous row then return the value.
        // if a matching value is found then this value should not get printed out
        // so return an empty String
        if (this.previousRow.containsKey(new Integer(group)))
        {
            for (int j = 1; j <= group; j++)
            {

                if (!((String) this.previousRow.get(new Integer(j))).equals((this.nextRow.get(new Integer(j)))))
                {
                    // no match found so return this value back to the caller.
                    return value;
                }
            }
        }

        // This is used, for when there is no data in the previous row,
        // It gets used only the first time.
        if (this.previousRow.size() == 0)
        {
            return value;
        }

        // There is corresponding value in the previous row
        // this value doesn't need to be printed, return an empty String
        return TagConstants.EMPTY_STRING;
    }

    /**
     * Writes the table body content.
     * @throws ObjectLookupException for errors in looking up properties in objects
     * @throws DecoratorException for errors returned by decorators
     */
    private void writeTableBody() throws ObjectLookupException, DecoratorException
    {
        JspWriter out = this.pageContext.getOut();

        // Ok, start bouncing through our list (only the visible part)
        RowIterator rowIterator = this.tableModel.getRowIterator(false);

        // iterator on rows
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            if (log.isDebugEnabled())
            {
                log.debug("[" + getUid() + "] rowIterator.next()=" + row);
            }
            if (this.tableModel.getTableDecorator() != null)
            {
                String stringStartRow = this.tableModel.getTableDecorator().startRow();
                if (stringStartRow != null)
                {
                    write(stringStartRow, out);
                }
            }

            // open tr
            write(row.getOpenTag(), out);

            // iterator on columns
            if (log.isDebugEnabled())
            {
                log.debug("[" + getUid() + "] creating ColumnIterator on " + this.tableModel.getHeaderCellList());
            }
            ColumnIterator columnIterator = row.getColumnIterator(this.tableModel.getHeaderCellList());

            while (columnIterator.hasNext())
            {
                Column column = columnIterator.nextColumn();

                // Get the value to be displayed for the column
                write(column.getOpenTag(), out);
                String value = column.getChoppedAndLinkedValue();

                // check if column is grouped
                if (column.getGroup() != -1)
                {
                    value = this.groupColumns(value, column.getGroup());
                }

                // add column value
                write(value, out);
                write(column.getCloseTag(), out);
            }

            // no columns?
            if (this.tableModel.isEmpty())
            {
                if (log.isDebugEnabled())
                {
                    log.debug("[" + getUid() + "] table has no columns");
                }
                write(TagConstants.TAG_TD_OPEN, out);
                write(row.getObject().toString(), out);
                write(TagConstants.TAG_TD_CLOSE, out);
            }

            // close tr
            write(row.getCloseTag(), out);

            if (this.tableModel.getTableDecorator() != null)
            {
                String endRow = this.tableModel.getTableDecorator().finishRow();
                if (endRow != null)
                {
                    write(endRow, out);
                }
            }
        }

        if (this.tableModel.getRowListPage().size() == 0)
        {
            write(MessageFormat.format(properties.getEmptyListRowMessage(), new Object[]{new Integer(this.tableModel
                .getNumberOfColumns())}), out);
        }
    }

    /**
     * Generates table footer with links for export commands.
     */
    private void writeTableFooter()
    {
        // Put the page stuff there if it needs to be there...
        if (this.properties.getAddPagingBannerBottom())
        {
            writeSearchResultAndNavigation();
        }

        // add export links (only if the table is not empty)
        if (this.export && this.tableModel.getRowListPage().size() != 0)
        {
            writeExportLinks();
        }
    }

    /**
     * generates the search result and navigation bar.
     */
    private void writeSearchResultAndNavigation()
    {
        if (this.pagesize != 0 && this.listHelper != null)
        {
            // create a new href
            Href navigationHref = new Href(this.baseHref);

            write(this.listHelper.getSearchResultsSummary());
            write(this.listHelper.getPageNavigationBar(
                navigationHref,
                encodeParameter(TableTagParameters.PARAMETER_PAGE)));
        }
    }

    /**
     * Writes the formatted export links section.
     */
    private void writeExportLinks()
    {
        // Figure out what formats they want to export, make up a little string
        Href exportHref = new Href(this.baseHref);

        StringBuffer buffer = new StringBuffer(200);
        Iterator iterator = MediaTypeEnum.iterator();

        while (iterator.hasNext())
        {
            MediaTypeEnum currentExportType = (MediaTypeEnum) iterator.next();

            if (this.properties.getAddExport(currentExportType))
            {

                if (buffer.length() > 0)
                {
                    buffer.append(this.properties.getExportBannerSeparator());
                }

                exportHref.addParameter(encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE), currentExportType
                    .getCode());

                // export marker
                exportHref.addParameter(TableTagParameters.PARAMETER_EXPORTING, "1");

                Anchor anchor = new Anchor(exportHref, this.properties.getExportLabel(currentExportType));
                buffer.append(anchor.toString());
            }
        }

        String[] exportOptions = {buffer.toString()};
        write(MessageFormat.format(this.properties.getExportBanner(), exportOptions));
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
    public void release()
    {
        super.release();

        // tag attributes
        this.decoratorName = null;
        this.defaultSortedColumn = -1;
        this.defaultSortOrder = null;
        this.export = false;
        this.length = 0;
        this.listAttribute = null;
        this.name = null;
        this.offset = 0;
        this.pagesize = 0;
        this.property = null;
        this.requestUri = null;
        this.dontAppendContext = false;
        this.scope = null;
        this.sortFullTable = null;
        this.excludedParams = null;
        this.filteredRows = null;
        this.uid = null;
    }

    /**
     * Returns the name.
     * @return String
     */
    public String getName()
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