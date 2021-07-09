/*
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.tagext.TagAttributeInfo;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * TEI for TableTag, defines 3 variables.
 * <ul>
 * <li>table uid = object contained in row</li>
 * <li>table uid + ROWNUM_SUFFIX = row number</li>
 * </ul>
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class TableTagExtraInfo extends TagExtraInfo {

    /**
     * Suffix added to id for saving row number in pagecontext.
     */
    public static final String ROWNUM_SUFFIX = "_rowNum"; //$NON-NLS-1$

    /**
     * Java keywords.
     */
    private static final String[] KEYWORDS = { "abstract", //$NON-NLS-1$
            "assert", //$NON-NLS-1$
            "boolean", //$NON-NLS-1$
            "break", //$NON-NLS-1$
            "byte", //$NON-NLS-1$
            "case", //$NON-NLS-1$
            "catch", //$NON-NLS-1$
            "char", //$NON-NLS-1$
            "class", //$NON-NLS-1$
            "const", //$NON-NLS-1$
            "continue", //$NON-NLS-1$
            "default", //$NON-NLS-1$
            "do", //$NON-NLS-1$
            "double", //$NON-NLS-1$
            "else", //$NON-NLS-1$
            "enum", //$NON-NLS-1$
            "extends", //$NON-NLS-1$
            "false", //$NON-NLS-1$
            "final", //$NON-NLS-1$
            "finally", //$NON-NLS-1$
            "float", //$NON-NLS-1$
            "for", //$NON-NLS-1$
            "goto", //$NON-NLS-1$
            "if", //$NON-NLS-1$
            "implements", //$NON-NLS-1$
            "import", //$NON-NLS-1$
            "instanceof", //$NON-NLS-1$
            "int", //$NON-NLS-1$
            "interface", //$NON-NLS-1$
            "long", //$NON-NLS-1$
            "native", //$NON-NLS-1$
            "new", //$NON-NLS-1$
            "null", //$NON-NLS-1$
            "package", //$NON-NLS-1$
            "private", //$NON-NLS-1$
            "protected", //$NON-NLS-1$
            "public", //$NON-NLS-1$
            "return", //$NON-NLS-1$
            "short", //$NON-NLS-1$
            "static", //$NON-NLS-1$
            "strictfp", //$NON-NLS-1$
            "super", //$NON-NLS-1$
            "switch", //$NON-NLS-1$
            "synchronized", //$NON-NLS-1$
            "this", //$NON-NLS-1$
            "throw", //$NON-NLS-1$
            "throws", //$NON-NLS-1$
            "transient", //$NON-NLS-1$
            "true", //$NON-NLS-1$
            "try", //$NON-NLS-1$
            "void", //$NON-NLS-1$
            "volatile", //$NON-NLS-1$
            "while" }; //$NON-NLS-1$

    /**
     * Variables TableTag makes available in the pageContext.
     *
     * @param data
     *            TagData
     *
     * @return VariableInfo[]
     *
     * @see javax.servlet.jsp.tagext.TagData
     * @see javax.servlet.jsp.tagext.VariableInfo
     */
    @Override
    public VariableInfo[] getVariableInfo(final TagData data) {
        final List<VariableInfo> variables = new ArrayList<>(2);

        Object idObj = data.getAttribute(TagAttributeInfo.ID);

        // handle both the id and uid attributes
        if (idObj == null) {
            idObj = data.getAttribute("uid"); //$NON-NLS-1$
        }

        // avoid errors, but "id" and "id_rownum" will not be defined
        if (idObj != TagData.REQUEST_TIME_VALUE && idObj != null) {
            final String tagId = idObj.toString();

            // don't try to add variables if id is not a valid java identifier.
            if (TableTagExtraInfo.isJavaId(tagId)) {
                // current row
                variables.add(new VariableInfo(tagId, Object.class.getName(), true, VariableInfo.NESTED));
                // current row number
                variables.add(new VariableInfo(tagId + TableTagExtraInfo.ROWNUM_SUFFIX, Integer.class.getName(), true,
                        VariableInfo.NESTED));
            }
        }

        return variables.toArray(new VariableInfo[] {});
    }

    /**
     * isJavaId Returns true if the name is a valid java identifier.
     *
     * @param id
     *            to check
     *
     * @return boolean true/false
     */
    public static boolean isJavaId(final String id) {
        if (id == null || id.length() == 0 || Arrays.binarySearch(TableTagExtraInfo.KEYWORDS, id) >= 0
                || !Character.isJavaIdentifierStart(id.charAt(0))) {
            return false;
        }

        for (int j = 1; j < id.length(); j++) {
            if (!Character.isJavaIdentifierPart(id.charAt(j))) {
                return false;
            }
        }
        return true;
    }

}