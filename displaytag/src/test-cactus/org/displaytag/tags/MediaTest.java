package org.displaytag.tags;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;

import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;
import org.apache.cactus.extension.jsp.JspTagLifecycle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.test.KnownValue;
import org.displaytag.util.ParamEncoder;


/**
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public class MediaTest extends DisplaytagTestCase
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(MediaTest.class);

    /**
     * table tag.
     */
    TableTag table;

    /**
     * table tag lifecycle.
     */
    JspTagLifecycle tableLifecycle;

    /**
     * first column tag lifecycle.
     */
    JspTagLifecycle columnOneLifecycle;

    /**
     * second column tag lifecycle.
     */
    JspTagLifecycle columnTwoLifecycle;

    /**
     * third column tag lifecycle.
     */
    JspTagLifecycle columnThreeLifecycle;

    /**
     * @param name test name
     */
    public MediaTest(String name)
    {
        super(name);
    }

    /**
     * @param name test name
     * @param test test instance
     */
    public MediaTest(String name, Test test)
    {
        super(name, test);
    }

    /**
     * set up the test.
     */
    protected void setUp()
    {
        List testData = new ArrayList();
        testData.add(new KnownValue());
        testData.add(new KnownValue());

        this.table = new TableTag();
        request.setAttribute("test", testData);
        this.table.setName("requestScope.test");
        this.tableLifecycle = new JspTagLifecycle(this.pageContext, this.table);

        ColumnTag acolumn = new ColumnTag();
        acolumn.setMedia(MediaTypeEnum.HTML.getName());
        acolumn.setProperty("ant");
        this.columnOneLifecycle = this.tableLifecycle.addNestedTag(acolumn);

        ColumnTag bcolumn = new ColumnTag();
        bcolumn.setMedia(MediaTypeEnum.XML.getName());
        bcolumn.setProperty("bee");
        this.columnTwoLifecycle = this.tableLifecycle.addNestedTag(bcolumn);

        ColumnTag ccolumn = new ColumnTag();
        ccolumn.setProperty("camel");
        bcolumn.setMedia(MediaTypeEnum.XML.getName() + " " + MediaTypeEnum.HTML.getName());
        this.columnThreeLifecycle = this.tableLifecycle.addNestedTag(ccolumn);
    }

    /**
     * test for html.
     * @throws Exception any exception generated during the test
     */
    public void testAsHtml() throws Exception
    {
        this.tableLifecycle.invoke();
        this.columnOneLifecycle.expectBodyEvaluated(2);
        this.columnTwoLifecycle.expectBodySkipped();
        this.columnThreeLifecycle.expectBodyEvaluated(2);
    }

    /**
     * end the test for html.
     * @param webresponse WebResponse
     */
    public void endAsHtml(WebResponse webresponse)
    {
        assertContains(webresponse, KnownValue.ANT);
        assertDoesNotContain(webresponse, KnownValue.BEE);
        assertContains(webresponse, KnownValue.CAMEL);
        log.debug("RESPONSE" + webresponse.getText());
    }

    /**
     * begin the test for xml.
     * @param webrequest WebRequest
     */
    public void beginAsXml(WebRequest webrequest)
    {
        ParamEncoder encoder = new ParamEncoder(null, "requestScope.test");
        webrequest.addParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE), ""
            + MediaTypeEnum.XML.getCode());
    }

    /**
     * test for xml.
     * @throws Exception any exception generated during the test
     */
    public void testAsXml() throws Exception
    {
        this.tableLifecycle.invoke();
        this.columnOneLifecycle.expectBodySkipped();
        this.columnTwoLifecycle.expectBodyEvaluated(2);
        this.columnThreeLifecycle.expectBodyEvaluated(2);
    }

    /**
     * end the test for xml.
     * @param webresponse WebResponse
     */
    public void endAsXml(WebResponse webresponse)
    {
        assertDoesNotContain(webresponse, KnownValue.ANT);
        assertContains(webresponse, KnownValue.BEE);
        assertContains(webresponse, KnownValue.CAMEL);
        log.debug("RESPONSE" + webresponse.getText());
    }

}
