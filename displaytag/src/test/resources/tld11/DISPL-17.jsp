<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" 
	xmlns:display="urn:jsptld:../../../src/tld/displaytag-12.tld">
	<jsp:text>
		<![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
	<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
	<title>Displaytag unit test</title>
	</head>
	<body>
	<jsp:scriptlet> <![CDATA[
            	 session.setAttribute( "stest", new org.displaytag.sample.TestList( 10, false ) );
            ]]> </jsp:scriptlet>
	<display:table id="wontWork" name="sessionScope.stest" defaultsort="1">
		<display:column headerClass="wontSort" property="id" title="ID" sortable="true" />
		<display:column headerClass="wontSort" property="name" sortable="true" />
		<display:column headerClass="wontSort" property="email" sortable="true" />
		<display:column headerClass="wontSort" property="status" sortable="true" />
	</display:table>
	</body>
	</html>
</jsp:root>