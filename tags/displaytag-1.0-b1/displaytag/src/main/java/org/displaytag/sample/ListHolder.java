package org.displaytag.sample;

import java.util.List;

/**
 * One line description of what this class does.
 *
 * More detailed class description, including examples of usage if applicable.
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class ListHolder extends Object
{
	/**
	 * Field myList
	 */
	private List mList;

	/**
	 * Constructor for ListHolder
	 */
	public ListHolder()
	{
		mList = new TestList(15);
	}

	/**
	 * Method getList
	 * @return List
	 */
	public final List getList()
	{
		return mList;
	}
}
