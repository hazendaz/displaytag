<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:scriptlet> request.setAttribute( "test", new org.displaytag.sample.ReportList() );</jsp:scriptlet>
  <tags:page>
    <h1>Column grouping</h1>
    <display:table name="test" class="simple">
      <display:column property="city" title="CITY" group="1"/>
      <display:column property="project" title="PROJECT" group="2"/>
      <display:column property="amount" title="HOURS"/>
      <display:column property="task" title="TASK"/>
    </display:table>
    <p>You have a List who's objects are sorted and grouped by column A, column B and column C, so instead of repeating
      columns A, B over and over again, it does a grouping of those columns, and only shows data in those columns when
      it changes. Think of reports... We use the this display tag as a key part of our reporting framework.
    </p>
    <p>
      Grouping is straight-forward, simply make sure that your list that you are providing is sorted appropriately, then
      indicate the grouping order via the
      <code>group</code>
      attribute of the column tags.
    </p>
    <tags:code>
    <![CDATA[
<display:table name="test" class="simple">
  <display:column property="city" title="CITY" group="1"/>
  <display:column property="project" title="PROJECT" group="2"/>
  <display:column property="amount" title="HOURS"/>
  <display:column property="task" title="TASK"/>
</display:table>
    ]]>
    </tags:code>
  </tags:page>
</jsp:root>