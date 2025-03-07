<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:c="urn:jsptld:http://java.sun.com/jstl/core"
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
                java.util.List<org.displaytag.test.KnownTypes> list = new java.util.ArrayList<org.displaytag.test.KnownTypes>();
                list.add(new org.displaytag.test.KnownTypes());
                list.add(new org.displaytag.test.KnownTypes());
                request.setAttribute( "test", list);
            ]]> </jsp:scriptlet>
      <display:table name="requestScope.test" varTotals="totals">
        <display:column property="time" />
        <display:column property="long" total="true"/>
        <display:column property="long" total="true"/>
        <display:footer>
                        <tr>
                            <td>${totals.column1}</td>
                            <td>${totals.column2}</td>
                            <td>${totals.column3}</td>
                        </tr>
        </display:footer>
      </display:table>
    </body>
  </html>
</jsp:root>
