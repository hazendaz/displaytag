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
                testData.add(new org.displaytag.test.KnownValue());
                request.setAttribute( "test", testData);
            ]]> </jsp:scriptlet>
            <display:table name="requestScope.test" id="table">
                <display:column><jsp:expression>table_rowNum</jsp:expression></display:column>
                <display:column><jsp:expression>((org.displaytag.test.KnownValue)table).getAnt()</jsp:expression></display:column>
                <display:column property="bee" />
                <display:column>
          <display:table name="requestScope.test" id="table2">
                    <display:column><jsp:expression>table2_rowNum</jsp:expression></display:column>
                    <display:column><jsp:expression>((org.displaytag.test.KnownValue)table2).getAnt()</jsp:expression></display:column>
                    <display:column property="camel" />
          </display:table>
        </display:column>
      </display:table>
        </body>
    </html>
</jsp:root>
