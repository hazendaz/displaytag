<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:scriptlet>
  <![CDATA[
   Object foo = session.getAttribute( "two-test1" );
   if( foo == null ) {
     session.setAttribute( "two-test1", new org.displaytag.sample.ReportList() );
     session.setAttribute( "two-test2", new org.displaytag.sample.ReportList(12) );
   }
   ]]>
  </jsp:scriptlet>
  <tags:page>
    <h1>Two tables working together in the same page with independent paging-sorting
    </h1>
    <display:table name="sessionScope.two-test1" sort="list" pagesize="10" id="table1" export="true">
      <display:column property="city" title="CITY" group="1" sortable="true" headerClass="sortable"/>
      <display:column property="project" title="PROJECT" group="2" sortable="true" headerClass="sortable"/>
      <display:column property="amount" title="HOURS"/>
      <display:column property="task" title="TASK"/>
    </display:table>
    <display:table name="sessionScope.two-test2" sort="list" pagesize="10" id="table2" export="true">
      <display:column property="city" title="CITY" group="1" sortable="true" headerClass="sortable"/>
      <display:column property="project" title="PROJECT" group="2" sortable="true" headerClass="sortable"/>
      <display:column property="amount" title="HOURS"/>
      <display:column property="task" title="TASK"/>
    </display:table>
    <p>
      Do you need to put more than one
      <code><![CDATA[&lt;display:table&gt;]]></code>
      in the same page, with independent pagination and sorting?
    </p>
    <p>
      No problem: parameter in pagination, sorting and exporting are encoded to be processed only by the source table.
      The only requirement is to provide a different
      <code>id</code>
      attribute for the table. If you click on sorting or pagination links in one of the previous tables the other one
      doesn't get affected by that.
    </p>
    <tags:code>
    <![CDATA[
<display:table name="sessionScope.two-test1" sort="list" pagesize="10" id="table1" export="true">
  <display:column property="city" title="CITY" group="1" sortable="true" headerClass="sortable"/>
  <display:column property="project" title="PROJECT" group="2" sortable="true" headerClass="sortable"/>
  <display:column property="amount" title="HOURS"/>
  <display:column property="task" title="TASK"/>
</display:table>

<display:table name="sessionScope.two-test2" sort="list" pagesize="10" id="table2" export="true">
  <display:column property="city" title="CITY" group="1" sortable="true" headerClass="sortable"/>
  <display:column property="project" title="PROJECT" group="2" sortable="true" headerClass="sortable"/>
  <display:column property="amount" title="HOURS"/>
  <display:column property="task" title="TASK"/>
</display:table>
    ]]>
    </tags:code>
  </tags:page>
</jsp:root>
