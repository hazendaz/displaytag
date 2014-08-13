<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:scriptlet> request.setAttribute( "test", new org.displaytag.sample.TestList(4, false) );</jsp:scriptlet>
  <tags:page>
    <h1>Nested tables</h1>
    <display:table name="test" id="parent">
      <display:column property="id"/>
      <display:column property="status"/>
      <display:column property="description" title="Comments"/>
      <c:set var="nestedName" value="test.item[${parent_rowNum -1}].subList"/>
      <display:column title="Related address">
        <display:table name="${nestedName}" id="child${parent_rowNum}" class="simple sublist">
          <display:column property="name" class="textRed"/>
          <display:column property="email"/>
        </display:table>
      </display:column>
    </display:table>
    <p> In this sample you can see two nested <code>&amp;lt;display:table&gt;</code>. Table tags can be nested to create sublist, but
      you need to consider some limitations: 
    </p>
    <ul>
      <li>
        You can't use page attributes created by the parent table for iteration in the nested table (for example if you
        have a parent table with
        <code>id="parent"</code>
        you can't use
        <code>name="pageScope.parent"</code>
        as source for the nested table). The expression is not evaluated during iteration, but only at the end of the
        whole table (to avoid useless work when you only need to display a part of the list), so you will get "nothing
        found to display" as a result, since the object created during page iteration is removed before the display.
        However, you can use objects created by the parent table do compose an expression like in the current sample.
      </li>
      <li> You can use the row index to get the object to display from the parent table list like in the current sample
        only if you are not using sorting at the same time. If you use sorting the result will be wrong, since the
        original list is not affected by sorting.
      </li>
      <li> Nested tables are not exported (how could you describe nested tables in csv or excel?).
      </li>
    </ul>
    <tags:code>
    <![CDATA[
<display:table name="test" id="parent">
  <display:column property="id"/>
  <display:column property="status"/>
  <display:column property="description" title="Comments"/>
  <c:set var="nestedName" value="test.item[\${parent_rowNum -1}].subList"/>
  <display:column title="Related address">
    <display:table name="${nestedName}" id="child\${parent_rowNum}" class="simple sublist">
      <display:column property="name" class="textRed"/>
      <display:column property="email"/>
    </display:table>
  </display:column>
</display:table>
    ]]>
    </tags:code>
  </tags:page>
</jsp:root>