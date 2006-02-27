<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <jsp:scriptlet> request.setAttribute( "test", new ReportList() ); </jsp:scriptlet>

  <h2>Column grouping</h2>

  <display:table name="test" class="simple">
    <display:column property="city" title="CITY" group="1" />
    <display:column property="project" title="PROJECT" group="2" />
    <display:column property="amount" title="HOURS" />
    <display:column property="task" title="TASK" />
  </display:table>


  <p>You have a List who's objects are sorted and grouped by column A, column B and column C, so instead of repeating
  columns A, B over and over again, it does a grouping of those columns, and only shows data in those columns when it
  changes. Think of reports... We use the this display tag as a key part of our reporting framework.</p>

  <p>Grouping is straight-forward, simply make sure that your list that you are providing is sorted appropriately, then
  indicate the grouping order via the <strong>group</strong> attribute of the column tags.</p>


  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
