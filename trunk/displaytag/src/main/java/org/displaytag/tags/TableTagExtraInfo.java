package org.displaytag.tags;

import javax.servlet.jsp.tagext.TagAttributeInfo;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class TableTagExtraInfo extends TagExtraInfo
{

    /**
     * Suffix added to id for saving row number in pagecontext.
     */
    public static final String ROWNUM_SUFFIX = "_rowNum";

    /**
     * Variables TableTag makes available in the pageContext.
     *
     * @param data TagData
     * @return VariableInfo[]
     * @see javax.servlet.jsp.tagext.TagData
     * @see javax.servlet.jsp.tagext.VariableInfo
     */
    public VariableInfo[] getVariableInfo(TagData data)
    {

        Object tagId = data.getAttributeString(TagAttributeInfo.ID);

        // if id is null don't define anything
        if (tagId == null)
        {
            return new VariableInfo[] {
            };
        }

        // current row
        VariableInfo info1 = new VariableInfo(tagId.toString(), "java.lang.Object", true, VariableInfo.NESTED);

        // current row number
        VariableInfo info2 =
            new VariableInfo(tagId.toString() + ROWNUM_SUFFIX, "java.lang.Integer", true, VariableInfo.NESTED);

        return new VariableInfo[] { info1, info2 };
    }

}
