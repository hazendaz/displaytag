package org.displaytag.tags;

import javax.servlet.jsp.tagext.TagAttributeInfo;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;
import java.util.ArrayList;
import java.util.List;


/**
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
        List variables = new ArrayList(4);

        Object tagId = data.getAttributeString(TagAttributeInfo.ID);

        // if id is null don't define anything
        if (tagId != null)
        {
            // current row
            variables.add(new VariableInfo(tagId.toString(), "java.lang.Object", //$NON-NLS-1$
                true, VariableInfo.NESTED));

            // current row number
            variables.add(new VariableInfo(tagId + ROWNUM_SUFFIX, "java.lang.Integer", //$NON-NLS-1$
                true, VariableInfo.NESTED));
        }

        // media type row number
        variables.add(new VariableInfo(TableTag.PAGE_ATTRIBUTE_MEDIA, "org.displaytag.properties.MediaTypeEnum", //$NON-NLS-1$
            true, VariableInfo.NESTED));

        return (VariableInfo[]) variables.toArray(new VariableInfo[]{});
    }

}