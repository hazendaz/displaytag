<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net/el"
  xmlns:c="urn:jsptld:http://java.sun.com/jstl/core">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <jsp:scriptlet>

  // available column sets should be managed in the backend, not in the jsp page.
  // you just need to build a list with Maps containing standard column attributes

  java.util.Map email = new java.util.HashMap();
  email.put("property", "email");
  email.put("title", "email title");


  java.util.Map date = new java.util.HashMap();
  date.put("property", "date");
  date.put("title", "date");
  date.put("sortable", Boolean.TRUE);


  java.util.Map money = new java.util.HashMap();
  money.put("property", "money");
  money.put("title", "money");
  money.put("format", "{0,number,000.00 €}");

  java.util.List set1 = new java.util.ArrayList();
  java.util.List set2 = new java.util.ArrayList();
  java.util.List set3 = new java.util.ArrayList();

  set1.add(email);
  set1.add(date);
  set1.add(money);

  set2.add(money);
  set2.add(email);

  set3.add(date);
  set3.add(date);

  // this is the logic for choosing a column set
  // should be done in a controller/viewer helper, not in the jsp

  String choose = request.getParameter("set");
  if ("3".equals(choose))
  {
      request.setAttribute("collist", set3);
  }
  else if ("2".equals(choose))
  {
      request.setAttribute("collist", set2);
  }
  else
  {
      request.setAttribute("collist", set1);
  }

  // just prepare the usual list
  request.setAttribute( "test", new TestList(10, false) );

  </jsp:scriptlet>


  <h2>Using predefined column lists</h2>

  <p>This example shows how a predefined set of columns can be applied to a table.</p>
  <p>All you have to do is feeding the table with a list of beans/Maps which contains the needed column attributes and
  create the needed <code><![CDATA[&lt;display:column>]]></code> tags and use a simple iteration directly in the jsp</p>
  <p>In this page we create three different set of colums, implemented as Lists of Maps, directly in the jsp. Obviously,
  you will probably want to configure them in a db, xml file, or any other source for a real use (Spring xml files could
  be an easy and flexible solution). No dipendence on displaytag-specific classes is required in your application, since
  you can simply use a plain Map or implement a custom bean.</p>
  <p>Click on a column set below to see it applied to the table.</p>

  <br />
  <br />

  <ul id="stylelist">
    <li><a href="example-columnlist.jsp?set=1">column set 1</a></li>
    <li><a href="example-columnlist.jsp?set=2">column set 2</a></li>
    <li><a href="example-columnlist.jsp?set=3">column set 3</a></li>
  </ul>

  <br />
  <br />

  <display:table name="test">
    <c:forEach var="cl" items="${collist}">
      <display:column property="${cl.property}" title="${cl.title}" sortable="${cl.sortable}" format="${cl.format}" />
    </c:forEach>
  </display:table>

  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
