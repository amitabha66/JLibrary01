<?xml version="1.0"?>
<!--
| $Author: kkarun $
| $Date: 20-apr-00.23:38:54 $
| $Source: /vobs/oracore3/demo/xdk/java/xsql/demo/classerr/invalidclasses.xsl.mkelem $
| $Revision: /main/0 $
+-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:template match="/">
    <html>
      <head>
      <title>Invalid Oracle8i Java Classes</title>
      <style>
	.odd {font-family: Andale Mono, Courier; font-size: 8pt; background-color: white; color: navy}
	.even {font-family: Andale Mono, Courier; font-size: 8pt; background-color: yellow; color: navy}
      </style>
      </head>

      <body bgcolor="white">

      <p><span style="font-family: Tahoma, sans-serif; font-size: 18pt; color: rgb(0,0,255)">Invalid Java Classes</span></p>

      <table width="100%" border="0" cellpadding="4" bgcolor="navy" style="color: white">
	<tr>
	  <td><h2>ClassName</h2></td>
	  <td><h2>Error Text</h2></td>
	</tr>
	<xsl:for-each select="rowset/row">
          <tr>
	  <xsl:attribute name="class">
	    <xsl:choose>
	      <xsl:when test="position() mod 2 = 1">odd</xsl:when>
	      <xsl:when test="position() mod 2 = 0">even</xsl:when>
	    </xsl:choose>
	  </xsl:attribute>
	    <td valign="top"><xsl:value-of select="classname"/></td>
	    <td>
                <xsl:choose>
                  <xsl:when test="errors/errors_item">
                    <table border="0" width="100%" cellpadding="2">
                      <xsl:for-each select="errors/errors_item">
		        <tr>
		          <td style="font-size:8pt">
                           <xsl:value-of select="message"/></td>
		        </tr>
                      </xsl:for-each>
                    </table>
                  </xsl:when>
                  <xsl:otherwise>
                   &#160;
                  </xsl:otherwise>
                </xsl:choose>
              </td>
	    </tr>
	</xsl:for-each>
        <xsl:if test="rowset[not(row)]">
	  <tr class="odd">
	    <td valign="top">No Classes are Invalid.</td>
	    <td>&#160;</td>
	  </tr>
        </xsl:if>
      </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
