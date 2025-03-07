<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:display="urn:jsptld:http://displaytag.sf.net"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt">
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
                            request.setAttribute( "test", testData);
            ]]> </jsp:scriptlet>
            <fmt:bundle basename="jstlbundleresources" prefix="fix.">
              <display:table name="requestScope.test" id="table">
                              <display:column property="ant" titleKey="fookey" />
                              <display:column property="bee" titleKey="bazkey" />
                              <display:column property="camel" />
                              <display:column property="bee" titleKey="missing" />
                          </display:table>
            </fmt:bundle>
        </body>
    </html>
</jsp:root>
