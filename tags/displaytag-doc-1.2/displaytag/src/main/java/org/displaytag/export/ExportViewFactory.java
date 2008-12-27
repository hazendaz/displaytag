/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.export;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.Messages;
import org.displaytag.exception.WrappedRuntimeException;
import org.displaytag.model.TableModel;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.ReflectHelper;


/**
 * Factory for export views.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class ExportViewFactory
{

    /**
     * Singleton.
     */
    private static ExportViewFactory instance;

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(ExportViewFactory.class);

    /**
     * Map containing MediaTypeEnum - View class.
     */
    private final Map viewClasses = new HashMap();

    /**
     * Private constructor.
     */
    private ExportViewFactory()
    {
        TableProperties properties = TableProperties.getInstance(null);
        String[] exportTypes = properties.getExportTypes();

        if (log.isInfoEnabled())
        {
            log.info(Messages.getString("ExportViewFactory.initializing", //$NON-NLS-1$
                new Object[]{ArrayUtils.toString(exportTypes)}));
        }
        for (int j = 0; j < exportTypes.length; j++)
        {
            String className = properties.getExportClass(exportTypes[j]);
            registerExportView(exportTypes[j], className);
        }
    }

    /**
     * Returns the simgleton for this class.
     * @return ExportViewFactory instance
     */
    public static synchronized ExportViewFactory getInstance()
    {
        if (instance == null)
        {
            instance = new ExportViewFactory();
        }
        return instance;
    }

    /**
     * Register a new Export View, associated with a Media Type. If another export view is currently associated with the
     * given media type it's replaced.
     * @param name media name
     * @param viewClassName export view class name
     */
    public void registerExportView(String name, String viewClassName)
    {
        Class exportClass;
        try
        {
            exportClass = ReflectHelper.classForName(viewClassName);
        }
        catch (ClassNotFoundException e)
        {
            log.error(Messages.getString("ExportViewFactory.classnotfound", //$NON-NLS-1$
                new Object[]{name, viewClassName}));
            return;
        }
        catch (NoClassDefFoundError e)
        {
            log.warn(Messages.getString("ExportViewFactory.noclassdef" //$NON-NLS-1$
                , new Object[]{name, viewClassName, e.getMessage()}));
            return;
        }

        try
        {
            exportClass.newInstance();
        }
        catch (InstantiationException e)
        {
            log.error(Messages.getString("ExportViewFactory.instantiationexception", //$NON-NLS-1$
                new Object[]{name, viewClassName, e.getMessage()}));
            return;
        }
        catch (IllegalAccessException e)
        {
            log.error(Messages.getString("ExportViewFactory.illegalaccess", //$NON-NLS-1$
                new Object[]{name, viewClassName, e.getMessage()}));
            return;
        }
        catch (NoClassDefFoundError e)
        {
            log.warn(Messages.getString("ExportViewFactory.noclassdef" //$NON-NLS-1$
                , new Object[]{name, viewClassName, e.getMessage()}));
            return;
        }

        MediaTypeEnum media = MediaTypeEnum.registerMediaType(name);
        viewClasses.put(media, exportClass);

        if (log.isDebugEnabled())
        {
            log.debug(Messages.getString("ExportViewFactory.added", //$NON-NLS-1$ 
                new Object[]{media, viewClassName}));
        }
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
    public ExportView getView(MediaTypeEnum exportType, TableModel tableModel, boolean exportFullList,
        boolean includeHeader, boolean decorateValues)
    {
        ExportView view;

        Class viewClass = (Class) viewClasses.get(exportType);

        try
        {
            view = (ExportView) viewClass.newInstance();
        }
        catch (InstantiationException e)
        {
            // should not happen (class has already been instantiated before)
            throw new WrappedRuntimeException(getClass(), e);
        }
        catch (IllegalAccessException e)
        {
            // should not happen (class has already been instantiated before)
            throw new WrappedRuntimeException(getClass(), e);
        }

        view.setParameters(tableModel, exportFullList, includeHeader, decorateValues);
        return view;
    }

}