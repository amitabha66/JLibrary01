<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:import href="../common/rowcol.xsl"/>
  <xsl:output type="html" indent="no"/>
  
  <xsl:template match="Department_Name">
    <b><xsl:value-of select="."/></b>
    <xsl:text>&#160;&#160;</xsl:text>
    <a target="right" href="SalChart.xsql?deptno={../H_DEPTNO}">Chart</a>
    <xsl:text>&#160;&#160;</xsl:text>
    <a target="right" href="SalChart.xsql?deptno={../H_DEPTNO}&amp;xml-stylesheet=none">xml</a>
    <xsl:text>&#160;&#160;</xsl:text>
    <a target="right" href="SalChart.xsql?deptno={../H_DEPTNO}&amp;xml-stylesheet=SalChartRaw.xsl">svg</a>
  </xsl:template>

</xsl:stylesheet>



