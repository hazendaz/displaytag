package org.displaytag.test;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.filter.MockFilterSupport;

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
     * Runs the test.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public abstract void doTest(String jspName) throws Exception;

    /**
     * run the test with the jsp 11 tld.
     * @throws Exception any axception thrown during test.
     */
    public void test11() throws Exception
    {
        doTest("http://localhost" + CONTEXT + "/tld11/" + getJspName());
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
        // remove any compiled jsp from a previous run.
        cleanupTempFile("tld11/" + getJspName());
        cleanupTempFile("el/" + getJspName());

        // need to pass a web.xml file to setup servletunit working directory
        ClassLoader classLoader = getClass().getClassLoader();
        URL webXmlUrl = classLoader.getResource("WEB-INF/web.xml");
        String path = webXmlUrl.getFile();

        // start servletRunner
        runner = new ServletRunner(path, CONTEXT);

        // register the filter servlet
        runner.registerServlet("*" + MockFilterSupport.FILTERED_EXTENSION, MockFilterSupport.class.getName());
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
     * Clean up temporary files from a previous test.
     * @param jspName jsp name, with full path
     */
    private void cleanupTempFile(String jspName)
    {
        if (SystemUtils.JAVA_IO_TMPDIR != null)
        {
            String path = SystemUtils.JAVA_IO_TMPDIR + jspName;

            File tempFile = new File(StringUtils.replace(path, ".jsp", "$jsp.java"));
            if (tempFile.exists())
            {
                log.debug("Deleting temporary file " + tempFile.getPath());
                tempFile.delete();
            }
            tempFile = new File(StringUtils.replace(path, ".jsp", "$jsp.class"));
            if (tempFile.exists())
            {
                log.debug("Deleting temporary file " + tempFile.getPath());
                tempFile.delete();
            }
        }
    }

}