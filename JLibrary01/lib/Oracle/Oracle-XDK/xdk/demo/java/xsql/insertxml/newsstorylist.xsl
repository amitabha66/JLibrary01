<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:import href="../common/rowcol.xsl"/>
  <xsl:template match="TITLE">
    <a href="{../H_URL}"><xsl:value-of select="."/></a>
  </xsl:template>
<xsl:output method="html"/>
<xsl:template match="/">
<HTML>
<HEAD>
<link rel="stylesheet" type="text/css" href="../common/rowcol.css" />
</HEAD>
<BODY>
<center><h2>Last 10 News Stories</h2></center>
<xsl:apply-templates select="/page/latestnews/ROWSET"/>
</BODY>
</HTML>
</xsl:template>
</xsl:stylesheet>
