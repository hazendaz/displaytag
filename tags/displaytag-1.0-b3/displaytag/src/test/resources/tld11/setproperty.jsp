<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="../../../target/tld/displaytag-11.tld" prefix="display" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
	<title>Displaytag unit test</title>
</head>

<body>
  <% java.util.List testData = new java.util.ArrayList();
        testData.add(new org.displaytag.test.KnownValue());
        request.setAttribute( "test", testData);
  %>
  
  <display:table name="requestScope.test" id="table1">
    <display:column property="ant"/>
    <display:setProperty name="basic.show.header" value="false" />
  </display:table>
  
  <display:table name="requestScope.test" id="table2">
    <display:column property="ant"/>
  </display:table>
  
  <display:table name="requestScope.test" id="table3">
    <display:column property="ant"/>
    <display:setProperty name="basic.show.header">false</display:setProperty>
  </display:table>

</body>
</html>
