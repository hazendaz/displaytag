/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Helper class for message bundle access.
 */
public final class Messages {

    /**
     * Base name for the bundle.
     */
    private static final String BUNDLE_NAME = "org.displaytag.messages"; //$NON-NLS-1$

    /**
     * Loaded ResourceBundle.
     */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(Messages.BUNDLE_NAME);

    /**
     * Don't instantiate.
     */
    private Messages() {
        // unused
    }

    /**
     * Returns a message from the resource bundle.
     *
     * @param key
     *            Message key.
     *
     * @return message String.
     */
    public static String getString(final String key) {
        try {
            return Messages.RESOURCE_BUNDLE.getString(key);
        } catch (final MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    /**
     * Reads a message from the resource bundle and format it using java MessageFormat.
     *
     * @param key
     *            Message key.
     * @param parameters
     *            Parameters to pass to MessageFormat.format()
     *
     * @return message String.
     */
    public static String getString(final String key, final Object[] parameters) {
        String baseMsg;
        try {
            baseMsg = Messages.RESOURCE_BUNDLE.getString(key);
        } catch (final MissingResourceException e) {
            return '!' + key + '!';
        }

        return MessageFormat.format(baseMsg, parameters);
    }

    /**
     * Reads a message from the resource bundle and format it using java MessageFormat.
     *
     * @param key
     *            Message key.
     * @param parameter
     *            single parameter to pass to MessageFormat.format()
     *
     * @return message String.
     */
    public static String getString(final String key, final Object parameter) {
        return Messages.getString(key, new Object[] { parameter });
    }
}
