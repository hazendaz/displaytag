<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  version="1.0">
  <xsl:output method="html" 
              encoding="ISO-8859-1"
              indent="yes" 
              omit-xml-declaration="yes"/>
  <!--
    This XSL is used to transform Tag library Documentation XML files into
    HTML documents formatted for the Jakarta Taglibs Web site.
    
    The Tag Library Documentation file(s) are standalone XML documents based
    on the Tag Library Descriptor format (i.e., they have no DTD or DOCTYPE 
    declaration).
    
    The TLD forms the basis for the Documentation, much of it used here as the
    a natural base for the HTML documentation.  Added to it are arbitrary, but 
    conventional (the conventions determined by the Jakarta Taglibs project 
    for the time being), XML elements used to provide information extraneous to 
    the TLD proper.  For example, info elements are added for tag attributes 
    (a request has been made to the JSP expert group to add such an element to 
    the TLD proper), and example elements are added for usage examples.
  
    Scott Stirling
    sstirling@mediaone.net
    1/1/2001  
  -->

<!--
    How some of the non-obvious TLD==>HTML mappings are used here:

    HTML <title> : TLD <taglib><info></info></taglib>
    HTML page title <h1>: TLD <taglib><info></info></taglib>
    
    - Sometimes the <short-name> value of the TLD is used as the taglib name or 
      the prefix for various minutiae, other times the <info> line is used.  
      Depends on the context, as you can see below.
 -->

<xsl:template match="/">
  <xsl:apply-templates />
</xsl:template>

<xsl:template match="/document">
<html>
  <xsl:apply-templates />
</html>
</xsl:template>

<xsl:template match="/document/properties">
  <head>
    <meta content="{author}" name="author"/>
    <xsl:choose>
      <xsl:when test="title">
        <title><xsl:value-of select="title"/></title>
      </xsl:when>
      <xsl:otherwise>
        <title>
          Jakarta-Taglibs:
          <xsl:value-of select="/document/taglib/display-name"/>
        </title>
      </xsl:otherwise>
    </xsl:choose>
  </head>
</xsl:template>
  
