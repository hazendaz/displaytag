<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% request.setAttribute( "test", new TestList( 25 ) ); %>


<jsp:include page="header.jsp" flush="true" />

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Basic, showing subsets of data from the List</span></td>
<td align="right" valign="top" nowrap><a href="./example-subsets.html">View JSP Source</a></td>
</tr>
</table>
<p>


<center>
<table width="85%" border="0">
<tr>
<td width="48%" valign="top" align="left">
<center>Complete List</center>

<display:table name="test" >
  <display:column property="id" title="ID" />
  <display:column property="email" />
  <display:column property="status" />
</display:table>

</td>
<td width="4%">&nbsp;</td>
<td width="48%" valign="top" align="left">

<center>First 5 Items</center>
<display:table name="test" length="5">
  <display:column property="id" title="ID" />
  <display:column property="email" />
  <display:column property="status" />
</display:table>
<p>

<br>
<p>

<center>Items 11-20</center>
<display:table name="test" offset="10" length="10">
  <display:column property="id" title="ID" />
  <display:column property="email" />
  <display:column property="status" />
</display:table>


</td>
</tr>
</table>
</center>

<p>
Let's say that you have a list that contains 300 elements, but you only want
to show the first 10, or for some reason you want to show elements 101-120 (Yes,
I know what you really want is to be able to page through them - be patient
that example is coming up).<p>

You can use the <code>length</code> and <code>offset</code> attributes to limit
the display to only portions of your List.<p>


<jsp:include page="footer.jsp" flush="true" />
</html>