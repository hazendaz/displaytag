<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new ReportList(10) ); %>

<h2><a href="./index.jsp">Examples</a> > Using callbacks to show totals</h2>


<display:table name="test" class="simple nocol" decorator="org.displaytag.sample.TotalWrapper" >
  <display:column property="city" title="CITY" group="1" />
  <display:column property="project" title="PROJECT" group="2" />
  <display:column property="amount" title="HOURS" />
  <display:column property="task" title="TASK" />
</display:table>


<p>
	The decorator API provides more then just the ability to reformat data before
	it is put into columns, it also provides a hook that allows you perform some
	action on the table after each row is processed.  This allows you to interject
	some code that allows you to figure out where you are in the list of data, and
	then insert an additional row with totals, etc...  Typically you would use this
	functionality along with <a href="./example-grouping.jsp">grouping</a> features to spit out some
	nice web based reports.
</p>

<p>
	See the TableDecorator.finishRow() API documentation, along with the example
	decorator that is used in this example page.
</p>


<%@ include file="inc/footer.jsp" %>