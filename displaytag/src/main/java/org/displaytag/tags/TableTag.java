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
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.DecoratorFactory;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.InvalidTagAttributeValueException;
import org.displaytag.export.BaseExportView;
import org.displaytag.export.CsvView;
import org.displaytag.export.ExcelView;
import org.displaytag.export.XmlView;
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
     * Has the commons-lang dependency been checked?
     */
    protected static boolean commonsLangChecked = false;


    /**
     * Iterator on collection
     */
    private Iterator mIterator;

    /**
     * actual row number, updated during iteration
     */
    private int mRowNumber = 1;

    /**
     * List. Used when user directly set a collection using the "list" attribute
     */
    private Object mList = null;

    /**
     * name of the object to use for iteration. Can contain expression
     */
    private String mName;

    /**
     * property to get into the bean defined by "name".
     * @deprecated Use expressions in "name" attribute
     */
    private String mProperty;

    /**
     * scope of the bean defined by "name". Use expressions in name instead
     * @deprecated
     */
    private String mScope;

    /**
     * Hashtable wich contains previous row values. Needed for grouping
     */
    private Hashtable mPreviousRow = null;

    /**
     * table model - initialized in doStartTag()
     */
    private TableModel mTableModel;

    /**
     * current row
     */
    private Row mCurrentRow;

    /**
     * next row
     */
    private Hashtable mNextRow = null;

    /**
     * length of list to display - reset in doEndTag()
     */
    private int mLength = 0;

    /**
     * table decorator class name - cleaned in doEndTag()
     */
    private String mDecoratorName;

    /**
     * page size - reset in doEndTag()
     */
    private int mPagesize = 0;

    /**
     * add export links - reset in doEndTag()
     */
    private boolean mExport = false;

    /**
     * Used by various functions when the person wants to do paging -
     * cleaned in doEndTag()
     */
    private SmartListHelper mHelper = null;

    /**
     * base href used for links - set in initParameters()
     */
    private Href mBaseHref;

    /**
     * table properties - set in doStartTag()
     */
    private TableProperties mProp;

    /**
     * page number - set in initParameters()
     */
    private int mPageNumber = 1;

    /**
     * list offset - reset in doEndTag()
     */
    private int mOffset = 0;

    /**
     * export type - set in initParameters()
     */
    private int mExportType = TableTagParameters.EXPORT_TYPE_NONE;

    /**
     * index of the previously sorted column
     */
    private int mPreviousSortColumn;

    /**
     * previous sorting order
     */
    private boolean mPreviousOrder;

    /**
     * sort the full list?
     */
    private boolean mSortFullTable;

    /**
     * Request uri
     */
    private String mRequestUri;

    /**
     * daAfterBody() has been executed at least once?
     */
    private boolean mDoAfterBodyExecuted;

    /**
     * the String used to encode parameter.
     * Initialized at the first use of encodeParameter()
     */
    private String mTableParameterIdentifier;

    /**
     * the index of the column sorted by default
     */
    private int mDefaultSortedColumn = -1;

    /**
     * setter for the "sort" attribute
     * @param pSort "page" (sort a single page) or "list" (sort the full list)
     * @throws InvalidTagAttributeValueException if value is not "page" or "list"
     */
    public void setSort(String pSort) throws InvalidTagAttributeValueException
    {
        if (TableTagParameters.SORT_AMOUNT_PAGE.equals(pSort))
        {
            mSortFullTable = false;
        }
        else if (TableTagParameters.SORT_AMOUNT_LIST.equals(pSort))
        {
            mSortFullTable = true;
        }
        else
        {
            throw new InvalidTagAttributeValueException(getClass(), "sort", pSort);
        }
    }

    /**
     * setter for the "requestURI" attribute
     * @param pUri base URI for creating links
     */
    public void setRequestURI(String pUri)
    {
        mRequestUri = pUri;
    }

    /**
     * Method setList
     * @param pList Object
     */
    public void setList(Object pList)
    {
        mList = pList;
    }

    /**
     * Method setName
     * @param pName String
     */
    public void setName(String pName)
    {
        mName = pName;
    }

    /**
     * Method setProperty
     * @param pProperty String
     */
    public void setProperty(String pProperty)
    {
        mProperty = pProperty;
    }

    /**
     * Method setLength
     * @param pLength String
     * @throws InvalidTagAttributeValueException if value is not a valid integer
     */
    public void setLength(String pLength) throws InvalidTagAttributeValueException
    {

        try
        {
            mLength = Integer.parseInt(pLength);
        }
        catch (NumberFormatException e)
        {
            throw new InvalidTagAttributeValueException(getClass(), "length", pLength);
        }

    }

    /**
     * set the index of the default sorted column
     * @param pDefaultSortedColumnIndex index of the column to sort
     * @throws InvalidTagAttributeValueException if value is not a valid integer
     */
    public void setDefaultsort(String pDefaultSortedColumnIndex) throws InvalidTagAttributeValueException
    {

        try
        {
            // parse and subtract one (internal index is 0 based)
            mDefaultSortedColumn = Integer.parseInt(pDefaultSortedColumnIndex) - 1;
        }
        catch (NumberFormatException e)
        {
            throw new InvalidTagAttributeValueException(getClass(), "defaultsort", pDefaultSortedColumnIndex);
        }

    }

    /**
     * Method setPagesize
     * @param pStringValue String
     * @throws InvalidTagAttributeValueException if value is not a valid integer
     */
    public void setPagesize(String pStringValue) throws InvalidTagAttributeValueException
    {

        try
        {
            mPagesize = Integer.parseInt(pStringValue);
        }
        catch (NumberFormatException e)
        {
            throw new InvalidTagAttributeValueException(getClass(), "pagesize", pStringValue);
        }

    }

    /**
     * Method setScope
     * @param pScope String
     */
    public void setScope(String pScope)
    {
        mScope = pScope;
    }

    /**
     * Method setDecorator
     * @param pDecorator String
     */
    public void setDecorator(String pDecorator)
    {
        mDecoratorName = pDecorator;
    }

    /**
     * Method setExport
     * @param pExport String
     */
    public void setExport(String pExport)
    {
        if (!Boolean.FALSE.toString().equals(pExport))
        {
            mExport = true;
        }
    }

    /**
     * Method setOffset
     * @param pStringValue String
     * @throws InvalidTagAttributeValueException if value is not a valid positive integer
     */
    public void setOffset(String pStringValue) throws InvalidTagAttributeValueException
    {
        try
        {
            int userOffset = Integer.parseInt(pStringValue);

            if (userOffset < 1)
            {
                throw new InvalidTagAttributeValueException(getClass(), "offset", pStringValue);
            }

            // mOffset is 0 based, subtract 1
            mOffset = (userOffset - 1);
        }
        catch (NumberFormatException e)
        {
            throw new InvalidTagAttributeValueException(getClass(), "offset", pStringValue);
        }

    }

    /**
     * Set the base href used in creating link
     * @param pBaseHref Href
     */
    protected void setBaseHref(Href pBaseHref)
    {
        mBaseHref = pBaseHref;
    }

    /**
     * Returns the properties.
     * @return TableProperties
     */
    protected TableProperties getProperties()
    {
        return mProp;
    }

    /**
     * Called by interior column tags to help this tag figure out how it is
     * supposed to display the information in the List it is supposed to
     * display
     *
     * @param pColumn an internal tag describing a column in this tableview
     **/
    public void addColumn(HeaderCell pColumn)
    {
        log.debug("addColumn " + pColumn);
        mTableModel.addColumnHeader(pColumn);
    }

    /**
     * Method addCell
     * @param pCell Cell
     */
    public void addCell(Cell pCell)
    {
        // check if null: could be null if list is empty, we don't need to fill rows
        if (mCurrentRow != null)
        {
            mCurrentRow.addCell(pCell);
        }
    }

    /**
     * Method isFirstIteration
     * @return boolean
     */
    protected boolean isFirstIteration()
    {
        // in first iteration mRowNumber is 1
        // (mRowNumber is incremented in doAfterBody)
        return mRowNumber == 1;
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
          {  // Do they have commons lang ?
              Class stringUtils = Class.forName("org.apache.commons.lang.StringUtils");
              try
              {
                  // this method is new in commons-lang 2.0
                  stringUtils.getMethod("capitalize", new Class[]{String.class});
              }
              catch (NoSuchMethodException ee)
              {
                  throw new JspTagException("\n\nYou appear to have an INCOMPATIBLE VERSION of the Commons Lang library.  \n" +
                          "Displaytag requires version 2 of this library, and you appear to have a prior version in your \n" +
                          "classpath.  You must remove this prior version AND ensure that ONLY version 2 is in your classpath.\n " +
                          "If commons-lang-1.x is in your classpath, be sure to remove it. \n" +
                          "Be sure to delete all cached or temporary jar files from your application server; Tomcat users \n" +
                          "should be sure to also check the CATALINA_HOME/shared folder; you may need to restart the server. \n" +
                          "commons-lang-2.jar is available in the displaytag distribution, or from the Jakarta website at \n" +
                          "http://jakarta.apache.org/commons \n\n.");
              }
          }
          catch (ClassNotFoundException e)
          {
              throw new JspTagException("You do not appear to have the Commons Lang library, version 2.  " +
                      "commons-lang-2.jar is available in the displaytag distribution, or from the Jakarta website at " +
                      "http://jakarta.apache.org/commons .  ");
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

        mTableModel = new TableModel();

        mProp = new TableProperties();

        initParameters();

        doIteration();

        // always return EVAL_BODY_TAG to get column headers also if the table is empty
        return EVAL_BODY_TAG;
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
        mDoAfterBodyExecuted = true;

        if (log.isDebugEnabled())
        {
            log.debug("doAfterBody() - iterator with id = " + id + " on row number " + mRowNumber);
        }

        // increment mRowNumber
        mRowNumber++;

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
        if (mCurrentRow != null)
        {
            // if yes add to table model and remove
            mTableModel.addRow(mCurrentRow);
            mCurrentRow = null;
        }

        if (mIterator.hasNext())
        {

            Object iteratedObject = mIterator.next();

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
                pageContext.setAttribute(id + TableTagExtraInfo.ROWNUM_SUFFIX, new Integer(mRowNumber));
            }

            // Row object for Cell values
            mCurrentRow = new Row(iteratedObject, mRowNumber);

            if (log.isDebugEnabled())
            {
                log.debug("doIteration() returning EVAL_BODY_TAG");
            }

            // new iteration
            return EVAL_BODY_TAG;
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("doIteration() - iterator [" + id + "] ended after " + mRowNumber + " rows");
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
        mPageNumber = (pageNumberParameter == null) ? 1 : pageNumberParameter.intValue();

        Integer sortColumnParameter = requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_SORT));
        int sortColumn = (sortColumnParameter == null) ? mDefaultSortedColumn : sortColumnParameter.intValue();
        mTableModel.setSortedColumnNumber(sortColumn);

        mTableModel.setSortFullTable(mSortFullTable);

        Integer paramOrder = requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_ORDER));
        boolean order = !(new Integer(TableTagParameters.VALUE_SORT_DESCENDING).equals(paramOrder));
        mTableModel.setSortOrderAscending(order);

        // if the behaviour is sort full page we need to go back to page one if sort of order is changed
        if (mSortFullTable && (sortColumn != -1))
        {

            // save actual sort to href
            mBaseHref.addParameter(encodeParameter(TableTagParameters.PARAMETER_PREVIOUSSORT), sortColumn);
            mBaseHref.addParameter(encodeParameter(TableTagParameters.PARAMETER_PREVIOUSORDER), paramOrder);

            // read previous sort from request
            Integer previousSortColumnParameter =
                requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_SORT));
            mPreviousSortColumn = (previousSortColumnParameter == null) ? -1 : previousSortColumnParameter.intValue();

            Integer previousParamOrder =
                requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_PREVIOUSORDER));
            mPreviousOrder = !(new Integer(TableTagParameters.VALUE_SORT_DESCENDING).equals(previousParamOrder));

        }

        Integer exportTypeParameter =
            requestHelper.getIntParameter(encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE));
        mExportType =
            (exportTypeParameter == null) ? TableTagParameters.EXPORT_TYPE_NONE : exportTypeParameter.intValue();

        // if list is null check
        if (mList == null)
        {
            // create a complete string for compatibility with previous version before expression evaluation.
            // this approach is optimized for new expressions, not for previous property/scope parameters
            StringBuffer fullName = new StringBuffer();

            // append scope
            if (mScope != null && !"".equals(mScope))
            {
                fullName.append(mScope).append("Scope.");
            }

            // base bean name
            if (mName != null)
            {
                fullName.append(mName);
            }

            // append property
            if (mProperty != null && !"".equals(mProperty))
            {
                fullName.append('.').append(mProperty);
            }

            mList = evaluateExpression(fullName.toString());

        }

        mIterator = IteratorUtils.getIterator(mList);

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

        if (mRequestUri != null)
        {
            // if user has added a requestURI create a new href
            mBaseHref = new Href(mRequestUri);

            // ... ancd copy parameters from the curret request
            HashMap parameterMap = normalHref.getParameterMap();
            mBaseHref.addParameterMap(parameterMap);
        }
        else
        {
            // simply copy href
            mBaseHref = normalHref;
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

        if (!mDoAfterBodyExecuted)
        {
            log.debug(
                "tag body is empty. Manually iterates on collection to preserve compatibility with previous version");

            // first row (created in doStartTag)
            if (mCurrentRow != null)
            {
                // if yes add to table model and remove
                mTableModel.addRow(mCurrentRow);
            }

            // other rows
            while (mIterator.hasNext())
            {
                Object iteratedObject = mIterator.next();
                mRowNumber++;

                // Row object for Cell values
                mCurrentRow = new Row(iteratedObject, mRowNumber);

                mTableModel.addRow(mCurrentRow);
            }
        }

        // here

        // new in 1.0-b2
        // if no rows are defined automatically get all properties from bean
        if (mTableModel.isEmpty())
        {
            describeEmptyTable();
        }

        TableDecorator tableDecorator = DecoratorFactory.loadTableDecorator(mDecoratorName);

        if (tableDecorator != null)
        {
            tableDecorator.init(pageContext, mList);
            mTableModel.setTableDecorator(tableDecorator);
        }

        List pageData = getViewableData();
        mTableModel.setRowListPage(pageData);

        // Figure out how we should sort this data, typically we just sort
        // the data being shown, but the programmer can override this behavior

        if (!mTableModel.isSortFullTable())
        {
            mTableModel.sortPageList();
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

        if (mExportType == TableTagParameters.EXPORT_TYPE_NONE)
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
        mHelper = null;
        mExport = false;
        mScope = null;
        mProperty = null;
        mDecoratorName = null;
        mPagesize = 0;
        mLength = 0;
        mOffset = 0;
        mDefaultSortedColumn = -1;
        mRowNumber = 1;
        mList = null;
        mSortFullTable = false;
        mDoAfterBodyExecuted = false;
        mCurrentRow = null;
        mTableModel = null;
        mRequestUri = null;
        mTableParameterIdentifier = null;

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
        mIterator = IteratorUtils.getIterator(mList);
        if (mIterator.hasNext())
        {
            Object iteratedObject = mIterator.next();
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
                String property = (String) propertiesIterator.next();

                // dont't want to add the standard "class" property
                if (!"class".equals(property))
                {
                    // creates a new header and add to the table model
                    HeaderCell headerCell = new HeaderCell();
                    headerCell.setBeanPropertyName(property);
                    mTableModel.addColumnHeader(headerCell);
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

        BaseExportView exportView;
        boolean exportFullList = mProp.getExportFullList();

        log.debug("exportType=" + mExportType);
        switch (mExportType)
        {

            case TableTagParameters.EXPORT_TYPE_CSV :
                log.debug("export CSV");
                exportView = new CsvView(mTableModel, exportFullList);
                break;
            case TableTagParameters.EXPORT_TYPE_EXCEL :
                log.debug("export EXCEL");
                exportView = new ExcelView(mTableModel, exportFullList);
                break;
            case TableTagParameters.EXPORT_TYPE_XML :
                log.debug("export XML");
                exportView = new XmlView(mTableModel, exportFullList);
                break;
            default :
                exportView = null;

        }

        if (exportView == null)
        {
            throw new JspException("Invalid export type: " + mExportType);
        }

        String mimeType = exportView.getMimeType();
        String exportString = exportView.doExport();

        return writeExport(mimeType, exportString);
    }

    /**
     * Method writeExport
     * @param pMimeType  mime type to set in the response
     * @param pExportString String
     * @return int
     * @throws JspException if errors writing to out
     */
    protected int writeExport(String pMimeType, String pExportString) throws JspException
    {
        ServletResponse response = pageContext.getResponse();
        JspWriter out = pageContext.getOut();

        int returnValue = EVAL_PAGE;

        try
        {
            out.clear();

            response.setContentType(pMimeType);

            out.write(pExportString);

            out.flush();

            returnValue = SKIP_PAGE;
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage(), ex);
            throw new JspException(ex.getMessage());
        }

        return returnValue;
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

        if (mTableModel.isSortFullTable())
        {
            // Sort the total list...
            mTableModel.sortFullList();
        }

        Object originalData = mTableModel.getRowListFull();

        // If they have asked for a subset of the list via the length
        // attribute, then only fetch those items out of the master list.

        fullList = CollectionUtil.getListFromObject(originalData, mOffset, mLength);

        // If they have asked for just a page of the data, then use the
        // SmartListHelper to figure out what page they are after, etc...

        if (mPagesize > 0)
        {

            mHelper = new SmartListHelper(fullList, mPagesize, mProp);

            mHelper.setCurrentPage(mPageNumber);

            fullList = mHelper.getListForCurrentPage();

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
        mPreviousRow = new Hashtable(10);

        // variables to hold next row column values.
        mNextRow = new Hashtable(10);

        //  Put the page stuff there if it needs to be there...
        if (mProp.getAddPagingBannerTop())
        {
            // search result and navigation bar
            buffer.append(getSearchResultAndNavigation());
        }

        // open table
        buffer.append(getOpenTag());

        // thead
        if (mProp.getShowHeader())
        {
            buffer.append(getTableHeader());
        }

        // open table body
        buffer.append(TagConstants.TAG_TBODY_OPEN);

        // Ok, start bouncing through our list...
        RowIterator rowIterator = mTableModel.getRowIterator();

        // iterator on rows
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            if (log.isDebugEnabled())
            {
                log.debug("lRowIterator.next()=" + row);
            }
            if (mTableModel.getTableDecorator() != null)
            {

                String stringStartRow = mTableModel.getTableDecorator().startRow();
                if (stringStartRow != null)
                {
                    buffer.append(stringStartRow);
                }

            }

            // open tr
            buffer.append(row.getOpenTag());

            // iterator on columns
            log.debug("creating ColumnIterator on " + mTableModel.getHeaderCellList());
            ColumnIterator columnIterator = row.getColumnIterator(mTableModel.getHeaderCellList());

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
            if (mTableModel.isEmpty())
            {
                log.debug("table has no columns");
                buffer.append(TagConstants.TAG_TD_OPEN);
                buffer.append(row.getObject().toString());
                buffer.append(TagConstants.TAG_TD_CLOSE);
            }

            // close tr
            buffer.append(row.getCloseTag());

            if (mTableModel.getTableDecorator() != null)
            {
                String endRow = mTableModel.getTableDecorator().finishRow();
                if (endRow != null)
                {
                    buffer.append(endRow);
                }
            }

        }

        if (mTableModel.getRowListPage().size() == 0)
        {
            buffer.append("\t\t<tr class=\"even empty\">\n");
            buffer.append(
                "<td colspan=\""
                    + (mTableModel.getNumberOfColumns() + 1)
                    + "\">"
                    + mProp.getEmptyListMessage()
                    + "</td></tr>");
        }

        // close table body
        buffer.append(TagConstants.TAG_TBODY_CLOSE);

        buffer.append(this.getTableFooter());

        if (mTableModel.getTableDecorator() != null)
        {
            mTableModel.getTableDecorator().finish();
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
        if (mTableModel.isEmpty())
        {
            buffer.append(TagConstants.TAG_TH_OPEN);
            buffer.append(mProp.getNoColumnMessage());
            buffer.append(TagConstants.TAG_TH_CLOSE);
        }

        // iterator on columns for header
        Iterator iterator = mTableModel.getHeaderCellList().iterator();

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
                        + (mTableModel.isSortOrderAscending()
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
                Href href = new Href(mBaseHref);

                // add column number as link parameter
                href.addParameter(encodeParameter(TableTagParameters.PARAMETER_SORT), headerCell.getColumnNumber());

                boolean nowOrderAscending;
                if (headerCell.isAlreadySorted() && mTableModel.isSortOrderAscending())
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
                if (mTableModel.isSortFullTable())
                {
                    // if sorting (column or order) is changed reset page
                    if (headerCell.getColumnNumber() != mPreviousSortColumn || ((nowOrderAscending ^ mPreviousOrder)))
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

        // close table
        buffer.append(getCloseTag());

        // Put the page stuff there if it needs to be there...
        if (mProp.getAddPagingBannerBottom())
        {
            buffer.append(getSearchResultAndNavigation());
        }

        // add export links
        if (mExport)
        {
            addExportLinks(buffer);
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

        if (mPagesize != 0 && mHelper != null)
        {
            // create a new href
            Href navigationHref = new Href(mBaseHref);

            // add page parameter with message format
            navigationHref.addParameter(encodeParameter(TableTagParameters.PARAMETER_PAGE), "{0,number,#}");

            StringBuffer buffer =
                new StringBuffer().append(mHelper.getSearchResultsSummary()).append(
                    mHelper.getPageNavigationBar(navigationHref.toString()));

            return buffer.toString();
        }
        return "";
    }

    /**
     * Method addExportLinks
     * @param pBuffer StringBuffer
     */
    private void addExportLinks(StringBuffer pBuffer)
    {

        // Figure out what formats they want to export, make up a little string

        Href exportHref = new Href(mBaseHref);

        StringBuffer buffer = new StringBuffer();
        if (mProp.getAddCsvExport())
        {
            exportHref.addParameter(
                encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE),
                TableTagParameters.EXPORT_TYPE_CSV);

            Anchor anchor = new Anchor(exportHref, mProp.getExportCsvLabel());
            buffer.append(anchor.toString());
        }

        if (mProp.getAddExcelExport())
        {
            if (buffer.length() > 0)
            {
                buffer.append(mProp.getExportBannerSeparator());
            }
            exportHref.addParameter(
                encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE),
                TableTagParameters.EXPORT_TYPE_EXCEL);

            Anchor anchor = new Anchor(exportHref, mProp.getExportExcelLabel());
            buffer.append(anchor.toString());
        }

        if (mProp.getAddXmlExport())
        {
            if (buffer.length() > 0)
            {
                buffer.append(mProp.getExportBannerSeparator());
            }
            exportHref.addParameter(
                encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE),
                TableTagParameters.EXPORT_TYPE_XML);

            Anchor anchor = new Anchor(exportHref, mProp.getExportXmlLabel());
            buffer.append(anchor.toString());
        }

        String[] exportOptions = { buffer.toString()};
        pBuffer.append(MessageFormat.format(mProp.getExportBanner(), exportOptions));
    }

    /**
     * This takes a cloumn value and grouping index as the argument.
     * It then groups the column and returns the appropritate string back to the
     * caller.
     * @param pValue String
     * @param pGroup int
     * @return String
     */
    private String groupColumns(String pValue, int pGroup)
    {

        if ((pGroup == 1) & mNextRow.size() > 0)
        {
            // we are at the begining of the next row so copy the contents from
            // nextRow to the previousRow.
            mPreviousRow.clear();
            mPreviousRow.putAll(mNextRow);
            mNextRow.clear();
        }

        if (!mNextRow.containsKey(new Integer(pGroup)))
        {
            // Key not found in the nextRow so adding this key now...
            // remember all the old values.
            mNextRow.put(new Integer(pGroup), new String(pValue));
        }

        //  Start comparing the value we received, along with the grouping index.
        //  if no matching value is found in the previous row then return the value.
        //  if a matching value is found then this value should not get printed out
        //  so return ""
        if (mPreviousRow.containsKey(new Integer(pGroup)))
        {
            for (int j = 1; j <= pGroup; j++)
            {

                if (!((String) mPreviousRow.get(new Integer(j))).equals(((String) mNextRow.get(new Integer(j)))))
                {
                    // no match found so return this value back to the caller.
                    return pValue;
                }
            }
        }

        // This is used, for when there is no data in the previous row,
        // It gets used only the first time.
        if (mPreviousRow.size() == 0)
        {
            return pValue;
        }

        // There is corresponding value in the previous row
        // this value doesn't need to be printed, return ""
        return "";
    }

    /**
     * Called by the setProperty tag to override some default behavior or text String.
     * @param pName String property name
     * @param pValue String property value
     */
    public void setProperty(String pName, String pValue)
    {
        mProp.setProperty(pName, pValue);
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
        return mName;
    }

    /**
     * encode a parameter name to be unique in the page
     * @param pParameterName parameter name to encode
     * @return String encoded parameter name
     */
    protected String encodeParameter(String pParameterName)
    {
        // code used to encode the parameter already creeated?
        if (mTableParameterIdentifier == null)
        {
            // use name and id to get the unique identifier
            String stringIdentifier = "x-" + getId() + mName;

            // get the array
            char[] charArray = stringIdentifier.toCharArray();

            // calculate a simple checksum-like value
            int checkSum = 0;

            for (int j = 0; j < charArray.length; j++)
            {
                checkSum += charArray[j] * j;
            }

            // this is the full identifier used for all the parameters
            mTableParameterIdentifier = "d-" + checkSum + "-";

        }

        return mTableParameterIdentifier + pParameterName;
    }

}
