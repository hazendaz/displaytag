<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:scriptlet> request.setAttribute( "test", new org.displaytag.sample.TestList(10, false) );</jsp:scriptlet>
  <tags:page>
    <h1>Using format</h1>
    <display:table name="test">
      <display:column property="id" title="ID"/>
      <display:column property="email" format="email is {0}"/>
      <display:column property="date" format="{0,date,dd-MM-yyyy}" sortable="true"/>
      <display:column property="money" format="{0,number,0,000.00} $" sortable="true"/>
    </display:table>
    <br/>
    <br/>
    <p>
      You can use any valid
      <code>java.text.MessageFormat</code>
      pattern in the
      <code>format</code>
      attribute. Sorting will be based on the original object, not on the formatted String.
    </p>
    <p>Note that errors due to an invalid pattern/object combination (for example trying to format a String like a
      number) will not be rethrown. Instead, an error log will be written and the original unformatted object displayed.
    </p>
    <p>
      You can also use a
      <code>format</code>
      pattern along with column decorators (the pattern will be applied after the decoration).
    </p>
    <tags:code>
    <![CDATA[
<display:table name="test">
  <display:column property="id" title="ID"/>
  <display:column property="email" format="email is {0}"/>
  <display:column property="date" format="{0,date,dd-MM-yyyy}" sortable="true"/>
  <display:column property="money" format="{0,number,0,000.00} $" sortable="true"/>
</display:table>
    ]]>
    </tags:code>
  </tags:page>
</jsp:root>