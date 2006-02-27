<%@ include file="inc/header.jsp" %>

<% request.setAttribute( "test", new TestList( 10 ) ); %>

<% String lClass = "isis";
   if( request.getParameter( "class" ) != null ) {
      lClass = request.getParameter( "class" );
	  if (!("isis".equals(lClass) || "its".equals(lClass) || "mars".equals(lClass) || "simple".equals(lClass) || "report".equals(lClass) || "mark".equals(lClass)))
	  {
		lClass="";
	  }
   }
%>


<h2><a href="./index.jsp">Examples</a> > Basic, columns - different styles</h2>

<ul id="stylelist">
	<li><a href="example-styles.jsp?class=isis">ISIS</a></li>
	<li><a href="example-styles.jsp?class=its">ITS</a></li>
	<li><a href="example-styles.jsp?class=mars">Mars</a></li>
	<li><a href="example-styles.jsp?class=simple">Simple</a></li>
	<li><a href="example-styles.jsp?class=report">Report</a></li>
	<li><a href="example-styles.jsp?class=mark">Mark Column</a></li>
</ul>


<display:table name="test" class="<%=lClass%>">
  <display:column property="id" title="ID" class="idcol"/>
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" class="tableCellError" />
  <display:column property="description" title="Comments"/>
</display:table>

<p>
	You actually have a lot of flexibility in how the table is displayed, but of
	course you should probably stay close to the defaults in most cases.  You adjust
	the look of the table via two methods, 1) pass through table and
	column attributes, and 2) Style sheets which are described below.
</p>

<p>
	Click through the above links to see different style examples of the same
	basic table.  Most of the differences in appearance between the tables below
	are achieved via only stylesheet changes.
</p>


<h3>Html attributes</h3>

<p>
	You can assign to the &lt;display:table&gt; tag any standard html attribute (es. cellspacing,
	cellpadding), and it will be included in the rendered table.
</p>

<p class="changed">
	Starting from version 0.9 the &lt;display:table&gt; there are no default for html attributes
	not explicitely setted. Why? Because so you can totally control the display of your table
	and you can do that (as we hope) totally using css, avoiding deprecated html presentational
	attribute (did you notice this website is written using xhtml strict?).
</p>



<p>
	Likewise, you can assign to the &lt;display:column&gt; tag any standard html attribute 
	ant it will be included in any &lt;td&gt; tag of the rendered table.
	You can also specify a class to be used only for the column header (&lt;th&gt;) adding a 
	<code>headerClass</code> attribute.
<p>

<p class="changed">
	Note: the attribute <code>styleClass</code> used for the &lt;table&gt; and  &lt;column&gt; tag 
	in previous version of the taglibrary is deprecated in favour of the standard html <code>class</code> atribute
</p>




<h3>Style Sheets</h3>

<p>
	While attributes might be the most comfortable way to change the appearance
	of your table, using style sheets are more powerful.  We use style sheets to
	make the header a dark color, make the rows alternate color, and set the fonts
	within the cells to a smaller version of verdana.  As the &lt;display:table&gt;
	tag is drawing it assigns the following class names to elements.
</p>

<p>
	You can then create a style sheet and assign attributes such as font size,
	family, color, etc... to each of those class names and the table will be
	shown according to your styles.
</p>

<p class="changed">
	Starting from version 0.9 the &lt;display:table&gt; tag produce good html tables with 
	&lt;thead&gt; and &lt;tbody&gt; sections. This makes useless the definition of few styles
	used in previous versions. See the @deprecated paragraph at the bottom of the page for details
</p>

<p class="changed">
 	New styles are added for sorted columns and display of pagination
	is handled by properties.
</p>

<table>
	<thead>
		<tr>
			<th>class</th>
			<th>assigned to</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>odd</td>
			<td>assigned to the tr tag of all odd numbered data rows</td>
		</tr>
		<tr>
			<td>even</td>
			<td>assigned to the tr tag of all even numbered data rows</td>
		</tr>
		<tr>
			<td>sorted</td>
			<td>assigned to the th tag of the sorted column</td>
		</tr>
		<tr>
			<td>order1</td>
			<td>assigned to the th tag of the sorted column if sort order is ascending</td>
		</tr>
		<tr>
			<td>order2</td>
			<td>assigned to the th tag of the sorted column if sort order is descending</td>
		</tr>
	</tbody>
</table>

<h3>Old Styles @deprecated</h3>

<p class="changed">
 	These css classes were applied in previous version of the &lt;display:*&gt; tag library. See the "now use"
	column for direction on how migrate your css to the new version (a quick task wich will also improve the
	quality -and lower the weight- of your html output)
</p>

<table>
	<thead>
		<tr>
			<th>class</th>
			<th>was</th>
			<th>now use</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>table</td>
			<td>assigned to the main table tag</td>
			<td>on class is assigned by default. You can add your own adding a "class" attribute to the &lt;display:table&gt; tag</td>
		</tr>
		<tr>
			<td>tableRowAction</td>
			<td>assigned to the tr tag of the action row (paging, export, etc...)</td>
			<td>You can totally customize the html generated using properties, no css class is added by default</td>
		</tr>
		<tr>
			<td>tableRowHeader</td>
			<td>assigned to the tr tag of the header row</td>
			<td>
				Assign a style to <code>thead tr</code> element, you don't need a class. 
				If you want this style to be applied only to the &lt;display:table&gt; simply add a class or id
				to the table tag and create a children rule in css like <code>table.myclass thead tr {color:red;}</code>
			</td>
		</tr>
		<tr>
			<td>tableRowOdd</td>
			<td>assigned to the tr tag of all odd numbered data rows</td>
			<td>the name of the class is now simply "odd"</td>
		</tr>
		<tr>
			<td>tableRowEven</td>
			<td>assigned to the tr tag of all even numbered data rows</td>
			<td>the name of the class is now simply "even"</td>
		</tr>
		<tr>
			<td>tableCellAction</td>
			<td>assigned to the td tag of all actions cells (paging, export, etc...)</td>
			<td>You can totally customize the html generated using properties, no css class is added by default</td>
		</tr>
		<tr>
			<td>tableCellHeader</td>
			<td>assigned to the td tag of all header cells</td>
			<td>
				Assign a style to <code>thead th</code> element, you don't need a class. 
				If you want this style to be applied only to the &lt;display:table&gt; simply add a class or id
				to the table tag and create a children rule in css like <code>table.myclass thead th {color:red;}</code>
			</td>
		</tr>
		<tr>
			<td>tableCell</td>
			<td>assigned to the td tag of all data cells</td>
			<td>
				Assign a style to <code>tbody td</code> element, you don't need a class.
				If you want this style to be applied only to the &lt;display:table&gt; simply add a class or id
				to the table tag and create a children rule in css like <code>table.myclass tbody td {color:red;}</code>
			</td>
		</tr>
	</tbody>
</table>


<%@ include file="inc/footer.jsp" %>