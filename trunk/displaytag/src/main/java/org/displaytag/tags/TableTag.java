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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.DecoratorFactory;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.InvalidTagAttributeValueException;
import org.displaytag.export.BaseExportView;
import org.displaytag.export.ExportTypeEnum;
import org.displaytag.export.ExportViewFactory;
import org.displaytag.filter.ResponseOverrideFilter;
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
 * This tag takes a list of objects and creates a table to display those
 * objects.  With the help of column tags, you simply provide the name of
 * properties (get Methods) that are called against the objects in your list
 * that gets displayed [[reword that...]]
 *
 * This tag works very much like the struts iterator tag, most of the attributes
 * have the same name and functionality as the struts tag.
 *
 * Simple Usage:<p>
 *
 *   <display:list name="list" >
 *     <display:column property="title" />
 *     <display:column property="code" />
 *     <display:column property="dean" />
 *   </display:list>
 *
 * More Complete Usage:<p>
 *
 *   <display:list name="list" pagesize="100">
 *     <display:column property="title" title="College Title" width="60%" sort="true"
 *              href="/osiris/pubs/college/edit.page" paramId="OID" paramProperty="OID" />
 *     <display:column property="code" sortable="true"/>
 *     <display:column property="primaryOfficer.name" title="Dean" />
 *     <display:column property="active" sortable="true" />
 *   </display:list>
 *
 * @author mraible
 * @version $Revision$ ($Author$)
 **/
public class TableTag extends HtmlTableTag
{

    /**
     * logger
     */
    private static Log log = LogFactory.getLog(TableTag.class);

    /**
     * name of the attribute added to page scope when exporting, containing an ExportTypeEnum
     * this can be used in column content to detect the output type and to return different data
     * when exporting
     */
    public static final String PAGE_ATTRIBUTE_EXPORT = "export";

    /**
     * Has the commons-lang dependency been checked?
     */
    private static boolean commonsLangChecked = false;

    /**
     * Iterator on collection
     */
    private Iterator tableIterator;

    /**
     * actual row number, updated during iteration
     */
    private int rowNumber = 1;

    /**
     * List. Used when user directly set a collection using the "list" attribute
     */
    private Object list = null;

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
     * Hashtable wich contains previous row values. Needed for grouping
     */

    private Hashtable previousRow = null;

    /**
     * table model - initialized in doStartTag()
     */
    private TableModel tableModel;

    /**
     * current row
     */
    private Row currentRow;

    /**
     * next row
     */
    private Hashtable nextRow = null;

    /**
     * length of list to display - reset in doEndTag()
     */
    private int length = 0;

    /**
     * table decorator class name - cleaned in doEndTag()
     */
    private String decoratorName;

    /**
     * page size - reset in doEndTag()
     */
    private int pagesize = 0;

    /**
     * add export links - reset in doEndTag()
     */
    private boolean export = false;

    /**
     * Used by various functions when the person wants to do paging -
     * cleaned in doEndTag()
     */
    private SmartListHelper listHelper = null;

    /**
     * base href used for links - set in initParameters()
     */
    private Href baseHref;

    /**
     * table properties - set in doStartTag()
     */
    private TableProperties properties;

    /**
     * page number - set in initParameters()
     */
    private int pageNumber = 1;

    /**
     * list offset - reset in doEndTag()
     */
    private int offset = 0;

    /**
     * export type - set in initParameters()
     */
    private ExportTypeEnum exportType;

    /**
     * index of the previously sorted column
     */
    private int previousSortedColumn;

    /**
     * previous sorting order
     */
    private boolean previousOrder;

    /**
     * sort the full list?
     */
    private boolean sortFullTable;

    /**
     * Request uri
     */
    private String requestUri;

    /**
     * daAfterBody() has been executed at least once?
     */
    private boolean doAfterBodyExecuted;

    /**
     * the String used to encode parameter.
     * Initialized at the first use of encodeParameter()
     */
    private String tableParameterIdentifier;

    /**
     * the index of the column sorted by default
     */
    private int defaultSortedColumn = -1;

    /**
     * static footer added using the footer tag
     */
    private String footer;

    /**
     * Sets the content of the footer
     * @param string footer content
     */
    public void setFooter(String string)
    {
        footer = string;
    }

    /**
     * Is the current row empty?  Should the columns display?
     * @return true if the current row is empty
     */
    protected boolean isEmpty()
    {
        return this.currentRow == null;
    }

    /**
     * setter for the "sort" attribute
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
     * setter for the "requestURI" attribute
     * @param value base URI for creating links
     */
    public void setRequestURI(String value)
    {
        this.requestUri = value;
    }

