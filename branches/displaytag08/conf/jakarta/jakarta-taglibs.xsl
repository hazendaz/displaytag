<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Content Stylesheet for Taglibs Documentation -->
<!-- $Id$ -->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  version="1.0">

  <!-- Output method -->
  <xsl:output method="html" indent="no"/>

  <!-- Defined variables -->
  <xsl:variable name="body-bg"   select="'#ffffff'"/>
  <xsl:variable name="body-fg"   select="'#000000'"/>
  <xsl:variable name="body-link" select="'#023264'"/>
  <xsl:variable name="banner-bg" select="'#023264'"/>
  <xsl:variable name="banner-fg" select="'#ffffff'"/>
  <xsl:variable name="docDir">http://jakarta.apache.org/taglibs/doc</xsl:variable>

  <xsl:param name="prefix"></xsl:param> 
  <!-- Process an entire document into an HTML page -->
  <xsl:template match="document">
    <xsl:variable name="project"
                select="document('../../xml/display.xml')/project"/>

    <html>
    <head>
    <meta name="author" content="{properties/author/.}"/>
    <!-- <link rel="stylesheet" type="text/css" href="default.css"/> -->
    <xsl:choose>
      <xsl:when test="properties/title">
        <title><xsl:value-of select="properties/title"/></title>
      </xsl:when>
      <xsl:when test="body/title">
        <title><xsl:value-of select="body/title"/></title>
      </xsl:when>
      <xsl:otherwise>
        <title>Jakarta-Taglibs Project</title>
      </xsl:otherwise>
    </xsl:choose>
    </head>

    <body bgcolor="{$body-bg}" text="{$body-fg}" link="{$body-link}"
          alink="{$body-link}" vlink="{$body-link}">

    <table border="0" width="100%" cellspacing="5">

      <tr><td colspan="2">
        <a href="http://jakarta.apache.org">
          <img src="{$prefix}images/jakarta-logo.gif" align="left" border="0"/>
        </a>
        <a href="http://jakarta.apache.org/taglibs/index.html">
          <img src="{$prefix}images/taglibs.gif" align="right" border="0"/>
        </a>
      </td></tr>

      <tr><td colspan="2">
        <hr/>
      </td></tr>

      <tr>
        <td width="120" valign="top">
          <xsl:apply-templates select="$project"/>
        </td>

        <td valign="top">
          <xsl:apply-templates select="body"/>
        </td>
      </tr>

      <tr><td colspan="2">
        <hr/>
      </td></tr>

      <tr><td colspan="2">
        <div align="center"><font color="{$body-link}" size="-1"><em>
        Copyright &#169; 2000, Apache Software Foundation
        </em></font></div>
      </td></tr>

    </table>
    </body>
    </html>

  </xsl:template>

  <!-- Process a menu for the navigation bar -->
  <xsl:template match="menu">
    <table border="0" cellspacing="5">
      <tr>
        <th colspan="2" align="left">
          <font color="{body-link}"><strong>
            <xsl:value-of select="@name"/>
          </strong></font>
        </th>
      </tr>
      <xsl:choose>
        <xsl:when test="@local='true'">
          <xsl:apply-templates select="item">
            <xsl:with-param name="urlprefix" select="$prefix"/>
          </xsl:apply-templates>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates select="item"/>
        </xsl:otherwise>
      </xsl:choose>
    </table>
  </xsl:template>

  <!-- Process a menu item for the navigation bar -->
  <xsl:template match="item">
    <xsl:param name="urlprefix"></xsl:param>
    <tr>
      <td align="center" width="15"></td>
      <td>
        <font size="-1">
        <xsl:variable name="href">
          <xsl:value-of select="@href"/>
        </xsl:variable>
        <a href="{$urlprefix}{$href}"><xsl:value-of select="@name"/></a>
        </font>
      </td>
    </tr>
  </xsl:template>

  <!-- Process a documentation section -->
  <xsl:template match="section">
    <xsl:choose>
      <xsl:when test="@href">
        <xsl:variable name="href">
          <xsl:value-of select="@href"/>
        </xsl:variable>
        <a name="{$href}"></a>
      </xsl:when>
    </xsl:choose>
    <table border="0" cellspacing="5" cellpadding="5" width="100%">
      <tr><td bgcolor="{$banner-bg}">
        <font color="{$banner-fg}" face="arial,helvetica,sanserif" size="+1">
          <strong><xsl:value-of select="@name"/></strong>
        </font>
      </td></tr>
      <tr><td>
          <xsl:apply-templates/>
      </td></tr>
    </table>
  </xsl:template>

  <!-- Process a tag library section -->
  <xsl:template match="taglib">
    <table border="0" cellspacing="5" cellpadding="5" width="100%">
      <tr><td bgcolor="{$banner-bg}">
        <font color="{$banner-fg}" face="arial,helvetica,sanserif" size="+1">
          <strong><xsl:value-of select="display-name"/></strong>
        </font>
      </td></tr>
      <tr><td>
        <blockquote>
          <xsl:apply-templates select="info"/>
        </blockquote>
      </td></tr>
      <tr><td>
        <blockquote>
          <table border="1" cellspacing="2" cellpadding="2">
            <tr>
              <th width="15%">Tag Name</th>
              <th>Description</th>
            </tr>
            <xsl:for-each select="tag">
              <tr>
                <td align="center">
                  <xsl:variable name="name">
                    <xsl:value-of select="name"/>
                  </xsl:variable>
                  <a href="#{$name}"><xsl:value-of select="name"/></a>
                </td>
                <td>
                  <xsl:value-of select="summary"/>
                </td>
              </tr>
            </xsl:for-each>
          </table>
        </blockquote>
      </td></tr>
    </table>
    <xsl:apply-templates select="tag"/>
  </xsl:template>

  <!-- Process an individual tag -->
  <xsl:template match="tag">
    <xsl:variable name="name">
      <xsl:value-of select="name"/>
    </xsl:variable>
    <a name="{$name}"></a>
    <table border="0" cellspacing="2" cellpadding="2">
      <tr><td bgcolor="{$banner-bg}">
        <font color="{$banner-fg}" face="arial,helvetica,sanserif">
          <strong><xsl:value-of select="name"/></strong> -
          <xsl:value-of select="summary"/>
        </font>
      </td></tr>
      <tr><td>
        <blockquote>
          <xsl:apply-templates select="info"/>
        </blockquote>
      </td></tr>
      <tr><td>
        <blockquote>
          <table border="1" cellspacing="2" cellpadding="2">
            <tr>
              <th width="15%">Attribute Name</th>
              <th>Description</th>
            </tr>
            <xsl:for-each select="attribute">
              <tr>
                <td align="center">
                  <xsl:value-of select="name"/>
                </td>
                <td>
                  <xsl:apply-templates select="info"/>
                  <xsl:variable name="required">
                    <xsl:value-of select="required"/>
                  </xsl:variable>
                  <xsl:if test="required='true'">
                    [Required]
                  </xsl:if>
                  <xsl:if test="rtexprvalue='true'">
                    [RT Expr]
                  </xsl:if>
                </td>
              </tr>
            </xsl:for-each>
          </table>
        </blockquote>
      </td></tr>
    </table>
  </xsl:template>

  <!-- Process an individual paragraph -->
  <xsl:template match="p">
    <p><xsl:apply-templates/><br/></p>
  </xsl:template>

  <!-- Process a task list section -->
  <xsl:template match="task-list">
    <xsl:choose>
      <xsl:when test="@href">
        <xsl:variable name="href">
          <xsl:value-of select="@href"/>
        </xsl:variable>
        <a name="{$href}"></a>
      </xsl:when>
    </xsl:choose>
    <table border="0" cellspacing="5" cellpadding="5" width="100%">
      <tr><td bgcolor="{$banner-bg}">
        <font color="{$banner-fg}" face="arial,helvetica,sanserif" size="+1">
          <xsl:value-of select="@name"/>
        </font>
      </td></tr>
      <tr><td>
        <xsl:apply-templates select="info"/>
      </td></tr>
      <tr><td>
        <blockquote>
          <table border="1" cellspacing="5" cellpadding="5" width="100%">
            <tr>
              <th width="75%">Description</th>
              <th width="25%">Volunteer</th>
            </tr>
            <xsl:apply-templates select="task"/>
          </table>
        </blockquote>
      </td></tr>
    </table>
  </xsl:template>

  <!-- Process an individual task (in a TODO list) -->
  <xsl:template match="task">
    <tr>
      <td>
        <xsl:choose>
          <xsl:when test="@name">
            <em><xsl:value-of select="@name"/></em>.
          </xsl:when>
        </xsl:choose>
        <xsl:value-of select="info"/>
      </td>
      <td><xsl:value-of select="assigned"/></td>
    </tr>
  </xsl:template>

  <xsl:template match="news">
    <table cellpadding="2">
      <xsl:apply-templates/>
    </table>
  </xsl:template>

  <xsl:template match="newsitem">
    <tr>
      <td bgcolor="#CCCCCC">
         <b><font size="-1"><xsl:value-of select="@date"/></font></b>
      </td>                                                              
      <td bgcolor="#CCCCCC"> 
         <xsl:if test="@libname">
           <font size="-1"><xsl:call-template name="libURL"/></font>
         </xsl:if>
         <font size="-1"><xsl:apply-templates/></font></td>
    </tr>
  </xsl:template>

  <xsl:template name="libURL">
    <xsl:choose>

      <xsl:when test="@libname = 'DBTags'">
        <xsl:call-template name="libURLTemplate">
          <xsl:with-param name="libname">dbtags</xsl:with-param>
        </xsl:call-template>
      </xsl:when>

      <xsl:when test="@libname = 'UltraDev'">
        <xsl:call-template name="libURLTemplate">
          <xsl:with-param name="libname">ultradev4</xsl:with-param>
        </xsl:call-template>
      </xsl:when>

      <xsl:when test="@libname = 'Log'">
        <xsl:call-template name="libURLTemplate">
          <xsl:with-param name="libname">log</xsl:with-param>
        </xsl:call-template>
      </xsl:when>

      <xsl:when test="@libname = 'IO'">
        <xsl:call-template name="libURLTemplate">
          <xsl:with-param name="libname">io</xsl:with-param>
        </xsl:call-template>
      </xsl:when>

      <xsl:when test="@libname = 'XTags'">
        <xsl:call-template name="libURLTemplate">
          <xsl:with-param name="libname">xtags</xsl:with-param>
        </xsl:call-template>
      </xsl:when>

      <xsl:when test="@libname = 'Taglibs'">
        [Taglibs]
      </xsl:when>

      <xsl:otherwise>
        <xsl:call-template name="libURLTemplate"/>
      </xsl:otherwise>

    </xsl:choose>
  </xsl:template>

  <xsl:template name="libURLTemplate">
    <xsl:param name="libname" select="@libname"/>
    [<a href="{$docDir}/{$libname}-doc/intro.html"><xsl:value-of select="@libname"/></a>]
  </xsl:template>

  <xsl:template match="tagindex">
    <font size="-1">
    <xsl:if test="taginfo[@state='released']">
      <xsl:call-template name="released_tags"/>
    </xsl:if>
    <xsl:if test="taginfo[@state='beta']">
      <xsl:call-template name="beta_tags"/>
    </xsl:if>
    <xsl:if test="taginfo[@state='develop']">
      <xsl:call-template name="develop_tags"/>
    </xsl:if>
    <xsl:if test="taginfo[@state='unsupported']">
      <xsl:call-template name="unsupported_tags"/>
    </xsl:if>
    </font>
  </xsl:template>

  <xsl:template name="develop_tags">
    <font size="+1"><u>Pre-Release Tags</u></font>
    <dl>
      <xsl:apply-templates select="taginfo[@state='develop']">
        <xsl:sort select="@name"/>
      </xsl:apply-templates>
    </dl>
  </xsl:template>
  
  <xsl:template name="released_tags">
    <font size="+1"><u>Released Tags</u></font>
    <dl>
      <xsl:apply-templates select="taginfo[@state='released']">
        <xsl:sort select="@name"/>
      </xsl:apply-templates>
    </dl>
  </xsl:template>  

  <xsl:template name="beta_tags">
    <font size="+1"><u>Beta Tags</u></font>
    <dl>
      <xsl:apply-templates select="taginfo[@state='beta']">
        <xsl:sort select="@name"/>
      </xsl:apply-templates>
    </dl>
  </xsl:template> 

  <xsl:template name="unsupported_tags">
    <font size="+1"><u>Unsupported Tags</u></font>
    <dl>
      <xsl:apply-templates select="taginfo[@state='unsupported']">
        <xsl:sort select="@name"/>
      </xsl:apply-templates>
    </dl>
  </xsl:template> 

  <xsl:template match="taginfo">
    <dt><b><xsl:value-of select="@name"/> Taglib</b></dt>
    <dd><xsl:apply-templates/></dd>
  </xsl:template>

  <xsl:template match="ctlxindex">
    <font size="-1">
    <xsl:if test="ctlxinfo[@state='released']">
      <xsl:call-template name="released_ctlx"/>
    </xsl:if>
    <xsl:if test="ctlxinfo[@state='beta']">
      <xsl:call-template name="beta_ctlx"/>
    </xsl:if>
    <xsl:if test="ctlxinfo[@state='develop']">
      <xsl:call-template name="develop_ctlx"/>
    </xsl:if>
    <xsl:if test="ctlxinfo[@state='unsupported']">
      <xsl:call-template name="unsupported_ctlx"/>
    </xsl:if>
    </font>
  </xsl:template>

  <xsl:template name="develop_ctlx">
    <font size="+1"><u>Pre-Release CTLX</u></font>
    <dl>
      <xsl:apply-templates select="ctlxinfo[@state='develop']">
        <xsl:sort select="@name"/>
      </xsl:apply-templates>
    </dl>
  </xsl:template>
 
  <xsl:template name="released_ctlx">
    <font size="+1"><u>Released CTLX</u></font>
    <dl>
      <xsl:apply-templates select="ctlxinfo[@state='released']">
        <xsl:sort select="@name"/>
      </xsl:apply-templates>
    </dl>
  </xsl:template>

  <xsl:template name="beta_ctlx">
    <font size="+1"><u>Beta CTLX</u></font>
    <dl>
      <xsl:apply-templates select="ctlxinfo[@state='beta']">
        <xsl:sort select="@name"/>
      </xsl:apply-templates>
    </dl>
  </xsl:template>

  <xsl:template name="unsupported_ctlx">
    <font size="+1"><u>Unsupported CTLX</u></font>
    <dl>
      <xsl:apply-templates select="ctlxinfo[@state='unsupported']">
        <xsl:sort select="@name"/>
      </xsl:apply-templates>
    </dl>
  </xsl:template>

  <xsl:template match="ctlxinfo">
    <dt><b><xsl:value-of select="@name"/> CTLX</b></dt>
    <dd><xsl:apply-templates/></dd>
  </xsl:template>

  <!-- Process everything else by just passing it through -->
  <xsl:template match="*|@*">
    <xsl:copy>
      <xsl:apply-templates select="@*|*|text()"/>
    </xsl:copy>
  </xsl:template>

</xsl:stylesheet>
