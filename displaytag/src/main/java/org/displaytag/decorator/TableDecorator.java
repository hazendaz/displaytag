package org.displaytag.decorator;

/**
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public abstract class TableDecorator extends Decorator
{
	/**
	 * Field mObject
	 */
	private Object mCurrentRowObject;

	/**
	 * Field mViewIndex
	 */
	private int mViewIndex = -1;

	/**
	 * Field mListIndex
	 */
	private int mListIndex = -1;

	/**
	 * Method getViewIndex
	 * @return int
	 */
	public int getViewIndex()
	{
		return mViewIndex;
	}

	/**
	 * Method getListIndex
	 * @return int
	 */
	public int getListIndex()
	{
		return mListIndex;
	}

	/**
	 * Method initRow
	 * @param pObject Object
	 * @param pViewIndex int
	 * @param pListIndex int
	 */
	public void initRow(Object pObject, int pViewIndex, int pListIndex)
	{
		mCurrentRowObject = pObject;
		mViewIndex = pViewIndex;
		mListIndex = pListIndex;
	}

	/**
	 * Method called at the beginning of a row
	 * @return String
	 */
	public String startRow()
	{
		return "";
	}

	/**
	 * Method called at the end of a row
	 * @return String
	 */
	public String finishRow()
	{
		return "";
	}

	/**
	 * Method called at the end of evaluation
	 */
	public void finish()
	{
		mCurrentRowObject = null;
		super.finish();
	}

	/**
	 * Returns the currentRowObject.
	 * @return Object
	 */
	public Object getCurrentRowObject()
	{
		return mCurrentRowObject;
	}

}
