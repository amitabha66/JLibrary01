<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml"/>
<xsl:template match="@*|node()">
  <xsl:copy>
    <xsl:apply-templates select="@*|node()"/>
  </xsl:copy>
</xsl:template>
<xsl:template match="DAMAGEREPORT">
  <DAMAGEREPORT><xsl:value-of select="." disable-output-escaping="yes"/></DAMAGEREPORT>
</xsl:template>
<xsl:template match="CONTENTS">
  <CONTENTS><xsl:value-of select="." disable-output-escaping="yes"/></CONTENTS>
</xsl:template>
</xsl:stylesheet>
