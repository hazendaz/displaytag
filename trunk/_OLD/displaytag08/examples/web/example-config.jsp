<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

 <%@ page session="true" %>
<%@ page import="org.apache.taglibs.display.test.TestList,
                 org.apache.taglibs.display.test.ListHolder,
                 java.util.List,
                 java.util.ArrayList"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>
<% request.setAttribute( "test2", new ArrayList() ); %>
<% Object foo = session.getAttribute( "test3" );
   if( foo == null ) {
      session.setAttribute( "test3", new TestList( 320 ) );
   }
%>

<%@ include file="header.jsp" %>
<%-- Can't do a flush here! <jsp:include page="header.jsp" flush="true" /> --%>

<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td align="left"><span class="banner"><a href="./index.jsp">Examples</a> > Config, Overriding defaults</span></td>
<td align="right" valign="top" nowrap><a href="./example-config.html">View JSP Source</a></td>
</tr>
</table>
<p>

There are a number of "default" values and strings used by the display tags to
show messages, decide which options to display, etc...  You can use the
&lt;display:setProperty name=... value=...&gt; tag to override these default
values.  This is useful if you want to change the behavior of the tag a little
(for example, don't show the header, or only show 1 export option), or if you
need to localize some of the default messages and banners.<p>

<center>
<display:table width="85%" name="test3" export="true" pagesize="10" decorator="org.apache.taglibs.display.test.Wrapper" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="nullValue" nulls="true"/>
  <display:column property="date" align="right" />

  <display:setProperty name="export.amount" value="list" />
  <%-- <display:setProperty name="export.decorated" value="false" /> --%>

  <display:setProperty name="export.xml" value="false" />
  <display:setProperty name="export.excel.include_header" value="true" />

  <display:setProperty name="paging.banner.group_size" value="6" />
  <display:setProperty name="paging.banner.prev_label" value="Back" />
  <display:setProperty name="paging.banner.next_label" value="Forw" />
  <display:setProperty name="paging.banner.item_name" value="Cat" />
  <display:setProperty name="paging.banner.items_name" value="Cats" />
  <display:setProperty name="paging.banner.some_items_found" value="{0} {1} sleeping, waking {2} to {3}" />

</display:table>
</center>

<p>
The various properties that are currently available are listed along with
their default values and a brief explanation.<p>

<table width="100%" border="1" cellspacing="0" cellpadding="2">
<tr>
<td><b>Property</b></td>
<td><b>Default</b></td>
<td><b>Valid Values</b></td>
</tr>



<tr>
<td>basic.show.header</td>
<td>true</td>
<td>true, false </td>
</tr>
<tr>
<td colspan="3">Indicates if you want the header to appear at the top of the
table, the header contains the column names, and any additional action banners
that might be required (like paging, export, etc...)<p></td>
</tr>

<tr>
<td>basic.msg.empty_list</td>
<td>Nothing found to display</td>
<td>Any string</td>
</tr>
<tr>
<td colspan="3">The message that is displayed with the list that this table is
associated with is either null, or empty.<p></td>
</tr>

<tr>
<td>sort.behavior</td>
<td>page</td>
<td>page, list</td>
</tr>
<tr>
<td colspan="3">Describes the behavior that happens when a user clicks on
a sortable column in a table that is showing just a portion of a long list.  The
default behavior (page), just resorts the elements currently being displayed
and leaves the user showing the same subset.  If you change this value to (list)
then the entire list will be resorted, and the user will be taken back to
the first subset of the newly sorted list.<p></td>
</tr>

<tr>
<td>export.banner</td>
<td>Export options: {0}</td>
<td>Any string in a message format with 1 placeholder</td>
</tr>
<tr>
<td colspan="3">Contains the string that is displayed in the table footer when
the user indicates that they want to enabled the export function.  The placeholder
is replaced with links to the various export formats that are support.<p></td>
</tr>

<tr>
<td>export.sepchar</td>
<td> | </td>
<td>Any string</td>
</tr>
<tr>
<td colspan="3">Used to seperate the valid export type (typically would be a
bar a comma, or a dash).<p></td>
</tr>

<tr>
<td>export.csv</td>
<td>true</td>
<td>true, false</td>
</tr>
<tr>
<td colspan="3">Should the tag present the option to export data in comma
seperated format (csv).<p></td>
</tr>

<tr>
<td>export.csv.label</td>
<td>CSV</td>
<td>Any string</td>
</tr>
<tr>
<td colspan="3">The label on the link that the user clicks on to export the
data in CSV format.<p></td>
</tr>

<tr>
<td>export.csv.mimetype</td>
<td>text/csv</td>
<td>Any valid mime-type</td>
</tr>
<tr>
<td colspan="3">The MIME type that is used when sending CSV data back to the
user's web browser.  I can't think of many reasons you would want to change
this, but perhaps you want to launch a specific program to deal with the data
on the user's system.<p></td>
</tr>

<tr>
<td>export.csv.include_header</td>
<td>false</td>
<td>true, false</td>
</tr>
<tr>
<td colspan="3">If set to true, then the first line of the export will
contain the column titles as displayed on the HTML page.  By default this is
set to false, so the header is not included in the export.<p></td>
</tr>


<tr>
<td>export.excel</td>
<td>true</td>
<td>true, false</td>
</tr>
<tr>
<td colspan="3">Should the tag present the option to export data in Excel
format (tab seperated values).<p></td>
</tr>

<tr>
<td>export.excel.label</td>
<td>Excel</td>
<td>Any string</td>
</tr>
<tr>
<td colspan="3">The label on the link that the user clicks on to export the
data in Excel format.<p></td>
</tr>

<tr>
<td>export.excel.mimetype</td>
<td>application/vnd.ms-excel</td>
<td>Any valid mime-type</td>
</tr>
<tr>
<td colspan="3">The MIME type that is used when sending Excel data back to the
user's web browser.  I can't think of many reasons you would want to change
this, but perhaps you want to launch a specific program to deal with the data
on the user's system.<p></td>
</tr>

<tr>
<td>export.excel.include_header</td>
<td>false</td>
<td>true, false</td>
</tr>
<tr>
<td colspan="3">If set to true, then the first line of the export will
contain the column titles as displayed on the HTML page.  By default this is
set to false, so the header is not included in the export.<p></td>
</tr>

<tr>
<td>export.xml</td>
<td>true</td>
<td>true, false</td>
</tr>
<tr>
<td colspan="3">Should the tag present the option to export data in XML
format.<p></td>
</tr>

<tr>
<td>export.xml.label</td>
<td>XML</td>
<td>Any string</td>
</tr>
<tr>
<td colspan="3">The label on the link that the user clicks on to export the
data in XML format.<p></td>
</tr>

<tr>
<td>export.xml.mimetype</td>
<td>text/xml</td>
<td>Any valid mime-type</td>
</tr>
<tr>
<td colspan="3">The MIME type that is used when sending XML data back to the
user's web browser.  I can't think of many reasons you would want to change
this, but perhaps you want to launch a specific program to deal with the data
on the user's system.<p></td>
</tr>




<tr>
<td>export.amount</td>
<td>list</td>
<td>page, list</td>
</tr>
<tr>
<td colspan="3">Indicates how much data should be sent down to the user when
they ask for a data export.  By default, it sends the entire list, but you can
instruct the table tag to only send down the data that is currently being
shown on the page.<p></td>
</tr>

<tr>
<td>export.decorated</td>
<td>true</td>
<td>true, false</td>
</tr>
<tr>
<td colspan="3">Should the data be "decorated" as it is exported.  The default
value is true, but you might want to turn off any decoratation that is HTML
specific for example when exporting the data.<p></td>
</tr>


<tr>
<td>paging.banner.placement</td>
<td>top</td>
<td>top, bottom, both</td>
</tr>
<tr>
<td colspan="3">When the table tag has to show the header for paging through a long list, this
option indicates where that header should be shown in relation to the table<p></td>
</tr>


<tr>
<td>paging.banner.item_name</td>
<td>item</td>
<td>Any string</td>
</tr>
<tr>
<td colspan="3">What the various objects in the list being displayed should
be refered to as (singular).<p></td>
</tr>


<tr>
<td>paging.banner.items_name</td>
<td>items</td>
<td>Any string</td>
</tr>
<tr>
<td colspan="3">What the various objects in the list being displayed should
be refered to as (plural).<p></td>
</tr>

<tr>
<td>paging.banner.no_items_found</td>
<td>No {0} found.</td>
<td>Any string in a message format with 1 placeholder</td>
</tr>
<tr>
<td colspan="3">What is shown in the pagination header when no objects are
available in the list to be displayed.  The single placeholder is replaced
with the name of the items in the list (plural).<p></td>
</tr>


<tr>
<td>paging.banner.one_items_found</td>
<td>1 {0} found.</td>
<td>Any string in a message format with 1 placeholder</td>
</tr>
<tr>
<td colspan="3">What is shown in the pagination header when one object is
available in the list to be displayed.  The single placeholder is replaced
with the name of the items in the list (singular).<p></td>
</tr>

<tr>
<td>paging.banner.all_items_found</td>
<td>{0} {1} found, showing all {2}</td>
<td>Any string in a message format with 3 placeholders</td>
</tr>
<tr>
<td colspan="3">What is shown in the pagination header when all the objects
in the list are being shown. {0} and {2} are replaced with the number of objects
in the list, {1} is replaced with the name of the items {plural}.<p></td>
</tr>

<tr>
<td>paging.banner.some_items_found</td>
<td>{0} {1} found, displaying {2} to {3}</td>
<td>Any string in a message format with 4 placeholders</td>
</tr>
<tr>
<td colspan="3">What is shown in the pagination header when a partial list
of the the objects in the list are being shown. {0} indicates the total number
of objects in the list, {1} is replaced with the name of the items (plural},
{2} and {3} are replaced with the start and end index of the objects being shown
respectively.<p></td>
</tr>

<tr>
<td>paging.banner.include_first_last</td>
<td>false</td>
<td>true, false</td>
</tr>
<tr>
<td colspan="3">Should the banner contain a "First" and "Last" link to instantly
jump to the start and end of the list.  The default behavior is to not include
those links<p></td>
</tr>

<tr>
<td>paging.banner.first_label</td>
<td>First</td>
<td>Any string</td>
</tr>
<tr>
<td colspan="3">Label for the link that takes the person to the first page
of objects being shown in the list.<p></td>
</tr>

<tr>
<td>paging.banner.last_label</td>
<td>Last</td>
<td>Any string</td>
</tr>
<tr>
<td colspan="3">Label for the link that takes the person to the last page
of objects being shown in the list.<p></td>
</tr>

<tr>
<td>paging.banner.prev_label</td>
<td>Prev</td>
<td>Any string</td>
</tr>
<tr>
<td colspan="3">Label for the link that takes the person to the previous page
of objects being shown in the list.<p></td>
</tr>

<tr>
<td>paging.banner.next_label</td>
<td>Next</td>
<td>Any string</td>
</tr>
<tr>
<td colspan="3">Label for the link that takes the person to the next page
of objects being shown in the list.<p></td>
</tr>

<tr>
<td>paging.banner.group_size</td>
<td>8</td>
<td>Any reasonable number</td>
</tr>
<tr>
<td colspan="3">The number of pages to show in the header that this person can
instantly jump to.<p></td>
</tr>

</table>


<jsp:include page="footer.jsp" flush="true" />
</html>