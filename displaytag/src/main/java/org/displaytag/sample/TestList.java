package org.displaytag.sample;

import java.util.ArrayList;
import java.util.Random;

/**
 * Just a utility class for testing out the table and column tags.
 *
 * When this class is created, it loads itself with a number of ListObjects
 * that are shown throughout the various example pages that exercise the table
 * object.  If created via the default constructor, this loads itself with 60
 * ListObjects.
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class TestList extends ArrayList
{

	/**
	 * Creats a TestList that is filled with 60 ListObjects suitable for testing
	 */
	public TestList()
	{
		super();

		for (int lCount = 0; lCount < 60; lCount++)
		{
			add(new ListObject());
		}
	}

	/**
	 * Creates a TestList that is filled with [size] ListObjects suitable for
	 * testing.
	 * @param pSize int
	 */
	public TestList(int pSize)
	{
		super();

		for (int lCount = 0; lCount < pSize; lCount++)
		{
			add(new ListObject());
		}
	}

	/**
	 * Constructor for TestList
	 * @param pDuplicates boolean
	 * @param pSize int
	 */
	public TestList(boolean pDuplicates, int pSize)
	{
		super();

		// generate a random number between 1 and 3 and duplicate that many number of times.
		for (int lCount = 0; lCount < pSize; lCount++)
		{

			ListObject lObject1 = new ListObject();
			ListObject lObject2 = new ListObject();
			ListObject lObject3 = new ListObject();

			int lRandom = new Random().nextInt(3);
			for (int lCount2 = 0; lCount2 <= lRandom; lCount2++)
			{
				add(lObject1);

			}

			lObject1.setID(lObject2.getId());

			lRandom = new Random().nextInt(3);
			for (int lCount2 = 0; lCount2 <= lRandom; lCount2++)
			{
				add(lObject1);
				add(lObject2);

			}

			lObject1.setEmail(lObject3.getEmail());

			lRandom = new java.util.Random().nextInt(3);
			for (int lCount2 = 0; lCount2 <= lRandom; lCount2++)
			{
				add(lObject1);
			}
		}
	}

	/**
	 * Method getItem. Returns a ListObject using get(index) from the Array
	 * @param pIndex int
	 * @return ListObject
	 */
	public ListObject getItem(int pIndex)
	{
		return (ListObject) super.get(pIndex);
	}

}
