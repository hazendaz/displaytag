/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.test;

/**
 * The Class KnownValueWithId.
 */
public class KnownValueWithId extends KnownValue {

    /** The id. */
    private String id;

    /**
     * Instantiates a new known value with id.
     *
     * @param id
     *            the id
     */
    public KnownValueWithId(final String id) {
        this.id = id;
    }

    /**
     * Getter for <code>objectId</code>.
     *
     * @return Returns the objectId.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Setter for <code>objectId</code>.
     *
     * @param objectId
     *            The objectId to set.
     */
    public void setId(final String objectId) {
        this.id = objectId;
    }

}
