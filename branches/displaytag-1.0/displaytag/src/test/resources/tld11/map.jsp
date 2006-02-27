<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" 
    xmlns:display="urn:jsptld:../../../src/tld/displaytag-12.tld">
    <jsp:text> <![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ]]> </jsp:text>
    <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
        <head>
            <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
            <title>Displaytag unit test</title>
        </head>
        <body>
            <jsp:scriptlet> <![CDATA[
                java.util.Map map1 = new java.util.TreeMap();
                map1.put(org.displaytag.test.KnownValue.ANT,org.displaytag.test.KnownValue.ANT);
                map1.put(org.displaytag.test.KnownValue.BEE,org.displaytag.test.KnownValue.BEE);
                map1.put(org.displaytag.test.KnownValue.CAMEL,org.displaytag.test.KnownValue.CAMEL);
                
                Object[] testData= new Object[]{map1, map1};
                request.setAttribute( "test", testData);
            ]]> </jsp:scriptlet>
            <display:table name="requestScope.test" id="table">
                <display:column property="ant"/>
                <display:column property="bee"/>
                <display:column property="camel" title="Camel" />
            </display:table>
        </body>
    </html>
</jsp:root>