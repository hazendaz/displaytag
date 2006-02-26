/**
 * Todo:
 *   - Investigate using styles rather then color specific attributes for
 *     displaying the table.
 *
 *   - Make the header links, so that if you click on them, it will reorder the
 *     list according to that column (all automatically of course).
 *
 *   - Provide a hook that allows people to choose which columns they want to
 *     view in the list.
 *
 * $Id$
 **/

package org.apache.taglibs.display;

import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;

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
 *   <displaytag:table name="list" >
 *     <displaytag:column property="title" />
 *     <displaytag:column property="code" />
 *     <displaytag:column property="dean" />
 *   </displaytag:list>
 *
 * More Complete Usage:<p>
 *
 *   <displaytag:table name="list" pagesize="100">
 *     <displaytag:column property="title"
 *                    title="College Title" width="60%" sort="true"
 *                    href="/displaytag/pubs/college/edit.page"
 *                      paramId="OID"
 *                    paramProperty="OID" />
 *     <displaytag:column property="code" width="10%" sort="true"/>
 *     <displaytag:column property="primaryOfficer.name" title="Dean" width="30%" />
 *     <displaytag:column property="active" sort="true" />
 *   </displaytag:list>
 *
 *
 * Attributes:<p>
 *
 *   collection
 *   name
 *   property
 *   scope
 *   length
 *   offset
 *   pagesize
 *   decorator
 *
 *
 * HTML Pass-through Attributes
 *
 * There are a number of additional attributes that just get passed through to
 * the underlying HTML table declaration.  With the exception of the following
 * few default values, if these attributes are not provided, they will not
 * be displayed as part of the <table ...> tag.
 *
 *   width          - defaults to "100%" if not provided
 *   border         - defaults to "0" if not provided
 *   cellspacing    - defaults to "0" if not provided
 *   cellpadding    - defaults to "2" if not provided
 *   align
 *   background
 *   bgcolor
 *   frame
 *   height
 *   hspace
 *   rules
 *   summary
 *   vspace
 *
 * Notes:
 *   - We rely on struts goodies to help us deal with a variety of things we
 *     can iterator over (Collections, Arrays, etc...), and we use their tools
 *     to allow us to fetch properties from beans.
 *
 * @version $Revision$
 */
public class TableTag extends TemplateTag {
    private static final int EXPORT_TYPE_NONE = -1;
    private static final int EXPORT_TYPE_CSV = 1;
    private static final int EXPORT_TYPE_EXCEL = 2;
    private static final int EXPORT_TYPE_XML = 3;

    private static final int SORT_ORDER_DECENDING = 1;
    private static final int SORT_ORDER_ASCEENDING = 2;

    private List columns = new ArrayList(10);

    private Object list = null;
    private Decorator dec = null;

    private String name = null;
    private String property = null;
    private String length = null;
    private String offset = null;
    private String scope = null;
    private String pagesize = null;
    private String decorator = null;
    private String export = null;

    private String width = null;
    private String border = null;
    private String cellspacing = null;
    private String cellpadding = null;
    private String align = null;
    private String background = null;
    private String bgcolor = null;
    private String frame = null;
    private String height = null;
    private String hspace = null;
    private String rules = null;
    private String summary = null;
    private String vspace = null;
    private String clazz = null;

    private String requestURI = null;
    private Properties prop = null;

    private String display = null; // This variable hold the value of the data has to be displayed without the table.

    private SmartListHelper helper = null; // Used by various functions when the person wants to do paging

    // Variables that get set by investigating the request parameters

    private int sortColumn = -1;
    private int sortOrder = SORT_ORDER_ASCEENDING;
    private int pageNumber = 1;
    private int exportType = EXPORT_TYPE_NONE;

    // -------------------------------------------------------- Accessor methods

    public void setList(Object v) {
        this.list = v;
    }

    public void setName(String v) {
        this.name = v;
    }

    public void setProperty(String v) {
        this.property = v;
    }

    public void setLength(String v) {
        this.length = v;
    }

    public void setOffset(String v) {
        this.offset = v;
    }

    public void setScope(String v) {
        this.scope = v;
    }

    public void setPagesize(String v) {
        this.pagesize = v;
    }

    public void setDecorator(String v) {
        this.decorator = v;
    }

    public void setExport(String v) {
        this.export = v;
    }

    public void setWidth(String v) {
        this.width = v;
    }

    public void setBorder(String v) {
        this.border = v;
    }

    public void setCellspacing(String v) {
        this.cellspacing = v;
    }

    public void setCellpadding(String v) {
        this.cellpadding = v;
    }

    public void setAlign(String v) {
        this.align = v;
    }

    public void setBackground(String v) {
        this.background = v;
    }

    public void setBgcolor(String v) {
        this.bgcolor = v;
    }

    public void setFrame(String v) {
        this.frame = v;
    }

    public void setHeight(String v) {
        this.height = v;
    }

    public void setHspace(String v) {
        this.hspace = v;
    }

    public void setRules(String v) {
        this.rules = v;
    }

    public void setSummary(String v) {
        this.summary = v;
    }

    public void setVspace(String v) {
        this.vspace = v;
    }

