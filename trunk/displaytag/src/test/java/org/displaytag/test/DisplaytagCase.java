package org.displaytag.test;

import java.net.URL;

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
     * logger.
     */
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * HttpUnit ServletRunner.
     */
    protected ServletRunner runner;

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public DisplaytagCase(String name)
    {
        super(name);
    }

    /**
     * Returns the tested jsp name.
     * @return jsp name
     */
    public abstract String getJspName();

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        // need to pass a web.xml file to setup servletunit working directory
        ClassLoader classLoader = getClass().getClassLoader();
        URL webXmlUrl = classLoader.getResource("WEB-INF/web.xml");
        String path = webXmlUrl.getFile();

        // start servletRunner
        runner = new ServletRunner(path, "");
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

}