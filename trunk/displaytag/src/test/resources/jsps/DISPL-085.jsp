<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:display="urn:jsptld:http://displaytag.sf.net" xmlns:c="urn:jsptld:http://java.sun.com/jstl/core">
  <jsp:directive.page isELIgnored="false" />
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
          request.setAttribute( "test", new Object[]{new org.displaytag.test.KnownValue()});

          // prepare column collection
          java.util.Map antColumn = new java.util.HashMap();
          antColumn.put("property", "ant");
          antColumn.put("title", "ant title");
          antColumn.put("sortable", Boolean.TRUE);

          java.util.Map beeColumn = new java.util.HashMap();
          beeColumn.put("property", "bee");
          beeColumn.put("title", "bee title");

          java.util.List columnlist = new java.util.ArrayList();
          columnlist.add(antColumn);
          columnlist.add(beeColumn);

          // note that for this test EL is required.
          // we are using the embedded EL support in jsp 2.0 (see the isELIgnored="false" page directive), so this
          // test will only work in jsp 2.0 containers

      </jsp:scriptlet>
      <display:table name="requestScope.test" uid="table">
          <jsp:scriptlet>
            java.util.Iterator it = columnlist.iterator();
            while (it.hasNext())
            {
                pageContext.setAttribute("cl", it.next());
                </jsp:scriptlet>
                  <display:column property="${cl.property}" title="${cl.title}" sortable="${cl.sortable}" />
                <jsp:scriptlet>
            }
          </jsp:scriptlet>
      </display:table>
    </body>
  </html>
</jsp:root>