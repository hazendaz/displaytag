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

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.ColumnDecorator;


/**
 * Simple column decorator which formats a date.
 * @author epesh
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class LongDateWrapper implements ColumnDecorator
{

    /**
     * FastDateFormat used to format the date object.
     */
    private FastDateFormat dateFormat = FastDateFormat.getInstance("MM/dd/yyyy HH:mm:ss"); //$NON-NLS-1$

    /**
     * transform the given object into a String representation. The object is supposed to be a date.
     * @param columnValue Object
     * @return String
     */
    public final String decorate(Object columnValue)
    {
        Date date = (Date) columnValue;
        return this.dateFormat.format(date);
    }
}
