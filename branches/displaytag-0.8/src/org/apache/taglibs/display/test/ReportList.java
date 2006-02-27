/**
 * $Id$
 *
 * Status: Ok
 **/

package org.apache.taglibs.display.test;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Just a utility class for testing out the table and column tags.
 *
 * This List fills itself with objects and sorts them as though it where pulling
 * data from a report.  This list is used to show the various report oriented
 * examples (such as grouping, callbacks, and data exports).
 **/

public class ReportList extends ArrayList {
    /**
     * Creats a TestList that is filled with 20 ReportableListObject suitable for testing
     */

    public ReportList() {
        super();

        for (int i = 0; i < 20; i++) {
            this.add(new ReportableListObject());
        }

        Collections.sort(this);
    }

    /**
     * Creates a TestList that is filled with [size] ReportableListObject suitable for
     * testing.
     */

    public ReportList(int size) {
        super();

        for (int i = 0; i < size; i++) {
            this.add(new ReportableListObject());
        }

        Collections.sort(this);
    }
}
