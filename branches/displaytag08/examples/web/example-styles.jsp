<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="org.apache.taglibs.display.test.TestList"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>

<% String wpage = "3a";
   if( request.getParameter( "page" ) != null ) {
      wpage = request.getParameter( "page" );
   }
   String css = "./site" + wpage + ".css";
%>

<jsp:include page="header.jsp" flush="true" />
<link rel=stylesheet href="<%= css %>" type="text/css">
<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Basic, columns - different styles</span></td>
<td align="right" valign="top" nowrap><a href="./example-styles.html">View JSP Source</a></td>
</tr>
</table>
<p>

<center>
<a href="example-styles.jsp?page=3a">ISIS</a>  |
<a href="example-styles.jsp?page=3b">ITS</a>  |
<a href="example-styles.jsp?page=3c">Mars</a>  |
<a href="example-styles.jsp?page=3d">Simple</a> |
<a href="example-styles.jsp?page=3e">Report</a>  |
<a href="example-styles.jsp?page=3g">Ugly</a> |
<a href="example-styles.jsp?page=3h">Mark Column</a>
</center>
<p>

<% String epage = "example" + wpage + ".jsp"; %>

<jsp:include flush="true" page="<%= epage %>" />

<p>
You actually have a lot of flexibility in how the table is displayed, but of
course you should probably stay close to the defaults in most cases.  You adjust
the look of the table via two methods, 1) pass through table and
column attributes, and 2) Style sheets which are described below.<p>

Click through the above links to see different style examples of the same
basic table.  Most of the differences in appearance between the tables below
are achieved via only stylesheet changes.<p>


<h3>Attributes</h3>
The following &lt;table&gt; attributes are also &lt;display:table&gt; attributes
and if you provide them they will be used when drawing the underlying table.<p>

<center>
<table width="90%" border="1">
<tr>
<td width="20%"><b>Attribute</b></td>
<td width="10%"><b>Default</b></td>
<td width="70%"><b>Comments</b></td>
</tr>

<tr>
<td>width</td>
<td>100%</td>
<td>width of the table</td>
</tr>

<tr>
<td>border</td>
<td>0</td>
<td>width of border to be drawn around cells</td>
</tr>

<tr>
<td>cellspacing</td>
<td>1</td>
<td>amount of space between cells</td>
</tr>

<tr>
<td>cellpadding</td>
<td>2</td>
<td>amount of space between cell border and contents</td>
</tr>

<tr>
<td>styleClass</td>
<td>table</td>
<td>style class name to apply to the table</td>
</tr>

<tr>
<td>align</td>
<td>none</td>
<td>aligns within the text flow - use stylesheets instead</td>
</tr>

<tr>
<td>background</td>
<td>none</td>
<td>background image - just don't do this</td>
</tr>

<tr>
<td>bgcolor</td>
<td>none</td>
<td>background color of table - use stylesheets instead</td>
</tr>

<tr>
<td>frame</td>
<td>none</td>
<td>how to draw borders around the table (IE only)</td>
</tr>

<tr>
<td>height</td>
<td>none</td>
<td>specifies height of the table</td>
</tr>

<tr>
<td>hspace</td>
<td>none</td>
<td>number of pixels to left/right of an aligned table</td>
</tr>

<tr>
<td>rules</td>
<td>none</td>
<td>how to draw borders within the table (IE only)</td>
</tr>

<tr>
<td>summary</td>
<td>none</td>
<td>like alt, provides a summary of the table for non-display browsers</td>
</tr>

<tr>
<td>vspace</td>
<td>none</td>
<td>number of pixels above/below an aligned table</td>
</tr>

</table>
</center><p>


Likewise, the following &lt;td&gt; attributes are also &lt;display:column&gt;
attributes and if provided, they will be used when drawing the individual
cells for that column.<p>

<center>
<table width="90%" border="1">
<tr>
<td width="20%"><b>Attribute</b></td>
<td width="10%"><b>Default</b></td>
<td width="70%"><b>Comments</b></td>
</tr>

<tr>
<td>styleClass</td>
<td>tableCell</td>
<td>style class name to apply to the cell</td>
</tr>

<tr>
<td>width</td>
<td>none</td>
<td>width of the cell</td>
</tr>


<tr>
<td>align</td>
<td>left</td>
<td>aligns within the cell</td>
</tr>

<tr>
<td>valign</td>
<td>top</td>
<td>specifies vertical alignment within the cell</td>
</tr>

<tr>
<td>background</td>
<td>none</td>
<td>background image - just don't do this</td>
</tr>

<tr>
<td>bgcolor</td>
<td>none</td>
<td>background color of cell - use stylesheets instead</td>
</tr>

<tr>
<td>height</td>
<td>none</td>
<td>specifies height of the cell</td>
</tr>

<tr>
<td>nowrap</td>
<td>none</td>
<td>indicates that the cell should not wrap text</td>
</tr>


</table>
</center>
<p>
<h3>Style Sheets</h3>

While attributes might be the most comfortable way to change the appearance
of your table, using style sheets are more powerful.  We use style sheets to
make the header a dark color, make the rows alternate color, and set the fonts
within the cells to a smaller version of verdana.  As the &lt;display:table&gt;
tag is drawing it assigns the following class names to elements.<p>

You can then create a style sheet and assign attributes such as font size,
family, color, etc... to each of those class names and the table will be
shown according to your styles.<p>

<center>
<table width="90%" border="1">
<tr>
<td width="20%">table</td>
<td width="80%">assigned to the main table tag</td>
</tr>

<tr>
<td width="20%">tableRowAction</td>
<td width="80%">assigned to the tr tag of the action row (paging, export, etc...)</td>
</tr>

<tr>
<td width="20%">tableRowHeader</td>
<td width="80%">assigned to the tr tag of the header row</td>
</tr>
<tr>
<td width="20%">tableRowOdd</td>
<td width="80%">assigned to the tr tag of all odd numbered data rows</td>
</tr>
<tr>
<td width="20%">tableRowEven</td>
<td width="80%">assigned to the tr tag of all even numbered data rows</td>
</tr>

<tr>
<td width="20%">tableCellAction</td>
<td width="80%">assigned to the td tag of all actions cells (paging, export, etc...)</td>
</tr>
<tr>
<td width="20%">tableCellHeader</td>
<td width="80%">assigned to the td tag of all header cells</td>
</tr>
<tr>
<td width="20%">tableCell</td>
<td width="80%">assigned to the td tag of all data cells</td>
</tr>
</table>
</center>
<p>

<jsp:include page="footer.jsp" flush="true" />
</html>