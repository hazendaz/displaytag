<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>


<jsp:include page="header.jsp" flush="true" />

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Wow, auto-modification of columns displayed</span></td>
<td align="right" valign="top" nowrap><a href="./example-column-chooser.html">View JSP Source</a></td>
</tr>
</table>
<p>

<h2><font color="#cc0000">Todo</font></h2>

You have a table that is showing four very useful columns of data, but the user
actually wants to see other properties of the objects then what you are giving
as defaults.  So they click on a little <b>columns...</b> link in the action
bar and up pops a little window that lists all the columns that can be displayed
and they can select the columns that they want to view.  It sticks a cookie
on their browser so it remembers all that and redisplays the table according to
their preferences...<p>

<center>
<display:table width="75%" name="test" >
  <display:column property="id" title="ID" />
  <display:column property="email" />
  <display:column property="status" />
</display:table>
</center>

<jsp:include page="footer.jsp" flush="true" />
</html>