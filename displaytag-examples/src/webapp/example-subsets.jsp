<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new TestList(10, false) ); %>

<h2><a href="./index.jsp">Examples</a> > Showing subsets of data from the List</h2>

<h4>Complete List</h4>

<display:table name="test" >
  <display:column property="id" title="ID" />
  <display:column property="email" />
  <display:column property="status" />
</display:table>


<h4>First 5 Items</h4>

<display:table name="test" length="5">
  <display:column property="id" title="ID" />
  <display:column property="email" />
  <display:column property="status" />
</display:table>


<h4>Items 3-8</h4>

<display:table name="test" offset="3" length="5">
  <display:column property="id" title="ID" />
  <display:column property="email" />
  <display:column property="status" />
</display:table>



<p>
	Let's say that you have a list that contains 300 elements, but you only want
	to show the first 10, or for some reason you want to show elements 101-120 (Yes,
	I know what you really want is to be able to page through them - be patient
	that example is coming up).
</p>

<p>
	You can use the <code>length</code> and <code>offset</code> attributes to limit
	the display to only portions of your List.
</p>

<%@ include file="inc/footer.jsp" %>