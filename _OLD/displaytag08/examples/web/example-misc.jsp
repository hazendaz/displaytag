<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

 <%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List,
                 java.util.ArrayList"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>
<% request.setAttribute( "test2", new ArrayList() ); %>
<% Object foo = session.getAttribute( "test3" );
   if( foo == null ) {
      session.setAttribute( "test3", new TestList( 320 ) );
   }
%>

<%@ include file="header.jsp" %>

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Miscellaneous... </span></td>
<td align="right" valign="top" nowrap><a href="./example-misc.html">View JSP Source</a></td>
</tr>
</table>
<p>

This page shows how to use various bells and whistles that are features of the
display taglib that you might not be aware of.<p>

This table shows how you can use the "nulls" attribute to suppress "null" values
that might be returned from your business objects.  It also shows the "maxLength"
attribute being used to restrict the size of the LongDescription column, so that
it fits within a certain size of table.<p>

<center>
<display:table width="80%" name="test" >
  <display:column property="id" title="ID" />
  <display:column property="nullValue" nulls="false"/>
  <display:column property="longDescription" maxLength="60" nowrap="true"/>
</display:table>
</center>


<jsp:include page="footer.jsp" flush="true" />
</html>