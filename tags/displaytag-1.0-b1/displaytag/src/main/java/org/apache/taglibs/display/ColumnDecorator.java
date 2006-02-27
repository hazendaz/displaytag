package org.apache.taglibs.display;

import org.displaytag.exception.DecoratorException;

/**
 * <p>Placeholder class to preserve compatibility with decorator created with older version of the display taglib</p>
 * <p>Never extend this class, you should always implements the <code>org.displaytag.decorator.ColumnDecorator</code>
 * interface<p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 * @deprecated use <code>org.displaytag.decorator.ColumnDecorator</code>
 */
public abstract class ColumnDecorator implements org.displaytag.decorator.ColumnDecorator
{
	/**
	 * @see org.displaytag.decorator.ColumnDecorator#decorate(Object)
	 */
	public abstract String decorate(Object pColumnValue) throws DecoratorException;

}
