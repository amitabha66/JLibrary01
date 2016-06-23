<?xml version="1.0"?> 

<!-- Test for simple identity transformation -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<!-- Any first child -->
<xsl:template match="//*[1]">
first child:<xsl:value-of select="@id"/>
<xsl:apply-templates/>
</xsl:template>

<!-- Any second child -->
<xsl:template match="*[1]//*[2]">
second child:<xsl:value-of select="@id"/>
<xsl:apply-templates/>
</xsl:template>

<!-- Any third child -->
<xsl:template match="//*[3][@id]">
third child:<xsl:value-of select="@id"/>
<xsl:apply-templates/>
</xsl:template>

<!-- Any fourth child -->
<xsl:template match="//*[4]">
fourth child:<xsl:value-of select="@id"/>
<xsl:apply-templates/>
</xsl:template>


<xsl:template match="*[2]//*[2]//*[2]">
Should select only "id12":<xsl:value-of select="@id"/>
<xsl:apply-templates/>
</xsl:template>

<xsl:template match="*[1]//*[1]//*[2]">
Should select only "id4" and "id6":<xsl:value-of select="@id"/>
<xsl:apply-templates/>
</xsl:template>

<xsl:template match="//*[2]//*[1]">
Should select only "id9 and id11":<xsl:value-of select="@id"/>
<xsl:apply-templates/>
</xsl:template>


<xsl:template match="text()"/>

</xsl:stylesheet>