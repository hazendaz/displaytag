<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new TestList(10, false) ); %>


<h2><a href="./index.jsp">Examples</a> > Basic, implicit objects created by table</h2>


<display:table name="test" id="testit">
    <display:column property="id" title="ID" />
    <display:column property="name" />
    <display:column title="static value">static</display:column>
    <display:column title="row number (testit_rowNum)"><%=testit_rowNum%></display:column>
    <display:column title="((ListObject)testit).getMoney()"><%=((ListObject)testit).getMoney()%></display:column>
</display:table>

<p class="changed">
	If you add an <code>id</code> attribute the table tag makes the object corresponding
	to the given row available in the page context so you could use it inside scriptlet code
	or some other tag. Another implicit object exposed by the table tag is the row number,
	named <code><em>id</em>_rowNum</code>.
</p>

<p class="changed">
	If you do not specify the <code>id</code> attribute no object is added to the pagecontext
	by the table tag
</p>

<p>
	You can also use "decorators" to provide the same type
	of functionality. See:
</p>

<ul>
	<li><a href="example-decorator.jsp">Using decorators to transform/process data</a></li>
	<li><a href="example-decorator-link.jsp">Using decorators to create dynamic links</a></li>
	<li><a href="example-callbacks.jsp">Using callbacks to show totals</a></li>
</ul>


<%@ include file="inc/footer.jsp" %>