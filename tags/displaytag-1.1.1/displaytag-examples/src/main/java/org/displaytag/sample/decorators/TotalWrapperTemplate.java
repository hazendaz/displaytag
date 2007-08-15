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
package org.displaytag.sample.decorators;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.sample.ReportableListObject;


/**
 * Same idea implemented in TableWriterTemplate applied to decorators.
 * @see org.displaytag.render.TableWriterTemplate
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 */
public abstract class TotalWrapperTemplate extends TableDecorator
{

    /**
     * total amount.
     */
    private double grandTotal;

    /**
     * total amount for city.
     */
    private double cityTotal;

    /**
     * Obtain the <code>StringBuffer</code> used to build the totals line.
     */
    private StringBuffer buffer;

    /**
     * After every row completes we evaluate to see if we should be drawing a new total line and summing the results
     * from the previous group.
     * @return String
     */
    public final String finishRow()
    {
        int listindex = ((List) getDecoratedObject()).indexOf(this.getCurrentRowObject());
        ReportableListObject reportableObject = (ReportableListObject) this.getCurrentRowObject();
        String nextCity = null;

        this.cityTotal += reportableObject.getAmount();
        this.grandTotal += reportableObject.getAmount();

        if (listindex != ((List) getDecoratedObject()).size() - 1)
        {
            nextCity = ((ReportableListObject) ((List) getDecoratedObject()).get(listindex + 1)).getCity();
        }

        this.buffer = new StringBuffer(1000);

        // City subtotals...
        if (!ObjectUtils.equals(nextCity, reportableObject.getCity()))
        {
            writeCityTotal(reportableObject.getCity(), this.cityTotal);
            this.cityTotal = 0;
        }

        // Grand totals...
        if (getViewIndex() == ((List) getDecoratedObject()).size() - 1)
        {
            writeGrandTotal(this.grandTotal);
        }

        return buffer.toString();
    }

    /**
     * Render the city total in the appropriate format.
     * @param city The city name to render.
     * @param total The city total to render.
     */
    abstract protected void writeCityTotal(String city, double total);

    /**
     * Render the grand total in the appropriate format.
     * @param total The grand total to render.
     */
    abstract protected void writeGrandTotal(double total);

    /**
     * Obtain the <code>StringBuffer</code> used to build the totals line.
     * @return The <code>StringBuffer</code> used to build the totals line.
     */
    protected StringBuffer getStringBuffer()
    {
        return this.buffer;
    }
}
