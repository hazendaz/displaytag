<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:scriptlet> request.setAttribute( "test", new org.displaytag.sample.ReportList(6) );</jsp:scriptlet>
  <tags:page>
    <h1>Simplest case, no columns</h1>
    <display:table name="test"/>
    <p>
      The simplest possible usage of the table tag is to point the table tag at a
      <code>java.util.List</code>
      implementation and do nothing else. The table tag will iterate through the list and display a column for each
      property contained in the objects.
    </p>
    <p>Typically, the only time that you would want to use the tag in this simple way would be during development as a
      sanity check. For production, you should always define at least a single column.
    </p>
    <tags:code>
    <![CDATA[
<jsp:scriptlet>
  request.setAttribute( "test", new org.displaytag.sample.ReportList(6));
</jsp:scriptlet>

<display:table name="test"/>
    ]]>
    </tags:code>
  </tags:page>
</jsp:root>