<xsl:template match="/document/taglib">
  <body bgcolor="white">
  <center>
    <h1>Jakarta Project: <xsl:value-of select="display-name"/></h1>
    <h3>Version: <xsl:value-of select="tlib-version"/></h3>
  </center>

  <!-- Table of Contents -->
  <h3>Table of Contents</h3>
  <ul>
    <li><a href="#overview">Overview</a></li>
    <li><a href="#requirements">Requirements</a></li>
    <li><a href="#config">Configuration</a></li>
    <xsl:call-template name="doctoc"/>
    <li><a href="#summary">Tag Summary</a></li>
    <li><a href="#reference">Tag Reference</a></li>
    <!-- Additional TOC sections, the toc element is undocumented -->
    <xsl:for-each select="toc">
      <li>
        <a>
          <xsl:attribute name="href">#<xsl:value-of select="@href"/>
          </xsl:attribute><xsl:value-of select="@name"/>
        </a>
      </li>
    </xsl:for-each>
    <!-- End Additional TOC sections -->
    <li><a href="#examples">Examples</a></li>
    <li><a href="#javadocs">Javadocs</a></li>
    <li><a href="#history">Revision History</a></li>
    <xsl:if test="developers-notes">
      <li><a href="#developers-notes">Developers' Notes</a></li>
    </xsl:if>
  </ul>    

  <!-- Overview -->
  <a><xsl:attribute name="name">overview</xsl:attribute></a>
  <h3>Overview</h3>
  <xsl:apply-templates select="description"/>
    
  <!-- Requirements -->
  <a><xsl:attribute name="name">requirements</xsl:attribute></a>
  <h3>Requirements</h3>
  <xsl:apply-templates select="requirements-info"/>

  <!-- Configuration -->
  <xsl:call-template name="configuration"/>

  <!-- Documentation -->
  <xsl:call-template name="documentation"/>

  <!--  Tag Summary Section  -->
  <a><xsl:attribute name="name">summary</xsl:attribute></a>
  <h3>Tag Summary</h3>
  <xsl:for-each select="tagtoc">
    <b><xsl:value-of select="@name"/></b>
    <table border="0" width="90%" cellpadding="3" cellspacing="3">
      <xsl:for-each select="tag">
        <tr>
          <td width="25%" valign="top">
            <a>
              <xsl:attribute name="href">#<xsl:value-of select="name"/>
              </xsl:attribute><xsl:value-of select="name"/>
            </a>
          </td>
          <td width="75%">
            <xsl:choose>
              <xsl:when test="summary">
                <xsl:value-of select="summary"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="description"/>
              </xsl:otherwise>
            </xsl:choose>
          </td>
        </tr> 
      </xsl:for-each>
      <!-- The next empty row is for formatting purposes only. -->
      <tr>
        <td colspan="2"><xsl:text>&#160;</xsl:text></td>
      </tr>
    </table>
  </xsl:for-each>

  <!-- Tag Reference Section -->
  <a><xsl:attribute name="name">reference</xsl:attribute></a>
  <h3>Tag Reference</h3>
  <xsl:for-each select="tagtoc">
    <!-- Start for-each to process <tag> elements here -->
    <xsl:for-each select="tag">
      <table border="0" width="90%" cellpadding="3" cellspacing="3">
        <tr bgcolor="#cccccc">
          <td colspan="5">
            <b><font size="+1">
              <a><xsl:attribute name="name"><xsl:value-of select="name"/>
                 </xsl:attribute><xsl:value-of select="name"/>
              </a>
            </font></b>
          </td>
          <td align="right" width="17%">
            Availability:<xsl:text>&#160;</xsl:text><xsl:value-of select="availability"/>
          </td>
        </tr>
        <tr>
          <td colspan="6">
            <xsl:apply-templates select="description"/>
          </td>
        </tr>
        <tr>
          <td width="15%"><b>Tag Body</b></td>
          <td width="17%"><xsl:value-of select="body-content"/></td>
          <td width="17%"><xsl:text>&#160;</xsl:text></td>
          <td width="17%"><xsl:text>&#160;</xsl:text></td>
          <td width="17%"><xsl:text>&#160;</xsl:text></td>
          <td width="17%"><xsl:text>&#160;</xsl:text></td>
        </tr>
        <tr>
          <td><b>Restrictions</b></td>
          <td colspan="5">
            <xsl:choose>
              <xsl:when test="restrictions">
                <xsl:value-of select="restrictions"/>
              </xsl:when>
              <xsl:otherwise>
                None
              </xsl:otherwise>
            </xsl:choose>
          </td>
        </tr>

        <!-- BEGIN xsl:choose
             If tag has attributes, then create headers and iterate
             over the attributes, otherwise, skip headers and print
             "None" -->
        <xsl:choose>
          <xsl:when test="attribute">
            <tr> 
              <td><b>Attributes</b></td>
              <td >Name</td>
              <td >Required</td>
              <td colspan="2">
                Runtime<xsl:text>&#160;</xsl:text>Expression<xsl:text>&#160;</xsl:text>Evaluation
              </td>
              <td >Availability</td>
            </tr>
    
            <!-- Start <attribute> for-each here -->
            <xsl:for-each select="attribute">
              <tr bgcolor="#cccccc">
                <td bgcolor="#ffffff"><xsl:text>&#160;</xsl:text></td>
                <td><b><xsl:value-of select="name"/></b></td>
                <td>
                   <xsl:text>&#160;</xsl:text>
                   <xsl:value-of select="required"/>
                </td>
                <td colspan="2">
                  <xsl:text>&#160;</xsl:text>
                  <xsl:value-of select="rtexprvalue"/>
                </td>
                <td>
                  <xsl:text>&#160;</xsl:text>
                  <xsl:value-of select="availability"/>
                </td>
              </tr>
              <tr>
                <td bgcolor="#ffffff"><xsl:text>&#160;</xsl:text></td>
                <td colspan="5">
                  <xsl:apply-templates select="description"/>
                </td>
              </tr>
            </xsl:for-each>
            <!-- End <attribute> for-each here -->
          </xsl:when>
          <xsl:otherwise>
            <tr> 
              <td><b>Attributes</b></td>
              <td colspan="5">None</td>
            </tr>
          </xsl:otherwise>
        </xsl:choose>
        <!-- END xsl:choose -->
    
        <!-- BEGIN xsl:choose
             If tag creates a script variable or attribute with
             properties, then create headers and iterate over the
             properties, otherwise, skip headers and print "None" -->
        <xsl:choose>  
          <xsl:when test="variable">
            <tr>
              <td><b>Variables</b></td>
              <td colspan="2">Name</td>
              <td colspan="2">Scope</td>
              <td>Availability</td>
            </tr>
            <xsl:for-each select="variable">
              <tr bgcolor="#cccccc">
                <td bgcolor="#ffffff"><xsl:text>&#160;</xsl:text></td>
                <xsl:choose>
                  <xsl:when test="name-given">
                    <td colspan="2">
                      <xsl:text>&#160;</xsl:text>
                      <b><xsl:value-of select="name-given"/></b>
                    </td>
		  </xsl:when>
		  <xsl:otherwise>
                    <td colspan="2"><xsl:text>&#160;</xsl:text>
                      <b><xsl:value-of select="name-from-attribute"/></b>
                      attribute value
                    </td>
                  </xsl:otherwise>
                </xsl:choose>
                <td colspan="2">
                  <xsl:text>&#160;</xsl:text>
                  <xsl:choose>
                    <xsl:when test="scope='AT_BEGIN'">
                      Start of tag to end of page
                    </xsl:when>
                    <xsl:when test="scope='AT_END'">
                      End of tag to end of page
                    </xsl:when>
                    <xsl:otherwise>
                      Nested within tag
                    </xsl:otherwise>
                  </xsl:choose>
                </td>
                <td>
                  <xsl:text>&#160;</xsl:text>
                  <xsl:value-of select="availability"/>
                </td>
              </tr>
              <tr>
                <td><xsl:text>&#160;</xsl:text></td>
                <td colspan="5">
                  <xsl:apply-templates select="description"/>
                </td>
              </tr>
              <xsl:choose>
                <xsl:when test="beanprop">
                  <tr>
                    <td><xsl:text>&#160;</xsl:text></td>
                    <td ><b>Properties</b></td>
                    <td >Name</td>
                    <td >Get</td>
                    <td >Set</td>
                    <td >Availability</td>
                  </tr>

                  <!-- Start <beanprop> for-each here -->
                  <xsl:for-each select="beanprop">
                    <tr bgcolor="#cccccc">
                      <td bgcolor="#ffffff"><xsl:text>&#160;</xsl:text></td>
                      <td bgcolor="#ffffff"><xsl:text>&#160;</xsl:text></td>
                      <td>
                        <xsl:text>&#160;</xsl:text>
                        <b><xsl:value-of select="name"/></b>
                      </td>
                      <td><xsl:text>&#160;</xsl:text>
                        <xsl:value-of select="get"/>
                      </td>
                      <td><xsl:text>&#160;</xsl:text>
                        <xsl:value-of select="set"/>
                      </td>
                      <td><xsl:text>&#160;</xsl:text>
                        <xsl:value-of select="availability"/>
                      </td>
                    </tr>
                    <tr> 
                      <td><xsl:text>&#160;</xsl:text></td>
                      <td><xsl:text>&#160;</xsl:text></td>
                      <td colspan="4">
                        <xsl:apply-templates select="description"/>
                      </td>
                    </tr>
                  </xsl:for-each>
                  <!-- End <beanprop> for-each here -->
                </xsl:when>
                <xsl:otherwise>
                  <tr>
                    <td><xsl:text>&#160;</xsl:text></td>
                    <td><b>Properties</b></td>
                    <td colspan="4">None</td>
                  </tr>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:for-each>
          </xsl:when>
          <xsl:otherwise>
            <tr>         
              <td><b>Variables</b></td>
              <td colspan="5">None</td>
            </tr>
          </xsl:otherwise>
        </xsl:choose>
        <!-- END xsl:choose -->

        <!-- Examples Section -->
        <xsl:call-template name="examples"/>
        <!-- End Examples Section -->
      
      </table>
      <!-- End for-each <tag> element here -->
    </xsl:for-each>
  </xsl:for-each>
  
  <!-- Additional TOC sections -->
  <xsl:for-each select="toc">
    <a><xsl:attribute name="name"><xsl:value-of select="@href"/>
      </xsl:attribute>
    </a>
    <h3><xsl:value-of select="@name"/></h3>
    <xsl:copy-of select="*|text()"/>
  </xsl:for-each>
  <!-- End Additional TOC sections -->
  
  <!-- Footer Section (Examples, Javadoc, History) -->
  <a name="examples"><h3>Examples</h3></a>
  <p>See the example application
    <xsl:value-of select="short-name"/>-examples.war for examples of the usage
    of the tags from this custom tag library.</p>

  <a name="javadocs"><h3>Java Docs</h3></a>
  <p>Java programmers can view the java class documentation for this tag
    library as <a href="javadoc/index.html">javadocs</a>.</p>

  <a name="history"><h3>Revision History</h3></a>
  <p>Review the complete <a href="changes.html">revision history</a> of
    this tag library.</p>
  
  <!-- developers' notes, if any -->  
  <xsl:apply-templates select="developers-notes"/>

  </body>
