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
package org.displaytag.decorator;

import org.displaytag.exception.DecoratorException;


/**
 * <p>
 * Interface for simple column decorators.
 * </p>
 * <p>
 * A column decorator is called after the object has been retrieved and it can "transform" the object before the
 * rendering. A column decorator is simply an object formatter, and it is only aware of the value to format.
 * </p>
 * @author epesh
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public interface ColumnDecorator
{

    /**
     * Called after the object has been retrieved from the bean contained in the list. The decorate method is
     * responsible for transforming the object into a string to render in the page.
     * @param columnValue Object to decorate
     * @return String decorated object
     * @throws DecoratorException wrapper exception for any exception thrown during decoration
     */
    String decorate(Object columnValue) throws DecoratorException;

}
