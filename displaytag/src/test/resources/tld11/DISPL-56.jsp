<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="../../../src/tld/displaytag-12.tld" prefix="display" %>
<html>
 <head>
    <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
    <title>Displaytag unit test</title>
  </head>
<body>
<%!
  public class ValueBean {
   public ValueBean(String value) {
   this.value=value;
   }
   private String value;
   public String getValue() {
   return this.value;
   }
  }
%>
<%
  java.util.List theList = new java.util.ArrayList();
  theList.add(new ValueBean("1"));
  theList.add(new ValueBean("2"));
  theList.add(new ValueBean("3"));

  for (int i=0;i<3;i++) {
    request.setAttribute("row" + String.valueOf(i),new java.util.ArrayList(theList));
%>

<display:table uid='<%="row" + String.valueOf(i)%>' name='<%="row" + String.valueOf(i)%>' sort="list" >
  <display:column property="value" sortable="true"/>
</display:table>
<% } %>
</body>
</html> 