package org.displaytag.sample;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.ColumnDecorator;

/**
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class LongDateWrapper implements ColumnDecorator
{
    /**
     * Field sdf
     */
    private FastDateFormat dateFormat = FastDateFormat.getInstance("MM/dd/yyyy HH:mm:ss");

    /**
     * Method decorate
     * @param columnValue Object
     * @return String
     */
    public final String decorate(Object columnValue)
    {
        Date date = (Date) columnValue;
        return dateFormat.format(date);
    }
}
