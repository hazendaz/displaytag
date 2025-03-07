<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:display="urn:jsptld:http://displaytag.sf.net">
    <jsp:text> <![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ]]> </jsp:text>
    <jsp:directive.page contentType="text/html; charset=UTF8"/>
    <html xmlns="http://www.w3.org/1999/xhtml" lang="en">
        <head>
            <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
            <title>Displaytag unit test</title>
        </head>
        <body>
            <jsp:scriptlet> <![CDATA[
                java.util.List<org.displaytag.test.KnownValue> testData = new java.util.ArrayList<org.displaytag.test.KnownValue>();
                testData.add(new org.displaytag.test.KnownValue());
                testData.add(new org.displaytag.test.KnownValue());
                request.setAttribute( "test", testData);
                response.addHeader("dummy", "dummy");
                response.addHeader("Cache-Control", "private");
                response.addHeader("Expires", "Thu, 01 Dec 1999 16:00:00 GMT");
                response.addHeader("Pragma", "no-cache");
            ]]> </jsp:scriptlet>

                    <display:table name="requestScope.test" id="table" export="true">
                        <display:column property="ant" media="html" />
                        <display:column property="bee" media="xml" />
                        <display:column property="camel" title="Camel" media="xml html" />
                    </display:table>
                </body>
    </html>
</jsp:root>
