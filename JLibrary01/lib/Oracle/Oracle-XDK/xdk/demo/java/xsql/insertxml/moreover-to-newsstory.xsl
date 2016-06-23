<?xml version = '1.0'?>
<ROWSET xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xsl:version="1.0">
  <xsl:for-each select="moreovernews/article">
  <ROW>
    <TITLE><xsl:value-of select="headline_text"/></TITLE>
    <URL><xsl:value-of select="url"/></URL>
    <SOURCE><xsl:value-of select="source"/></SOURCE>
  </ROW>
  </xsl:for-each>
</ROWSET>