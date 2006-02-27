package org.displaytag.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.displaytag.exception.TagStructureException;

/**
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class SetPropertyTag extends BodyTagSupport implements Cloneable
{

    /**
     * Field mName
     */
    private String mName;

    /**
     * Field mValue
     */
    private String mValue;

    /**
     * Method setName
     * @param pName String
     */
    public void setName(String pName)
    {
        mName = pName;
    }

    /**
     * Method setValue
     * @param pValue String
     */
    public void setValue(String pValue)
    {
        mValue = pValue;
    }

    /**
     * Method getName
     * @return String
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Method getValue
     * @return String
     */
    public String getValue()
    {
        return mValue;
    }

    /**
     * Passes attribute information up to the parent TableTag.<p>
     *
     * When we hit the end of the tag, we simply let our parent (which better
     * be a TableTag) know what the user wants to change a property value, and
     * we pass the name/value pair that the user gave us, up to the parent
     *
     * @return int
     * @throws JspException if no parent table tag is found
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     **/
    public int doEndTag() throws JspException
    {

        TableTag lTableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

        if (lTableTag == null)
        {
            throw new TagStructureException(getClass(), "property", "table");
        }

        lTableTag.setProperty(mName, mValue);

        return super.doEndTag();
    }

}
