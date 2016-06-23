<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
 <xsl:variable name="demodir">demo</xsl:variable>
 <xsl:template match="page-content[@name='Demos']">
  <table class="y" width="100%" cellpadding="5" cellspacing="0" border="0">
<!--  <table border="2" cellspacing="0" width="100%" cellpadding="5"> -->
    <xsl:for-each select="demo">
       <xsl:variable name="url_base">
         <xsl:text>../</xsl:text>
	 <xsl:choose>
	   <xsl:when test="baseurl"><xsl:value-of select="baseurl"/></xsl:when>
	   <xsl:otherwise><xsl:value-of select="@id"/></xsl:otherwise>
	 </xsl:choose>
         <xsl:text>/</xsl:text>
       </xsl:variable>
      <tr>
	    <td nowrap="" align="right" valign="top">
            <xsl:if test="position() mod 2 = 1">
             <xsl:attribute name="class">h</xsl:attribute>
            </xsl:if>
            <a href="{$url_base}{url}">
              <strong>
                <xsl:value-of select="name"/>
              </strong>
      	    </a>
        </td>
	<td valign="top">
            <xsl:if test="position() mod 2 = 1">
             <xsl:attribute name="class">h</xsl:attribute>
            </xsl:if>
          <xsl:choose>
            <xsl:when test="ie5-only='Y'">
              <img align="absmiddle" border="0" src="ie5xml.gif"/>
            </xsl:when>
            <xsl:otherwise>
              <img align="absmiddle" border="0" src="any.gif"/>
            </xsl:otherwise>
          </xsl:choose>
        </td>
        <td valign="top">
            <xsl:if test="position() mod 2 = 1">
             <xsl:attribute name="class">h</xsl:attribute>
            </xsl:if>
          <xsl:choose>
            <xsl:when test="sourceurl='None'">
              &#160;
            </xsl:when>
            <xsl:otherwise>
               <a href="{$url_base}{sourceurl}">
                 <img align="absmiddle" border="0" src="xmlfile.gif"/>
               </a>
            </xsl:otherwise>
          </xsl:choose>
        </td>
	      <td valign="top">
            <xsl:if test="position() mod 2 = 1">
             <xsl:attribute name="class">h</xsl:attribute>
            </xsl:if>
            <xsl:value-of select="description"/>
        </td>
      </tr>
    </xsl:for-each>
  </table>
  <small>
    <ul>
    <li>
    Demos with <img align="absmiddle" border="0" src="any.gif"/> work in any browser. Demos with
    <img align="absmiddle" border="0" src="ie5xml.gif"/> require XML browsing and/or
    processing capabilities of Internet Explorer 5.0.
    </li>
    <li>
    Follow the <img align="absmiddle" border="0" src="xmlfile.gif"/> icon link to view XSQL page source.
    </li>
    </ul>
    </small>
</xsl:template>
</xsl:stylesheet>
