<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new TestList(10, false) ); %>


<h2><a href="./index.jsp">Examples</a> > Implicit objects created by table</h2>


<display:table name="test" id="testit">
    <display:column property="id" title="ID" />
    <display:column property="name" />
    <display:column title="static value">static</display:column>
    <display:column title="row number (testit_rowNum)"><%=pageContext.getAttribute("testit_rowNum")%></display:column>
    <display:column title="((ListObject)testit).getMoney()"><%=((ListObject)pageContext.getAttribute("testit")).getMoney()%></display:column>
</display:table>

<p>
  If you add an <code>id</code> attribute the table tag makes the object corresponding
  to the given row available in the page context so you could use it inside scriptlet code
  or some other tag. Another implicit object exposed by the table tag is the row number,
  named <code><em>id</em>_rowNum</code>.
</p>
<p>
    These objects are saved as attributes in the page scope (you can access it using
    <code>pageContext.getAttribute("id")</code>). They are also defined as nested variables (accessible using
    <code>&lt;%=id%&gt;</code>), but only if the value of the id atribute is not a runtime expression. The preferred way
    for fetching the value is to always use pageContext.getAttribute().
</p>
<p>
  If you do not specify the <code>id</code> attribute no object is added to the pagecontext
  by the table tag
</p>
<p>
  This is a simple snippet which shows the use of the implicit objects created by the table tag with
  <acronym title="Jsp Standard Tag Library">JSTL.</acronym>
</p>

<pre>
  &lt;display:table id="row" name="mylist">
    &lt;display:column title="row number" >
      &lt;c:out value="${row_rowNum}"/>
    &lt;/display:column>
    &lt;display:column title="name" >
      &lt;c:out value="${row.first_name}"/>
      &lt;c:out value="${row.last_name}"/>
    &lt;/display:column>
  &lt;/display:table>
</pre>


<%@ include file="inc/footer.jsp" %>