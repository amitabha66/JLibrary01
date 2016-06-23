<?xml version="1.0"?> 

<!-- Test for simple identity transformation -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
               
  <xsl:template match="/">
    <HTML>
      <H1>Simple test for xsl:copy.</H1>
      <HR/>
      <P>Should show a list with item1, item2, item3, subitem1, subitem2, subitem3:</P>
      <xsl:apply-templates/>
    </HTML>
  </xsl:template>

  <xsl:template match="*|@*|comment()|text()">
      <xsl:copy>
          <xsl:apply-templates select="*|@*|comment()|text()"/>
      </xsl:copy>
  </xsl:template>
     
</xsl:stylesheet>
