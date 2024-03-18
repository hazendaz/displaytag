Generating links
----------------

### Smart linking of column data

If you have email addresses or web URLs in the data that you are
displaying in columns of your table, then you can set the
autolink="true" attribute in your display:column tag, and that will tell
the display:table to automatically display those pieces of data as
hyperlinks, you will not have to take any action to convert that data.

-   Email addresses will be wrapped with a
    `<a href="mailto:xxx">xxx</a>` tag, where "xxx" is the email address
    that was detected.
-   Web URLs will be wrapped with a `<a href="xxx">xxx</a>` tag, where
    "xxx" is the URL that was detected (it can be any valid URL type,
    http://, https://, ftp://, etc...)

If your column data has additional text, only the data that appears to
be an email address or a URL will be linked (not the entire column).

Turning on autolink does carry a performance penalty, as each string has
to be scanned for patterns and updated if it matches on an address or
URL.

### Dynamic links

There are two ways to create dynamic links that should appear in a
column. The first method is a "struts-like" approach which works well if
the link you want to create is based on just a single property of the
object being displayed (like a primary key value). The second approach
makes use of decorators as described on the previous example. A
decorator should be used when the dynamic link being created relies on
multiple pieces of information, relies on the index of the object in the
list, relies on some other data around it, or you want to change the
text that is linked (ie you want it to say "edit", instead of showing
the primary key of the object). Below I show how to use both examples.

### Struts-like approach

The column tag provides 5 struts-like attributes that can be set to
create a dynamic linke ( href, paramID, paramName, paramProperty).
See the display:column documentation, and the struts
documentation for a complete description of their usage, but basically:

href
:   the base URL used to construct the dynamic link

paramId
:   the name of the parameter that gets added to the URL specified above

paramName
:   name of the bean that contains the data we want to tack on the the
    URL (typically null, indicating the current object in the List)

paramProperty
:   property to call on the object specified above to return the value
    that gets tacked onto the URL.

Of these params, you typically would not use paramName. Leaving each as null indicates that you want to use the
object corresponding to the current row being processed.

```html
<display:table name="sessionScope.details">
  <display:column property="id" title="ID" href="details.jsp" paramId="id" />
  <display:column property="email" href="details.jsp" paramId="action" paramName="testparam" />
  <display:column property="status" href="details.jsp" paramId="id" paramProperty="id" />
</display:table>
```

### Using a decorator

The previous example page introduced the decorator to format dates,
money, etc... It can also be used to create dynamic links on the fly so
that you can either click on a particular column value and "drill down"
for more information, or you can create a column of text labels which
are hyperlinks that perform some action on the object in that row.

These dynamic links can be created based on some primary key of the
object, or they can make use of the object List index.

Below is a table that has two columns that have hyperlinks created on
the fly, the first makes use of the object's "ID" field to show
additional details about that object, while the second makes use of the
object's row index value to do basically the same thing.

Here you can see the details of the getLink1() and getLink2() methods in
the sample TableDecorator

```java
public String getLink1()
{
        ListObject lObject= (ListObject)getCurrentRowObject();
        int lIndex= getListIndex();
        return "\<a href=\"details.jsp?index=" + lIndex + "\"\>" + lObject.getId() + "\</a\>";
}


public String getLink2()
{
        ListObject lObject= (ListObject)getCurrentRowObject();
        int lId= lObject.getId();

        return "\<a href=\"details.jsp?id=" + lId + "&amp;action=view\">View&lt;/a> | "
                + "&lt;a href=\"details.jsp?id=" + lId + "&amp;action=edit\">Edit\</a> | "
                + "\<a href=\"details.jsp?id=" + lId + "&amp;action=delete\">Delete\</a>";
}
```

```html
<display:table name="sessionScope.details" decorator="org.displaytag.sample.Wrapper" >
  <display:column property="link1" title="ID" />
  <display:column property="email" />
  <display:column property="link2" title="Actions" />
</display:table>
```
