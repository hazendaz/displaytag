<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<%@ page import="org.apache.taglibs.display.test.TestList"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<center>
<display:table width="75%" name="test" >
  <display:column property="id" title="ID" />
  <display:column property="name" />
  <display:column property="email" />
  <display:column property="status" />
  <display:column property="description" title="Comments"/>
</display:table>
</center>
</html>