<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new TestList(10, false) ); %>


<h2><a href="./index.jsp">Examples</a> > Data exporting</h2>


<display:table name="test" export="true" id="currentRowObject">
  <display:column property="id" title="ID"/>
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="longDescription" media="csv excel xml pdf" title="Not On HTML"/>
  <display:column property="date" />
  <display:column media="html" title="URL">
    <%-- n.b. that this could be done via the autolink attribute, but that rather defeats the purpose  :) --%>
    <% String url = ((ListObject)pageContext.findAttribute("currentRowObject")).getUrl();%>
    <a href="<%=url%>"><%=url%></a></display:column>
  <display:column media="csv excel" title="URL" property="url"/>
  <display:setProperty name="export.pdf" value="true" />
</display:table>


<p>
	When you set the Table Tag's <strong>export</strong> attribute to "true", a footer will
	appear below the table which will allow you to export the data being shown in
	various formats, just click on the format.
</p>
<p>
    If you need to change what you output based on the destination, use the <strong>media</strong> attribute of the Column Tag.
    In this example, we are making the url a hyperlink in html, we are just outputting it plain in csv and excel,
    and are skipping the column altogether in xml.  Valid values for the media tag are 'html', 'xml', 'csv', and 'excel'.
</p>

<p> Please note that the basic export functionality will <strong>not</strong> work when the JSP page
    is included in another page via a jsp:include or the RequestDispatcher.  Front end frameworks such as
    Struts and Tiles will do this behind the scenes.  If you want to use export functionality in any
    of these scenarios, you must configure the
    <a href="http://displaytag.sourceforge.net/install.html#export-filter">export filter</a>.  Filters are
    a feature of the servlet 2.3 spec, so you will need to make sure that your container is a 2.3
    servlet container.  (The core display:* tag requires servlet 2.2; you only need servlet 2.3 if you
    would like to use the export filter.)
    Also, make sure you check the <a href="http://displaytag.sourceforge.net/faq.html#action">FAQ</a>.
</p>


<%@ include file="inc/footer.jsp" %>
