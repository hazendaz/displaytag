<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net/el">
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
                org.displaytag.decorator.ModelDecorator decorator = new org.displaytag.decorator.ModelDecorator();
                pageContext.setAttribute("htmlModelDecorator",   decorator);
                pageContext.setAttribute("wexcelModelDecorator", decorator);
                pageContext.setAttribute("wpdfModelDecorator",   decorator);
                pageContext.setAttribute("rtfModelDecorator",    decorator);
            ]]> </jsp:scriptlet>
     <display:table name="requestScope.test" id="table" pagesize="1">
         <display:setProperty name="decorator.media.html"   value="htmlModelDecorator" />
         <display:setProperty name="decorator.media.wexcel" value="wexcelModelDecorator" />
         <display:setProperty name="decorator.media.wpdf"   value="wpdfModelDecorator" />
         <display:setProperty name="decorator.media.rtf"    value="rtfModelDecorator" />
       <display:column property="decoratedValue"/>
     </display:table>
        </body>
    </html>
</jsp:root>