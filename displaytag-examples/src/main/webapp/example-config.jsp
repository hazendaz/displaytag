<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:directive.page import="java.util.*"/>
  <jsp:scriptlet> request.setAttribute( "test", new org.displaytag.sample.TestList(10, false) );</jsp:scriptlet>
  <jsp:scriptlet> request.setAttribute( "test2", new ArrayList<>() );</jsp:scriptlet>
  <jsp:scriptlet> Object foo = session.getAttribute( "test3" ); if( foo == null ) { session.setAttribute( "test3", new
    org.displaytag.sample.TestList(320, false) ); }
  </jsp:scriptlet>
  <tags:page>
    <h1>Config, Overriding defaults behaviors/messages</h1>
    <p>There are a number of "default" values and strings used by the display tags to show messages, decide which
      options to display, etc... This is useful if you want to change the behavior of the tag a little (for example,
      don't show the header, or only show 1 export option), or if you need to localize some of the default messages and
      banners.
    </p>
    <hr/>
    <display:table name="sessionScope.test3" export="true" pagesize="10"
      decorator="org.displaytag.sample.decorators.Wrapper">
      <display:column property="id" title="ID"/>
      <display:column property="name"/>
      <display:column property="email"/>
      <display:column property="nullValue" nulls="true"/>
      <display:column property="date"/>
      <display:setProperty name="export.amount" value="list"/>
      <display:setProperty name="export.xml" value="false"/>
      <display:setProperty name="export.excel.include_header" value="true"/>
      <display:setProperty name="paging.banner.group_size" value="6"/>
      <display:setProperty name="paging.banner.item_name" value="Cat"/>
      <display:setProperty name="paging.banner.items_name" value="Cats"/>
      <display:setProperty name="paging.banner.some_items_found">
      <![CDATA[
        <div class="btn-group pull-left"><span class="pagebanner pagination">{0} {1} sleeping, waking {2} to {3}</span></div>
      ]]>
      </display:setProperty>
    </display:table>
    <tags:code>
    <![CDATA[
<display:table name="sessionScope.test3" export="true" pagesize="10"
  decorator="org.displaytag.sample.decorators.Wrapper">
  <display:column property="id" title="ID"/>
  <display:column property="name"/>
  <display:column property="email"/>
  <display:column property="nullValue" nulls="true"/>
  <display:column property="date"/>
  <display:setProperty name="export.amount" value="list"/>
  <display:setProperty name="export.xml" value="false"/>
  <display:setProperty name="export.excel.include_header" value="true"/>
  <display:setProperty name="paging.banner.group_size" value="6"/>
  <display:setProperty name="paging.banner.item_name" value="Cat"/>
  <display:setProperty name="paging.banner.items_name" value="Cats"/>
  <display:setProperty name="paging.banner.some_items_found">
    <div class="btn-group pull-left"><span class="pagebanner pagination">{0} {1} sleeping, waking {2} to {3}</span></div>
  </display:setProperty>
</display:table>
    ]]>
    </tags:code>
    <p>
      The defaults can be overriden for just this table via the
      <code>&amp;lt;display:setProperty name="..." value="..."&gt;</code>
      tag, or you can override for the entire site via a displaytag.properties file or by directly setting properties at
      runtime. Look for the "configuration" section in the displaytag documentation for the full list of properties,
      their default values, and details on how to configure them in your system.
    </p>
    <hr/>
    <tags:ad name="middle"/>
    <h3>
      Using
      <code>basic.empty.showtable=false</code>
    </h3>
    <p>The following sample shows how you can change the behaviour for empty tables (showing the table or a simple
      message) using the "basic.empty.showtable" property. The default behavior for an empty table is to display the
      following text:
    </p>
    <display:table name="requestScope.empty">
      <display:column property="column" title="column1"/>
      <display:column property="column" title="column2"/>
      <display:column property="column" title="column3"/>
    </display:table>
    <tags:code>
    <![CDATA[
<display:table name="requestScope.empty">
  <display:column property="column" title="column1"/>
  <display:column property="column" title="column2"/>
  <display:column property="column" title="column3"/>
</display:table>
    ]]>
    </tags:code>
    <h3>
      Using
      <code>basic.empty.showtable=true</code>
    </h3>
    <display:table name="requestScope.empty">
      <display:column property="column" title="column1"/>
      <display:column property="column" title="column2"/>
      <display:column property="column" title="column3"/>
      <display:setProperty name="basic.empty.showtable" value="true"/>
    </display:table>
    <tags:code>
    <![CDATA[
<display:table name="requestScope.empty">
  <display:column property="column" title="column1"/>
  <display:column property="column" title="column2"/>
  <display:column property="column" title="column3"/>
  <display:setProperty name="basic.empty.showtable" value="true"/>
</display:table>
    ]]>
    </tags:code>
  </tags:page>
</jsp:root>
