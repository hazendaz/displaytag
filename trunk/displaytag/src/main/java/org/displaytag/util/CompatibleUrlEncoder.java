package org.displaytag.util;

import java.lang.reflect.Method;
import java.net.URLEncoder;


/**
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class CompatibleUrlEncoder
{

    /**
     * j2se 1.4 encode method, used by reflection if available.
     */
    private static Method encodeMethod14;

    static
    {
        // URLEncoder.encode(String) has been deprecated in J2SE 1.4.
        // Take advantage of the new method URLEncoder.encode(String, enc) if J2SE 1.4 is used.
        try
        {
            Class urlEncoderClass = Class.forName("java.net.URLEncoder");
            encodeMethod14 = urlEncoderClass.getMethod("encode", new Class[]{String.class, String.class});
        }
        catch (Throwable ex)
        {
            // encodeMethod14 will be null if exception
        }
    }

    /**
     * Called encodeUrl using j2se 1.4 version by reflection if available, or backward compatible version.
     * @param url url to be encoded
     * @return encoded url.
     */
    public static String encode(String url, String encoding)
    {
        if (encodeMethod14 != null)
        {
            Object[] methodArgs = new Object[2];
            methodArgs[0] = url;
            methodArgs[1] = encoding;

            try
            {
                return (String) encodeMethod14.invoke(null, methodArgs);
            }
            catch (Exception ex)
            {
                throw new RuntimeException("System error invoking URLEncoder.encode() by reflection.", ex);
            }
        }
        else
        {
            // must use J2SE 1.3 version
            return URLEncoder.encode(url);
        }
    }

}
