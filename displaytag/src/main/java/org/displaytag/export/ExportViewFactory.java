package org.displaytag.export;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.exception.NestableRuntimeException;
import org.displaytag.model.TableModel;
import org.displaytag.properties.MediaTypeEnum;


/**
 * Factory for export views.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class ExportViewFactory
{

    /**
     * Map containing MediaTypeEnum - View class.
     */
    private static final Map VIEWCLASSES = new HashMap();

    static
    {
        VIEWCLASSES.put(MediaTypeEnum.CSV, CsvView.class);
        VIEWCLASSES.put(MediaTypeEnum.EXCEL, ExcelView.class);
        VIEWCLASSES.put(MediaTypeEnum.XML, XmlView.class);
    }

    /**
     * utility class, don't instantiate.
     */
    private ExportViewFactory()
    {
        // unused
    }

    /**
     * returns an instance of export view associated with the given export type.
     * @param exportType MediaTypeEnum
     * @param tableModel table model containing data to render
     * @param exportFullList should the complete list be exported?
     * @param includeHeader should header be included in export?
     * @param decorateValues should ouput be decorated?
     * @return specialized instance of BaseExportView
     */
    public static ExportView getView(MediaTypeEnum exportType, TableModel tableModel, boolean exportFullList,
        boolean includeHeader, boolean decorateValues)
    {
        ExportView view;

        Class viewClass = (Class) VIEWCLASSES.get(exportType);

        try
        {
            view = (ExportView) viewClass.newInstance();
        }
        catch (InstantiationException e)
        {
            // @todo better exception message
            throw new NestableRuntimeException(e);
        }
        catch (IllegalAccessException e)
        {
            // @todo better exception message
            throw new NestableRuntimeException(e);
        }

        view.setParameters(tableModel, exportFullList, includeHeader, decorateValues);
        return view;
    }

}