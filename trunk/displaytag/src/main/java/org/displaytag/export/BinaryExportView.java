package org.displaytag.export;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.jsp.JspException;


/**
 * Main interface for exportViews which need to output binary data.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public interface BinaryExportView extends ExportView
{

    /**
     * Returns the exported content as a String.
     * @param out output writer
     * @throws IOException for exceptions in accessing the output stream
     * @throws JspException for other exceptions during export
     */
    void doExport(OutputStream out) throws IOException, JspException;
}
