package org.displaytag.test;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

        // start servletRunner
        runner = new ServletRunner(new File(path), CONTEXT);
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

}