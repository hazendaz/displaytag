/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
 */
public class DisplaySourceServlet extends HttpServlet {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * the folder containg example pages.
     */
    private static final String EXAMPLE_FOLDER = "/"; //$NON-NLS-1$

    /**
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        if (inputStream == null) {
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
        for (int currentChar = inputStream.read(); currentChar != -1; currentChar = inputStream.read()) {
            if (currentChar == '<') {
                out.print("&lt;"); //$NON-NLS-1$
            } else {
                out.print((char) currentChar);
            }
        }
        out.println("</pre>"); //$NON-NLS-1$
        out.println("</body>"); //$NON-NLS-1$
        out.println("</html>"); //$NON-NLS-1$
    }

}
