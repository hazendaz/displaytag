<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<%@ page import="org.apache.taglibs.display.test.TestList"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<center>
<display:table width="75%" name="test" >
  <display:column property="id" title="ID" width="20%" />
  <display:column property="name" width="5%" valign="top"/>
  <display:column property="email" width="5%" />
  <display:column property="status" width="65%" valign="bottom"/>
  <display:column property="description" title="Comments" width="5%"/>
</display:table>
</center>
</html>