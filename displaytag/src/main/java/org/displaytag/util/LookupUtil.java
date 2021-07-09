/*
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.displaytag.exception.ObjectLookupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class with methods for object and properties retrieving.
 *
 * @author Fabrizio Giustina
 *
 * @version $Id$
 */
public final class LookupUtil {

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(LookupUtil.class);

    /** The Constant INDEXED_DELIM. */
    private static final char INDEXED_DELIM = '[';

    /** The Constant INDEXED_DELIM2. */
    private static final char INDEXED_DELIM2 = ']';

    /** The Constant MAPPED_DELIM. */
    private static final char MAPPED_DELIM = '(';

    /** The Constant MAPPED_DELIM2. */
    private static final char MAPPED_DELIM2 = ')';

    /** The Constant NESTED_DELIM. */
    private static final char NESTED_DELIM = '.';

    /**
     * don't instantiate a LookupUtil.
     */
    private LookupUtil() {
        // unused
    }

    /**
     * Read an object from the pagecontext with the specified scope and eventually lookup a property in it.
     *
     * @param pageContext
     *            PageContext
     * @param beanAndPropertyName
     *            String expression with bean name and attributes
     * @param scope
     *            One of the following values:
     *            <ul>
     *            <li>PageContext.PAGE_SCOPE</li>
     *            <li>PageContext.REQUEST_SCOPE</li>
     *            <li>PageContext.SESSION_SCOPE</li>
     *            <li>PageContext.APPLICATION_SCOPE</li>
     *            </ul>
     *
     * @return Object
     *
     * @throws ObjectLookupException
     *             for errors while retrieving a property in the bean
     */
    public static Object getBeanValue(final PageContext pageContext, final String beanAndPropertyName, final int scope)
            throws ObjectLookupException {

        if (beanAndPropertyName.indexOf('.') != -1) {
            // complex: property from a bean
            final String objectName = StringUtils.substringBefore(beanAndPropertyName, ".");
            final String beanProperty = StringUtils.substringAfter(beanAndPropertyName, ".");
            Object beanObject;

            if (LookupUtil.log.isDebugEnabled()) {
                LookupUtil.log.debug("getBeanValue - bean: {}, property: {}", objectName, beanProperty);
            }

            // get the bean
            beanObject = pageContext.getAttribute(objectName, scope);

            // if null return
            if (beanObject == null) {
                return null;
            }

            // go get the property
            return LookupUtil.getBeanProperty(beanObject, beanProperty);

        }

        // simple, only the javabean
        if (LookupUtil.log.isDebugEnabled()) {
            LookupUtil.log.debug("getBeanValue - bean: {}", beanAndPropertyName);
        }

        return pageContext.getAttribute(beanAndPropertyName, scope);
    }

    /**
     * <p>
     * Returns the value of a property in the given bean.
     * </p>
     * <p>
     * Handle <code>NestedNullException</code> returning nulls and other exceptions returning
     * <code>ObjectLookupException</code>.
     * </p>
     *
     * @param bean
     *            javabean
     * @param name
     *            name of the property to read from the javabean
     *
     * @return Object
     *
     * @throws ObjectLookupException
     *             for errors while retrieving a property in the bean
     */
    public static Object getBeanProperty(final Object bean, final String name) throws ObjectLookupException {

        Validate.notNull(bean, "No bean specified");
        Validate.notNull(name, "No name specified");

        if (LookupUtil.log.isDebugEnabled()) {
            LookupUtil.log.debug("getProperty [{}] on bean {}", name, bean);
        }

        try {
            return LookupUtil.getProperty(bean, name);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ObjectLookupException(LookupUtil.class, bean, name, e);
        } catch (final NestedNullException nne) {
            // don't throw exceptions for nulls
            return null;
        } catch (final IllegalArgumentException e) {
            // don't throw exceptions for nulls; the bean and name have already been checked; this is being thrown when
            // the bean property value is itself null.
            LookupUtil.log.debug(
                    "Caught IllegalArgumentException from beanutils while looking up " + name + " in bean " + bean, e);
            return null;
        }
    }

    /**
     * Return the value of the (possibly nested) property of the specified name, for the specified bean, with no type
     * conversions.
     *
     * @param bean
     *            Bean whose property is to be extracted
     * @param name
     *            Possibly nested name of the property to be extracted
     *
     * @return Object
     *
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     * @throws NoSuchMethodException
     *             the no such method exception
     */
    public static Object getProperty(final Object bean, final String name)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (LookupUtil.log.isDebugEnabled()) {
            LookupUtil.log.debug("getProperty [{}] on bean {}", name, bean);
        }

        Validate.notNull(bean, "No bean specified");
        Validate.notNull(name, "No name specified");

        Object evalBean = bean;
        String evalName = name;

