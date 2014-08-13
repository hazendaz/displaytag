<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:directive.page import="org.displaytag.sample.*"/>
  <jsp:scriptlet> request.setAttribute( "test", new ReportList() );</jsp:scriptlet>
  <tags:page>
    <h1>Paging + sorting + grouping + exporting working together</h1>
    <display:table name="test" export="true" sort="list" pagesize="8">
      <display:column property="city" title="CITY" group="1" sortable="true" headerClass="sortable"/>
      <display:column property="project" title="PROJECT" group="2" sortable="true" headerClass="sortable"/>
      <display:column property="amount" title="HOURS"/>
      <display:column property="task" title="TASK"/>
    </display:table>
    <p>
      What happen if you put everything together, sorting the full list (attribute
      <code>sort="list"</code>
      ), using pagination and exporting?
    </p>
    <p>When sorting is enabled on the full list, the page number is automatically reset if you change the sorted column or the sort order.
    </p>
    <p>Exported documents contain the full list. Grouping is not applied in the exported documents.</p>
  </tags:page>
</jsp:root>