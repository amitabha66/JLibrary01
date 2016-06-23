<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="no"/>
<xsl:variable name="i">../images/</xsl:variable>
<xsl:template match="/">
<html>
  
  <!-- Setup basic variables needed for paging -->

  <xsl:variable name="cat" select="/datapage/INFO/CAT"/>
  <xsl:variable name="find" select="/datapage/INFO/FIND"/>
  <xsl:variable name="size" select="number(6)"/>
  <xsl:variable name="total" select="number(/datapage/INFO/TOTAL)"/>
  <xsl:variable name="first" select="number(/datapage/MAINITEMS/ITEM[1]/@num)"/>
  <xsl:variable name="last"  select="number(/datapage/MAINITEMS/ITEM[last()]/@num)"/>
  <xsl:variable name="curskip">
    <xsl:if test="$first > 1">
      <xsl:value-of select="number($first)-1"/>
    </xsl:if>
  </xsl:variable>

  <xsl:variable name="page">doyouxml<xsl:if test="$find!=''">find</xsl:if>.xsql</xsl:variable>
  <!-- Setup next/previous variables -->

  <xsl:variable name="next">
    <xsl:if test="$total > $last"><xsl:value-of select="$last"/></xsl:if>
  </xsl:variable>

  <xsl:variable name="prev">
      <xsl:if test="$first > $size"><xsl:value-of select="($first - $size) - 1"/></xsl:if>
  </xsl:variable>

  <!-- HTML Page Starts Here -->

<head>
  <link rel="stylesheet" type="text/css" href="doyouxml.css"/>
<title>Oracle XML Central</title>
</head>
<body bgcolor="#FFFFFF">
 <form method="post" action="doyouxmlfind.xsql">
<!-- The Site Structure Section -->
<table border="0" width="100%" height="105">
  <tr>
    <td width="100%" height="44"><p align="center"><img src="{$i}banner.gif" width="755"
    height="44"/></p></td>
  </tr>
  <tr>
    <td width="100%" height="19"><div align="center"><center><table border="0" width="755"
    cellpadding="0">
      <tr>
        <td width="100%"><table border="0" width="100%" cellspacing="4" cellpadding="0">
          <tr>
          <xsl:for-each select="datapage/SiteStructure/Category">
              <td width="*" bgcolor="#FFFFFF" align="center">
              <xsl:choose>
                <xsl:when test="Name='Search'">
                  <img border="0" align="absmiddle" src="{$i}{Icon}"/><xsl:text>&#160;</xsl:text>
                  <input class="f" type="text" name="find" value="{$find}" size="12"/>
                  <input type="hidden" name="cat" value="{$cat}"/>
                </xsl:when>
                <xsl:otherwise>
                  <img align="absmiddle" src="{$i}vspace.gif"/>
                  <a class="b" href="doyouxml.xsql?cat={Name}"><img border="0" 
                    align="absmiddle" src="{$i}{Icon}"/></a><xsl:text>&#160;</xsl:text><a 
                    class="c" href="doyouxml.xsql?cat={Name}"><xsl:value-of select="Name"/></a>
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
  <tr>
    <td width="100%" height="24"><p align="center"><img src="{$i}bannerline.gif" width="755"
    height="8"/></p></td>
  </tr>

</table>
     <div align="center">
       <center>
         <table width="755" border="0" cellspacing="0" cellpadding="10">
           <tr>
             <td valign="top" width="604">
<div align="center">
<center>
<table  border="0" width="600">
  <tr>
    <td width="600"><table border="0" width="100%" cellspacing="0" cellpadding="0">
      <xsl:for-each select="datapage/MAINITEMS/ITEM">
      <tr>
        <td width="3%" valign="top">
          <img src="{$i}a.gif" width="8" height="9"/>
          <font color="#FFFFFF">.</font>
        </td>
        <td width="97%">
            <a class="t" href="{URL}"><xsl:value-of select="TITLE"/></a>
          <span class="d">&#160;(<xsl:value-of select="TIMESTAMP"/>)</span><span class="tr"><br/><xsl:value-of select="DESCRIPTION"/></span>
        </td>
      </tr>
    </xsl:for-each>
    </table>
    </td>
  </tr>
</table>
</center>
</div>
             </td>
<td width="151" bgcolor="#F4F4F4" valign="top">
<xsl:for-each select="datapage/SIDEBARITEMS/ITEM"><font face="Arial" size="2"><a href="{URL}"><xsl:value-of select="TITLE"/></a>
   </font><xsl:if test="not(position()=last())"><hr/></xsl:if>
</xsl:for-each>
</td>
           </tr>
<tr><td align="center" colspan="2"><span class="n">
  Viewing <xsl:value-of select="$first"/>-<xsl:value-of select="$last"/> of <xsl:value-of select="$total"/>  
  <xsl:if test="$prev">
    <a href="{$page}?cat={$cat}&amp;skip={$prev}&amp;find={$find}">&lt;&lt; Previous <xsl:value-of select="$size"/></a>
  </xsl:if>
  <xsl:if test="$next">
    <a href="{$page}?cat={$cat}&amp;skip={$next}&amp;find={$find}"> Next <xsl:value-of select="$size"/> >></a>
  </xsl:if>&#160;&#160;&#160;See the source for this site: <a class="file" href="doyouxml-xsql.xml">doyouxml.xsql</a>, <a class="file" href="doyouxmlfind-xsql.xml">doyouxmlfind.xsql</a>, <a class="file" href="doyouxml.xsl">doyouxml.xsl</a>
  </span><br/>
         <span class="n">View the <a href="{$page}?cat={$cat}&amp;skip={$curskip}&amp;find={$find}&amp;xml-stylesheet=none">Raw XML</a> or <a href="{$page}?cat={$cat}&amp;skip={$curskip}&amp;find={$find}&amp;xml-stylesheet=rss.xsl">RSS Format</a> for this dynamic page, produced using <a href="../doc/relnotes.html">Oracle XSQL Pages</a></span></td></tr>
         </table>
       </center>
     </div>
  </form>
</body>
</html>
</xsl:template>
</xsl:stylesheet>
