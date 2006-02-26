<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List,
                 org.apache.taglibs.display.test.ListObject"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>


<jsp:include page="header.jsp" flush="true" />

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Basic, implicit objects created by table</span></td>
<td align="right" valign="top" nowrap><a href="./example-imp-objects.html">View JSP Source</a></td>
</tr>
</table>
<p>

There are none...<p>

I include this page, as it is a reasonable assumption/resquest that this tag
would work like other iterating tags, and perhaps make the object corresponding
to the given row available as some type of implicit object that you could use
inside scriptlet code (or some other tag) for you to access while the list you
provided is being looped over.<p>

I tried designing this as an iterating tag that would expose the underlying
object each time through the loop, etc...  But I ran into many problems, and
ultimatly decided that using "<a href="example-decorator.jsp">decorators</a>"
provided the same type of functionality and was cleaner as it helped keep code
out of the JSP pages.<p>

So if you have come to this page, thinking I wish I had access to the objects
as they were being processed in the loop in my JSP page, well I'm not going to
provide that - but look at the following three pages and I'll bet one of them
will have a solution to your problem.<p>

<ul>
<li><a href="example-decorator.jsp">Standard, using decorators to transform/process data</a>
<li><a href="example-decorator-link.jsp">Standard, using decorators to create dynamic links</a>
<li><a href="example-callbacks.jsp">Wow, using callbacks to show totals</a>
</ul>

Rather then implementing this tag as an iterating tag, it's implemented as
follows: The various column tags register themselves with the parent table tag
just once, and when we hit the closing table tag, we use the configuration data
provided in the table attributes, and the column tags to blast through the list
of objects and draw the table all at once.  We do <b>not</b> loop over the
column tags and call them over and over, and thus we can't really provide any
implicit objects that would be useful to you in the JSP page...  Doing that
caused the following problems:<p>

<ul>
<li>broke sorting, as we need to do some processing of the entire list up front.
<li>made dealing with exceptional cases difficult (no values, null values)
<li>made performance horrible (3-5 times slower, then just creating the
buffer in the end tag).
<li>probably others, I moved away from this approach pretty early on.
</ul>



<jsp:include page="footer.jsp"  flush="true"/>
</html>