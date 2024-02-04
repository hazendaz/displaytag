/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.sample.ReportableListObject;

/**
 * Same idea implemented in TableWriterTemplate applied to decorators.
 *
 * @author Jorge L. Barroso
 *
 * @version $Revision$ ($Author$)
 *
 * @see org.displaytag.render.TableWriterTemplate
 */
public abstract class TotalWrapperTemplate extends TableDecorator {

    /**
     * total amount.
     */
    private double grandTotal;

    /**
     * total amount for city.
     */
    private double cityTotal;

    /**
     * Obtain the <code>StringBuilder</code> used to build the totals line.
     */
    private StringBuilder buffer;

    /**
     * After every row completes we evaluate to see if we should be drawing a new total line and summing the results
     * from the previous group.
     *
     * @return String
     */
    @SuppressWarnings("deprecation")
    @Override
    public final String finishRow() {
        int listindex = ((List<ReportableListObject>) getDecoratedObject()).indexOf(this.getCurrentRowObject());
        ReportableListObject reportableObject = (ReportableListObject) this.getCurrentRowObject();
        String nextCity = null;

        this.cityTotal += reportableObject.getAmount();
        this.grandTotal += reportableObject.getAmount();

        if (listindex != ((List<ReportableListObject>) getDecoratedObject()).size() - 1) {
            nextCity = (((List<ReportableListObject>) getDecoratedObject()).get(listindex + 1)).getCity();
        }

        this.buffer = new StringBuilder(1000);

        // City subtotals...
        if (!ObjectUtils.equals(nextCity, reportableObject.getCity())) {
            writeCityTotal(reportableObject.getCity(), this.cityTotal);
            this.cityTotal = 0;
        }

        // Grand totals...
        if (getViewIndex() == ((List<ReportableListObject>) getDecoratedObject()).size() - 1) {
            writeGrandTotal(this.grandTotal);
        }

        return this.buffer.toString();
    }

    /**
     * Render the city total in the appropriate format.
     *
     * @param city
     *            The city name to render.
     * @param total
     *            The city total to render.
     */
    protected abstract void writeCityTotal(String city, double total);

    /**
     * Render the grand total in the appropriate format.
     *
     * @param total
     *            The grand total to render.
     */
    protected abstract void writeGrandTotal(double total);

    /**
     * Obtain the <code>StringBuilder</code> used to build the totals line.
     *
     * @return The <code>StringBuilder</code> used to build the totals line.
     */
    protected StringBuilder getStringBuilder() {
        return this.buffer;
    }
}
