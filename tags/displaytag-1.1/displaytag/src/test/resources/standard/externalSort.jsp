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
                java.util.List testData = new java.util.ArrayList();
                testData.add(new org.displaytag.test.KnownValue());
                testData.add(new org.displaytag.test.KnownValue());
                testData.add(new org.displaytag.test.KnownValue());
                request.setAttribute( "test", testData);
                request.setAttribute("test2", new org.displaytag.test.ShuffledNumberedList());
            ]]> </jsp:scriptlet>
            <display:table name="requestScope.test" id="table" sort="external">
                <display:column property="ant" sortable="true"/>
                <display:column property="bee" sortable="true" sortName="buzz"/>
            </display:table>
            <display:table name="requestScope.test2" id="table2" sort="external">
                <display:column property="number" sortable="true" sortName="number"/>
            </display:table>
        </body>
    </html>
</jsp:root>