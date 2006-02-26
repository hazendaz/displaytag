<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List,
                 org.apache.taglibs.display.test.ReportList"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% request.setAttribute( "test", new ReportList() ); %>


<jsp:include page="header.jsp" flush="true" />
<link rel=stylesheet href="./report.css" type="text/css">

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Wow, column grouping</span></td>
<td align="right" valign="top" nowrap><a href="./example-grouping.html">View JSP Source</a></td>
</tr>
</table>
<p>

<center>
<display:table bgcolor="#eeeeee" width="75%" name="test" >
  <display:column property="city" title="CITY" align="left" group="1"/>
  <display:column property="project" title="PROJECT" align="left" group="2"/>
  <display:column property="amount" title="HOURS" align="left" />
  <display:column property="task" title="TASK" align="left"/>
</display:table>
</center>

<p>
You have a List who's objects are sorted and grouped by column A, column B and
column C, so instead of repeating columns A, B over and over again, it does a
grouping of those columns, and only shows data in those columns when it changes.
Think of reports...  We use the this display tag as a key part of our
reporting framework.<p>

Grouping is straight-forward, simply make sure that your list that you are
providing is sorted appropriately, then then indicate the grouping
order via the <b>group</b> attribute of the column tags.<p>

The <a href="example-callbacks.jsp">callbacks</a> example shows how to extend this and use callbacks
to extend the table tag in order to show totals for particular groups.  Finally
pair this with the
<a href="example-export.jsp">data exporting</a> and the soon to be implemented
ResultSet functionality and you have a key piece of
an easy to understand reporting framework.<p>

<jsp:include page="footer.jsp"  flush="true"/>
</html>