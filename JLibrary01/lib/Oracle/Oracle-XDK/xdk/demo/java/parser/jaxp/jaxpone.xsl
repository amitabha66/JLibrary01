<xsl:stylesheet version='1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>
	<xsl:output method="xml"/>
	
	<xsl:template name="one" match="driver">
		<xsl:if test="./@name='OracleXSLTDriver'">
			<H2><center><xsl:text>Oracle XSLT BenchMark 1.0</xsl:text></center></H2>
		</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>
