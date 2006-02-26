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
    
    - Sometimes the <shortname> value of the TLD is used as the taglib name or 
      the prefix for various minutiae, other times the <info> line is used.  
      Depends on the context, as you can see below.
 -->

<xsl:template match="/document">
  <xsl:apply-templates select="properties"/>
  <body bgcolor="white">
    <center>
      <h1> 
        Jakarta Project: <xsl:value-of select="/document/taglib/display-name"/>
        Revision History
      </h1>
    </center>   
    <xsl:apply-templates select="revision"/>
  </body>
</xsl:template>

<xsl:template match="properties">
  <head>
    <meta content="{author}" name="author"/>
    <xsl:choose>
      <xsl:when test="title">
        <title><xsl:value-of select="title"/></title>
      </xsl:when>
      <xsl:otherwise>
        <title>Jakarta-Taglibs: <xsl:value-of select="/document/taglib/display-name"/> Revision History</title>
      </xsl:otherwise>
    </xsl:choose>
  </head>
</xsl:template>
  
<xsl:template match="revision">
  <h3><xsl:value-of select="@date"/> - <xsl:value-of select="@release"/></h3>
  <p><xsl:value-of select="description"/></p>
  <xsl:apply-templates select="section"/>
</xsl:template>

<xsl:template match="section">
  <p><b><xsl:value-of select="@name"/></b>
    <ul>
      <xsl:apply-templates select="item"/>
    </ul>
  </p>
</xsl:template>

<xsl:template match="item">
  <li><xsl:copy-of select="*|text()"/></li>
</xsl:template>

<xsl:template match="/document/taglib"/>

</xsl:stylesheet>
