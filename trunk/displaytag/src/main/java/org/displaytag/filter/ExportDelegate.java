package org.displaytag.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.Messages;


/**
 * Actually writes out the content of the wrapped response. Used by the j2ee filter and the Spring interceptor
 * implementations.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ExportDelegate
{

    /**
     * logger
     */
    private static Log log = LogFactory.getLog(ExportDelegate.class);

    /**
     * @param wrapper
     * @param servletResponse
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    protected static void writeExport(BufferedResponseWrapper wrapper, ServletResponse servletResponse)
        throws UnsupportedEncodingException, IOException
    {

        if (wrapper.isOutRequested())
        {
            // data already written
            log.debug("Everything done, exiting");
            return;
        }

        // if you reach this point the PARAMETER_EXPORTING has been found, but the special header has never been set in
        // response (this is the signal from table tag that it is going to write exported data)
        log.debug("Something went wrong, displaytag never requested writer as expected.");

        String pageContent;
        String contentType;

        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String characterEncoding = resp.getCharacterEncoding();
        if (characterEncoding != null)
        {
            characterEncoding = "; charset=" + characterEncoding; //$NON-NLS-1$
        }
        log.debug(Messages.getString("ExportDelegate.notoverriding")); //$NON-NLS-1$
        pageContent = wrapper.getContentAsString();
        contentType = wrapper.getContentType();

        if (contentType != null)
        {
            if (contentType.indexOf("charset") > -1) //$NON-NLS-1$
            {
                // charset is already specified (see #921811)
                servletResponse.setContentType(contentType);
            }
            else
            {
                servletResponse.setContentType(contentType + StringUtils.defaultString(characterEncoding));
            }
        }

        if (characterEncoding != null)
        {
            servletResponse.setContentLength(pageContent.getBytes(characterEncoding).length);
        }
        else
        {
            servletResponse.setContentLength(pageContent.getBytes().length);
        }

        PrintWriter out = servletResponse.getWriter();
        out.write(pageContent);
        out.close();
    }
}
