<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="yes"/>
<xsl:template match="/">
<html>
<head>
<title>Claim</title>
</head>
<body>
<xsl:apply-templates select="//ROW"/>
</body>
</html>
</xsl:template>



<xsl:template match="HOMEADDRESS|ADDRESS">
<div style="font-family: monospace; color:white">
<table border="1" cellspacing="0" cellpadding="10">
  <tr>
    <td width="100%" bgcolor="#FFFFFF">
    <xsl:value-of select="../FIRSTNAME"/>
    <xsl:text>&#160;</xsl:text>
    <xsl:value-of select="../LASTNAME"/><br x=""/>
    <xsl:value-of select="STREET"/><br x="" />
    <xsl:value-of select="CITY"/>
    <xsl:text>, </xsl:text>
    <xsl:value-of select="STATE"/>
    <xsl:text>&#160;</xsl:text>
    <xsl:value-of select="ZIP"/>
  </td>
  </tr>
</table>
</div>
</xsl:template>

<xsl:template match="CLAIMPOLICY">
  <xsl:apply-templates select="PRIMARYINSURED" />
  <br x=""/>
</xsl:template>

<xsl:template match="PRIMARYINSURED|CUSTOMER">
  <xsl:apply-templates select="HOMEADDRESS" />
</xsl:template>

<xsl:template match="SETTLEMENTS">
<table border="1" width="100%" cellpadding="5" cellspacing="0">
  <tr>
    <td width="33%" bgcolor="#400080"><strong><font color="#FFFFFF" face="Arial">Payment Date</font></strong></td>
    <td align="right" width="33%" bgcolor="#400080"><strong><font color="#FFFFFF" face="Arial">Amount</font></strong></td>
    <td width="34%" bgcolor="#400080"><strong><font color="#FFFFFF" face="Arial">Approved By</font></strong></td>
  </tr>
  <xsl:for-each select="SETTLEMENTS_ITEM">
  <tr style="background-color:white">
    <td bgcolor="white" width="33%"><xsl:value-of select="substring-before(PAYDATE,' ')"/></td>
    <td bgcolor="white" align="right" width="33%">$<xsl:value-of select="AMOUNT"/>.00</td>
    <td bgcolor="white" width="34%"><xsl:value-of select="APPROVER"/></td>
  </tr>
  </xsl:for-each>
</table>
</xsl:template>

<xsl:template match="*"/>

<xsl:template match="CLAIM">
<table border="0" width="100%">
  <tr>
    <td width="40%"><big><big><strong><font face="Arial">Oracle Insurance</font></strong></big></big></td>
    <td width="*"></td>
    <td width="12%"><strong><font face="Arial">Claim# <xsl:value-of select="CLAIMID"/></font></strong></td>
  </tr>
  <tr>
    <td colspan="3" ><xsl:apply-templates/></td>
  </tr>
</table>
</xsl:template>

<xsl:template match="ROW">
<xsl:apply-templates/>
<hr x=""/>
</xsl:template>

</xsl:stylesheet>

