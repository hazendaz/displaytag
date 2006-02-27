<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="../../../src/tld/displaytag-12.tld" prefix="display"%>
  <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
      <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
      <title>Displaytag unit test</title>
    </head>
    <body>
      <%
          java.util.List testData = new java.util.ArrayList();
          testData.add(new org.displaytag.test.KnownValue());
          request.setAttribute( "test", testData);
      %>
      
      <%
      for (int j = 0; j<2; j++)
      {
      %>
      <display:table name="requestScope.test">
        <display:column property="ant" />
      </display:table>
      <%
      }
      for (int j = 0; j<2; j++)
      {
      %>
      <display:table list="<%=testData%>">
        <display:column property="ant" />
      </display:table>
      <%
      }
      %>
      <display:table list="<%=testData%>">
        <display:column property="ant" />
      </display:table>
      <display:table list="<%=testData%>">
        <display:column property="ant" />
      </display:table>
    </body>
  </html>