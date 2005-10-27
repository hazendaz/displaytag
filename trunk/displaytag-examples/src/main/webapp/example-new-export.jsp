<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

<jsp:scriptlet> request.setAttribute( "test", new ReportList(10) ); </jsp:scriptlet>

<h2><a href="./index.jsp">Examples</a> >"What You See Is What You Get" Exports</h2>

<display:table name="test" class="simple nocol" export="true" decorator="org.displaytag.sample.HtmlTotalWrapper">
  <display:setProperty name="export.pdf.decorator" value="org.displaytag.sample.ItextTotalWrapper"/>
  <display:setProperty name="export.pdf.filename" value="example.pdf"/>
  <display:setProperty name="export.rtf.decorator" value="org.displaytag.sample.ItextTotalWrapper"/>
  <display:setProperty name="export.rtf.filename" value="example.rtf"/>
  <display:setProperty name="export.excel.decorator" value="org.displaytag.sample.HssfTotalWrapper"/>
  <display:setProperty name="export.excel.filename" value="example.xls"/>
  <display:setProperty name="export.csv" value="false"/>
  <display:setProperty name="export.xml" value="false"/>
  <display:caption media="html"><b>A Caption</b></display:caption>
  <display:caption media="excel pdf rtf">A Caption</display:caption>
  <display:footer media="html">
      <tr>
      <td colspan="4"><b>Sample footer</b></td>
    </tr> 
  </display:footer>
  <display:footer media="excel pdf rtf">Sample footer</display:footer>
  <display:column property="city" title="CITY" group="1"/>
  <display:column property="project" title="PROJECT" group="2"/>
  <display:column property="amount" title="HOURS"/>
  <display:column property="task" title="TASK"/>
</display:table>

<p>
<b>The purpose of this example is two-fold:</b> 1) to demonstrate exports that mimic the HTML data presentation.  What you
see rendered in the Excel, PDF, and RTF formats is what you see rendered as HTML, 2) to demonstrate how easy it is
to implement this by applying the template method pattern to render the main table and decorate it.  Refer to the following source listings.
<ul>
<li><a href="example-new-export.txt">JSP</a></li>
<li>Main Table</li>
  <ul>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag/xref/org/displaytag/render/TableWriterTemplate.html">Generic Table Writer</a></li>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag/xref/org/displaytag/render/HtmlTableWriter.html">HTML Table Writer</a></li>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag-export-poi/xref/org/displaytag/render/HssfTableWriter.html">Excel Table Writer</a></li>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag/xref/org/displaytag/render/ItextTableWriter.html">PDF Table Writer</a></li>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag/xref/org/displaytag/render/ItextTableWriter.html">RTF Table Writer</a></li>
  </ul>
<li>Decorators</li>
  <ul>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag-examples/xref/org/displaytag/sample/TotalWrapperTemplate.html">Generic Decorator</a></li>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag-examples/xref/org/displaytag/sample/HtmlTotalWrapper.html">HTML Decorator</a></li>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag-examples/xref/org/displaytag/sample/HssfTotalWrapper.html">Excel Decorator</a></li>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag-examples/xref/org/displaytag/sample/ItextTotalWrapper.html">PDF Decorator</a></li>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag-examples/xref/org/displaytag/sample/ItextTotalWrapper.html">RTF Decorator</a></li>
  </ul>
<li>Table-Writer Clients</li>
  <ul>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag/xref/org/displaytag/tags/TableTag.html#1603">TableTag</a></li>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag-export-poi/xref/org/displaytag/export/excel/DefaultHssfExportView.html">Excel Exporter</a></li>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag/xref/org/displaytag/export/DefaultItextExportView.html">iText Exporter</a></li>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag/xref/org/displaytag/export/DefaultPdfExportView.html">PDF Exporter</a></li>
     <li><a href="http://displaytag.sourceforge.net/11/multiproject/displaytag/xref/org/displaytag/export/DefaultRtfExportView.html">RTF Exporter</a></li>
  </ul>
</ul>
</p>
<p>
<b>Why would one want to do this?</b>  When typical business users are presented with the
   displaytag export facility, they usually expect the exported Excel or PDF to
   look just like the HTML in their browser; they expect a WYSIWYG rendering.  Yes, even
   when exporting to Excel, users tend to expect the same look, feel, and structure
   of the rendered HTML, instead of raw data.
</p>
<p>
(<b>Note</b>: the model state in this example changes with every view request, such that the data shown will change
with every request, but the report's structure remains the same in all formats.)
</p>
<p>
	<b>What this table shows:</b>  You have a List who's objects are sorted and grouped by column A, column B and
	column C, so instead of repeating columns A, B over and over again, it does a
	grouping of those columns, and only shows data in those columns when it changes.
	Think of reports...  We use the this display tag as a key part of our
	reporting framework.
</p>
<p>
	Grouping is straight-forward, simply make sure that your list that you are
	providing is sorted appropriately, then indicate the grouping
	order via the <strong>group</strong> attribute of the column tags.
</p>

<p>
<b>Potential and upcoming enhancements</b> (Exit-Alpha Features):
<ol>
<li> Use css table styles as style configuration for Excel, PDF, and RTF exports.
   If not practical, provide export style config properties.</li>
<li> Specify Excel formats for columns, e.g., ##.##.</li>
<li> Include smart linking in Excel, PDF, and RTF exports.</li>
<li> Export just the current page, a page range, or all data, especially if</li>
<pre>
   paging is used.
   The ui may look like this:

   Export options: Excel | PDF | RTF | From: [1] To: [1] (default to this page)
   Export options: Excel | PDF | RTF | From: [1] To: [max] (default to all)

   Defaults to exporting what's on the page currently.  Default would be
   configurable through property.
</pre>
<li> Set the column width to the max column string value width.</li>
<li> Exporters should support nested tables, at least the iText versions should, since iText supports nested tables.</li>
<li> Implement font styles in RTF export, e.g., bold face.</li>
<li> Export in MS Word format using POI</li>
</ol>
</p>

  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
