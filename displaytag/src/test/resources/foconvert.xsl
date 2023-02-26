<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
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
                <fo:external-graphic src="http://localhost:8080/backstop/static/css/omega/print_logo.png"/>
            </fo:block>
            <fo:block margin="0.5in 0.5in 0.1in 0.5in" text-align="left" font-size="12pt" font-weight="bold" color="#003366">Omega GL Income Statement</fo:block>
          </fo:static-content>
          <fo:static-content flow-name="xsl-region-after">
                <fo:block margin-bottom="10pt" text-align="right" margin-right="0.5in" padding-right="5pt">Page <fo:page-number/></fo:block>
          </fo:static-content>

        <fo:flow flow-name="xsl-region-body">
          <fo:block hyphenate="true" font-size="7" font-family="Helvetica">
              <fo:table text-align="left">
                <fo:table-header>
                    <xsl:apply-templates select="//row[1]"/>
                </fo:table-header>
                <fo:table-body>
                    <xsl:apply-templates select="//row[ position() > 1]"/>
                </fo:table-body>
              </fo:table>
          </fo:block>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>

    <!-- ordinary rows -->
  <xsl:template match="//row[position() > 1]">
      <fo:table-row > <xsl:apply-templates /> </fo:table-row>
  </xsl:template>

  <xsl:template match="//row[position() > 1]/column">
       <fo:table-cell padding="6pt"><fo:block ><xsl:value-of select="."/></fo:block></fo:table-cell>
  </xsl:template>

    <!-- header rows -->
  <xsl:template match="//row[1]" >
      <fo:table-row> <xsl:apply-templates /> </fo:table-row>
  </xsl:template>

  <xsl:template match="//row[1]/column">
       <fo:table-cell padding="6pt" border-bottom="0.5pt solid black"> <fo:block  font-weight="bold"><xsl:value-of select="."/></fo:block> </fo:table-cell>
  </xsl:template>


</xsl:stylesheet>
