package org.displaytag.properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Servlet which loads a user supplied properties file for the display taglibrary.</p>
 * <p>To set a default properties for a whole web application configure this servlet in web.xml and set a
 * "properties.filename" parameter with the path to the properties file, relative to the web application root
 * (example: "WEB-INF/display.properties").</p>
 * <p>The original properties file is still loaded and used when any of the parameters is missing</p>
 * <p>
 * Sample Web.xml entry:
 * </p>
 * <pre>
 * 	&lt;servlet id="DisplayPropertiesLoaderServlet">
 * 		&lt;servlet-name>DisplayPropertiesLoaderServlet&lt;/servlet-name>
 * 		&lt;display-name>DisplayPropertiesLoaderServlet&lt;/display-name>
 * 		&lt;description>displaytag initialization servlet&lt;/description>
 * 		&lt;servlet-class>org.displaytag.properties.DisplayPropertiesLoaderServlet&lt;/servlet-class>
 * 		&lt;init-param>
 * 			&lt;param-name>properties.filename&lt;/param-name>
 * 			&lt;param-value>WEB-INF/display.properties&lt;/param-value>
 * 		&lt;/init-param>
 * 		&lt;load-on-startup>1&lt;/load-on-startup>
 * 	&lt;/servlet>
 * </pre>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class DisplayPropertiesLoaderServlet extends HttpServlet
{

    /**
     * logger
     */
    private Log mLog = LogFactory.getLog(DisplayPropertiesLoaderServlet.class);

    /**
     * name of the parameter containing the properties file path
     */
    private static final String PROPERTIES_PARAMETER = "properties.filename";

    /**
     * Init: retrieve the confiuration parameter and set the user file name in
     * org.displaytag.tags.TableProperties
     * @param pServletConfig ServletConfig
     * @throws ServletException generic exception
     * @see javax.servlet.Servlet#init(ServletConfig)
     */
    public final void init(ServletConfig pServletConfig)
        throws ServletException
    {

        super.init(pServletConfig);

        // read the parameter
        String lFile = getInitParameter(PROPERTIES_PARAMETER);

        // debug parameter
        mLog.debug(PROPERTIES_PARAMETER + "=" + lFile);

        if (lFile != null)
        {
            // add the webapp path
            String lPrefix = getServletContext().getRealPath("/");

            String lFullPath = lPrefix + SystemUtils.FILE_SEPARATOR + lFile;

            // set the user file
            TableProperties.setPropertiesFilename(lFullPath);

        }

    }

}
