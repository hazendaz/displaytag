<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% Object foo = session.getAttribute( "details" );
   if( foo == null ) {
      session.setAttribute( "details", new TestList( 10 ) );
   }
%>

<jsp:include page="header.jsp" flush="true" />

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Standard, creating dynamic links</span></td>
<td align="right" valign="top" nowrap><a href="./example-decorator-link.html">View JSP Source</a></td>
</tr>
</table>
<p>

There are two ways to create dynamic links that should appear in a column.  The
first method is a "struts-like" approach which works well if the link you want
to create is based on just a single property of the object being displayed (like
a primary key value).  The second approach makes use of decorators as described
on the previous example.  A decorator should be used when the dynamic link being
created relies on multiple pieces of information, relies on the index of the
object in the list, relies on some other data around it, or you want to change
the text that is linked (ie you want it to say "edit", instead of showing the
primary key of the object).  Below I show how to use both examples.<p>

<h2>Stuts-like approach</h2>

The column tag provides 5 struts-like attributes that can be set to create a
dynamic linke ( href, paramID, paramName, paramProperty, paramScope ).  See the
display:column documentation, and the struts documentation for a complete description
of their usage, but basically:<p>

<ul>
<li><b>href</b> - the base URL used to construct the dynamic link
<li><b>paramId</b> - the name of the parameter that gets added to the URL
specified above
<li><b>paramName</b> - name of the bean that contains the data we want to tack
on the the URL (typicall null, indicating the current object in the List)
<li><b>paramProperty</b> - property to call on the object specified above to
return the value that gets tacked onto the URL.
<li><b>paramScope</b> - specific scope where the databean lives, typically null
</ul>

Of these params, you typically would not use paramName and paramScope.  Leaving
each as null indicates that you want to use the object corresponding to the
current row being processed.<p>

<center>
<display:table width="75%" name="details">
  <display:column property="id" title="ID"
              href="details.jsp" paramId="id" paramProperty="id" />
  <display:column property="email" />
  <display:column property="status" />
</display:table>
</center>
<p>

<h2>Using a decorator</h2>


The previous example page introduced the decorator to format dates, money, etc...  It
can also be used to create dynamic links on the fly so that you can either
click on a particular column value and "drill down" for more information, or
you can create a column of text labels which are hyperlinks that perform some
action on the object in that row.<p>

These dynamic links can be created based on some primary key of the object, or
they can make use of the object List index.<p>

Below is a table that has two columns that have hyperlinks created on the fly,
the first makes use of the object's "ID" field to show additional details
about that object, while the second makes use of the object's row index value
to do basically the same thing.<p>

<a href="">Here</a> is the source to the Wrapper class, focus on the getLink1()
and getLink2() methods.<p>

<center>
<display:table  width="75%" name="details" decorator="org.apache.taglibs.display.test.Wrapper" >
  <display:column property="link1" title="ID" />
  <display:column property="email" />
  <display:column property="link2" title="Actions" align="right" />
</display:table>
</center>
<p>

<jsp:include page="footer.jsp" flush="true" />
</html>