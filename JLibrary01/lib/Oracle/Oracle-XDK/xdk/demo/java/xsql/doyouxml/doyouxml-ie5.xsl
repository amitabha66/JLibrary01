<?xml version="1.0" ?>
<!--
| $Author: kkarun $
| $Date: 20-apr-00.23:44:06 $
| $Source: /vobs/oracore3/demo/xdk/java/xsql/demo/doyouxml/doyouxml-ie5.xsl.mkelem $
| $Revision: /main/0 $
+-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl" 
                result-ns="http://www.w3.org/TR/REC-html40">

<xsl:template match="/">
  <xsl:apply-templates select="datapage"/>
</xsl:template>
<!-- The overall Page -->
<xsl:template match="datapage">
<html>
<head>
<title>Oracle XML Central</title>
</head>
<body bgcolor="#FFFFFF">
 <form method="post" action="doyouxml.xsql">
   <xsl:apply-templates select="SiteStructure" />
     <div align="center">
       <center>
         <table width="755" border="0" cellspacing="0" cellpadding="10">
           <tr>
             <td valign="top" width="604">
               <xsl:apply-templates select="MAINITEMS"/>
             </td>
             <xsl:apply-templates select="SIDEBARITEMS"/>
           </tr>
         </table>
       </center>
     </div>
  </form>
</body>
</html>
</xsl:template>

<!-- The Site Structure Section -->
<xsl:template match="SiteStructure">
<table border="0" width="100%" height="105">
  <tr>
    <td width="100%" height="44"><p align="center"/><img src="banner.gif" width="755"
    height="44"/></td>
  </tr>
  <tr>
    <td width="100%" height="19"><div align="center"><center><table border="0" width="755"
    cellpadding="0">
      <tr>
        <td width="100%"><table border="0" width="100%" cellspacing="4" cellpadding="0">
          <tr>
          <xsl:for-each select="Category">
            <td width="*" bgcolor="#FFFFFF" align="center">
              <xsl:choose>
                <xsl:when test='.[Name="Search"]'>
                  <img border="0" align="absmiddle"><xsl:attribute name="src"><xsl:value-of select="Icon"/></xsl:attribute></img>
                  <input style="font-size: 9pt; font-family: Arial" 
                         type="text" name="find" value="" size="12"/>
                  <input type="hidden" name="cat" value="Search"/>
                </xsl:when>
                <xsl:otherwise>
                  <img align="absmiddle" src="vspace.gif"><xsl:attribute name="src"><xsl:value-of select="Icon"/></xsl:attribute></img>
                  <a><xsl:attribute name="href">doyouxml.xsql?cat=<xsl:value-of select="Name"/></xsl:attribute>
                  <img border="0" align="absmiddle"><xsl:attribute name="src"><xsl:value-of select="Icon"/></xsl:attribute></img></a>
                  <a style="text-decoration: none;color:black"><xsl:attribute name="href">doyouxml.xsql?cat=<xsl:value-of select="Name"/></xsl:attribute>
                  <strong><xsl:value-of select="Name"/></strong>
                  </a>
                </xsl:otherwise>
              </xsl:choose>
            </td>
          </xsl:for-each>
          </tr>
        </table>
        </td>
      </tr>
    </table>
    </center></div></td>
  </tr>
<!--
  <tr>
    <td width="100%" height="24"><p align="center"/><img src="bannerline.gif" width="755"
    height="8"/></td>
  </tr>
    <tr align="center">
    </tr>
-->
  <tr>
    <td width="100%" height="24"><p align="center"/><img src="bannerline.gif" width="755"
    height="8"/></td>
  </tr>

</table>
</xsl:template>

<!-- The Main News Section -->
<xsl:template match="MAINITEMS">
<div align="center">
<center>
<table  border="0" width="600">
  <tr>
    <td width="600"><table border="0" width="100%" cellspacing="0" cellpadding="0">
      <xsl:for-each select="ITEM">
      <tr>
        <td width="3%" valign="top">
          <img src="a.gif" width="8" height="9"/>
          <font color="#FFFFFF">.</font>
        </td>
        <td width="97%">
          <font face="Arial,Times" size="2">
            <a><xsl:attribute name="href"><xsl:value-of select="URL"/></xsl:attribute><strong><xsl:value-of select="TITLE"/></strong>
          </a></font>
          <font size="1"> (<xsl:value-of select="TIMESTAMP"/>)</font>
          <br/>
          <font face="Arial,Times" size="2"><xsl:value-of select="DESCRIPTION"/></font>
        </td>
      </tr>
    </xsl:for-each>
    </table>
    </td>
  </tr>
</table>
</center>
</div>
</xsl:template>

<!-- The Site Structure Section -->
<!-- The Main News Section -->
<xsl:template match="SIDEBARITEMS[ITEM]">
<td width="151" bgcolor="#F4F4F4" valign="top">
<xsl:for-each select="ITEM"><font face="Arial" size="2"><a><xsl:attribute name="href"><xsl:value-of select="URL"/></xsl:attribute></a><xsl:value-of select="TITLE"/>
   </font><xsl:if test=".[not(end())]"><hr x=""/></xsl:if>
</xsl:for-each>
</td>
</xsl:template>

<xsl:template match="SIDEBARITEMS[not(ITEM)]">
<td width="151"/>
</xsl:template>

</xsl:stylesheet>
