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
package org.displaytag.sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet used to display jsp source for example pages.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class DisplaySourceServlet extends HttpServlet
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * the folder containg example pages.
     */
    private static final String EXAMPLE_FOLDER = "/"; //$NON-NLS-1$

    /**
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
     */
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException
    {

        String jspFile = request.getRequestURI();

        // lastIndexOf(".") can't be null, since the servlet is mapped to ".source"
        jspFile = jspFile.substring(0, jspFile.lastIndexOf(".")); //$NON-NLS-1$

        if (jspFile.lastIndexOf("/") != -1) //$NON-NLS-1$
        {
            jspFile = jspFile.substring(jspFile.lastIndexOf("/") + 1); //$NON-NLS-1$
        }

        // only want to show sample pages, don't play with url!
        if (!jspFile.startsWith("example-")) //$NON-NLS-1$
        {
            throw new ServletException("Invalid file selected: " + jspFile); //$NON-NLS-1$
        }

        String fullName = EXAMPLE_FOLDER + jspFile;

        InputStream inputStream = getServletContext().getResourceAsStream(fullName);

        if (inputStream == null)
        {
            throw new ServletException("Unable to find JSP file: " + jspFile); //$NON-NLS-1$
        }

        response.setContentType("text/html"); //$NON-NLS-1$

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" " //$NON-NLS-1$
            + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"); //$NON-NLS-1$
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">"); //$NON-NLS-1$
        out.println("<head>"); //$NON-NLS-1$
        out.println("<title>"); //$NON-NLS-1$
        out.println("source for " + jspFile); //$NON-NLS-1$
        out.println("</title>"); //$NON-NLS-1$
        out.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />"); //$NON-NLS-1$
        out.println("</head>"); //$NON-NLS-1$
        out.println("<body>"); //$NON-NLS-1$
        out.println("<pre>"); //$NON-NLS-1$
        for (int currentChar = inputStream.read(); currentChar != -1; currentChar = inputStream.read())
        {
            if (currentChar == '<')
            {
                out.print("&lt;"); //$NON-NLS-1$
            }
            else
            {
                out.print((char) currentChar);
            }
        }
        out.println("</pre>"); //$NON-NLS-1$
        out.println("</body>"); //$NON-NLS-1$
        out.println("</html>"); //$NON-NLS-1$
    }

}
