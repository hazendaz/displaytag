package org.apache.taglibs.display;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * SetProperty tag - allows you to change a tables properties.
 *
 * @version $Revision$
 */
public class SetPropertyTag extends BodyTagSupport {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Passes property information up to the parent TableTag.
     *
     * <p>When we hit the end of the tag, we simply let our parent (which better
     * be a TableTag) know that the user wants to change a property value, and
     * we pass the name/value pair that the user gave us, up to the parent.
     *
     * @throws javax.servlet.jsp.JspException if this tag is being used outside of a
     *    &lt;display:table...&gt; tag.
     */
    public int doEndTag() throws JspException {
        Object parent = getParent();

        if (parent == null) {
            throw new JspException("Can not use setProperty tag outside of a " +
                                   "TableTag. Invalid parent = null");
        }
        if (!(parent instanceof TableTag)) {
            throw new JspException("Can not use setProperty tag outside of a " +
                                   "TableTag. Invalid parent = " + parent.getClass().getName());
        }

        ((TableTag) parent).setProperty(name, value);

        return super.doEndTag();
    }
}