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
 * @version $Revision $ ($Author $)
 */
public class DisplaySourceServlet extends HttpServlet
{

    /**
     * the folder containg example pages.
     */
    private static final String EXAMPLE_FOLDER = "/";

    /**
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
     */
    protected final void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {

        String jspFile = request.getRequestURI();

        // lastIndexOf(".") can't be null, since the servlet is mapped to ".source"
        jspFile = jspFile.substring(0, jspFile.lastIndexOf("."));

        if (jspFile.lastIndexOf("/") != -1)
        {
            jspFile = jspFile.substring(jspFile.lastIndexOf("/") + 1);
        }

        // only want to show sample pages, don't play with url!
        if (!jspFile.startsWith("example-"))
        {
            throw new ServletException("Invalid file selected: " + jspFile);
        }

        String fullName = EXAMPLE_FOLDER + jspFile;

        InputStream inputStream = getServletContext().getResourceAsStream(fullName);

        if (inputStream == null)
        {
            throw new ServletException("Unable to find JSP file: " + jspFile);
        }

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println(
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
                + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">");
        out.println("<head>");
        out.println("<title>");
        out.println("source for " + jspFile);
        out.println("</title>");
        out.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />");
        out.println("</head>");
        out.println("<body>");
        out.println("<pre>");
        for (int currentChar = inputStream.read(); currentChar != -1; currentChar = inputStream.read())
        {
            if (currentChar == '<')
            {
                out.print("&lt;");
            }
            else
            {
                out.print((char) currentChar);
            }
        }
        out.println("</pre>");
        out.println("</body>");
        out.println("</html>");
    }

}
