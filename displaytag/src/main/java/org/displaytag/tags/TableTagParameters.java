/**
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
     * name of the parameter specifying that the sorted column will be specified using name and not index.
     */
    public static final String PARAMETER_SORTUSINGNAME = "n"; //$NON-NLS-1$

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
     * let the server handle the sorting
     */
    public static final String SORT_AMOUNT_EXTERNAL = "external"; //$NON-NLS-1$

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