<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net" xmlns:c="http://java.sun.com/jsp/jstl/core">
  <html>
    <head>
      <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
      <title>Displaytag unit test</title>
    </head>
    <body>
      <jsp:scriptlet>
  <![CDATA[
    java.util.List testData = new java.util.ArrayList();
    testData.add(new org.displaytag.test.KnownValue());
    testData.add(new org.displaytag.test.KnownValue());
    request.setAttribute( "test", testData);
    ]]>
      </jsp:scriptlet>
      <c:set var="count" value="0"/>
      <display:table uid="table" name="test">
        <c:set var="count" value="${count + 1}"/>
        <display:column property="ant" style="style-${count}" class="class-${count}"/>
      </display:table>
    </body>
  </html>
</jsp:root>