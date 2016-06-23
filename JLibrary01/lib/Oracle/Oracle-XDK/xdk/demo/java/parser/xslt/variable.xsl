<?xml version="1.0"?> 

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="chapter">
    <div>
    <xsl:call-template name="component.title"/>    
     <xsl:call-template name="component.subtitle"/>
  </div>
</xsl:template>

<xsl:template name="component.title">
  <xsl:param name="node" select="."/>
  <xsl:param name="title">
    <xsl:call-template name="inline.component.title">
      <xsl:with-param name="node" select="$node"/>
    </xsl:call-template>
  </xsl:param>

  <h2>
      <xsl:apply-templates select="$node" mode="component.number">
        <xsl:with-param name="add.space" select="true()"/>
      </xsl:apply-templates>
      <xsl:copy-of select="$title"/>
  </h2>
</xsl:template>

<xsl:template name="inline.component.title">
  <xsl:param name="node" select="."/>
  <xsl:variable name="title" select="$node/title"/>
  <xsl:variable name="ititle" select="$node/docinfo/title"/>

  <xsl:choose>
    <xsl:when test="$ititle">
        <xsl:value-of select="$ititle"/>
    </xsl:when>
  </xsl:choose>
</xsl:template>

<xsl:template match="chapter" mode="component.number">
  <xsl:param name="add.space" select="false()"/>
  <xsl:number from="book" count="chapter" format="1."/>
  <xsl:if test="$add.space">
    <xsl:text>   </xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template name="component.subtitle">
  <xsl:param name="node" select="."/>
  <xsl:variable name="has.subtitle" 
                select="$node/subtitle|$node/docinfo/subtitle"/>
  <xsl:variable name="subtitle" select="$node/subtitle"/>
  <xsl:variable name="isubtitle" select="$node/docinfo/subtitle"/>

  <xsl:if test="$has.subtitle">
    <h3>
        <xsl:choose>
          <xsl:when test="$isubtitle">
            <xsl:value-of select="$isubtitle"/>
          </xsl:when>
          <xsl:when test="$subtitle">
            <xsl:value-of select="$subtitle"/>
          </xsl:when>
          <xsl:otherwise><!--this can't happen--></xsl:otherwise>
        </xsl:choose>
    </h3>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
