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

package org.displaytag.decorator.itext;

import com.lowagie.text.Font;
import com.lowagie.text.Table;


/**
 * An implementor of this interface decorates tables and columns appearing in iText documents.
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 */
public interface DecoratesItext
{

    /**
     * Set the iText table used to render a table model.
     * @param table The iText table used to render a table model.
     */
    void setTable(Table table);

    /**
     * Set the font used to render a table's content.
     * @param font The font used to render a table's content.
     */
    void setFont(Font font);
}
