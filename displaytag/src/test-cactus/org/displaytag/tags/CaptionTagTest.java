package org.displaytag.tags;

import javax.servlet.jsp.tagext.BodyContent;

import org.apache.cactus.WebResponse;
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
     * @param name test name
     */
    public CaptionTagTest(String name)
    {
        super(name);
    }

    /**
     * base test for the caption tag. @todo unfinished test!
     * @throws Exception any exception thrown during test.
     */
    public void testCaption() throws Exception
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
        request.setAttribute("test", new TestList(2, false));
        table.setName("requestScope.test");

        tag.setParent(table);

        // start lifecycle methods
        tag.doStartTag();

        BodyContent bodyContent = this.pageContext.pushBody();
        tag.setBodyContent(bodyContent);
        tag.doInitBody();
        bodyContent.println("This is the caption content");

        // actually handles the processing of the body
        tag.doAfterBody();

        // after the body processing completes
        tag.doEndTag();

        // finally call popBody
        this.pageContext.popBody();

    }

    /**
     * end the test for caption.
     * @param webresponse WebResponse
     */
    public void endCaption(WebResponse webresponse)
    {
        assertContains(webresponse, "<caption");
        assertContains(webresponse, ">This is the caption content<");

        log.debug("RESPONSE" + webresponse.getText());
    }


}
