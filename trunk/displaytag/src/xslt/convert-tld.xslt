<?xml version="1.0"?>
<!--
 This stylesheet converts 1.2 TLD's to the 1.1 format.
 -->
<xsl:stylesheet version='1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:tld='http://java.sun.com/JSP/TagLibraryDescriptor' xmlns:tld11='http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd'>
    <xsl:output method="xml" indent="yes"/>
    <xsl:template match="//tld:taglib">
        <taglib>
          <tlibversion><xsl:value-of select="tld:tlib-version"/></tlibversion>
          <jspversion><xsl:value-of select="tld:jsp-version"/></jspversion>
          <shortname><xsl:value-of select="tld:short-name"/></shortname>
          <uri><xsl:value-of select="tld:uri"/></uri>
          <info><xsl:value-of select="tld:description"/></info>
           <xsl:apply-templates select="//tld:tag"/>
            
        </taglib>
    </xsl:template>
    
    <xsl:template match="tld:tag">


        <tag>
<!--  1.1   <!ELEMENT tag (name, tagclass, teiclass?, bodycontent?, info?, attribute*) >-->
<!--  1.2      <!ELEMENT tag (name, tag-class, tei-class?, body-content?, display-name?,
               small-icon?, large-icon?, description?, variable*, attribute*,
               example?) >
-->
            <name><xsl:value-of select="tld:name"/></name>
            <tagclass><xsl:value-of select="tld:tag-class"/></tagclass>
            <xsl:if test="tld:tei-class"><teiclass><xsl:value-of select="tld:tei-class"/></teiclass></xsl:if>
            <bodycontent><xsl:value-of select="tld:body-content"/></bodycontent>
            <info><xsl:value-of select="tld:description"/></info>
            <xsl:apply-templates select="tld:attribute"/>
        </tag>


    </xsl:template>

    <!-- 1.1   <!ELEMENT attribute (name, required? , rtexprvalue?) > -->
    <!-- 1.2    <!ELEMENT attribute (name, required? , rtexprvalue?, type?, description?) > -->
    <xsl:template match="tld:attribute">

        <attribute>
            <xsl:comment><xsl:value-of select="tld:description"/></xsl:comment>
            <name><xsl:value-of select="tld:name"/></name>
            <required><xsl:value-of select="tld:required"/></required>
            <rtexprvalue><xsl:value-of select="tld:rtexprvalue"/></rtexprvalue>
        </attribute>

    </xsl:template>

<!--    <xsl:template match="tld:type|tld:description|tld:variable|tld:small-icon|tld:large-icon|tld:display-name"/>-->

</xsl:stylesheet>