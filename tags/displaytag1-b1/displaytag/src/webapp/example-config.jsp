<%@ include file="inc/header.jsp" %>


<% request.setAttribute( "test", new TestList( 10 ) ); %>
<% request.setAttribute( "test2", new ArrayList() ); %>
<% Object foo = session.getAttribute( "test3" );
   if( foo == null ) {
      session.setAttribute( "test3", new TestList( 320 ) );
   }
%>

<h2><a href="./index.jsp">Examples</a> > Config, Overriding defaults</h2>


<p>
	There are a number of "default" values and strings used by the display tags to
	show messages, decide which options to display, etc...  You can use the
	&lt;display:setProperty name=... value=...&gt; tag to override these default
	values.  This is useful if you want to change the behavior of the tag a little
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
  <display:setProperty name="paging.banner.prev_label" value="Back" />
  <display:setProperty name="paging.banner.next_label" value="Forw" />
  <display:setProperty name="paging.banner.item_name" value="Cat" />
  <display:setProperty name="paging.banner.items_name" value="Cats" />
  <display:setProperty name="paging.banner.some_items_found" value="{0} {1} sleeping, waking {2} to {3}" />

</display:table>


<p>
	See documentation for the full list of properties and their default values.
</p>


<%@ include file="inc/footer.jsp" %>