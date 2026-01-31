/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import jakarta.servlet.jsp.PageContext;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils2.MappedPropertyDescriptor;
import org.apache.commons.beanutils2.PropertyUtils;
import org.displaytag.model.TableModel;

/**
 * This class provides some basic functionality for all objects which serve as decorators for the objects in the List
 * being displayed.
 * <p>
 * Decorator should never be subclassed directly. Use TableDecorator instead
 */
abstract class Decorator {

    /**
     * Char used to separate class name and property in the cache key.
     */
    private static final char CLASS_PROPERTY_SEPARATOR = '#';

    /**
     * property info cache contains classname#propertyname Strings as keys and Booleans as values.
     */
    private static Map<String, Boolean> propertyMap = new ConcurrentHashMap<>();

    /**
     * page context.
     */
    private PageContext pageContext;

    /**
     * decorated object. Usually a List
     */
    private Object decoratedObject;

    /**
     * The table model.
     *
     * @since 1.1
     */
    protected TableModel tableModel;

    /**
     * Initialize the TableTecorator instance.
     *
     * @param pageContext
     *            PageContext
     * @param decorated
     *            decorated object (usually a list)
     *
     * @see #init(PageContext, Object, TableModel)
     *
     * @deprecated use #init(PageContext, Object, TableModel)
     */
    @Deprecated
    public void init(final PageContext pageContext, final Object decorated) {
        this.init(pageContext, decorated, null);
    }

    /**
     * Initialize the TableTecorator instance.
     *
     * @param pageContext
     *            PageContext
     * @param decorated
     *            decorated object (usually a list)
     * @param tableModel
     *            table model
     */
    public void init(final PageContext pageContext, final Object decorated, final TableModel tableModel) {
        this.pageContext = pageContext;
        this.decoratedObject = decorated;
        this.tableModel = tableModel;
    }

    /**
     * returns the page context.
     *
     * @return PageContext
     */
    public PageContext getPageContext() {
        return this.pageContext;
    }

    /**
     * returns the decorated object.
     *
     * @return Object
     */
    public Object getDecoratedObject() {
        return this.decoratedObject;
    }

    /**
     * Called at the end of evaluation to clean up instance variable. A subclass of Decorator can override this method
     * but should always call super.finish() before return
     */
    public void finish() {
        this.pageContext = null;
        this.decoratedObject = null;
    }

    /**
     * Check if a getter exists for a given property. Uses cached info if property has already been requested. This
     * method only check for a simple property, if pPropertyName contains multiple tokens only the first part is
     * evaluated
     *
     * @param propertyName
     *            name of the property to check
     *
     * @return boolean true if the decorator has a getter for the given property
     */
    public boolean hasGetterFor(final String propertyName) {
        String simpleProperty = propertyName;

        // get the simple (not nested) bean property
        final int indexOfDot = simpleProperty.indexOf('.');
        if (indexOfDot > 0) {
            simpleProperty = simpleProperty.substring(0, indexOfDot);
        }

        final Boolean cachedResult = Decorator.propertyMap
                .get(this.getClass().getName() + Decorator.CLASS_PROPERTY_SEPARATOR + simpleProperty);

        if (cachedResult != null) {
            return cachedResult.booleanValue();
        }

        // not already cached... check
        final boolean hasGetter = this.searchGetterFor(propertyName);

        // save in cache
        Decorator.propertyMap.put(this.getClass().getName() + Decorator.CLASS_PROPERTY_SEPARATOR + simpleProperty,
                hasGetter);

        // and return
        return hasGetter;

    }

    /**
     * Looks for a getter for the given property using introspection.
     *
     * @param propertyName
     *            name of the property to check
     *
     * @return boolean true if the decorator has a getter for the given property
     */
    public boolean searchGetterFor(final String propertyName) {

        boolean result = false;

        try {
            // using getPropertyType instead of isReadable since isReadable doesn't support mapped properties.
            // Note that this method usually returns null if a property is not found and doesn't throw any exception
            // also for non existent properties
            final PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(this, propertyName);

            if (pd != null) {
                // double check, see tests in TableDecoratorTest
                if (pd instanceof MappedPropertyDescriptor) {
                    result = ((MappedPropertyDescriptor) pd).getMappedReadMethod() != null;
                } else if (pd instanceof IndexedPropertyDescriptor) {
                    result = ((IndexedPropertyDescriptor) pd).getIndexedReadMethod() != null;
                } else {
                    result = pd.getReadMethod() != null;
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            // ignore
        }

        return result;

    }

}
