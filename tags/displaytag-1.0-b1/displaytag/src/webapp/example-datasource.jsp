<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>

<h2><a href="./index.jsp">Examples</a> > Basic, acquiring your List of data</h2>


<p>
	Up until this point, we have simply had a List object available to us under the
	name "list" in the request scope that has driven the display of the tables
	shown.  We have been setting up that bean with the following scriptlet, but
	presumably you would be doing something similar in your Action class rather
	then raw on this jsp page.
</p>

<pre>
&lt;% request.setAttribute( "test", new TestList( 10 ) ); %&gt;
</pre>

<p>
	This table is called with the following attributes:
</p>

<pre>
&lt;display:table name="test"&gt;
</pre>

<display:table name="test" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="description" title="Comments"/>
</display:table>


<p>
	But, like other struts tags, you can acquire a handle to the list you
	want to display by specifying not only a bean name, but also a bean property
	(a getter method), and the table tag will call that property to fetch the list
	to display.
</p>

<div class="changed">
	<p>
		The value of the <code>name</code> attribute can be expressed with a sintax similar to 
		<acronym title="expression language">EL</acronym> of <acronym title="Java standar tag library">JSTL</acronym>.
	</p>
	
	<p>
		You can define the scope of the bean adding one of the following suffix:
	</p>
	<ul>
		<li>pageScope</li>
		<li>requestScope (default)</li>
		<li>sessionScope</li>
		<li>applicationScope</li>
	</ul>
	
	<p>
		You can also access javabean style properties, mapped properties or indexed properties in the bean, also nested!.
		The sintax for accessing a javabean property is <code>.property</code>. You can read a mapped property specifing it
		between <code>()</code> and an indexed property using <code>[]</code>.
	</p>
	
	<p>
		So the following:
	</p>
	
	<code>sessionScope.list.value.attribute(name).item[1]</code>
	
	<p>
	is equivalent to:
	</p>
	
	<code>session.getAttribute("list").getValue().getAttribute("name").getItem[1]</code>
	
	
	<p>
		Note: Why not using really LE? Two main reasons:
	</p>
	<ul>
		<li>compatibility with &lt; 0.9 version of the display tag library</li>
		<li>compatibility with J2EE 1.2 (JSTL and LE require j2ee 1.3)</li>
	</ul>
</div>

<p>
	The lists above and below are both generated randomly each time you come to
	this page, but since this list of data is attached to your session, it should
	remain the same through page refreshes.
</p>

<p>
	This table is called with the following attributes:
</p>


<pre>
&lt;display:table name="sessionScope.holder.list"&gt;
</pre>

<% 
	Object foo = session.getAttribute( "holder" );
	if( foo == null ) {
      ListHolder h = new ListHolder();
      session.setAttribute( "holder", h );
	}
%>


<display:table name="sessionScope.holder.list" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="description" title="Comments"/>
</display:table>


<p class="changed">
	For compatibility with previous versions of the display tag library you can use the separated
	<code>scope</code> and <code>property</code> attributes. This is now deprecated, since now
	the code is optimized using the new expression language. Anyway, this will still work:
</p>

<code>
@deprecated!
&lt;display:table name="holder" property="list" scope="session"&gt;
</code>







<p>
	If you are doing sick stuff in your JSP pages, you can also pass in the list
	of objects directly into the tag.  Although I would hope this approach is not
	used very often... The table below is generated from the following scriptlet
	code and attributes: (Note it only has 5 rows)
</p>

<pre>
&lt;% List blech = new TestList( 5 ); %&gt;
&lt;display:table list="&lt;%= blech %&gt;" &gt;
</pre>

<% List blech = new TestList( 5 ); %>

<display:table list="<%= blech %>" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="description" title="Comments"/>
</display:table>

<p>
	By default, if you supply the table tag with either a null object, or an
	empty list, then it will display the column headers like it normally would, but
	will then display a single row that spans all the columns that says
	"Nothing found to display".
</p>

<p class="changed">
	You can override this message using a &lt;setProperty&gt; tag or a custom properties file.
	See <a href="example-config.jsp">Config, overriding default behaviors/messages</a> page.
</p>

<% request.setAttribute( "test2", null ); %>

<display:table name="test2" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="description" title="Comments"/>
</display:table>



<%@ include file="inc/footer.jsp" %>