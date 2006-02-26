package org.apache.taglibs.display;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.io.Writer;

/**
 * This is an abstract class that most tags should inherit from, it provides a
 * number of utility methods that allow tags to read in a template or multiple
 * template files from the web/templates directory, and use those templates as
 * flexible StringBuffers that reread themselves when their matching file
 * changes, etc.
 *
 * @version $Revision$
 */
abstract public class TemplateTag extends BodyTagSupport {
    public void write(String value) throws JspTagException {
        try {
            Writer out = pageContext.getOut();
            out.write(value);
        }
        catch (IOException e) {
            throw new JspTagException("IOException writing to out: " + e.getMessage());
        }
    }

    public void write(StringBuffer val) throws JspTagException {
        this.write(val.toString());
    }
}

