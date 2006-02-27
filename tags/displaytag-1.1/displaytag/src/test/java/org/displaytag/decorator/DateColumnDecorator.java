package org.displaytag.decorator;

import java.util.Locale;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;


/**
 * A test column decorator for dates.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class DateColumnDecorator implements DisplaytagColumnDecorator
{

    /**
     * date formatter.
     */
    FastDateFormat dateFormat = FastDateFormat.getInstance("EEEE", Locale.ENGLISH);

    /**
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(java.lang.Object, javax.servlet.jsp.PageContext,
     * org.displaytag.properties.MediaTypeEnum)
     */
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException
    {
        return dateFormat.format(columnValue);
    }

}