package org.displaytag.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;

import org.displaytag.exception.TagStructureException;

/**
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class SetPropertyTag extends BodyTagSupport implements Cloneable
{

    /**
     * property name.
     */
    private String name;

    /**
     * property value.
     */
    private String value;

    /**
     * Sets the name of the property.
     * @param propertyName String
     */
    public void setName(String propertyName)
    {
        this.name = propertyName;
    }

    /**
     * Sets the value of the property.
     * @param propertyValue String
     */
    public void setValue(String propertyValue)
    {
        this.value = propertyValue;
    }

    /**
     * Passes attribute information up to the parent TableTag.
     * <p>
     * When we hit the end of the tag, we simply let our parent (which better be a TableTag) know what the user wants
     * to change a property value, and we pass the name/value pair that the user gave us, up to the parent
     * </p>
     * @return <code>TagSupport.EVAL_PAGE</code>
     * @throws TagStructureException if no parent table tag is found
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws TagStructureException
    {

        TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

        if (tableTag == null)
        {
            throw new TagStructureException(getClass(), "property", "table");
        }

        tableTag.setProperty(this.name, this.value);

        return EVAL_PAGE;
    }

}
