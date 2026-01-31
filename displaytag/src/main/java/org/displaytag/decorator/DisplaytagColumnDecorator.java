/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import javax.servlet.jsp.PageContext;

import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 * Interface for simple column decorators.
 * <p>
 * A column decorator is called after the object has been retrieved and it can "transform" the object before the
 * rendering.
 * <p>
 * The <code>DisplaytagColumnDecorator</code> interface has been introduced in displaytag 1.1 and replaces the previous
 * <code>ColumnDecorator</code> interface, adding the pageContext and media parameters, and changing the return type to
 * object to allow decorator chaining.
 *
 * @since 1.1
 */
public interface DisplaytagColumnDecorator {

    /**
     * Called after the object has been retrieved from the bean contained in the list. The decorate method is
     * responsible for transforming the object into a string to render in the page.
     *
     * @param columnValue
     *            Object to decorate
     * @param pageContext
     *            jsp page context
     * @param media
     *            current media (html, pdf, excel...)
     *
     * @return Object decorated object
     *
     * @throws DecoratorException
     *             wrapper exception for any exception thrown during decoration
     */
    Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException;

}
