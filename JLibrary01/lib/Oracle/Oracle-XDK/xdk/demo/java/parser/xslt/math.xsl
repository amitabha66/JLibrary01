<?xml version="1.0"?> 

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="doc">
    <HTML>
      <H1>Test for mod.</H1>
      <HR/>
      <P>Should say "1": <xsl:value-of select="5 mod 2"/></P>
      <P>Should say "1": <xsl:value-of select="n1 mod n2"/></P>
      <P>Should say "-1": <xsl:value-of select="div mod mod"/></P>
      <P> <xsl:value-of select="div or ((mod)) | or"/></P>
    </HTML>
  </xsl:template>
 
</xsl:stylesheet>
