<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:import href="../common/rowcol.xsl"/>

  <xsl:template match="page">
    <html>
      <head><link rel="stylesheet" type="text/css" href="../common/rowcol.css" />
      </head>
      <body class="page">
        <center><form action="emp.xsql" method="post">Value of 'find' Parameter to Match LAST_NAME:<input type="text" 
         value="{/page/request/parameters/find}" name="find"/>
        </form></center>
        <xsl:apply-templates select="ROWSET"/>      
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
