package org.displaytag.tags;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.DecoratorFactory;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.FactoryInstantiationException;
import org.displaytag.exception.InvalidTagAttributeValueException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.export.BaseExportView;
import org.displaytag.export.ExportViewFactory;
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
    public static final String PAGE_ATTRIBUTE_MEDIA = "mediaType";

    /**
     * If this buffer has been appended to at all, the contents of the buffer will be served as the sole output of the
     * request. Request variable.
     */
    public static final String FILTER_CONTENT_OVERRIDE_BODY = "org.displaytag.filter.ResponseOverrideFilter.CONTENT_OVERRIDE_BODY";

    /**
     * If the request content is overriden, you must also set the content type appropriately. Request variable.
     */
    public static final String FILTER_CONTENT_OVERRIDE_TYPE = "org.displaytag.filter.ResponseOverrideFilter.CONTENT_OVERRIDE_TYPE";

    /**
     * If the filename is specified, there will be a supplied filename. Request variable.
     */
    public static final String FILTER_CONTENT_OVERRIDE_FILENAME = "org.displaytag.filter.ResponseOverrideFilter.CONTENT_OVERRIDE_FILENAME";

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(TableTag.class);

    /**
     * RequestHelperFactory instance used for link generation.
     */
    private static RequestHelperFactory rhf;

    // -- start tag attributes --

    /**
     * Object (collection, list) on which the table is based. Set directly using the "list" attribute or evaluated from
     * expression.
     */
    protected Object list;

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
     * the index of the column sorted by default.
     */
    private int defaultSortedColumn = -1;

    /**
     * the sorting order for the sorted column.
     */
    private SortOrderEnum defaultSortOrder;

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
     * index of the previously sorted column.
     */
    private int previousSortedColumn;

    /**
     * previous sorting order.
     */
    private boolean previousOrder;

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
            throw new InvalidTagAttributeValueException(getClass(), "sort", value);
        }
    }

    /**
     * setter for the "requestURI" attribute.
     * @param value base URI for creating links
     */
    public void setRequestURI(String value)
    {
        this.requestUri = value;
    }

    /**
     * Used to directly set a list (or any object you can iterate on).
     * @param value Object
     * @deprecated use setName() to get the object from the page or request scope instead of setting it directly here
     */
    public void setList(Object value)
    {
        this.list = value;
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
            // ok, assuming thi is the name of the object
            this.name = (String) value;
        }
        else
        {
            // is this the list?
            this.list = value;
        }
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
            throw new InvalidTagAttributeValueException(getClass(), "defaultorder", value);
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
     * Called by interior column tags to help this tag figure out how it is supposed to display the information in the
     * List it is supposed to display.
     * @param column an internal tag describing a column in this tableview
     */
    public void addColumn(HeaderCell column)
    {
        if (log.isDebugEnabled())
        {
            log.debug("[" + getId() + "] addColumn " + column);
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
                + getId()
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

        if (log.isDebugEnabled())
        {
            log.debug("[" + getId() + "] doStartTag called");
        }

        this.properties = TableProperties.getInstance();
        this.tableModel = new TableModel(this.properties);

        // copying id to the table model for logging
        this.tableModel.setId(getId());

        initParameters();

        // set the PAGE_ATTRIBUTE_MEDIA attribute in the page scope
        if (this.currentMediaType != null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("[" + getId() + "] setting media [" + this.currentMediaType + "] in this.pageContext");
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
            log.debug("[" + getId() + "] doAfterBody called - iterating on row " + this.rowNumber);
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
            log.debug("[" + getId() + "] doIteration called");
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
            if (getId() != null)
            {
                if ((iteratedObject != null))
                {
                    // set object into this.pageContext
                    if (log.isDebugEnabled())
                    {
                        log.debug("[" + getId() + "] setting attribute \"" + getId() + "\" in pageContext");
                    }
                    this.pageContext.setAttribute(getId(), iteratedObject);

                }
                else
                {
                    // if row is null remove previous object
                    this.pageContext.removeAttribute(getId());
                }
                // set the current row number into this.pageContext
                this.pageContext.setAttribute(this.id + TableTagExtraInfo.ROWNUM_SUFFIX, new Integer(this.rowNumber));
            }

            // Row object for Cell values
            this.currentRow = new Row(iteratedObject, this.rowNumber);

            if (log.isDebugEnabled())
            {
                log.debug("[" + getId() + "] doIteration() returning EVAL_BODY_TAG");
            }

            // new iteration
            // using int to avoid deprecation error in compilation using j2ee 1.3
            return 2;
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("[" + getId() + "] doIteration() - iterator ended after " + (this.rowNumber - 1) + " rows");
            }

            // end iteration
            return SKIP_BODY;
        }
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

        SortOrderEnum paramOrder = SortOrderEnum.fromIntegerCode(requestHelper
            .getIntParameter(encodeParameter(TableTagParameters.PARAMETER_ORDER)));

        // if no order parameter is set use default
        if (paramOrder == null)
        {
            paramOrder = this.defaultSortOrder;
        }

        boolean order = SortOrderEnum.DESCENDING != paramOrder;
        this.tableModel.setSortOrderAscending(order);

        // if the behaviour is sort full page we need to go back to page one if sort of order is changed
        if (finalSortFull && (sortColumn != -1))
        {

            // save actual sort to href
            this.baseHref.addParameter(encodeParameter(TableTagParameters.PARAMETER_PREVIOUSSORT), sortColumn);
            this.baseHref.addParameter(encodeParameter(TableTagParameters.PARAMETER_PREVIOUSORDER), paramOrder);

            // read previous sort from request
            Integer previousSortColumnParameter = requestHelper
                .getIntParameter(encodeParameter(TableTagParameters.PARAMETER_SORT));
            this.previousSortedColumn = (previousSortColumnParameter == null) ? -1 : previousSortColumnParameter
                .intValue();

            SortOrderEnum previousParamOrder = SortOrderEnum.fromIntegerCode(requestHelper
                .getIntParameter(encodeParameter(TableTagParameters.PARAMETER_PREVIOUSORDER)));

            this.previousOrder = SortOrderEnum.DESCENDING != previousParamOrder;

        }

        Integer exportTypeParameter = requestHelper
            .getIntParameter(encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE));
        this.currentMediaType = MediaTypeEnum.fromIntegerCode(exportTypeParameter);
        if (this.currentMediaType == null)
        {
            this.currentMediaType = MediaTypeEnum.HTML;
        }

        String fullName = getFullObjectName();

        // only evaluate if needed, else preserve original list
        if (fullName != null)
        {
            this.list = evaluateExpression(fullName);
        }

        this.tableIterator = IteratorUtils.getIterator(this.list);
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

        StringBuffer fullName = new StringBuffer();

        // append scope
        if (StringUtils.isNotBlank(this.scope))
        {
            fullName.append(this.scope).append("Scope.");
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

        if (this.requestUri != null)
        {
            // if user has added a requestURI create a new href

            // call encodeURL to preserve session id when cookies are disabled
            String encodedURI = ((HttpServletResponse) this.pageContext.getResponse()).encodeURL(this.requestUri);
            this.baseHref = new Href(encodedURI);

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
            log.debug("[" + getId() + "] doEndTag called");
        }

        if (!this.doAfterBodyExecuted)
        {
            if (log.isDebugEnabled())
            {
                log.debug("["
                    + getId()
                    + "] tag body is empty. Iterates to preserve compatibility with previous version");
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

        // new in 1.0-b2
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

        List pageData = getViewableData();

        this.tableModel.setRowListPage(pageData);

        // Figure out how we should sort this data, typically we just sort
        // the data being shown, but the programmer can override this behavior

        if (!this.tableModel.isSortFullTable())
        {
            this.tableModel.sortPageList();
        }

        // Figure out where this data is going, if this is an export, then
        // we don't add the header and footer information
        StringBuffer buffer = new StringBuffer(8000);

        // Get the data back in the representation that the user is after, do they
        // want HTML/XML/CSV/EXCEL/etc...
        // When writing the data, if it it's a normal HTML display, then go ahead
        // and write the data within the context of the web page, for any of the
        // other export types, we need to clear our buffer before we can write
        // out the data
        int returnValue = EVAL_PAGE;

        if (MediaTypeEnum.HTML.equals(this.currentMediaType))
        {
            buffer.append(getHTMLData());
            write(buffer);
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("[" + getId() + "] doEndTag - exporting");
            }

            returnValue = doExport();
        }


        // do not remove media attribute! if the table is nested in other tables this is still needed
        // this.pageContext.removeAttribute(PAGE_ATTRIBUTE_MEDIA);

        if (log.isDebugEnabled())
        {
            log.debug("[" + getId() + "] doEndTag - end");
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
        this.previousOrder = false;
        this.previousRow = null;
        this.previousSortedColumn = 0;
        this.properties = null;
        this.rowNumber = 1;
        this.tableIterator = null;
        this.tableModel = null;
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
                if (!"class".equals(propertyName))
                {
                    // creates a new header and add to the table model
                    HeaderCell headerCell = new HeaderCell();
                    headerCell.setBeanPropertyName(propertyName);
                    this.tableModel.addColumnHeader(headerCell);
                }
            }
        }
    }

    /**
     * called when data are not displayed in a html page but should be exported.
     * @return int EVAL_PAGE or SKIP_PAGE
     * @throws JspException generic exception
     */
    protected int doExport() throws JspException
    {

        BaseExportView exportView = null;
        boolean exportFullList = this.properties.getExportFullList();

        if (log.isDebugEnabled())
        {
            log.debug("[" + getId() + "] currentMediaType=" + this.currentMediaType);
        }

        boolean exportHeader = this.properties.getExportHeader(this.currentMediaType);

        exportView = ExportViewFactory.getView(this.currentMediaType, this.tableModel, exportFullList, exportHeader);

        String mimeType = exportView.getMimeType();
        String exportString = exportView.doExport();

        String filename = properties.getExportFileName(this.currentMediaType);
        return writeExport(mimeType, exportString, filename);
    }

    /**
     * Will write the export. The default behavior is to write directly to the response. If the ResponseOverrideFilter
     * is configured for this request, will instead write the export content to a StringBuffer in the Request object.
     * @param mimeType mime type to set in the response
     * @param exportString String
     * @param filename name of the file to be saved. Can be null, if set the content-disposition header will be added.
     * @return int
     * @throws JspException for errors in resetting the response or in writing to out
     */
    protected int writeExport(String mimeType, String exportString, String filename) throws JspException
    {
        HttpServletResponse response = (HttpServletResponse) this.pageContext.getResponse();
        JspWriter out = this.pageContext.getOut();

        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        StringBuffer bodyBuffer = (StringBuffer) request.getAttribute(FILTER_CONTENT_OVERRIDE_BODY);
        if (bodyBuffer != null)
        {
            // We are running under the export filter
            StringBuffer contentTypeOverride = (StringBuffer) request.getAttribute(FILTER_CONTENT_OVERRIDE_TYPE);
            contentTypeOverride.append(mimeType);
            bodyBuffer.append(exportString);

            if (StringUtils.isNotEmpty(filename))
            {
                StringBuffer filenameOverride = (StringBuffer) request.getAttribute(FILTER_CONTENT_OVERRIDE_FILENAME);
                filenameOverride.append(filename);
            }

        }
        else
        {
            try
            {
                // this will also reset headers, needed when the server is sending a "no-cache" header
                this.pageContext.getResponse().reset();
                out.clear();
            }
            catch (Exception e)
            {
                throw new JspException("Unable to reset response before returning exported data. "
                    + "You are not using an export filter. "
                    + "Be sure that no other jsp tags are used before display:table or refer to the displaytag "
                    + "documentation on how to configure the export filter (requires j2ee 1.3).");
            }

            response.setContentType(mimeType);

            if (StringUtils.isNotEmpty(filename))
            {
                response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            }

            try
            {
                out.write(exportString);
                out.flush();
            }
            catch (IOException e)
            {
                throw new JspException("IOException while writing data.");
            }
        }

        return SKIP_PAGE;
    }

    /**
     * This returns a list of all of the data that will be displayed on the page via the table tag. This might include
     * just a subset of the total data in the list due to to paging being active, or the user asking us to just show a
     * subset, etc...
     * <p>
     * The list that is returned from here is not the original list, but it does contain references to the same objects
     * in the original list, so that means that we can sort and reorder the list, but we can't mess with the data
     * objects in the list.
     * </p>
     * @return List
     */
    public List getViewableData()
    {

        List fullList = new ArrayList();

        // If the user has changed the way our default behavior works, then we
        // need to look for it now, and resort things if needed before we ask
        // for the viewable part. (this is a bad place for this, this should be
        // refactored and moved somewhere else).

        if (this.tableModel.isSortFullTable())
        {
            // Sort the total list...
            this.tableModel.sortFullList();
        }

        Object originalData = this.tableModel.getRowListFull();

        // If they have asked for a subset of the list via the length
        // attribute, then only fetch those items out of the master list.

        fullList = CollectionUtil.getListFromObject(originalData, this.offset, this.length);

        // If they have asked for just a page of the data, then use the
        // SmartListHelper to figure out what page they are after, etc...

        if (this.pagesize > 0)
        {

            this.listHelper = new SmartListHelper(fullList, this.pagesize, this.properties);

            this.listHelper.setCurrentPage(this.pageNumber);

            fullList = this.listHelper.getListForCurrentPage();

        }

        return fullList;
    }

    /**
     * called when data have to be displayed in a html page.
     * @return String
     * @throws JspException generic exception
     */
    private String getHTMLData() throws JspException
    {
        if (log.isDebugEnabled())
        {
            log.debug("[" + getId() + "] getHTMLData called for table [" + getId() + "]");
        }

        boolean noItems = this.tableModel.getRowListPage().size() == 0;

        if (noItems && !this.properties.getEmptyListShowTable())
        {
            return this.properties.getEmptyListMessage();
        }

        StringBuffer buffer = new StringBuffer(8000);

        // variables to hold the previous row columns values.
        this.previousRow = new Hashtable(10);

        // variables to hold next row column values.
        this.nextRow = new Hashtable(10);

        //  Put the page stuff there if it needs to be there...
        if (this.properties.getAddPagingBannerTop())
        {
            // search result and navigation bar
            buffer.append(getSearchResultAndNavigation());
        }

        String css = this.properties.getCssTable();
        if (StringUtils.isNotBlank(css))
        {
            this.addClass(css);
        }

        // open table
        buffer.append(getOpenTag());

        // caption
        if (this.caption != null)
        {
            buffer.append(this.caption);
        }

        // thead
        if (this.properties.getShowHeader())
        {
            buffer.append(getTableHeader());
        }

        if (this.footer != null)
        {
            buffer.append(TagConstants.TAG_TFOOTER_OPEN);
            buffer.append(this.footer);
            buffer.append(TagConstants.TAG_TFOOTER_CLOSE);
            // reset footer
            this.footer = null;
        }

        // open table body
        buffer.append(TagConstants.TAG_TBODY_OPEN);

        // write table body
        buffer.append(getTableBody());

        // close table body
        buffer.append(TagConstants.TAG_TBODY_CLOSE);

        // close table
        buffer.append(getCloseTag());

        buffer.append(this.getTableFooter());

        if (this.tableModel.getTableDecorator() != null)
        {
            this.tableModel.getTableDecorator().finish();
        }

        if (log.isDebugEnabled())
        {
            log.debug("[" + getId() + "] getHTMLData end");
        }
        return buffer.toString();

    }

    /**
     * Generates the table header, including the first row of the table which displays the titles of the columns.
     * @return String
     */
    private String getTableHeader()
    {
        if (log.isDebugEnabled())
        {
            log.debug("[" + getId() + "] getTableHeader called");
        }
        StringBuffer buffer = new StringBuffer();

        // open thead
        buffer.append(TagConstants.TAG_THEAD_OPEN);

        // open tr
        buffer.append(TagConstants.TAG_TR_OPEN);

        // no columns?
        if (this.tableModel.isEmpty())
        {
            buffer.append(TagConstants.TAG_TH_OPEN);
            buffer.append(TagConstants.TAG_TH_CLOSE);
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
            buffer.append(headerCell.getHeaderOpenTag());

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

            buffer.append(header);
            buffer.append(headerCell.getHeaderCloseTag());
        }

        // close tr
        buffer.append(TagConstants.TAG_TR_CLOSE);

        // close thead
        buffer.append(TagConstants.TAG_THEAD_CLOSE);

        if (log.isDebugEnabled())
        {
            log.debug("[" + getId() + "] getTableHeader end");
        }
        return buffer.toString();
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
        //@todo optimize this using SortOrderEnum
        if (nowOrderAscending)
        {
            href.addParameter(encodeParameter(TableTagParameters.PARAMETER_ORDER), SortOrderEnum.ASCENDING.getCode());
        }
        else
        {
            href.addParameter(encodeParameter(TableTagParameters.PARAMETER_ORDER), SortOrderEnum.DESCENDING.getCode());
        }

        // only if user want to sort the full table. Check if I need to reset the page number
        if (this.tableModel.isSortFullTable())
        {
            // if sorting (column or order) is changed reset page
            if (headerCell.getColumnNumber() != this.previousSortedColumn || ((nowOrderAscending ^ this.previousOrder)))
            {
                href.addParameter(encodeParameter(TableTagParameters.PARAMETER_PAGE), 1);
            }
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

        if ((group == 1) & this.nextRow.size() > 0)
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
            this.nextRow.put(new Integer(group), new String(value));
        }

        //  Start comparing the value we received, along with the grouping index.
        //  if no matching value is found in the previous row then return the value.
        //  if a matching value is found then this value should not get printed out
        //  so return ""
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
        // this value doesn't need to be printed, return ""
        return "";
    }

    /**
     * Writes the table body content.
     * @return table body content
     * @throws ObjectLookupException for errors in looking up properties in objects
     * @throws DecoratorException for errors returned by decorators
     */
    private String getTableBody() throws ObjectLookupException, DecoratorException
    {
        StringBuffer buffer = new StringBuffer();

        // Ok, start bouncing through our list...
        RowIterator rowIterator = this.tableModel.getRowIterator();

        // iterator on rows
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            if (log.isDebugEnabled())
            {
                log.debug("[" + getId() + "] rowIterator.next()=" + row);
            }
            if (this.tableModel.getTableDecorator() != null)
            {
                String stringStartRow = this.tableModel.getTableDecorator().startRow();
                if (stringStartRow != null)
                {
                    buffer.append(stringStartRow);
                }
            }

            // open tr
            buffer.append(row.getOpenTag());

            // iterator on columns
            if (log.isDebugEnabled())
            {
                log.debug("[" + getId() + "] creating ColumnIterator on " + this.tableModel.getHeaderCellList());
            }
            ColumnIterator columnIterator = row.getColumnIterator(this.tableModel.getHeaderCellList());

            while (columnIterator.hasNext())
            {
                Column column = columnIterator.nextColumn();

                // Get the value to be displayed for the column
                buffer.append(column.getOpenTag());
                Object value = column.getChoppedAndLinkedValue();

                // check if column is grouped
                if (column.getGroup() != -1)
                {
                    value = this.groupColumns(value.toString(), column.getGroup());
                }

                // add column value
                buffer.append(value);
                buffer.append(column.getCloseTag());
            }

            // no columns?
            if (this.tableModel.isEmpty())
            {
                if (log.isDebugEnabled())
                {
                    log.debug("[" + getId() + "] table has no columns");
                }
                buffer.append(TagConstants.TAG_TD_OPEN).append(row.getObject().toString()).append(
                    TagConstants.TAG_TD_CLOSE);
            }

            // close tr
            buffer.append(row.getCloseTag());

            if (this.tableModel.getTableDecorator() != null)
            {
                String endRow = this.tableModel.getTableDecorator().finishRow();
                if (endRow != null)
                {
                    buffer.append(endRow);
                }
            }
        }

        if (this.tableModel.getRowListPage().size() == 0)
        {
            buffer.append(MessageFormat.format(properties.getEmptyListRowMessage(), new Object[]{new Integer(
                this.tableModel.getNumberOfColumns())}));
        }

        return buffer.toString();
    }

    /**
     * Generates table footer with links for export commands.
     * @return String
     */
    private String getTableFooter()
    {
        StringBuffer buffer = new StringBuffer(1000);

        // Put the page stuff there if it needs to be there...
        if (this.properties.getAddPagingBannerBottom())
        {
            buffer.append(getSearchResultAndNavigation());
        }

        // add export links (only if the table is not empty)
        if (this.export && this.tableModel.getRowListPage().size() != 0)
        {
            buffer.append(getExportLinks());
        }

        return buffer.toString();
    }

    /**
     * generates the search result and navigation bar.
     * @return String
     */
    private String getSearchResultAndNavigation()
    {
        if (log.isDebugEnabled())
        {
            log.debug("[" + getId() + "] starting getSearchResultAndNavigation");
        }

        if (this.pagesize != 0 && this.listHelper != null)
        {
            // create a new href
            Href navigationHref = new Href(this.baseHref);

            StringBuffer buffer = new StringBuffer().append(this.listHelper.getSearchResultsSummary()).append(
                this.listHelper
                    .getPageNavigationBar(navigationHref, encodeParameter(TableTagParameters.PARAMETER_PAGE)));

            return buffer.toString();
        }
        return "";
    }

    /**
     * returns the formatted export links section.
     * @return String export links section
     */
    private String getExportLinks()
    {

        // Figure out what formats they want to export, make up a little string

        Href exportHref = new Href(this.baseHref);

        StringBuffer buffer = new StringBuffer();

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

                Anchor anchor = new Anchor(exportHref, this.properties.getExportLabel(currentExportType));
                buffer.append(anchor.toString());
            }
        }

        String[] exportOptions = {buffer.toString()};
        return MessageFormat.format(this.properties.getExportBanner(), exportOptions);
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
        this.list = null;
        this.name = null;
        this.offset = 0;
        this.pagesize = 0;
        this.property = null;
        this.requestUri = null;
        this.scope = null;
        this.sortFullTable = null;
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
            this.paramEncoder = new ParamEncoder(this.id);
        }

        return this.paramEncoder.encodeParameterName(parameterName);
    }

}