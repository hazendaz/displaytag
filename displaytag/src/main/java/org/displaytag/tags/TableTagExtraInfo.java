package org.displaytag.tags;

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
     * suffix added to id for saving row number in pagecontext
     */
    public static final String ROWNUM_SUFFIX = "_rowNum";

    /**
     * Variabiles TableTag makes available in the pageContext
     * @param pData TagData
     * @return VariableInfo[]
     * @see javax.servlet.jsp.tagext.TagData
     * @see javax.servlet.jsp.tagext.VariableInfo
     */
    public VariableInfo[] getVariableInfo(TagData pData)
    {
        // if id is null don't define anything
        if (pData.getId() == null)
        {
            return new VariableInfo[] {
            };
        }

        // current row
        VariableInfo lInfo1 = new VariableInfo(pData.getId(), "java.lang.Object", true, VariableInfo.NESTED);

        // current row number
        VariableInfo lInfo2 =
            new VariableInfo(pData.getId() + ROWNUM_SUFFIX, "java.lang.Integer", true, VariableInfo.NESTED);

        return new VariableInfo[] { lInfo1, lInfo2 };
    }

}
