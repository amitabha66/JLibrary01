<?xml version="1.0"?> 

<!-- Test for numbering -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:include href="style.txt"/>
	  
	<xsl:template match="doc">
	  <H1>Test for xsl:number</H1>
	  <xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="title">
	  <P>
	     (any: count="title" <xsl:number level="any"
	                 count="title"
	                 from="title"
	                 format="A. "/>)
	     (any - count="chapter|section|subsection": <xsl:number level="any"
			 from="chapter|section|subsection"
	                 count="chapter|section|subsection"
	                 format="A. "/>)
	     <xsl:apply-templates/>
	  </P>
	</xsl:template>
	  
	<xsl:template match="chapter//title">
	  <P>
	     (single: <xsl:number level="single"
	                 count="chapter|section|subsection"
	                 from="chapter|section|subsection"
	                 format="1."/>) 
	     (multi: <xsl:number level="multiple"
	                 count="chapter|section|subsection"
	                 format="1.1."/>) 
	     (any: <xsl:number level="any"
	                 count="title"
	                 format="A. "/>)
	     <xsl:apply-templates/>
	  </P>
	</xsl:template>

	<xsl:template match="appendix//title">
	  <P>
	     (single: <xsl:number level="single"
	                 count="appendix|section|subsection"
	                 from="appendix|section|subsection"
	                 format="1."/>) 
	     (multi: <xsl:number level="multiple"
	                 count="appendix|section|subsection"
	                 format="A.1.I. "/>) 
	     (any: <xsl:number level="any"
	                 count="title"
	                 format="A. "/>)
	     <xsl:apply-templates/>
	  </P>
	</xsl:template>
   
</xsl:stylesheet>