    public void setStyleClass(String v) {
        this.clazz = v;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Object getList() {
        return this.list;
    }

    public String getName() {
        return this.name;
    }

    public String getProperty() {
        return this.property;
    }

    public String getLength() {
        return this.length;
    }

    public String getOffset() {
        return this.offset;
    }

    public String getScope() {
        return this.scope;
    }

    public String getPagesize() {
        return this.pagesize;
    }

    public String getDecorator() {
        return this.decorator;
    }

    public String getExport() {
        return this.export;
    }

    public String getWidth() {
        return this.width;
    }

    public String getBorder() {
        return this.border;
    }

    public String getCellspacing() {
        return this.cellspacing;
    }

    public String getCellpadding() {
        return this.cellpadding;
    }

    public String getAlign() {
        return this.align;
    }

    public String getBackground() {
        return this.background;
    }

    public String getBgcolor() {
        return this.bgcolor;
    }

    public String getFrame() {
        return this.frame;
    }

    public String getHeight() {
        return this.height;
    }

    public String getHspace() {
        return this.hspace;
    }

    public String getRules() {
        return this.rules;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getVspace() {
        return this.vspace;
    }

    public String getStyleClass() {
        return this.clazz;
    }

    public void reset() {
        this.pageNumber = 1;
        this.sortColumn = 1;
    }

    public String getDisplay() {
        return this.display;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    /**
     * Returns the offset that the person provided as an int. If the user does
     * not provide an offset, or we can't figure out what they are trying to
     * tell us, then we default to 0.
     *
     * @return the number of objects that should be skipped while looping through
     *    the list to display the table
     */
    private int getOffsetValue() {
        int offsetValue = 0;

        if (this.offset != null) {
            try {
                offsetValue = Integer.parseInt(this.offset);
            }
            catch (NumberFormatException e) {
                Integer offsetObject = (Integer) pageContext.findAttribute(offset);
                if (offsetObject == null) {
                    offsetValue = 0;
                }
                else {
                    offsetValue = offsetObject.intValue();
                }
            }
        }
        if (offsetValue < 0) {
            offsetValue = 0;
        }

        return offsetValue;
    }

    /**
     * Returns the length that the person provided as an int. If the user does
     * not provide a length, or we can't figure out what they are trying to tell
     * us, then we default to 0.
     *
     * @return the maximum number of objects that should be shown while looping
     *    through the list to display the values.  0 means show all objects.
     */
    private int getLengthValue() {
        int lengthValue = 0;

        if (this.length != null) {
            try {
                lengthValue = Integer.parseInt(this.length);
            }
            catch (NumberFormatException e) {
                Integer lengthObject = (Integer) pageContext.findAttribute(length);
                if (lengthObject == null) {
                    lengthValue = 0;
                }
                else {
                    lengthValue = lengthObject.intValue();
                }
            }
        }
        if (lengthValue < 0) {
            lengthValue = 0;
        }

        return lengthValue;
    }

    /**
     * Returns the pagesize that the person provided as an int.  If the user does
     * not provide a pagesize, or we can't figure out what they are trying to tell
     * us, then we default to 0.
     *
     * @return the maximum number of objects that should be shown on any one page
     *    when displaying the list.  Setting this value also indicates that the
     *    person wants us to manage the paging of the list.
     */
    private int getPagesizeValue() {
        int pagesizeValue = 0;

        if (this.pagesize != null) {
            try {
                pagesizeValue = Integer.parseInt(this.pagesize);
            }
            catch (NumberFormatException e) {
                Integer pagesizeObject = (Integer) pageContext.findAttribute(this.pagesize);
                if (pagesizeObject == null) {
                    pagesizeValue = 0;
                }
                else {
                    pagesizeValue = pagesizeObject.intValue();
                }
            }
        }

        if (pagesizeValue < 0) {
            pagesizeValue = 0;
        }

        return pagesizeValue;
    }

    // ---------------------------------------- Communication with interior tags

    /**
     * Called by interior column tags to help this tag figure out how it is
     * supposed to display the information in the List it is supposed to
     * display
     *
     * @param obj an internal tag describing a column in this tableview
     */
    public void addColumn(ColumnTag obj) {
        columns.add(obj);
    }

    // --------------------------------------------------------- Tag API methods

    /**
     * When the tag starts, we just initialize some of our variables, and do a
     * little bit of error checking to make sure that the user is not trying
     * to give us parameters that we don't expect.
     */
    public int doStartTag() throws JspException {
        columns = new ArrayList(10);

        HttpServletRequest req = (HttpServletRequest) this.pageContext.getRequest();

        this.loadDefaultProperties();

        this.pageNumber = 1;
        if (req.getParameter("page") != null) {
            this.pageNumber = 1;
            try {
                this.pageNumber = Integer.parseInt(req.getParameter("page"));
            }
            catch (NumberFormatException e) {
                // It's ok to ignore, if they give us something bogus, we default
                // to showing the first page (index = 1)
            }
        }

        if (req.getParameter("sort") != null) {
            this.sortColumn = 0;
            try {
                this.sortColumn = Integer.parseInt(req.getParameter("sort"));
            }
            catch (NumberFormatException e) {
                // It's ok to ignore, if they give us something bogus, we default
                // to sorting the first column (index = 0)
            }

            this.sortOrder = SORT_ORDER_ASCEENDING;
            if (req.getParameter("order") != null) {
                if (req.getParameter("order").equalsIgnoreCase("dec")) {
                    this.sortOrder = SORT_ORDER_DECENDING;
                }
            }
        }

        this.exportType = EXPORT_TYPE_NONE;
        if (this.export != null) {
            if (req.getParameter("exportType") != null) {
                this.exportType = EXPORT_TYPE_NONE;
                try {
                    this.exportType = Integer.parseInt(req.getParameter("exportType"));
                }
                catch (NumberFormatException e) {
                    // It's ok to ignore, if they give us something bogus, we default
                    // to showing the first page (index = 1)
                }
            }
        }

        return super.doStartTag();
    }

    /**
     * Draw the table.  This is where everything happens, we figure out what
     * values we are supposed to be showing, we figure out how we are supposed
     * to be showing them, then we draw them.
     */
    public int doEndTag() throws JspException {
        HttpServletResponse res = (HttpServletResponse) this.pageContext.getResponse();
        JspWriter out = pageContext.getOut();

        List viewableData = this.getViewableData();

        // Load our table decorator if it is requested
        //this.dec = this.loadDecorator();
        //if( this.dec != null ) this.dec.init( this.pageContext, viewableData );

//      this.filterDataIfNeeded( viewableData );

        // Figure out how we should sort this data, typically we just sort
        // the data being shown, but the programmer can override this behavior

        if (prop.getProperty("sort.behavior").equalsIgnoreCase("page")) {
            this.sortDataIfNeeded(viewableData);
        }

        // Figure out where this data is going, if this is an export, then
        // we don't add the header and footer information

        StringBuffer buf = new StringBuffer(8000);

        // Get the data back in the representation that the user is after, do they
        // want HTML/XML/CSV/PDF/etc...

        /* JBO 2003/03/29: I added the toString() for 1.3 compatibility */
        if (this.exportType == EXPORT_TYPE_NONE) {
            buf.append(this.getHTMLData(viewableData).toString());
        }
        else if (this.exportType == EXPORT_TYPE_CSV) {
            buf.append(this.getRawData(viewableData).toString());
        }
        else if (this.exportType == EXPORT_TYPE_EXCEL) {
            buf.append(this.getExcelData(viewableData).toString());
        }
        else if (this.exportType == EXPORT_TYPE_XML) {
            buf.append(this.getXMLData(viewableData).toString());
        }

        // FIXME - Narayan had code to show raw data via an attribute, I've
        // broken it and need to fix it...

        // When writing the data, if it it's a normal HTML display, then go ahead
        // and write the data within the context of the web page, for any of the
        // other export types, we need to clear our buffer before we can write
        // out the data

        int returnValue = EVAL_PAGE;
        if (this.exportType == EXPORT_TYPE_NONE) {
            write(buf);
        }
        else {
            // If we can't clear the buffer, then the user needs to increase their
            // buffer size or something so that this will work.

            try {
                out.clear();

                if (this.exportType == EXPORT_TYPE_CSV) {
                    res.setContentType(prop.getProperty("export.csv.mimetype"));
                    out.write(buf.toString());
                    out.flush();
                }
                else if (this.exportType == EXPORT_TYPE_EXCEL) {
                    res.setContentType(prop.getProperty("export.excel.mimetype"));
                    out.write(buf.toString());
                    out.flush();
                }
                else if (this.exportType == EXPORT_TYPE_XML) {
                    res.setContentType(prop.getProperty("export.xml.mimetype"));
                    out.write(buf.toString());
                    out.flush();
                }

                returnValue = SKIP_PAGE;
            }
            catch (java.io.IOException ioe) {
                // FIXME - Beef up this error message and give the user some hints
                // on what they can do to resolve this...
                write(ioe.toString());
            }
        }
        reset();
        return returnValue;
    }

    /**
     * Given an Object, let's do our best to iterate over it
     */
    public static Iterator getIterator(Object o) throws JspException {

        Iterator iterator = null;

        if (o instanceof Collection) {
            iterator = ((Collection) o).iterator();
        }
        else if (o instanceof Iterator) {
            iterator = (Iterator) o;
        }
        else if (o instanceof Map) {
            iterator = ((Map) o).entrySet().iterator();
            /*
             *   This depends on importing struts.util.* -- see remarks in the import section of the file
             *
             * } else if( o instanceof Enumeration ) {
             *     iterator = new IteratorAdapter( (Enumeration)o );
             * }
            */
        }
        else {
            throw new JspException("I do not know how to iterate over '" + o.getClass() + "'.");
        }
        return iterator;
    }

    /**
     * This returns a list of all of the data that will be displayed on the
     * page via the table tag.  This might include just a subset of the total
     * data in the list due to to paging being active, or the user asking us
     * to just show a subset, etc.
     *
     * <p>The list that is returned from here is not the original list, but it
     * does contain references to the same objects in the original list, so that
     * means that we can sort and reorder the list, but we can't mess with the
     * data objects in the list.</p>
     */
    public List getViewableData() throws JspException {
        List viewableData = new ArrayList();

        // Acquire the collection that we are going to iterator over...

        Object collection = this.list;

        if (collection == null) {
            collection = this.lookup(this.pageContext, this.name,
                                     this.property, this.scope, false);
        }

        // Should put a check here that allows the user to choose if they
        // want this to error out or not...  Right now if we can't find a pointer
        // to their list or if they give us a null list, we just create an
        // empty list.

        if (collection == null) {
            collection = new ArrayList();
        }

        // Constructor an iterator that we use to actually go through the list
        // they have provided (no matter what type of data collection set they
        // are using


        /* YIKES! If we use a RowSetDynaClass to wrap a ResultSet, we'd
         * copy the entire set to a List -- not good at all if paging
         * (though functional -- I tested). Furthermore, RowSetDynaClass
         * is not yet officially released, so I'm leaving this out for
         * now.
         *
         * Anyway, the proper semantics would involve a class like ResultSetDynaClass,
         * but with some changes to the Iterator I'l trying to work on.
         *
         * if (collection instanceof ResultSet)
         *   try {
         *       collection = new RowSetDynaClass((ResultSet)collection).getRows ();
         *   } catch (java.sql.SQLException e) {
         *       throw new JspException( "Problems iterating over ResultSet.", e);
         *       }
         */

        if (collection.getClass().isArray())
            collection = Arrays.asList((Object[]) collection);

        Iterator iterator = getIterator(collection);

        // If the user has changed the way our default behavior works, then we
        // need to look for it now, and resort things if needed before we ask
        // for the viewable part. (this is a bad place for this, this should be
        // refactored and moved somewhere else).

        // Load our table decorator if it is requested
        this.dec = this.loadDecorator();
        if (this.dec != null) this.dec.init(this.pageContext, collection);

        if (!prop.getProperty("sort.behavior").equalsIgnoreCase("page")) {
            // Sort the total list...
            this.sortDataIfNeeded(collection);

            // If they have changed the default sorting behavior of the table
            // tag, and just clicked on a column, then reset their page number
            HttpServletRequest req = (HttpServletRequest) this.pageContext.getRequest();
            if (req.getParameter("sort") != null) {
                if (!prop.getProperty("sort.behavior").equalsIgnoreCase("page") && req.getParameter("page") == null) {
                    this.pageNumber = 1;
                }
            }
        }

        // If they have asked for an subset of the list via the offset or length
        // attributes, then only fetch those items out of the master list.
        int offsetValue = this.getOffsetValue();
        int lengthValue = this.getLengthValue();

        for (int i = 0; i < offsetValue; i++) {
            if (iterator.hasNext()) {
                iterator.next();
            }
        }

        int cnt = 0;
        while (iterator.hasNext()) {
            if (lengthValue > 0 && ++cnt > lengthValue) {
                break;
            }
            viewableData.add(iterator.next());
        }

        // If they have asked for just a page of the data, then use the
        // SmartListHelper to figure out what page they are after, etc...

        int pagesizeValue = this.getPagesizeValue();
        if (pagesizeValue > 0) {
            if (!(collection instanceof List))
                throw new JspException("Paging is not available for collections of type '" + collection.getClass() + "'.");
            helper = new SmartListHelper((List) collection, pagesizeValue, this.prop);
            // tlaw 12-10-2001
            // added the if/else statement below to allow sending an empty list to
            // this part of the program.  seems to work.
            // the statement inside the if is still needed by this program.
            // everything else can be removed.
            if (((List) collection).size() > 0) {
                helper.setCurrentPage(this.pageNumber);
            }
            iterator = helper.getListForCurrentPage().listIterator();

            viewableData.clear();
            while (iterator.hasNext()) {
                if (lengthValue > 0 && ++cnt > lengthValue) {
                    break;
                }
                viewableData.add(iterator.next());
            }
        }

        return viewableData;
    }

    /**
     * This method will sort the data in either ascending or decending order
     * based on the user clicking on the column headers.
     */
    private void sortDataIfNeeded(Object viewableData) {
        if (!(viewableData instanceof List))
            throw new RuntimeException("This function is only supported if the given collection is a java.util.List.");


        // At this point we have all the objects that are supposed to be shown
        // sitting in our internal list ready to be shown, so if they have clicked
        // on one of the titles, then sort the list in either ascending or
        // decending order...

        if (this.sortColumn != -1) {
            ColumnTag tag = (ColumnTag) this.columns.get(this.sortColumn);

            // If it is an explicit value, then sort by that, otherwise sort by
            // the property...

            if (tag.getValue() != null) {
                // Todo, figure out how to sort this better....
                //Collections.sort( (List)collection, tag.getValue() );
            }
            else {
                Collections.sort((List) viewableData, new BeanSorter(tag.getProperty(), this.dec));
            }

            if (this.sortOrder == SORT_ORDER_DECENDING) {
                Collections.reverse((List) viewableData);
            }
        }
    }

    private Hashtable previousRow = null;
    private Hashtable nextRow = null;

    private StringBuffer getHTMLData(List viewableData) throws JspException {
        StringBuffer buf = new StringBuffer(8000);

        previousRow = new Hashtable(10); // variables to hold the previous row columns values.
        nextRow = new Hashtable(10); // variables to hold next row column values.

        // paulsenj:columndecorator
        // build an array of column decorator objects - 1 for each column tag
        ColumnDecorator[] colDecorators = new ColumnDecorator[columns.size()];
        for (int c = 0; c < columns.size(); c++) {
            String columnDecorator = ((ColumnTag) columns.get(c)).getDecorator();
            colDecorators[c] = loadColumnDecorator(columnDecorator);
            if (colDecorators[c] != null)
                colDecorators[c].init(this.pageContext, this.list);
        }

        // Ok, start bouncing through our list.........

        int rowcnt = 0;
        buf.append(this.getTableHeader().toString());
        Iterator iterator = viewableData.iterator();

        while (iterator.hasNext()) {
            Object obj = iterator.next();

            if (this.dec != null) {
                String rt = this.dec.initRow(obj, rowcnt, rowcnt + this.getOffsetValue());
                if (rt != null) buf.append(rt);
            }

            // paulsenj:columndecorator
            for (int c = 0; c < columns.size(); c++) {
                if (colDecorators[c] != null) {
                    colDecorators[c].initRow(obj, rowcnt, rowcnt + (this.getPagesizeValue() * (this.pageNumber - 1)));
                }
            }

            pageContext.setAttribute("smartRow", obj);


            if (this.dec != null) {
                String rt = this.dec.startRow();
                if (rt != null) buf.append(rt);
            }

            // Start building the row to be displayed...

            buf.append("<tr");

            if (rowcnt % 2 == 0) {
                buf.append(" class=\"tableRowOdd\"");
            }
            else {
                buf.append(" class=\"tableRowEven\"");
            }

            buf.append(">\n");

            // Bounce through our columns and pull out the data from this object
            // that we are currently focused on (lives in "smartRow").
            for (int i = 0; i < this.columns.size(); i++) {
                ColumnTag tag = (ColumnTag) this.columns.get(i);

                buf.append("<td ");
                buf.append(tag.getCellAttributes().toString());
                buf.append(">");

                // Get the value to be displayed for the column

                Object value = null;
                if (tag.getValue() != null) {
                    value = tag.getValue();
                }
                else {
                    if (tag.getProperty().equalsIgnoreCase("ff")) {
                        value = String.valueOf(rowcnt);
                    }
                    else {
                        value = this.lookup(pageContext, "smartRow",
                                            tag.getProperty(), null, true);

                        // paulsenj:columndecorator
                        // if columndecorator supplied for this column
                        // call it to decorate the value
                        // ed - I call the column decorator after the table decorator is called
                        // feel free to modify this as you wish
                        if (colDecorators[i] != null)
                            value = colDecorators[i].decorate(value);
                    }
                }

                // By default, we show null values as empty strings, unless the
                // user tells us otherwise.

                if (value == null || value.equals("null")) {
                    if (tag.getNulls() == null || tag.getNulls().equalsIgnoreCase("false")) {
                        value = "";
                    }
                }

                // String to hold what's left over after value is chopped
                String leftover = "";
                boolean chopped = false;
                String tempValue = "";
                if (value != null) {
                    tempValue = value.toString();
                }

                // trim the string if a maxLength or maxWords is defined
                if (tag.getMaxLength() > 0 && tempValue.length() > tag.getMaxLength()) {
                    leftover = "..." + tempValue.substring(tag.getMaxLength(), tempValue.length());
                    value = tempValue.substring(0, tag.getMaxLength()) + "...";
                    chopped = true;
                }
                else if (tag.getMaxWords() > 0) {
                    StringBuffer tmpBuffer = new StringBuffer();
                    StringTokenizer st = new StringTokenizer(tempValue);
                    int numTokens = st.countTokens();
                    if (numTokens > tag.getMaxWords()) {
                        int x = 0;
                        while (st.hasMoreTokens() && (x < tag.getMaxWords())) {
                            tmpBuffer.append(st.nextToken() + " ");
                            x++;
                        }
                        leftover = "..." + tempValue.substring(tmpBuffer.length(), tempValue.length());
                        tmpBuffer.append("...");
                        value = tmpBuffer;
                        chopped = true;
                    }
                }

                // Are we supposed to set up a link to the data being displayed
                // in this column...
                if (tag.getAutolink() != null && tag.getAutolink().equalsIgnoreCase("true")) {
                    // TODO, set up some regex style function that does a search
                    // and replace on email addresses, and URLs...  Don't use any
                    // 3rd party stuff...

                    value = this.autoLink(value.toString());
                }

                // TODO - This args stuff is very bad, it really needs cleaned up..

                if (tag.getHref() != null) {
                    if (tag.getParamId() != null) {
                        String name = tag.getParamName();

                        if (name == null) {
                            name = "smartRow";
                        }

                        Object param =
                                this.lookup(pageContext, name,
                                            tag.getParamProperty(), tag.getParamScope(), true);

                        // flag to determine if we should use a ? or a &
                        int index = tag.getHref().indexOf('?');
                        String separator = "";
                        if (index == -1) {
                            separator = "?";
                        }
                        else {
                            separator = "&";
                        }
                        // if value has been chopped, add leftover as title
                        if (chopped) {
                            value = "<a href=\"" + tag.getHref() + separator + tag.getParamId()
                                    + "=" + param
                                    + (tag.getTarget() != null ? "\" target=\"" + tag.getTarget() : "")
                                    + "\" title=\"" + leftover + "\">" + value + "</a>";
                        }
                        else {
                            value = "<a href=\"" + tag.getHref() + separator + tag.getParamId()
                                    + "=" + param
                                    + (tag.getTarget() != null ? "\" target=\"" + tag.getTarget() : "")
                                    + "\">" + value + "</a>";
                        }
                    }
                    else {
                        // if value has been chopped, add leftover as title
                        if (chopped) {
                            value = "<a href=\"" + tag.getHref()
                                    + (tag.getTarget() != null ? "\" target=\"" + tag.getTarget() : "")
                                    + "\" title=\"" + leftover + "\">" + value + "</a>";
                        }
                        else {
                            value = "<a href=\"" + tag.getHref()
                                    + (tag.getTarget() != null ? "\" target=\"" + tag.getTarget() : "")
                                    + "\">" + value + "</a>";
                        }
                    }
                }

                // Ok, let's write this column's cell...

                /*
             if( rowcnt % 2 == 0 ) {
                   buf.append( this.value );
                } else {
                   buf.append( value );
                }
             */

                if (tag.getGroup() != null) {
                    try {
                        //System.err.println ( "Tag getGroup : " + tag.getGroup() );
                        buf.append(this.groupColumns(value.toString(), new Integer(tag.getGroup()).intValue()).toString());
                    }
                    catch (Exception e) {
                        System.err.println(e.getMessage());
                        e.printStackTrace(System.err);
                    }
                }
                else {
                    if (chopped && tag.getHref() == null) {

                        buf.append(value.toString().substring(0, value.toString().length() - 3));
                        buf.append("<a style=\"cursor: help;\" title=\"" + leftover +
                                   "\">");
                        buf.append(value.toString().substring(value.toString().length() - 3,
                                                              value.toString().length()) + "</a>");
                    }
                    else {
                        buf.append(value.toString());
                    }

                }

                buf.append("</td>\n");
            }

            // Special case, if they didn't provide any columns, then just spit out
            // the object's string representation to the table.

            if (this.columns.size() == 0) {
                buf.append("<td class=\"tableCell\">");
                buf.append(obj.toString());
                buf.append("</td>");
            }

            buf.append("</tr>\n");

            if (this.dec != null) {
                String rt = this.dec.finishRow();
                if (rt != null) buf.append(rt);
            }

            // paulsen:columndecorator
            for (int c = 0; c < columns.size(); c++)
                if (colDecorators[c] != null)
                    colDecorators[c].finishRow();


            rowcnt++;
        }

        if (rowcnt == 0) {
            buf.append("<tr class=\"tableRowOdd\">");
            buf.append("<td class=\"tableCell\" align=\"center\" colspan=\"" +
                       (this.columns.size() + 1) + "\">" +
                       prop.getProperty("basic.msg.empty_list") + "</td></tr>");
        }

        buf.append(this.getTableFooter().toString());

        buf.append("</table>\n");

        if (this.dec != null) this.dec.finish();
        this.dec = null;

        // paulsenj:columndecorator
        for (int c = 0; c < columns.size(); c++)
            if (colDecorators[c] != null)
                colDecorators[c].finish();

        return buf;
    }

    /**
     * This returns a table of data in CSV format
     */
    private StringBuffer getRawData(List viewableData) throws JspException {
        StringBuffer buf = new StringBuffer(8000);
        Iterator iterator = viewableData.iterator();
        // iterate through all the objects and dispaly 'em.

        int rowcnt = 0;
        boolean decorated = prop.getProperty("export.decorated").equalsIgnoreCase("true");

        if (prop.getProperty("export.amount").equalsIgnoreCase("list")) {
            iterator = getIterator(this.list);
        }

        // If they want the first line to be the column titles, then spit them out

        if (prop.getProperty("export.csv.include_header").equalsIgnoreCase("true")) {
            for (int i = 0; i < this.columns.size(); i++) {
                ColumnTag tag = (ColumnTag) this.columns.get(i);

                String header = tag.getTitle();
                if (header == null) {
                    header = StringUtil.toUpperCaseAt(tag.getProperty(), 0);
                }

                buf.append(header);
                if (this.columns.size() > (i + 1)) buf.append(",");
            }
            buf.append("\n");
        }

        while (iterator.hasNext()) {
            Object obj = iterator.next();

            if (decorated && this.dec != null) {
                String rt = this.dec.initRow(obj, rowcnt, rowcnt + this.getOffsetValue());
                if (rt != null) buf.append(rt);
            }

            pageContext.setAttribute("smartRow", obj);

            for (int i = 0; i < this.columns.size(); i++) {
                ColumnTag tag = (ColumnTag) this.columns.get(i);

                Object value = null;
                if (tag.getValue() != null) {
                    value = tag.getValue();
                }
                else {
                    if (tag.getProperty().equalsIgnoreCase("table_index")) {
                        value = String.valueOf(rowcnt);
                    }
                    else {
                        value = this.lookup(pageContext, "smartRow",
                                            tag.getProperty(), null, decorated);
                    }
                }

                if (value == null && tag.getNulls() == null) {
                    value = "";
                }

                if (tag.getDoubleQuote() != null) {
                    buf.append("\"" + value + "\"");
                }
                else {
                    buf.append(value.toString());
                }

                if (this.columns.size() > (i + 1)) {
                    /**
                     *  Do not add comma at the end of the line
                     */
                    buf.append(",");
                }
            }

            buf.append("\n");
            rowcnt++;
        }

        return buf;
    }

    /**
     * This returns a table of data in Excel format
     */
    private StringBuffer getExcelData(List viewableData) throws JspException {
        StringBuffer buf = new StringBuffer(8000);
        Iterator iterator = viewableData.iterator();

        int rowcnt = 0;
        boolean decorated = prop.getProperty("export.decorated").equalsIgnoreCase("true");

        if (prop.getProperty("export.amount").equalsIgnoreCase("list")) {
            /**
 Ok.  This is soon to be an unsupported tag.  But I can't
get the whole list when I export through excel, even
though I have set the export.amount to equal list.

I found that when we are building the view of "viewableData", it is shortening it to the page
size, even though it should be exporting the entire list.

This addition keeps all things the same, but makes sure
that the entire list is exported
             */
            Object obj = this.lookup(this.pageContext, this.name,
                                     this.property, this.scope, false);
            iterator = getIterator(obj);
        }

        // If they want the first line to be the column titles, then spit them out

        if (prop.getProperty("export.excel.include_header").equalsIgnoreCase("true")) {
            for (int i = 0; i < this.columns.size(); i++) {
                ColumnTag tag = (ColumnTag) this.columns.get(i);

                String header = tag.getTitle();
                if (header == null) {
                    header = StringUtil.toUpperCaseAt(tag.getProperty(), 0);
                }

                buf.append(header);
                if (this.columns.size() > (i + 1)) buf.append("\t");
            }
            buf.append("\n");
        }

        while (iterator.hasNext()) {
            Object obj = iterator.next();

            if (decorated && this.dec != null) {
                String rt = this.dec.initRow(obj, rowcnt, rowcnt + this.getOffsetValue());
                if (rt != null) buf.append(rt);
            }

            pageContext.setAttribute("smartRow", obj);

            for (int i = 0; i < this.columns.size(); i++) {
                ColumnTag tag = (ColumnTag) this.columns.get(i);

                Object value = null;
                if (tag.getValue() != null) {
                    value = tag.getValue();
                }
                else {
                    if (tag.getProperty().equalsIgnoreCase("table_index")) {
                        value = String.valueOf(rowcnt);
                    }
                    else {
                        value = this.lookup(pageContext, "smartRow",
                                            tag.getProperty(), null, decorated);
                    }
                }

                if (value == null && tag.getNulls() == null) {
                    value = "";
                }

                if (tag.getDoubleQuote() != null) {
                    buf.append("\"" + value + "\"");
                }
                else {
                    buf.append(value);
                }

                if (this.columns.size() > (i + 1)) {
                    /**
                     *  Do not add comma at the end of the line
                     */
                    buf.append("\t");
                }
            }

            rowcnt++;
            //buf.append( "<br>\n" );
            buf.append("\n");
        }

        return buf;
    }

    /**
     * This returns a table of data in XML format
     *
     * FIXME - this obviously needs cleaned up...
     */
    private StringBuffer getXMLData(List viewableData) throws JspException {
        StringBuffer buf = new StringBuffer(8000);
        Iterator iterator = viewableData.iterator();
        // iterate through all the objects and dispaly 'em.

        int rowcnt = 0;
        boolean decorated = prop.getProperty("export.decorated").equalsIgnoreCase("true");

        if (prop.getProperty("export.amount").equalsIgnoreCase("list")) {
            iterator = getIterator(this.list);
        }

        buf.append("<table>\n");
        while (iterator.hasNext()) {
            Object obj = iterator.next();

            if (decorated && this.dec != null) {
                String rt = this.dec.initRow(obj, rowcnt, rowcnt + this.getOffsetValue());
                if (rt != null) buf.append(rt);
            }

            pageContext.setAttribute("smartRow", obj);
            buf.append("<row>\n");

            for (int i = 0; i < this.columns.size(); i++) {
                ColumnTag tag = (ColumnTag) this.columns.get(i);

                Object value = null;
                if (tag.getValue() != null) {
                    value = tag.getValue();
                }
                else {
                    if (tag.getProperty().equalsIgnoreCase("table_index")) {
                        value = String.valueOf(rowcnt);
                    }
                    else {
                        value =
                                this.lookup(pageContext, "smartRow",
                                            tag.getProperty(), null, decorated);
                    }
                }

                if (value == null && tag.getNulls() == null) {
                    value = "";
                }

                // TODO - need to escape this or something...
                buf.append("<column>" + value + "</column>\n");
            }
            rowcnt++;
            buf.append("</row>\n");
        }
        buf.append("</table>\n");
        return buf;
    }

    // --------------------------------------------------------- Utility Methods

    /**
     * Generates the table header, including the first row of the table which
     * displays the titles of the various columns
     */
    private String getTableHeader() {
        StringBuffer buf = new StringBuffer(1000);
        int pagesizeValue = this.getPagesizeValue();

        HttpServletRequest req = (HttpServletRequest) this.pageContext.getRequest();
        String url = this.requestURI == null ?
            req.getRequestURI() : this.requestURI;

        String separator = url.indexOf('?') == -1 ? "?" : "&";

        StringBuffer pagingUrl = new StringBuffer(url).append(separator);
        if(this.sortColumn > -1) {
            pagingUrl.append("sort=").append(this.sortColumn).append("&");
        }
        if(req.getParameter("order")!=null) {
            pagingUrl.append("order=").append((this.sortOrder == TableTag.SORT_ORDER_ASCEENDING ?"asc":"dec")).append("&");
        }

        buf.append("<table");
        buf.append(this.getTableAttributes().toString());
        buf.append(">\n");

        // If they don't want the header shown for some reason, then stop here.

        if (!this.prop.getProperty("basic.show.header").equalsIgnoreCase("true")) {
            return buf.toString();
        }

        if (pagesizeValue != 0 && helper != null) {
            buf.append("<tr><td width=\"100%\" colspan=\"" + this.columns.size());
            buf.append("\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" ");
            buf.append("cellpadding=\"0\"><tr class=\"tableRowAction\">");
            buf.append("<td align=\"left\" valign=\"bottom\" class=\"");
            buf.append("tableCellAction\">");
            buf.append(helper.getSearchResultsSummary());
            buf.append("</td>\n");
            buf.append("<td valign=\"bottom\" align=\"right\" class=\"");
            buf.append("tableCellAction\">\n");
            buf.append(helper.getPageNavigationBar(pagingUrl.append("&page={0,number,#}").toString()));
            buf.append("</td>\n</tr></table></td></tr>\n");
        }

        buf.append("<tr class=\"tableRowHeader\">");

        int titleColumnsToSkip = 0;

        for (int i = 0; i < this.columns.size(); i++) {
            if (titleColumnsToSkip > 0) {
                titleColumnsToSkip--;
                continue;
            }

            ColumnTag tag = (ColumnTag) this.columns.get(i);

            buf.append("<th");
            if (tag.getWidth() != null)
                buf.append(" width=\"" + tag.getWidth() + "\"");

            if (tag.getAlign() != null)
                buf.append(" align=\"" + tag.getAlign() + "\"");

            int span = tag.getTitleColSpan();
            if (span > 1) {
                buf.append(" colspan=\"" + span + "\"");
                titleColumnsToSkip = span - 1;
            }

            if (tag.getHeaderStyleClass() != null) {
                buf.append(" class=\"" + tag.getHeaderStyleClass() + "\">");
            }
            else {
                buf.append(" class=\"tableCellHeader\">");
            }

            String header = tag.getTitle();
            if (header == null) {
                header = StringUtil.toUpperCaseAt(tag.getProperty(), 0);
            }

            if (tag.getSort() != null) {
//            if( this.sortColumn == 0 ) {
//               buf.append( "<a href=\"" + url + separator + "order=dec&sort=" + i + "\" class=\"tableCellHeader\">" );
//            } else {
                if (this.sortOrder == SORT_ORDER_ASCEENDING) {
                    buf.append("<a href=\"" + url + separator + "order=dec&sort=" + i + "\" class=\"tableCellHeader\">");
                }
                else {
                    buf.append("<a href=\"" + url + separator + "order=asc&sort=" + i + "\" class=\"tableCellHeader\">");
                }
//            }
                buf.append(header);
                buf.append("</a>");
            }
            else {
                buf.append(header);
            }

            buf.append("</th>\n");
        }

        // Special case, if they don't provide any columns, then just set
        // the title to a message, telling them to provide some...

        if (this.columns.size() == 0) {
            buf.append("<td><b>" + prop.getProperty("error.msg.no_column_tags") +
                       "</b></td>");
        }

        buf.append("</tr>\n");

        return buf.toString();
    }

    /**
     * Generates table footer with links for export commands.
     */
    private String getTableFooter() {
        StringBuffer buf = new StringBuffer(1000);
        int pagesizeValue = this.getPagesizeValue();

        HttpServletRequest req = (HttpServletRequest) this.pageContext.getRequest();
        String url = this.requestURI;
        if (url == null) {
            url = req.getRequestURI();
        }

        // flag to determine if we should use a ? or a &
        int index = url.indexOf('?');
        String separator = "";
        if (index == -1) {
            separator = "?";
        }
        else {
            separator = "&";
        }

        // Put the page stuff there if it needs to be there...

        if (this.prop.getProperty("paging.banner.placement").equalsIgnoreCase("both") ||
                this.prop.getProperty("paging.banner.placement").equalsIgnoreCase("bottom")) {
            if (pagesizeValue != 0 && this.helper != null) {
                buf.append("<tr><td width=\"100%\" colspan=\"" + this.columns.size());
                buf.append("\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" ");
                buf.append("cellpadding=\"0\"><tr class=\"tableRowAction\">");
                buf.append("<td align=\"left\" valign=\"bottom\" class=\"");
                buf.append("tableCellAction\">");
                buf.append(this.helper.getSearchResultsSummary());
                buf.append("</td>\n");
                buf.append("<td valign=\"bottom\" align=\"right\" class=\"");
                buf.append("tableCellAction\">\n");
                buf.append(this.helper.getPageNavigationBar(url + separator + "page={0}"));
                buf.append("</td>\n</tr></table></td></tr>\n");
            }
        }

        String qString = req.getQueryString();
        if (qString != null && !qString.equals("")) {
            url += separator + URLEncoder.encode(qString) + "&";
        }
        else {
            url += separator;
        }

        if (this.export != null) {
            buf.append("<tr><td align=\"left\" width=\"100%\" colspan=\"" + this.columns.size() + "\">");
            buf.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" ");
            buf.append("cellpadding=0><tr class=\"tableRowAction\">");
            buf.append("<td align=\"left\" valign=\"bottom\" class=\"");
            buf.append("tableCellAction\">");

            // Figure out what formats they want to export, make up a little string

            String formats = "";
            if (prop.getProperty("export.csv").equalsIgnoreCase("true")) {
                formats += "<a href=\"" + url + "exportType=1\">" +
                        prop.getProperty("export.csv.label") + "</a>\n";
            }

            if (prop.getProperty("export.excel").equalsIgnoreCase("true")) {
                if (!formats.equals("")) formats += prop.getProperty("export.banner.sepchar");
                formats += "<a href=\"" + url + "exportType=2\">" +
                        prop.getProperty("export.excel.label") + "</a>\n";
            }

            if (prop.getProperty("export.xml").equalsIgnoreCase("true")) {
                if (!formats.equals("")) formats += prop.getProperty("export.banner.sepchar");
                formats += "<a href=\"" + url + "exportType=3\">" +
                        prop.getProperty("export.xml.label") + "</a>\n";
            }

            Object[] objs = {formats};
            buf.append(MessageFormat.format(prop.getProperty("export.banner"), objs));
            buf.append("</td></tr>");
            buf.append("</table>\n");
            buf.append("</td></tr>");
        }

        return buf.toString();
    }

    /**
     * This takes a cloumn value and grouping index as the argument.
     * It then groups the column and returns the appropritate string back to the
     * caller.
     */
    private String groupColumns(String value, int group) {

        if ((group == 1) & this.nextRow.size() > 0) { // we are at the begining of the next row so copy the contents from .
            // nextRow to the previousRow.
            this.previousRow.clear();
            this.previousRow.putAll(nextRow);
            this.nextRow.clear();
        }

        if (!this.nextRow.containsKey(new Integer(group))) {
            // Key not found in the nextRow so adding this key now... remember all the old values.
            this.nextRow.put(new Integer(group), new String(value));
        }

        /*
         *  Start comparing the value we received, along with the grouping index.
         *  if no matching value is found in the previous row then return the value.
         *  if a matching value is found then this value should not get printed out
         *  so return ""
         */
        if (this.previousRow.containsKey(new Integer(group))) {
            for (int x = 1; x <= group; x++) {

                if (!(this.previousRow.get(new Integer(x))).equals(
                        ((String) this.nextRow.get(new Integer(x))
                         ))) {
                    // no match found so return this value back to the caller.
                    return value;
                }
            }
        }

        /*
         * This is used, for when there is no data in the previous row,
         * It gets used only the firt time.
         */
        if (this.previousRow.size() == 0) {
            return value;
        }

        // There is corresponding value in the previous row so this value need not be printed, return ""
        return ""; // we are done !.
    }

/*
   private void clearHashForColumGroupings()
   {
      // clear the hashes so that the hash does not have any reside from previous reports and not cause any
      // problems.
      this.previousRow.clear();
      this.nextRow.clear();
      System.err.println( "Cleared hash" );

   }
*/

    /**
     * Takes all the table pass-through arguments and bundles them up as a
     * string that gets tacked on to the end of the table tag declaration.<p>
     *
     * <p>Note that we override some default behavior, specifically:</p>
     *
     * <ul>
     *     <li>width - defaults to 100% if not provided</li>
     *     <li>border - defaults to 0 if not provided</li>
     *     <li>cellspacing - defaults to 1 if not provided</li>
     *     <li>cellpadding - defaults to 2 if not provided</li>
     * </ul>
     */
    private String getTableAttributes() {
        StringBuffer results = new StringBuffer();

        if (this.name != null) {
            results.append(" id=\"");
            results.append(this.name);
            results.append("\"");
        }

        if (this.clazz != null) {
            results.append(" class=\"");
            results.append(this.clazz);
            results.append("\"");
        }
        else {
            results.append(" class=\"table\"");
        }

        if (this.width != null) {
            results.append(" width=\"");
            results.append(this.width);
            results.append("\"");
        }
        else {
            results.append(" width=\"100%\"");
        }

        if (this.border != null) {
            results.append(" border=\"");
            results.append(this.border);
            results.append("\"");
        }
        else {
            results.append(" border=\"0\"");
        }

        if (this.cellspacing != null) {
            results.append(" cellspacing=\"");
            results.append(this.cellspacing);
            results.append("\"");
        }
        else {
            results.append(" cellspacing=\"0\"");
        }

        if (this.cellpadding != null) {
            results.append(" cellpadding=\"");
            results.append(this.cellpadding);
            results.append("\"");
        }
        else {
            results.append(" cellpadding=\"2\"");
        }

        if (this.align != null) {
            results.append(" align=\"");
            results.append(this.align);
            results.append("\"");
        }

        if (this.background != null) {
            results.append(" background=\"");
            results.append(this.background);
            results.append("\"");
        }

        if (this.bgcolor != null) {
            results.append(" bgcolor=\"");
            results.append(this.bgcolor);
            results.append("\"");
        }

        if (this.frame != null) {
            results.append(" frame=\"");
            results.append(this.frame);
            results.append("\"");
        }

        if (this.height != null) {
            results.append(" height=\"");
            results.append(this.height);
            results.append("\"");
        }

        if (this.hspace != null) {
            results.append(" hspace=\"");
            results.append(this.hspace);
            results.append("\"");
        }

        if (this.rules != null) {
            results.append(" rules=\"");
            results.append(this.rules);
            results.append("\"");
        }

        if (this.summary != null) {
            results.append(" summary=\"");
            results.append(this.summary);
            results.append("\"");
        }

        if (this.vspace != null) {
            results.append(" vspace=\"");
            results.append(this.vspace);
            results.append("\"");
        }

        return results.toString();
    }

    /**
     * This functionality is borrowed from struts, but I've removed some
     * struts specific features so that this tag can be used both in a
     * struts application, and outside of one.
     *
     * Locate and return the specified bean, from an optionally specified
     * scope, in the specified page context.  If no such bean is found,
     * return <code>null</code> instead.
     *
     * @param pageContext Page context to be searched
     * @param name Name of the bean to be retrieved
     * @param scope Scope to be searched (page, request, session, application)
     *  or <code>null</code> to use <code>findAttribute()</code> instead
     *
     * @exception JspException if an invalid scope name is requested
     */
    public Object lookup(PageContext pageContext, String name, String scope)
            throws JspException {

        Object bean = null;
        if (scope == null)
            bean = pageContext.findAttribute(name);
        else if (scope.equalsIgnoreCase("page"))
            bean = pageContext.getAttribute(name, PageContext.PAGE_SCOPE);
        else if (scope.equalsIgnoreCase("request"))
            bean = pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);
        else if (scope.equalsIgnoreCase("session"))
            bean = pageContext.getAttribute(name, PageContext.SESSION_SCOPE);
        else if (scope.equalsIgnoreCase("application"))
            bean = pageContext.getAttribute(name, PageContext.APPLICATION_SCOPE);
        else {
            Object[] objs = {name, scope};
            String msg =
                    MessageFormat.format(prop.getProperty("error.msg.cant_find_bean"), objs);

            throw new JspException(msg);
        }

        return (bean);
    }

    /**
     * This functionality is borrowed from struts, but I've removed some
     * struts specific features so that this tag can be used both in a
     * struts application, and outside of one.
     *
     * Locate and return the specified property of the specified bean, from
     * an optionally specified scope, in the specified page context.
     *
     * @param pageContext Page context to be searched
     * @param name Name of the bean to be retrieved
     * @param property Name of the property to be retrieved, or
     *  <code>null</code> to retrieve the bean itself
     * @param scope Scope to be searched (page, request, session, application)
     *  or <code>null</code> to use <code>findAttribute()</code> instead
     *
     * @exception JspException if an invalid scope name is requested
     * @exception JspException if the specified bean is not found
     * @exception JspException if accessing this property causes an
     *  IllegalAccessException, IllegalArgumentException,
     *  InvocationTargetException, or NoSuchMethodException
     */
    public Object lookup(PageContext pageContext, String name,
                         String property, String scope, boolean useDecorator)
            throws JspException {
        if (useDecorator && this.dec != null) {
            // First check the decorator, and if it doesn't return a value
            // then check the inner object...

            try {
                if (property == null) return this.dec;
                return (PropertyUtils.getProperty(this.dec, property));
            }
            catch (IllegalAccessException e) {
                Object[] objs = {property, this.dec};
                throw new JspException(
                        MessageFormat.format(prop.getProperty("error.msg.illegal_access_exception"), objs));
            }
            catch (InvocationTargetException e) {
                Object[] objs = {property, this.dec};
                throw new JspException(
                        MessageFormat.format(prop.getProperty("error.msg.invocation_target_exception"), objs));
            }
            catch (NoSuchMethodException e) {
                // We ignore this puppy and just fall down to the bean lookup below.
            }
        }

        // Look up the requested bean, and return if requested
        Object bean = this.lookup(pageContext, name, scope);
        if (property == null) return (bean);

        if (bean == null) {
            Object[] objs = {name, scope};
            throw new JspException(
                    MessageFormat.format(prop.getProperty("error.msg.cant_find_bean"), objs));
        }

        // Locate and return the specified property

        try {
            return (PropertyUtils.getProperty(bean, property));
        }
        catch (IllegalAccessException e) {
            Object[] objs = {property, name};
            throw new JspException(
                    MessageFormat.format(prop.getProperty("error.msg.illegal_access_exception"), objs));
        }
        catch (InvocationTargetException e) {
            Object[] objs = {property, name};
            throw new JspException(
                    MessageFormat.format(prop.getProperty("error.msg.invocation_target_exception"), objs));
        }
        catch (NoSuchMethodException e) {
            Object[] objs = {property, name};
            throw new JspException(
                    MessageFormat.format(prop.getProperty("error.msg.nosuchmethod_exception"), objs));
        }
    }

    /**
     * If the user has specified a decorator, then this method takes care of
     * creating the decorator (and checking to make sure it is a subclass of
     * the TableDecorator object).  If there are any problems loading the
     * decorator then this will throw a JspException which will get propogated
     * up the page.
     */
    private Decorator loadDecorator()
            throws JspException {
        if (this.decorator == null || this.decorator.length() == 0) return null;

        try {
            Class c = Class.forName(this.decorator);

            // Probably should really be TableDecorator, need to move towards that
            // in the future, if I did it now, it would break stuff, and I have
            // a feeling I'm going to make changes to the decorator more in a little
            // bit, so I would rather just do it all at once...

            if (!Class.forName("org.apache.taglibs.display.Decorator").isAssignableFrom(c)) {
                throw new JspException(prop.getProperty("error.msg.invalid_decorator"));
            }

            return (Decorator) c.newInstance();
        }
        catch (Exception e) {
            throw new JspException(e.toString());
        }
    }

    /**
     * paulsenj:columndecorator
     * If the user has specified a column decorator, then this method takes care of
     * creating the decorator (and checking to make sure it is a subclass of
     * the ColumnDecorator object).  If there are any problems loading the
     * decorator then this will throw a JspException which will get propogated
     * up the page.
     */
    private ColumnDecorator loadColumnDecorator(String columnDecorator) throws JspException {
        if (columnDecorator == null || columnDecorator.length() == 0)
            return null;

        try {
            Class c = Class.forName(columnDecorator);

            // paulsenj - removed 'jakarta' from class name
            if (!Class.forName("org.apache.taglibs.display.ColumnDecorator").isAssignableFrom(c))
                throw new JspException("column decorator class is not a subclass of ColumnDecorator.");

            return (ColumnDecorator) c.newInstance();
        }
        catch (Exception e) {
            throw new JspException(e.toString());
        }
    }

    /**
     * This takes the string that is passed in, and "auto-links" it, it turns
     * email addresses into hyperlinks, and also turns things that looks like
     * URLs into hyperlinks as well.  The rules are currently very basic, In
     * Perl regex lingo...
     *
     * <ul>
     *     <li>Email:  \b\S+\@[^\@\s]+\b</li>
     *     <li>URL:    (http|https|ftp)://\S+\b</li>
     * </ul>
     *
     * <p>Doing this via brute-force since we don't want to be dependent on a
     * third party regex package.</p>
     */
    private String autoLink(String data) {
        String work = new String(data);
        int index = -1;
        String results = "";

        if (data == null || data.length() == 0) return data;

        // First check for email addresses.

        while ((index = work.indexOf("@")) != -1) {
            int start = 0;
            int end = work.length() - 1;

            // scan backwards...
            for (int i = index; i >= 0; i--) {
                if (Character.isWhitespace(work.charAt(i))) {
                    start = i + 1;
                    break;
                }
            }

            // scan forwards...
            for (int i = index; i <= end; i++) {
                if (Character.isWhitespace(work.charAt(i))) {
                    end = i - 1;
                    break;
                }
            }

            String email = work.substring(start, (end - start + 1));
            results = results + work.substring(0, start) +
                    "<a href=\"mailto:" + email + "\">" + email + "</a>";

            if (end == work.length()) {
                work = "";
            }
            else {
                work = work.substring(end + 1);
            }
        }

        work = results + work;
        results = "";

        // Now check for urls...

        while ((index = work.indexOf("http://")) != -1) {
            int end = work.length() - 1;

            // scan forwards...
            for (int i = index; i <= end; i++) {
                if (Character.isWhitespace(work.charAt(i))) {
                    end = i - 1;
                    break;
                }
            }

            String url = work.substring(index, (end - index + 1));

            results = results + work.substring(0, index) +
                    "<a href=\"" + url + "\">" + url + "</a>";

            if (end == work.length()) {
                work = "";
            }
            else {
                work = work.substring(end + 1);
            }
        }

        results += work;
        return results;
    }

    /**
     * Called by the setProperty tag to override some default behavior or text
     * string.
     */
    public void setProperty(String name, String value) {
        this.prop.setProperty(name, value);
    }

    /**
     * This sets up all the default properties for the table tag.
     */
    private void loadDefaultProperties() {
        this.prop = new Properties();

        prop.setProperty("basic.show.header", "true");
        prop.setProperty("basic.msg.empty_list", "Nothing found to display");

        prop.setProperty("sort.behavior", "page");

        prop.setProperty("export.banner", "Export options: {0}");
        prop.setProperty("export.banner.sepchar", " | ");
        prop.setProperty("export.csv", "true");
        prop.setProperty("export.csv.label", "CSV");
        prop.setProperty("export.csv.mimetype", "text/csv");
        prop.setProperty("export.csv.include_header", "false");
        prop.setProperty("export.excel", "true");
        prop.setProperty("export.excel.label", "Excel");
        prop.setProperty("export.excel.mimetype", "application/vnd.ms-excel");
        prop.setProperty("export.excel.include_header", "false");
        prop.setProperty("export.xml", "true");
        prop.setProperty("export.xml.label", "XML");
        prop.setProperty("export.xml.mimetype", "text/xml");
        prop.setProperty("export.amount", "page");
        prop.setProperty("export.decorated", "true");

        prop.setProperty("paging.banner.placement", "top");
        prop.setProperty("paging.banner.item_name", "item");
        prop.setProperty("paging.banner.items_name", "items");
        prop.setProperty("paging.banner.no_items_found", "No {0} found.");
        prop.setProperty("paging.banner.one_item_found", "1 {0} found.");
        prop.setProperty("paging.banner.all_items_found", "{0} {1} found, displaying all {2}");
        prop.setProperty("paging.banner.some_items_found", "{0} {1} found, displaying {2} to {3}");
        prop.setProperty("paging.banner.include_first_last", "false");
        prop.setProperty("paging.banner.first_label", "First");
        prop.setProperty("paging.banner.last_label", "Last");
        prop.setProperty("paging.banner.prev_label", "Prev");
        prop.setProperty("paging.banner.next_label", "Next");
        prop.setProperty("paging.banner.group_size", "8");

        prop.setProperty("error.msg.cant_find_bean", "Could not find bean {0} in scope {1}");
        prop.setProperty("error.msg.invalid_bean", "The bean that you gave me is not a Collection I understand: {0}");
        prop.setProperty("error.msg.no_column_tags", "Please provide column tags.");
        prop.setProperty("error.msg.illegal_access_exception", "IllegalAccessException trying to fetch property {0} on bean {1}");
        prop.setProperty("error.msg.invocation_target_exception", "InvocationTargetException trying to fetch property {0} on bean {1}");
        prop.setProperty("error.msg.nosuchmethod_exception", "NoSuchMethodException trying to fetch property {0} on bean {1}");
        prop.setProperty("error.msg.invalid_decorator", "Decorator class is not a subclass of TableDecorator");
        prop.setProperty("error.msg.invalid_page", "Invalid page ({0}) provided, value should be between 1 and {1}");
    }
}