</xsl:template>
  
<xsl:template match="requirements-info">
  <xsl:call-template name="uri"/>
</xsl:template>

<xsl:template match="description">
  <xsl:call-template name="uri"/>
</xsl:template>

<xsl:template match="docs">
  <xsl:call-template name="uri"/>
</xsl:template>

<xsl:template match="developers-notes">
  <h3><a name="developers-notes">Developers' Notes</a></h3>
  <font size="2" color="blue">
    Last updated: <xsl:value-of select="@last-updated"/>
  </font>  

  <xsl:call-template name="uri"/>
</xsl:template>

<xsl:template name="uri">
  <xsl:choose>
    <xsl:when test="@uri">
      <xsl:copy-of select="document(@uri)"/>
    </xsl:when>
    <xsl:otherwise>
      <p>
        <xsl:copy-of select="*|text()"/>  
      </p>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
       
<xsl:template match="/document/revision"/>

<xsl:template name="documentation">
  <xsl:if test="documentation">
    <a><xsl:attribute name="name">documentation</xsl:attribute></a>
    <h3>Documentation</h3>
    <xsl:apply-templates select="documentation/docs"/>
  </xsl:if>
</xsl:template>

<xsl:template name="doctoc">

  <xsl:if test="documentation">
    <li><a href="#documentation">Documentation</a></li>
  </xsl:if>

  <xsl:if test="documentation/doctoc">
    <ul><xsl:apply-templates select="documentation/doctoc"/></ul>
  </xsl:if>
