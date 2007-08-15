<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:directive.page import="java.util.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <jsp:scriptlet> request.setAttribute( "test", new TestList(10, false) ); </jsp:scriptlet>
  <jsp:scriptlet> request.setAttribute( "test2", new ArrayList() ); </jsp:scriptlet>
  <jsp:scriptlet> Object foo = session.getAttribute( "test3" );
   if( foo == null ) {
      session.setAttribute( "test3", new TestList(320, false) );
   }
</jsp:scriptlet>

  <h2>Miscellaneous...</h2>

  <p>This page shows how to use various bells and whistles that are features of the display taglib that you might not be
  aware of.</p>

  <p>This table shows how you can use the "nulls" attribute to suppress "null" values that might be returned from your
  business objects. It also shows the "maxLength" attribute being used to restrict the size of the LongDescription
  column, so that it fits within a certain size of table.</p>

  <display:table name="test">
    <display:column property="id" title="ID" />
    <display:column property="nullValue" nulls="false" />
    <display:column property="longDescription" maxLength="60" style="white-space: nowrap;" />
  </display:table>


  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
