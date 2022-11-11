<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:text> <![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ]]>
  </jsp:text>
  <jsp:directive.page contentType="text/html; charset=UTF8"/>
  <html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
      <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
      <title>Displaytag unit test</title>
    </head>
    <body>
      <jsp:scriptlet> <![CDATA[
                java.util.List<org.displaytag.test.KnownValueWithId> testData = new java.util.ArrayList<org.displaytag.test.KnownValueWithId>();
                testData.add(new org.displaytag.test.KnownValueWithId("A"));
                testData.add(new org.displaytag.test.KnownValueWithId("B"));
                testData.add(new org.displaytag.test.KnownValueWithId("C"));
                testData.add(new org.displaytag.test.KnownValueWithId("D"));
                request.setAttribute("test", testData);
            ]]>
      </jsp:scriptlet>
      <form name="testform" action="${pageContext.request.requestURI}">
        <display:table items="${requestScope.test}" id="table" pagesize="2" form="testform">
          <display:column property="id"/>
          <display:column>foo</display:column>
          <display:setProperty name="paging.banner.placement" value="bottom" />
        </display:table>
      </form>
    </body>
  </html>
</jsp:root>