    /**
     * Method setList
     * @param value Object
     */
    public void setList(Object value)
    {
        this.list = value;
    }

    /**
     * Method setName
     * @param value String
     */
    public void setName(String value)
    {
        this.name = value;
    }

    /**
     * Method setProperty
     * @param value String
     */
    public void setProperty(String value)
    {
        this.property = value;
    }

    /**
     * Method setLength
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
     * set the index of the default sorted column
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
     * Method setPagesize
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
     * Method setScope
     * @param value String
     */
    public void setScope(String value)
    {
        this.scope = value;
    }

    /**
     * Method setDecorator
     * @param decorator String
     */
    public void setDecorator(String decorator)
    {
        this.decoratorName = decorator;
    }

    /**
     * Method setExport
     * @param booleanValue String
     */
    public void setExport(String booleanValue)
    {
        if (!Boolean.FALSE.toString().equals(booleanValue))
        {
            this.export = true;
        }
    }

    /**
     * Method setOffset
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
     * Set the base href used in creating link
     * @param value Href
     */
    protected void setBaseHref(Href value)
    {
        this.baseHref = value;
    }

    /**
     * It's a getter.
     * @return the PageContext
     */
    public PageContext getPageContext()
    {
        return pageContext;
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
     * Called by interior column tags to help this tag figure out how it is
     * supposed to display the information in the List it is supposed to
     * display
     *
     * @param column an internal tag describing a column in this tableview
     **/
    public void addColumn(HeaderCell column)
    {
        log.debug("addColumn " + column);
        this.tableModel.addColumnHeader(column);
    }

    /**
     * Method addCell
     * @param cell Cell
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
     * Method isFirstIteration
     * @return boolean
     */
    protected boolean isFirstIteration()
    {
        // in first iteration this.rowNumber is 1
        // (this.rowNumber is incremented in doAfterBody)
        return this.rowNumber == 1;
    }

    /**
     * Displaytag requires commons-lang 2.x or better; it is not compatible with earlier versions.
     * @throws javax.servlet.jsp.JspTagException if the wrong library, or no library at all, is found.
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
     * When the tag starts, we just initialize some of our variables, and do a
     * little bit of error checking to make sure that the user is not trying
     * to give us parameters that we don't expect.
     * @return int
     * @throws JspException generic exception
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     **/
    public int doStartTag() throws JspException
    {
        checkCommonsLang();
        if (log.isDebugEnabled())
        {
            log.debug("doStartTag()");
        }

        this.tableModel = new TableModel();

        this.properties = new TableProperties();

        initParameters();

        // set the PAGE_ATTRIBUTE_EXPORT attribute in the page scope
        if (this.exportType != null)
        {
            pageContext.setAttribute(PAGE_ATTRIBUTE_EXPORT, exportType);
        }

        doIteration();

        // always return EVAL_BODY_TAG to get column headers also if the table is empty
        // using int to avoid deprecation error in compilation using j2ee 1.3
        return 2;
    }

    /**
     * Method doAfterBody
     * @return int
     * @throws JspException generic exception
     * @see javax.servlet.jsp.tagext.BodyTag#doAfterBody()
     */
    public int doAfterBody() throws JspException
    {
        // doAfterBody() has been called, body is not empty
        this.doAfterBodyExecuted = true;

        if (log.isDebugEnabled())
        {
            log.debug("doAfterBody() - iterator with id = " + id + " on row number " + this.rowNumber);
        }

        // increment this.rowNumber
        this.rowNumber++;

        // Call doIteration() to do the common work
        return doIteration();
    }

    /**
     * <p>Utility method that is used by both doStartTag() and doAfterBody() to perform an iteration.</p>
     *
     * @return <code>int</code> either EVAL_BODY_TAG or SKIP_BODY depending on whether another iteration is desired.
     * @throws JspException generic exception
     */
    protected int doIteration() throws JspException
    {

        if (log.isDebugEnabled())
        {
            log.debug("doIteration()");
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
                    // set object into pageContext
                    if (log.isDebugEnabled())
                    {
                        log.debug("setting attribute \"" + getId() + "\" in pagecontext");
                    }
                    pageContext.setAttribute(getId(), iteratedObject);

                }
                else
                {
                    // if row is null remove previous object
                    pageContext.removeAttribute(getId());
                }
                // set the current row number into pageContext
                pageContext.setAttribute(id + TableTagExtraInfo.ROWNUM_SUFFIX, new Integer(this.rowNumber));
            }

            // Row object for Cell values
            this.currentRow = new Row(iteratedObject, this.rowNumber);

            if (log.isDebugEnabled())
            {
                log.debug("doIteration() returning EVAL_BODY_TAG");
            }

            // new iteration
            // using int to avoid deprecation error in compilation using j2ee 1.3
            return 2;
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("doIteration() - iterator [" + id + "] ended after " + this.rowNumber + " rows");
            }

