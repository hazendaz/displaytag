package org.displaytag.filter;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Buffers the response; will not send anything directly through to the actual response. Note that this blocks the
 * content-type from being set, you must set it manually in the response.
 * @author rapruitt
 * @version $Revision$ ($Author$)
 * @since 1.0
 */
public class BufferedResponseWrapper extends HttpServletResponseWrapper
{
    /**
     * The buffered response.
     */
    private CharArrayWriter output;

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
        this.output = new CharArrayWriter();
    }
    
    /**
     * Get the String representation.
     * @return the contents of the response
     */
    public String toString()
    {
        return this.output.toString();
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
     * @return the associated print writer
     */
    public PrintWriter getWriter()
    {
        return new PrintWriter(this.output);
    }
    /**
     * Flush the buffer, not the response.
     */
    public void flushBuffer()
    {
        this.output.flush();
    }
}
