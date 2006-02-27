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
    public static final String PARAMETER_SORT = "s"; //$NON-NLS-1$

    /**
     * name of the parameter specifying the current page number.
     */
    public static final String PARAMETER_PAGE = "p"; //$NON-NLS-1$

    /**
     * name of the parameter specifying the current sorting order.
     */
    public static final String PARAMETER_ORDER = "o"; //$NON-NLS-1$

    /**
     * name of the parameter specifying the export type.
     */
    public static final String PARAMETER_EXPORTTYPE = "e"; //$NON-NLS-1$

    /**
     * name of the <strong>fixed </strong> parameter that will be added to the url if exporting is requested for any of
     * the display table in the page. Used by the export filter to understand when output should not be flushed.
     */
    public static final String PARAMETER_EXPORTING = "6578706f7274"; //$NON-NLS-1$

    /**
     * sort only the displayed page.
     */
    public static final String SORT_AMOUNT_PAGE = "page"; //$NON-NLS-1$

    /**
     * sort the full list.
     */
    public static final String SORT_AMOUNT_LIST = "list"; //$NON-NLS-1$

    /**
     * css class added to empty tables.
     */
    public static final String CSS_EMPTYLIST = "empty";

    /**
     * Key on the map passed to the filter containg the "buffer" flag.
     */
    public static final String BEAN_BUFFER = "buffer";

    /**
     * Key on the map passed to the filter containg the content type.
     */
    public static final String BEAN_CONTENTTYPE = "contenttype";

    /**
     * Key on the map passed to the filter containg the file name.
     */
    public static final String BEAN_FILENAME = "filename";

    /**
     * Key on the map passed to the filter containg the exported data.
     */
    public static final String BEAN_BODY = "body";

    /**
     * utility class - don't instantiate.
     */
    private TableTagParameters()
    {
        // unused
    }

}