            // end iteration
            return SKIP_BODY;
        }
    }

    /**
     * Method initParameters
     * @throws JspException generic exception
     */
    private void initParameters() throws JspException
    {

        initHref();

        RequestHelper requestHelper = new RequestHelper((HttpServletRequest) pageContext.getRequest());

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
        this.exportType = ExportTypeEnum.fromIntegerCode(exportTypeParameter);

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
     * Method initHref
     * @throws JspException generic exception
     */
    protected void initHref() throws JspException
    {

        RequestHelper requestHelper = new RequestHelper((HttpServletRequest) pageContext.getRequest());

        // get the href for this request
        Href normalHref = requestHelper.getHref();

        if (this.requestUri != null)
        {
            // if user has added a requestURI create a new href
            this.baseHref = new Href(this.requestUri);

            // ... ancd copy parameters from the curret request
            HashMap parameterMap = normalHref.getParameterMap();
            this.baseHref.addParameterMap(parameterMap);
        }
        else
        {
            // simply copy href
            this.baseHref = normalHref;
        }

    }

    /**
     * Draw the table.  This is where everything happens, we figure out what
     * values we are supposed to be showing, we figure out how we are supposed
     * to be showing them, then we draw them.
     * @return int
     * @throws JspException generic exception
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException
    {

        if (log.isDebugEnabled())
        {
            log.debug("doEndTag");
        }

        if (!this.doAfterBodyExecuted)
        {
            log.debug(
                "tag body is empty. Manually iterates on collection to preserve compatibility with previous version");

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
            tableDecorator.init(pageContext, this.list);
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

        if (this.exportType == null)
        {
            buffer.append(getHTMLData());
            write(buffer);
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("doEndTag - exporting");
            }

            returnValue = doExport();
        }

        // clean up
        this.listHelper = null;
        this.export = false;
        this.exportType = null;
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

        // remove export attribute
        pageContext.removeAttribute(PAGE_ATTRIBUTE_EXPORT);

        if (log.isDebugEnabled())
        {
            log.debug("doEndTag - end");
        }

        return returnValue;
    }

    /**
     * If no columns are provided, automatically add them from bean properties.
     * Get the first object in the list and get all the properties (except the
     * "class" property which is automatically skipped).
     * Of course this isn't possible for empty lists
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
     * Method doExport
     * @return int EVAL_PAGE or SKIP_PAGE
     * @throws JspException generic exception
     */
    protected int doExport() throws JspException
    {

        BaseExportView exportView = null;
        boolean exportFullList = this.properties.getExportFullList();

        log.debug("exportType=" + this.exportType);

        boolean exportHeader = properties.getExportHeader(this.exportType);

        exportView = ExportViewFactory.getView(exportType, this.tableModel, exportFullList, exportHeader);

        String mimeType = exportView.getMimeType();
        String exportString = exportView.doExport();

        return writeExport(mimeType, exportString);
    }

    /**
     * Will write the export.  The default behavior is to write directly to the response.  If the ResponseOverrideFilter
     * is configured for this request, will instead write the export content to a StringBuffer in the Request object.
     *
     * @param mimeType  mime type to set in the response
     * @param exportString String
     * @return int
     * @throws JspException if errors writing to out
     */
    protected int writeExport(String mimeType, String exportString) throws JspException
    {
        ServletResponse response = pageContext.getResponse();
        JspWriter out = pageContext.getOut();

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        StringBuffer bodyBuffer = (StringBuffer) request.getAttribute(ResponseOverrideFilter.CONTENT_OVERRIDE_BODY);
        if (bodyBuffer != null)
        {
            // We are running under the export filter
            StringBuffer contentTypeOverride =
                (StringBuffer) request.getAttribute(ResponseOverrideFilter.CONTENT_OVERRIDE_TYPE);
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
     * This returns a list of all of the data that will be displayed on the
     * page via the table tag.  This might include just a subset of the total
     * data in the list due to to paging being active, or the user asking us
     * to just show a subset, etc...<p>
     *
     * The list that is returned from here is not the original list, but it
     * does contain references to the same objects in the original list, so that
     * means that we can sort and reorder the list, but we can't mess with the
     * data objects in the list.
     * @return List
     * @throws JspException generic exception
     */
    public List getViewableData() throws JspException
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
     * Method getHTMLData
     * @return String
     * @throws JspException generic exception
     */
    private String getHTMLData() throws JspException
    {
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
                log.debug("lRowIterator.next()=" + row);
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
            log.debug("creating ColumnIterator on " + this.tableModel.getHeaderCellList());
            ColumnIterator columnIterator = row.getColumnIterator(this.tableModel.getHeaderCellList());

            while (columnIterator.hasNext())
            {
                // mLog.debug("lColumnIterator.hasNext()");
                Column column = columnIterator.nextColumn();

                Object value;

                // Get the value to be displayed for the column
                try
                {
                    buffer.append(column.getOpenTag());

                    value = column.getChoppedAndLinkedValue();
                }
                catch (Exception ex)
                {
                    log.error(ex.getMessage(), ex);
                    throw new JspException(ex.getMessage());
                }

                // Ok, let's write this column's cell...

                if (column.getGroup() != -1)
                {
                    try
                    {
                        buffer.append(groupColumns(value.toString(), column.getGroup()));
                    }
                    catch (Exception e)
                    {
                        log.error(e.getMessage(), e);
                    }
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
                log.debug("table has no columns");
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
            buffer.append("\t\t<tr class=\"even empty\">\n");
            buffer.append(
                "<td colspan=\""
                    + (this.tableModel.getNumberOfColumns() + 1)
                    + "\">"
                    + this.properties.getEmptyListMessage()
                    + "</td></tr>");
        }

        // close table body
        buffer.append(TagConstants.TAG_TBODY_CLOSE);

        // close table body
        buffer.append(TagConstants.TAG_TBODY_CLOSE);

        if (footer != null)
        {
            buffer.append(TagConstants.TAG_TFOOTER_OPEN);
            buffer.append(footer);
            buffer.append(TagConstants.TAG_TFOOTER_CLOSE);

            // reset footer
            footer = null;
        }

        // close table
        buffer.append(getCloseTag());

        buffer.append(this.getTableFooter());

        if (this.tableModel.getTableDecorator() != null)
        {
            this.tableModel.getTableDecorator().finish();
        }

        log.debug("getHTMLData end");

        return buffer.toString();

    }

    /**
     * Generates the table header, including the first row of the table which
     * displays the titles of the various columns
     * @return String
     **/
    private String getTableHeader()
    {
        log.debug("getTableHeader");
        StringBuffer buffer = new StringBuffer();

        // open thead
        buffer.append(TagConstants.TAG_THEAD_OPEN);

        // open tr
        buffer.append(TagConstants.TAG_TR_OPEN);

        // no columns?
        if (this.tableModel.isEmpty())
        {
            buffer.append(TagConstants.TAG_TH_OPEN);
            buffer.append(this.properties.getNoColumnMessage());
            buffer.append(TagConstants.TAG_TH_CLOSE);
        }

        // iterator on columns for header
        Iterator iterator = this.tableModel.getHeaderCellList().iterator();

        while (iterator.hasNext())
        {
            // get the header cell
            HeaderCell headerCell = (HeaderCell) iterator.next();

            // if sorted add styles
            if (headerCell.isAlreadySorted())
            {
                // sorted css class
                headerCell.addHeaderClass(TableTagParameters.CSS_SORTEDCOLUMN);

                // sort order css class
                headerCell.addHeaderClass(
                    TableTagParameters.CSS_SORTORDERPREFIX
                        + (this.tableModel.isSortOrderAscending()
                            ? TableTagParameters.VALUE_SORT_DESCENDING
                            : TableTagParameters.VALUE_SORT_ASCENDING));
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

        log.debug("getTableHeader::end");
        return buffer.toString();
    }

    /**
     * Generates table footer with links for export commands.
     * @return String
     **/
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
     * Method getSearchResultAndNavigation
     * @return String
     */
    private String getSearchResultAndNavigation()
    {
        if (log.isDebugEnabled())
        {
            log.debug("starting getSearchResultAndNavigation");
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
     * returns the formatted export links section
     * @return String export links section
     */
    private String getExportLinks()
    {

        // Figure out what formats they want to export, make up a little string

        Href exportHref = new Href(this.baseHref);

        StringBuffer buffer = new StringBuffer();

        Iterator iterator = ExportTypeEnum.iterator();

        while (iterator.hasNext())
        {
            ExportTypeEnum currentExportType = (ExportTypeEnum) iterator.next();

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
     * This takes a cloumn value and grouping index as the argument.
     * It then groups the column and returns the appropritate string back to the
     * caller.
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

                if (!((String) this.previousRow.get(new Integer(j)))
                    .equals(((String) this.nextRow.get(new Integer(j)))))
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
     * encode a parameter name to be unique in the page
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
