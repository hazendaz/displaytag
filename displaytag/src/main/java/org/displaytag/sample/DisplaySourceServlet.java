package org.displaytag.sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Servlet used to display jsp source for example pages</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class DisplaySourceServlet extends HttpServlet
{

	/**
	 * the folder containg example pages
	 */
	private static final String EXAMPLE_FOLDER = "/examples/";

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
	 */
	protected final void doGet(HttpServletRequest pRequest, HttpServletResponse pResponse)
		throws ServletException, IOException
	{

		String lJspFile = pRequest.getRequestURI();

		// lastIndexOf(".") can't be null, since the servlet is mapped to ".source"
		lJspFile = lJspFile.substring(0, lJspFile.lastIndexOf("."));

		if (lJspFile.lastIndexOf("/") != -1)
		{
			lJspFile = lJspFile.substring(lJspFile.lastIndexOf("/") + 1);
		}

		// only want to show sample pages, don't play with url!
		if (!lJspFile.startsWith("example-"))
		{
			throw new ServletException("Invalid file selected: " + lJspFile);
		}

		String lFullName = EXAMPLE_FOLDER + lJspFile;

		InputStream lInputStream = getServletContext().getResourceAsStream(lFullName);

		if (lInputStream == null)
		{
			throw new ServletException("Unable to find JSP file: " + lJspFile);
		}

		pResponse.setContentType("text/html");

		PrintWriter lOut = pResponse.getWriter();

		lOut.println(
			"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
				+ "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		lOut.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">");
		lOut.println("<head>");
		lOut.println("<title>");
		lOut.println("source for " + lJspFile);
		lOut.println("</title>");
		lOut.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\" />");
		lOut.println("</head>");
		lOut.println("<body>");
		lOut.println("<pre>");
		for (int lChar = lInputStream.read(); lChar != -1; lChar = lInputStream.read())
		{
			if (lChar == '<')
			{
				lOut.print("&lt;");
			}
			else
			{
				lOut.print((char) lChar);
			}
		}
		lOut.println("</pre>");
		lOut.println("</body>");
		lOut.println("</html>");
	}

}
