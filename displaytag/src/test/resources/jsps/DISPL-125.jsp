<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:text>
    <![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ]]>
  </jsp:text>
  <jsp:directive.page contentType="text/html; charset=UTF8" />
  <html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
      <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
      <title>Displaytag unit test</title>
    </head>
    <body>
      <jsp:scriptlet>
        <![CDATA[
          java.util.List<org.displaytag.test.KnownValue> testData = new java.util.ArrayList<org.displaytag.test.KnownValue>();
          testData.add(new org.displaytag.test.KnownValue());
          testData.add(new org.displaytag.test.KnownValue());
          testData.add(new org.displaytag.test.KnownValue());
          testData.add(new org.displaytag.test.KnownValue());
          testData.add(new org.displaytag.test.KnownValue());
          request.setAttribute( "test", testData);
      ]]>
      </jsp:scriptlet>
      <display:table name="requestScope.test" id="table" pagesize="2" keepStatus="true" clearStatus="false">
        <display:column title="empty" sortable="true">
          <jsp:expression>((org.displaytag.test.KnownValue)pageContext.getAttribute("table")).getAnt()</jsp:expression>
        </display:column>
        <display:setProperty name="paging.banner.group_size" value="3" />
        <display:setProperty name="paging.banner.full">
            <div id="pagination">{0}</div>
        </display:setProperty>
        <display:setProperty name="paging.banner.first">
            <div id="pagination">{0}</div>
        </display:setProperty>
        <display:setProperty name="paging.banner.last">
            <div id="pagination">{0}</div>
        </display:setProperty>
        <display:setProperty name="paging.banner.page.selected" value="[{0}]" />
        <display:setProperty name="paging.banner.page.link" value="{0}" />
      </display:table>
    </body>
  </html>
</jsp:root>
