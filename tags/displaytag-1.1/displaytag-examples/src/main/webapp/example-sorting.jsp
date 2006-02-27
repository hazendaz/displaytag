<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />


  <jsp:scriptlet> Object foo = session.getAttribute( "stest" );
   if( foo == null ) {
      session.setAttribute( "stest", new TestList( 10, false ) );
   }
</jsp:scriptlet>


  <h2>Auto-sorting by columns</h2>

  <display:table name="sessionScope.stest" defaultsort="1" defaultorder="descending">
    <display:column property="id" title="ID" sortable="true" headerClass="sortable" />
    <display:column property="name" sortable="true" headerClass="sortable" />
    <display:column property="email" />
    <display:column property="status" sortable="true" headerClass="sortable" />
  </display:table>

  <p>If you want to allow the user to sort the data as it is coming back, then you need to just do two things, make sure
  that the data returned from your property implements the Comparable interface (if it doesn't natively - use the
  decorator pattern as shown a couple of examples ago), and then set the attribute <code>sortable="true"</code> on the
  columns that you want to be able to sort by.</p>

  <p>Note the <code>sortable</code> attribute was previously named <code>sort</code>. The sort attribute is still
  supported for compatibility with previous versions.</p>

  <p>When the user clicks on the column title the rows will be sorted in ascending order and redisplayed on the page. If
  the user clicks on the column title again, the data will get sorted in descending order and redisplayed.</p>

  <p>Only the rows being shown on the page are sorted and resorted, so if you use this attribute along with the pagesize
  attribute, it will not resort the entire list. It is assumed that if you want to allow the user to resort a large list
  that will not all fit on a single page, then you will provide an interface which is more appropriate to that task.</p>

  <p>When doing sorts, we copy the list into our own internally managed list, so that we don't change the ordering of
  the original list.</p>

  <p>All the parameters received in the first request are preserved in sorting.</p>

  <p>You can also define the column to be sorted by default (if the user has not yet clicked on any sortable column)
  specifying the column index in the <code>defaultsort</code> attribute of the table tag. The default order can be set
  using the <code>defaultorder</code> attribute: the default value is <code>ascending</code>, you can set it to <code>descending</code>
  to reverse the order.</p>


  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
