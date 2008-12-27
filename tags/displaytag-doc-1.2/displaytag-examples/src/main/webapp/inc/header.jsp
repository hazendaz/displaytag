<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="urn:jsptld:http://java.sun.com/jstl/core">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:text>
    <![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">]]>
  </jsp:text>
  <jsp:text>
    <![CDATA[<html xmlns="http://www.w3.org/1999/xhtml" lang="en">]]>
  </jsp:text>
  <head>
    <title>
      The
      <![CDATA[&lt;display:*>]]>
      tag library
    </title>
    <meta http-equiv="Expires" content="-1" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <style type="text/css" media="all">
      @import url("css/maven-base.css"); @import url("css/maven-theme.css"); @import url("css/site.css"); @import
      url("css/screen.css");
    </style>
    <link rel="stylesheet" href="./css/print.css" type="text/css" media="print" />
  </head>
  <jsp:text>
    <![CDATA[<body class="composite">]]>
  </jsp:text>
  <div id="banner">
    <a href="index.jsp" id="bannerRight">
      <img src="img/displaytag.png" alt="" />
    </a>
    <div class="clear">
      <hr />
    </div>
  </div>
  <div id="breadcrumbs">
    <div class="xright">
      <a href="http://displaytag.sf.net">Documentation</a>
      |
      <a href="index.jsp">Examples</a>
    </div>
    <div class="clear">
      <hr />
    </div>
  </div>
  <div id="leftColumn">
    <div id="navcolumn">
      <h5>Examples</h5>
      <ul>
        <li>
          <a href="example-nocolumns.jsp">Simplest case, no columns</a>
        </li>
        <li>
          <a href="example-columns.jsp">Columns</a>
        </li>
        <li>
          <a href="example-styles.jsp">Styles</a>
        </li>
        <li>
          <a href="example-datasource.jsp">Acquiring your List of data</a>
        </li>
        <li>
          <a href="example-imp-objects.jsp">Implicit objects</a>
        </li>
        <li>
          <a href="example-subsets.jsp">Showing subsets of data</a>
        </li>
        <li>
          <a href="example-autolink.jsp">Smart linking</a>
        </li>
        <li>
          <a href="example-decorator.jsp">Using decorators</a>
        </li>
        <li>
          <a href="example-decorator-link.jsp">Creating dynamic links</a>
        </li>
        <li>
          <a href="example-paging.jsp">Auto-paging of long lists</a>
        </li>
        <li>
          <a href="example-sorting.jsp">Auto-sorting by columns</a>
        </li>
        <li>
          <a href="example-grouping.jsp">Column grouping</a>
        </li>
        <li>
          <a href="example-callbacks.jsp">Using callbacks for totals</a>
        </li>
        <li>
          <a href="example-export.jsp">Data exporting</a>
        </li>
        <li>
          <a href="example-config.jsp">Config, overriding default</a>
        </li>
        <li>
          <a href="example-pse.jsp">All the features together</a>
        </li>
        <li>
          <a href="example-twotables.jsp">More tables on one page</a>
        </li>
        <li>
          <a href="example-nestedtables.jsp">Nested tables</a>
        </li>
        <li>
          <a href="example-caption-footer.jsp">Caption and footer</a>
        </li>
        <li>
          <a href="example-misc.jsp">Misc, odds and ends</a>
        </li>
        <li>
          <a href="example-new-export.jsp">WYSIWYG data exporting</a>
        </li>
        <li>
          <a href="example-format.jsp">Using format</a>
        </li>
        <li>
          <a href="example-columnlist.jsp">Dynamic Column Creation</a>
        </li>
        <li>
          <a href="example-rowclass.jsp">Row highlighting</a>
        </li>
        <li>
          <a href="example-columnsummation.jsp">Column Summation</a>
        </li>
        <li>
          <a href="example-keepstatus.jsp">Preserve pagination status</a>
        </li>
        <li>
          <a href="example-checkboxes.jsp">Posting forms (checkboxes)</a>
        </li>
      </ul>
      <a href="http://validator.w3.org/check?uri=referer" id="poweredBy">
        <img src="img/valid-xhtml10.png" alt="Valid XHTML 1.0!" height="31" width="88" style="border:none" />
      </a>
    </div>
  </div>
  <jsp:scriptlet>if (request.getRequestURI().indexOf("example-") != -1) {</jsp:scriptlet>
  <ul id="showsource">
    <li><![CDATA[<a href="]]><c:out value="${pageContext.request.requestURI}" />
      <![CDATA[.source">View source</a>]]></li>
  </ul>
  <jsp:scriptlet>}</jsp:scriptlet>
  <jsp:text>
    <![CDATA[<div id="bodyColumn">
      <div id="contentBox">
      <div class="section">]]>
  </jsp:text>
</jsp:root>
