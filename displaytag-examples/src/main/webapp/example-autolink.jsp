<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:scriptlet> request.setAttribute( "test", new org.displaytag.sample.TestList(10, false) );</jsp:scriptlet>
  <tags:page>
    <h1>Smart linking of column data</h1>
    <display:table name="test">
      <display:column property="id" title="ID"/>
      <display:column property="email" autolink="true"/>
      <display:column property="url" autolink="true"/>
    </display:table>
    <p>
      If you have email addresses or web URLs in the data that you are displaying in columns of your table, then you can
      set the
      <code>autolink="true"</code>
      attribute in your display:column tag, and that will tell the display:table to automatically display those pieces
      of data as hyperlinks, you will not have to take any action to convert that data.
    </p>
    <ul>
      <li>
        Email addresses will be wrapped with a
        <code><![CDATA[&lt;a href="mailto:xxx"&gt;xxx&lt;/a&gt;]]></code>
        tag, where "xxx" is the email address that was detected.
      </li>
      <li>
        Web URLs will be wrapped with a
        <code><![CDATA[&lt;a href="xxx"&gt;xxx&lt;/a&gt;]]></code>
        tag, where "xxx" is the URL that was detected (it can be any valid URL type, http://, https://, ftp://, etc...)
      </li>
    </ul>
    <p>If your column data has additional text, only the data that appears to be an email address or a URL will be
      linked (not the entire column).
    </p>
    <p>Turning on autolink does carry a performance penalty, as each string has to be scanned for patterns and updated
      if it matches on an address or URL.
    </p>
    <tags:code>
<![CDATA[
<display:table name="test">
  <display:column property="id" title="ID"/>
  <display:column property="email" autolink="true"/>
  <display:column property="url" autolink="true"/>
</display:table>
]]>
    </tags:code>
  </tags:page>
</jsp:root>
