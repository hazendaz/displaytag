package org.displaytag.tags;

import org.apache.cactus.WebResponse;
import org.apache.cactus.extension.jsp.JspTagLifecycle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.sample.TestList;


/**
 * Test for CaptionTag.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class CaptionTagTest extends DisplaytagTestCase
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(CaptionTagTest.class);

    /**
     * caption tag lifecycle.
     */
    JspTagLifecycle captionLifeCycle;

    /**
     * table tag lifecycle.
     */
    JspTagLifecycle tableLifeCycle;

    /**
     * @param name test name
     */
    public CaptionTagTest(String name)
    {
        super(name);
    }

    /**
     * test set up.
     * @throws Exception any exception thrown during test.
     */
    protected void setUp() throws Exception
    {
        CaptionTag tag = new CaptionTag();
        tag.setPageContext(this.pageContext);
        tag.setClass("cssclass");
        tag.setId("captionid");
        tag.setStyle("border: 1px solid red");
        tag.setTitle("caption title");
        tag.setLang("english");
        tag.setDir("ltr");

        // table setup
        TableTag table = new TableTag();
        table.setPageContext(this.pageContext);
        this.pageContext.setAttribute("test", new TestList(2, false));
        table.setName("pageScope.test");

        tag.setParent(table);

        tableLifeCycle = new JspTagLifecycle(this.pageContext, table);
        captionLifeCycle = tableLifeCycle.addNestedTag(tag);
        captionLifeCycle.addNestedText("This is the caption content");

    }

    /**
     * base test for the caption tag.
     * @throws Exception any exception thrown during test.
     */
    public void testCaption() throws Exception
    {
        // remember, assertion should go before invoke()
        captionLifeCycle.expectBodyEvaluated(1);
        tableLifeCycle.invoke();
    }

    /**
     * end the test for caption.
     * @param webresponse WebResponse
     */
    public void endCaption(WebResponse webresponse)
    {
        log.debug("RESPONSE: " + webresponse.getText());

        assertContains(webresponse, "<caption");
        assertContains(webresponse, "class=\"cssclass\"");
        assertContains(webresponse, "id=\"captionid\"");
        assertContains(webresponse, "style=\"border: 1px solid red\"");
        assertContains(webresponse, "title=\"caption title\"");
        assertContains(webresponse, "lang=\"english\"");
        assertContains(webresponse, "dir=\"ltr\"");
        assertContains(webresponse, ">This is the caption content</caption>");
    }


}