</xsl:template>

<xsl:template match="li">
  <li>

  <xsl:choose>
    <xsl:when test="@anchor">
      <a>
        <xsl:attribute name="href">#<xsl:value-of select="@anchor"/></xsl:attribute>
        <xsl:apply-templates/>
      </a>
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-templates/>
    </xsl:otherwise>
  </xsl:choose>

  </li>
</xsl:template>

<xsl:template match="ul">
  <ul>
    <xsl:apply-templates/>
  </ul>
</xsl:template>

<xsl:template name="examples">
  <xsl:choose>
    <xsl:when test="example">
      <xsl:apply-templates select="example"/>
    </xsl:when>
    <xsl:otherwise>
      <tr>
        <td><b>Examples</b></td>
        <td colspan="5">None</td>
      </tr>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="usage">
  <tr>
    <!-- not sure why it's 2 and not 1  :) -->
    <td><xsl:if test="position() = 2"><b>Examples</b></xsl:if></td>
    <xsl:if test="comment">
      <td colspan="5" bgcolor="#cccccc">
        <xsl:value-of select="comment"/>
        <xsl:text>&#160;</xsl:text>
      </td>
    </xsl:if>
  </tr>

  <tr>
    <td><xsl:text>&#160;</xsl:text></td>
    <td colspan="5">
      <xsl:for-each select="scriptlet">
        &lt;%<xsl:value-of select="."/>%&gt;<br/> 
      </xsl:for-each>
      <!-- A little trick here: the XML element <code> in our
           TLDoc matches the HTML element <code> on purpose so
           that we can just copy the whole node, including the
           opening and closing <code> tags.
           -->
      <p><pre><xsl:copy-of select="code"/></pre></p>
    </td>
  </tr> 
</xsl:template>

<xsl:template name="configuration">
  <a><xsl:attribute name="name">config</xsl:attribute></a>
  <h3>Configuration</h3>
  <p>Follow these steps to configure your web application with this tag
     library:</p>
  <ul>
    <li>Copy the tag library descriptor file to the /WEB-INF subdirectory
        of your web application.</li>
    <li>Copy the tag library JAR file to the /WEB-INF/lib subdirectory 
        of your web application.</li>
    <li>Add a &lt;taglib&gt; element to your web application deployment
        descriptor in /WEB-INF/web.xml like this:
<pre>
&lt;taglib&gt;
  &lt;taglib-uri&gt;<xsl:value-of select="uri"/>&lt;/taglib-uri&gt;
  &lt;taglib-location&gt;<xsl:value-of select="taglib-location"/>&lt;/taglib-location&gt;
&lt;/taglib&gt;
</pre>
    </li>
  </ul>
  <p>To use the tags from this library in your JSP pages, add the following
     directive at the top of each page: </p>
<pre>
&lt;%@ taglib uri=&quot;<xsl:value-of select="uri"/>&quot; prefix=&quot;<xsl:value-of select="prefix"/>&quot; %&gt;
</pre>
  <p>where &quot;<i><xsl:value-of select="prefix"/></i>&quot; is the tag
     name prefix you wish to use for tags from this library. You can change
     this value to any prefix you like.</p>
</xsl:template>

</xsl:stylesheet>

