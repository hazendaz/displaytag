<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new TestList(10, false) ); %>
<% request.setAttribute( "test2", new ArrayList() ); %>
<% Object foo = session.getAttribute( "test3" );
   if( foo == null ) {
      session.setAttribute( "test3", new TestList(320, false) );
   }
%>

<h2><a href="./index.jsp">Examples</a> > Miscellaneous...</h2>

<p>
	This page shows how to use various bells and whistles that are features of the
	display taglib that you might not be aware of.
</p>

<p>
	This table shows how you can use the "nulls" attribute to suppress "null" values
	that might be returned from your business objects.  It also shows the "maxLength"
	attribute being used to restrict the size of the LongDescription column, so that
	it fits within a certain size of table.
</p>

<display:table name="test" >
  <display:column property="id" title="ID" />
  <display:column property="nullValue" nulls="false"/>
  <display:column property="longDescription" maxLength="60" style="whitespace: nowrap;"/>
</display:table>


<%@ include file="inc/footer.jsp" %>