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

package org.displaytag.decorator.hssf;

import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * An implementor of this interface decorates tables and columns appearing in an HSSF workbook.
 * 
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 */
public interface DecoratesHssf
{
    /**
     * Set the worksheet used to render a table model.
     * 
     * @param sheet The worksheet used to render a table model.
     */
    void setSheet(HSSFSheet sheet);
}
