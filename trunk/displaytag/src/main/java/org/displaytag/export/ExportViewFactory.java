package org.displaytag.export;

import org.displaytag.model.TableModel;

/**
 * Factory for export views
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public final class ExportViewFactory
{
    /**
     * utility class, don't instantiate
     */
    private ExportViewFactory()
    {
    }

    /**
     * returns an instance of export view associated with the given export type
     * @param exportType ExportTypeEnum
     * @param tableModel table model containing data to render
     * @param exportFullList should the complete list be exported?
     * @param includeHeader should header be included in export?
     * @return specialized instance of BaseExportView
     */
    public static BaseExportView getView(
        ExportTypeEnum exportType,
        TableModel tableModel,
        boolean exportFullList,
        boolean includeHeader)
    {
        if (exportType == ExportTypeEnum.CSV)
        {
            return new CsvView(tableModel, exportFullList, includeHeader);
        }
        else if (exportType == ExportTypeEnum.EXCEL)
        {
            return new ExcelView(tableModel, exportFullList, includeHeader);
        }
        else if (exportType == ExportTypeEnum.XML)
        {
            return new XmlView(tableModel, exportFullList, includeHeader);
        }
        else
        {
            throw new IllegalArgumentException("Unknown export type: " + exportType);
        }
    }
}
