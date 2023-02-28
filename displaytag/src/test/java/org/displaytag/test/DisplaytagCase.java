/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jasper.servlet.JasperInitializer;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.servletunit.ServletRunner;

/**
 * Base TestCase class for tests.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public abstract class DisplaytagCase {

    /**
     * Context mapped to the test application.
     */
    public static final String CONTEXT = "/context";

    /**
     * logger.
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * HttpUnit ServletRunner.
     */
    protected ServletRunner runner;

    /**
     * Runs the test.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    public abstract void doTest() throws Exception;

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     */
    public abstract String getJspName();

    /**
     * Gets the jsp url.
     *
     * @param jsp
     *            the jsp
     *
     * @return the jsp url
     */
    protected String getJspUrl(final String jsp) {
        return "http://localhost" + DisplaytagCase.CONTEXT + "/jsps/" + jsp;
    }

    /**
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     *
     * @see junit.framework.TestCase#setUp()
     */
    @BeforeEach
    public void setUp() throws Exception {
        // need to pass a web.xml file to setup servletunit working directory
        final ClassLoader classLoader = this.getClass().getClassLoader();
        final URL webXmlUrl = classLoader.getResource("WEB-INF/web.xml");
        final String path = URLDecoder.decode(webXmlUrl.getFile(), StandardCharsets.UTF_8.name());

        HttpUnitOptions.setDefaultCharacterSet(StandardCharsets.UTF_8.name());
        System.setProperty("file.encoding", StandardCharsets.UTF_8.name());

        // start servletRunner
        this.runner = new ServletRunner(new File(path), DisplaytagCase.CONTEXT);
        this.runner.getSession(true).getServletContext().setAttribute(InstanceManager.class.getName(),
                new SimpleInstanceManager());

        // Initializer Jasper
        final JasperInitializer jsp = new JasperInitializer();
        jsp.onStartup(null, this.runner.getSession(true).getServletContext());

        final Properties params = new Properties();
        params.put("javaEncoding", StandardCharsets.UTF_8.name());
        params.put("scratchdir", "target");
        this.runner.registerServlet("*.jsp", "org.apache.jasper.servlet.JspServlet", params);

        this.log.debug("ServletRunner setup OK");

    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     *
     * @see junit.framework.TestCase#tearDown()
     */
    @AfterEach
    public void tearDown() throws Exception {
        // shutdown servlet engine
        this.runner.shutDown();
    }

    /**
     * Compare 2 arrays of string ignoring order.
     *
     * @param message
     *            message to output in case of failure
     * @param expected
     *            expected array
     * @param actual
     *            actual array
     */
    public void assertEqualsIgnoreOrder(final String message, final String[] expected, final String[] actual) {
        if (expected.length != actual.length) {
            Assertions.fail(message + " Wrong number of values, expected " + expected.length + " ("
                    + ArrayUtils.toString(expected) + "), actual " + actual.length + " (" + ArrayUtils.toString(actual)
                    + ")");
        }

        outer: for (final String exp : expected) {
            for (final String element : actual) {
                if (StringUtils.equals(exp, element)) {
                    continue outer;
                }
            }
            Assertions.fail(message + " Expected value \"" + exp + "\" not found in actual array: "
                    + ArrayUtils.toString(actual));
        }
    }
}
