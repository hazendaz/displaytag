package org.displaytag.pagination;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>Object representing a page</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class NumberedPage
{

	/**
	 * page number
	 */
	private int mNumber;

	/**
	 * is the page selected?
	 */
	private boolean mSelected;

	/**
	 * Creates a new page with the specified number
	 * @param pNumber page number
	 * @param pSelected is the page selected?
	 */
	public NumberedPage(int pNumber, boolean pSelected)
	{
		mNumber = pNumber;
		mSelected = pSelected;
	}

	/**
	 * return the page number
	 * @return the page number
	 */
	public int getNumber()
	{
		return mNumber;
	}

	/**
	 * is the page selected?
	 * @return true if the page is slected
	 */
	public boolean getSelected()
	{
		return mSelected;
	}

	/**
	 * return a String representation of the page
	 * @return String representation of the page
	 */
	public String toString()
	{
		return new ToStringBuilder(this).append("number", mNumber).append("selected", mSelected).toString();
	}

}