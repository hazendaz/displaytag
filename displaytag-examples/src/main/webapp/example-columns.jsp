<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF8"/>
  <jsp:scriptlet> request.setAttribute( "test", new org.displaytag.sample.TestList(10, false) );
  </jsp:scriptlet>
  <tags:page>
    <h1>Columns</h1>
    <display:table name="test">
      <display:column property="id" title="ID"/>
      <display:column property="name"/>
      <display:column property="email"/>
      <display:column property="status"/>
      <display:column property="description" title="Comments"/>
    </display:table>
    <p>This example starts to show you how to use the table tag. You point the table tag at a datasource (a List), then
      define a number of columns with properties that map to accessor methods (getXXX) for each object in the List.
    </p>
    <p>Note that you have one column tag for every column that you want to appear in the table. And, the column
      specifies what property is shown in that particular row.
    </p>
    <p>
      You can define the content of a column by adding a
      <code>property</code>
      attribute to the column tag or adding a content to the tag.
    </p>
    <ul>
      <li>
        <code><![CDATA[&lt;display:column property="email" />]]></code>
      </li>
      <li>
        <code><![CDATA[&lt;display:column title="email">email@it.com&lt;/display:column>]]></code>
      </li>
    </ul>
    <p>
      There are two ways to define the content of a column. Of course, in the tag body you can use scriptlets or other
      custom tags. Using the
      <code>property</code>
      attribute to define the content of a column is usually faster and works better with sorting. If you add a
      <code>property</code>
      attribute the tag body is ignored.
    </p>
    <p>
      The
      <code>property</code>
      attribute specifies what
      <code>getXXX</code>
      method is called on each item in the list. So for the second column,
      <code>getName</code>
      is called. By default the property name is used as the header of the column unless you explicitly give the column
      a title.
    </p>
    <tags:code>
    <![CDATA[
<jsp:scriptlet>
  request.setAttribute( "test", new org.displaytag.sample.TestList(10, false) );
</jsp:scriptlet>

<display:table name="test">
  <display:column property="id" title="ID"/>
  <display:column property="name"/>
  <display:column property="email"/>
  <display:column property="status"/>
  <display:column property="description" title="Comments"/>
</display:table>
    ]]>
    </tags:code>
  </tags:page>
</jsp:root>
