<?xml version="1.0"?>
<!--
 This stylesheet documents TLD's.
 -->
<xsl:stylesheet version='1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:tld='http://java.sun.com/JSP/TagLibraryDescriptor' xmlns:tld11='http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd'>
    <xsl:output method="html" encoding="UTF-8"/>

    <!--<xsl:param name="cssfile" select="maven.css"/>-->
    <xsl:template match="/">
        <document>

          <properties>
            <title>Tag reference</title>
            <author email="fgiust AT users.sourceforge.net">Fabrizio Giustina</author>
          </properties>

          <body>


        <section>
			<xsl:attribute name="name">
            <xsl:choose>
				<xsl:when test="tld:taglib/tld:display-name">
					<xsl:value-of select="tld:taglib/tld:display-name"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="tld:taglib/tld:short-name|tld11:taglib/tld11:shortname"/>
				</xsl:otherwise>
			</xsl:choose>
			</xsl:attribute>

        <p>   <a name="top"></a>
            <xsl:value-of select="tld:taglib/tld:description|tld11:taglib/tld11:info"/>
             This is version <xsl:value-of select="tld:taglib/tld:tlib-version|tld11:taglib/tld11:tlibversion"/>.
        </p>


         <ul>
            <xsl:for-each select="//tld:tag|//tld11:tag">
            <li>    <!-- Showing only the first sentence -->
	            <a href="#{tld:name}{tld11:name}"><xsl:value-of select="tld:name|tld11:name"/></a>
	            <xsl:text>&#x20;</xsl:text>
    	        <xsl:if test="not(contains(tld:description,'.'))"> <xsl:value-of select="tld:description|tld:info"/></xsl:if>
                <xsl:value-of select="substring-before(tld:description,'.')"/>
    	        <xsl:value-of select="substring-before(tld:info,'.')"/>.
			</li>
            </xsl:for-each>
        </ul>
            <p> <em>Required attributes are marked with a <span style="color: red;">*</span>.</em> </p>
        </section>

        <xsl:apply-templates select="//tld:tag|//tld11:tag">
        </xsl:apply-templates>

    </body>
        </document>
</xsl:template>

<xsl:template match="tld:tag|tld11:tag">    
    <section name="{tld:name}{tld11:name}">

        <p><a name="{tld:name}{tld11:name}"></a> <xsl:value-of select="tld:description|tld11:info" /> </p>

        <p>Can contain: <xsl:value-of select="tld:body-content|tld11:bodycontent"/></p>
        <xsl:if test="tld:example">
        <subsection name="Example">
        <xsl:apply-templates select="tld:example"/>
        </subsection>
        </xsl:if>


        <xsl:if test="tld:variable|tld11:variable">
            <subsection name="Variables">
            <table>
                <thead>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Scope</th>
                    <th>Description</th>
                </thead>
                <xsl:apply-templates select="tld:variable|tld11:variable"/>
            </table>
            </subsection>
        </xsl:if>

        <subsection name="Attributes">
        <table>
        <xsl:choose>
            <xsl:when test="tld:attribute|tld11:attribute">
                <tr>
                    <th>Name</th>
                    <th>Description</th>
	                <xsl:if test="//tld:type"><th>Type</th></xsl:if>
               </tr>
                <xsl:apply-templates select="tld:attribute|tld11:attribute">
                    <!--<xsl:sort select="tld:description[ contains(., 'html pass through')]" order="ascending"/>-->
                    <xsl:sort select="tld:description[ contains( translate(.,'ACDEPRT','acdeprt'),'deprecated')]" order="ascending"/>
                    <xsl:sort select="tld:required|tld11:required" order="descending"/>
                    <xsl:sort select="tld:name|tld11:name"/>
                </xsl:apply-templates>
            </xsl:when>
            <xsl:otherwise><tr><td>This tag has no attributes.</td></tr></xsl:otherwise>
        </xsl:choose>
        </table>
            <span style="text-align: right;"><a href="#top">top</a></span>
        </subsection>

    </section>
</xsl:template>

<xsl:template match="tld:attribute|tld11:attribute">
    <tr>
        <xsl:if test="not('true'=tld:required|'true'=tld11:required)">
            <xsl:attribute name="class">required</xsl:attribute>
            <xsl:attribute name="title">Required</xsl:attribute>
        </xsl:if>

        <!-- comment -->
        <td><a name="{preceding-sibling::tld:name}_{tld:name}{tld11:name}"></a> <xsl:if test="not('true'=tld:required|'true'=tld11:required)"><span style="color: red;"> * </span></xsl:if>
            <xsl:choose>
                <xsl:when test="tld:description[ contains( translate(.,'ACDEPRT','acdeprt'),'deprecated')]">
                    <em class="deprecated"><xsl:value-of select="tld:name|tld11:name"/></em>
                </xsl:when>
                <xsl:otherwise><xsl:value-of select="tld:name|tld11:name"/></xsl:otherwise>
            </xsl:choose>
            <!-- comment -->
        </td>
	    <td><xsl:value-of select="tld:description|tld11:info"/></td>
        <xsl:if test="//tld:type">
            <td><code>
            <xsl:choose>
                <xsl:when test="starts-with(tld:type,'java.lang.')"><xsl:value-of select="substring-after(tld:type,'java.lang.')"/></xsl:when>
                <xsl:when test="tld:type"><xsl:value-of select="tld:type"/></xsl:when>
                <xsl:otherwise>String</xsl:otherwise>
            </xsl:choose>
            </code>
            </td>
        </xsl:if>
    </tr>
</xsl:template>


<xsl:template match="tld:example">
<source>
    <xsl:value-of select="."/>
</source>
</xsl:template>


<xsl:template match="tld:variable|tld11:variable">
    <tr >
        <td>
            <xsl:choose>
                <xsl:when test="tld:name-given"><xsl:value-of select="tld:name-given"/> (constant)</xsl:when>
                <xsl:otherwise>specified via <a href="#{preceding-sibling::tld:name}_{tld:name-from-attribute}"><xsl:value-of select="tld:name-from-attribute"/></a></xsl:otherwise>
            </xsl:choose>
        </td>
        <td><xsl:value-of select="tld:variable-class|tld11:variable-class"/></td>
        <td><xsl:value-of select="tld:scope|tld11:scope"/></td>
        <td><xsl:value-of select="tld:description|tld11:info"/></td>
    </tr>

</xsl:template>




</xsl:stylesheet>