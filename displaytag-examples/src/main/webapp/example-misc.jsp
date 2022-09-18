<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:scriptlet>
  <![CDATA[
   request.setAttribute( "test", new org.displaytag.sample.TestList(10, false) ); 
   request.setAttribute( "test2", new java.util.ArrayList<>() );  
    Object foo = session.getAttribute( "test3" );
   if( foo == null ) {
      session.setAttribute( "test3", new org.displaytag.sample.TestList(320, false) );
   }
   ]]>
  </jsp:scriptlet>
  <tags:page>
    <h1>Miscellaneous...</h1>
    <p>This page shows how to use various bells and whistles that are features of the display taglib that you might not
      be aware of.
    </p>
    <p>
      This table shows how you can use the
      <code>nulls</code>
      attribute to suppress "null" values that might be returned from your business objects. It also shows the
      <code>maxLength</code>
      attribute being used to restrict the size of the LongDescription column, so that it fits within a certain size of
      table.
    </p>
    <display:table name="test">
      <display:column property="id" title="ID"/>
      <display:column property="nullValue" nulls="false"/>
      <display:column property="longDescription" maxLength="60" style="white-space: nowrap;"/>
    </display:table>
    <tags:code>
    <![CDATA[
<display:table name="test">
  <display:column property="id" title="ID"/>
  <display:column property="nullValue" nulls="false"/>
  <display:column property="longDescription" maxLength="60" style="white-space: nowrap;"/>
</display:table>
    ]]>
    </tags:code>
  </tags:page>
</jsp:root>