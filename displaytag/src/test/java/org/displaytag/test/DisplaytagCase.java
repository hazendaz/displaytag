package org.displaytag.test;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
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
        // removed compiled jsp from a previous run.
        cleanupTempFile();

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

    /**
     * Clean up temporary files from a previous test.
     */
    private void cleanupTempFile()
    {
        if (SystemUtils.JAVA_IO_TMPDIR != null)
        {
            String path = SystemUtils.JAVA_IO_TMPDIR.substring(0, SystemUtils.JAVA_IO_TMPDIR.length() - 1)
                + getJspName();

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