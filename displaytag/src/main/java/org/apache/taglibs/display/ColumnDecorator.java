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
package org.apache.taglibs.display;

import org.displaytag.exception.DecoratorException;


/**
 * <p>
 * Placeholder class to preserve compatibility with decorator created with older version of the display taglib.
 * </p>
 * <p>
 * Never extend this class, you should always implements the <code>org.displaytag.decorator.ColumnDecorator</code>
 * interface.
 * <p>
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 * @deprecated use <code>org.displaytag.decorator.ColumnDecorator</code>
 */
public abstract class ColumnDecorator implements org.displaytag.decorator.ColumnDecorator
{

    /**
     * @see org.displaytag.decorator.ColumnDecorator#decorate(Object)
     */
    public abstract String decorate(Object columnValue) throws DecoratorException;

}
