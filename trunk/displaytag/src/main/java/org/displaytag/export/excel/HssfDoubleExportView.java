package org.displaytag.export.excel;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.jsp.JspException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.displaytag.model.HeaderCell;
import org.displaytag.render.HssfTableWriter;

/**
 * If you are doing an export, and do not know if the user would like a view that is grouped or a view that is raw,
 * just use this one; it will export one tab that is grouped, and one tab that is not. Override the getHssfTableWriter
 * method to change which HssfTableWriter is actually used.
 * @author andy
 * @see DefaultHssfExportView#getHssfTableWriter(org.apache.poi.hssf.usermodel.HSSFWorkbook)
 * Date: Nov 13, 2010
 * Time: 8:46:29 AM
 */
public class HssfDoubleExportView extends DefaultHssfExportView
{
    @Override
    public void doExport(OutputStream out) throws IOException, JspException
        {
            try
            {
                HSSFWorkbook wb = new HSSFWorkbook();
                HssfTableWriter writer = getHssfTableWriter(wb);
                writer.setSetSheetName("Export");
                writer.writeTable(this.model, "-1");

                boolean hasGroups = false;
                for (HeaderCell cell : this.model.getHeaderCellList())
                {
                    if (cell.getGroup() > 0)
                    {
                        hasGroups = true;
                        cell.setGroup(0);
                    }
                }
                if (hasGroups)
                {
                    writer.setSetSheetName("Data");
                    model.reset();
                    writer.writeTable(this.model, "-1");
                }
                wb.write(out);
            }
            catch (Exception e)
            {
                throw new HssfGenerationException(e);
            }
        }
}
