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
      <jsp:scriptlet>
      java.util.List listA = new java.util.ArrayList();
      listA.add("a");
      java.util.List listB = new java.util.ArrayList();
      listB.add("b");
      java.util.List listC = new java.util.ArrayList();
      listC.add("c");

      Object[] testData = new Object[]{listC, listA, listB};
          request.setAttribute( "test", testData);
      </jsp:scriptlet>
      <display:table name="test" decorator="org.displaytag.decorator.ListIndexTableDecorator" defaultsort="1">
        <display:column property="[0]"/>
        <display:column property="viewIndex"/>
        <display:column property="listIndex"/>
      </display:table>
    </body>
  </html>
</jsp:root>