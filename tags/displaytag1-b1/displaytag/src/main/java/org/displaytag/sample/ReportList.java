package org.displaytag.sample;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Just a utility class for testing out the table and column tags.
 *
 * This List fills itself with objects and sorts them as though it where pulling
 * data from a report.  This list is used to show the various report oriented
 * examples (such as grouping, callbacks, and data exports).
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class ReportList extends ArrayList
{
	/**
	 * Creats a TestList that is filled with 20 ReportableListObject suitable for testing
	 */
	public ReportList()
	{
		super();

		for (int lCount = 0; lCount < 20; lCount++)
		{
			add(new ReportableListObject());
		}

		Collections.sort(this);
	}

	/**
	 * Creates a TestList that is filled with [size] ReportableListObject suitable for
	 * testing.
	 * @param pSize int
	 */
	public ReportList(int pSize)
	{
		super();

		for (int lCount = 0; lCount < pSize; lCount++)
		{
			add(new ReportableListObject());
		}

		Collections.sort(this);
	}
}
