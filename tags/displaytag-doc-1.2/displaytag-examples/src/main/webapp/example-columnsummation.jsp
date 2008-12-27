<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <jsp:scriptlet> request.setAttribute( "test", new org.displaytag.sample.ReportList(10) ); </jsp:scriptlet>

  <h2>Column summation</h2>


  <display:table name="test" class="simple nocol" defaultsort="1">
    <display:column property="city" group="1" />
    <display:column property="project" />
    <display:column property="amount" format="{0,number,0.00} $" headerClass="r" class="r" total="true" />
    <display:column property="count" format="{0,number,0}" headerClass="r" class="r" total="true" />
  </display:table>

  <hr />

  <p>The table above shows a simple report, grouped by the "city" value, and with some amount.</p>
  <p>The following tables are based on the same code, with the only addition of a table decorator <code>org.displaytag.decorator.TotalTableDecorator</code>.
  </p>
  <p>The decorator will generate totals for any column with the <code>total</code> attribute set, and subtotal rows by
  grouping using the column with a <code>group</code> attribute set to 1.</p>

  <hr />

  <p>Group by city, total for amount and count</p>

  <display:table name="test" class="simple nocol" defaultsort="1" decorator="org.displaytag.decorator.TotalTableDecorator">
    <display:column property="city" group="1" />
    <display:column property="project" />
    <display:column property="amount" format="{0,number,0.00} $" headerClass="r" class="r" total="true" />
    <display:column property="count" format="{0,number,0}" headerClass="r" class="r" total="true" />
  </display:table>

  <hr />

  <p>Group by project and city, totals for amount only, customize labels.</p>

  <jsp:scriptlet>
     org.displaytag.decorator.TotalTableDecorator totals = new org.displaytag.decorator.TotalTableDecorator();
     totals.setTotalLabel("full total");
     totals.setSubtotalLabel("partial amount");
     pageContext.setAttribute("totals", totals);
  </jsp:scriptlet>


  <display:table name="test" class="simple nocol" defaultsort="1" decorator="totals">
    <display:column property="project" group="1" />
    <display:column property="city" group="2" />
    <display:column property="amount" format="{0,number,0.00} $" headerClass="r" class="r" total="true" />
    <display:column property="count" format="{0,number,0}" headerClass="r" class="r" />
  </display:table>

  <hr />

  <p>Totals for amount and count, without groups.</p>

  <display:table name="test" class="simple nocol" defaultsort="1" decorator="totals">
    <display:column property="city" />
    <display:column property="project" />
    <display:column property="amount" format="{0,number,0.00} $" headerClass="r" class="r" total="true" />
    <display:column property="count" format="{0,number,0}" headerClass="r" class="r" total="true" />
  </display:table>

  <hr />
    <jsp:scriptlet>
       org.displaytag.decorator.MultilevelTotalTableDecorator subtotals = new org.displaytag.decorator.MultilevelTotalTableDecorator();
       subtotals.setGrandTotalDescription("Total Across All Categories");    // optional, defaults to Grand Total
       subtotals.setSubtotalLabel("{0} Subtotal", request.getLocale());      // optional, defaults to "{0} Total"
       pageContext.setAttribute("subtotaler", subtotals);
    </jsp:scriptlet>

    <p>If you need subtotals for subgroups, try the MultilevelTotalTableDecorator. Items with only one entry are not
    totaled separately, but items that occur more than once are subtotaled together.</p>
    <display:table name="test" class="simple grouped-table" defaultsort="1" decorator="subtotaler">
      <display:column property="city"  group="1" title="City" />
      <display:column property="project"  group="2" title="Project" />
      <display:column property="amount" format="{0,number,$0.00}" headerClass="r" class="r" total="true" />
      <display:column property="count" format="{0,number,0}" headerClass="r" class="r" total="true"/>
    </display:table>


  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
