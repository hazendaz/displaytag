/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.sample.decorators;

import java.text.DecimalFormat;

import org.apache.commons.lang3.time.FastDateFormat;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.sample.ListObject;


/**
 * This class is a decorator of the TestObjects that we keep in our List. This class provides a number of methods for
 * formatting data, creating dynamic links, and exercising some aspects of the display:table API functionality.
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

        this.dateFormat = FastDateFormat.getInstance("MM/dd/yy");
        this.moneyFormat = new DecimalFormat("$ #,###,###.00");
    }

    /**
     * Test method which always returns a null value.
     *
     * @return the null value
     */
    public String getNullValue()
    {
        return null;
    }

    /**
     * Returns the date as a String in MM/dd/yy format.
     *
     * @return the date
     */
    public String getDate()
    {
        return this.dateFormat.format(((ListObject) this.getCurrentRowObject()).getDate());
    }

    /**
     * Returns the money as a String in $ #,###,###.00 format.
     *
     * @return the money
     */
    public String getMoney()
    {
        return this.moneyFormat.format(((ListObject) this.getCurrentRowObject()).getMoney());
    }

    /**
     * Returns the TestObject's ID as a hyperlink that the person can click on and "drill down" for more details.
     *
     * @return the link 1
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
     *
     * @return the link 2
     */
    public String getLink2()
    {
        ListObject object = (ListObject) getCurrentRowObject();
        int id = object.getId();

        return "<a href=\"details.jsp?id="
            + id
            + "&amp;action=view\">View</a> | "
            + "<a href=\"details.jsp?id="
            + id
            + "&amp;action=edit\">Edit</a> | "
            + "<a href=\"details.jsp?id="
            + id
            + "&amp;action=delete\">Delete</a>";
    }
}
