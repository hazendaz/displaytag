<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="org.apache.taglibs.display.test.TestList,
                 java.util.ArrayList,
                 java.util.List"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<jsp:include page="header.jsp" flush="true" />

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Simple case, no columns</span></td>
<td align="right" valign="top" nowrap><a href="./example-nocolumns.html">View JSP Source</a></td>
</tr>
</table>

<p>

<% request.setAttribute( "test", new TestList( 6 ) ); %>
<% List test2 = new ArrayList( 6 );
   test2.add( "Test String 1" );
   test2.add( "Test String 2" );
   test2.add( "Test String 3" );
   test2.add( "Test String 4" );
   test2.add( "Test String 5" );
   test2.add( "Test String 6" );

   request.setAttribute( "test2", test2 ); %>

<center>
<table width="90%" boder="0">
<tr>
<td width="48%">
<center>
<display:table width="100%" name="test2" />
</center>
</td>

<td>&nbsp; </td>

<td width="48%">
<center>
<display:table width="100%" name="test" />
</center>
</td>
</tr>
</table>
</center>

<p>
The simplest possible usage of the table tag is to simply point the table
tag at a list object and do nothing else.  The table tag will loop through the
list and call <code>toString()</code> on each object and displays that value in
the table's single column.  The title of the table will be a message indicating
that you should provide some default column tags.<p>

The table on the left points to a List of simple strings, while the table on the right
points at a list of ListObject objects. I have implemented my own
<code>toString()</code> method in ListObject, otherwise the data in the column
below would look like the standard Object toString() method
(<code>org.apache.taglibs.display.test.ListObject@58a1a0</code>).<p>

Typically the only time that you would want to use the tag in this simple way,
is during development just as a sanity check, or while debugging.  For
production, you should always define at least a single column if for no other
reason that you can set the column title.<p>

<jsp:include page="footer.jsp" flush="true" />

</html>