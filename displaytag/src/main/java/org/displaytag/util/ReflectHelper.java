/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

/**
 * Utility method for reflection.
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
