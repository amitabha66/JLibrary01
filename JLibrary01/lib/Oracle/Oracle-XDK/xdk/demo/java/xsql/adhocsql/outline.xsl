<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="yes"/>
<xsl:template match="/">
  <html>
  <body bgcolor="white">
  <xsl:apply-templates/>
  </body>
  </html>
</xsl:template>

<xsl:template match="*[not(text())]">
  <xsl:param name="treeicon">n</xsl:param>
  <xsl:variable name="level" select="count(ancestor-or-self::*)"/>
  <xsl:variable name="title" select="."/>
  <xsl:variable name="this"  select="name(.)"/>
  <img src="../images/s.gif" border="0" height="1" width="{22*$level}"/>
  <img border="0" src="../images/{concat($treeicon,'.gif')}"/>
  <span style="color:red"><xsl:value-of select="$this"/></span>
  <br/>
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="*[text()]">
  <xsl:param name="treeicon">n</xsl:param>
  <xsl:variable name="level" select="count(ancestor-or-self::*)"/>
  <xsl:variable name="title" select="."/>
  <xsl:variable name="this"  select="name(.)"/>
  <img src="../images/s.gif" border="0" height="1" width="{22*$level}"/>
  <img border="0" src="../images/{concat($treeicon,'.gif')}"/>
  <span style="color:red"><xsl:value-of select="$this"/></span> 
  <span style="color:blue"> (<xsl:value-of select="$title"/>)</span>
  <br/>
  <xsl:apply-templates/>
</xsl:template>

<xsl:template match="@*|comment()|processing-instruction()|text()"/>

</xsl:stylesheet>
