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

/**
 * Utility method for reflection.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class ReflectHelper
{

    /**
     * Don't instantiate.
     */
    private ReflectHelper()
    {
        // unused
    }

    /**
     * Tries to load a class with more classloaders. Can be useful in J2EE applications if jar is loaded from a
     * different classloader than user classes. If class is not found using the standard classloader, tries whit the
     * thread classloader.
     * @param className class name
     * @return Class loaded class
     * @throws ClassNotFoundException if none of the ClassLoaders is able to found the reuested class
     */
    public static Class classForName(String className) throws ClassNotFoundException
    {
        try
        {
            // trying with the default ClassLoader
            return Class.forName(className);
        }
        catch (ClassNotFoundException cnfe)
        {
            try
            {
                // trying with thread ClassLoader
                Thread thread = Thread.currentThread();
                ClassLoader threadClassLoader = thread.getContextClassLoader();
                return Class.forName(className, false, threadClassLoader);
            }
            catch (ClassNotFoundException cnfe2)
            {
                throw cnfe2;
            }
        }
    }

}