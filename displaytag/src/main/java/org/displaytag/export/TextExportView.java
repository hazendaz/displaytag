package org.displaytag.export;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;


/**
 * Main interface for exportViews which need to output character data.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public interface TextExportView extends ExportView
{

    /**
     * Returns the exported content as a String.
     * @param out output writer
     * @throws IOException for exceptions in accessing the output stream
     * @throws JspException for other exceptions during export
     */
    void doExport(Writer out) throws IOException, JspException;

    /**
     * If <code>true</code> exported data will be included in the html page. <strong>actually not evaluated. Included
     * for future enhancements </strong>
     * @return <code>true</code> if exported data should be included in the html page
     */
    boolean outputPage();
}
