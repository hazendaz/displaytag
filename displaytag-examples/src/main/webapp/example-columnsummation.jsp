<jsp:root version="2.0" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:fn="http://java.sun.com/jsp/jstl/functions" xmlns:tags="urn:jsptagdir:/WEB-INF/tags/project" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8"/>
  <jsp:scriptlet> request.setAttribute( "test", new org.displaytag.sample.ReportList(10) );</jsp:scriptlet>
  <jsp:scriptlet>
  <![CDATA[
     org.displaytag.decorator.TotalTableDecorator totals = new org.displaytag.decorator.TotalTableDecorator();
     totals.setTotalLabel("full total");
     totals.setSubtotalLabel("partial amount");
     pageContext.setAttribute("totals", totals);

     org.displaytag.decorator.MultilevelTotalTableDecorator subtotals = new org.displaytag.decorator.MultilevelTotalTableDecorator();
     subtotals.setGrandTotalDescription("Total Across All Categories");    // optional, defaults to Grand Total
     subtotals.setSubtotalLabel("{0} Subtotal", request.getLocale());      // optional, defaults to "{0} Total"
     pageContext.setAttribute("subtotaler", subtotals);
     ]]>
  </jsp:scriptlet>
  <tags:page>
    <h1>Column summation</h1>
    <display:table name="test" class=" nocol" defaultsort="1">
      <display:column property="city" group="1"/>
      <display:column property="project"/>
      <display:column property="amount" format="{0,number,0.00} $" headerClass="r" class="r" total="true"/>
      <display:column property="count" format="{0,number,0}" headerClass="r" class="r" total="true"/>
    </display:table>
    <hr/>
    <p>The table above shows a simple report, grouped by the "city" value, and with some amount.</p>
    <p>
      The following tables are based on the same code, with the only addition of a table decorator
      <code>org.displaytag.decorator.TotalTableDecorator</code>
      .
    </p>
    <p>
      The decorator will generate totals for any column with the
      <code>total</code>
      attribute set, and subtotal rows by grouping using the column with a
      <code>group</code>
      attribute set to 1.
    </p>
    <hr/>
    <p>Group by city, total for amount and count</p>
    <display:table name="test" class=" nocol" defaultsort="1" decorator="org.displaytag.decorator.TotalTableDecorator">
      <display:column property="city" group="1"/>
      <display:column property="project"/>
      <display:column property="amount" format="{0,number,0.00} $" headerClass="r" class="r" total="true"/>
      <display:column property="count" format="{0,number,0}" headerClass="r" class="r" total="true"/>
    </display:table>
    <tags:code>
    <![CDATA[
<display:table name="test" class=" nocol" defaultsort="1"
  decorator="org.displaytag.decorator.TotalTableDecorator">
  <display:column property="city" group="1"/>
  <display:column property="project"/>
  <display:column property="amount" format="{0,number,0.00} $" headerClass="r" class="r" total="true"/>
  <display:column property="count" format="{0,number,0}" headerClass="r" class="r" total="true"/>
</display:table>
    ]]>
    </tags:code>
    <hr/>
    <p>Group by project and city, totals for amount only, customize labels.</p>
    <display:table name="test" class=" nocol" defaultsort="1" decorator="totals">
      <display:column property="project" group="1"/>
      <display:column property="city" group="2"/>
      <display:column property="amount" format="{0,number,0.00} $" headerClass="r" class="r" total="true"/>
      <display:column property="count" format="{0,number,0}" headerClass="r" class="r"/>
    </display:table>
    <tags:code>
    <![CDATA[
     org.displaytag.decorator.TotalTableDecorator totals = new org.displaytag.decorator.TotalTableDecorator();
     totals.setTotalLabel("full total");
     totals.setSubtotalLabel("partial amount");
     pageContext.setAttribute("totals", totals);
     
<display:table name="test" class=" nocol" defaultsort="1" decorator="totals">
  <display:column property="project" group="1"/>
  <display:column property="city" group="2"/>
  <display:column property="amount" format="{0,number,0.00} $" headerClass="r" class="r" total="true"/>
  <display:column property="count" format="{0,number,0}" headerClass="r" class="r"/>
</display:table>
    ]]>
    </tags:code>
    <hr/>
    <p>Totals for amount and count, without groups.</p>
    <display:table name="test" class=" nocol" defaultsort="1" decorator="totals">
      <display:column property="city"/>
      <display:column property="project"/>
      <display:column property="amount" format="{0,number,0.00} $" headerClass="r" class="r" total="true"/>
      <display:column property="count" format="{0,number,0}" headerClass="r" class="r" total="true"/>
    </display:table>
    <tags:code>
    <![CDATA[
     org.displaytag.decorator.TotalTableDecorator totals = new org.displaytag.decorator.TotalTableDecorator();
     totals.setTotalLabel("full total");
     totals.setSubtotalLabel("partial amount");
     pageContext.setAttribute("totals", totals);
     
<display:table name="test" class=" nocol" defaultsort="1" decorator="totals">
  <display:column property="city"/>
  <display:column property="project"/>
  <display:column property="amount" format="{0,number,0.00} $" headerClass="r" class="r" total="true"/>
  <display:column property="count" format="{0,number,0}" headerClass="r" class="r" total="true"/>
</display:table>
    ]]>
    </tags:code>
    <hr/>
    <p>If you need subtotals for subgroups, try the MultilevelTotalTableDecorator. Items with only one entry are not
      totaled separately, but items that occur more than once are subtotaled together.
    </p>
    <display:table name="test" class=" grouped-table" defaultsort="1" decorator="subtotaler">
      <display:column property="city" group="1" title="City"/>
      <display:column property="project" group="2" title="Project"/>
      <display:column property="amount" format="{0,number,$0.00}" headerClass="r" class="r" total="true"/>
      <display:column property="count" format="{0,number,0}" headerClass="r" class="r" total="true"/>
    </display:table>
    <tags:code>
    <![CDATA[
     org.displaytag.decorator.MultilevelTotalTableDecorator subtotals = new org.displaytag.decorator.MultilevelTotalTableDecorator();
     subtotals.setGrandTotalDescription("Total Across All Categories");    // optional, defaults to Grand Total
     subtotals.setSubtotalLabel("{0} Subtotal", request.getLocale());      // optional, defaults to "{0} Total"
     pageContext.setAttribute("subtotaler", subtotals);
     
<display:table name="test" class=" grouped-table" defaultsort="1" decorator="subtotaler">
  <display:column property="city" group="1" title="City"/>
  <display:column property="project" group="2" title="Project"/>
  <display:column property="amount" format="{0,number,$0.00}" headerClass="r" class="r" total="true"/>
  <display:column property="count" format="{0,number,0}" headerClass="r" class="r" total="true"/>
</display:table>
    ]]>
    </tags:code>
  </tags:page>
</jsp:root>