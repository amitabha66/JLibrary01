<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<!--
 | Import stylesheets that format the
 | individual tab page contents
 +-->

<xsl:import href="help.xsl"/>
<xsl:import href="demos.xsl"/>

<xsl:param name="thispage"/>

<!--
 | Prefix for all images. Defaults to null, meaning
 | that images will be found in the current directory.
 +-->
<xsl:variable name="i"></xsl:variable>

<xsl:output method="html" indent="no"/>

<xsl:template match="/site">
    <html>
      <head>
         <link rel="stylesheet" type="text/css" href="home.css"/>
      </head>
      <body bdgcolor="#FFFFFF" leftmargin="0" topmargin="0">
      <!--boxBody-->
      <table width="100%" cellspacing="0" cellpadding="0" border="0">
        <xsl:apply-templates select="page-content"/>
      </table>
      </body>
    </html>
</xsl:template>

</xsl:stylesheet>
