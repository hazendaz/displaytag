package org.displaytag.decorator;

import org.displaytag.exception.DecoratorException;

/**
 * <p>Interface for simple column decorators.</p>
 * <p>A column decorator is called after the object has been retrieved and it can "transform" the object before the
 * rendering</p>
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public interface ColumnDecorator
{

    /**
     * Method called to decorate the underlining object
     * @param columnValue Object to decorate
     * @return String decorated object
     * @throws DecoratorException wrapper exception for any exception thrown during decoration
     */
    String decorate(Object columnValue) throws DecoratorException;

}
