<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List,
                 java.util.ArrayList"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>


<jsp:include page="header.jsp" flush="true" />

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Basic, acquiring your List of data</span></td>
<td align="right" valign="top" nowrap><a href="./example-datasource.html">View JSP Source</a></td>
</tr>
</table>
<p>

Up until this point, we have simply had a List object available to us under the
name "list" in the request scope that has driven the display of the tables
shown.  We have been setting up that bean with the following scriptlet, but
presumably you would be doing something similar in your Action class rather
then raw on this jsp page.

<blockquote>
<pre>
 &lt;% request.setAttribute( "test", new TestList( 10 ) ); %&gt;
</pre>
</blockquote>

This table is called with the following attributes:<p>

<blockquote>
<pre>
&lt;display:table width="75%" name="test"&gt;
</pre>
</blockquote>
<center>
<display:table width="75%" name="test" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="description" title="Comments"/>
</display:table>
</center>
<p>


But, like other struts tags, you can acquire a handle to the list you
want to display by specifying not only a bean name, but also a bean property
(a getter method), and the table tag will call that property to fetch the list
to display.<p>

So in this contrived example, we have an object (bean) called "holder", and that
bean is currently attached to your session.  "holder" is a bean of type
<code>ListHolder</code>, and he has a single method called <code>getList</code>
that returns a List object.  That list being a different list then we used above
so it should contain different data.<p>

The lists above and below are both generated randomly each time you come to
this page, but since this list of data is attached to your session, it should
remain the same through page refreshes.<p>

This table is called with the following attributes:<p>

<blockquote>
<pre>
&lt;display:table width="75%" name="holder" property="list" scope="session"&gt;
</pre>
</blockquote>

<% Object foo = session.getAttribute( "holder" );
   if( foo == null ) {
      ListHolder h = new ListHolder();
      session.setAttribute( "holder", h );
   }
%>

<center>
<display:table width="75%" name="holder" property="list" scope="session" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="description" title="Comments"/>
</display:table>
</center>



<p>
If you are doing sick stuff in your JSP pages, you can also pass in the list
of objects directly into the tag.  Although I would hope this approach is not
used very often... The table below is generated from the following scriptlet
code and attributes: (Note it only has 5 rows)

<blockquote>
<pre>
&lt;% List blech = new TestList( 5 ); %&gt;
&lt;display:table width="75%" list="&lt;%= blech %&gt;" &gt;
</pre>
</blockquote>

<% List blech = new TestList( 5 ); %>

<center>
<display:table width="75%" list="<%= blech %>" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="description" title="Comments"/>
</display:table>
</center>

<p>By default, if you supply the table tag with either a null object, or an
empty list, then it will display the column headers like it normally would, but
will then display a single row that spans all the columns that says
"Nothing found to display".  In the next version of the display taglib, you
will be be able to override this behavior (and message) via a display:setProperty
tag.<p>

<% request.setAttribute( "test2", null ); %>
<center>
<display:table width="75%" name="test2" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="description" title="Comments"/>
</display:table>
</center>

<jsp:include page="footer.jsp" flush="true" />
</html>