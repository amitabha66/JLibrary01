<xsl:stylesheet 
	version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:ages="http://www.oracle.com/ages">
	
	<xsl:param name="ages:age">99</xsl:param>
	
	<xsl:template match="/">
    <age>
    	<xsl:value-of select="$ages:age"/>
    </age>
  </xsl:template>
</xsl:stylesheet>
