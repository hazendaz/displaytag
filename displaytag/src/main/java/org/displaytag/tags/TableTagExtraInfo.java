package org.displaytag.tags;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.TagAttributeInfo;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;


/**
 * TEI for TableTag, defines 3 variables.
 * <ul>
 * <li>table id = object contained in row</li>
 * <li>table id + ROWNUM_SUFFIX = row number</li>
 * </ul>
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TableTagExtraInfo extends TagExtraInfo
{

    /**
     * Suffix added to id for saving row number in pagecontext.
     */
    public static final String ROWNUM_SUFFIX = "_rowNum"; //$NON-NLS-1$

    /**
     * Variables TableTag makes available in the pageContext.
     * @param data TagData
     * @return VariableInfo[]
     * @see javax.servlet.jsp.tagext.TagData
     * @see javax.servlet.jsp.tagext.VariableInfo
     */
    public VariableInfo[] getVariableInfo(TagData data)
    {
        List variables = new ArrayList(2);

        Object idObj = data.getAttribute(TagAttributeInfo.ID);

        // avoid errors, but "id" and "id_rownum" will not be defined
        if (idObj != TagData.REQUEST_TIME_VALUE && idObj != null)
        {
            String tagId = data.getAttributeString(TagAttributeInfo.ID);

            // current row
            variables.add(new VariableInfo(tagId.toString(), Object.class.getName(), true, VariableInfo.NESTED));

            // current row number
            variables.add(new VariableInfo(tagId + ROWNUM_SUFFIX, Integer.class.getName(), true, VariableInfo.NESTED));
        }

        return (VariableInfo[]) variables.toArray(new VariableInfo[]{});
    }

}