<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:display="urn:jsptld:http://displaytag.sf.net">
    <jsp:text> <![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ]]>
    </jsp:text>
    <jsp:directive.page contentType="text/html; charset=UTF8"/>
    <html xmlns="http://www.w3.org/1999/xhtml" lang="en">
        <head>
            <meta http-equiv="content-type"
                content="text/html; charset=UTF-8" />
            <title>Displaytag unit test</title>
        </head>
        <body>
            <jsp:scriptlet> <![CDATA[
                java.util.List<String> list = new java.util.ArrayList<String>(3);
                list.add("string1");
                list.add("string2");
                list.add("string3");
                request.setAttribute( "test", list );
            ]]>
            </jsp:scriptlet>
            <display:table name="requestScope.test" id="table" />
        </body>
    </html>
</jsp:root>
