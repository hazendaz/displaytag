<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <jsp:scriptlet> request.setAttribute( "test", new TestList(10, false) ); </jsp:scriptlet>

  <h2>Acquiring your List of data</h2>


  <p>Up until this point, we have simply had a List object available to us under the name "list" in the request scope
  that has driven the display of the tables shown. We have been setting up that bean with the following scriptlet, but
  presumably you would be doing something similar in your Action class rather then raw on this jsp page.</p>

  <pre>
&lt;% request.setAttribute( "test", new TestList( 10 ) ); %&gt;
</pre>

  <p>This table is called with the following attributes:</p>

  <pre>
<![CDATA[
&lt;display:table name="test"&gt;
]]>
</pre>

  <display:table name="test">
    <display:column property="id" title="ID" />
    <display:column property="name" />
    <display:column property="email" />
    <display:column property="status" />
    <display:column property="description" title="Comments" />
  </display:table>


  <p>But, like other struts tags, you can acquire a handle to the list you want to display by specifying not only a bean
  name, but also a bean property (a getter method), and the table tag will call that property to fetch the list to
  display.</p>

  <div>
  <p>The value of the <code>name</code> attribute can be expressed with a syntax similar to <acronym
    title="expression language">EL</acronym> of <acronym title="Java standard tag library">JSTL</acronym>.</p>

  <p>You can define the scope of the bean adding one of the following suffix:</p>
  <ul>
    <li>pageScope</li>
    <li>requestScope (default)</li>
    <li>sessionScope</li>
    <li>applicationScope</li>
  </ul>

  <p>You can also access javabean style properties, mapped properties or indexed properties in the bean, also nested!.
  The syntax for accessing a javabean property is <code>.property</code>. You can read a mapped property specifying it
  between <code>()</code> and an indexed property using <code>[]</code>.</p>

  <p>So the following:</p>

  <code>sessionScope.list.value.attribute(name).item[1]</code>

  <p>is equivalent to:</p>

  <code>session.getAttribute("list").getValue().getAttribute("name").getItem(1)</code></div>

  <p>The lists above and below are both generated randomly each time you come to this page, but since this list of data
  is attached to your session, it should remain the same through page refreshes.</p>

  <p>This table is called with the following attributes:</p>


  <pre><![CDATA[
&lt;display:table name="sessionScope.holder.list"&gt;
]]>
</pre>

  <jsp:scriptlet>
	Object foo = session.getAttribute( "holder" );
	if( foo == null ) {
      ListHolder h = new ListHolder();
      session.setAttribute( "holder", h );
	}
</jsp:scriptlet>


  <display:table name="sessionScope.holder.list">
    <display:column property="id" title="ID" />
    <display:column property="name" />
    <display:column property="email" />
    <display:column property="status" />
    <display:column property="description" title="Comments" />
  </display:table>

  <p>By default, if you supply the table tag with either a null object, or an empty list, it won't generate any tables
  at all, but it will display a message says "Nothing found to display"..</p>

  <p>You can override this message using a <code><![CDATA[&lt;setProperty&gt;]]></code> tag or a custom properties file.
  See <a href="example-config.jsp">Config, overriding default behaviors/messages</a> page.</p>

  <jsp:scriptlet> request.setAttribute( "test2", null ); </jsp:scriptlet>

  <display:table name="test2">
    <display:column property="id" title="ID" />
    <display:column property="name" />
    <display:column property="email" />
    <display:column property="status" />
    <display:column property="description" title="Comments" />
  </display:table>



  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
