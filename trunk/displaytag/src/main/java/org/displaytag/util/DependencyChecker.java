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

import javax.servlet.jsp.JspTagException;

import org.displaytag.Messages;


/**
 * An user-friendly dependency checker. This will check the presence of libraries needed by displaytag, throwing an
 * Exception with an informative message if the library is missing or the version is not compatible.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class DependencyChecker
{

    /**
     * Has the commons-lang dependency been checked?
     */
    private static boolean commonsLangChecked;

    /**
     * Don't instantiate.
     */
    private DependencyChecker()
    {
        // unused
    }

    /**
     * Displaytag requires commons-lang 2.x or better; it is not compatible with earlier versions.
     * @throws JspTagException if the wrong library, or no library at all, is found.
     */
    public static void check() throws JspTagException
    {
        if (commonsLangChecked)
        {
            return;
        }
        try
        {
            // Do they have commons lang ?
            Class stringUtils = Class.forName("org.apache.commons.lang.StringUtils"); //$NON-NLS-1$
            try
            {
                // this method is new in commons-lang 2.0
                stringUtils.getMethod("capitalize", new Class[]{String.class}); //$NON-NLS-1$
            }
            catch (NoSuchMethodException ee)
            {
                throw new JspTagException(Messages.getString("DependencyChecker.lib.incompatible", //$NON-NLS-1$
                    new Object[]{"commons-lang", new Integer(2), "http://jakarta.apache.org/commons/lang"} //$NON-NLS-1$ //$NON-NLS-1$
                    ));
            }
        }
        catch (ClassNotFoundException e)
        {
            throw new JspTagException(Messages.getString("DependencyChecker.lib.missing", //$NON-NLS-1$
                new Object[]{"commons-lang", new Integer(2), "http://jakarta.apache.org/commons/lang"} //$NON-NLS-1$ //$NON-NLS-1$
                ));
        }
        commonsLangChecked = true;
    }

}