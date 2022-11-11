<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:text>
    <![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ]]>
  </jsp:text>
  <jsp:directive.page contentType="text/html; charset=UTF8"/>
  <html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
      <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
      <title>DISPL-505</title>
    </head>
    <body>
      <jsp:scriptlet>
        <![CDATA[
                java.util.List<org.displaytag.test.KnownTypes> testData = new java.util.ArrayList<org.displaytag.test.KnownTypes>();
                testData.add(new org.displaytag.test.KnownTypes());
                request.setAttribute( "test", testData);
        ]]>
      </jsp:scriptlet>
      <display:table items="requestScope.test" id="table">
        <display:column property="time" decorator="org.displaytag.decorator.DateColumnDecorator"/>
        <display:column property="long"/>
        <display:setProperty name="export.decorated" value="false"/>
      </display:table>
      <display:table items="requestScope.test" id="table2">
        <display:setProperty name="factory.decorator" value="org.displaytag.decorator.NoDecorationDecoratorFactory"/>
        <display:column property="time" decorator="org.displaytag.decorator.DateColumnDecorator"/>
        <display:column property="long"/>
        <display:setProperty name="export.decorated" value="false"/>
      </display:table>
    </body>
  </html>
</jsp:root>
