<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:display="urn:jsptld:http://displaytag.sf.net/el">
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
          request.setAttribute( "test", new Object[]{new org.displaytag.test.KnownTypes()});
      ]]> </jsp:scriptlet>
      <display:table name="requestScope.test" uid="table">
        <display:column property="time" decorator="org.displaytag.decorator.DateColumnDecorator org.displaytag.decorator.PrefixColumnDecorator" />
      </display:table>
    </body>
  </html>
</jsp:root>