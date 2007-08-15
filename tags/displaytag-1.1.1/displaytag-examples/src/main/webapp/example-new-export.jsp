<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <jsp:scriptlet> request.setAttribute( "test", new ReportList(10) ); </jsp:scriptlet>

  <h2>"What You See Is What You Get" Exports</h2>

  <display:table name="test" class="simple nocol" export="true">
    <display:setProperty name="decorator.media.html" value="org.displaytag.sample.decorators.HtmlTotalWrapper" />
    <display:setProperty name="decorator.media.pdf" value="org.displaytag.sample.decorators.ItextTotalWrapper" />
    <display:setProperty name="export.pdf.filename" value="example.pdf" />
    <display:setProperty name="decorator.media.rtf" value="org.displaytag.sample.decorators.ItextTotalWrapper" />
    <display:setProperty name="export.rtf.filename" value="example.rtf" />
    <display:setProperty name="decorator.media.excel" value="org.displaytag.sample.decorators.HssfTotalWrapper" />
    <display:setProperty name="export.excel.filename" value="example.xls" />
    <display:setProperty name="export.csv" value="false" />
    <display:setProperty name="export.xml" value="false" />
    <display:caption media="html">
      <strong>A Caption</strong>
    </display:caption>
    <display:caption media="excel pdf rtf">A Caption</display:caption>
    <display:footer media="html">
      <tr>
        <td colspan="4"><strong>Sample footer</strong></td>
      </tr>
    </display:footer>
    <display:footer media="excel pdf rtf">Sample footer</display:footer>
    <display:column property="city" title="CITY" group="1" />
    <display:column property="project" title="PROJECT" group="2" />
    <display:column property="amount" title="HOURS" />
    <display:column property="task" title="TASK" />
  </display:table>

  <p>The purpose of this example is two-fold:</p>

  <ol>
    <li>to demonstrate exports that mimic the HTML data presentation. What you see rendered in the Excel, PDF, and RTF
    formats is what you see rendered as HTML</li>
    <li>to demonstrate how easy it is to implement this by applying the template method pattern to render the main table
    and decorate it</li>
  </ol>

  <p><strong>Why would one want to do this?</strong> When typical business users are presented with the displaytag
  export facility, they usually expect the exported Excel or PDF to look just like the HTML in their browser; they
  expect a WYSIWYG rendering. Yes, even when exporting to Excel, users tend to expect the same look, feel, and structure
  of the rendered HTML, instead of raw data.</p>
  <p>(<strong>Note</strong>: the model state in this example changes with every view request, such that the data shown
  will change with every request, but the report's structure remains the same in all formats.)</p>
  <p><strong>What this table shows:</strong> You have a List who's objects are sorted and grouped by column A, column B
  and column C, so instead of repeating columns A, B over and over again, it does a grouping of those columns, and only
  shows data in those columns when it changes. Think of reports... We use the this display tag as a key part of our
  reporting framework.</p>
  <p>Grouping is straight-forward, simply make sure that your list that you are providing is sorted appropriately, then
  indicate the grouping order via the <strong>group</strong> attribute of the column tags.</p>


  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