        int indexOfINDEXEDDELIM = -1;
        int indexOfMAPPEDDELIM = -1;
        int indexOfMAPPEDDELIM2 = -1;
        int indexOfNESTEDDELIM = -1;
        while (true) {

            indexOfNESTEDDELIM = evalName.indexOf(LookupUtil.NESTED_DELIM);
            indexOfMAPPEDDELIM = evalName.indexOf(LookupUtil.MAPPED_DELIM);
            indexOfMAPPEDDELIM2 = evalName.indexOf(LookupUtil.MAPPED_DELIM2);
            if (indexOfMAPPEDDELIM2 >= 0 && indexOfMAPPEDDELIM >= 0
                    && (indexOfNESTEDDELIM < 0 || indexOfNESTEDDELIM > indexOfMAPPEDDELIM)) {
                indexOfNESTEDDELIM = evalName.indexOf(LookupUtil.NESTED_DELIM, indexOfMAPPEDDELIM2);
            } else {
                indexOfNESTEDDELIM = evalName.indexOf(LookupUtil.NESTED_DELIM);
            }
            if (indexOfNESTEDDELIM < 0) {
                break;
            }
            final String next = evalName.substring(0, indexOfNESTEDDELIM);
            indexOfINDEXEDDELIM = next.indexOf(LookupUtil.INDEXED_DELIM);
            indexOfMAPPEDDELIM = next.indexOf(LookupUtil.MAPPED_DELIM);
            if (evalBean instanceof Map) {
                evalBean = ((Map<?, ?>) evalBean).get(next);
            } else if (indexOfMAPPEDDELIM >= 0) {

                evalBean = PropertyUtils.getMappedProperty(evalBean, next);

            } else if (indexOfINDEXEDDELIM >= 0) {
                evalBean = LookupUtil.getIndexedProperty(evalBean, next);
            } else {
                evalBean = PropertyUtils.getSimpleProperty(evalBean, next);
            }

            if (evalBean == null) {
                LookupUtil.log.debug("Null property value for '{}'", evalName.substring(0, indexOfNESTEDDELIM));
                return null;
            }
            evalName = evalName.substring(indexOfNESTEDDELIM + 1);

        }

        indexOfINDEXEDDELIM = evalName.indexOf(LookupUtil.INDEXED_DELIM);
        indexOfMAPPEDDELIM = evalName.indexOf(LookupUtil.MAPPED_DELIM);

        if (evalBean instanceof Map) {
            evalBean = ((Map<?, ?>) evalBean).get(evalName);
        } else if (indexOfMAPPEDDELIM >= 0) {
            evalBean = PropertyUtils.getMappedProperty(evalBean, evalName);
        } else if (indexOfINDEXEDDELIM >= 0) {
            evalBean = LookupUtil.getIndexedProperty(evalBean, evalName);
        } else {
            evalBean = PropertyUtils.getSimpleProperty(evalBean, evalName);
        }

        return evalBean;

    }

    /**
     * Return the value of the specified indexed property of the specified bean, with no type conversions. The
     * zero-relative index of the required value must be included (in square brackets) as a suffix to the property name,
     * or <code>IllegalArgumentException</code> will be thrown. In addition to supporting the JavaBeans specification,
     * this method has been extended to support <code>List</code> objects as well.
     *
     * @param bean
     *            Bean whose property is to be extracted
     * @param name
     *            <code>propertyname[index]</code> of the property value to be extracted
     *
     * @return Object
     *
     * @exception IllegalAccessException
     *                if the caller does not have access to the property accessor method
     * @exception InvocationTargetException
     *                if the property accessor method throws an exception
     * @exception NoSuchMethodException
     *                if an accessor method for this propety cannot be found
     */
    public static Object getIndexedProperty(final Object bean, final String name)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        Validate.notNull(bean, "No bean specified");
        Validate.notNull(name, "No name specified");

        String evalName = name;

        // Identify the index of the requested individual property
        final int delim = evalName.indexOf(LookupUtil.INDEXED_DELIM);
        final int delim2 = evalName.indexOf(LookupUtil.INDEXED_DELIM2);
        if (delim < 0 || delim2 <= delim) {
            throw new IllegalArgumentException("Invalid indexed property '" + evalName + "'");
        }
        int index = -1;
        try {
            final String subscript = evalName.substring(delim + 1, delim2);
            index = Integer.parseInt(subscript);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("Invalid indexed property '" + evalName + "'");
        }
        evalName = evalName.substring(0, delim);

        if (LookupUtil.log.isDebugEnabled()) {
            LookupUtil.log.debug("getIndexedProperty property name={} with index {}", evalName, index);

        }

        // addd support for lists and arrays
        if (StringUtils.isEmpty(evalName)) {
            if (bean instanceof List) {
                return ((List<?>) bean).get(index);
            }
            if (bean.getClass().isArray()) {
                return ((Object[]) bean)[index];
            }
        }
        // Request the specified indexed property value
        return PropertyUtils.getIndexedProperty(bean, evalName, index);

    }

}