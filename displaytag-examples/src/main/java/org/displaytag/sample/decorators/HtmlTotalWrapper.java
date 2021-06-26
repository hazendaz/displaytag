/*
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
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


/**
 * Same idea implemented in HtmlTableWriter applied to decorators.
 *
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 * @see org.displaytag.render.HtmlTableWriter
 */
public class HtmlTotalWrapper extends TotalWrapperTemplate
{

    /**
     * Write the city in HTML.
     * @param city City name.
     * @param total City total.
     */
    @Override
    protected void writeCityTotal(String city, double total)
    {
        StringBuffer buffer = this.getStringBuffer();
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
     * @param total The table grand total.
     */
    @Override
    protected void writeGrandTotal(double total)
    {
        StringBuffer buffer = this.getStringBuffer();
        buffer.append("<tr><td colspan=\"4\"><hr/></td></tr>"); //$NON-NLS-1$
        buffer.append("<tr><td>&nbsp;</td>"); //$NON-NLS-1$
        buffer.append("<td align=\"right\"><strong>Grand Total:</strong></td><td><strong>"); //$NON-NLS-1$
        buffer.append(total);
        buffer.append("</strong></td><td>&nbsp;</td></tr>"); //$NON-NLS-1$
    }
}
