package org.displaytag.decorator;

import java.util.Locale;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.exception.DecoratorException;


/**
 * A test column decorator for dates.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class DateColumnDecorator implements ColumnDecorator
{

    /**
     * date formatter.
     */
    FastDateFormat dateFormat = FastDateFormat.getInstance("EEEE", Locale.ENGLISH);

    /**
     * @see org.displaytag.decorator.ColumnDecorator#decorate(java.lang.Object)
     */
    public String decorate(Object columnValue) throws DecoratorException
    {
        return dateFormat.format(columnValue);
    }

}