package org.displaytag.tags;

import java.util.ArrayList;
import java.util.List;

import org.apache.cactus.JspTestCase;
import org.apache.cactus.WebRequest;
import org.apache.cactus.WebResponse;
import org.apache.cactus.extension.jsp.JspTagLifecycle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.export.MediaTypeEnum;

/**
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public class MediaTest extends JspTestCase
{

    /**
     * logger
     */
    private static Log log = LogFactory.getLog(MediaTest.class);

    TableTag table;
    JspTagLifecycle tableLifecycle;
    JspTagLifecycle aLifecycle;
    JspTagLifecycle bLifecycle;
    JspTagLifecycle cLifecycle;
    JspTagLifecycle dLifecycle;

    protected void setUp() throws Exception
    {
        List testData = new ArrayList();
        testData.add(new KnownValue(1));
        testData.add(new KnownValue(2));

        table = new TableTag();
        table.setList(testData);
        table.encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE);
        tableLifecycle = new JspTagLifecycle(pageContext, table);

        ColumnTag acolumn = new ColumnTag();
        acolumn.setMedia(MediaTypeEnum.HTML.getName());
        acolumn.setProperty("ant");
        aLifecycle = tableLifecycle.addNestedTag(acolumn);

        ColumnTag bcolumn = new ColumnTag();
        bcolumn.setMedia(MediaTypeEnum.XML.getName());
        bcolumn.setProperty("bee");
        bLifecycle = tableLifecycle.addNestedTag(bcolumn);

        ColumnTag ccolumn = new ColumnTag();
        ccolumn.setProperty("camel");
        bcolumn.setMedia(MediaTypeEnum.XML.getName() + " " + MediaTypeEnum.HTML.getName());
        cLifecycle = tableLifecycle.addNestedTag(ccolumn);

        ColumnTag dcolumn = new ColumnTag();
        dcolumn.setProperty("position");
        dLifecycle = tableLifecycle.addNestedTag(dcolumn);
    }

    public void testAsHtml() throws Exception
    {
        tableLifecycle.invoke();
        aLifecycle.expectBodyEvaluated(2);
        bLifecycle.expectBodySkipped();
        cLifecycle.expectBodyEvaluated(2);
    }
    public void endTestAsHtml(WebResponse response)
    {
        assertContains(response, KnownValue.ant);
        assertDoesNotContain(response, KnownValue.bee);
        assertContains(response, KnownValue.camel);
        log.debug("RESPONSE" + response.getText());
    }

    public void beginTestAsXml(WebRequest request)
    {
        request.addParameter(
            table.encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE),
            "" + MediaTypeEnum.XML.getCode());
    }

    public void testAsXml() throws Exception
    {
        tableLifecycle.invoke();
        aLifecycle.expectBodySkipped();
        bLifecycle.expectBodyEvaluated(2);
        cLifecycle.expectBodyEvaluated(2);
    }

    public void endTestAsXml(WebResponse response)
    {
        assertDoesNotContain(response, KnownValue.ant);
        assertContains(response, KnownValue.bee);
        assertContains(response, KnownValue.camel);
        log.debug("RESPONSE" + response.getText());
    }

    /**
     * Convenience function that asserts that a substring can be found in a the returned HTTP response body.
     * 
     * @param theResponse
     * the response from the server side.
     * @param theSubstring
     * the substring to look for
     */
    public void assertContains(WebResponse theResponse, String theSubstring)
    {
        String target = theResponse.getText();
        if (target.indexOf(theSubstring) < 0)
        {
            fail("Response did not contain the substring: [" + theSubstring + "]");
        }
    }
    /**
     * Convenience function that asserts that a substring can be found in a the returned HTTP response body.
     * 
     * @param theResponse
     * the response from the server side.
     * @param theSubstring
     * the substring to look for
     */
    public void assertDoesNotContain(WebResponse theResponse, String theSubstring)
    {
        String target = theResponse.getText();
        if (target.indexOf(theSubstring) > -1)
        {
            fail("Response did not contain the substring: [" + theSubstring + "]");
        }
    }

}



/**
 * Simple test data provider.
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
class KnownValue
{
    int position;
    public static String ant = "acolumn";
    public static String bee = "bcolumn";
    public static String camel = "ccolumn";

    public KnownValue(int position)
    {
        this.position = position;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public String getAnt()
    {
        return ant;
    }

    public void setAnt(String aa)
    {
        ant = aa;
    }

    public String getBee()
    {
        return bee;
    }

    public void setBee(String bb)
    {
        bee = bb;
    }

    public String getCamel()
    {
        return camel;
    }

    public void setCamel(String cc)
    {
        camel = cc;
    }
}

