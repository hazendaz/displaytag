/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.export;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.displaytag.Messages;
import org.displaytag.exception.WrappedRuntimeException;
import org.displaytag.model.TableModel;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.ReflectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for export views.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public final class ExportViewFactory {

    /**
     * Singleton.
     */
    private static ExportViewFactory instance;

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(ExportViewFactory.class);

    /**
     * Map containing MediaTypeEnum - View class.
     */
    private final Map<MediaTypeEnum, Class<ExportView>> viewClasses = new HashMap<>();

    /**
     * Private constructor.
     */
    private ExportViewFactory() {
        final TableProperties properties = TableProperties.getInstance(null);
        final String[] exportTypes = properties.getExportTypes();

        if (ExportViewFactory.log.isInfoEnabled()) {
            ExportViewFactory.log.info(Messages.getString("ExportViewFactory.initializing", //$NON-NLS-1$
                    new Object[] { ArrayUtils.toString(exportTypes) }));
        }
        for (final String exportType : exportTypes) {
            final String className = properties.getExportClass(exportType);
            this.registerExportView(exportType, className);
        }
    }

    /**
     * Returns the simgleton for this class.
     *
     * @return ExportViewFactory instance
     */
    public static synchronized ExportViewFactory getInstance() {
        if (ExportViewFactory.instance == null) {
            ExportViewFactory.instance = new ExportViewFactory();
        }
        return ExportViewFactory.instance;
    }

    /**
     * Register a new Export View, associated with a Media Type. If another export view is currently associated with the
     * given media type it's replaced.
     *
     * @param name
     *            media name
     * @param viewClassName
     *            export view class name
     */
    public void registerExportView(final String name, final String viewClassName) {
        Class<ExportView> exportClass;
        try {
            exportClass = (Class<ExportView>) ReflectHelper.classForName(viewClassName);
        } catch (final ClassNotFoundException e) {
            ExportViewFactory.log.error(Messages.getString("ExportViewFactory.classnotfound", //$NON-NLS-1$
                    new Object[] { name, viewClassName }));
            return;
        } catch (final NoClassDefFoundError e) {
            ExportViewFactory.log.warn(Messages.getString("ExportViewFactory.noclassdef" //$NON-NLS-1$
                    , new Object[] { name, viewClassName, e.getMessage() }));
            return;
        }

        try {
            exportClass.getDeclaredConstructor().newInstance();
        } catch (final InstantiationException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            ExportViewFactory.log.error(Messages.getString("ExportViewFactory.instantiationexception", //$NON-NLS-1$
                    new Object[] { name, viewClassName, e.getMessage() }));
            return;
        } catch (final IllegalAccessException e) {
            ExportViewFactory.log.error(Messages.getString("ExportViewFactory.illegalaccess", //$NON-NLS-1$
                    new Object[] { name, viewClassName, e.getMessage() }));
            return;
        } catch (final NoClassDefFoundError e) {
            ExportViewFactory.log.warn(Messages.getString("ExportViewFactory.noclassdef" //$NON-NLS-1$
                    , new Object[] { name, viewClassName, e.getMessage() }));
            return;
        }

        final MediaTypeEnum media = MediaTypeEnum.registerMediaType(name);
        this.viewClasses.put(media, exportClass);

        if (ExportViewFactory.log.isDebugEnabled()) {
            ExportViewFactory.log.debug(Messages.getString("ExportViewFactory.added", //$NON-NLS-1$
                    new Object[] { media, viewClassName }));
        }
    }

    /**
     * returns an instance of export view associated with the given export type.
     *
     * @param exportType
     *            MediaTypeEnum
     * @param tableModel
     *            table model containing data to render
     * @param exportFullList
     *            should the complete list be exported?
     * @param includeHeader
     *            should header be included in export?
     * @param decorateValues
     *            should ouput be decorated?
     *
     * @return specialized instance of BaseExportView
     */
    public ExportView getView(final MediaTypeEnum exportType, final TableModel tableModel, final boolean exportFullList,
            final boolean includeHeader, final boolean decorateValues) {
        ExportView view;

        final Class<ExportView> viewClass = this.viewClasses.get(exportType);

        try {
            view = viewClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            // should not happen (class has already been instantiated before)
            throw new WrappedRuntimeException(this.getClass(), e);
        }

        view.setParameters(tableModel, exportFullList, includeHeader, decorateValues);
        return view;
    }

}
