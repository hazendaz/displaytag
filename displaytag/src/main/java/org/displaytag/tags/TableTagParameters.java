package org.displaytag.tags;

/**
 * Constants for parameter names.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class TableTagParameters
{

    /**
     * name of the parameter specifying the current sorted column index.
     */
    public static final String PARAMETER_SORT = "s";

    /**
     * name of the parameter specifying the previous sorted column index.
     */
    public static final String PARAMETER_PREVIOUSSORT = "xs";

    /**
     * name of the parameter specifying the current page number.
     */
    public static final String PARAMETER_PAGE = "p";

    /**
     * name of the parameter specifying the current sorting order.
     */
    public static final String PARAMETER_ORDER = "o";

    /**
     * name of the parameter specifying the previous sorting order.
     */
    public static final String PARAMETER_PREVIOUSORDER = "xo";

    /**
     * name of the parameter specifying the export type.
     */
    public static final String PARAMETER_EXPORTTYPE = "e";

    /**
     * sort only the displayed page.
     */
    public static final String SORT_AMOUNT_PAGE = "page";

    /**
     * sort the full list.
     */
    public static final String SORT_AMOUNT_LIST = "list";

    /**
     * css class added to empty tables.
     */
    public static final String CSS_EMPTYLIST = "empty";

    /**
     * utility class - don't instantiate.
     */
    private TableTagParameters()
    {
    }

}