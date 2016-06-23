<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>

<xsl:template match="/">
<DIV>
<B><xsl:text> Length of string:</xsl:text>
<xsl:value-of select="//text"/>=<xsl:value-of 
select="string-length(//text)"/></B>
</DIV>
<DIV>
<B><xsl:text>Text: </xsl:text></B>
<xsl:value-of select="//text"/> 
</DIV>
<B><xsl:text>Text before </xsl:text>
<xsl:value-of select="//string"/>
<xsl:text>: </xsl:text></B>
<xsl:value-of select="substring-before(//text,//string)"/>
<DIV>
<B><xsl:text>Text after </xsl:text>
<xsl:value-of select="//string"/>
<xsl:text>: </xsl:text></B>
<xsl:value-of select="substring-after(//text,//string)"/>
</DIV>
<DIV>
<B><xsl:text>Text from position </xsl:text>
<xsl:value-of select="//start"/>
<xsl:text>: </xsl:text></B>
<xsl:value-of select="substring(//text,//start)"/>
</DIV>
<DIV>
<B><xsl:text>Text from position </xsl:text>
<xsl:value-of select="//start"/>
<xsl:text> of length  </xsl:text>
<xsl:value-of select="//end"/>
<xsl:text>: </xsl:text></B>
<xsl:value-of select="substring(//text,//start,//end)"/>
</DIV>

<DIV>
<B><xsl:text>Text from position </xsl:text>
<xsl:value-of select="//start * -1"/>
<xsl:text>: </xsl:text></B>
<xsl:value-of select="substring(//text,//start * -1)"/>
</DIV>
<DIV>
<B><xsl:text>Text from position </xsl:text>
<xsl:value-of select="//start + 0.45"/>
<xsl:text>: </xsl:text></B>
<xsl:value-of select="substring(//text,//start + 0.45)"/>
</DIV>
<DIV>
<B><xsl:text>Text from position </xsl:text>
<xsl:value-of select="//start *-2"/>
<xsl:text> of length  </xsl:text>
<xsl:value-of select="//end *1.5"/>
<xsl:text>: </xsl:text></B>
<xsl:value-of select="substring(//text,//start * -2,//end * 1.5)"/>
</DIV>
<DIV>
<B><xsl:text>Text from position </xsl:text>
<xsl:value-of select="//start + 0.4"/>
<xsl:text> of length  </xsl:text>
<xsl:value-of select="//end  div 10 + 0.7"/>
<xsl:text>: </xsl:text></B>
<xsl:value-of select="substring(//text,//start + 0.4,//end div 10 + 0.7)"/>
</DIV>
<DIV>
<B><xsl:text>Text from position </xsl:text>
<xsl:value-of select="//start + 0.4"/>
<xsl:text> of length  </xsl:text>
<xsl:value-of select="//end  div 10 + 0.2"/>
<xsl:text>: </xsl:text></B>
<xsl:value-of select="substring(//text,//start + 0.4,//end div 10 + 0.2)"/>
</DIV>
<DIV>
<B><xsl:text>Boundary conditions </xsl:text></B>
<xsl:text> 'Hello' (4, -1) should return '': </xsl:text>
<xsl:value-of select="substring('Hello', 4, -1)"/>
</DIV>
<DIV>
<B><xsl:text>Boundary conditions </xsl:text></B>
<xsl:text> 'Hello' (42, 12) should return '': </xsl:text>
<xsl:value-of select="substring('Hello', 42, 12)"/>
</DIV>
<DIV>
<B><xsl:text>Boundary conditions </xsl:text></B>
<xsl:text> 'Hello' (6, 1) should return '': </xsl:text>
<xsl:value-of select="substring('Hello', 6, 1)"/>
</DIV>
<DIV>
<B><xsl:text>Boundary conditions </xsl:text></B>
<xsl:text> 'Hello' (-6, -1) should return '': </xsl:text>
<xsl:value-of select="substring('Hello', -6, -1)"/>
</DIV>
</xsl:template>
</xsl:stylesheet>