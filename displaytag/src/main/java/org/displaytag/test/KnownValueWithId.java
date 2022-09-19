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
package org.displaytag.test;

/**
 * The Class KnownValueWithId.
 *
 * @author fgiust
 *
 * @version $Revision$ ($Author$)
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
