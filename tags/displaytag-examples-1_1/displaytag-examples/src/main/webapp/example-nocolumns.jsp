<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <h2>Simplest case, no columns</h2>

  <jsp:scriptlet> request.setAttribute( "test", new ReportList(6) ); </jsp:scriptlet>
  <display:table name="test" />

  <p>The simplest possible usage of the table tag is to point the table tag at a java.util.List implementation and do
  nothing else. The table tag will iterate through the list and display a column for each property contained in the
  objects.</p>

  <p>Typically, the only time that you would want to use the tag in this simple way would be during development as a
  sanity check. For production, you should always define at least a single column.</p>


  <jsp:include page="inc/footer.jsp" flush="true" />
</jsp:root>
