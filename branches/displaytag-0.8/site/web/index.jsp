<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<jsp:include page="header.jsp" flush="true" />

<h1>Overview</h1>


<b><font color="#cc0000">What's New:</font></b> Version <b>0.8.5</b> has been released
(03/23/03).  This release is primarily a bug-fixing release and also represents
the first release from the <a href="http://sf.net/projects/displaytag">project</a>
at SourceForge.  If you would like to have a say in the future direction of this
project, please join one of the <a href="http://sourceforge.net/mail/?group_id=73068">
mailing lists</a>.  A complete changelog is available 
<a href="/display-docs-@version@/changelog.jsp">here</a>, and click 
<a href="download.jsp">here to download</a> the latest source or
standalone jar file.<p>

<hr width="50%" noshade size="1"><p>

This display tag library hopes to be an open source suite of custom tags that
provide high level web presentation patterns (like tables, tabs, templates,
inspectors, trees, etc...) which will work in a MVC model, and provide a
significant amount of functionality while still being simple and straight-forward
to use.<p>

For now, my focus has been on the table/column tags that are used to show data
extracted from a List in a simple and consistant way.  Over time, I hope to flush
out the library with the following UI elements.  Basically I would like this library
to provide simple to use tools that allow me to quickly implement almost any
standard Web interface pattern.<p>

<ul>
<li><b>table</b> - simple two dimensional table based on a List of objects,
provide functionality to page long lists, sort by column, export to various formats,
and reporting features like drill down links, grouping, totalling, etc...<p>

<li><b>inspector</b> - given a data object, hook it up to an "inspector" which will
allow you to view selected attributes of that object, or change attributes based
on standard Bean-style descriptors, etc...<p>

<li><b>template</b> - simple templating, provide simple hooks for search/replace of
tokens, and provide a way to hook into other actions or GUI elements.<p>

<li><b>treeview</b> - display a heirarchy of data in a familar tree view with hooks to
various actions when the user clicks on nodes and branches.<p>

<li><b>tabpanel</b> - familar panel metaphor, hook into various templates and run various
actions when the user clicks on the tabs to move between panels.
</uL>


I have a number of <a href="/display-examples-@version@/">example pages</a> which show the
functionality of tags that have been completed (table/column).  These example
pages also allow you to view the JSP source, so you can see how you might
interface with the tag in your own application. Below are the table/column tag
examples:<p>

<ul>
<li><a href="/display-examples-@version@/example-nocolumns.jsp">Simplest case, no columns</a>
<li><a href="/display-examples-@version@/example-columns.jsp">Basic, columns</a>
<li><a href="/display-examples-@version@/example-styles.jsp">Basic, columns - different styles</a>
<li><a href="/display-examples-@version@/example-datasource.jsp">Basic, acquiring your List of data</a>
<li><a href="/display-examples-@version@/example-imp-objects.jsp">Basic, implicit objects created by table</a>
<li><a href="/display-examples-@version@/example-subsets.jsp">Basic, showing subsets of data from the List</a>
<li><a href="/display-examples-@version@/example-autolink.jsp">Standard, smart linking of column data</a>
<li><a href="/display-examples-@version@/example-decorator.jsp">Standard, using decorators to transform/process data</a>
<li><a href="/display-examples-@version@/example-decorator-link.jsp">Standard, creating dynamic links</a>
<li><a href="/display-examples-@version@/example-paging.jsp">Wow, auto-paging of long lists</a>
<li><a href="/display-examples-@version@/example-sorting.jsp">Wow, auto-sorting by columns</a>
<li><a href="/display-examples-@version@/example-grouping.jsp">Wow, column grouping</a>
<li><a href="/display-examples-@version@/example-callbacks.jsp">Wow, using callbacks to show totals</a>
<li><a href="/display-examples-@version@/example-export.jsp">Wow, data exporting (excel, csv, xml)</a>
<li><a href="/display-examples-@version@/example-config.jsp">Config, overriding default behaviors/messages</a>
<li><a href="/display-examples-@version@/example-misc.jsp">Misc, odds and ends</a>

<!--
<li><a href="/display-examples-@version@/example-filtering.jsp">Wow, filtering by columns</a> (coming...)
<li><a href="/display-examples-@version@/example-resultset.jsp">By Demand, working with jdbc results</a> (coming...)
<li><a href="/display-examples-@version@/example-column-chooser.jsp">Wow, user choice over which columns get displayed</a> (coming...)
-->
</ul>
<p>


The following additional documentation is provided that should quickly provide
the information you need to use the display taglib in your own applications.<p>

<ul>

<!-- <li><a href="/display-docs-@version@/intro.html">User Guide - a gentle introduction to the tags...</a> -->
<li><a href="/display-docs-@version@/install.jsp">How to install</a>
<li><a href="/display-docs-@version@/build.jsp">Building from source</a>
<li><a href="/display-docs-@version@/changelog.jsp">Change history</a>
<li><a href="/display-docs-@version@/todo.jsp">Todo list</a>
<!-- <li><a href="/display-docs-@version@/ref.html">Taglib quick reference</a>-->
<li><a href="/display-docs-@version@/javadoc/">Javadoc API documentation</a>
<!-- <li><a href="/display-docs-@version@/code-hints.html">Hints on extending the tags, anchor points, what to override, etc...</a> -->
</ul>

Please send any comments, suggestions, bug reports, etc... to:<p>

The SourceForge Project Team<br />
<a href="mailto:displaytag-user@lists.sourceforge.net">displaytag-user@lists.sourceforge.net</a><br />
<a href="http://sf.net/projects/displaytag">http://sf.net/projects/displaytag</a>
<br /><br />
<a href="http://sourceforge.net" title="This Logo is designed to track statistics"> 
<img src="http://sourceforge.net/sflogo.php?group_id=73068&amp;type=5" width="210" height="62" border="0" alt="SourceForge.net Logo"></a>
<br /><br />
</p>
<jsp:include page="footer.jsp" flush="true" />