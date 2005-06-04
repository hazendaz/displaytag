<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="urn:jsptld:http://java.sun.com/jstl/core">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:text>
    <![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">]]>
  </jsp:text>

  <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
  <title>The &lt;display:*&gt; tag library</title>
  <meta http-equiv="Expires" content="-1" />
  <meta http-equiv="Pragma" content="no-cache" />
  <meta http-equiv="Cache-Control" content="no-cache" />
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" href="css/screen.css" type="text/css" media="screen, print" />
  </head>

  <body>

  <div id="header">
  <h1>The &amp;lt;display:*> tag library <small>1.1</small></h1>

  <ul>
    <li><a href="http://displaytag.sf.net">Documentation</a></li>
    <li><a href="index.jsp">Examples</a></li>
  </ul>

  </div>

  <jsp:scriptlet>if (request.getRequestURI().indexOf("example-") != -1) {</jsp:scriptlet>
  <ul id="showsource">
    <li>

    <![CDATA[<a href="]]><c:out value="${request.requestURI}" /><![CDATA[.source">View JSP Source</a>]]>

    </li>
  </ul>
  <jsp:scriptlet>}</jsp:scriptlet>

  <jsp:text>
    <![CDATA[<div id="body">]]>
  </jsp:text>
</jsp:root>
