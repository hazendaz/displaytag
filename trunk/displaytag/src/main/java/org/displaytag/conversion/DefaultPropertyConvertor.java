package org.displaytag.conversion;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.Converter;


/**
 * A default implementation.  Local PropertyConvertors are encouraged to subclass this class, and dispatch all but
 * special cases to super().
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public class DefaultPropertyConvertor implements Converter
{
    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(DefaultPropertyConvertor.class);

    /**
     * Only handles Number; everything else is toString'd.
     * @param value   the value
     * @param type   the value -- must be Number
     * @return a Number; 0 if an error occurs in evaluation
     */
    public Object convert(Class type, Object value)
    {
        if (value == null)
        {
            throw new NullPointerException("Value cannot be null");
        }
        if (!Number.class.equals(type))
        {
            throw new UnsupportedOperationException("This class can only convert to Number.");
        }
        if (value instanceof Number)
        {
            return (Number) value;
        }
        return convertStringToNumber(value.toString());
    }

    /**
     * This implementation is just a suggestion.  It strips out some non-numeric characters; it is not
     * obviously not safe for most i18n currencies, etc.  It is here for convenience when
     * this class is locally extended.
     * @param value    the value to interpret
     * @return its value as a number
     */
    protected Number convertStringToNumber(String value)
    {
        String str = value;
        try
        {
            str = StringUtils.replaceChars(str, "$\n\r\t ", "");
            return Double.valueOf(str);
        }
        catch (NumberFormatException e)
        {
            // It cannot be handled - fall through and throw an exception
            log.warn("Cannot convert " + value + " to a number, " + e.getMessage()
                     + " -- assuming a value of zero.", e);
            return new Double(0);
        }
    }
}
