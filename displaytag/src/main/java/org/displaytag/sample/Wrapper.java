package org.displaytag.sample;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.displaytag.decorator.TableDecorator;

/**
 * This class is a decorator of the TestObjects that we keep in our List.  This
 * class provides a number of methods for formatting data, creating dynamic
 * links, and exercising some aspects of the display:table API functionality
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class Wrapper extends TableDecorator
{
	/**
	 * Field datefmt
	 */
	private SimpleDateFormat mDateFormat = null;
	/**
	 * Field moneyfmt
	 */
	private DecimalFormat mMoneyFormat = null;

	/**
	 * Creates a new Wrapper decorator who's job is to reformat some of the
	 * data located in our TestObject's.
	 */

	public Wrapper()
	{
		super();

		// Formats for displaying dates and money.

		this.mDateFormat = new SimpleDateFormat("MM/dd/yy");
		this.mMoneyFormat = new DecimalFormat("$ #,###,###.00");
	}

	/**
	 * Method getNullValue
	 * @return String
	 */
	public String getNullValue()
	{
		return null;
	}

	/**
	 * Returns the date as a String in MM/dd/yy format
	 * @return String
	 */

	public String getDate()
	{
		return this.mDateFormat.format(((ListObject) this.getDecoratedObject()).getDate());
	}

	/**
	 * Returns the money as a String in $ #,###,###.00 format
	 * @return String
	 */

	public String getMoney()
	{
		return this.mMoneyFormat.format(((ListObject) this.getDecoratedObject()).getMoney());
	}

	/**
	 * Returns the TestObject's ID as a hyperlink that the person can click on
	 * and "drill down" for more details.
	 * @return String
	 */
	public String getLink1()
	{
		ListObject lObject = (ListObject) getDecoratedObject();
		int lIndex = getListIndex();

		return "<a href=\"details.jsp?index=" + lIndex + "\">" + lObject.getId() + "</a>";
	}

	/**
	 * Returns an "action bar" of sorts that allow the user to perform various
	 * actions on the TestObject based on it's id.
	 * @return String
	 */
	public String getLink2()
	{
		ListObject lObject = (ListObject) getDecoratedObject();
		int lId = lObject.getId();

		return "<a href=\"details.jsp?id="
			+ lId
			+ "&action=view\">View</a> | "
			+ "<a href=\"details.jsp?id="
			+ lId
			+ "&action=edit\">Edit</a> | "
			+ "<a href=\"details.jsp?id="
			+ lId
			+ "&action=delete\">Delete</a>";
	}
}
