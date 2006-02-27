<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
  <html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
      <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
      <title>Displaytag unit test</title>
    </head>
    <body>
      <%
        Object[] testData = new Object[]{new org.displaytag.test.KnownValue()};
        pageContext.setAttribute( "test", testData);
        Integer intvalue = new Integer(1);
      %>

      <display:table name="pageScope.test">
        <display:column property="ant"/>
        <display:column value="<%=intvalue%>"/>
        <display:column value="<%=intvalue%>" format="{0,number,0} $"/>
      </display:table>
    </body>
  </html>