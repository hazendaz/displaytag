/**
 * $Id$
 *
 * Status: Ok
 **/

package org.apache.taglibs.display;

public abstract class ColumnDecorator extends Decorator
{
   public ColumnDecorator()
   {
      super();
   }

   public abstract String decorate( Object columnValue );
}

