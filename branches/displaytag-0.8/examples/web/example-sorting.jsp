<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% Object foo = session.getAttribute( "stest" );
   if( foo == null ) {
      session.setAttribute( "stest", new TestList( 10 ) );
   }
%>


<jsp:include page="header.jsp" flush="true" />

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Wow, auto-sorting by columns</span></td>
<td align="right" valign="top" nowrap><a href="./example-sorting.html">View JSP Source</a></td>
</tr>
</table>
<p>

<center>
<display:table width="75%" name="stest" scope="session" >
  <display:column property="id" title="ID" sort="true"/>
  <display:column property="name" sort="true"/>
  <display:column property="email" />
  <display:column property="status" sort="true"/>


</display:table>
</center>
<p>

If you want to allow the user to sort the data as it is coming back, then you
need to just do two things, make sure that the data
returned from your property implements the Comparable interface (if it doesn't
nativly - use the decorator pattern as shown a couple of examples ago), and
then set the attribute <code>sort="true"</code> on the columns that you want
to be able to sort by.<p>

When the user clicks on the column title the rows
will be sorted in ascending order and redisplayed on the page.  If the user clicks
on the column title again, the data will get sorted in descending order and
redisplayed.<p>

Only the rows being shown on the page are sorted and resorted, so if you use
this attribute along with the pagesize attribute, it will not resort the
entire list.  It is assumed that if you want to allow the user to resort a large
list that will not all fit on a single page, then you will provide an interface
which is more appropriate to that task.<p>

When doing sorts, we copy the list into our own internally managed list, so
that we don't change the ordering of the original list. <p>


<jsp:include page="footer.jsp" flush="true" />
</html>