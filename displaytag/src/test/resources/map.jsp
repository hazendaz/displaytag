<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="../../target/tld/displaytag-11.tld" prefix="display" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
	<title>Displaytag unit test</title>
</head>

<body>
  <%    java.util.Map map1 = new java.util.TreeMap();
        map1.put(org.displaytag.test.KnownValue.ANT,org.displaytag.test.KnownValue.ANT);
        map1.put(org.displaytag.test.KnownValue.BEE,org.displaytag.test.KnownValue.BEE);
        map1.put(org.displaytag.test.KnownValue.CAMEL,org.displaytag.test.KnownValue.CAMEL);
        
        Object[] testData= new Object[]{map1, map1};
        request.setAttribute( "test", testData);
  %>
  
  <display:table name="requestScope.test" id="table">
    <display:column property="ant"/>
    <display:column property="bee"/>
    <display:column property="camel"/>
  </display:table>

</body>
</html>
