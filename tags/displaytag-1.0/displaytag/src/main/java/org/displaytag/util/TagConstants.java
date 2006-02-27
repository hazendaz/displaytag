/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.util;

/**
 * Constants for html tags.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class TagConstants
{

    /**
     * start of tag <code>&lt;</code>.
     */
    public static final String TAG_OPEN = "\n<"; //$NON-NLS-1$

    /**
     * start of closing tag <code>&lt;/</code>.
     */
    public static final String TAG_OPENCLOSING = "</"; //$NON-NLS-1$

    /**
     * end of tag <code>&gt;</code>.
     */
    public static final String TAG_CLOSE = ">"; //$NON-NLS-1$

    /**
     * html <code>a</code> tag name.
     */
    public static final String TAGNAME_ANCHOR = "a"; //$NON-NLS-1$

    /**
     * html <code>table</code> tag name.
     */
    public static final String TABLE_TAG_NAME = "table"; //$NON-NLS-1$

    /**
     * html <code>td</code> tag name.
     */
    public static final String TAGNAME_COLUMN = "td"; //$NON-NLS-1$

    /**
     * html <code>tr</code> tag name.
     */
    public static final String TAGNAME_ROW = "tr"; //$NON-NLS-1$

    /**
     * html <code>th</code> tag name.
     */
    public static final String TAGNAME_COLUMN_HEADER = "th"; //$NON-NLS-1$

    /**
     * html <code>tbody</code> tag name.
     */
    public static final String TAGNAME_TABLE_HEAD = "thead"; //$NON-NLS-1$

    /**
     * html <code>tbody</code> tag name.
     */
    public static final String TAGNAME_TABLE_BODY = "tbody"; //$NON-NLS-1$

    /**
     * html <code>tfooter</code> tag name.
     */
    public static final String TAGNAME_TABLE_FOOTER = "tfoot"; //$NON-NLS-1$

    /**
     * html <code>caption</code> tag name.
     */
    public static final String TAGNAME_CAPTION = "caption"; //$NON-NLS-1$

    /**
     * html tag <code>%lt;tr&gt;</code>.
     */
    public static final String TAG_TR_OPEN = TAG_OPEN + TAGNAME_ROW + TAG_CLOSE;

    /**
     * html tag <code>%lt;/tr&gt;</code>.
     */
    public static final String TAG_TR_CLOSE = TAG_OPENCLOSING + TAGNAME_ROW + TAG_CLOSE;

    /**
     * html tag <code>%lt;thead&gt;</code>.
     */
    public static final String TAG_THEAD_OPEN = TAG_OPEN + TAGNAME_TABLE_HEAD + TAG_CLOSE;

    /**
     * html tag <code>%lt;/thead&gt;</code>.
     */
    public static final String TAG_THEAD_CLOSE = TAG_OPENCLOSING + TAGNAME_TABLE_HEAD + TAG_CLOSE;

    /**
     * html tag <code>%lt;tbody&gt;</code>.
     */
    public static final String TAG_TBODY_OPEN = TAG_OPEN + TAGNAME_TABLE_BODY + TAG_CLOSE;

    /**
     * html tag <code>%lt;/tbody&gt;</code>.
     */
    public static final String TAG_TBODY_CLOSE = TAG_OPENCLOSING + TAGNAME_TABLE_BODY + TAG_CLOSE;

    /**
     * html tag <code>%lt;tfooter&gt;</code>.
     */
    public static final String TAG_TFOOTER_OPEN = TAG_OPEN + TAGNAME_TABLE_FOOTER + TAG_CLOSE;

    /**
     * html tag <code>%lt;/tfooter&gt;</code>.
     */
    public static final String TAG_TFOOTER_CLOSE = TAG_OPENCLOSING + TAGNAME_TABLE_FOOTER + TAG_CLOSE;

    /**
     * html tag <code>%lt;th&gt;</code>.
     */
    public static final String TAG_TH_OPEN = TAG_OPEN + TAGNAME_COLUMN_HEADER + TAG_CLOSE;

    /**
     * html tag <code>%lt;/th&gt;</code>.
     */
    public static final String TAG_TH_CLOSE = TAG_OPENCLOSING + TAGNAME_COLUMN_HEADER + TAG_CLOSE;

    /**
     * html tag <code>%lt;td&gt;</code>.
     */
    public static final String TAG_TD_OPEN = TAG_OPEN + TAGNAME_COLUMN + TAG_CLOSE;

    /**
     * html tag <code>%lt;/td&gt;</code>.
     */
    public static final String TAG_TD_CLOSE = TAG_OPENCLOSING + TAGNAME_COLUMN + TAG_CLOSE;

    /**
     * html attribute <code>rules</code>.
     */
    public static final String ATTRIBUTE_RULES = "rules"; //$NON-NLS-1$

    /**
     * html attribute <code>bgcolor</code>.
     */
    public static final String ATTRIBUTE_BGCOLOR = "bgcolor"; //$NON-NLS-1$

    /**
     * html attribute <code>frame</code>.
     */
    public static final String ATTRIBUTE_FRAME = "frame"; //$NON-NLS-1$

    /**
     * html attribute <code>height</code>.
     */
    public static final String ATTRIBUTE_HEIGHT = "height"; //$NON-NLS-1$

    /**
     * html attribute <code>hspace</code>.
     */
    public static final String ATTRIBUTE_HSPACE = "hspace"; //$NON-NLS-1$

    /**
     * html attribute <code>width</code>.
     */
    public static final String ATTRIBUTE_WIDTH = "width"; //$NON-NLS-1$

    /**
     * html attribute <code>border</code>.
     */
    public static final String ATTRIBUTE_BORDER = "border"; //$NON-NLS-1$

    /**
     * html attribute <code>cellspacing</code>.
     */
    public static final String ATTRIBUTE_CELLSPACING = "cellspacing"; //$NON-NLS-1$

    /**
     * html attribute <code>cellpadding</code>.
     */
    public static final String ATTRIBUTE_CELLPADDING = "cellpadding"; //$NON-NLS-1$

    /**
     * html attribute <code>align</code>.
     */
    public static final String ATTRIBUTE_ALIGN = "align"; //$NON-NLS-1$

    /**
     * html attribute <code>background</code>.
     */
    public static final String ATTRIBUTE_BACKGROUND = "background"; //$NON-NLS-1$

    /**
     * html attribute <code>summary</code>.
     */
    public static final String ATTRIBUTE_SUMMARY = "summary"; //$NON-NLS-1$

    /**
     * html attribute <code>vspace</code>.
     */
    public static final String ATTRIBUTE_VSPACE = "vspace"; //$NON-NLS-1$

    /**
     * html attribute <code>class</code>.
     */
    public static final String ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$

    /**
     * html attribute <code>id</code>.
     */
    public static final String ATTRIBUTE_ID = "id"; //$NON-NLS-1$

    /**
     * html attribute <code>style</code>.
     */
    public static final String ATTRIBUTE_STYLE = "style"; //$NON-NLS-1$

    /**
     * html attribute <code>title</code>.
     */
    public static final String ATTRIBUTE_TITLE = "title"; //$NON-NLS-1$

    /**
     * html attribute <code>valign</code>.
     */
    public static final String ATTRIBUTE_VALIGN = "valign"; //$NON-NLS-1$

    /**
     * html attribute <code>nowrap</code>.
     */
    public static final String ATTRIBUTE_NOWRAP = "nowrap"; //$NON-NLS-1$

    /**
     * html attribute <code>lang</code>.
     */
    public static final String ATTRIBUTE_LANG = "lang"; //$NON-NLS-1$

    /**
     * html attribute <code>dir</code>.
     */
    public static final String ATTRIBUTE_DIR = "dir"; //$NON-NLS-1$

    /**
     * escaped ampersand <code>&amp;amp;</code>.
     */
    public static final String AMPERSAND = "&amp;"; //$NON-NLS-1$

    /**
     * Empty String "". Used as constant mainly to avoid useless i18n checks and to track use of empty strings
     * (sometimes index of bad code).
     */
    public static final String EMPTY_STRING = ""; //$NON-NLS-1$

    /**
     * utility class - don't instantiate.
     */
    private TagConstants()
    {
        // unused
    }
}