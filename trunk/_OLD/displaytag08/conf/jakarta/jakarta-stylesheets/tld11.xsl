<?xml version="1.0" encoding="ISO-8859-1"?>
  <!-- 
    This XSL sheet converts Tag Library Documentation into a Tag Library 
    Descriptor. Originally authored by Craig McClanahan for the Jakarta Struts
    project, and used here without modification. 

    See the other XML documents in this distribution for more information on 
    how to format the Tag Library Documentation XML files.  Basically, you
    just write a TLD, leave out the DTD, and add whatever other XML elements
    you need for other purposes.  This XSL will ignore any elements that are not 
    part of the TLD proper.

    Scott Stirling
    sstirling@mediaone.net
    1/1/2001
  -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xalan="http://xml.apache.org/xslt"
  version="1.0">
  <!-- Output method and formatting -->
  <xsl:output
             method="xml"
             indent="yes"
             xalan:indent-amount="2"
     doctype-public="-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN"
     doctype-system="http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd"/>
   <xsl:strip-space elements="taglib tag attribute"/>
  <!-- Process an entire tag library -->
  <xsl:template match="taglib">
    <taglib>
      <xsl:if test="tlib-version">
        <tlibversion><xsl:value-of select="tlib-version"/></tlibversion>
      </xsl:if>
      <xsl:if test="jsp-version">
        <jspversion><xsl:value-of select="jsp-version"/></jspversion>
      </xsl:if>
      <xsl:if test="short-name">
        <shortname><xsl:value-of select="short-name"/></shortname>
      </xsl:if>
      <xsl:if test="uri">
        <uri><xsl:value-of select="uri"/></uri>
      </xsl:if>
      <xsl:apply-templates select="description"/>
      <xsl:apply-templates select="tagtoc/tag"/>
    </taglib>
  </xsl:template>

  <!-- Process an individual tag -->
  <xsl:template match="tag">
    <tag>
      <xsl:if test="name">
        <name><xsl:value-of select="name"/></name>
      </xsl:if>
      <xsl:if test="tag-class">
        <tagclass><xsl:value-of select="tag-class"/></tagclass>
      </xsl:if>
      <xsl:if test="tei-class">
        <teiclass><xsl:value-of select="tei-class"/></teiclass>
      </xsl:if>
      <xsl:if test="body-content">
        <bodycontent><xsl:value-of select="body-content"/></bodycontent>
      </xsl:if>
      <xsl:apply-templates select="attribute"/>
    </tag>
  </xsl:template>

  <!-- Process an individual tag attribute -->
  <xsl:template match="attribute">
    <attribute>
      <xsl:if test="name">
        <name><xsl:value-of select="name"/></name>
      </xsl:if>
      <xsl:if test="required">
        <required><xsl:value-of select="required"/></required>
      </xsl:if>
      <xsl:if test="rtexprvalue">
        <rtexprvalue><xsl:value-of select="rtexprvalue"/></rtexprvalue>
      </xsl:if>
    </attribute>
  </xsl:template>

  <xsl:template match="description">
    <info>
      <xsl:value-of select="."/>
    </info>
  </xsl:template>

  <!-- Skip irrelevant details -->
  <xsl:template match="properties"/>
  <xsl:template match="example"/>
  <xsl:template match="/document/revision"/>

</xsl:stylesheet>
