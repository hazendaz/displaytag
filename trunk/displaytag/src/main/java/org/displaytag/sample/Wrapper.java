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

import java.text.DecimalFormat;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.TableDecorator;


/**
 * This class is a decorator of the TestObjects that we keep in our List. This class provides a number of methods for
 * formatting data, creating dynamic links, and exercising some aspects of the display:table API functionality.
 * @author epesh
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Wrapper extends TableDecorator
{

    /**
     * FastDateFormat used to format dates in getDate().
     */
    private FastDateFormat dateFormat;

    /**
     * DecimalFormat used to format money in getMoney().
     */
    private DecimalFormat moneyFormat;

    /**
     * Creates a new Wrapper decorator who's job is to reformat some of the data located in our TestObject's.
     */
    public Wrapper()
    {
        super();

        // Formats for displaying dates and money.

        this.dateFormat = FastDateFormat.getInstance("MM/dd/yy");
        this.moneyFormat = new DecimalFormat("$ #,###,###.00");
    }

    /**
     * Test method which always returns a null value.
     * @return <code>null</code>
     */
    public String getNullValue()
    {
        return null;
    }

    /**
     * Returns the date as a String in MM/dd/yy format.
     * @return formatted date
     */
    public String getDate()
    {
        return this.dateFormat.format(((ListObject) this.getCurrentRowObject()).getDate());
    }

    /**
     * Returns the money as a String in $ #,###,###.00 format.
     * @return String
     */
    public String getMoney()
    {
        return this.moneyFormat.format(((ListObject) this.getCurrentRowObject()).getMoney());
    }

    /**
     * Returns the TestObject's ID as a hyperlink that the person can click on and "drill down" for more details.
     * @return String
     */
    public String getLink1()
    {
        ListObject object = (ListObject) getCurrentRowObject();
        int index = getListIndex();

        return "<a href=\"details.jsp?index=" + index + "\">" + object.getId() + "</a>";
    }

    /**
     * Returns an "action bar" of sorts that allow the user to perform various actions on the TestObject based on it's
     * id.
     * @return String
     */
    public String getLink2()
    {
        ListObject object = (ListObject) getCurrentRowObject();
        int id = object.getId();

        return "<a href=\"details.jsp?id="
            + id
            + "&action=view\">View</a> | "
            + "<a href=\"details.jsp?id="
            + id
            + "&action=edit\">Edit</a> | "
            + "<a href=\"details.jsp?id="
            + id
            + "&action=delete\">Delete</a>";
    }
}
