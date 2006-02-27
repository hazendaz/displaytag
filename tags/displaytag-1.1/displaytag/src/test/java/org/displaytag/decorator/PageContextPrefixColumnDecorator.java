package org.displaytag.decorator;

import javax.servlet.jsp.PageContext;

import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;


/**
 * A column decorator which adds a prefix from the "prefix" pageContext attribute.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PageContextPrefixColumnDecorator implements DisplaytagColumnDecorator
{

    /**
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(Object, PageContext, MediaTypeEnum)
     */
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException
    {
        return pageContext.getAttribute("prefix").toString() + media.getName() + " " + columnValue;
    }

}