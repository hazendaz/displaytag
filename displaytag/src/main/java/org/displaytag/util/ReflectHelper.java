/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.util;

/**
 * Utility method for reflection.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public final class ReflectHelper {

    /**
     * Don't instantiate.
     */
    private ReflectHelper() {
        // unused
    }

    /**
     * Tries to load a class with more classloaders. Can be useful in J2EE applications if jar is loaded from a
     * different classloader than user classes. If class is not found using the standard classloader, tries whit the
     * thread classloader.
     *
     * @param className
     *            class name
     *
     * @return Class loaded class
     *
     * @throws ClassNotFoundException
     *             if none of the ClassLoaders is able to found the reuested class
     */
    public static Class<?> classForName(final String className) throws ClassNotFoundException {
        try {
            // trying with the default ClassLoader
            return Class.forName(className);
        } catch (final ClassNotFoundException cnfe) {
            try {
                // trying with thread ClassLoader
                final Thread thread = Thread.currentThread();
                final ClassLoader threadClassLoader = thread.getContextClassLoader();
                return Class.forName(className, false, threadClassLoader);
            } catch (final ClassNotFoundException cnfe2) {
                throw cnfe2;
            }
        }
    }

}
