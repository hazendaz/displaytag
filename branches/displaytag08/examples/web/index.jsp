<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<jsp:include page="header.jsp" flush="true" />

<h1>Examples</h1>

The following examples all show the functionality of the table/column tags.  These
example pages also allow you to view the JSP source, so you can see how you
might interface with the tag in your own application.<p>

<ul>
<li><a href="example-nocolumns.jsp">Simplest case, no columns</a>
<li><a href="example-columns.jsp">Basic, columns</a>
<li><a href="example-styles.jsp">Basic, columns - different styles</a>
<li><a href="example-datasource.jsp">Basic, acquiring your List of data</a>
<li><a href="example-imp-objects.jsp">Basic, implicit objects created by table</a>
<li><a href="example-subsets.jsp">Basic, showing subsets of data from the List</a>
<li><a href="example-autolink.jsp">Standard, smart linking of column data</a>
<li><a href="example-decorator.jsp">Standard, using decorators to transform/process data</a>
<li><a href="example-decorator-link.jsp">Standard, creating dynamic links</a>
<li><a href="example-paging.jsp">Wow, auto-paging of long lists</a>
<li><a href="example-sorting.jsp">Wow, auto-sorting by columns</a>
<li><a href="example-grouping.jsp">Wow, column grouping</a>
<li><a href="example-callbacks.jsp">Wow, using callbacks to show totals</a>
<li><a href="example-export.jsp">Wow, data exporting (excel, csv, xml)</a>
<li><a href="example-config.jsp">Config, overriding default behaviors/messages</a>
<li><a href="example-misc.jsp">Misc, odds and ends</a>

<!--
<li><a href="example-filtering.jsp">Wow, filtering by columns</a> (coming...)
<li><a href="example-resultset.jsp">By Demand, working with jdbc results</a> (coming...)
<li><a href="example-column-chooser.jsp">Wow, user choice over which columns get displayed</a> (coming...)
-->

</ul>
<p>

<jsp:include page="footer.jsp" flush="true" />