package org.displaytag.decorator;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;


/**
 * A column decorator which returns 100/value with padding of spaces.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PercentualColumnDecorator implements DisplaytagColumnDecorator
{

    /**
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(java.lang.Object, javax.servlet.jsp.PageContext,
     * org.displaytag.properties.MediaTypeEnum)
     */
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException
    {

        int intValue = ((Number) columnValue).intValue();
        if (intValue == 0)
        {
            intValue = 1;
        }
        return StringUtils.leftPad(Integer.toString(100 / intValue), 3);
    }

}