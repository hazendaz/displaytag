<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net" xmlns:c="http://java.sun.com/jsp/jstl/core">
  <html>
    <head>
      <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
      <title>Displaytag unit test</title>
    </head>
    <body>
      <jsp:scriptlet>
  <![CDATA[
    java.util.List<org.displaytag.test.KnownValue> testData = new java.util.ArrayList<org.displaytag.test.KnownValue>();
    testData.add(new org.displaytag.test.KnownValue());
    testData.add(new org.displaytag.test.KnownValue());
    request.setAttribute( "test", testData);
    ]]>
      </jsp:scriptlet>
      <jsp:scriptlet>pageContext.setAttribute("counter", Integer.valueOf(0));</jsp:scriptlet>
      <display:table uid="table" name="test">
        <jsp:scriptlet>pageContext.setAttribute("counter", ((Integer)pageContext.getAttribute("counter")) + 1);</jsp:scriptlet>
        <!-- using a scriptlet because c:set randomically fails when tests are run by maven -->
        <display:column property="ant" style="style-${counter}" class="class-${counter}"/>
      </display:table>
    </body>
  </html>
</jsp:root>