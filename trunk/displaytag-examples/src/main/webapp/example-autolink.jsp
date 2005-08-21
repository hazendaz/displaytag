<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new TestList(10, false) ); %>

<h2><a href="./index.jsp">Examples</a> > Smart linking of column data</h2>


<display:table name="test" >
  <display:column property="id" title="ID" />
  <display:column property="email" autolink="true" />
  <display:column property="url" autolink="true" />
</display:table>

<p>
	If you have email addresses or web URLs in the data that you are displaying in
	columns of your table, then you can set the <code>autolink="true"</code>
	attribute in your display:column tag, and that will tell the display:table to
	automatically display those pieces of data as hyperlinks, you will not have
	to take any action to convert that data.
</p>

<ul>
	<li>
		Email addresses will be wrapped with a <code>&lt;a href="mailto:xxx"&gt;xxx&lt;/a&gt;</code> tag,
		where "xxx" is the email address that was detected.
	</li>
	<li>	
		Web URLs will be wrapped with a <code>&lt;a href="xxx"&gt;xxx&lt;/a&gt;</code> tag,
		where "xxx" is the URL that was detected (it can be any valid URL type, http://, https://, ftp://, etc...)
	</li>
</ul>

<p>
	If your column data has additional text, only the data that appears to be an
	email address or a URL will be linked (not the entire column).
</p>

<p>
	Turning on autolink does carry a performance penalty, as each string has to be
	scanned for patterns and updated if it matches on an address or URL.
</p>


<%@ include file="inc/footer.jsp" %>
