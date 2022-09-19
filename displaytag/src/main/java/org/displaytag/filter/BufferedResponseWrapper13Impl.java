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
package org.displaytag.filter;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * J2ee 1.3 implementation of BufferedResponseWrapper. Need to extend HttpServletResponseWrapper for Weblogic
 * compatibility.
 *
 * @author rapruitt
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class BufferedResponseWrapper13Impl extends HttpServletResponseWrapper implements BufferedResponseWrapper {

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(BufferedResponseWrapper13Impl.class);

    /**
     * The buffered response.
     */
    private final CharArrayWriter outputWriter;

    /**
     * The outputWriter stream.
     */
    private final SimpleServletOutputStream servletOutputStream;

    /**
     * The contentType.
     */
    private String contentType;

    /**
     * The character encoding.
     */
    private String characterEncoding;

    /** The char enc set. */
    private boolean charEncSet;

    /**
     * If state is set, allow getOutputStream() to return the "real" output stream, elsewhere returns a internal buffer.
     */
    private boolean state;

    /**
     * Writer has been requested.
     */
    private boolean outRequested;

    /**
     * Instantiates a new buffered response wrapper 13 impl.
     *
     * @param httpServletResponse
     *            the response to wrap
     */
    public BufferedResponseWrapper13Impl(final HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        this.outputWriter = new CharArrayWriter();
        this.servletOutputStream = new SimpleServletOutputStream();
    }

    /**
     * Gets the content type.
     *
     * @return the content type
     *
     * @see org.displaytag.filter.BufferedResponseWrapper#getContentType()
     */
    @Override
    public String getContentType() {
        final StringBuilder ret = new StringBuilder(this.contentType);

        if (this.characterEncoding != null && this.charEncSet) {
            ret.append("; charset=").append(this.characterEncoding);
        }

        return this.contentType;
    }

    /**
     * The content type is NOT set on the wrapped response. You must set it manually. Overrides any previously set
     * value.
     *
     * @param type
     *            the content type.
     */
    @Override
    public void setContentType(final String type) {
        if (this.state) {
            BufferedResponseWrapper13Impl.log.debug("Allowing content type");
            this.getResponse().setContentType(type);
        }

        if (type == null) {
            this.contentType = null;
            return;
        }

        boolean hasCharEnc = false;
        final String charEnc = StringUtils.trim(StringUtils.substringAfter(type, "charset="));
        if (StringUtils.isNotEmpty(charEnc)) {
            hasCharEnc = true;
        }

        if (!hasCharEnc) {
            this.contentType = type;
            return;
        }

        this.contentType = StringUtils.substringBefore(type, ";");
        if (StringUtils.isNotEmpty(charEnc)) {
            this.characterEncoding = charEnc;
            this.charEncSet = true;
        }

    }

    /**
     * If the app server sets the character encoding of the response, it is sticky and you will not be able to change
     * it. Therefore it is intercepted here.
     *
     * @return the character encoding that was most recently set
     */
    @Override
    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    /**
     * Sets the character encoding.
     *
     * @param characterEncoding
     *            the new character encoding
     */
    @Override
    public void setCharacterEncoding(final String characterEncoding) {

        if (characterEncoding == null) {
            return;
        }

        if (this.state) {
            BufferedResponseWrapper13Impl.log.debug("Allowing character encoding");
            this.getResponse().setCharacterEncoding(characterEncoding);
        }

        this.characterEncoding = characterEncoding;
        this.charEncSet = true;
    }

    /**
     * Gets the writer.
     *
     * @return the writer
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     *
     * @see javax.servlet.ServletResponse#getWriter()
     */
    @Override
    public PrintWriter getWriter() throws IOException {

        if (this.state && !this.outRequested) {
            BufferedResponseWrapper13Impl.log.debug("getWriter() returned");

            // ok, exporting in progress, discard old data and go on streaming
            this.servletOutputStream.reset();
            this.outputWriter.reset();
            this.outRequested = true;
            return ((HttpServletResponse) this.getResponse()).getWriter();
        }

        return new PrintWriter(this.outputWriter);
    }

    /**
     * Flush the buffer, not the response.
     *
     * @throws IOException
     *             if encountered when flushing
     */
    @Override
    public void flushBuffer() throws IOException {
        if (this.outputWriter != null) {
            this.outputWriter.flush();
            this.servletOutputStream.outputStream.reset();
        }
    }

    /**
     * Gets the output stream.
     *
     * @return the output stream
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     *
     * @see javax.servlet.ServletResponse#getOutputStream()
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.state && !this.outRequested) {
            BufferedResponseWrapper13Impl.log.debug("getOutputStream() returned");

            // ok, exporting in progress, discard old data and go on streaming
            this.servletOutputStream.reset();
            this.outputWriter.reset();
            this.outRequested = true;
            return ((HttpServletResponse) this.getResponse()).getOutputStream();
        }
        return this.servletOutputStream;
    }

    /**
     * Adds the header.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     *
     * @see javax.servlet.http.HttpServletResponse#addHeader(java.lang.String, java.lang.String)
     */
    @Override
    public void addHeader(final String name, final String value) {
        // if the "magic parameter" is set, a table tag is going to call getOutputStream()
        if (TableTagParameters.PARAMETER_EXPORTING.equals(name)) {
            BufferedResponseWrapper13Impl.log.debug("Magic header received, real response is now accessible");
            this.state = true;
        } else if (!ArrayUtils.contains(BufferedResponseWrapper.FILTERED_HEADERS, StringUtils.lowerCase(name))) {
            ((HttpServletResponse) this.getResponse()).addHeader(name, value);
        }
    }

    /**
     * Checks if is out requested.
     *
     * @return true, if is out requested
     *
     * @see org.displaytag.filter.BufferedResponseWrapper#isOutRequested()
     */
    @Override
    public boolean isOutRequested() {
        return this.outRequested;
    }

    /**
     * Gets the content as string.
     *
     * @return the content as string
     *
     * @see org.displaytag.filter.BufferedResponseWrapper#getContentAsString()
     */
    @Override
    public String getContentAsString() {
        return this.outputWriter.toString() + this.servletOutputStream.toString();
    }

    /**
     * Sets the date header.
     *
     * @param name
     *            the name
     * @param date
     *            the date
     *
     * @see javax.servlet.http.HttpServletResponse#setDateHeader(java.lang.String, long)
     */
    @Override
    public void setDateHeader(final String name, final long date) {
        if (!ArrayUtils.contains(BufferedResponseWrapper.FILTERED_HEADERS, StringUtils.lowerCase(name))) {
            ((HttpServletResponse) this.getResponse()).setDateHeader(name, date);
        }
    }

    /**
     * Adds the date header.
     *
     * @param name
     *            the name
     * @param date
     *            the date
     *
     * @see javax.servlet.http.HttpServletResponse#addDateHeader(java.lang.String, long)
     */
    @Override
    public void addDateHeader(final String name, final long date) {
        if (!ArrayUtils.contains(BufferedResponseWrapper.FILTERED_HEADERS, StringUtils.lowerCase(name))) {
            ((HttpServletResponse) this.getResponse()).addDateHeader(name, date);
        }
    }

    /**
     * Sets the header.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     *
     * @see javax.servlet.http.HttpServletResponse#setHeader(java.lang.String, java.lang.String)
     */
    @Override
    public void setHeader(final String name, final String value) {
        if (!ArrayUtils.contains(BufferedResponseWrapper.FILTERED_HEADERS, StringUtils.lowerCase(name))) {
            ((HttpServletResponse) this.getResponse()).setHeader(name, value);
        }
    }

    /**
     * Sets the int header.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     *
     * @see javax.servlet.http.HttpServletResponse#setIntHeader(java.lang.String, int)
     */
    @Override
    public void setIntHeader(final String name, final int value) {
        if (!ArrayUtils.contains(BufferedResponseWrapper.FILTERED_HEADERS, StringUtils.lowerCase(name))) {
            ((HttpServletResponse) this.getResponse()).setIntHeader(name, value);
        }
    }

    /**
     * Adds the int header.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     *
     * @see javax.servlet.http.HttpServletResponse#addIntHeader(java.lang.String, int)
     */
    @Override
    public void addIntHeader(final String name, final int value) {
        if (!ArrayUtils.contains(BufferedResponseWrapper.FILTERED_HEADERS, StringUtils.lowerCase(name))) {
            ((HttpServletResponse) this.getResponse()).addIntHeader(name, value);
        }
    }

}