<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="init.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>The &lt;display:*&gt; tag library</title>
	<meta http-equiv="Expires" content="-1" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
	<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen, print" />
</head>

<body>

<div id="header">
	<h1>The &lt;display:*&gt; tag library <small>1.0</small></h1>
	
	<ul>
		<li><a href="http://displaytag.sf.net">Documentation</a></li>	
		<li><a href="index.jsp">Examples</a></li>	
	</ul>
	
</div>

<% 
	if (request.getRequestURI().indexOf("example-") != -1)
	{
%>
	<ul id="showsource">
		<li><a href="<%=request.getRequestURI()%>.source">View JSP Source</a></li>
	</ul>
<% 
	} 
%>

<div id="body">