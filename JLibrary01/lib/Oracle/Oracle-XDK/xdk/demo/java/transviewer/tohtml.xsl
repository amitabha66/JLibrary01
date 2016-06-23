<?xml version="1.0"?> 

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:output method="xml"/> 

  <xsl:template match="/">
    <HTML>
      <HEAD>
      </HEAD>
      <xsl:apply-templates/>
    </HTML>
  </xsl:template>
 
  <!-- document xsl:template -->

  <xsl:template match="booklist">
      <BODY BGCOLOR="#CCFFFF">
        <H1>List of books</H1>
        <P> This will illustrate the transformation of XML file containing list of books to a HTML table form </P>
        <xsl:apply-templates/>
      </BODY>
  </xsl:template>
  
  <xsl:template match="booklist/book">
	<BR/><xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="booklist/book/title">
      <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="booklist/book/author">
     <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="booklist/book/publisher">
  </xsl:template>

  <xsl:template match="booklist/book/price">
       Price: $<xsl:apply-templates/>
  </xsl:template>

  
</xsl:stylesheet>
