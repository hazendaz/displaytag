/**
 * This is an abstract class that most tags should inherit from, it provides a
 * number of utility methods that allow tags to read in a template or multiple
 * template files from the web/templates directory, and use those templates as
 * flexible StringBuffers that reread themselves when their matching file
 * changes, etc...
 *
 * $Id$
 **/

package org.apache.taglibs.display;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

abstract public class TemplateTag extends BodyTagSupport
{

   public void write( String val ) throws JspTagException
   {
      try {
         JspWriter out = pageContext.getOut();
         out.write( val );
      } catch( IOException e ) {
         throw new JspTagException( "Writer Exception: " + e );
      }
   }

   public void write( StringBuffer val ) throws JspTagException
   {
      this.write( val.toString() );
   }

}

