/**
 * $Id$
 *
 * Todo:
 *   - provide filters in some way?  Instead of just getting some bit of data
 *     also provide a way to feed that data through some other object that
 *     will reformat it in some way (like converting dates to another format)
 *   - update documentation, HTML column attributes are not set.
 *   - specify groupings.
 *   - error checking, value or property must be set.
 *
 */

package org.apache.taglibs.display;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * This tag works hand in hand with the SmartListTag to display a list of
 * objects.  This describes a column of data in the SmartListTag.  There can
 * be any (reasonable) number of columns that make up the list.
 *
 * <p>This tag does no work itself, it is simply a container of information.  The
 * TableTag does all the work based on the information provided in the
 * attributes of this tag.
 * <p>
 * Usage:</p><p>
 *   &lt;display:column property="title"
 *	 	           title="College Title" width="33%"
 *               href="/osiris/pubs/college/edit.page"
 *		           paramId="OID"
 *               paramProperty="OID" /&gt;
 * </p><p>
 * Attributes:<pre>
 *
 *   property       - the property method that is called to retrieve the
 *                    information to be displayed in this column.  This method
 *                    is called on the current object in the iteration for
 *                    the given row.  The property format is in typical struts
 *                    format for properties (required)
 *
 *   title          - the title displayed for this column.  if this is omitted
 *                    then the property name is used for the title of the column
 *                    (optional)
 *
 *   nulls          - by default, null values don't appear in the list, by
 *                    setting viewNulls to 'true', then null values will
 *                    appear as "null" in the list (mostly useful for debugging)
 *                    (optional)
 *
 *   width          - the width of the column (gets passed along to the html
 *                    td tag). (optional)
 *
 *   group          - the grouping level (starting at 1 and incrementing) of
 *                    this column (indicates if successive contain the same
 *                    values, then they should not be displayed).  The level
 *                    indicates that if a lower level no longer matches, then
 *                    the matching for this higher level should start over as
 *                    well. If this attribute is not included, then no grouping
 *                    is performed. (optional)
 *
 *   decorator      - a class that should be used to "decorate" the underlying
 *                    object being displayed. If a decorator is specified for
 *                    the entire table, then this decorator will decorate that
 *                    decorator. (optional)
 *
 *   autolink       - if set to true, then any email addresses and URLs found
 *                    in the content of the column are automatically converted
 *                    into a hypertext link.
 *
 *   href           - if this attribute is provided, then the data that is
 *                    shown for this column is wrapped inside a &lt;a href&gt;
 *                    tag with the url provided through this attribute.
 *                    Typically you would use this attribute along with one
 *                    of the struts-like param attributes below to create
 *                    a dynamic link so that each row creates a different
 *                    URL based on the data that is being viewed. (optional)
 *
 *   paramId        - The name of the request parameter that will be dynamically
 *                    added to the generated href URL. The corresponding value
 *                    is defined by the paramProperty and (optional) paramName
 *                    attributes, optionally scoped by the paramScope attribute.
 *                    (optional)
 *
 *   paramName      - The name of a JSP bean that is a String containing the
 *                    value for the request parameter named by paramId (if
 *                    paramProperty is not specified), or a JSP bean whose
 *                    property getter is called to return a String (if
 *                    paramProperty is specified). The JSP bean is constrained
 *                    to the bean scope specified by the paramScope property,
 *                    if it is specified.  If paramName is omitted, then it is
 *                    assumed that the current object being iterated on is the
 *                    target bean. (optional)
 *
 *   paramProperty  - The name of a property of the bean specified by the
 *                    paramName attribute (or the current object being iterated
 *                    on if paramName is not provided), whose return value must
 *                    be a String containing the value of the request parameter
 *                    (named by the paramId attribute) that will be dynamically
 *                    added to this href URL. (optional)
 *
 *   paramScope     - The scope within which to search for the bean specified by
 *                    the paramName attribute. If not specified, all scopes are
 *                    searched.  If paramName is not provided, then the current
 *                    object being iterated on is assumed to be the target bean.
 *                    (optional)
 *
 *
 *   maxLength      - If this attribute is provided, then the column's displayed
 *                    is limited to this number of characters.  An elipse (...)
 *                    is appended to the end if this column is linked, and the
 *                    user can mouseover the elipse to get the full text.
 *                    (optional)
 *
 *   maxWords       - If this attribute is provided, then the column's displayed
 *                    is limited to this number of words.  An elipse (...) is
 *                    appended to the end if this column is linked, and the user
 *                    can mouseover the elipse to get the full text. (optional)
 *
 *   titleColSpan    - the number of columns that the title should span. Default is 1.
 *</pre></p>
 */
public class ColumnTag extends BodyTagSupport implements Cloneable {
    private String property;
    private String title;
    private String nulls;
    private String sort;
    private String autolink;
    private String group; /* If this property is set then the values have to be grouped */

