package org.displaytag.sample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.displaytag.decorator.ColumnDecorator;

/**
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class LongDateWrapper implements ColumnDecorator
{
	/**
	 * Field sdf
	 */
	private DateFormat mDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	/**
	 * Method decorate
	 * @param pColumnValue Object
	 * @return String
	 */
	public final String decorate(Object pColumnValue)
	{
		Date lDate = (Date) pColumnValue;
		return mDateFormat.format(lDate);
	}
}
