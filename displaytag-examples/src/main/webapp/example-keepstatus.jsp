<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />
  <h2>Posting form values and selecting rows with checkboxes</h2>
  <jsp:scriptlet>
    <![CDATA[
        if (request.getSession().getAttribute( "testform")==null){
          request.getSession().setAttribute( "testform", new TestList(30, false) );
        }


            ]]>
  </jsp:scriptlet>
  <display:table name="sessionScope.testform" id="table" pagesize="10" keepStatus="true">
    <display:column property="id" />
    <display:column property="name" sortable="true" />
    <display:column property="email" />
    <display:column property="description" />
  </display:table>
  <p>
    This example shows how you can tell displaytag to preserve the current pagination status using session, so that you
    can navigate away to other pages and find your table back where you left it automatically.
  </p>
  <p>
    This behavior is controlled by the
    <code>keepStatus</code>
    table attribute. If you need to clear the current status you can set another attribute
    <code>clearStatus</code>
    to true.
  </p>
  <hr />
  <p>
    <strong>
      <a href="?">Click here</a>
    </strong>
    in order to test a link the reload the current page without passing any parameter. You will see that displaytag will
    reload the same page.
  </p>
  <jsp:include page="inc/footer.jsp" flush="true" />
</jsp:root>
