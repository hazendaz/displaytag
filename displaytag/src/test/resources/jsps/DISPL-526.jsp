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
  java.util.List<java.util.Map<String, Integer>> list = new java.util.ArrayList<java.util.Map<String, Integer>>();
  java.util.Map<String, Integer> map1 = new java.util.HashMap<String, Integer>();
  map1.put("Entry1", 1); //The value, 1, is the same as the one below.
  map1.put("Entry2", 2);
  list.add(map1);
  java.util.Map<String, Integer> map2 = new java.util.HashMap<String, Integer>();
  map2.put("Entry1", 1); //The value, 1, will not show up in HTML, as it is the same as the one directly above. 
                                            // If it is change to another value, like 2, it will show up.
  map2.put("Entry2", 5);
  list.add(map2);
  request.setAttribute("testList", list);
            ]]>
      </jsp:scriptlet>
      <display:table name="testList" />
    </body>
  </html>
</jsp:root>
