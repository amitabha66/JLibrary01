<?xml version="1.0" ?>
<!--
| $Author: kkarun $
| $Date: 20-apr-00.23:43:51 $
| $Source: /vobs/oracore3/demo/xdk/java/xsql/demo/document/xml99.xsl.mkelem $
| $Revision: /main/0 $
+-->
<!DOCTYPE xsl:stylesheet [
<!ENTITY codefont "Andale Mono, Courier New">
<!ENTITY idcontents "contents">
]>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:output method="html" indent="yes"/>

<xsl:template match="/">
  <xsl:apply-templates select="Paper"/>
</xsl:template>

<xsl:template match="Paper">
  <html>
  <head>
    <link rel="stylesheet" type="text/css" href="xml99.css"/>
    <style>
    .literal {font-family: monospace }
    </style>
    <title><xsl:value-of select="Title"/></title>
  </head>
  <body>
    <h1><img src="line1.gif" width="600" height="15"/><br/><xsl:value-of select="Title"/></h1>
    <xsl:apply-templates select="AuthorInfo"/>
    <xsl:call-template name="table-of-contents"/>
    <xsl:for-each select="Sect">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </body>
  </html>
</xsl:template>

<xsl:template match="AuthorInfo">
    <xsl:value-of select="Name/First"/><xsl:text> </xsl:text><xsl:value-of select="Name/Last"/>, <xsl:value-of select="JobTitle"/>, <xsl:value-of select="Affiliation"/>
</xsl:template>
<xsl:template match="Sect">
<xsl:if test="not(@hide='yes')">
<a name="{generate-id()}"><h2 style="color:blue"><img src="line3.gif" width="300" height="11"/><br x=""/>
<xsl:value-of select="Title"/></h2></a>
<xsl:apply-templates select="Title[1]/following-sibling::*"/>
</xsl:if>
</xsl:template>

<xsl:template match="SubSect1">
<xsl:if test="not(@hide='yes')">
<a name="{@id}"><h3 style="color:red"><img src="line4.gif" width="200" height="9"/><br/>
<xsl:value-of select="Title"/></h3></a>
<xsl:apply-templates select="Title[1]/following-sibling::*"/>
</xsl:if>
</xsl:template>

<xsl:template match="Para">
    <p><xsl:apply-templates /></p>
</xsl:template>

<xsl:template match="ulink">
  <a href="{@href}"><xsl:apply-templates/></a>
</xsl:template>

<xsl:template match="literal">
  <span class="literal"><xsl:value-of select="."/></span>
</xsl:template>

<xsl:template match="Figure">
  <div align="center"><table><tr><th><xsl:value-of select="Title"/></th></tr><tr><td><xsl:apply-templates select="Img"/></td></tr></table></div>
</xsl:template>

<xsl:template match="Img">
  <img src="{@Src}"/>
</xsl:template>

<xsl:template match="Emphasis">
  <xsl:choose>
    <xsl:when test="@Type='Bold'">
      <b><xsl:apply-templates/></b>
    </xsl:when>
    <xsl:when test="@Type='Italic'">
      <i><xsl:apply-templates/></i>
    </xsl:when>
    <xsl:when test="@Type='Mono'">
      <span class="literal"><xsl:apply-templates/></span>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="programlisting">
<pre><xsl:value-of select="."/></pre>
</xsl:template>

<xsl:template name="table-of-contents">
<h2 style="color:blue"><img src="line3.gif" width="300" height="11"/><br/>
Contents</h2>
  <ul>
  <xsl:for-each select="/Paper/Sect">
  <li><a href="#{generate-id()}"><xsl:value-of select="Title"/></a></li>
  </xsl:for-each>
  </ul>
</xsl:template>

<xsl:template match="sectionref">
  <xsl:variable name="id" select="@id"/>
  <xsl:for-each select="//section[@id=$id]">
  <a href="#{@id}"><xsl:value-of select="Title"/></a>
  <xsl:if test="not(position()=last())"><xsl:text>, </xsl:text></xsl:if>
  </xsl:for-each>
</xsl:template>

<xsl:template match="ListItem">
  <li><xsl:apply-templates/></li>
</xsl:template>

<xsl:template match="note">
  <br/>
  <table border="1" cellspacing="0" cellpadding="4" >
   <tr><td valign="top" align="right" bgcolor="yellow"><img src="point.gif"/></td><td valign="top"><b><u>Note</u></b>:<xsl:apply-templates />
   </td></tr></table>
</xsl:template>


<xsl:template match="List">
  <xsl:if test="@Type='BULLET'">
  <ul>
  <xsl:apply-templates />
  </ul>
  </xsl:if>
  <xsl:if test="@Type='NUMBER'">
  <ol>
  <xsl:apply-templates />
  </ol>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
