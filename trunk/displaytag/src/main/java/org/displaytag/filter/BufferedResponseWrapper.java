package org.displaytag.filter;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/**
 * Buffers the response; will not send anything directly through to the actual response. Note that this blocks the
 * content-type from being set, you must set it manually in the response. For a given response, you should call either
 * #getWriter or #getOutputStream , but not both.
 * @author rapruitt
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 * @since 1.0
 */
public class BufferedResponseWrapper extends HttpServletResponseWrapper implements HttpServletResponse
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
    private String contentType;

    /**
     * @param httpServletResponse the response to wrap
     */
    public BufferedResponseWrapper(HttpServletResponse httpServletResponse)
    {
        super(httpServletResponse);
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

    /**
     * @see javax.servlet.http.HttpServletResponse#setDateHeader(java.lang.String, long)
     */
    public void setDateHeader(String name, long date)
    {
        // don't add headers that can prevent caching, export (opening in an external program) will not work
        if (!"Expires".equalsIgnoreCase(name)) //$NON-NLS-1$
        {
            ((HttpServletResponse) getResponse()).setDateHeader(name, date);
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
            ((HttpServletResponse) getResponse()).addDateHeader(name, date);
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
            ((HttpServletResponse) getResponse()).setHeader(name, value);
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
            ((HttpServletResponse) getResponse()).addHeader(name, value);
        }
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
    private static class SimpleServletOutputStream extends ServletOutputStream
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