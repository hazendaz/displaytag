<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<jsp:include page="header.jsp" flush="true" />

<h1><a href="./index.jsp">Documentation</a> > Building from Source</h1>

<p>The build process (mostly) follows the typical Jakarta taglibs
build process documented at:</p>

<blockquote>
   <a href="http://jakarta.apache.org/taglibs/sourcedist.html">http://jakarta.apache.org/taglibs/sourcedist.html</a>
</blockquote>

<p>In a nutshell, you need to have the following environment set up.</p>

<ol>
<li>JDK 1.1 or later (I've only tested under 1.3)
<li>The latest version of ant - <a href="http://jakarta.apache.org/ant/">http://jakarta.apache.org/ant/</a>
<li>A jaxp compatible XML parser - <a href="http://java.sun.com/xml/index.html">http://java.sun.com/xml/index.html</a>
<li>The xalan XSLT processor - <a href="http://xml.apache.org/xalan-j/">http://xml.apache.org/xalan-j/</a>
<li>An application server that provides you with a servlet.jar file<p>

<li>The Jakarta commons-beanutils.jar library - <a href="http://jakarta.apache.org/commons/beanutils.html">http://jakarta.apache.org/commons/beanutils.html</a><br>
Note: for now, I have included that library in my lib directory...<p>

<li>The Jakarta log4j.jar library - <a href="http://jakarta.apache.org/log4j/">http://jakarta.apache.org/log4j/</a><br>
Note: for now, I have included that library in my lib directory...
</ol>

<p>I believe the jaxp and xalan stuff is used just for documentation generation
during the build, so if you are not going to mess with the docs, then you don't
need those library (but of course don't expect me to accept any submissions
unless the documentation is updated as well :-)</p>

<p>Once you believe you have an appropriate environment setup.  You will then need
to update the following property file:</p>

    <blockquote>conf/local.properties</blockquote>

<p>Inside that file, you need to update various settings to point to your own
local setup (directory path, jar files, etc...).  There are only a handful
of properties, and that file is well documented.</p>


<p>Once you have updated local.properties, cd into the top level display directory
and type "ant dist".  If everthing is working ok, it should build all of the
binaries that are currently located in the dist directory.</p>

<p>To erase everything and build over (be careful to backup the current files in
the dist directory as they will get removed, and if your build is not working
properly you are hosed...), run the command "ant clean && ant dist"</p>


<p>Above, I say this build process (mostly) follows the Jakarta taglib process in
that it is organized the same way, but I've copied pieces of the Jakarta taglib
source and build files into the conf/ directory for the same reason I include
the commons-beanutil.jar and log4j.jar file in the lib directory.  To make it
easy to evaluate this tag library without installing a bunch of dependent
libraries and files.</p>


<jsp:include page="footer.jsp" flush="true" />