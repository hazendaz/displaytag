<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>


<jsp:include page="header.jsp" flush="true" />

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Standard, smart linking of column data</span></td>
<td align="right" valign="top" nowrap><a href="./example-autolink.html">View JSP Source</a></td>
</tr>
</table>
<p>

<center>
<display:table width="75%" name="test" >
  <display:column property="id" title="ID" />
  <display:column property="email" autolink="true" />
  <display:column property="url" autolink="true" />
</display:table>
</center>

<p>
If you have email addresses or web URLs in the data that you are displaying in
columns of your table, then you can set the <code>autolink="true"</code>
attribute in your display:column tag, and that will tell the display:table to
automatically display those pieces of data as hyperlinks, you will not have
to take any action to convert that data.<p>

<ul>
<li>Email addresses will be wrapped with a <code>&lt;a href="mailto:xxx"&gt;xxx&lt;/a&gt;</code> tag,
where "xxx" is the email address that was detected.<p>
<li>Web URLs will be wrapped with a <code>&lt;a href="xxx"&gt;xxx&lt;/a&gt;</code> tag,
where "xxx" is the URL that was detected (it can be any valid URL type, http://, https://, ftp://, etc...)

</ul>

If your column data has additional text, only the data that appears to be an
email address or a URL will be linked (not the entire column). <p>

Turning on autolink does carry a performance penalty, as each string has to be
scanned for patterns and updated if it matches on an address or URL.<p>


<jsp:include page="footer.jsp" flush="true" />

</html>