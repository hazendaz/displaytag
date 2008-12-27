package org.displaytag.test;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Hashtable;

import junit.framework.TestCase;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.servletunit.ServletRunner;


/**
 * Base TestCase class for tests.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public abstract class DisplaytagCase extends TestCase
{

    /**
     * Context mapped to the test application.
     */
    public static final String CONTEXT = "/context";

    /**
     * logger.
     */
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * HttpUnit ServletRunner.
     */
    protected ServletRunner runner;

    /**
     * Returns the tested jsp name.
     * @return jsp name
     */
    public abstract String getJspName();

    /**
     * Runs the test.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public abstract void doTest(String jspName) throws Exception;

    /**
     * run the test with the non-el tld.
     * @throws Exception any axception thrown during test.
     */
    public void test11() throws Exception
    {
        doTest("http://localhost" + CONTEXT + "/standard/" + getJspName());
    }

    /**
     * run the test with the el tld.
     * @throws Exception any axception thrown during test.
     */
    public void testEL() throws Exception
    {
        doTest("http://localhost" + CONTEXT + "/el/" + getJspName());
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        // need to pass a web.xml file to setup servletunit working directory
        ClassLoader classLoader = getClass().getClassLoader();
        URL webXmlUrl = classLoader.getResource("WEB-INF/web.xml");
        String path = URLDecoder.decode(webXmlUrl.getFile(), "UTF-8");

        HttpUnitOptions.setDefaultCharacterSet("utf-8");
        System.setProperty("file.encoding", "utf-8");

        // start servletRunner
        runner = new ServletRunner(new File(path), CONTEXT);

        Hashtable params = new Hashtable();
        params.put("javaEncoding", "utf-8");
        runner.registerServlet("*.jsp", "org.apache.jasper.servlet.JspServlet", params);

        log.debug("ServletRunner setup OK");

        super.setUp();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
        // shutdown servlet engine
        runner.shutDown();

        super.tearDown();
    }

    /**
     * @see junit.framework.TestCase#getName()
     */
    public String getName()
    {
        return getClass().getName() + "." + super.getName() + " (" + getJspName() + ")";
    }

    /**
     * Compare 2 arrays of string ignoring order.
     * @param message message to output in case of failure
     * @param expected expected array
     * @param actual actual array
     */
    public void assertEqualsIgnoreOrder(String message, String[] expected, String[] actual)
    {
        if (expected.length != actual.length)
        {
            fail(message
                + " Wrong number of values, expected "
                + expected.length
                + " ("
                + ArrayUtils.toString(expected)
                + "), actual "
                + actual.length
                + " ("
                + ArrayUtils.toString(actual)
                + ")");
        }

        outer : for (int j = 0; j < expected.length; j++)
        {
            String exp = expected[j];
            for (int q = 0; q < actual.length; q++)
            {
                if (StringUtils.equals(exp, actual[q]))
                {
                    continue outer;
                }
            }
            fail(message + " Expected value \"" + exp + "\" not found in actual array: " + ArrayUtils.toString(actual));
        }
    }
}