/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.util;

import java.lang.reflect.Method;
import java.net.URLEncoder;

import org.displaytag.Messages;


/**
 * Wrapper class to choose between the java 1.4 implementation of UrlEncoder.encode(), when available, or the java 1.3
 * implementation.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class CompatibleUrlEncoder
{

    /**
     * j2se 1.4 encode method, used by reflection if available.
     */
    private static Method encodeMethod14;

    /**
     * don't instantiate.
     */
    private CompatibleUrlEncoder()
    {
        // unused
    }

    static
    {
        // URLEncoder.encode(String) has been deprecated in J2SE 1.4.
        // Take advantage of the new method URLEncoder.encode(String, enc) if J2SE 1.4 is used.
        try
        {
            Class urlEncoderClass = Class.forName("java.net.URLEncoder"); //$NON-NLS-1$
            encodeMethod14 = urlEncoderClass.getMethod("encode", new Class[]{String.class, String.class}); //$NON-NLS-1$
        }
        catch (Throwable ex)
        {
            // encodeMethod14 will be null if exception
        }
    }

    /**
     * Called encodeUrl using j2se 1.4 version by reflection if available, or backward compatible version.
     * @param url url to be encoded
     * @param encoding encoding to use for jse 1.4
     * @return encoded url.
     */
    public static String encode(String url, String encoding)
    {
        if (encodeMethod14 != null)
        {
            Object[] methodArgs = new Object[2];
            methodArgs[0] = url;

            if (encoding != null)
            {
                methodArgs[1] = encoding;
            }
            else
            {
                methodArgs[1] = "UTF8"; //$NON-NLS-1$
            }

            try
            {
                return (String) encodeMethod14.invoke(null, methodArgs);
            }
            catch (Throwable ex)
            {
                throw new RuntimeException(Messages.getString("CompatibleUrlEncoder.errorinvoking", //$NON-NLS-1$
                    new Object[]{encoding, ex.getMessage()}));
            }
        }

        // must use J2SE 1.3 version
        return URLEncoder.encode(url);

    }
}
