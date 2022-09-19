/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.util;

/**
 * Constants for html tags.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public final class TagConstants {

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
    public static final String TAG_TR_OPEN = TagConstants.TAG_OPEN + TagConstants.TAGNAME_ROW + TagConstants.TAG_CLOSE;

    /**
     * html tag <code>%lt;/tr&gt;</code>.
     */
    public static final String TAG_TR_CLOSE = TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_ROW
            + TagConstants.TAG_CLOSE;

    /**
     * html tag <code>%lt;thead&gt;</code>.
     */
    public static final String TAG_THEAD_OPEN = TagConstants.TAG_OPEN + TagConstants.TAGNAME_TABLE_HEAD
            + TagConstants.TAG_CLOSE;

    /**
     * html tag <code>%lt;/thead&gt;</code>.
     */
    public static final String TAG_THEAD_CLOSE = TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_TABLE_HEAD
            + TagConstants.TAG_CLOSE;

    /**
     * html tag <code>%lt;tbody&gt;</code>.
     */
    public static final String TAG_TBODY_OPEN = TagConstants.TAG_OPEN + TagConstants.TAGNAME_TABLE_BODY
            + TagConstants.TAG_CLOSE;

    /**
     * html tag <code>%lt;/tbody&gt;</code>.
     */
    public static final String TAG_TBODY_CLOSE = TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_TABLE_BODY
            + TagConstants.TAG_CLOSE;

    /**
     * html tag <code>%lt;tfooter&gt;</code>.
     */
    public static final String TAG_TFOOTER_OPEN = TagConstants.TAG_OPEN + TagConstants.TAGNAME_TABLE_FOOTER
            + TagConstants.TAG_CLOSE;

    /**
     * html tag <code>%lt;/tfooter&gt;</code>.
     */
    public static final String TAG_TFOOTER_CLOSE = TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_TABLE_FOOTER
            + TagConstants.TAG_CLOSE;

    /**
     * html tag <code>%lt;th&gt;</code>.
     */
    public static final String TAG_TH_OPEN = TagConstants.TAG_OPEN + TagConstants.TAGNAME_COLUMN_HEADER
            + TagConstants.TAG_CLOSE;

    /**
     * html tag <code>%lt;/th&gt;</code>.
     */
    public static final String TAG_TH_CLOSE = TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN_HEADER
            + TagConstants.TAG_CLOSE;

    /**
     * html tag <code>%lt;td&gt;</code>.
     */
    public static final String TAG_TD_OPEN = TagConstants.TAG_OPEN + TagConstants.TAGNAME_COLUMN
            + TagConstants.TAG_CLOSE;

    /**
     * html tag <code>%lt;/td&gt;</code>.
     */
    public static final String TAG_TD_CLOSE = TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN
            + TagConstants.TAG_CLOSE;

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
     * html attribute <code>scope</code>.
     */
    public static final String ATTRIBUTE_SCOPE = "scope"; //$NON-NLS-1$

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
    private TagConstants() {
        // unused
    }
}