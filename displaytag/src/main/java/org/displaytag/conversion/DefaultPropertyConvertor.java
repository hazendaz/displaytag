package org.displaytag.conversion;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * A default implementation.  Local PropertyConvertors are encouraged to subclass this class, and dispatch all but
 * special cases to super().
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public class DefaultPropertyConvertor implements PropertyConvertor
{
    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(DefaultPropertyConvertor.class);

    /**
     * Only handles Number; everything else is toString'd.
     * @param value   the value
     * @return a Number; 0 if an error occurs in evaluation
     */
    public Number asNumber(Object value)
    {
        if (value == null)
        {
            throw new NullPointerException("Value cannot be null");
        }
        if (value instanceof Number)
        {
            return (Number) value;
        }
        return convertStringToNumber(value.toString());
    }

    /**
     * This implementation is just a suggestion.  It strips out all non-numeric characters; it is not obviously not safe
     * for most i18n currencies, etc.  It is here for convenience when this class is locally extended.
     * @param value    the value to interpret
     * @return its value as a number
     */
    protected Number convertStringToNumber(String value)
    {
        String str = value;
        try
        {
            if (str.indexOf(",") > -1)
            {
                str = StringUtils.replace(str, ",", "");
            }
            if (str.indexOf("$") > -1)
            {
                str = StringUtils.replace(str, "$", "");
            }
            if (str.indexOf("\n") > -1)
            {
                str = StringUtils.replace(str, "\n", "");
            }
            if (str.indexOf("\r") > -1)
            {
                str = StringUtils.replace(str, "\r", "");
            }
            if (str.indexOf("\t") > -1)
            {
                str = StringUtils.replace(str, "\t", "");
            }
            str = str.trim();
            return Double.valueOf(str);
        }
        catch (NumberFormatException e)
        {
            // It cannot be handled - fall through and throw an exception
            log.warn("Cannot convert " + value + " to a number, " + e.getMessage() + " -- assuming a value of zero.");
            return new Double(0);
        }
    }
}
