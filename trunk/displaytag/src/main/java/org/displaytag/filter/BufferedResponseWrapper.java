package org.displaytag.filter;

import javax.servlet.http.HttpServletResponse;


/**
 * Buffers the response; will not send anything directly through to the actual response. Note that this blocks the
 * content-type from being set, you must set it manually in the response.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public interface BufferedResponseWrapper extends HttpServletResponse
{

    /**
     * Return <code>true</code> if ServletOutputStream has been requested from Table tag.
     * @return <code>true</code> if ServletOutputStream has been requested
     */
    boolean isOutRequested();

    /**
     * If the app server sets the content-type of the response, it is sticky and you will not be able to change it.
     * Therefore it is intercepted here.
     * @return the ContentType that was most recently set
     */
    String getContentType();

    /**
     * Returns the String representation of the content written to the response.
     * @return the content of the response
     */
    String getContentAsString();
}
