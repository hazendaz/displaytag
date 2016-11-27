/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.test;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Hashtable;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    protected final Logger log = LoggerFactory.getLogger(getClass());


    /**
     * HttpUnit ServletRunner.
     */
    protected ServletRunner runner;

    /**
     * Runs the test.
     *
     * @throws Exception any axception thrown during test.
     */
    public abstract void doTest() throws Exception;

    /**
     * Gets the jsp url.
     *
     * @param jsp the jsp
     * @return the jsp url
     */
    protected String getJspUrl(String jsp)
    {
        return "http://localhost" + CONTEXT + "/jsps/" + jsp;
    }

    /**
     * Sets the up.
     *
     * @throws Exception the exception
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
        this.runner = new ServletRunner(new File(path), CONTEXT);

        Hashtable<String, String> params = new Hashtable<String, String>();
        params.put("javaEncoding", "utf-8");
        this.runner.registerServlet("*.jsp", "org.apache.jasper.servlet.JspServlet", params);

        this.log.debug("ServletRunner setup OK");

    }

    /**
     * Tear down.
     *
     * @throws Exception the exception
     * @see junit.framework.TestCase#tearDown()
     */
    @After
    public void tearDown() throws Exception
    {
        // shutdown servlet engine
        this.runner.shutDown();
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