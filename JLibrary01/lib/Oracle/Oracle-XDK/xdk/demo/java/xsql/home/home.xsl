<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<!--
 | Allow setting tab from outside
 +-->
<xsl:param name="tab"/>
<xsl:param name="lasttab"/>
<xsl:param name="sub"/>
<xsl:param name="thispage"/>

<!--
 | Figure out the id of the current tab.
 | If it's been set from outside to a non-null value
 | then use that, otherwise set it to the value of
 | the tab id specified in the "initial" attribute
 | on the tabset if that is specified. If not, 
 | default to the first tab in the set.
 +-->
<xsl:variable name="cur">
  <xsl:choose>
    <xsl:when test="$tab">
      <xsl:value-of select="$tab"/>
    </xsl:when>
    <xsl:when test="$lasttab">
      <xsl:value-of select="$lasttab"/>
    </xsl:when>
    <xsl:when test="/site/tabs/@initial"> 
      <xsl:value-of select="/site/tabs/@initial"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="/site/tabs/tab[1]/@id"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:variable>

<!--
 | Figure out the id of the current subtab.
 | If it's been set from outside to a non-null value
 | then use that, otherwise set it to the value of
 | the sub-tab id specified in the "initial" attribute
 | on the subtab tabset if that is specified. If not, 
 | default to the first tab in the sub-tabset.
 +-->
<xsl:variable name="cursub">
  <xsl:choose>
    <xsl:when test="$sub!=''">
      <xsl:value-of select="$sub"/>
    </xsl:when>
    <xsl:when test="/site/tabs/tab[@id=$cur]/subtabs/@initial"> 
      <xsl:value-of select="/site/tabs/tab[@id=$cur]/subtabs/@initial"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:value-of select="/site/tabs/tab[@id=$cur]/subtabs/tab[1]/@id"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:variable>

<!--
 | Prefix for all images. Defaults to null, meaning
 | that images will be found in the current directory.
 +-->
<xsl:variable name="i"></xsl:variable>

<xsl:output method="html" indent="no"/>

<xsl:template match="/">
    <html>
      <head>
         <link rel="stylesheet" type="text/css" href="home.css"/>
      </head>
      <script>
        <xsl:comment>
        function g(i) { parent.contents.location = "homecontent.xsql?tab=" + i; }
        function g2(i) { parent.contents.location = i; }
        </xsl:comment>
      </script>
      <body bdgcolor="#FFFFFF" leftmargin="0" topmargin="0">
      <!--boxBody-->
      <xsl:apply-templates/>
      </body>
    </html>
</xsl:template>

<xsl:template match="site">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td align="LEFT" rowspan="2"><a href="../index.html" target="_parent"><img src="{$i}portal_logo_banner.gif" width="449"
height="69" border="0" alt="Oracle XSQL Pages &amp; XSQL Servlet"/></a></td>
<td align="RIGHT" valign="top" colspan="25">
<xsl:apply-templates select="navbar"/>
</td>
</tr>

<tr>
<td width="98%">&#160;</td>
<td valign="bottom" align="right">
<table border="0" cellpadding="0" cellspacing="0">
<tr>
<td align="right">
<xsl:apply-templates select="tabs"/>
</td>
</tr>
</table>
</td>
</tr>
</table>
<!-- <xsl:apply-templates select="tabs/tab[@id=$cur]/subtabs"/> -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td align="right" valign="top" width="49" height="21"
bgcolor="#336699"><img src="{$i}left_end_bottom.gif" height="21"
width="49" /></td>
<td align="right" valign="top" bgcolor="#FFFFFF" height="21"
width="100%"><img src="{$i}bottom_middle.gif" height="6"
width="100%" /></td>
<td align="LEFT" valign="top" height="21" width="5"
bgcolor="#FFFFFF"><img src="{$i}right_end_bottom.gif" height="7"
width="5" /></td>
</tr>
</table>
</xsl:template>

<xsl:template match="tab">
  <xsl:choose>
    <xsl:when test="@id=$cur">
      <xsl:call-template name="current-tab"/>
    </xsl:when>
    <xsl:otherwise> 
      <xsl:call-template name="non-current-tab"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="non-current-tab">
