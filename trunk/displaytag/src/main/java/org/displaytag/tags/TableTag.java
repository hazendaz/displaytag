package org.displaytag.tags;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.DecoratorFactory;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.InvalidTagAttributeValueException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.export.BaseExportView;
import org.displaytag.export.MediaTypeEnum;
import org.displaytag.export.ExportViewFactory;
import org.displaytag.model.Cell;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;
import org.displaytag.pagination.SmartListHelper;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.Anchor;
import org.displaytag.util.CollectionUtil;
import org.displaytag.util.Href;
import org.displaytag.util.RequestHelper;
import org.displaytag.util.TagConstants;

/**
 * This tag takes a list of objects and creates a table to display those objects. With the help of column tags, you
 * simply provide the name of properties (get Methods) that are called against the objects in your list that gets
 * displayed. This tag works very much like the struts iterator tag, most of the attributes have the same name and
 * functionality as the struts tag.
 * @author mraible
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
     * logger.
     */
    private static Log log = LogFactory.getLog(TableTag.class);

    /**
     * Has the commons-lang dependency been checked?
     */
    private static boolean commonsLangChecked;

    /**
     * Iterator on collection.
     */
    private Iterator tableIterator;

    /**
     * actual row number, updated during iteration.
     */
    private int rowNumber = 1;

    /**
     * Used when user directly set a collection using the "list" attribute.
     * @deprecated place object in the page/request scope and use the "name" attribute
     */
    private Object list;

    /**
     * name of the object to use for iteration. Can contain expression
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
     * length of list to display - reset in doEndTag().
     */
    private int length;

    /**
     * table decorator class name - cleaned in doEndTag().
     */
    private String decoratorName;

    /**
     * page size - reset in doEndTag().
     */
    private int pagesize;

    /**
     * add export links - reset in doEndTag().
     */
    private boolean export;

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
     * list offset - reset in doEndTag().
     */
    private int offset;

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
     * sort the full list?
     */
    private boolean sortFullTable;

    /**
     * Request uri.
     */
    private String requestUri;

    /**
     * daAfterBody() has been executed at least once?
     */
    private boolean doAfterBodyExecuted;

    /**
     * the String used to encode parameter. Initialized at the first use of encodeParameter().
     */
    private String tableParameterIdentifier;

    /**
     * the index of the column sorted by default.
     */
    private int defaultSortedColumn = -1;

    /**
     * static footer added using the footer tag.
     */
    private String footer;

    /**
     * Sets the content of the footer.
     * @param string footer content
     */
    public void setFooter(String string)
    {
        this.footer = string;
    }

    /**
     * Is the current row empty? Should the columns display?
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
            this.sortFullTable = false;
        }
        else if (TableTagParameters.SORT_AMOUNT_LIST.equals(value))
        {
            this.sortFullTable = true;
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
     * @param value name of the object to use for iteration. Can contain expression
     */
    public void setName(String value)
    {
        this.name = value;
    }

    /**
     * sets the property to get into the bean defined by "name".
     * @param value property name
     * @deprecated Use expressions in "name" attribute
     */
    public void setProperty(String value)
    {
        this.property = value;
    }

    /**
     * sets the number of items to be displayed in the page.
     * @param value String
     * @throws InvalidTagAttributeValueException if value is not a valid integer
     */
    public void setLength(String value) throws InvalidTagAttributeValueException
    {

        try
        {
            this.length = Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            throw new InvalidTagAttributeValueException(getClass(), "length", value);
        }

    }

    /**
     * sets the index of the default sorted column.
     * @param value index of the column to sort
     * @throws InvalidTagAttributeValueException if value is not a valid integer
     */
    public void setDefaultsort(String value) throws InvalidTagAttributeValueException
    {

        try
        {
            // parse and subtract one (internal index is 0 based)
            this.defaultSortedColumn = Integer.parseInt(value) - 1;
        }
        catch (NumberFormatException e)
        {
            throw new InvalidTagAttributeValueException(getClass(), "defaultsort", value);
        }

    }

    /**
     * sets the number of items that should be displayed for a single page.
     * @param value String
     * @throws InvalidTagAttributeValueException if value is not a valid integer
     */
    public void setPagesize(String value) throws InvalidTagAttributeValueException
    {

        try
        {
            this.pagesize = Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            throw new InvalidTagAttributeValueException(getClass(), "pagesize", value);
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
     * @param booleanValue <code>true</code> if export should be enabled
     */
    public void setExport(String booleanValue)
    {
        if (!Boolean.FALSE.toString().equals(booleanValue))
        {
            this.export = true;
        }
    }

    /**
     * Setter for the list offset attribute.
     * @param value String
     * @throws InvalidTagAttributeValueException if value is not a valid positive integer
     */
    public void setOffset(String value) throws InvalidTagAttributeValueException
    {
        try
        {
            int userOffset = Integer.parseInt(value);

            if (userOffset < 1)
            {
                throw new InvalidTagAttributeValueException(getClass(), "offset", value);
            }

            // this.offset is 0 based, subtract 1
            this.offset = (userOffset - 1);
        }
        catch (NumberFormatException e)
        {
            throw new InvalidTagAttributeValueException(getClass(), "offset", value);
        }

    }

    /**
     * Set the base href used in creating link.
     * @param value Href
     */
    protected void setBaseHref(Href value)
    {
        this.baseHref = value;
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
            log.debug(
                "[" + getId() + "] first iteration=" + (this.rowNumber == 1) + " (row number=" + this.rowNumber + ")");
        }
        // in first iteration this.rowNumber is 1
        // (this.rowNumber is incremented in doAfterBody)
        return this.rowNumber == 1;
    }

    /**
     * Displaytag requires commons-lang 2.x or better; it is not compatible with earlier versions.
     * @throws JspTagException if the wrong library, or no library at all, is found.
     */
    public static void checkCommonsLang() throws JspTagException
    {
        if (commonsLangChecked)
        {
            return;
        }
        try
        { // Do they have commons lang ?
            Class stringUtils = Class.forName("org.apache.commons.lang.StringUtils");
            try
            {
                // this method is new in commons-lang 2.0
                stringUtils.getMethod("capitalize", new Class[] { String.class });
            }
            catch (NoSuchMethodException ee)
            {
                throw new JspTagException(
                    "\n\nYou appear to have an INCOMPATIBLE VERSION of the Commons Lang library.  \n"
                        + "Displaytag requires version 2 of this library, and you appear to have a prior version in \n"
                        + "your classpath.  You must remove this prior version AND ensure that ONLY version 2 is in \n"
                        + "your classpath.\n "
                        + "If commons-lang-1.x is in your classpath, be sure to remove it. \n"
                        + "Be sure to delete all cached or temporary jar files from your application server; Tomcat \n"
                        + "users should be sure to also check the CATALINA_HOME/shared folder; you may need to \n"
                        + "restart the server. \n"
                        + "commons-lang-2.jar is available in the displaytag distribution, or from the Jakarta \n"
                        + "website at http://jakarta.apache.org/commons \n\n.");
            }
        }
        catch (ClassNotFoundException e)
        {
            throw new JspTagException(
                "You do not appear to have the Commons Lang library, version 2.  "
                    + "commons-lang-2.jar is available in the displaytag distribution, or from the Jakarta website at "
                    + "http://jakarta.apache.org/commons .  ");
        }
        commonsLangChecked = true;
    }

    /**
     * When the tag starts, we just initialize some of our variables, and do a little bit of error checking to make
     * sure that the user is not trying to give us parameters that we don't expect.
     * @return int
     * @throws JspException generic exception
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException
    {
        checkCommonsLang();
        if (log.isDebugEnabled())
        {
            log.debug("[" + getId() + "] doStartTag called");
        }

        this.properties = new TableProperties();
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
     */
    private void initParameters() throws ObjectLookupException
    {

        initHref();

        RequestHelper requestHelper = new RequestHelper((HttpServletRequest) this.pageContext.getRequest());

        Integer pageNumberParameter = requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_PAGE));
        this.pageNumber = (pageNumberParameter == null) ? 1 : pageNumberParameter.intValue();

        Integer sortColumnParameter = requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_SORT));
        int sortColumn = (sortColumnParameter == null) ? this.defaultSortedColumn : sortColumnParameter.intValue();
        this.tableModel.setSortedColumnNumber(sortColumn);

        this.tableModel.setSortFullTable(this.sortFullTable);

        Integer paramOrder = requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_ORDER));
        boolean order = !(new Integer(TableTagParameters.VALUE_SORT_DESCENDING).equals(paramOrder));
        this.tableModel.setSortOrderAscending(order);

        // if the behaviour is sort full page we need to go back to page one if sort of order is changed
        if (this.sortFullTable && (sortColumn != -1))
        {

            // save actual sort to href
            this.baseHref.addParameter(encodeParameter(TableTagParameters.PARAMETER_PREVIOUSSORT), sortColumn);
            this.baseHref.addParameter(encodeParameter(TableTagParameters.PARAMETER_PREVIOUSORDER), paramOrder);

            // read previous sort from request
            Integer previousSortColumnParameter =
                requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_SORT));
            this.previousSortedColumn =
                (previousSortColumnParameter == null) ? -1 : previousSortColumnParameter.intValue();

            Integer previousParamOrder =
                requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_PREVIOUSORDER));
            this.previousOrder = !(new Integer(TableTagParameters.VALUE_SORT_DESCENDING).equals(previousParamOrder));

        }

        Integer exportTypeParameter =
            requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE));
        this.currentMediaType = MediaTypeEnum.fromIntegerCode(exportTypeParameter);
        if (this.currentMediaType == null)
        {
            this.currentMediaType = MediaTypeEnum.HTML;
        }

        // if list is null check
        if (this.list == null)
        {
            // create a complete string for compatibility with previous version before expression evaluation.
            // this approach is optimized for new expressions, not for previous property/scope parameters
            StringBuffer fullName = new StringBuffer();

            // append scope
            if (this.scope != null && !"".equals(this.scope))
            {
                fullName.append(this.scope).append("Scope.");
            }

            // base bean name
            if (this.name != null)
            {
                fullName.append(this.name);
            }

            // append property
            if (this.property != null && !"".equals(this.property))
            {
                fullName.append('.').append(this.property);
            }

            this.list = evaluateExpression(fullName.toString());

        }

        this.tableIterator = IteratorUtils.getIterator(this.list);

    }

    /**
     * init the href object used to generate all the links for pagination, sorting, exporting.
     */
    protected void initHref()
    {

        RequestHelper requestHelper = new RequestHelper((HttpServletRequest) this.pageContext.getRequest());

        // get the href for this request
        Href normalHref = requestHelper.getHref();

        if (this.requestUri != null)
        {
            // if user has added a requestURI create a new href
            this.baseHref = new Href(this.requestUri);

            // ... ancd copy parameters from the curret request
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
                log.debug(
                    "[" + getId() + "] tag body is empty. Iterates to preserve compatibility with previous version");
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

        // clean up
        this.listHelper = null;
        this.export = false;
        this.currentMediaType = null;
        this.scope = null;
        this.property = null;
        this.decoratorName = null;
        this.pagesize = 0;
        this.length = 0;
        this.offset = 0;
        this.defaultSortedColumn = -1;
        this.rowNumber = 1;
        this.list = null;
        this.sortFullTable = false;
        this.doAfterBodyExecuted = false;
        this.currentRow = null;
        this.tableModel = null;
        this.requestUri = null;
        this.tableParameterIdentifier = null;

        // do not remove media attribute! if the table is nested in other tables this is still needed
        // this.pageContext.removeAttribute(PAGE_ATTRIBUTE_MEDIA);

        if (log.isDebugEnabled())
        {
            log.debug("[" + getId() + "] doEndTag - end");
        }

        return returnValue;
    }

    /**
     * If no columns are provided, automatically add them from bean properties. Get the first object in the list and
     * get all the properties (except the "class" property which is automatically skipped). Of course this isn't
     * possible for empty lists.
     * @since 1.0
     */
    private void describeEmptyTable()
    {
        this.tableIterator = IteratorUtils.getIterator(this.list);
        if (this.tableIterator.hasNext())
        {
            Object iteratedObject = this.tableIterator.next();
            Map objectProperties = new HashMap();
            try
            {
                objectProperties = BeanUtils.describe(iteratedObject);
            }
            catch (Exception e)
            {
                log.warn("Unable to automatically add columns: " + e.getMessage(), e);
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

        return writeExport(mimeType, exportString);
    }

    /**
     * Will write the export. The default behavior is to write directly to the response. If the ResponseOverrideFilter
     * is configured for this request, will instead write the export content to a StringBuffer in the Request object.
     * @param mimeType mime type to set in the response
     * @param exportString String
     * @return int
     * @throws JspException if errors writing to out
     */
    protected int writeExport(String mimeType, String exportString) throws JspException
    {
        ServletResponse response = this.pageContext.getResponse();
        JspWriter out = this.pageContext.getOut();

        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        StringBuffer bodyBuffer =
            (StringBuffer) request.getAttribute("org.displaytag.filter.ResponseOverrideFilter.CONTENT_OVERRIDE_BODY");
        if (bodyBuffer != null)
        {
            // We are running under the export filter
            StringBuffer contentTypeOverride =
                (StringBuffer) request.getAttribute(
                    "org.displaytag.filter.ResponseOverrideFilter.CONTENT_OVERRIDE_TYPE");
            contentTypeOverride.append(mimeType);
            bodyBuffer.append(exportString);
        }
        else
        {
            try
            {
                out.clear();
                response.setContentType(mimeType);
                out.write(exportString);
                out.flush();
            }
            catch (Exception ex)
            {
                log.error(ex.getMessage(), ex);
                throw new JspException(ex.getMessage());
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

        // thead
        if (this.properties.getShowHeader())
        {
            buffer.append(getTableHeader());
        }

        // open table body
        buffer.append(TagConstants.TAG_TBODY_OPEN);

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
                Object value;

                // Get the value to be displayed for the column
                buffer.append(column.getOpenTag());
                value = column.getChoppedAndLinkedValue();

                // Ok, let's write this column's cell...
                if (column.getGroup() != -1)
                {
                    buffer.append(groupColumns(value.toString(), column.getGroup()));
                }
                else
                {
                    buffer.append(value);
                }

                buffer.append(column.getCloseTag());
            }

            // no columns?
            if (this.tableModel.isEmpty())
            {
                if (log.isDebugEnabled())
                {
                    log.debug("[" + getId() + "] table has no columns");
                }
                buffer.append(TagConstants.TAG_TD_OPEN);
                buffer.append(row.getObject().toString());
                buffer.append(TagConstants.TAG_TD_CLOSE);
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
            //@todo fix this!
            buffer.append("\t\t<tr class=\"empty\">\n");
            buffer.append(
                "<td colspan=\""
                    + (this.tableModel.getNumberOfColumns() + 1)
                    + "\">"
                    + this.properties.getEmptyListMessage()
                    + "</td></tr>");
        }

        // close table body
        buffer.append(TagConstants.TAG_TBODY_CLOSE);

        if (this.footer != null)
        {
            buffer.append(TagConstants.TAG_TFOOTER_OPEN);
            buffer.append(this.footer);
            buffer.append(TagConstants.TAG_TFOOTER_CLOSE);
            // reset footer
            this.footer = null;
        }

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
                if (StringUtils.isNotBlank(cssSortable))
                {
                    headerCell.addHeaderClass(cssSortable);
                }
            }

            // if sorted add styles
            if (headerCell.isAlreadySorted())
            {
                String css = this.properties.getCssSorted();

                if (StringUtils.isNotBlank(css))
                {
                    // sorted css class
                    headerCell.addHeaderClass(css);
                }

                // sort order css class
                String cssOrder = this.properties.getCssOrder(this.tableModel.isSortOrderAscending());

                if (StringUtils.isNotBlank(cssOrder))
                {
                    headerCell.addHeaderClass(cssOrder);
                }
            }

            // append th with html attributes
            buffer.append(headerCell.getHeaderOpenTag());

            // title
            String header = headerCell.getTitle();

            // column is sortable, create link
            if (headerCell.getSortable())
            {

                // costruct Href from base href, preserving parameters
                Href href = new Href(this.baseHref);

                // add column number as link parameter
                href.addParameter(encodeParameter(TableTagParameters.PARAMETER_SORT), headerCell.getColumnNumber());

                boolean nowOrderAscending;
                if (headerCell.isAlreadySorted() && this.tableModel.isSortOrderAscending())
                {
                    href.addParameter(
                        encodeParameter(TableTagParameters.PARAMETER_ORDER),
                        TableTagParameters.VALUE_SORT_DESCENDING);
                    nowOrderAscending = false;
                }
                else
                {
                    href.addParameter(
                        encodeParameter(TableTagParameters.PARAMETER_ORDER),
                        TableTagParameters.VALUE_SORT_ASCENDING);
                    nowOrderAscending = true;
                }

                // only if user want to sort the full table
                // check if I need to reset the page number
                if (this.tableModel.isSortFullTable())
                {
                    // if sorting (column or order) is changed reset page
                    if (headerCell.getColumnNumber() != this.previousSortedColumn
                        || ((nowOrderAscending ^ this.previousOrder)))
                    {
                        href.addParameter(encodeParameter(TableTagParameters.PARAMETER_PAGE), 1);
                    }
                }

                // create link
                Anchor anchor = new Anchor(href, header);

                // append to buffer
                buffer.append(anchor.toString());
            }
            else
            {
                buffer.append(header);
            }

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

        // add export links
        if (this.export)
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

            // add page parameter with message format
            navigationHref.addParameter(encodeParameter(TableTagParameters.PARAMETER_PAGE), "{0,number,#}");

            StringBuffer buffer =
                new StringBuffer().append(this.listHelper.getSearchResultsSummary()).append(
                    this.listHelper.getPageNavigationBar(navigationHref.toString()));

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

                exportHref.addParameter(
                    encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE),
                    currentExportType.getCode());

                Anchor anchor = new Anchor(exportHref, this.properties.getExportLabel(currentExportType));
                buffer.append(anchor.toString());
            }
        }

        String[] exportOptions = { buffer.toString()};
        return MessageFormat.format(this.properties.getExportBanner(), exportOptions);
    }

    /**
     * This takes a cloumn value and grouping index as the argument. It then groups the column and returns the
     * appropriate string back to the caller.
     * @param value String
     * @param group int
     * @return String
     */
    private String groupColumns(String value, int group)
    {

        if ((group == 1) & this.nextRow.size() > 0)
        {
            // we are at the begining of the next row so copy the contents from
            // nextRow to the previousRow.
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
     * encode a parameter name to be unique in the page.
     * @param parameterName parameter name to encode
     * @return String encoded parameter name
     */
    protected String encodeParameter(String parameterName)
    {
        // code used to encode the parameter already creeated?
        if (this.tableParameterIdentifier == null)
        {
            // use name and id to get the unique identifier
            String stringIdentifier = "x-" + getId() + this.name;

            // get the array
            char[] charArray = stringIdentifier.toCharArray();

            // calculate a simple checksum-like value
            int checkSum = 0;

            for (int j = 0; j < charArray.length; j++)
            {
                checkSum += charArray[j] * j;
            }

            // this is the full identifier used for all the parameters
            this.tableParameterIdentifier = "d-" + checkSum + "-";

        }

        return this.tableParameterIdentifier + parameterName;
    }

}
