<?xml version="1.0"?> 

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="doc">
      <H1>Pattern match test for position()=X in more complex patterns.</H1>
      <HR/>
      <xsl:apply-templates/>
  </xsl:template>
  
  <xsl:template match="foo[3]/a[position()=3]/num/@val">
    <P>Should say 9: <xsl:value-of select="."/></P>
  </xsl:template>

  <xsl:template match="foo[3]/a[position()=2]/num/@val">
    <P>Should say 8: <xsl:value-of select="."/></P>
  </xsl:template>
  
  <xsl:template match="foo[3]/a[position()=1]/num/@val">
    <P>Should say 7: <xsl:value-of select="."/></P>
  </xsl:template>

  <xsl:template match="foo[2]/a[position()=3]/num/@val">
    <P>Should say 6: <xsl:value-of select="."/></P>
  </xsl:template>

  <xsl:template match="foo[2]/a[position()=2]/num/@val">
    <P>Should say 5: <xsl:value-of select="."/></P>
  </xsl:template>

  <xsl:template match="foo[2]/a[position()=1]/num/@val">
    <P>Should say 4: <xsl:value-of select="."/></P>
  </xsl:template>

  <xsl:template match="foo[1]/a[position()=3]/num/@val">
    <P>Should say 3: <xsl:value-of select="."/></P>
  </xsl:template>

  <xsl:template match="foo[1]/a[position()=2]/num/@val">
    <P>Should say 2: <xsl:value-of select="."/></P>
  </xsl:template>

  <xsl:template match="foo[1]/a[position()=1]/num/@val">
    <P>Should say 1: <xsl:value-of select="."/></P>
  </xsl:template>
  
  <xsl:template match="a/num">
    <xsl:apply-templates select="@val"/>
  </xsl:template>

  <xsl:template match="@val" priority="-1"/>
 
</xsl:stylesheet>
