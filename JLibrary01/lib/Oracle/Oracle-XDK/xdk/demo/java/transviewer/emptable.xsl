<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="yes"/>
  <xsl:template match="/">
    <html>
      <head><link rel="stylesheet" type="text/css" href="rowcol.css" />
      </head>
      <body class="page">
      <center>
        
        <P><font color="#FF0000">
       This illustrate a database query result set that is converted to XML. The XML is then transformed to this HTML table by applying XSL stylesheet. The resulting HTML is rendered in Java panel for use by front end Java applications
           </font>
        </P>
        <H1><font color="#00FF00">Employee Table Query Results</font></H1>
      <table border="2" cellpadding="4">
      <xsl:for-each select="rowset/row[1]">
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
      <xsl:for-each select="rowset/row">
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

