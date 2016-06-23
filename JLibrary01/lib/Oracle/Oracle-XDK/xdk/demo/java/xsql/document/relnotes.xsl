<?xml version="1.0" ?>
<!--
| $Author: kkarun $
| $Date: 20-apr-00.23:43:06 $
| $Source: /vobs/oracore3/demo/xdk/java/xsql/demo/document/relnotes.xsl.mkelem $
| $Revision: /main/0 $
+-->
<!DOCTYPE xsl:stylesheet [
<!ENTITY codefont "Andale Mono, Courier New">
<!ENTITY idcontents "contents">
]>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:output method="html" indent="yes"/>
<xsl:variable name="i">../images/</xsl:variable>

<xsl:preserve-space elements="programlisting"/>

<xsl:template match="/">
  <xsl:apply-templates select="article"/>
</xsl:template>

<xsl:template match="article">
  <html>
  <head>
    <link rel="stylesheet" type="text/css" href="relnotes.css"/>
    <style>
    .literal {font-family: monospace }
    </style>
    <title><xsl:apply-templates select="artheader/title"/></title>
  </head>
  <body>
    <xsl:apply-templates select="artheader"/>
    <xsl:call-template name="table-of-contents"/>
    <xsl:apply-templates select="artheader/following-sibling::*"/>
<!--  <xsl:apply-templates/> -->
  </body>
  </html>
</xsl:template>

<xsl:template match="artheader">
    <h1 style="color:#000088"><img src="{$i}line1.gif" width="600" height="15"/><br/><xsl:apply-templates select="title"/></h1>
    <h3><xsl:value-of select="@role"/></h3>
    <xsl:apply-templates select="author"/><br/>
    <xsl:value-of select="date"/>
</xsl:template>

<xsl:template match="author">
    <xsl:value-of  select="firstname"/>
    <xsl:text> </xsl:text>
    <xsl:value-of select="surname"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="affiliation/jobtitle"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="affiliation/orgname"/>
</xsl:template>

<xsl:template match="sect1">
<xsl:if test="@id">
<a name="{@id}"/>
</xsl:if>
<a name="{generate-id()}"><h2 style="color:#000088"><img src="{$i}line3.gif" width="300" height="11"/><br x=""/>
<xsl:apply-templates select="title"/></h2></a>
<xsl:apply-templates select="title[1]/following-sibling::*"/>
</xsl:template>

<xsl:template match="sect2">
<xsl:if test="@id">
<a name="{@id}"/>
</xsl:if>
<a name="{generate-id()}"><h3 style="color:#000066"><img src="{$i}line4.gif" width="200" height="9"/><br/>
<xsl:apply-templates select="title"/></h3></a>
<xsl:apply-templates select="title[1]/following-sibling::*"/>
</xsl:template>

<xsl:template match="sect3">
<xsl:if test="@id">
<a name="{@id}"/>
</xsl:if>
<a name="{generate-id()}"><h4 style="color:#000044"><img src="{$i}line4.gif" width="150" height="7"/><br/>
<xsl:apply-templates select="title"/></h4></a>
<xsl:apply-templates select="title[1]/following-sibling::*"/>
</xsl:template>

<xsl:template match="para">
    <p><xsl:apply-templates /></p>
</xsl:template>

<xsl:template match="ulink">
  <a href="{@url}"><xsl:apply-templates/></a>
</xsl:template>

<xsl:template match="link">
  <a href="#{@linkend}"><xsl:apply-templates/></a>
</xsl:template>

<xsl:template match="literal">
  <span class="literal"><xsl:apply-templates/></span>
</xsl:template>

<xsl:template match="sgmltag">
<span class="literal">&lt;<xsl:value-of select="."/>&gt;</span>
</xsl:template>

<xsl:template match="graphic">
  <table border="0" cellspacing="0" cellpadding="0"><tr><td><img src="{@fileref}"/></td></tr></table>
</xsl:template>

<xsl:template match="emphasis">
<i><xsl:apply-templates/></i>
</xsl:template>

<xsl:template match="replaceable">
<i><xsl:apply-templates/></i>
</xsl:template>

<!-- <pre><xsl:value-of select="."/></pre> -->

<xsl:template match="programlisting">
<pre><xsl:apply-templates/></pre>
</xsl:template>

<xsl:template name="table-of-contents">
<h2 style="color:#000090"><img src="{$i}line3.gif" width="300" height="11"/><br/>
Contents</h2>
  <ul>
  <xsl:for-each select="/article/sect1">
  <li><a href="#{generate-id()}"><xsl:apply-templates select="title"/></a></li>
  </xsl:for-each>
  </ul>
</xsl:template>

<xsl:template match="sectionref">
  <xsl:variable name="id" select="@id"/>
  <xsl:for-each select="//section[@id=$id]">
  <a href="#{@id}"><xsl:value-of select="title"/></a>
  <xsl:if test="not(position()=last())"><xsl:text>, </xsl:text></xsl:if>
  </xsl:for-each>
</xsl:template>

<xsl:template match="listitem">
  <li><xsl:apply-templates/></li>
</xsl:template>

<xsl:template match="note">
  <br/>
  <table border="1" cellspacing="0" cellpadding="4" >
   <tr><td valign="top" align="right" bgcolor="yellow"><img src="{$i}point.gif"/></td><td valign="top"><b><u>Note</u></b>:<xsl:apply-templates />
   </td></tr></table>
<br/>
</xsl:template>


<xsl:template match="itemizedlist">
  <ul>
  <xsl:apply-templates />
  </ul>
</xsl:template>

<xsl:template match="orderedlist">
  <ol>
  <xsl:apply-templates/>
  </ol>
</xsl:template>

</xsl:stylesheet>
