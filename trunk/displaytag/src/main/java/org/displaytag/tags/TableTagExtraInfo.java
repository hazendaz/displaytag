package org.displaytag.tags;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.TagAttributeInfo;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;


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
     * Java keywords.
     */
    private static final String[] KEYWORDS = {
        "abstract",
        "assert",
        "boolean",
        "break",
        "byte",
        "case",
        "catch",
        "char",
        "class",
        "const",
        "continue",
        "default",
        "do",
        "double",
        "else",
        "enum",
        "extends",
        "false",
        "final",
        "finally",
        "float",
        "for",
        "goto",
        "if",
        "implements",
        "import",
        "instanceof",
        "int",
        "interface",
        "long",
        "native",
        "new",
        "null",
        "package",
        "private",
        "protected",
        "public",
        "return",
        "short",
        "static",
        "strictfp",
        "super",
        "switch",
        "synchronized",
        "this",
        "throw",
        "throws",
        "transient",
        "true",
        "try",
        "void",
        "volatile",
        "while"};

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

            // don't try to add variables if id is not a valid java identifier.
            if (isJavaId(tagId))
            {
                // current row
                variables.add(new VariableInfo(tagId, Object.class.getName(), true, VariableInfo.NESTED));
                // current row number
                variables.add(new VariableInfo(
                    tagId + ROWNUM_SUFFIX,
                    Integer.class.getName(),
                    true,
                    VariableInfo.NESTED));
            }
        }

        return (VariableInfo[]) variables.toArray(new VariableInfo[]{});
    }

    /**
     * isJavaId Returns true if the name is a valid java identifier.
     * @param id to check
     * @return boolean true/false
     */
    public static boolean isJavaId(String id)
    {
        if (StringUtils.isBlank(id)
            || ArrayUtils.contains(KEYWORDS, id)
            || !Character.isJavaIdentifierStart(id.charAt(0)))
        {
            return false;
        }

        for (int j = 1; j < id.length(); j++)
        {
            if (!Character.isJavaIdentifierPart(id.charAt(j)))
            {
                return false;
            }
        }
        return true;
    }

}