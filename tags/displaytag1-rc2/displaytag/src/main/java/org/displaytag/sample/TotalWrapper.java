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

import java.util.List;

import org.displaytag.decorator.TableDecorator;


/**
 * This decorator only does a summing of different groups in the reporting style examples...
 * @author epesh
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TotalWrapper extends TableDecorator
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
     * After every row completes we evaluate to see if we should be drawing a new total line and summing the results
     * from the previous group.
     * @return String
     */
    public final String finishRow()
    {
        int listindex = ((List) getDecoratedObject()).indexOf(this.getCurrentRowObject());
        ReportableListObject reportableObject = (ReportableListObject) this.getCurrentRowObject();
        String nextCity;

        this.cityTotal += reportableObject.getAmount();
        this.grandTotal += reportableObject.getAmount();

        if (listindex == ((List) getDecoratedObject()).size() - 1)
        {
            nextCity = "XXXXXX"; // Last row hack, it's only a demo folks... //$NON-NLS-1$
        }
        else
        {
            nextCity = ((ReportableListObject) ((List) getDecoratedObject()).get(listindex + 1)).getCity();
        }

        StringBuffer buffer = new StringBuffer(1000);

        // City subtotals...
        if (!nextCity.equals(reportableObject.getCity()))
        {
            buffer.append("\n<tr>\n<td>&nbsp;</td><td>&nbsp;</td><td><hr noshade size=\"1\"></td>"); //$NON-NLS-1$
            buffer.append("\n<td>&nbsp;</td></tr>"); //$NON-NLS-1$
            buffer.append("\n<tr><td>&nbsp;</td>"); //$NON-NLS-1$
            buffer.append("\n<td align=\"right\"><strong>" //$NON-NLS-1$
                + reportableObject.getCity() + " Total:</strong></td>\n<td><strong>"); //$NON-NLS-1$
            buffer.append(this.cityTotal);
            buffer.append("</strong></td>\n<td>&nbsp;</td>\n</tr>"); //$NON-NLS-1$
            buffer.append("\n<tr>\n<td colspan=\"4\">&nbsp;\n</td>\n</tr>"); //$NON-NLS-1$

            this.cityTotal = 0;
        }

        // Grand totals...
        if (getViewIndex() == ((List) getDecoratedObject()).size() - 1)
        {
            buffer.append("<tr><td colspan=\"4\"><hr></td></tr>"); //$NON-NLS-1$
            buffer.append("<tr><td>&nbsp;</td>"); //$NON-NLS-1$
            buffer.append("<td align=\"right\"><strong>Grand Total:</strong></td><td><strong>"); //$NON-NLS-1$
            buffer.append(this.grandTotal);
            buffer.append("</strong></td><td>&nbsp;</td></tr>"); //$NON-NLS-1$
        }

        return buffer.toString();
    }

}
