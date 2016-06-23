<?xml version="1.0" ?>
<!--
| $Author: kkarun $
| $Date: 20-apr-00.23:44:34 $
| $Source: /vobs/oracore3/demo/xdk/java/xsql/demo/doyouxml/doyouxml-lynx.xsl.mkelem $
| $Revision: /main/0 $
+-->
<xsl:stylesheet   xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                  xmlns="http://www.w3.org/TR/REC-html40"
                  result-ns="">

<xsl:template match="/">
  <xsl:apply-templates select="datapage"/>
</xsl:template>
<!-- The overall Page -->
<xsl:template match="datapage">
<html>
<head>
<title>Oracle XML Central</title>
</head>
<body>
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
            <td width="*" align="center">
              <xsl:choose>
                <xsl:when test='.[Name="Search"]'>
                  <img border="0" align="absmiddle" src="{Icon}"/><xsl:text>  </xsl:text>
                  <input type="text" name="find" value="{../Find/@Criteria}" size="12"/>
                  <input type="hidden" name="cat" value="Search"/>
                </xsl:when>
                <xsl:otherwise>
                  <img align="absmiddle" src="vspace.gif"/>
                  <a href="doyouxml.xsql?category={Name}">
                  <img border="0" align="absmiddle" src="{Icon}"/>
                  </a>
                  <xsl:text>  </xsl:text>
                  <a style="text-decoration: none" href="doyouxml.xsql?category={Name}">
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
      <xsl:sort select="ID" order="descending"/>
      <tr>
        <td width="3%" valign="top">
          <img src="a.gif" width="8" height="9"/>
        </td>
        <td width="97%">
            <a href="{URL}"><strong><xsl:value-of select="TITLE"/></strong></a>
          <xsl:text> </xsl:text>(<xsl:value-of select="TIMESTAMP"/>)
          <br/>
          <xsl:value-of select="DESCRIPTION"/>
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
<td width="151" valign="top">
<xsl:for-each select="ITEM"><a href="{URL}"><xsl:value-of select="TITLE"/></a>
   <xsl:if test="not(position()=last())"><hr x=""/></xsl:if>
</xsl:for-each>
</td>
</xsl:template>

<xsl:template match="SIDEBARITEMS[not(ITEM)]">
<td width="151"/>
</xsl:template>

</xsl:stylesheet>
