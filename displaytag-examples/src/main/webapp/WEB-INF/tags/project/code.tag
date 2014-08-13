<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project">
  <jsp:directive.attribute name="type" type="java.lang.String" required="false" rtexprvalue="true"/>
  <c:set var="body">
    <jsp:doBody/>
  </c:set>
  <div class="highlight">
    <pre>
      <code class="${!empty type ? type : 'html'}">
        <jsp:text>${fn:escapeXml(body)}</jsp:text>
      </code>
    </pre>
  </div>
</jsp:root>