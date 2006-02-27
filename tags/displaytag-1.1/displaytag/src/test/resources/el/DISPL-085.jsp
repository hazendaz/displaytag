<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:display="urn:jsptld:http://displaytag.sf.net/el" xmlns:c="urn:jsptld:http://java.sun.com/jstl/core">
  <jsp:text> <![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ]]> </jsp:text>
    <jsp:directive.page contentType="text/html; charset=UTF8"/>
  <html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
      <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
      <title>Displaytag unit test</title>
    </head>
    <body>
      <jsp:scriptlet>
          request.setAttribute( "test", new Object[]{new org.displaytag.test.KnownValue()});

          // prepare column collection
          java.util.Map antColumn = new java.util.HashMap();
          antColumn.put("property", "ant");
          antColumn.put("title", "ant title");
          antColumn.put("sortable", Boolean.TRUE);

          java.util.Map beeColumn = new java.util.HashMap();
          beeColumn.put("property", "bee");
          beeColumn.put("title", "bee title");

          java.util.List columnlist = new java.util.ArrayList();
          columnlist.add(antColumn);
          columnlist.add(beeColumn);

          request.setAttribute("columnlist", columnlist);

      </jsp:scriptlet>
      <display:table name="requestScope.test" uid="table">
        <c:forEach var="cl" items="${columnlist}">
          <display:column property="${cl.property}" title="${cl.title}" sortable="${cl.sortable}" />
        </c:forEach>
      </display:table>
    </body>
  </html>
</jsp:root>