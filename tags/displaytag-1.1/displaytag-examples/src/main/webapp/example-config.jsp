<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:directive.page import="java.util.*" />
  <jsp:include page="inc/header.jsp" flush="true" />


  <jsp:scriptlet> request.setAttribute( "test", new TestList(10, false) ); </jsp:scriptlet>
  <jsp:scriptlet> request.setAttribute( "test2", new ArrayList() ); </jsp:scriptlet>
  <jsp:scriptlet> Object foo = session.getAttribute( "test3" );
   if( foo == null ) {
      session.setAttribute( "test3", new TestList(320, false) );
   }
</jsp:scriptlet>

  <h2>Config, Overriding defaults behaviors/messages</h2>


  <p>There are a number of "default" values and strings used by the display tags to show messages, decide which options
  to display, etc... This is useful if you want to change the behavior of the tag a little (for example, don't show the
  header, or only show 1 export option), or if you need to localize some of the default messages and banners.</p>

  <hr/>

  <display:table name="sessionScope.test3" export="true" pagesize="10" decorator="org.displaytag.sample.decorators.Wrapper">
    <display:column property="id" title="ID" />
    <display:column property="name" />
    <display:column property="email" />
    <display:column property="nullValue" nulls="true" />
    <display:column property="date" />

    <display:setProperty name="export.amount" value="list" />
    <display:setProperty name="export.xml" value="false" />
    <display:setProperty name="export.excel.include_header" value="true" />
    <display:setProperty name="paging.banner.group_size" value="6" />
    <display:setProperty name="paging.banner.item_name" value="Cat" />
    <display:setProperty name="paging.banner.items_name" value="Cats" />
    <display:setProperty name="paging.banner.some_items_found">
      <![CDATA[
        <span class="pagebanner">{0} {1} sleeping, waking {2} to {3}</span>
      ]]>
    </display:setProperty>
    <display:setProperty name="paging.banner.full">
      <![CDATA[
		<span class="pagelinks">
        [<a href="{1}">First</a>/<a href="{2}">Back</a>]
        {0}
        [<a href="{3}">Forw</a>/<a href="{4}">Last</a>]
      ]]>
    </display:setProperty>

  </display:table>


  <p>The defaults can be overriden for just this table via the <code>&amp;lt;display:setProperty name="..." value="..."&gt;</code> tag, or
  you can override for the entire site via a displaytag.properties file or by directly setting properties at runtime.
  Look for the "configuration" section in the displaytag documentation for the full list of properties, their default values, and details on how to configure them in your system.</p>

  <hr/>

  <p>The following sample shows how you can change the behaviour for empty tables (showing the table or a simple
  message) using the "basic.empty.showtable" property.</p>

  <h3>Using <code>basic.empty.showtable=false</code></h3>

  <display:table name="requestScope.empty">
    <display:column property="column" title="column1" />
    <display:column property="column" title="column2" />
    <display:column property="column" title="column3" />
  </display:table>

  <h3>Using <code>basic.empty.showtable=true</code></h3>


  <display:table name="requestScope.empty">
    <display:column property="column" title="column1" />
    <display:column property="column" title="column2" />
    <display:column property="column" title="column3" />
    <display:setProperty name="basic.empty.showtable" value="true" />
  </display:table>


  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
