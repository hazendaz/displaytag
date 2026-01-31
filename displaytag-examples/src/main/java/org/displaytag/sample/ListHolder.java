/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.sample;

import java.util.List;

/**
 * Simple objects which holds a list.
 */
public class ListHolder extends Object {

    /**
     * contained list.
     */
    private List<ListObject> list;

    /**
     * Instantiate a new ListHolder and initialize a TestList with 5 elements.
     */
    public ListHolder() {
        this.list = new TestList(15, false);
    }

    /**
     * Returns the contained list.
     *
     * @return a TestList with 15 elements
     */
    public final List<ListObject> getList() {
        return this.list;
    }
}
