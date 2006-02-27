<%@ include file="inc/header.jsp" %>
<h2>Examples</h2>

<p>
	The following examples all show the functionality of the table/column tags.  These
	example pages also allow you to view the JSP source, so you can see how you
	might interface with the tag in your own application.
</p>

<ul>
	<li><a href="example-nocolumns.jsp">Simplest case, no columns</a></li>
	<li><a href="example-columns.jsp">Basic, columns</a></li>
	<li><a href="example-styles.jsp">Basic, columns - different styles</a></li>
	<li><a href="example-datasource.jsp">Basic, acquiring your List of data (<strong>new</strong>: expressions!)</a></li>
	<li><a href="example-imp-objects.jsp"><strong>New</strong>, implicit objects created by table</a></li>
	<li><a href="example-subsets.jsp">Basic, showing subsets of data from the List</a></li>
	<li><a href="example-autolink.jsp">Standard, smart linking of column data</a></li>
	<li><a href="example-decorator.jsp">Standard, using decorators to transform/process data</a></li>
	<li><a href="example-decorator-link.jsp">Standard, creating dynamic links</a></li>
	<li><a href="example-paging.jsp">Wow, auto-paging of long lists</a></li>
	<li><a href="example-sorting.jsp">Wow, auto-sorting by columns</a></li>
	<li><a href="example-grouping.jsp">Wow, column grouping</a></li>
	<li><a href="example-callbacks.jsp">Wow, using callbacks to show totals</a></li>
	<li><a href="example-export.jsp">Wow, data exporting (excel, csv, xml)</a></li>
	<li><a href="example-config.jsp">Config, overriding default behaviors/messages</a></li>
	<li><a href="example-misc.jsp">Misc, odds and ends</a></li>	
	<li><a href="example-pse.jsp"><strong>New</strong>, paging + sorting + grouping + exporting working together</a></li>
	<li><a href="example-twotables.jsp"><strong>New</strong>, 2 or more table working in the same page with indipendent sorting - paging</a></li>
	<li><a href="example-nestedtables.jsp"><strong>New</strong>, nested tables</a></li>
	
	
	<!--
	<li><a href="example-filtering.jsp">Wow, filtering by columns</a> (coming...)
	<li><a href="example-resultset.jsp">By Demand, working with jdbc results</a> (coming...)
	<li><a href="example-column-chooser.jsp">Wow, user choice over which columns get displayed</a> (coming...)
	-->
</ul>


<%@ include file="inc/footer.jsp" %>