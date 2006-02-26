/*
 * $Id$
 */

package org.apache.taglibs.display;

/**
 * @version $Revision$
 */
public abstract class ColumnDecorator extends Decorator {
    public ColumnDecorator() {
        super();
    }

    public abstract String decorate(Object columnValue);
}