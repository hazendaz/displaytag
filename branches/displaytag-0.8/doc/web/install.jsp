<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<jsp:include page="header.jsp" flush="true" />

<h1><a href="./index.jsp">Documentation</a> > Installation Guide</h1>

<p>This package comes with pre-built binaries located in the dist directory.  Those
distribution files are:</p>

<blockquote>
<table>
<tr>
<td>display-doc.war</td>
<td>the documentation for the display tag</td>
</tr>
<tr>
<td>display-examples.war &nbsp; &nbsp; &nbsp; </td>
<td>the examples showing how the display tag works</td>
</tr><tr>
<td>display.jar</td>
<td>the taglib jar</td>
</tr><tr>
<td>display.tld</td>
<td>the taglib tld file</td>
</tr>
</table>
</blockquote>

<p>To quickly view the documentation and examples showing the features and
functionality of the display taglib, just deploy the display-doc.war and the
display-examples.war files to your application server (the details of how
differ from server to server).</p>

<p>To make use of the display taglib in your own application, then do the
following:</p>

<ol>
<li>drop the display.tld file in your application /WEB-INF/ directory
<li>drop the display.jar file in your application /WEB-INF/lib directory
<li>make sure your /WEB-INF/lib/display.jar file is in your classpath<p>

<li>make sure that commons-beanutils.jar from the Jakarta project is in
       your WEB-INF/lib directory (or made available via the classpath to your
       application server).<p>

<li>make sure that commons-collections.jar from the Jakarta project is in
       your WEB-INF/lib directory (or made available via the classpath to your
       application server).<p>

<li>Make sure that log4j.jar from the Jakarta project is in your
       WEB-INF/lib directory (or made available via the classpath to your
       application server).<p>

<li>define a taglib element like the following in your /WEB-INF/web.xml file.<p>

<pre>
       &lt;taglib&gt;
         &lt;taglib-uri&gt;http://jakarta.apache.org/taglibs/display&lt;/taglib-uri&gt;
         &lt;taglib-location&gt;/WEB-INF/display.tld&lt;/taglib-location&gt;
       &lt;/taglib&gt;
</pre>

<li>define the tag extension in each JSP page that uses the display taglib.
       The uri directives must match what you defined in the web.xml file
       above..  The prefix indentifies the tags in the tag library within the
       JSP page.<p>

<pre>
       &lt;%@ taglib
           uri="http://jakarta.apache.org/taglibs/display" prefix="display" %&gt;
</pre>
</ol>

<p>As you can tell in steps 4-5, this package makes use of a couple of dependent
libraries.  For now, I have included that those libraries in my lib directory.
In the future I will not provide that library with this package, you will be
expected to fetch them yourself.</p>

<p>For more help with using taglibs in general, please see:</p>

<blockquote><a href="http://jakarta.apache.org/taglibs/tutorial.html">http://jakarta.apache.org/taglibs/tutorial.html</a>

<jsp:include page="footer.jsp" flush="true" />