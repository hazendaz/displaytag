/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * Helper class for message bundle access.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class Messages
{

    /**
     * Base name for the bundle.
     */
    private static final String BUNDLE_NAME = "org.displaytag.messages"; //$NON-NLS-1$

    /**
     * Loaded ResourceBundle.
     */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Don't instantiate.
     */
    private Messages()
    {
        // unused
    }

    /**
     * Returns a message from the resource bundle.
     * @param key Message key.
     * @return message String.
     */
    public static String getString(String key)
    {
        try
        {
            return RESOURCE_BUNDLE.getString(key);
        }
        catch (MissingResourceException e)
        {
            return '!' + key + '!';
        }
    }

    /**
     * Reads a message from the resource bundle and format it using java MessageFormat.
     * @param key Message key.
     * @param parameters Parameters to pass to MessageFormat.format()
     * @return message String.
     */
    public static String getString(String key, Object[] parameters)
    {
        String baseMsg;
        try
        {
            baseMsg = RESOURCE_BUNDLE.getString(key);
        }
        catch (MissingResourceException e)
        {
            return '!' + key + '!';
        }

        return MessageFormat.format(baseMsg, parameters);
    }

    /**
     * Reads a message from the resource bundle and format it using java MessageFormat.
     * @param key Message key.
     * @param parameter single parameter to pass to MessageFormat.format()
     * @return message String.
     */
    public static String getString(String key, Object parameter)
    {
        return getString(key, new Object[]{parameter});
    }
}