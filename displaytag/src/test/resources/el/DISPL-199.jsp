<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:display="urn:jsptld:http://displaytag.sf.net/el"
  xmlns:c="urn:jsptld:http://java.sun.com/jstl/core">
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
          testData.add(new org.displaytag.test.NumberedItem(1));
          testData.add(new org.displaytag.test.NumberedItem(2));
          testData.add(new org.displaytag.test.NumberedItem(4));
          request.setAttribute( "test", testData);
      ]]> </jsp:scriptlet>
      <display:table name="requestScope.test" varTotals="totals" decorator="org.displaytag.decorator.TotalTableDecorator">
        <display:column property="number" total="true"/>
      </display:table>
      <div id="divtotal"><c:out value="${totals.column1}" /></div>
    </body>
  </html>
</jsp:root>