package org.displaytag.util;

/**
 * <p>Constants for html tags</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public final class TagConstants
{

    /**
     * start of tag <code>&lt;</code>
     */
    public static final String TAG_OPEN = "\n<";

    /**
     * start of closing tag <code>&lt;/</code>
     */
    public static final String TAG_OPENCLOSING = "</";

    /**
     * end of tag <code>&gt;</code>
     */
    public static final String TAG_CLOSE = ">";

    /**
     * html <code>a</code> tag name
     */
    public static final String TAGNAME_ANCHOR = "a";

    /**
     * html <code>table</code> tag name
     */
    public static final String TABLE_TAG_NAME = "table";

    /**
     * html <code>td</code> tag name
     */
    public static final String TAGNAME_COLUMN = "td";

    /**
     * html <code>tr</code> tag name
     */
    public static final String TAGNAME_ROW = "tr";

    /**
     * html <code>th</code> tag name
     */
    public static final String TAGNAME_COLUMN_HEADER = "th";

    /**
     * html <code>tbody</code> tag name
     */
    public static final String TAGNAME_TABLE_HEAD = "thead";

    /**
     * html <code>tbody</code> tag name
     */
    public static final String TAGNAME_TABLE_BODY = "tbody";

    /**
     * html <code>tfooter</code> tag name
     */
    public static final String TAGNAME_TABLE_FOOTER = "tfooter";

    /**
    * html tag <code>%lt;tr&gt;</code>
    */
    public static final String TAG_TR_OPEN = TAG_OPEN + TAGNAME_ROW + TAG_CLOSE;

    /**
    * html tag <code>%lt;/tr&gt;</code>
    */
    public static final String TAG_TR_CLOSE = TAG_OPENCLOSING + TAGNAME_ROW + TAG_CLOSE;

    /**
    * html tag <code>%lt;thead&gt;</code>
    */
    public static final String TAG_THEAD_OPEN = TAG_OPEN + TAGNAME_TABLE_HEAD + TAG_CLOSE;

    /**
    * html tag <code>%lt;/thead&gt;</code>
    */
    public static final String TAG_THEAD_CLOSE = TAG_OPENCLOSING + TAGNAME_TABLE_HEAD + TAG_CLOSE;

    /**
    * html tag <code>%lt;tbody&gt;</code>
    */
    public static final String TAG_TBODY_OPEN = TAG_OPEN + TAGNAME_TABLE_BODY + TAG_CLOSE;

    /**
    * html tag <code>%lt;/tbody&gt;</code>
    */
    public static final String TAG_TBODY_CLOSE = TAG_OPENCLOSING + TAGNAME_TABLE_BODY + TAG_CLOSE;

    /**
    * html tag <code>%lt;tfooter&gt;</code>
    */
    public static final String TAG_TFOOTER_OPEN = TAG_OPEN + TAGNAME_TABLE_FOOTER + TAG_CLOSE;

    /**
    * html tag <code>%lt;/tfooter&gt;</code>
    */
    public static final String TAG_TFOOTER_CLOSE = TAG_OPENCLOSING + TAGNAME_TABLE_FOOTER + TAG_CLOSE;

    /**
    * html tag <code>%lt;th&gt;</code>
    */
    public static final String TAG_TH_OPEN = TAG_OPEN + TAGNAME_COLUMN_HEADER + TAG_CLOSE;

    /**
    * html tag <code>%lt;/th&gt;</code>
    */
    public static final String TAG_TH_CLOSE = TAG_OPENCLOSING + TAGNAME_COLUMN_HEADER + TAG_CLOSE;

    /**
    * html tag <code>%lt;td&gt;</code>
    */
    public static final String TAG_TD_OPEN = TAG_OPEN + TAGNAME_COLUMN + TAG_CLOSE;

    /**
    * html tag <code>%lt;/td&gt;</code>
    */
    public static final String TAG_TD_CLOSE = TAG_OPENCLOSING + TAGNAME_COLUMN + TAG_CLOSE;

    /**
     * html attribute <code>rules</code>
     */
    public static final String ATTRIBUTE_RULES = "rules";

    /**
     * html attribute <code>bgcolor</code>
     */
    public static final String ATTRIBUTE_BGCOLOR = "bgcolor";

    /**
     * html attribute <code>frame</code>
     */
    public static final String ATTRIBUTE_FRAME = "frame";

    /**
     * html attribute <code>height</code>
     */
    public static final String ATTRIBUTE_HEIGHT = "height";

    /**
     * html attribute <code>hspace</code>
     */
    public static final String ATTRIBUTE_HSPACE = "hspace";

    /**
     * html attribute <code>width</code>
     */
    public static final String ATTRIBUTE_WIDTH = "width";

    /**
     * html attribute <code>border</code>
     */
    public static final String ATTRIBUTE_BORDER = "border";

    /**
     * html attribute <code>cellspacing</code>
     */
    public static final String ATTRIBUTE_CELLSPACING = "cellspacing";

    /**
     * html attribute <code>cellpadding</code>
     */
    public static final String ATTRIBUTE_CELLPADDING = "cellpadding";

    /**
     * html attribute <code>align</code>
     */
    public static final String ATTRIBUTE_ALIGN = "align";

    /**
     * html attribute <code>background</code>
     */
    public static final String ATTRIBUTE_BACKGROUND = "background";

    /**
     * html attribute <code>summary</code>
     */
    public static final String ATTRIBUTE_SUMMARY = "summary";

    /**
     * html attribute <code>vspace</code>
     */
    public static final String ATTRIBUTE_VSPACE = "vspace";

    /**
     * html attribute <code>class</code>
     */
    public static final String ATTRIBUTE_CLASS = "class";

    /**
     * html attribute <code>id</code>
     */
    public static final String ATTRIBUTE_ID = "id";

    /**
     * html attribute <code>style</code>
     */
    public static final String ATTRIBUTE_STYLE = "style";

    /**
     * html attribute <code>title</code>
     */
    public static final String ATTRIBUTE_TITLE = "title";

    /**
     * html attribute <code>valign</code>
     */
    public static final String ATTRIBUTE_VALIGN = "valign";

    /**
     * html attribute <code>nowrap</code>
     */
    public static final String ATTRIBUTE_NOWRAP = "nowrap";

    /**
     * escaped ampersand <code>&amp;amp;</code>
     */
    public static final String AMPERSAND = "&amp;";

    /**
     * utility class - don't instantiate
     */
    private TagConstants()
    {
    }
}
