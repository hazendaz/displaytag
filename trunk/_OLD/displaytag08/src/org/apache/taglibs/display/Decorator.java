/*
 * $Id$
 *
 * Todo
 *   - push the appropriate stuff down into TableDecorator
 */

package org.apache.taglibs.display;

import java.util.Collection;
import java.util.List;

import javax.servlet.jsp.PageContext;

/**
 * This class provides some basic functionality for all objects which
 * serve as decorators for the objects in the List being displayed.
 *
 * @version $Revision$
 */
public abstract class Decorator extends Object {
    private PageContext ctx = null;
    private Object collection = null;

    private Object obj = null;
    private int viewIndex = -1;
    private int listIndex = -1;

    public Decorator() {
    }

    public void init(PageContext ctx, Object list) {
        this.ctx = ctx;
        this.collection = list;
    }

    public String initRow(Object obj, int viewIndex, int listIndex) {
        this.obj = obj;
        this.viewIndex = viewIndex;
        this.listIndex = listIndex;
        return "";
    }

    public String startRow() {
        return "";
    }

    public String finishRow() {
        return "";
    }

    public void finish() {

    }

    public PageContext getPageContext() {
        return this.ctx;
    }

    public List getList() {
        if (this.collection instanceof List)
            return (List) this.collection;
        else
            throw new RuntimeException("This function is only supported if the given collection is a java.util.List.");
    }

    public Collection getCollection() {
        if (this instanceof Collection)
            return (Collection) this.collection;
        else
            throw new RuntimeException("This function is only supported if the given collection is a java.util.Collection.");
    }

    public Object getObject() {
        return this.obj;
    }

    public int getViewIndex() {
        return this.viewIndex;
    }

    public int getListIndex() {
        return this.listIndex;
    }
}