package org.displaytag.decorator;

import org.displaytag.exception.DecoratorException;


/**
 * A column decorator which adds a "decorated: " prefix.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PrefixColumnDecorator implements ColumnDecorator
{

    /**
     * @see org.displaytag.decorator.ColumnDecorator#decorate(java.lang.Object)
     */
    public String decorate(Object columnValue) throws DecoratorException
    {
        return "decorated: " + columnValue;
    }

}