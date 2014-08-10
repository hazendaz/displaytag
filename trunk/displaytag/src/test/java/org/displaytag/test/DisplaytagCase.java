package org.displaytag.test;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Hashtable;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.servletunit.ServletRunner;


/**
 * Base TestCase class for tests.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public abstract class DisplaytagCase
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
     * Runs the test.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public abstract void doTest() throws Exception;

    protected String getJspUrl(String jsp)
    {
        return "http://localhost" + CONTEXT + "/jsps/" + jsp;
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Before
    public void setUp() throws Exception
    {
        // need to pass a web.xml file to setup servletunit working directory
        ClassLoader classLoader = getClass().getClassLoader();
        URL webXmlUrl = classLoader.getResource("WEB-INF/web.xml");
        String path = URLDecoder.decode(webXmlUrl.getFile(), "UTF-8");

        HttpUnitOptions.setDefaultCharacterSet("utf-8");
        System.setProperty("file.encoding", "utf-8");

        // start servletRunner
        runner = new ServletRunner(new File(path), CONTEXT);

        Hashtable<String, String> params = new Hashtable<String, String>();
        params.put("javaEncoding", "utf-8");
        runner.registerServlet("*.jsp", "org.apache.jasper.servlet.JspServlet", params);

        log.debug("ServletRunner setup OK");

    }

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @After
    public void tearDown() throws Exception
    {
        // shutdown servlet engine
        runner.shutDown();
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
            Assert.fail(message
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
            Assert.fail(message
                + " Expected value \""
                + exp
                + "\" not found in actual array: "
                + ArrayUtils.toString(actual));
        }
    }
}