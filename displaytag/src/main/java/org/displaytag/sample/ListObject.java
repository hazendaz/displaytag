package org.displaytag.sample;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

/**
 * Just a test class that returns columns of data that are useful for testing
 * out the ListTag class and ListColumn class.
 *
 * More detailed class description, including examples of usage if applicable.
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class ListObject extends Object
{
	/**
	 * Field rnd
	 */
	private static Random rnd = new Random();
	/**
	 * Field words
	 */
	private static List lWords = null;

	/**
	 * Field mId
	 */
	private int mId = -1;

	/**
	 * Field name
	 */
	private String mName;
	/**
	 * Field email
	 */
	private String mEmail;
	/**
	 * Field date
	 */
	private Date mDate;
	/**
	 * Field money
	 */
	private double mMoney;
	/**
	 * Field description
	 */
	private String mDescription;
	/**
	 * Field longDescription
	 */
	private String mLongDescription;
	/**
	 * Field status
	 */
	private String mStatus;
	/**
	 * Field url
	 */
	private String mUrl;

	/**
	 * sub list to test nested tables
	 */
	private ArrayList mSubList;

	/**
	 * Constructor for ListObject
	 */
	public ListObject()
	{
		setupRandomData();
	}

	/**
	 * Method getId
	 * @return int
	 */
	public int getId()
	{
		return mId;
	}

	/**
	 * Method setID
	 * @param pId int
	 */
	public void setID(int pId)
	{
		mId = pId;
	}

	/**
	 * Method getName
	 * @return String
	 */
	public String getName()
	{
		return mName;
	}

	/**
	 * Method getEmail
	 * @return String
	 */
	public String getEmail()
	{
		return mEmail;
	}

	/**
	 * Method setEmail
	 * @param pEmail String
	 */
	public void setEmail(String pEmail)
	{
		mEmail = pEmail;
	}

	/**
	 * Method getDate
	 * @return Date
	 */
	public Date getDate()
	{
		return mDate;
	}

	/**
	 * Method getMoney
	 * @return double
	 */
	public double getMoney()
	{
		return mMoney;
	}

	/**
	 * Method getDescription
	 * @return String
	 */
	public String getDescription()
	{
		return mDescription;
	}

	/**
	 * Method getLongDescription
	 * @return String
	 */
	public String getLongDescription()
	{
		return mLongDescription;
	}

	/**
	 * Method getStatus
	 * @return String
	 */
	public String getStatus()
	{
		return mStatus;
	}

	/**
	 * Method getUrl
	 * @return String
	 */
	public String getUrl()
	{
		return mUrl;
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
	 * Method toString
	 * @return String
	 */
	public String toString()
	{
		return "ListObject(" + mId + ")";
	}

	/**
	 * Method toDetailedString
	 * @return String
	 */
	public String toDetailedString()
	{
		return "ID:          "
			+ mId
			+ "\n"
			+ "Name:        "
			+ mName
			+ "\n"
			+ "Email:       "
			+ mEmail
			+ "\n"
			+ "Date:        "
			+ mDate
			+ "\n"
			+ "Money:       "
			+ mMoney
			+ "\n"
			+ "Description: "
			+ mDescription
			+ "\n"
			+ "Status:      "
			+ mStatus
			+ "\n"
			+ "URL:         "
			+ mUrl
			+ "\n";
	}

	/**
	 * Method setupRandomData
	 */
	public void setupRandomData()
	{
		mId = rnd.nextInt(99998) + 1;
		mMoney = (rnd.nextInt(999998) + 1) / 100;

		String lFirst = getRandomWord();
		String lLast = getRandomWord();

		mName = StringUtils.capitalise(lFirst) + " " + StringUtils.capitalise(lLast);

		mEmail = lFirst + "-" + lLast + "@" + getRandomWord() + ".com";

		Calendar lCalendar = Calendar.getInstance();
		lCalendar.add(Calendar.DATE, 365 - rnd.nextInt(730));
		mDate = lCalendar.getTime();

		mDescription = getRandomWord() + " " + getRandomWord() + "...";

		mLongDescription =
			getRandomWord()
				+ " "
				+ getRandomWord()
				+ " "
				+ getRandomWord()
				+ " "
				+ getRandomWord()
				+ " "
				+ getRandomWord()
				+ " "
				+ getRandomWord()
				+ " "
				+ getRandomWord()
				+ " "
				+ getRandomWord()
				+ " "
				+ getRandomWord()
				+ " "
				+ getRandomWord()
				+ " "
				+ getRandomWord();

		mStatus = getRandomWord().toUpperCase();

		// added sublist for testing of nested tables
		mSubList = new ArrayList();
		mSubList.add(new SubListItem(getRandomWord(), getRandomWord() + "@" + getRandomWord() + ".com"));
		mSubList.add(new SubListItem(getRandomWord(), getRandomWord() + "@" + getRandomWord() + ".com"));
		mSubList.add(new SubListItem(getRandomWord(), getRandomWord() + "@" + getRandomWord() + ".com"));

		mUrl = "http://www." + lLast + ".org/";
	}

	/**
	 * Method getRandomWord
	 * @return String
	 */
	public String getRandomWord()
	{
		if (lWords == null)
		{
			setupWordBase();
		}

		return ((String) lWords.get(rnd.nextInt(lWords.size()))).toLowerCase();
	}

	/**
	 * Method setupWordBase
	 */
	public void setupWordBase()
	{
		String lLoremIpsum =
			"Lorem ipsum dolor sit amet consetetur sadipscing elitr sed diam nonumy "
				+ "eirmod tempor invidunt ut labore et dolore magna aliquyam erat sed diam "
				+ "voluptua At vero eos et accusam et justo duo dolores et ea rebum Stet "
				+ "clita kasd gubergren no sea takimata sanctus est Lorem ipsum dolor sit "
				+ "amet Lorem ipsum dolor sit amet consetetur sadipscing elitr sed diam "
				+ "nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat "
				+ "sed diam voluptua At vero eos et accusam et justo duo dolores et ea "
				+ "rebum Stet clita kasd gubergren no sea takimata sanctus est Lorem "
				+ "ipsum dolor sit amet Lorem ipsum dolor sit amet consetetur sadipscing "
				+ "elitr sed diam nonumy eirmod tempor invidunt ut labore et dolore magna "
				+ "aliquyam erat sed diam voluptua At vero eos et accusam et justo duo "
				+ "dolores et ea rebum Stet clita kasd gubergren no sea takimata sanctus "
				+ "est Lorem ipsum dolor sit amet "
				+ "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse "
				+ "molestie consequat vel illum dolore eu feugiat nulla facilisis at vero "
				+ "eros et accumsan et iusto odio dignissim qui blandit praesent luptatum "
				+ "zzril delenit augue duis dolore te feugait nulla facilisi Lorem ipsum "
				+ "dolor sit amet consectetuer adipiscing elit sed diam nonummy nibh "
				+ "euismod tincidunt ut laoreet dolore magna aliquam erat volutpat "
				+ "Ut wisi enim ad minim veniam quis nostrud exerci tation ullamcorper "
				+ "suscipit lobortis nisl ut aliquip ex ea commodo consequat Duis autem "
				+ "vel eum iriure dolor in hendrerit in vulputate velit esse molestie "
				+ "consequat vel illum dolore eu feugiat nulla facilisis at vero eros et "
				+ "accumsan et iusto odio dignissim qui blandit praesent luptatum zzril "
				+ "delenit augue duis dolore te feugait nulla facilisi";

		lWords = new ArrayList();
		StringTokenizer lTokenizer = new StringTokenizer(lLoremIpsum);
		while (lTokenizer.hasMoreTokens())
		{
			String lWord = lTokenizer.nextToken();
			lWords.add(lWord);
		}
	}

	/**
	 * Returns the subList.
	 * @return ArrayList
	 */
	public ArrayList getSubList()
	{
		return mSubList;
	}

	/**
	 * <p>Inner class used in testing nested tables</p>
	 * @author fgiust
	 */
	public class SubListItem
	{

		/**
		 * Field mName
		 */
		private String mName;
		/**
		 * Field mEmail
		 */
		private String mEmail;

		/**
		 * Constructor for SubListItem
		 * @param pName String
		 * @param pEmail String
		 */
		public SubListItem(String pName, String pEmail)
		{
			mName = pName;
			mEmail = pEmail;
		}

		/**
		 * Returns the name.
		 * @return String
		 */
		public String getName()
		{
			return mName;
		}

		/**
		 * Returns the email.
		 * @return String
		 */
		public String getEmail()
		{
			return mEmail;
		}

	}

}
