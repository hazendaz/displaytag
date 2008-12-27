<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <jsp:scriptlet> Object foo = session.getAttribute( "test" );
   if( foo == null ) {
      session.setAttribute( "test", new TestList(120, false) );
   }
</jsp:scriptlet>

  <h2>Auto-paging of long lists</h2>

  <display:table name="sessionScope.test" pagesize="10">
    <display:column property="id" title="ID" />
    <display:column property="name" />
    <display:column property="email" />
    <display:column property="status" />
  </display:table>

  <p>Ok, you have made a query that returns you back a list of 120 elements, but you don't want to show the user all 120
  items at once, you want to allow them to page through the results (pretend 120 is really 1200 or 12000).</p>

  <p>One of the most common web UI techniques is to show a page of data (items 1-10), and then let the user click on a
  page number and allow them to scroll through the list. Include the "pagesize" attribute to your table tag, and the tag
  takes care of the reset.</p>

  <p>You need to design your page, so that the JSP page with the table tag can be reloaded without reloading the entire
  list of entries. It is assumed that you have gone through the action of loading the list in a previous page (or action
  in Struts-speak), and then redirected the person to the page with the table tag on it.</p>

  <p>You need to do this because the table tag's various page links will all point to the page the table tag is on (but
  also providing various parameters - which the table tag itself parses and deals with). So if you are doing any
  processing on the page, it can either 1) simply not work because the table tag doesn't know what parameters to pass
  for your processing, or 2) slow things down because you would be reloading the entire list on each page, rather then
  just showing a subset of the list. It sounds confusing, but that's just because I do a poor job of explaining it...</p>

  <p>By default, the table tag tries to figure out what the URL is for the page it is on by calling the
  request.getRequestURI() method, but this will not always return a correct answer in environment where you are
  forwarding a request around before arriving at the JSP that is to be displayed (like struts). In those cases, you need
  to tell the table tag what it's URL is via the "requestURI" attribute.</p>

  <p>All the parameters received in the first request are preserved in paging. You can exclude specific parameters using
  the "excludedParams" table attribute.</p>

  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
