package org.displaytag.filter;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.tags.TableTagParameters;


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
     * logger.
     */
    private static Log log = LogFactory.getLog(BufferedResponseWrapper.class);

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
        if (state)
        {
            log.debug("Allowing content type");
            getResponse().setContentType(theContentType);
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
            ((HttpServletResponse) getResponse()).addHeader(name, value);
        }
    }

    /**
     * Return <code>true</code> if ServletOutputStream has been requested from Table tag.
     * @return <code>true</code> if ServletOutputStream has been requested
     */
    protected boolean isOutRequested()
    {
        return this.outRequested;
    }

    /**
     * Get the String representation.
     * @return the contents of the response
     */
    public String toString()
    {
        return this.outputWriter.toString() + this.servletOutputStream.toString();
    }

}