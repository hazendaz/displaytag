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
                testData.add(new org.displaytag.test.KnownValueWithId("1"));
                testData.add(new org.displaytag.test.KnownValueWithId("1"));
                testData.add(new org.displaytag.test.KnownValueWithId("2"));
                testData.add(new org.displaytag.test.KnownValueWithId("2"));
                testData.add(new org.displaytag.test.KnownValueWithId("2"));
                testData.add(new org.displaytag.test.KnownValueWithId("3"));
                testData.add(new org.displaytag.test.KnownValueWithId("3"));
                testData.add(new org.displaytag.test.KnownValueWithId("3"));
                testData.add(new org.displaytag.test.KnownValueWithId("3"));
                testData.add(new org.displaytag.test.KnownValueWithId("4"));
                request.setAttribute( "test", testData);
            ]]> </jsp:scriptlet>
    <display:table name="requestScope.test" id="table" pagesize="5" decorator="org.displaytag.decorator.MultilevelTotalTableDecorator">
        <display:column property="id" group="1"/>
        <display:column property="two" total="true"/>
        <display:column property="ant"/>
    </display:table>
    </body>
    </html>
</jsp:root>
