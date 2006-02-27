package org.displaytag.filter;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.ServletOutputStream;


/**
 * Buffers the response; will not send anything directly through to the actual response. Note that this blocks the
 * content-type from being set, you must set it manually in the response. For a given response, you should call either
 * #getWriter or #getOutputStream , but not both.
 * @author rapruitt
 * @version $Revision$ ($Author$)
 * @since 1.0
 */
public class BufferedResponseWrapper extends HttpServletResponseWrapper
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
     * @param response the response to wrap
     */
    public BufferedResponseWrapper(HttpServletResponse response)
    {
        super(response);
        this.outputWriter = new CharArrayWriter();
        this.servletOutputStream = new SimpleServletOutputStream();
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
