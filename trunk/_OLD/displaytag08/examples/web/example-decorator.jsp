<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>


<jsp:include page="header.jsp" flush="true" />

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Standard, using decorators to transform data</span></td>
<td align="right" valign="top" nowrap><a href="./example-decorator.html">View JSP Source</a></td>
</tr>
</table>
<p>


<center>
<display:table  width="75%" name="test" decorator="org.apache.taglibs.display.test.Wrapper" >
  <display:column property="id" title="ID" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="date" align="right" />
  <display:column property="money" align="right" />
</display:table>
</center>

<p>
A "<a href="http://www-white.media.mit.edu/~tpminka/patterns/Decorator.html">decorator</a>"
is a design pattern where one object provides a layer of functionality by
wrapping or "decorating" another object.<p>

Let's assume you have list of business objects that you want to display, and
the objects contain properties that don't return native Strings, and you want
control over how they get displayed in the list (for example, Dates, money,
numbers, etc...).  I would be bad form to put this type of formatting code
inside your business objects, so instead create a Decorator that formats the
data according to your needs.<p>

Click <a href="./Wrapper.java.txt">here</a> to view the source for our Wrapper class.  Notice the
following 4 key things (and refer to the <a href="/display-docs-@version@/javadoc/org/apache/taglibs/display/TableDecorator.html">TableDecorator documentation</a> for some of the other
details).<p>

<ul>
<li>The Wrapper class must be a subclass of TableDecorator.  There is various
bootstrapping and API methods that are called inside the TableDecorator class
and your class must subclass it for things to work properly (you will get a
JspException if your class does not subclass it).<p>

<li>Be smart and create your formatters just once in the constructor method -
performance will be a lot better...<p>

<li>Notice how the getDate() and getMoney() methods overload the return value
of your business object contained in the List.  They use the
<code>TableDecorator.getObject()</code> method to get a handle to the underlying
business object, and then format it accordingly. <p>

<li>We do not have to overload each of the other business object properties
(like getID, getEmail, etc...).  The decorator class is called first, but it
doesn't implement the method for the property called, then the underlying
business class is called.<p>
</ul>


The way this works is that a single decorator object is created right before
the table tag starts iteratoring through your List, before it starts processing
a particular row, it gives the object for that row to the decorator, then as
the various properties getXXX() methods - the decorator will be called first
and if the decorator doesn't implement the given property, the method will
be called on the original object in your List.<p>

<h2>New - Column Decorators</h2>

You can now specify decorators that work on individual columns, this would allow
you to come up with data specific formatters, and just reuse them rather then
coming up with a custom decorator for each table that you want to show a
formatted date for.

<center>
<display:table  width="75%" name="test"  >
  <display:column property="id" title="ID" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="date" align="right" decorator="org.apache.taglibs.display.test.LongDateWrapper" />
</display:table>
</center>

<p>
There are other key API methods in the TableDecorator class that you can
overwrite, but those details are saved for a later example.<p>

<jsp:include page="footer.jsp" flush="true" />
</html>