<!--Tab1-->
<td bgcolor="#B6B687" width="1%" align="LEFT" valign="TOP"><img
src="{$i}tab_open.gif" height="21" width="13" border="0" /></td>
<td width="1%" bgcolor="#B6B687"><a href="{$thispage}?tab={@id}">
<xsl:attribute name="onclick">
  <xsl:choose>
    <xsl:when test="@url">
      <xsl:text>g2("</xsl:text>
      <xsl:value-of select="@url"/>
      <xsl:text>")</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>g("</xsl:text>
      <xsl:value-of select="@id"/>
      <xsl:text>")</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:attribute>
<font
size="-1" face="Arial" color="#000000"><xsl:value-of select="@name"/></font></a></td>
<td bgcolor="#B6B687" width="1%" align="RIGHT" valign="TOP"><img
src="{$i}tab_close.gif" height="21" width="10" border="0" /></td>
<!--/Tab1-->
</xsl:template>

<xsl:template name="current-tab">
<!--cTab1--><TD bgcolor="#336699" width="1%" align="LEFT" valign="TOP"><IMG SRC="{$i}ctab_open.gif" height="21" width="18" border="0"/></TD>
<TD width="1%" bgcolor="#336699"><b>
<xsl:attribute name="onclick">
  <xsl:choose>
    <xsl:when test="@url">
      <xsl:text>g2("</xsl:text>
      <xsl:value-of select="@url"/>
      <xsl:text>")</xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>g("</xsl:text>
      <xsl:value-of select="@id"/>
      <xsl:text>")</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
</xsl:attribute>
<font size="-1" face="Arial" color="#FFFFFF"><xsl:value-of select="@name"/></font></b></TD>
<TD bgcolor="#336699" width="1%" align="RIGHT" valign="TOP"><IMG SRC="{$i}ctab_close.gif" height="21" width="12" border="0"/></TD><!--/cTab1-->
</xsl:template>

<xsl:template match="navitem">
<td valign="TOP" align="CENTER" width="1%" colspan="1"><a
href="{@href}"><img src="{$i}{@image}" alt="{@name}"
border="0" width="32" height="32" /></a><br />
<font color="brown" face="verdana"
size="1">&#160;<xsl:value-of select="@name"/>&#160;</font></td>
</xsl:template>

<xsl:template match="tabs">
  <xsl:apply-templates select="tab"/>
</xsl:template>

<xsl:template match="navbar">
<!--NavBar-->
<table width="1" cellpadding="0" cellspacing="0" border="0">
<tr>
<xsl:apply-templates select="navitem"/>
</tr>
</table>
<!--/NavBar-->
</xsl:template>

<xsl:template match="subtabs/subtab">
  <xsl:choose>
    <xsl:when test="@id=$cursub">
      <xsl:call-template name="current-subtab"/>
    </xsl:when>
    <xsl:otherwise> 
      <xsl:call-template name="non-current-subtab"/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="current-subtab">
<xsl:comment>subcTab</xsl:comment>
<td width="1%" bgcolor="#336699">
  <font face="Arial" color="white">
    <b>
      <xsl:text>&#160;</xsl:text>
      <font size="-1" face="Arial">
        <xsl:value-of select="@name"/>
      </font>
      <xsl:text>&#160;</xsl:text>
    </b>
  </font>
</td>
<td width="1%" bgcolor="#336699"><img src="{$i}subtab.gif" /></td>
<xsl:comment>/subcTab</xsl:comment>
</xsl:template>

<xsl:template name="non-current-subtab">
<xsl:comment>subTab</xsl:comment>
<td width="1%" bgcolor="#336699">&#160;<b><a
href="{$thispage}.xsql?tab={$cur}&amp;sub={@id}"><font size="-1" face="Arial"
color="#CCCC99"><xsl:value-of select="@name"/></font></a></b>&#160;</td>
<td width="1%" bgcolor="#336699"><img src="{$i}subtab.gif" /></td>
<xsl:comment>/subTab</xsl:comment>
</xsl:template>

<xsl:template match="subtabs">
<xsl:comment>subtabs</xsl:comment>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td align="left" valign="top" width="1%" bgcolor="#336699"><img
src="{$i}top_left.gif" width="4" height="25" /></td>
<td align="left" valign="top" width="5%" bgcolor="#336699">&#160;</td>
<xsl:apply-templates select="tab"/>
<td align="left" valign="center" bgcolor="#336699" height="25"
width="94%">&#160;</td>
</tr>
</table>
<xsl:comment>/subtabs</xsl:comment>
</xsl:template>

<xsl:template match="category">
<tr>
<td width="1%" valign="top" align="left" bgcolor="#6699CC"><img
src="{$i}blue_upper_left.gif" /></td>
<td width="98%" align="left" valign="center" bgcolor="#6699CC">
&#160;&#160;<font color="white" face="Arial"><b><xsl:value-of select="@name"/></b></font></td>
<td width="1%" align="right" valign="center" bgcolor="#6699CC">
&#160;&#160;&#160;</td>
</tr>
<tr>
<td bgcolor="#FFFFFF" colspan="2">
<ul>
<xsl:apply-templates select="item"/>
</ul>
</td>
</tr>
</xsl:template>

<xsl:template match="category/item">
<li><a href="{@href}"><xsl:value-of select="@name"/></a></li>
</xsl:template>

</xsl:stylesheet>
