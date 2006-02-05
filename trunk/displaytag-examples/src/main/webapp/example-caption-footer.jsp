<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <jsp:scriptlet> request.setAttribute( "test", new TestList(10, false) ); </jsp:scriptlet>


  <h2>Caption &amp;amp; footer</h2>

  <display:table name="test">
    <display:caption>this is a table caption</display:caption>
    <display:column property="id" title="ID" />
    <display:column property="name" />
    <display:column property="email" />
    <display:column property="status" />
    <display:column property="description" title="Comments" />
    <display:footer>
      <tr>
        <td colspan="4">sample footer</td>
        <td>any sum or custom code can go here</td>
      </tr>
    </display:footer>
  </display:table>


  <p>You can use the <code><![CDATA[&lt;display:caption>]]></code> tag to add a caption to your table. The caption tag
  should be nested directly into the table tag. It supports all the standard html caption tag attributes (title, id,
  class, style, lang and dir).</p>
  <p>If you need to add a table footer with totals, static code, etc. you can nest a <code><![CDATA[&lt;table:footer>]]></code>
  tag inside the main table tag. All the content in the footer will be evaluated only once and written at the end of the
  table, wrapped in a tfoot html tag.</p>

  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