    private String href;
    private String target;
    private String paramId;
    private String paramName;
    private String paramProperty;
    private String paramScope;
    private int maxLength;
    private int maxWords;
    private int titleColSpan = 1;

    private String width;
    private String align;
    private String background;
    private String bgcolor;
    private String height;
    private String nowrap;
    private String valign;
    private String styleClass;
    private String headerStyleClass;

    private String value;

    private String doubleQuote;

    private String decorator;

    // -------------------------------------------------------- Accessor methods

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNulls() {
        return nulls;
    }

    public void setNulls(String nulls) {
        this.nulls = nulls;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getAutolink() {
        return autolink;
    }

    public void setAutolink(String autolink) {
        this.autolink = autolink;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamProperty() {
        return paramProperty;
    }

    public void setParamProperty(String paramProperty) {
        this.paramProperty = paramProperty;
    }

    public String getParamScope() {
        return paramScope;
    }

    public void setParamScope(String paramScope) {
        this.paramScope = paramScope;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxWords() {
        return maxWords;
    }

    public void setMaxWords(int maxWords) {
        this.maxWords = maxWords;
    }

    public int getTitleColSpan() {
        return titleColSpan;
    }

    public void setTitleColSpan(int titleColSpan) {
        this.titleColSpan = titleColSpan;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getNowrap() {
        return nowrap;
    }

    public void setNowrap(String nowrap) {
        this.nowrap = nowrap;
    }

    public String getValign() {
        return valign;
    }

    public void setValign(String valign) {
        this.valign = valign;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getHeaderStyleClass() {
        return headerStyleClass;
    }

    public void setHeaderStyleClass(String headerStyleClass) {
        this.headerStyleClass = headerStyleClass;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDoubleQuote() {
        return doubleQuote;
    }

    public void setDoubleQuote(String doubleQuote) {
        this.doubleQuote = doubleQuote;
    }

    public String getDecorator() {
        return decorator;
    }

    public void setDecorator(String decorator) {
        this.decorator = decorator;
    }

    // --------------------------------------------------------- Tag API methods

    /**
     * Passes attribute information up to the parent TableTag.
     *
     * <p>When we hit the end of the tag, we simply let our parent (which better
     * be a TableTag) know what the user wants to do with this column.
     * We do that by simple registering this tag with the parent.  This tag's
     * only job is to hold the configuration information to describe this
     * particular column.  The TableTag does all the work.</p>
     *
     * @throws JspException if this tag is being used outside of a
     *    &lt;display:list...&gt; tag.
     */
    public int doEndTag() throws JspException {
        Object parent = this.getParent();

        boolean foundTableTag = false;

        while (!foundTableTag) {
            if (parent == null) {
                throw new JspException("Can not use column tag outside of a TableTag.");
            }

            if (!(parent instanceof TableTag)) {
                if (parent instanceof TagSupport)
                    parent = ((TagSupport) parent).getParent();
                else
                    throw new JspException("Can not use column tag outside of a TableTag.");
            }
            else
                foundTableTag = true;
        }

        // Need to clone the ColumnTag before passing it to the TableTag as
        // the ColumnTags can be reused by some containers, and since we are
        // using the ColumnTags as basically containers of data, we need to
        // save the original values, and not the values that are being changed
        // as the tag is being reused...

        ColumnTag copy = this;
        try {
            copy = (ColumnTag) this.clone();
        }
        catch (CloneNotSupportedException e) {
        } // shouldn't happen...

        ((TableTag) parent).addColumn(copy);

        return super.doEndTag();
    }


    /**
     * Takes all the column pass-through arguments and bundles them up as a
     * string that gets tacked on to the end of the td tag declaration.
     */
    protected String getCellAttributes() {
        StringBuffer results = new StringBuffer();

        if (this.styleClass != null) {
            results.append(" class=\"");
            results.append(this.styleClass);
            results.append("\"");
        }
        else {
            results.append(" class=\"tableCell\"");
        }

        if (this.width != null) {
            results.append(" width=\"");
            results.append(this.width);
            results.append("\"");
        }

        if (this.align != null) {
            results.append(" align=\"");
            results.append(this.align);
            results.append("\"");
        }
        else {
            results.append(" align=\"left\"");
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

        if (this.height != null) {
            results.append(" height=\"");
            results.append(this.height);
            results.append("\"");
        }

        if (this.nowrap != null) {
            results.append(" nowrap");
        }

        if (this.valign != null) {
            results.append(" valign=\"");
            results.append(this.valign);
            results.append("\"");
        }
        else {
            results.append(" valign=\"top\"");
        }

        return results.toString();
    }

    /**
     * Returns a String representation of this Tag that is suitable for
     * printing while debugging.  The format of the string is subject to change
     * but it currently:
     *
     * <p><code>SmartColumnTag([title],[property],[href])</code></p>
     *
     * <p>Where the placeholders in brackets are replaced with their appropriate
     * instance variables.</p>
     */
    public String toString() {
        return "SmartColumnTag(" + title + "," + property + "," + href + ")";
    }
}