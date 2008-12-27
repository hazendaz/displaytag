package org.displaytag.decorator;

import javax.servlet.jsp.PageContext;

import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;


/**
 * A column decorator which adds a "decorated: " prefix.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PrefixColumnDecorator implements DisplaytagColumnDecorator
{

    /**
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(java.lang.Object, javax.servlet.jsp.PageContext,
     * org.displaytag.properties.MediaTypeEnum)
     */
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException
    {
        return "decorated: " + columnValue;
    }

}