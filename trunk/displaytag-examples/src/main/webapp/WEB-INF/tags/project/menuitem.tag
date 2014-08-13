<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project">
  <jsp:directive.attribute name="href" type="java.lang.String" required="true" rtexprvalue="true"/>
  <li class="${fn:contains(pageContext.request.requestURI, href) ? 'active' : ''}">
    <a href="${href}">
      <jsp:doBody/>
    </a>
  </li>
</jsp:root>