<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:display="urn:jsptld:http://displaytag.sf.net">
  <jsp:directive.page contentType="text/html; charset=UTF-8" />
  <jsp:directive.page import="org.displaytag.sample.*" />
  <jsp:include page="inc/header.jsp" flush="true" />

  <jsp:scriptlet> Object foo = session.getAttribute( "details" );
   if( foo == null ) {
      session.setAttribute( "details", new TestList(10, false) );
   }

   request.setAttribute("testparam", "sendamail");
</jsp:scriptlet>

  <h2>Creating dynamic links</h2>



  <p>There are two ways to create dynamic links that should appear in a column. The first method is a "struts-like"
  approach which works well if the link you want to create is based on just a single property of the object being
  displayed (like a primary key value). The second approach makes use of decorators as described on the previous
  example. A decorator should be used when the dynamic link being created relies on multiple pieces of information,
  relies on the index of the object in the list, relies on some other data around it, or you want to change the text
  that is linked (ie you want it to say "edit", instead of showing the primary key of the object). Below I show how to
  use both examples.</p>

  <h3>Struts-like approach</h3>

  <p>The column tag provides 5 struts-like attributes that can be set to create a dynamic linke ( href, paramID,
  paramName, paramProperty, paramScope ). See the display:column documentation, and the struts documentation for a
  complete description of their usage, but basically:</p>

  <dl>

    <dt>href</dt>
    <dd>the base URL used to construct the dynamic link</dd>

    <dt>paramId</dt>
    <dd>the name of the parameter that gets added to the URL specified above</dd>

    <dt>paramName</dt>
    <dd>name of the bean that contains the data we want to tack on the the URL (typically null, indicating the current
    object in the List)</dd>

    <dt>paramProperty</dt>
    <dd>property to call on the object specified above to return the value that gets tacked onto the URL.</dd>

    <dt>paramScope</dt>
    <dd>specific scope where the databean lives, typically null</dd>
  </dl>

  <p>Of these params, you typically would not use paramName and paramScope. Leaving each as null indicates that you want
  to use the object corresponding to the current row being processed.</p>


  <display:table name="sessionScope.details">
    <display:column property="id" title="ID" href="details.jsp" paramId="id" />
    <display:column property="email" href="details.jsp" paramId="action" paramName="testparam" paramScope="request" />
    <display:column property="status" href="details.jsp" paramId="id" paramProperty="id" />
  </display:table>


  <h3>Using a decorator</h3>

  <p>The previous example page introduced the decorator to format dates, money, etc... It can also be used to create
  dynamic links on the fly so that you can either click on a particular column value and "drill down" for more
  information, or you can create a column of text labels which are hyperlinks that perform some action on the object in
  that row.</p>

  <p>These dynamic links can be created based on some primary key of the object, or they can make use of the object List
  index.</p>

  <p>Below is a table that has two columns that have hyperlinks created on the fly, the first makes use of the object's
  "ID" field to show additional details about that object, while the second makes use of the object's row index value to
  do basically the same thing.</p>

  <p>Here you can see the details of the getLink1() and getLink2() methods in the sample TableDecorator</p>

  <pre>
<![CDATA[
	public String getLink1()
	{
		ListObject lObject= (ListObject)getCurrentRowObject();
		int lIndex= getListIndex();
		return "&lt;a href=\"details.jsp?index=" + lIndex + "\">" + lObject.getId() + "&lt;/a>";
	}


	public String getLink2()
	{
		ListObject lObject= (ListObject)getCurrentRowObject();
		int lId= lObject.getId();

		return "&lt;a href=\"details.jsp?id=" + lId
			+ "&amp;action=view\">View&lt;/a> | "
			+ "&lt;a href=\"details.jsp?id=" + lId
			+ "&amp;action=edit\">Edit&lt;/a> | "
			+ "&lt;a href=\"details.jsp?id=" + lId
			+ "&amp;action=delete\">Delete&lt;/a>";
	}
]]>
</pre>

  <display:table name="sessionScope.details" decorator="org.displaytag.sample.decorators.Wrapper">
    <display:column property="link1" title="ID" />
    <display:column property="email" />
    <display:column property="link2" title="Actions" />
  </display:table>



  <jsp:include page="inc/footer.jsp" flush="true" />

</jsp:root>
