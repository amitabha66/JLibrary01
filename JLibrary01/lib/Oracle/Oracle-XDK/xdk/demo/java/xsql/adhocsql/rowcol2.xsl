<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:import href="claim.xsl"/>

<xsl:output method="html" indent="yes"/>

  <xsl:template match="/">
    <html>
      <head><link rel="stylesheet" type="text/css" href="../common/rowcol.css" />
      </head>
      <body class="page">
      <center>
      <table border="0" cellpadding="4">
      <xsl:for-each select="ROWSET/ROW[1]">
        <tr>
          <xsl:for-each select="*">
            <th>
	      <xsl:attribute name="class">
		<xsl:choose>
		  <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
		  <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
		</xsl:choose>
	      </xsl:attribute>
              <xsl:value-of select="name(.)"/>
            </th>
          </xsl:for-each>
        </tr>
      </xsl:for-each>
      <xsl:for-each select="ROWSET/ROW">
        <tr>
          <xsl:attribute name="class">
            <xsl:choose>
              <xsl:when test="position() mod 2 = 1">rowodd</xsl:when>
              <xsl:when test="position() mod 2 = 0">roweven</xsl:when>
            </xsl:choose>
          </xsl:attribute>
          <xsl:for-each select="*">
            <td valign="middle">
	      <xsl:attribute name="class">
		<xsl:choose>
		  <xsl:when test="position() mod 2 = 1">colodd</xsl:when>
		  <xsl:when test="position() mod 2 = 0">coleven</xsl:when>
		</xsl:choose>
	      </xsl:attribute>
              <xsl:choose>
               <xsl:when test="not(*)">
                 <xsl:choose>
                   <xsl:when test=".=''">&#160;</xsl:when>
                   <xsl:otherwise><xsl:value-of select="."/></xsl:otherwise>
                 </xsl:choose>
               </xsl:when>
               <xsl:otherwise>
                 <xsl:apply-templates select="."/>
               </xsl:otherwise>
             </xsl:choose>
            </td>
          </xsl:for-each>
        </tr>
      </xsl:for-each>
      </table>
      </center>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>

