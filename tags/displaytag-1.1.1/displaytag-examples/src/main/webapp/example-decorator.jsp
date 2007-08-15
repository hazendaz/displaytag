<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <jsp:scriptlet> request.setAttribute( "test", new TestList(10, false) ); </jsp:scriptlet>


  <h2>Using decorators to transform data</h2>

  <display:table name="test" decorator="org.displaytag.sample.decorators.Wrapper">
    <display:column property="id" title="ID" />
    <display:column property="email" />
    <display:column property="status" />
    <display:column property="date" />
    <display:column property="money" />
  </display:table>


  <p>A "decorator" is a design pattern where one object provides a layer of functionality by wrapping or "decorating"
  another object.</p>

  <p>Let's assume you have list of business objects that you want to display, and the objects contain properties that
  don't return native Strings, and you want control over how they get displayed in the list (for example, Dates, money,
  numbers, etc...). It would be bad form to put this type of formatting code inside your business objects, so instead
  create a Decorator that formats the data according to your needs.</p>

  <p>Notice the following 4 key things (and refer to the TableDecorator javadoc for some of the other details).</p>

  <ul>
    <li>The Wrapper class must be a subclass of TableDecorator. There is various bootstrapping and API methods that are
    called inside the TableDecorator class and your class must subclass it for things to work properly (you will get a
    JspException if your class does not subclass it).</li>
    <li>Be smart and create your formatters just once in the constructor method - performance will be a lot better...</li>
    <li>Notice how the getDate() and getMoney() methods overload the return value of your business object contained in
    the List. They use the <code>TableDecorator.getCurrentRowObject()</code> method to get a handle to the underlying
    business object, and then format it accordingly.</li>
    <li>We do not have to overload each of the other business object properties (like getID, getEmail, etc...). The
    decorator class is called first, but if it doesn't implement the method for the property called, then the underlying
    business class is called.</li>
  </ul>

  <p>The way this works is that a single decorator object is created right before the table tag starts iterating through
  your List, before it starts processing a particular row, it gives the object for that row to the decorator, then as
  the various properties getXXX() methods - the decorator will be called first and if the decorator doesn't implement
  the given property, the method will be called on the original object in your List.</p>

  <h3>Column Decorators</h3>

  <p>You can specify decorators that work on individual columns, this would allow you to come up with data specific
  formatters, and just reuse them rather then coming up with a custom decorator for each table that you want to show a
  formatted date for. This kind of decorator must implement the <code>ColumnDecorator</code> interface.</p>


  <display:table name="test">
    <display:column property="id" title="ID" />
    <display:column property="email" />
    <display:column property="status" />
    <display:column property="date" decorator="org.displaytag.sample.LongDateWrapper" />
  </display:table>



  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
