<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new ReportList() ); %>

<h2><a href="./index.jsp">Examples</a> > Paging + sorting + grouping + exporting working together</h2>


<display:table name="test" export="true" sort="list" pagesize="8">
  <display:column property="city" title="CITY" group="1" sortable="true" headerClass="sortable"/>
  <display:column property="project" title="PROJECT" group="2" sortable="true" headerClass="sortable"/>
  <display:column property="amount" title="HOURS"/>
  <display:column property="task" title="TASK"/>
</display:table>

<p>
	What happen if you put everything together, sorting the full list (attribute <code>sort="list"</code>),
	using pagination and exporting?
</p>

<p>
	When sorting is enabled on the full list, the page number is automatically reset if you change
	the sorted column or the sort order.
</p>

<p>
	Exported documents contain the full list. Grouping is not applied in the exported documents.
</p>



<%@ include file="inc/footer.jsp" %>
