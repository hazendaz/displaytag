/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.filter;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * A simple implementation of ServletOutputStream which wraps a ByteArrayOutputStream.
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
        return this.outputStream.toString(StandardCharsets.UTF_8);
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
