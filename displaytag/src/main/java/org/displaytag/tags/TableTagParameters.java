package org.displaytag.tags;

/**
 * <p>Constants for parameter names</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public final class TableTagParameters
{

    /**
     * utility class - don't instantiate
     */
    private TableTagParameters()
    {
    }

    /**
     * name of the parameter specifying the current sorted column index
     */
    public static final String PARAMETER_SORT = "s";

    /**
     * name of the parameter specifying the previous sorted column index
     */
    public static final String PARAMETER_PREVIOUSSORT = "xs";

    /**
     * name of the parameter specifying the current page number
     */
    public static final String PARAMETER_PAGE = "p";

    /**
     * name of the parameter specifying the current sorting order
     */
    public static final String PARAMETER_ORDER = "o";

    /**
     * name of the parameter specifying the previous sorting order
     */
    public static final String PARAMETER_PREVIOUSORDER = "xo";

    /**
     * name of the parameter specifying the export type
     */
    public static final String PARAMETER_EXPORTTYPE = "e";

    /**
     * export type parameter value = no export
     */
    public static final int EXPORT_TYPE_NONE = -1;

    /**
     * export type parameter value = cvs export
     */
    public static final int EXPORT_TYPE_CSV = 1;

    /**
     * export type parameter value = excel export
     */
    public static final int EXPORT_TYPE_EXCEL = 2;

    /**
     * export type parameter value = xml export
     */
    public static final int EXPORT_TYPE_XML = 3;

    /**
     * sort only the displayed page
     */
    public static final String SORT_AMOUNT_PAGE = "page";

    /**
     * sort the full list
     */
    public static final String SORT_AMOUNT_LIST = "list";

    /**
     * order parameter value = descending
     */
    public static final int VALUE_SORT_DESCENDING = 1;

    /**
     * order parameter value = ascending
     */
    public static final int VALUE_SORT_ASCENDING = 2;

    /**
     * css class added to odd rows
     */
    public static final String CSS_ODDROW = "odd";

    /**
     * css class added to even rows
     */
    public static final String CSS_EVENROW = "even";

    /**
     * css class added to empty tables
     */
    public static final String CSS_EMPTYLIST = "empty";

    /**
     * css class added to sorted columns
     */
    public static final String CSS_SORTEDCOLUMN = "sorted";

    /**
     * prefix for the css class added to sorted column to specify order (0 and 1 is added)
     */
    public static final String CSS_SORTORDERPREFIX = "order";

}