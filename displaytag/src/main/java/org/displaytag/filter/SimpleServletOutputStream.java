/*
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
package org.displaytag.filter;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * A simple implementation of ServletOutputStream which wraps a ByteArrayOutputStream.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class SimpleServletOutputStream extends ServletOutputStream {

    /**
     * My outputWriter stream, a buffer.
     */
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final int b) {
        this.outputStream.write(b);
    }

    /**
     * Get the contents of the outputStream.
     *
     * @return contents of the outputStream
     */
    @Override
    public String toString() {
        return this.outputStream.toString();
    }

    /**
     * Reset the wrapped ByteArrayOutputStream.
     */
    public void reset() {
        this.outputStream.reset();
    }

    /**
     * Checks if is ready.
     *
     * @return true, if is ready
     */
    // TODO Do not add override as this is intended for very old legacy support and otherwise has no value beyond
    // compilation.
    @Override
    public boolean isReady() {
        return false;
    }

    /**
     * Sets the write listener.
     *
     * @param writeListener
     *            the new write listener
     */
    // TODO Do not add override as this is intended for very old legacy support and otherwise has no value beyond
    // compilation.
    @Override
    public void setWriteListener(final WriteListener writeListener) {
        // TODO Not yet supported
    }
}