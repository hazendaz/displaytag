<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>


<h2><a href="./index.jsp">Examples</a> > Wow, data exporting (excel, csv, xml)</h2>


<display:table name="test" export="true" decorator="org.displaytag.sample.Wrapper" >
  <display:column property="id" title="ID"/>
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="date" />
</display:table>


<p>
	When you set the Table Tag's <strong>export</strong> attribute to "true", a footer will
	appear below the table which will allow you to export the data being shown in
	various formats, just click on the format (currently supporting CSV, Excel, and
	a very crude XML).
</p>


<%@ include file="inc/footer.jsp" %>