<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>


<h2><a href="./index.jsp">Examples</a> > Basic, columns</h2>

<display:table name="test" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="description" title="Comments"/>
</display:table>


<p>
	This starts to show you how to use the table tag, you point the table tag at
	a datasource (a List), and then define a number of columns that map to
	property methods (getXXX) for each object in the List.
</p>

<p>
	Note that you have one column tag for every column that you want to appear in
	the table, and the column specifies what property is shown in that particular
	row.
</p>

<p class="changed">
	You can define the content of a column adding a <code>property</code> attribute
	to the column tag or adding a content to the tag.
</p>

<ul>
	<li>&lt;display:column property="email" /&gt;</li>
	<li>&lt;display:column title="email"&gt;email@it.com&lt;/display:column&gt;</li>
</ul>

<p class="changed">
	These are two way to define the content of a column. Of course in the tag body
	you can use scriptlets or other custom tags.
	Using the <code>property</code> attribute to define the content of a column is
	usually faster and works better with sorting. If you add a <code>property</code>
	attribute the tag body is ignored.
</p>

<p>
	The <code>property</code> attribute specifies what <code>getXXX</code> method is 
	called on each item in the list.  
	So for the second column, <code>getName</code> is called, and by default the 
	property name is used as the header of the column (unless you give it an explicit
	column name).
</p>



<%@ include file="inc/footer.jsp" %>