<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<jsp:include page="header.jsp" flush="true" />

<h1>Download</h1>

This <a href="/display-docs-@version@/changelog.jsp">changelog</a> documents the
differences between this version and previous ones.<p>

<table width="50%">
<tr>
<td><b>File</b></td>
<td align="right"><b>Version</b></td>
<td align="right"><b>Size</b></td>
<td align="right"><b>Date Posted</b></td>
</tr>
<tr>
	<td align="left"><a href="http://prdownloads.sourceforge.net/displaytag/display-bin-@version@.zip?download">display-bin-@version@.zip</a></td>
	<td align="right">@version@</td>
	<td align="right">740 K</td>
	<td align="right">@versiondate@</td>
</tr>
<tr>
	<td align="left"><a href="http://prdownloads.sourceforge.net/displaytag/display-bin-@version@.tar.gz?download">display-bin-@version@.tar.gz</a></td>
	<td align="right">@version@</td>
	<td align="right">740 K</td>
	<td align="right">@versiondate@</td>
</tr>
<tr>
	<td align="left"><a href="http://prdownloads.sourceforge.net/displaytag/display-src-@version@.zip?download">display-src-@version@.zip</a></td>
	<td align="right">@version@</td>
	<td align="right">1.4 M</td>
	<td align="right">@versiondate@</td>
</tr>
<tr>
	<td align="left"><a href="http://prdownloads.sourceforge.net/displaytag/display-src-@version@.tar.gz?download">display-src-@version@.tar.gz</a></td>
	<td align="right">@version@</td>
	<td align="right">1.3 M</td>
	<td align="right">@versiondate@</td>
</tr>
</table>
<p>

Previous versions can be found <a href="/downloads/display/">here</a>.<p>

The "bin" versions include the following:<p>

<ul>
<li><b>display.jar</b> - the jar file that contains the taglib classes, you need
to drop this file into your web application WEB-INF/lib directory.<p>
<li><b>display.tld</b> - the tld file that contains the tag library descriptor,
you need to drop this file into your web application WEB-INF directory.<p>
<li><b>display-examples.war</b> - the example applications that appear on this web site,
optionally drop this into your web container deployment directory.<p>
<li><b>display-docs.war</b> - the documentation that appears on this web site,
optionally drop this into your web container deployment directory.
</ul>

The "src" versions include the pre-built binaries (located in the dist directory
when unpacked), plus the complete source code to the tag library, the various
example pages, and the documentation.<p>

<p>You can also obtain the source from <a href="http://sourceforge.net/cvs/?group_id=73068">
the SourceForge CVS Server</a>.</p>

<jsp:include page="footer.jsp" flush="true" />