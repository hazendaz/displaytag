/**
 * $Id$
 *
 * Status: Under Development
 *
 * Todo
 *   - impementation
 *   - documentation (javadoc, examples, etc...)
 *   - junit test cases
 **/

package org.apache.taglibs.display.test;

import java.util.List;

/**
 * One line description of what this class does.
 *
 * More detailed class description, including examples of usage if applicable.
 **/

public class ListHolder extends Object {
    private List myList;

    public ListHolder() {
        myList = new TestList(15);
    }

    public List getList() {
        return this.myList;
    }
}
