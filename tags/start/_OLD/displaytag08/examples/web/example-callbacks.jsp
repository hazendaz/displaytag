<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List,
                 org.apache.taglibs.display.test.ReportList"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% request.setAttribute( "test", new ReportList( 10 ) ); %>


<jsp:include page="header.jsp" flush="true" />
<link rel=stylesheet href="./report.css" type="text/css">

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Using callbacks to show totals</span></td>
<td align="right" valign="top" nowrap><a href="./example-callbacks.html">View JSP Source</a></td>
</tr>
</table>
<p>

<center>
<display:table width="75%" name="test" decorator="org.apache.taglibs.display.test.TotalWrapper" >
  <display:column property="city" title="CITY" align="left" group="1"/>
  <display:column property="project" title="PROJECT" align="left" group="2"/>
  <display:column property="amount" title="HOURS" align="left" />
  <display:column property="task" title="TASK" align="left"/>
</display:table>
</center>

<p>
The decorator API provides more then just the ability to reformat data before
it is put into columns, it also provides a hook that allows you perform some
action on the table after each row is processed.  This allows you to interject
some code that allows you to figure out where you are in the list of data, and
then insert an additional row with totals, etc...  Typically you would use this
functionality along with <a href="./example-grouping.jsp">grouping</a> features to spit out some
nice web based reports.<p>

See the TableDecorator.finishRow() API documentation, along with the example
decorator that is used in this example page.  The source for the decorator for
this page is <a href="./TotalWrapper.java.txt">here</a>.<p>


<jsp:include page="footer.jsp" flush="true" />
</html>