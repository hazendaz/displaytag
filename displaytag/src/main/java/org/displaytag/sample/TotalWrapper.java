package org.displaytag.sample;

import java.util.List;

import org.displaytag.decorator.TableDecorator;

/**
 * This decorator only does a summing of different groups in the reporting
 * style examples...
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class TotalWrapper extends TableDecorator
{
    /**
     * Field cityTotal
     */
    private double mCityTotal = 0;

    /**
     * Field grandTotal
     */
    private double mGrandTotal = 0;

    /**
     * After every row completes we evaluate to see if we should be drawing a
     * new total line and summing the results from the previous group.
     * @return String
     */
    public final String finishRow()
    {
        int lListindex = ((List) getDecoratedObject()).indexOf(this.getCurrentRowObject());
        ReportableListObject lReportableObject = (ReportableListObject) this.getCurrentRowObject();
        String lNextCity = "";

        mCityTotal += lReportableObject.getAmount();
        mGrandTotal += lReportableObject.getAmount();

        if (lListindex == ((List) getDecoratedObject()).size() - 1)
        {
            lNextCity = "XXXXXX"; // Last row hack, it's only a demo folks...
        }
        else
        {
            lNextCity = ((ReportableListObject) ((List) getDecoratedObject()).get(lListindex + 1)).getCity();
        }

        StringBuffer lBuffer = new StringBuffer(1000);

        // City subtotals...
        if (!lNextCity.equals(lReportableObject.getCity()))
        {
            lBuffer.append("\n<tr>\n<td>&nbsp;</td><td>&nbsp;</td><td><hr noshade size=\"1\"></td>");
            lBuffer.append("\n<td>&nbsp;</td></tr>");

            lBuffer.append("\n<tr><td>&nbsp;</td>");
            lBuffer.append("\n<td align=\"right\"><b>" + lReportableObject.getCity() + " Total:</b></td>\n<td><b>");
            lBuffer.append(mCityTotal);
            lBuffer.append("</b></td>\n<td>&nbsp;</td>\n</tr>");
            lBuffer.append("\n<tr>\n<td colspan=\"4\">&nbsp;\n</td>\n</tr>");

            mCityTotal = 0;
        }

        // Grand totals...
        if (getViewIndex() == ((List) getDecoratedObject()).size() - 1)
        {
            lBuffer.append("<tr><td colspan=\"4\"><hr noshade size=\"1\"></td></tr>");
            lBuffer.append("<tr><td>&nbsp;</td>");
            lBuffer.append("<td align=\"right\"><b>Grand Total:</b></td><td><b>");
            lBuffer.append(mGrandTotal);
            lBuffer.append("</b></td><td>&nbsp;</td></tr>");
        }

        return lBuffer.toString();
    }

}
