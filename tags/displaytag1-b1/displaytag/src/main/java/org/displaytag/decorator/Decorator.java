package org.displaytag.decorator;

import java.beans.PropertyDescriptor;
import java.util.HashMap;

import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * <p>This class provides some basic functionality for all objects which serve
 * as decorators for the objects in the List being displayed.<p>
 * <p>Decorator should never be subclassed directly. Use TableDecorator instead</p>
 * @author mraible
 * @version $Revision$ ($Author$)
 **/
public abstract class Decorator
{

	/**
	 * page context
	 */
	private PageContext mPageContext;

	/**
	 * property info cache
	 */
	private static HashMap lPropertyMap = new HashMap();

	/**
	 * decorated object. Usually a List
	 */
	private Object mDecoratedObject;

	/**
	 * Method init
	 * @param pPageContext PageContext
	 * @param pDecoratedObject decorated object (usually a list)
	 */
	public void init(PageContext pPageContext, Object pDecoratedObject)
	{
		mPageContext = pPageContext;
		mDecoratedObject = pDecoratedObject;
	}

	/**
	 * returns the page context
	 * @return PageContext
	 */
	public PageContext getPageContext()
	{
		return mPageContext;
	}

	/**
	 * returns the decorated object
	 * @return Object
	 */
	public Object getDecoratedObject()
	{
		return mDecoratedObject;
	}

	/**
	 * Method called at the end of evaluation
	 */
	public void finish()
	{
		mPageContext = null;
		mDecoratedObject = null;
	}

	/**
	 * look for a getter for the given property using introspection
	 * @param pPropertyName name of the property to check
	 * @return boolean true if the decorator has a getter for the given property
	 */
	public boolean searchGetterFor(String pPropertyName)
	{
		PropertyDescriptor[] lDescriptors = PropertyUtils.getPropertyDescriptors(getClass());

		// iterate on property descriptors
		for (int lCount = 0; lCount < lDescriptors.length; lCount++)
		{
			if (pPropertyName.equals(lDescriptors[lCount].getName()))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Check if a getter exists for a given property. Uses cached info if property has already been requested.
	 * This method only check for a simple property, if pPropertyName contains multiple tokens only the first part is
	 * evaluated
	 * @param pPropertyName name of the property to check
	 * @return boolean true if the decorator has a getter for the given property
	 */
	public boolean hasGetterFor(String pPropertyName)
	{
		String lSimpleProperty = pPropertyName;

		// get the simple (not nested) bean property
		if ((lSimpleProperty != null) && (lSimpleProperty.indexOf(".") > 0))
		{
			lSimpleProperty = lSimpleProperty.substring(0, lSimpleProperty.indexOf("."));
		}

		Boolean lCachedResult;

		if ((lCachedResult = (Boolean) lPropertyMap.get(lSimpleProperty)) != null)
		{
			return lCachedResult.booleanValue();
		}

		// not already cached... check
		boolean lHasGetter = searchGetterFor(lSimpleProperty);

		// save in cache
		lPropertyMap.put(lSimpleProperty, new Boolean(lHasGetter));

		// and return
		return lHasGetter;

	}

}
