<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="org.apache.taglibs.display.test.TestList"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>

<jsp:include page="header.jsp" flush="true" />

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Basic, columns</span></td>
<td align="right" valign="top" nowrap><a href="./example-columns.html">View JSP Source</a></td>
</tr>
</table>
<p>

<center>
<display:table width="75%" name="test" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="description" title="Comments"/>
</display:table>
</center>

<p>
This starts to show you how to use the table tag, you point the table tag at
a datasource (a List), and then define a number of columns that map to
property methods (getXXX) for each object in the List.<p>

Note that you have one column tag for every column that you want to appear in
the table, and the column specifies what property is shown in that particular
row.<p>

The column property specifies what <code>getXXX</code> method is called on each item
in the list.  So for the second column, <code>getName</code> is called, and by
default the property name is used as the header of the column (unless you give
it an explicit column name).

<jsp:include page="footer.jsp" flush="true" />
</html>