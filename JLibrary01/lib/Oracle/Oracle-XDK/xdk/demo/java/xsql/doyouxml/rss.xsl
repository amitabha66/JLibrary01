<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" doctype-system="rss-0.91.dtd"/>
<xsl:template match="/">
<xsl:for-each select="datapage/MAINITEMS">
<rss version="0.91">
  <channel>
    <title>Do You XML?</title>
    <link>http://xml.us.oracle.com</link>
    <description>Oracle XML Website Demo</description>
    <language>en-us</language>
    <xsl:for-each select="ITEM">
    <item>
      <title><xsl:value-of select="TITLE"/></title>
      <link><xsl:value-of select="URL"/></link>
      <description><xsl:value-of select="DESCRIPTION"/></description>
    </item>
    </xsl:for-each>
  </channel>
</rss>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>
