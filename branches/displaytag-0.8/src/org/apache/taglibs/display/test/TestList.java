/**
 * $Id$
 *
 * Status: Suitable for testing
 *
 * Todo
 *   - nothing
 **/

package org.apache.taglibs.display.test;

import java.util.ArrayList;

/**
 * Just a utility class for testing out the table and column tags.
 *
 * When this class is created, it loads itself with a number of ListObjects
 * that are shown throughout the various example pages that exercise the table
 * object.  If created via the default constructor, this loads itself with 60
 * ListObjects.
 **/

public class TestList extends ArrayList {
    /**
     * Creats a TestList that is filled with 60 ListObjects suitable for testing
     */

    public TestList() {
        super();

        for (int i = 0; i < 60; i++) {
            this.add(new ListObject());
        }
    }

    /**
     * Creates a TestList that is filled with [size] ListObjects suitable for
     * testing.
     */

    public TestList(int size) {
        super();

        for (int i = 0; i < size; i++) {
            this.add(new ListObject());
        }
    }

    public TestList(boolean duplicates, int size) {
        super();
        // generate a random number between 1 and 3 and duplicate that many number of times.

        for (int i = 0; i < size; i++) {

            ListObject l1 = new ListObject();
            ListObject l2 = new ListObject();
            ListObject l3 = new ListObject();

            int random = new java.util.Random().nextInt(3);
            for (int k = 0; k <= random; k++) {
                this.add(l1);

            }

            l1.setID(l2.getId());

            random = new java.util.Random().nextInt(3);
            for (int k = 0; k <= random; k++) {
                this.add(l1);
                this.add(l2);

            }

            l1.setEmail(l3.getEmail());

            random = new java.util.Random().nextInt(3);
            for (int k = 0; k <= random; k++) {
                this.add(l1);
            }
        }
    }

}
