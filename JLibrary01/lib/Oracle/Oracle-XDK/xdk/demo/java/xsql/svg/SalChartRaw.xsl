<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:Color="http://www.oracle.com/XSL/Transform/java/java.awt.Color"
                xmlns:Integer="http://www.oracle.com/XSL/Transform/java/java.lang.Integer"
                exclude-result-prefixes="Color Integer">

 <xsl:strip-space elements="*"/>
 <xsl:output indent="no" standalone="yes"/> 

 <xsl:template match="/">
 <svg  width="1000" height="1000">
   <desc>Salary Chart</desc>
   <g style="stroke:#000000;stroke-width:1;font-family:Arial;font-size:16">
     <xsl:for-each select="ROWSET/ROW">
       <xsl:call-template name="drawBar">
         <xsl:with-param name="rowIndex" select="position()"/>
         <xsl:with-param name="ename" select="ENAME"/>
         <xsl:with-param name="sal" select="number(SAL)"/>
       </xsl:call-template>
     </xsl:for-each>
   </g>
 </svg>
 </xsl:template>

 <xsl:template name="drawBar">
   <xsl:param name="rowIndex" select="number(0)"/>
   <xsl:param name="ename"/> 
   <xsl:param name="sal" select="number(0)"/>

   <xsl:variable name="xOffset"   select="number(100)"/>
   <xsl:variable name="yOffset"   select="number(20)"/>
   <xsl:variable name="barHeight" select="number(25)"/>
   <xsl:variable name="gap"       select="number(10)"/>

   <xsl:variable name="x" select="$xOffset"/>
   <xsl:variable name="y" select="$yOffset + $rowIndex * ($barHeight + $gap)"/>
   <xsl:variable name="barWidth" select="$sal div number(10)"/>
   <xsl:variable name="fontHeight" select="number(18)"/>
   <xsl:comment> Bar <xsl:value-of select="$rowIndex"/></xsl:comment>
   <text x="20" y="{$y + $fontHeight}">
     <xsl:value-of select="$ename"/>
   </text>
   <rect x="{$x}" y="{$y}" height="{$barHeight}" width="{$barWidth}">
     <xsl:attribute name="style">
       <xsl:text>fill:#</xsl:text>
       <xsl:call-template name="getCoolColorStr">
         <xsl:with-param name="colorIndex" select="$rowIndex"/>
         <xsl:with-param name="totalColors" select="number(14)"/>
       </xsl:call-template>
       <xsl:text> </xsl:text>
     </xsl:attribute>
   </rect>
   <xsl:variable name="x2" select="$xOffset + $barWidth + 10"/>
   <text x="{$x2}" y="{$y + $fontHeight}">
     <xsl:value-of select="$sal"/>
   </text>
  </xsl:template>

  <xsl:template name="getCoolColorStr">
    <xsl:param name="colorIndex"/>
    <xsl:param name="totalColors"/>

    <xsl:variable name="SATURATION" select="number(0.6)"/>
    <xsl:variable name="BRIGHTNESS" select="number(0.9)"/>

    <xsl:variable name="hue" select="$colorIndex div $totalColors"/>
    <xsl:variable name="c"   select="Color:getHSBColor($hue, $SATURATION, $BRIGHTNESS)"/>
    <xsl:variable name="r"   select="Color:getRed($c)"/>
    <xsl:variable name="g"   select="Color:getGreen($c)"/>
    <xsl:variable name="b"   select="Color:getBlue($c)"/>
    <xsl:variable name="rs"  select="Integer:toHexString($r)"/>
    <xsl:variable name="gs"  select="Integer:toHexString($g)"/>
    <xsl:variable name="bs"  select="Integer:toHexString($b)"/>
    <xsl:if test="$r &lt; 16">0</xsl:if><xsl:value-of select="$rs"/>
    <xsl:if test="$g &lt; 16">0</xsl:if><xsl:value-of select="$gs"/>
    <xsl:if test="$b &lt; 16">0</xsl:if><xsl:value-of select="$bs"/>
  </xsl:template>

</xsl:stylesheet>






