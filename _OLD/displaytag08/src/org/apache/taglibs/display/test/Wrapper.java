/**
 * $Id$
 *
 * Status: Ok
 **/

package org.apache.taglibs.display.test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.taglibs.display.Decorator;

/**
 * This class is a decorator of the TestObjects that we keep in our List.  This
 * class provides a number of methods for formatting data, creating dynamic
 * links, and exercising some aspects of the display:table API functionality
 **/

public class Wrapper extends Decorator {
    private SimpleDateFormat datefmt = null;
    private DecimalFormat moneyfmt = null;

    /**
     * Creates a new Wrapper decorator who's job is to reformat some of the
     * data located in our TestObject's.
     */

    public Wrapper() {
        super();

        // Formats for displaying dates and money.

        this.datefmt = new SimpleDateFormat("MM/dd/yy");
        this.moneyfmt = new DecimalFormat("$ #,###,###.00");
    }

    public String getNullValue() {
        return null;
    }

    /**
     * Returns the date as a String in MM/dd/yy format
     */

    public String getDate() {
        return this.datefmt.format(((ListObject) this.getObject()).getDate());
    }

    /**
     * Returns the money as a String in $ #,###,###.00 format
     */

    public String getMoney() {
        return this.moneyfmt.format(((ListObject) this.getObject()).getMoney());
    }

    /**
     * Returns the TestObject's ID as a hyperlink that the person can click on
     * and "drill down" for more details.
     */

    public String getLink1() {
        ListObject obj = (ListObject) this.getObject();
        int index = this.getListIndex();

        return "<a href=\"details.jsp?index=" + index + "\">" + obj.getId() + "</a>";
    }

    /**
     * Returns an "action bar" of sorts that allow the user to perform various
     * actions on the TestObject based on it's id.
     */

    public String getLink2() {
        ListObject obj = (ListObject) this.getObject();
        int id = obj.getId();

        return "<a href=\"details.jsp?id=" + id + "&action=view\">View</a> | " +
                "<a href=\"details.jsp?id=" + id + "&action=edit\">Edit</a> | " +
                "<a href=\"details.jsp?id=" + id + "&action=delete\">Delete</a>";
    }
}
