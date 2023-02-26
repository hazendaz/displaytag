<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:scriptlet> request.setAttribute( "test", new org.displaytag.sample.ReportList(10) );</jsp:scriptlet>
  <tags:page>
    <h1>Using callbacks to show totals</h1>
    <display:table name="test" class="simple nocol" decorator="org.displaytag.decorator.TotalTableDecorator">
      <display:column property="city" title="CITY" group="1"/>
      <display:column property="project" title="PROJECT" group="2"/>
      <display:column property="amount" title="HOURS" total="true"/>
      <display:column property="task" title="TASK"/>
    </display:table>
    <p>
      The decorator API provides more then just the ability to reformat data before it is put into columns, it also
      provides a hook that allows you perform some action on the table after each row is processed. This allows you to
      interject some code that allows you to figure out where you are in the list of data, and then insert an additional
      row with totals, etc... Typically you would use this functionality along with
      <a href="./example-grouping.jsp">grouping</a>
      features to spit out some nice web based reports.
    </p>
    <p>See the TableDecorator.finishRow() API documentation, along with the example decorator that is used in this
      example page.
    </p>
    <tags:code>
    <![CDATA[
<display:table name="test" class="simple nocol" decorator="org.displaytag.decorator.TotalTableDecorator">
  <display:column property="city" title="CITY" group="1"/>
  <display:column property="project" title="PROJECT" group="2"/>
  <display:column property="amount" title="HOURS" total="true"/>
  <display:column property="task" title="TASK"/>
</display:table>
    ]]>
    </tags:code>
  </tags:page>
</jsp:root>
