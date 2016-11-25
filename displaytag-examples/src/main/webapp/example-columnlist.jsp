<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:scriptlet>
<![CDATA[
  // available column sets should be managed in the backend, not in the jsp page.
  // you just need to build a list with Maps containing standard column attributes

  java.util.Map<String, String> email = new java.util.HashMap<String, String>();
  email.put("property", "email");
  email.put("title", "email title");


  java.util.Map<String, String> date = new java.util.HashMap<String, String>();
  date.put("property", "date");
  date.put("title", "date");
  date.put("sortable", "true");


  java.util.Map<String, String> money = new java.util.HashMap<String, String>();
  money.put("property", "money");
  money.put("title", "money");
  money.put("format", "{0,number,000.00 €}");

  java.util.List<java.util.Map<String, String>> set1 = new java.util.ArrayList<java.util.Map<String, String>>();
  java.util.List<java.util.Map<String, String>> set2 = new java.util.ArrayList<java.util.Map<String, String>>();
  java.util.List<java.util.Map<String, String>> set3 = new java.util.ArrayList<java.util.Map<String, String>>();

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
  request.setAttribute( "test", new org.displaytag.sample.TestList(10, false) );
]]>
  </jsp:scriptlet>
  <tags:page>
    <h1>Using predefined column lists</h1>
    <p>This example shows how a predefined set of columns can be applied to a table.</p>
    <p>
      All you have to do is feeding the table with a list of beans/Maps which contains the needed column attributes and
      create the needed
      <code><![CDATA[&lt;display:column>]]></code>
      tags and use a simple iteration directly in the jsp
    </p>
    <p>In this page we create three different set of colums, implemented as Lists of Maps, directly in the jsp.
      Obviously, you will probably want to configure them in a db, xml file, or any other source for a real use (Spring
      xml files could be an easy and flexible solution). No dipendence on displaytag-specific classes is required in
      your application, since you can simply use a plain Map or implement a custom bean.
    </p>
    <p>Click on a column set below to see it applied to the table.</p>
    <div class="btn-group stylelist">
      <a href="example-columnlist.jsp?set=1" type="button"
        class="btn btn-default ${param.set eq '1' or empty param.set? 'active' : ''}">column set 1</a>
      <a href="example-columnlist.jsp?set=2" type="button" class="btn btn-default ${param.set eq '2' ? 'active' : ''}">column set 2</a>
      <a href="example-columnlist.jsp?set=3" type="button" class="btn btn-default ${param.set eq '3' ? 'active' : ''}">column set 3</a>
    </div>
    <display:table name="test">
      <c:forEach var="cl" items="${collist}">
        <display:column property="${cl.property}" title="${cl.title}" sortable="${cl.sortable}" format="${cl.format}"/>
      </c:forEach>
    </display:table>
    <tags:code>
    <![CDATA[
<display:table name="test">
  <c:forEach var="cl" items="\${collist}">
    <display:column property="\${cl.property}" title="\${cl.title}" sortable="\${cl.sortable}" format="\${cl.format}"/>
  </c:forEach>
</display:table>
    ]]>
    </tags:code>
  </tags:page>
</jsp:root>