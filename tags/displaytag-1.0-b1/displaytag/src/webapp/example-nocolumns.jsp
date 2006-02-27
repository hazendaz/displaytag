<%@ include file="inc/header.jsp" %>
<% 
	request.setAttribute( "test", new TestList( 6 ) );
	List test2 = new ArrayList( 6 );
	test2.add( "Test String 1" );
	test2.add( "Test String 2" );
	test2.add( "Test String 3" );
	test2.add( "Test String 4" );
	test2.add( "Test String 5" );
	test2.add( "Test String 6" );
	request.setAttribute( "test2", test2 ); 
%>

<h2><a href="./index.jsp">Examples</a> > Simple case, no columns</h2>

<display:table name="test2" />

<display:table name="test" />

<p>
	The simplest possible usage of the table tag is to simply point the table
	tag at a list object and do nothing else.  The table tag will loop through the
	list and call <code>toString()</code> on each object and displays that value in
	the table's single column.  The title of the table will be a message indicating
	that you should provide some default column tags.
</p>

<p>
	The table on the left points to a List of simple strings, while the table on the right
	points at a list of ListObject objects. I have implemented my own
	<code>toString()</code> method in ListObject, otherwise the data in the column
	below would look like the standard Object toString() method
	(<code>org.displaytag.sample.ListObject@58a1a0</code>).
</p>

<p>
	Typically the only time that you would want to use the tag in this simple way,
	is during development just as a sanity check, or while debugging.  For
	production, you should always define at least a single column if for no other
	reason that you can set the column title.
</p>

<%@ include file="inc/footer.jsp" %>