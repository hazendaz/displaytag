<!DOCTYPE xsl:stylesheet>
<xsl:stylesheet
      version="2.0"
      xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
      xmlns:fo="http://www.w3.org/1999/XSL/Format">
  <xsl:output method="xml" indent="yes"/>
  <xsl:template match="/">
    <fo:root>
      <fo:layout-master-set>
        <fo:simple-page-master master-name="letter-landscape" page-height="8.5in" page-width="11.0in">
            <fo:region-body region-name="xsl-region-body" margin="2in 0.5in 1in 0.5in"/>
            <fo:region-before region-name="xsl-region-before" extent="0.75in"/>
            <fo:region-after region-name="xsl-region-after" extent="0.75in"/>
            <fo:region-start/>
            <fo:region-end/>
        </fo:simple-page-master>
      </fo:layout-master-set>

      <fo:page-sequence master-reference="letter-landscape">
          <fo:static-content flow-name="xsl-region-before">
            <fo:block margin="0.5in 0.5in 0.1in 0.5in" padding-right="5pt" padding-top="5pt" text-align="right">
                <!--<fo:external-graphic src="OWNER_LOGO" content-height=".635in" />-->
            </fo:block>
            <fo:block margin="0.5in 0.5in 0.1in 0.5in" text-align="left" font-size="12pt" font-weight="bold" color="#003366">OWNER_NAME</fo:block>
          </fo:static-content>
          <fo:static-content flow-name="xsl-region-after">
                <fo:block margin-bottom="10pt" font-size="5" font-family="Helvetica" text-align="right" margin-right="0.5in" padding-right="5pt">Page <fo:page-number/></fo:block>
          </fo:static-content>

        <fo:flow flow-name="xsl-region-body">
          <fo:block hyphenate="true" font-size="5" font-family="Helvetica">
              <fo:table text-align="left">
                <fo:table-header>
                    <xsl:apply-templates select="//header"/>
                </fo:table-header>
                <fo:table-body>
                    <xsl:apply-templates select="//data"/>
                </fo:table-body>
              </fo:table>
          </fo:block>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>

    <xsl:template match="data">
        <xsl:apply-templates />
    </xsl:template>

    <!-- subgroup  -->
  <xsl:template match="subgroup">
      <xsl:variable name="grouping-column" select="@grouped-by"/>
      <xsl:comment>This is $grouping-column @grouped-by</xsl:comment>
      <xsl:variable name="grouping-value" select="(row[1]|subgroup[1]/row[1])/cell[position() = $grouping-column]"/>
      <fo:table-row >
          <xsl:for-each select="(row[1]|subgroup[1]/row[1])/cell[position() &lt; $grouping-column]">
              <fo:table-cell padding="6pt"> <fo:block/> </fo:table-cell>
          </xsl:for-each>
          <fo:table-cell padding="6pt"><fo:block font-weight="bold" >
              <!-- if the subgroup is not the outermost, italicize the label -->
              <xsl:if test="parent::subgroup[@grouped-by &gt; 0]"><xsl:attribute name="font-style">italic</xsl:attribute></xsl:if>
              <xsl:value-of select="$grouping-value"/></fo:block></fo:table-cell>
           <xsl:for-each select="(row[1]|subgroup[1]/row[1])/cell[position() &gt; $grouping-column]">
              <fo:table-cell padding="6pt"><fo:block/></fo:table-cell>
          </xsl:for-each>
      </fo:table-row>
      <!-- data values, including nested subgroups -->
      <xsl:apply-templates select="subgroup"/>
      <xsl:apply-templates select="row"/>
      <xsl:apply-templates select="subtotal"/>
      <!-- close the subgroup; show the totals -->
      <xsl:if test="subtotal">
            <fo:table-row>
                <xsl:choose>
                    <xsl:when test="$grouping-column = 0">
                        <!-- grand totals -->
                        <xsl:for-each select="subtotal//subtotal-cell">
                            <fo:table-cell padding="6pt" border-top="0.5pt solid black"><fo:block text-align="right" font-weight="bold"><xsl:value-of select="."/></fo:block></fo:table-cell>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:for-each select="subtotal//subtotal-cell[position() &lt; $grouping-column]"> <fo:table-cell><fo:block/></fo:table-cell> </xsl:for-each>
                        <fo:table-cell padding="6pt"><fo:block font-weight="bold" >
                            <!-- if the subgroup is not the outermost, italicize the label -->
                            <xsl:if test="parent::subgroup[@grouped-by &gt; 0]"><xsl:attribute name="font-style">italic</xsl:attribute></xsl:if>
                            <xsl:value-of select="$grouping-value"/> Total</fo:block></fo:table-cell>
                        <!-- When we are doing the grand total, the grouping column will be 0, but we have already written a total label in col 1; we can safely  -->
                        <xsl:for-each select="subtotal//subtotal-cell[position() &gt; $grouping-column]">
                            <!-- if the subgroup is not the outermost, italicize the total -->
                            <fo:table-cell padding="6pt"><fo:block text-align="right" font-weight="bold">
                            <xsl:if test="ancestor::subgroup[@grouped-by &gt; 1]"><xsl:attribute name="font-style">italic</xsl:attribute></xsl:if>
                            <xsl:value-of select="."/></fo:block></fo:table-cell>
                        </xsl:for-each>
                    </xsl:otherwise>
                </xsl:choose>
            </fo:table-row>
      </xsl:if>
  </xsl:template>


    <!-- An ordinary row -->
  <xsl:template match="row">
      <fo:table-row >
        <xsl:apply-templates select="cell"/>
      </fo:table-row>
  </xsl:template>

    <!-- an ordinary cell -->
  <xsl:template match="cell">
      <fo:table-cell padding="6pt"><fo:block>
          <xsl:if test="contains(@class, 'right') or @text-align='right'"><xsl:attribute name="text-align">right</xsl:attribute></xsl:if>
          <xsl:value-of select="."/></fo:block></fo:table-cell>
  </xsl:template>
    <!-- an ordinary cell which has been grouped -->
  <xsl:template match="cell[@grouped='true']">
      <fo:table-cell padding="6pt"><fo:block/></fo:table-cell>
  </xsl:template>





    <!-- header rows -->
  <xsl:template match="//header" >
      <fo:table-row> <xsl:apply-templates select="header-cell" /> </fo:table-row>
  </xsl:template>
  <!-- header cell -->
  <xsl:template match="//header-cell">
       <fo:table-cell padding="6pt" border-bottom="0.5pt solid black"> <fo:block  font-weight="bold"><xsl:value-of select="."/></fo:block> </fo:table-cell>
  </xsl:template>


</xsl:stylesheet>
