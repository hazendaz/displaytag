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
package org.displaytag.sample;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Just a utility class for testing out the table and column tags. This List fills itself with objects and sorts them as
 * though it where pulling data from a report. This list is used to show the various report oriented examples (such as
 * grouping, callbacks, and data exports).
 * @author epesh
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ReportList extends ArrayList
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Creats a TestList that is filled with 20 ReportableListObject suitable for testing.
     */
    public ReportList()
    {
        super();

        for (int j = 0; j < 20; j++)
        {
            add(new ReportableListObject());
        }

        Collections.sort(this);
    }

    /**
     * Creates a TestList that is filled with [size] ReportableListObject suitable for testing.
     * @param size int
     */
    public ReportList(int size)
    {
        super();

        for (int j = 0; j < size; j++)
        {
            add(new ReportableListObject());
        }

        Collections.sort(this);
    }
}
