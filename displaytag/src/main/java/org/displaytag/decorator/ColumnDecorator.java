package org.displaytag.decorator;

import org.displaytag.exception.DecoratorException;

/**
 * <p>Interface for simple column decorators.</p>
 * <p>A column decorator is called after the object has been retrieved and it can "transform" the object before the
 * rendering. A column decorator is simply an object formatter, and it is only aware of the value to format.</p>
 * @author epesh
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
