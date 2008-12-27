<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:display="urn:jsptld:http://displaytag.sf.net/el">
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
                java.util.List testData = new java.util.ArrayList();
                testData.add(new org.displaytag.test.KnownValue());
                testData.add(new org.displaytag.test.KnownValue());
                request.setAttribute( "test", testData);
                request.setAttribute( "resultSize", new Integer(4));
            ]]>
      </jsp:scriptlet>
      <display:table name="${requestScope.test}" id="table" pagesize="2" partialList="true" size="resultSize">
        <display:column property="ant" sortable="true" />
        <display:column property="bee" sortable="true" sortName="buzz" />
        <display:setProperty name="paging.banner.some_items_found">
          <div id="pagebanner">{0}|{2}|{3}</div>
        </display:setProperty>
        <display:setProperty name="paging.banner.full">
          <div id="pagelinks">{0}</div>
        </display:setProperty>
        <display:setProperty name="paging.banner.page.selected" value="[{0}]" />
        <display:setProperty name="paging.banner.page.link" value="{0}" />
        <display:setProperty name="paging.banner.page.separator" value="|" />
      </display:table>
    </body>
  </html>
</jsp:root>