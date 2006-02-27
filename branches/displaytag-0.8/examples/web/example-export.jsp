<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>

<%@ include file="header.jsp" %>
<%-- Can't do a flush here! <jsp:include page="header.jsp" flush="true" /> --%>

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Wow, data exporting (excel, csv, xml)</span></td>
<td align="right" valign="top" nowrap><a href="./example-export.html">View JSP Source</a></td>
</tr>
</table>
<p>

<center>
<display:table width="75%" name="test" export="true" decorator="org.apache.taglibs.display.test.Wrapper" >
  <display:column property="id" title="ID"/>
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="date" align="right" />
</display:table>
</center>

<p>
When you set the Table Tag's <b>export</b> attribute to "true", a footer will
appear below the table which will allow you to export the data being shown in
various formats, just click on the format (currently supporting CSV, Excel, and
a very crude XML). <p>

Down the road it makes sense to add of course PDF, and potentially others...<p>

<jsp:include page="footer.jsp" flush="true" />
</html>