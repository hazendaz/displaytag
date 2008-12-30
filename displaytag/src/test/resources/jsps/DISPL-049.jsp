<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
 <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>Displaytag unit test</title>
  </head>
<body>
  <%
    java.util.List testData = new java.util.ArrayList();
    testData.add(new org.displaytag.test.KnownValue());
    testData.add(new org.displaytag.test.KnownValue());
    request.setAttribute( "test", testData);
    int count = 0;
  %>

  <display:table uid="table" name="test">
    <% count++; %>
    <display:column property="ant" style="<%="style-" + count%>" class="<%="class-" + count%>" />
  </display:table>

</body>
</html>