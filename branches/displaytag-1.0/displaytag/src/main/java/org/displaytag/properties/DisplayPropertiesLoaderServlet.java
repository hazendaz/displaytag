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
package org.displaytag.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.Messages;


/**
 * <p>
 * Servlet which loads a user supplied properties file for the display taglibrary.
 * </p>
 * <p>
 * <strong>You probably do not need to use this servlet. For most cases, you should simply add a displaytag.properties
 * file to your system, to the same effect, and not have to configure this servlet. </strong> If you have both a
 * displaytag.properties, and you configure this servlet, the properties in the property file specified here will
 * override the properties in the displaytag.properties.
 * </p>
 * <p>
 * To set a default properties for a whole web application configure this servlet in web.xml and set a
 * "properties.filename" parameter with the path to the properties file, relative to the web application root (example:
 * "WEB-INF/display.properties").
 * </p>
 * <p>
 * The original properties file is still loaded and used when any of the parameters is missing
 * </p>
 * <p>
 * Sample Web.xml entry:
 * </p>
 * 
 * <pre>
 *     &lt;servlet id="DisplayPropertiesLoaderServlet">
 *         &lt;servlet-name>DisplayPropertiesLoaderServlet&lt;/servlet-name>
 *         &lt;display-name>DisplayPropertiesLoaderServlet&lt;/display-name>
 *         &lt;description>displaytag initialization servlet&lt;/description>
 *         &lt;servlet-class>org.displaytag.properties.DisplayPropertiesLoaderServlet&lt;/servlet-class>
 *         &lt;init-param>
 *             &lt;param-name>properties.filename&lt;/param-name>
 *             &lt;param-value>WEB-INF/display.properties&lt;/param-value>
 *         &lt;/init-param>
 *         &lt;load-on-startup>1&lt;/load-on-startup>
 *     &lt;/servlet>
 * </pre>
 * 
 * @author Fabrizio Giustina
 * @author rapruitt
 * @version $Revision$ ($Author$)
 * @deprecated this servlet doesn't support i18n. The recommended way to load custom properties is adding a
 * <code>displaytag.properties</code> file in your classpath. This will also load locale specific properties (for
 * example <code>displaytag_IT.properties</code>) when needed.
 */
public class DisplayPropertiesLoaderServlet extends HttpServlet
{

    /**
     * name of the parameter containing the properties file path.
     */
    public static final String PROPERTIES_PARAMETER = "properties.filename"; //$NON-NLS-1$

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(DisplayPropertiesLoaderServlet.class);

    /**
     * Init: retrieve the confiuration parameter and set the user file name in
     * <code>org.displaytag.tags.TableProperties</code>.
     * @param servletConfig ServletConfig
     * @throws ServletException generic exception
     * @see javax.servlet.Servlet#init(ServletConfig)
     */
    public final void init(ServletConfig servletConfig) throws ServletException
    {

        super.init(servletConfig);

        // read the parameter
        String file = getInitParameter(PROPERTIES_PARAMETER);

        // debug parameter
        if (log.isDebugEnabled())
        {
            log.debug(PROPERTIES_PARAMETER + '=' + file);
        }

        if (file != null)
        {
            InputStream propStream = servletConfig.getServletContext().getResourceAsStream(file);
            if (propStream == null)
            {
                log.warn("unable to find " + file);
                return;
            }
            Properties props = new Properties();
            try
            {
                props.load(propStream);
            }
            catch (IOException e)
            {
                throw new ServletException("Cannot load " + file + ": " + e.getMessage(), e);
            }

            // set the user properties
            TableProperties.setUserProperties(props);
        }
        else
        {
            log.warn(Messages.getString("DisplayPropertiesLoaderServlet.missingparameter", //$NON-NLS-1$
                new Object[]{PROPERTIES_PARAMETER}));
        }

    }

}
