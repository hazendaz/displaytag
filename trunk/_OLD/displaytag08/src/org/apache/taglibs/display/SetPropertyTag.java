/*
 * $Id$
 *
 * Todo
 *   - implementation
 *   - documentation (javadoc, examples, etc...)
 *   - junit test cases
 */

package org.apache.taglibs.display;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * One line description of what this class does.
 *
 * More detailed class description, including examples of usage if applicable.
 */
public class SetPropertyTag extends BodyTagSupport implements Cloneable {
    private String name;
    private String value;

    public void setName(String v) {
        this.name = v;
    }

    public void setValue(String v) {
        this.value = v;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    // --------------------------------------------------------- Tag API methods

    /**
     * Passes attribute information up to the parent TableTag.<p>
     *
     * When we hit the end of the tag, we simply let our parent (which better
     * be a TableTag) know what the user wants to change a property value, and
     * we pass the name/value pair that the user gave us, up to the parent
     *
     * @throws javax.servlet.jsp.JspException if this tag is being used outside of a
     *    <display:list...> tag.
     */
    public int doEndTag() throws JspException {
        Object parent = this.getParent();

        if (parent == null) {
            throw new JspException("Can not use column tag outside of a " +
                                   "TableTag. Invalid parent = null");
        }

        if (!(parent instanceof TableTag)) {
            throw new JspException("Can not use column tag outside of a " +
                                   "TableTag. Invalid parent = " +
                                   parent.getClass().getName());
        }

        ((TableTag) parent).setProperty(this.name, this.value);

        return super.doEndTag();
    }
}