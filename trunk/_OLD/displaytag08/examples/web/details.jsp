<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="org.apache.taglibs.display.test.TestList,
                 java.util.List,
                 org.apache.taglibs.display.test.ListObject"%>
<%@ taglib uri="/WEB-INF/display.tld" prefix="display" %>

<% List list = (List)session.getAttribute( "details" ); %>

<jsp:include page="header.jsp" flush="true" />

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Test Object Details</span></td>
<td align="right" valign="top" nowrap><a href="./example-export.html">View JSP Source</a></td>
</tr>
</table>
<p>

This shows the details of the object that you just clicked on, you can image
that this page could be an edit panel, or another report, or perhaps just
like what is shown here - additional detailed information about the object that
you just clicked on.<p>

In these simple contrived example, we are keeping the master list of objects in
your session and we just do scans based on either the id or index passed in,
naturally your approach might be different (such as doing a database select
based on the primary key passed in...)<p>

<% if( request.getParameter( "action" ) != null ) { %>
<h2>Action: <%= request.getParameter( "action" ) %></h2>
<% } %>

<%
   ListObject obj = null;
   if( request.getParameter( "id" ) != null ) {
      int id = Integer.parseInt( request.getParameter( "id" ) );
      for( int i = 0; i < list.size() ; i++ ) {
         ListObject lobj = (ListObject)list.get(i);

         if( lobj.getId() == id ) { obj = lobj; break; }
      }
   }

   if( request.getParameter( "index" ) != null ) {
      int index = Integer.parseInt( request.getParameter( "index" ) );
      obj = (ListObject)list.get( index );
   }
%>

<blockquote>
<pre>
<%= obj.toDetailedString() %>
</pre>
</blockquote>

<jsp:include page="footer.jsp" flush="true" />


