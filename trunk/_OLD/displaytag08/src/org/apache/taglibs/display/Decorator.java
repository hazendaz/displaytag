/**
 * $Id$
 *
 * Status: Ok
 *
 * Todo
 *   - push the appropriate stuff down into TableDecorator
 **/

package org.apache.taglibs.display;

import java.util.List;
import javax.servlet.jsp.PageContext;

/**
 * This class provides some basic functionality for all objects which serve
 * as decorators for the objects in the List being displayed.
 **/

public abstract class Decorator extends Object
{
   private PageContext ctx = null;
   private List list = null;

   private Object obj = null;
   private int viewIndex = -1;
   private int listIndex = -1;


   public Decorator() {

   }

   public void init( PageContext ctx, List list ) {
      this.ctx = ctx;
      this.list = list;
   }

   public String initRow( Object obj, int viewIndex, int listIndex ) {
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

   public PageContext getPageContext() { return this.ctx; }
   public List getList() { return this.list; }

   public Object getObject() { return this.obj; }
   public int getViewIndex() { return this.viewIndex; }
   public int getListIndex() { return this.listIndex; }
}
