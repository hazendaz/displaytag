package org.displaytag.decorator;

import org.apache.commons.lang.StringUtils;
import org.displaytag.exception.DecoratorException;


/**
 * A column decorator which returns 100/value with padding of spaces.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PercentualColumnDecorator implements ColumnDecorator
{

    /**
     * @see org.displaytag.decorator.ColumnDecorator#decorate(java.lang.Object)
     */
    public String decorate(Object columnValue) throws DecoratorException
    {
        int intValue = ((Number) columnValue).intValue();
        if (intValue == 0)
        {
            intValue = 1;
        }
        return StringUtils.leftPad(Integer.toString(100 / intValue), 3);
    }

}