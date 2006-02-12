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
          org.displaytag.pagination.SimplePaginatedList testData = new org.displaytag.pagination.SimplePaginatedList(2,2);
          request.setAttribute( "test", testData);
      ]]> </jsp:scriptlet>
      <display:table name="test" uid="table" sort="list">
        <display:column property="number" sortable="true"/>
        <display:setProperty name="paging.banner.some_items_found">
           <div id="pagebanner">{0}|{2}|{3}</div>
        </display:setProperty>
        <display:setProperty name="paging.banner.full">
           <div id="pagelinks">{0}</div>
        </display:setProperty>
        <display:setProperty name="paging.banner.page.selected" value="[{0}]"/>
        <display:setProperty name="paging.banner.page.link" value="{0}"/>
        <display:setProperty name="paging.banner.page.separator" value="|"/>
      </display:table>
    </body>
  </html>
</jsp:root>