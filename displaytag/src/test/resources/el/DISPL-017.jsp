<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:display="urn:jsptld:http://displaytag.sf.net/el">
	<jsp:text>
		<![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ]]>
	</jsp:text>
    <jsp:directive.page contentType="text/html; charset=UTF8"/>
	<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>Displaytag unit test</title>
	</head>
	<body>
	<jsp:scriptlet> <![CDATA[
				java.util.List test = new java.util.ArrayList(2);
				test.add(new org.displaytag.test.KnownValue());
				test.add(new org.displaytag.test.KnownValue());
            	session.setAttribute( "stest", test );
            ]]> </jsp:scriptlet>
	<display:table id="wontWork" name="${sessionScope.stest}" defaultsort="1">
		<display:column headerClass="wontSort" property="ant" title="ID" sortable="true" />
		<display:column headerClass="wontSort" property="bee" sortable="true" />
		<display:column headerClass="wontSort" property="camel" sortable="true" />
		<display:column headerClass="wontSort" property="ant" sortable="true" />
	</display:table>
	</body>
	</html>
</jsp:root>
