<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

 <%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% Object foo = session.getAttribute( "test" );
   if( foo == null ) {
      session.setAttribute( "test", new TestList( 320 ) );
   }
%>


<jsp:include page="header.jsp" flush="true" />

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Wow, auto-paging of long lists</span></td>
<td align="right" valign="top" nowrap><a href="./example-paging.html">View JSP Source</a></td>
</tr>
</table>
<p>


<center>
<display:table width="85%" name="test" scope="session" pagesize="15" requestURI="http://edhill.its.uiowa.edu/display-examples-@version@/example-paging.jsp">
  <display:column title="Index" value="5" />
  <display:column property="id" title="ID" sort="true"/>
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" />

  <display:setProperty name="sort.behavior" value="list" />
  <display:setProperty name="paging.banner.include_first_last" value="true" />
</display:table>
</center>

<p>
Ok, you have made a query that returns you back a list of 120 elements, but you
don't want to show the user all 120 items at once, you want to allow them to
page through the results (pretend 120 is really 1200 or 12000).<p>

One of the most common web UI techniques is to show a page of data (items 1-10),
and then let the user click on a page number and allow them to scroll through
the list.  Include the "pagesize" attribute to your table tag, and the tag takes
care of the reset.<p>

You need to design your page, so that the JSP page with the table tag can be reloaded
without reloading the entire list of entries.  It is assumed that you have gone
through the action of loading the list in a previous page (or action in
Struts-speak), and then redirected the person to the page with the table tag on it.<p>

You need to do this because the table tag's various page links will all point to
the page the table tag is on (but also providing various parameters - which the
table tag itself parses and deals with).  So if you are doing any processing on
the page, it can either 1) simply not work because the table tag doesn't know
what parameters to pass for your processing, or 2) slow things down because you
would be reloading the entire list on each page, rather then just showing a subset
of the list.  It sounds confusion, but that's just because I do a poor job of
explaining it...<p>

By default, the table tag tries to figure out what the URL is for the page it
is on by calling the request.getRequestURI() method, but this will not always return
a correct answer in environment where you are forwarding a request around before
arriving at the JSP that is to be displayed (like struts).  In those cases, you
need to tell the table tag what it's URL is via the "requestURI" attribute.<p>

<jsp:include page="footer.jsp" flush="true" />
</html>