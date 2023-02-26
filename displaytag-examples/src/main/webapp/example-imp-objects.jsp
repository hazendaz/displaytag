<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:scriptlet> request.setAttribute( "test", new org.displaytag.sample.TestList(10, false) );</jsp:scriptlet>
  <tags:page>
    <h1>Implicit objects created by table</h1>
    <display:table name="test" id="testit">
      <display:column property="id" title="ID"/>
      <display:column property="name"/>
      <display:column title="static value">static</display:column>
      <display:column title="row number (testit_rowNum)">${testit_rowNum}</display:column>
      <display:column title="((ListObject)testit).getMoney()">${testit.money}</display:column>
    </display:table>
    <p>
      If you add an
      <code>id</code>
      attribute the table tag makes the object corresponding to the given row available in the page context so you could
      use it inside scriptlet code or some other tag. Another implicit object exposed by the table tag is the row
      number, named
      <code>
        <em>id</em>
        _rowNum
      </code>
      .
    </p>
    <p>
      These objects are saved as attributes in the page scope (you can access it using
      <code>pageContext.getAttribute("id")</code>
      ). They are also defined as nested variables (accessible using
      <code>&amp;lt;%=id%&gt;</code>
      ), but only if the value of the id atribute is not a runtime expression. The preferred way for fetching the value
      is to always use pageContext.getAttribute().
    </p>
    <p>
      If you do not specify the
      <code>id</code>
      attribute no object is added to the pagecontext by the table tag
    </p>
    <p>
      This is a simple snippet which shows the use of the implicit objects created by the table tag with
      <acronym title="Jsp Standard Tag Library">JSTL.</acronym>
    </p>
    <tags:code>
<![CDATA[
<display:table name="test" id="testit">
  <display:column property="id" title="ID"/>
  <display:column property="name"/>
  <display:column title="static value">static</display:column>
  <display:column title="row number (testit_rowNum)">\${testit_rowNum}</display:column>
  <display:column title="((ListObject)testit).getMoney()">\${testit.money}</display:column>
</display:table>
]]>
    </tags:code>
  </tags:page>
</jsp:root>
