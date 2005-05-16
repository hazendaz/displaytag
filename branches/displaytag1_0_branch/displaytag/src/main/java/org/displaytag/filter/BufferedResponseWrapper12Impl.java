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
package org.displaytag.filter;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.tags.TableTagParameters;


/**
 * J2ee 1.2 implementation of BufferedResponseWrapper..
 * @author rapruitt
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class BufferedResponseWrapper12Impl implements BufferedResponseWrapper // don't extend j2ee 1.3
// HttpServletResponseWrapper
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(BufferedResponseWrapper12Impl.class);

    /**
     * The buffered response.
     */
    private CharArrayWriter outputWriter;

    /**
     * The outputWriter stream.
     */
    private SimpleServletOutputStream servletOutputStream;

    /**
     * The contentType.
     */
    private String contentType;

    /**
     * The wrapped response.
     */
    private HttpServletResponse response;

    /**
     * If state is set, allow getOutputStream() to return the "real" output stream, elsewhere returns a internal buffer.
     */
    private boolean state;

    /**
     * Writer has been requested.
     */
    private boolean outRequested;

    /**
     * @param httpServletResponse the response to wrap
     */
    public BufferedResponseWrapper12Impl(HttpServletResponse httpServletResponse)
    {
        this.response = httpServletResponse;
        this.outputWriter = new CharArrayWriter();
        this.servletOutputStream = new SimpleServletOutputStream();
    }

    /**
     * Returns the wrapped servletResponse.
     * @return wrapped servletResponse
     */
    public ServletResponse getResponse()
    {
        return this.response;
    }

    /**
     * @see org.displaytag.filter.BufferedResponseWrapper#getContentType()
     */
    public String getContentType()
    {
        return this.contentType;
    }

    /**
     * The content type is NOT set on the wrapped response. You must set it manually. Overrides any previously set
     * value.
     * @param theContentType the content type.
     */
    public void setContentType(String theContentType)
    {
        if (state)
        {
            log.debug("Allowing content type");

            if (this.contentType != null && // content type has been set before
                this.contentType.indexOf("charset") > -1) // and it specified charset
            {
                // so copy the charset
                String charset = this.contentType.substring(this.contentType.indexOf("charset"));
                if (log.isDebugEnabled())
                {
                    log.debug("Adding charset: [" + charset + "]");
                }

                getResponse().setContentType(StringUtils.substringBefore(theContentType, "charset") + ' ' + charset);
            }
            else
            {
                getResponse().setContentType(theContentType);
            }

        }
        this.contentType = theContentType;
    }

    /**
     * @see javax.servlet.ServletResponse#getWriter()
     */
    public PrintWriter getWriter() throws IOException
    {

        if (state && !outRequested)
        {
            log.debug("getWriter() returned");

            // ok, exporting in progress, discard old data and go on streaming
            this.servletOutputStream.reset();
            this.outputWriter.reset();
            this.outRequested = true;
            return ((HttpServletResponse) getResponse()).getWriter();
        }

        return new PrintWriter(this.outputWriter);
    }

    /**
     * Flush the buffer, not the response.
     * @throws IOException if encountered when flushing
     */
    public void flushBuffer() throws IOException
    {
        if (outputWriter != null)
        {
            this.outputWriter.flush();
            this.servletOutputStream.outputStream.reset();
        }
    }

    /**
     * @see javax.servlet.ServletResponse#getOutputStream()
     */
    public ServletOutputStream getOutputStream() throws IOException
    {
        if (state && !outRequested)
        {
            log.debug("getOutputStream() returned");

            // ok, exporting in progress, discard old data and go on streaming
            this.servletOutputStream.reset();
            this.outputWriter.reset();
            this.outRequested = true;
            return ((HttpServletResponse) getResponse()).getOutputStream();
        }
        return this.servletOutputStream;
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#addHeader(java.lang.String, java.lang.String)
     */
    public void addHeader(String name, String value)
    {
        // if the "magic parameter" is set, a table tag is going to call getOutputStream()
        if (TableTagParameters.PARAMETER_EXPORTING.equals(name))
        {
            log.debug("Magic header received, real response is now accessible");
            state = true;
        }
        else
        {
            if (!ArrayUtils.contains(FILTERED_HEADERS, StringUtils.lowerCase(name)))
            {
                ((HttpServletResponse) getResponse()).addHeader(name, value);
            }
        }
    }

    /**
     * @see org.displaytag.filter.BufferedResponseWrapper#isOutRequested()
     */
    public boolean isOutRequested()
    {
        return this.outRequested;
    }

    /**
     * @see org.displaytag.filter.BufferedResponseWrapper#getContentAsString()
     */
    public String getContentAsString()
    {
        return this.outputWriter.toString() + this.servletOutputStream.toString();
    }

    // -- standard methods --

    /**
     * Not available in servlets 2.2. Needed to compile with servlets 2.3.
     * @see javax.servlet.ServletResponse#resetBuffer()
     */
    public void resetBuffer()
    {
        // not available in servlets 2.2
    }

    /**
     * @see javax.servlet.ServletResponse#getCharacterEncoding()
     */
    public String getCharacterEncoding()
    {
        return response.getCharacterEncoding();
    }

    /**
     * @see javax.servlet.ServletResponse#setContentLength(int)
     */
    public void setContentLength(int len)
    {
        response.setContentLength(len);
    }

    /**
     * @see javax.servlet.ServletResponse#setBufferSize(int)
     */
    public void setBufferSize(int size)
    {
        response.setBufferSize(size);
    }

    /**
     * @see javax.servlet.ServletResponse#getBufferSize()
     */
    public int getBufferSize()
    {
        return response.getBufferSize();
    }

    /**
     * @see javax.servlet.ServletResponse#isCommitted()
     */
    public boolean isCommitted()
    {
        return response.isCommitted();
    }

    /**
     * @see javax.servlet.ServletResponse#reset()
     */
    public void reset()
    {
        response.reset();
    }

    /**
     * @see javax.servlet.ServletResponse#setLocale(java.util.Locale)
     */
    public void setLocale(Locale loc)
    {
        response.setLocale(loc);
    }

    /**
     * @see javax.servlet.ServletResponse#getLocale()
     */
    public Locale getLocale()
    {
        return response.getLocale();
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#addCookie(javax.servlet.http.Cookie)
     */
    public void addCookie(Cookie cookie)
    {
        response.addCookie(cookie);
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#containsHeader(java.lang.String)
     */
    public boolean containsHeader(String name)
    {
        return response.containsHeader(name);
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#encodeURL(java.lang.String)
     */
    public String encodeURL(String url)
    {
        return response.encodeURL(url);
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#encodeRedirectURL(java.lang.String)
     */
    public String encodeRedirectURL(String url)
    {
        return response.encodeRedirectURL(url);
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#encodeUrl(java.lang.String)
     * @deprecated
     */
    public String encodeUrl(String url)
    {
        return response.encodeUrl(url);
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#encodeRedirectUrl(java.lang.String)
     * @deprecated
     */
    public String encodeRedirectUrl(String url)
    {
        return response.encodeRedirectUrl(url);
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#sendError(int, java.lang.String)
     */
    public void sendError(int sc, String msg) throws IOException
    {
        response.sendError(sc, msg);
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#sendError(int)
     */
    public void sendError(int sc) throws IOException
    {
        response.sendError(sc);
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#sendRedirect(java.lang.String)
     */
    public void sendRedirect(String location) throws IOException
    {
        response.sendRedirect(location);
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#setDateHeader(java.lang.String, long)
     */
    public void setDateHeader(String name, long date)
    {
        if (!ArrayUtils.contains(FILTERED_HEADERS, StringUtils.lowerCase(name)))
        {
            response.setDateHeader(name, date);
        }
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#addDateHeader(java.lang.String, long)
     */
    public void addDateHeader(String name, long date)
    {
        if (!ArrayUtils.contains(FILTERED_HEADERS, StringUtils.lowerCase(name)))
        {
            response.addDateHeader(name, date);
        }
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#setHeader(java.lang.String, java.lang.String)
     */
    public void setHeader(String name, String value)
    {
        if (!ArrayUtils.contains(FILTERED_HEADERS, StringUtils.lowerCase(name)))
        {
            response.setHeader(name, value);
        }
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#setIntHeader(java.lang.String, int)
     */
    public void setIntHeader(String name, int value)
    {
        if (!ArrayUtils.contains(FILTERED_HEADERS, StringUtils.lowerCase(name)))
        {
            response.setIntHeader(name, value);
        }
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#addIntHeader(java.lang.String, int)
     */
    public void addIntHeader(String name, int value)
    {
        if (!ArrayUtils.contains(FILTERED_HEADERS, StringUtils.lowerCase(name)))
        {
            response.addIntHeader(name, value);
        }
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#setStatus(int)
     */
    public void setStatus(int sc)
    {
        response.setStatus(sc);
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#setStatus(int, java.lang.String)
     * @deprecated
     */
    public void setStatus(int sc, String sm)
    {
        response.setStatus(sc, sm);
    }

}