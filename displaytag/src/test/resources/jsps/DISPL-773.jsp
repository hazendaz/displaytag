<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
 <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>Displaytag unit test - issue 773</title>
  </head>
<body>
<%!
  public class SortBean {
   public SortBean(String name, boolean active) {
     this.name = name;
     this.active = active;
   }
   private String name;
   private boolean active;
   public String getName() { return this.name; }
   public boolean isActive() { return this.active; }
  }
%>
<%
  java.util.List<SortBean> theList = new java.util.ArrayList<SortBean>();
  theList.add(new SortBean("Charlie", false));
  theList.add(new SortBean("Alice", true));
  theList.add(new SortBean("Bob", false));
  request.setAttribute("test773", theList);
%>
<display:table name="test773" id="table" sort="list">
  <display:column property="name" media="csv" />
  <display:column property="name" title="Name" sortable="true" />
  <display:column property="active" title="Active" sortable="true" />
</display:table>
</body>
</html>
