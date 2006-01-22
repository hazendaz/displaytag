<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <jsp:scriptlet> request.setAttribute( "test", new TestList(10, false) ); </jsp:scriptlet>

  <h2>Decorating row class and id attributes</h2>

  <jsp:scriptlet>
        request.setAttribute("dyndecorator", new org.displaytag.decorator.TableDecorator()
        {
            public String addRowClass()
            {
                return ((ListObject)getCurrentRowObject()).getMoney() > 4000 ? "good" : "bad";
            }
            public String addRowId()
            {
                return "myrow" + evaluate("id");
            }
        });
  </jsp:scriptlet>

  <display:table name="test" decorator="dyndecorator">
    <display:column property="id" title="ID" />
    <display:column property="email" />
    <display:column property="date" />
    <display:column property="money" class="money" />
    <display:column title="Click on the link to test">
      <a href="#" onclick="alert('Row id: ' + this.parentNode.parentNode.id)">row id</a>
    </display:column>
  </display:table>

  <br />
  <br />

  <p>This quick example shows 2 new features in Displaytag 1.1:</p>
  <ul>
    <li>Changing the <strong>class</strong> and <strong>id</strong> attributes for a table row using a table decorator</li>
    <li>Implementing a simple table decorator on the fly</li>
  </ul>

  <p>Decoration of class and id attributes can be done by simply implementing the <code>addRowClass()</code>code> or <code>addRowId()</code>
  methods.</p>

  <hr />

  <p>For this example row that have a money value less than 4.000 $ have been assigned the "bad" css class, other rows
  have a "good" css class attribute.</p>
  <p>The implementation for the <code>addRowClass()</code> method is:</p>
  <pre>
  return ((ListObject)getCurrentRowObject()).getMoney() > 4000 ? "good" : "bad";
  </pre>
  <p>or, using the new <code>evaluate()</code> utility method in the TableDecorator class:</p>
  <pre>
  return ((Double)evaluate("money")).doubleValue() > 4000 ? "good" : "bad";
  </pre>
  <p>Combining a static css class added to the column class and a dinamic class added using a table decorator to a whole
  row, you can easily add different styles also to single cells, without the need for additional attributes. In this
  exaple cells in the "money" column have a different style when the value is &lt; 4000</p>

  <hr />

  <p>The id attribute for the row is generated using the "id" property in the iterated object, plus a "myrow" prefix.
  The implementation of the <code>addRowId()</code> method is easy:</p>
  <pre>
   return "myrow" + evaluate("id");
  </pre>

  <hr />

  <p>If you look at the source code you will see that there is no reference to an external decorator; a new decorator is
  implemented on the fly, extending only the needed methods and placing it into the page context.</p>
  <p>Displaytag will look in the page, request, session or attribute scopes for a decorator keyed by the value specified
  in the <code>decorator</code> table attribute. Only if an object is not found the name of the decorator will be
  considered as a class name and loaded using reflection.</p>
  <p>This behavior will allow you to generate quick decorators directly in the jsp page, passing parameters to existing
  decorator instances, or storing you set of decorators into request or application scope for configuration and reuse.</p>



  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
