/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.sample.decorators;

import java.util.List;
import java.util.Objects;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.sample.ReportableListObject;

/**
 * Same idea implemented in TableWriterTemplate applied to decorators.
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
            nextCity = ((List<ReportableListObject>) getDecoratedObject()).get(listindex + 1).getCity();
        }

        this.buffer = new StringBuilder(1000);

        // City subtotals...
        if (!Objects.equals(nextCity, reportableObject.getCity())) {
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
