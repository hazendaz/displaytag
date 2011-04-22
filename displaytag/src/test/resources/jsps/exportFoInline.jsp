<jsp:root version="1.2" xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:display="urn:jsptld:http://displaytag.sf.net">
    <jsp:text> <![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> ]]> </jsp:text>
    <jsp:directive.page contentType="text/html; charset=UTF8"/>
    <html xmlns="http://www.w3.org/1999/xhtml" lang="en">
        <head>
            <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
            <title>Displaytag unit test</title>
        </head>
        <body>
            <jsp:scriptlet> <![CDATA[
                java.util.List testData = new java.util.ArrayList();
                testData.add(new org.displaytag.test.KnownValue());
                testData.add(new org.displaytag.test.KnownValue());
                testData.add(new org.displaytag.test.KnownValue());
                request.setAttribute( "test", testData);
            ]]> </jsp:scriptlet>
			<display:table name="requestScope.test" id="table" pagesize="1">
                <display:setProperty name="export.xml.class">org.displaytag.export.XmlTotalsWriter</display:setProperty>
                <display:setProperty name="export.pdf.fo.stylesheetbody"><![CDATA[
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

            </fo:block>
            <fo:block margin="0.5in 0.5in 0.1in 0.5in" text-align="left" font-size="12pt" font-weight="bold" color="#003366">This is an inline stylesheet</fo:block>
          </fo:static-content>
          <fo:static-content flow-name="xsl-region-after">
                <fo:block margin-bottom="10pt" text-align="right" margin-right="0.5in" padding-right="5pt">Page <fo:page-number/></fo:block>
          </fo:static-content>

        <fo:flow flow-name="xsl-region-body">
          <fo:block hyphenate="true" font-size="7" font-family="Helvetica">
              <fo:table text-align="left">
                <fo:table-header>
                    <xsl:apply-templates select="//header"/>
                </fo:table-header>
                <fo:table-body>
                    <xsl:apply-templates select="//row"/>
                </fo:table-body>
              </fo:table>
          </fo:block>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>

    <!-- ordinary rows -->
  <xsl:template match="//row">
      <fo:table-row > <xsl:apply-templates /> </fo:table-row>
  </xsl:template>

  <xsl:template match="//cell">
       <fo:table-cell padding="6pt"><fo:block ><xsl:value-of select="."/></fo:block></fo:table-cell>
  </xsl:template>

    <!-- header rows -->
  <xsl:template match="//header" >
      <fo:table-row> <xsl:apply-templates /> </fo:table-row>
  </xsl:template>

  <xsl:template match="//header-cell">
       <fo:table-cell padding="6pt" border-bottom="0.5pt solid black"> <fo:block  font-weight="bold"><xsl:value-of select="."/></fo:block> </fo:table-cell>
  </xsl:template>

</xsl:stylesheet>
                ]]>
                </display:setProperty>
				<display:column title="row num"><jsp:expression>table_rowNum</jsp:expression></display:column>
				<display:column title="bee" property="bee"/>
			</display:table>
        </body>
    </html>
</jsp:root>