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

    Modified to support generation of jsp1.2 TLD
  -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xalan="http://xml.apache.org/xslt"
  version="1.0">
  <!-- Output method and formatting -->
  <xsl:output
             method="xml"
             indent="yes"
             xalan:indent-amount="2"/>
  <xsl:strip-space elements="taglib tag attribute"/>
  <!-- Process an entire tag library -->
  <xsl:template match="taglib">
    <taglib xmlns="http://java.sun.com/dtd/web-jsptaglibrary_1_2.dtd">
      <xsl:if test="tlib-version">
        <tlib-version><xsl:value-of select="tlib-version"/></tlib-version>
      </xsl:if>
      <xsl:if test="jsp-version">
        <jsp-version><xsl:value-of select="jsp-version"/></jsp-version>
      </xsl:if>
      <xsl:if test="short-name">
        <short-name><xsl:value-of select="shortname"/></short-name>
      </xsl:if>
      <xsl:if test="uri">
        <uri><xsl:value-of select="uri"/></uri>
      </xsl:if>
      <xsl:if test="display-name">
        <display-name><xsl:value-of select="display-name"/></display-name>
      </xsl:if>
      <xsl:if test="small-icon">
        <small-icon><xsl:value-of select="small-icon"/></small-icon>
      </xsl:if>
      <xsl:if test="large-icon">
        <large-icon><xsl:value-of select="large-icon"/></large-icon>
      </xsl:if>
      <xsl:apply-templates select="description"/>
      <xsl:apply-templates select="validator"/>
      <xsl:apply-templates select="listener"/>
      <xsl:apply-templates select="tagtoc/tag"/>
    </taglib>
  </xsl:template>

  <!-- Process the taglib validator -->
  <xsl:template match="validator">       
    <validator>
      <xsl:if test="validator-class">
        <validator-class><xsl:value-of select="validator-class"/></validator-class>
      </xsl:if>
      <xsl:apply-templates select="init-param"/>
      <xsl:apply-templates select="description"/>
    </validator>
  </xsl:template>

  <!-- Process the taglib validator init-param-->
  <xsl:template match="init-param"> 
    <init-param>
      <xsl:if test="param-name">
        <param-name><xsl:value-of select="param-name"/></param-name>
      </xsl:if>
      <xsl:if test="param-value"> 
        <param-value><xsl:value-of select="param-value"/></param-value>
      </xsl:if>
      <xsl:apply-templates select="description"/>
    </init-param>                          
  </xsl:template>

  <!-- Process the taglib listeners -->
  <xsl:template match="listener"> 
    <listener>
      <xsl:if test="listener-class">
        <listener-class><xsl:value-of select="listener-class"/></listener-class>
      </xsl:if>
    </listener>                          
  </xsl:template>

  <!-- Process an individual tag -->
  <xsl:template match="tag">
    <tag>
      <xsl:if test="name">
        <name><xsl:value-of select="name"/></name>
      </xsl:if>
      <xsl:if test="tag-class">
        <tag-class><xsl:value-of select="tag-class"/></tag-class>
      </xsl:if>
      <xsl:if test="tei-class">
        <tei-class><xsl:value-of select="tei-class"/></tei-class>
      </xsl:if>
      <xsl:if test="body-content">
        <body-content><xsl:value-of select="body-content"/></body-content>
      </xsl:if>
      <xsl:if test="display-name">
        <display-name><xsl:value-of select="display-name"/></display-name>
      </xsl:if>
      <xsl:if test="small-icon">
        <small-icon><xsl:value-of select="small-icon"/></small-icon>
      </xsl:if>
      <xsl:if test="large-icon">
        <large-icon><xsl:value-of select="large-icon"/></large-icon>
      </xsl:if>
      <xsl:apply-templates select="description"/>
      <xsl:apply-templates select="variable"/>
      <xsl:apply-templates select="attribute"/>
      <xsl:apply-templates select="example"/>
    </tag>
  </xsl:template>

  <!-- Process an individual tag variable -->
  <xsl:template match="variable">
    <variable>
      <xsl:if test="name-given">
        <name-given><xsl:value-of select="name-given"/></name-given>
      </xsl:if>
      <xsl:if test="name-from-attribute">
        <name-from-attribute><xsl:value-of select="name-from-attribute"/></name-from-attribute>
      </xsl:if>
      <xsl:if test="variable-class">
        <variable-class><xsl:value-of select="variable-class"/></variable-class>
      </xsl:if>
      <xsl:if test="declare">
        <declare><xsl:value-of select="declare"/></declare>
      </xsl:if>
      <xsl:if test="scope">    
        <scope><xsl:value-of select="scope"/></scope>            
      </xsl:if>
      <xsl:apply-templates select="description"/>
    </variable>
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
      <xsl:if test="type">
        <type><xsl:value-of select="type"/></type>
      </xsl:if>
      <xsl:apply-templates select="description"/>
    </attribute>
  </xsl:template>

<!-- For now the templates for description and example
     are commented out, the JSP1.2 DTD for the tld
     does not allow these elements to contain any
     markup and still be valid XML data. -->

  <!-- Generate the individual tag example
  <xsl:template match="example">
    <example>
      <xsl:for-each select="usage">
        <xsl:if test="comment">
          <xsl:value-of select="comment"/>
        </xsl:if>
        <xsl:text>
</xsl:text>
        <xsl:for-each select="scriptlet">
          &lt;%<xsl:value-of select="."/>%&gt;
        </xsl:for-each>
        <xsl:text>
</xsl:text>
        <xsl:if test="code">
          <xsl:value-of select="code"/>
        </xsl:if>
        <xsl:text>
</xsl:text>
      </xsl:for-each>
    </example>
  </xsl:template>
-->

  <!-- Generate the example for a tag
  <xsl:template match="example"/>
  <xsl:template match="description">
    <description>
      <xsl:copy-of select="*|text()"/>
    </description>
  </xsl:template>
-->

  <xsl:template match="example"/>
  <xsl:template match="description"/>

  <!-- Skip irrelevant details -->
  <xsl:template match="properties"/>

  <xsl:template match="/document/revision"/>

</xsl:stylesheet>
