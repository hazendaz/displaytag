package org.displaytag.filter;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * Buffers the response; will not send anything directly through to the actual response. Note that this blocks the
 * content-type from being set, you must set it manually in the response. For a given response, you should call either
 * #getWriter or #getOutputStream , but not both.
 * @author rapruitt
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 * @since 1.0
 */
public class BufferedResponseWrapper implements HttpServletResponse // don't extend j2ee 1.3 HttpServletResponseWrapper
{

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
    private String contentType = "text/html";

    /**
     * The wrapped response.
     */
    private HttpServletResponse response;

    /**
     * @param httpServletResponse the response to wrap
     */
    public BufferedResponseWrapper(HttpServletResponse httpServletResponse)
    {
        if (httpServletResponse == null)
        {
            throw new IllegalArgumentException("Response cannot be null");
        }
        this.response = httpServletResponse;
        this.outputWriter = new CharArrayWriter();
        this.servletOutputStream = new SimpleServletOutputStream();
    }


    /**
     * If the app server sets the content-type of the response, it is sticky and you will not be able to change it.
     * Therefore it is intercepted here.
     * @return the ContentType that was most recently set
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
        // response.setContentType(type);
        this.contentType = theContentType;
    }

    /**
     * Get the associated writer.
     * @return the associated print writer
     */
    public PrintWriter getWriter()
    {
        return new PrintWriter(this.outputWriter);
    }

    /**
     * Flush the buffer, not the response.
     * @throws IOException if encountered when flushing
     */
    public void flushBuffer() throws IOException
    {
        this.outputWriter.flush();
        this.servletOutputStream.outputStream.reset();
    }

    /**
     * {@inheritDoc}
     */
    public ServletOutputStream getOutputStream() throws IOException
    {
        return this.servletOutputStream;
    }

    // -- standard methods --

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
     * @see javax.servlet.ServletResponse#resetBuffer()
     */
    public void resetBuffer()
    {
        response.resetBuffer();
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
        // don't add headers that can prevent caching, export (opening in an external program) will not work
        if (!"Expires".equalsIgnoreCase(name)) //$NON-NLS-1$
        {
            response.setDateHeader(name, date);
        }

    }

    /**
     * @see javax.servlet.http.HttpServletResponse#addDateHeader(java.lang.String, long)
     */
    public void addDateHeader(String name, long date)
    {
        // don't add headers that can prevent caching, export (opening in an external program) will not work
        if (!"Expires".equalsIgnoreCase(name)) //$NON-NLS-1$
        {
            response.addDateHeader(name, date);
        }
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#setHeader(java.lang.String, java.lang.String)
     */
    public void setHeader(String name, String value)
    {
        // don't add headers that can prevent caching, export (opening in an external program) will not work
        if (!"Cache-Control".equalsIgnoreCase(name) //$NON-NLS-1$
            && !"Pragma".equalsIgnoreCase(name) //$NON-NLS-1$
            && !"Expires".equalsIgnoreCase(name)) //$NON-NLS-1$
        {
            response.setHeader(name, value);
        }
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#addHeader(java.lang.String, java.lang.String)
     */
    public void addHeader(String name, String value)
    {
        // don't add headers that can prevent caching, export (opening in an external program) will not work
        if (!"Cache-Control".equalsIgnoreCase(name) //$NON-NLS-1$
            && !"Pragma".equalsIgnoreCase(name) //$NON-NLS-1$
            && !"Expires".equalsIgnoreCase(name)) //$NON-NLS-1$
        {
            response.addHeader(name, value);
        }
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#setIntHeader(java.lang.String, int)
     */
    public void setIntHeader(String name, int value)
    {
        response.setIntHeader(name, value);
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#addIntHeader(java.lang.String, int)
     */
    public void addIntHeader(String name, int value)
    {
        response.addIntHeader(name, value);
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


    /**
     * Get the String representation.
     * @return the contents of the response
     */
    public String toString()
    {
        return this.outputWriter.toString() + this.servletOutputStream.toString();
    }


    /**
     * A simple implementation of ServletOutputStream.
     */
    private class SimpleServletOutputStream extends ServletOutputStream
    {

        /**
         * My outputWriter stream, a buffer.
         */
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        /**
         * {@inheritDoc}
         */
        public void write(int b)
        {
            this.outputStream.write(b);
        }

        /**
         * Get the contents of the outputStream.
         * @return contents of the outputStream
         */
        public String toString()
        {
            return this.outputStream.toString();
        }
    }
}