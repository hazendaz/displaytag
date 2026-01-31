/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.sample.decorators;

/**
 * Same idea implemented in HtmlTableWriter applied to decorators.
 *
 * @see org.displaytag.render.HtmlTableWriter
 */
public class HtmlTotalWrapper extends TotalWrapperTemplate {

    /**
     * Write the city in HTML.
     *
     * @param city
     *            City name.
     * @param total
     *            City total.
     */
    @Override
    protected void writeCityTotal(String city, double total) {
        StringBuilder buffer = this.getStringBuilder();
        buffer.append("\n<tr>\n<td>&nbsp;</td><td>&nbsp;</td><td><hr/></td>"); //$NON-NLS-1$
        buffer.append("\n<td>&nbsp;</td></tr>"); //$NON-NLS-1$
        buffer.append("\n<tr><td>&nbsp;</td>"); //$NON-NLS-1$
        buffer.append("\n<td align=\"right\"><strong>" //$NON-NLS-1$
                + city + " Total:</strong></td>\n<td><strong>"); //$NON-NLS-1$
        buffer.append(total);
        buffer.append("</strong></td>\n<td>&nbsp;</td>\n</tr>"); //$NON-NLS-1$
        buffer.append("\n<tr>\n<td colspan=\"4\">&nbsp;\n</td>\n</tr>"); //$NON-NLS-1$
    }

    /**
     * Write the table grand total in HTML.
     *
     * @param total
     *            The table grand total.
     */
    @Override
    protected void writeGrandTotal(double total) {
        StringBuilder buffer = this.getStringBuilder();
        buffer.append("<tr><td colspan=\"4\"><hr/></td></tr>"); //$NON-NLS-1$
        buffer.append("<tr><td>&nbsp;</td>"); //$NON-NLS-1$
        buffer.append("<td align=\"right\"><strong>Grand Total:</strong></td><td><strong>"); //$NON-NLS-1$
        buffer.append(total);
        buffer.append("</strong></td><td>&nbsp;</td></tr>"); //$NON-NLS-1$
    }
}
