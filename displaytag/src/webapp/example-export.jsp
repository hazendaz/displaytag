<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new TestList(10, false) ); %>


<h2><a href="./index.jsp">Examples</a> > Wow, data exporting (excel, csv, xml)</h2>


<display:table name="test" export="true" id="currentRowObject">
  <display:column property="id" title="ID"/>
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="longDescription" media="csv excel xml" title="Not On HTML"/>
  <display:column property="date" />
  <display:column media="html" title="URL">
    <%-- n.b. that this could be done via the autolink attribute, but that rather defeats the purpose  :) --%>
    <% String url = ((ListObject)pageContext.findAttribute("currentRowObject")).getUrl();%>
    <a href="<%=url%>"><%=url%></a></display:column>
  <display:column media="csv excel" title="URL" property="url"/>
</display:table>


<p>
	When you set the Table Tag's <strong>export</strong> attribute to "true", a footer will
	appear below the table which will allow you to export the data being shown in
	various formats, just click on the format (currently supporting CSV, Excel, and
	a very crude XML).
</p>
<p>
    If you need to change what you output based on the destination, use the <strong>media</strong> attribute of the Column Tag.
    In this example, we are making the url a hyperlink in html, we are just outputing it plain in csv and excel,
    and are skipping the column altogether in xml.  Valid values for the media tag are 'html', 'xml', 'csv', and 'excel'.
</p>


<%@ include file="inc/footer.jsp" %>