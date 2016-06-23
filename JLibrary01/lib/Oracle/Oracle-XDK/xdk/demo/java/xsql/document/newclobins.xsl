<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" cdata-section-elements="CONTENTS"/>
<xsl:template match="/">
<ROWSET> 
  <xsl:for-each select="request/parameters">
  <ROW>
    <AUTHOR><xsl:value-of select="author"/></AUTHOR>
    <TITLE><xsl:value-of select="title"/></TITLE>
    <STYLESHEET><xsl:value-of select="stylesheet"/></STYLESHEET>
    <DOCUMENT>
       <CONTENTS><xsl:value-of disable-output-escaping="yes" select="doc"/></CONTENTS>
    </DOCUMENT>
<!--    <DOCUMENT><xsl:value-of disable-output-escaping="yes" select="doc"/></DOCUMENT> -->
  </ROW>
  </xsl:for-each>
</ROWSET>
</xsl:template>
</xsl:stylesheet>