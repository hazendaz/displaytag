package org.displaytag.util;

import javax.servlet.jsp.JspTagException;


/**
 * An user-friendly dependency checker. This will check the presence of libraries needed by displaytag, throwing an
 * Exception with an informative message if the library is missing or the version is not compatible.
 * @author Fabrizo Giustina
 * @version $Revision$ ($Author$)
 */
public class DependencyChecker
{

    /**
     * Has the commons-lang dependency been checked?
     */
    private static boolean commonsLangChecked;

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
            Class stringUtils = Class.forName("org.apache.commons.lang.StringUtils");
            try
            {
                // this method is new in commons-lang 2.0
                stringUtils.getMethod("capitalize", new Class[]{String.class});
            }
            catch (NoSuchMethodException ee)
            {
                throw new JspTagException(
                    "\n\nYou appear to have an INCOMPATIBLE VERSION of the Commons Lang library.  \n"
                        + "Displaytag requires version 2 of this library, and you appear to have a prior version in \n"
                        + "your classpath.  You must remove this prior version AND ensure that ONLY version 2 is in \n"
                        + "your classpath.\n "
                        + "If commons-lang-1.x is in your classpath, be sure to remove it. \n"
                        + "Be sure to delete all cached or temporary jar files from your application server; Tomcat \n"
                        + "users should be sure to also check the CATALINA_HOME/shared folder; you may need to \n"
                        + "restart the server. \n"
                        + "commons-lang-2.jar is available in the displaytag distribution, or from the Jakarta \n"
                        + "website at http://jakarta.apache.org/commons \n\n.");
            }
        }
        catch (ClassNotFoundException e)
        {
            throw new JspTagException("You do not appear to have the Commons Lang library, version 2.  "
                + "commons-lang-2.jar is available in the displaytag distribution, or from the Jakarta website at "
                + "http://jakarta.apache.org/commons .  ");
        }
        commonsLangChecked = true;
    }


}