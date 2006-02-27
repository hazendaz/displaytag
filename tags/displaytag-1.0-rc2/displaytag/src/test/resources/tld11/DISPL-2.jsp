<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="../../../src/tld/displaytag-12.tld" prefix="display"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
<title>Displaytag unit test</title>
</head>
<body>
<%
  java.util.List testData = new java.util.ArrayList();
  testData.add(new org.displaytag.test.KnownValue());
  request.setAttribute( "test", testData);
%>

<display:table id="table" name="test" sort="list">
    <display:column property="ant" />
    <display:column><%=((org.displaytag.test.KnownValue)pageContext.getAttribute("table")).getBee()%></display:column>
    <display:column><%=((org.displaytag.test.KnownValue)table).getCamel()%></display:column>
</display:table>

<display:table id='<%=new String("table2")%>' name="test" sort="list">
    <display:column property="ant" />
    <display:column><%=((org.displaytag.test.KnownValue)pageContext.getAttribute("table2")).getBee()%></display:column>
</display:table>

<display:table id="1table" name="test" sort="list">
    <display:column property="ant" />
    <display:column><%=((org.displaytag.test.KnownValue)pageContext.getAttribute("1table")).getBee()%></display:column>
</display:table>

<display:table id="try" name="test" sort="list">
    <display:column property="ant" />
    <display:column><%=((org.displaytag.test.KnownValue)pageContext.getAttribute("try")).getBee()%></display:column>
</display:table>

</body>
</html>
