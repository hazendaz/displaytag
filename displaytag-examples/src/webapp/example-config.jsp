<%@ include file="inc/header.jsp" %>


<% request.setAttribute( "test", new TestList(10, false) ); %>
<% request.setAttribute( "test2", new ArrayList() ); %>
<% Object foo = session.getAttribute( "test3" );
   if( foo == null ) {
      session.setAttribute( "test3", new TestList(320, false) );
   }
%>

<h2><a href="./index.jsp">Examples</a> > Config, Overriding defaults</h2>


<p>
	There are a number of "default" values and strings used by the display tags to
	show messages, decide which options to display, etc...
	This is useful if you want to change the behavior of the tag a little
	(for example, don't show the header, or only show 1 export option), or if you
	need to localize some of the default messages and banners.
</p>


<display:table name="sessionScope.test3" export="true" pagesize="10" decorator="org.displaytag.sample.Wrapper" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="nullValue" nulls="true"/>
  <display:column property="date"/>

  <display:setProperty name="export.amount" value="list" />
  <display:setProperty name="export.xml" value="false" />
  <display:setProperty name="export.excel.include_header" value="true" />
  <display:setProperty name="paging.banner.group_size" value="6" />
  <display:setProperty name="paging.banner.item_name" value="Cat" />
  <display:setProperty name="paging.banner.items_name" value="Cats" />
  <display:setProperty name="paging.banner.some_items_found" value="{0} {1} sleeping, waking {2} to {3}" />
	<display:setProperty name="paging.banner.full">
		<span class="pagelinks">[<a href="{1}">First</a>/<a href="{2}">Back</a>] {0} 
    [<a href="{3}">Forw</a>/<a href="{4}">Last</a>]
  </display:setProperty>

</display:table>


<p>
	The defaults can be overriden for just this table via the
    &lt;display:setProperty name=... value=...&gt; tag, or you
    can override for the entire site via a displaytag.properties file or by directly setting
    properties at runtime.
    See the <a href="http://displaytag.sourceforge.net/configuration.html">configuration
    documentation</a> for the full list of properties, their default values, and details
    on how to configure them in your system.
</p>

<p>
	The following sample shows how you can change the behaviour for empty tables (showing the table or a simple message)
	using the "basic.empty.showtable" property.
</p>

<p>
	Using <code>basic.empty.showtable=false</code>
</p>

<display:table name="requestScope.empty">
	<display:column property="column" title="column1" />
	<display:column property="column" title="column2" />
	<display:column property="column" title="column3" />
</display:table>

<p>
	Using <code>basic.empty.showtable=true</code>
</p>


<display:table name="requestScope.empty">
	<display:column property="column" title="column1" />
	<display:column property="column" title="column2" />
	<display:column property="column" title="column3" />
	<display:setProperty name="basic.empty.showtable" value="true" />
</display:table>


<%@ include file="inc/footer.jsp" %>