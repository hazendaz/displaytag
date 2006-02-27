<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <jsp:scriptlet> request.setAttribute( "test", new TestList(10, false) ); </jsp:scriptlet>

  <h2>Showing subsets of data from the List</h2>

  <h3>Complete List</h3>

  <display:table name="test">
    <display:column property="id" title="ID" />
    <display:column property="email" />
    <display:column property="status" />
  </display:table>


  <h3>First 5 Items</h3>

  <display:table name="test" length="5">
    <display:column property="id" title="ID" />
    <display:column property="email" />
    <display:column property="status" />
  </display:table>


  <h3>Items 3-8</h3>

  <display:table name="test" offset="3" length="5">
    <display:column property="id" title="ID" />
    <display:column property="email" />
    <display:column property="status" />
  </display:table>



  <p>Let's say that you have a list that contains 300 elements, but you only want to show the first 10, or for some
  reason you want to show elements 101-120 (Yes, I know what you really want is to be able to page through them - be
  patient that example is coming up).</p>

  <p>You can use the <code>length</code> and <code>offset</code> attributes to limit the display to only portions of
  your List.</p>

  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
