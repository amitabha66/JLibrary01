<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:key name="action-name" match="element" use="@name"/> 

  <xsl:param name="name"/>

  <xsl:output method="html" indent="no"/>

  <xsl:template match="page-content[@name='Help']">
      <table class="x" width="100%" border="0">
        <tr>
          <td nowrap="" valign="top" width="200">
            <xsl:apply-templates select="element" mode="list">
              <xsl:sort select="@name"/>
            </xsl:apply-templates>
          </td>
          <td valign="top">
            <xsl:apply-templates select="key('action-name',$name)"/>
          </td>
        </tr>
      </table>
  </xsl:template>

  <xsl:template match="help">
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="a">
    <a style="text-decoration:underline" href="{@href}"><xsl:apply-templates/></a>
  </xsl:template>

  <xsl:template match="lit">
    <span class="code"><xsl:apply-templates/></span>
  </xsl:template>

  <xsl:template match="element">
    <xsl:apply-templates select="help"/>
      <br/>
      <table border="0" width="100%">
         <tr>
           <th align="left">&#160;Attributes</th>
         </tr>
      </table>
      <br/>
    <xsl:choose>
      <xsl:when test="attribute">
        <table border="0" cellspacing="0" cellpadding="2">
          <xsl:apply-templates select="attribute">
            <xsl:sort select="@required" order="descending"/>
            <xsl:sort select="@name"/>
          </xsl:apply-templates>
        </table>
      </xsl:when>
      <xsl:otherwise>
        <i>None</i><br/>
      </xsl:otherwise>
    </xsl:choose>
    <br/>
    <table border="0" width="100%">
       <tr>
         <th align="left">
           <xsl:text>&#160;Example</xsl:text>
           <xsl:if test="count(eg) != 1">
             <xsl:text>s</xsl:text>
           </xsl:if>
         </th>
       </tr>
    </table>
    <br/>
    <xsl:choose>
      <xsl:when test="eg">
        <xsl:apply-templates select="eg"/>
      </xsl:when>
      <xsl:otherwise>
        <i>None</i>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="element" mode="list">
    <a href="{$thispage}?elt={@name}">
    <xsl:choose>
      <xsl:when test="@name = $name">
        <span style="background:#FFA">
        <b>
        <xsl:call-template name="tagname">
          <xsl:with-param name="title" select="'Y'"/>
        </xsl:call-template>
        </b>
        </span>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="tagname">
          <xsl:with-param name="title" select="'Y'"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
    </a>
    <br/>
  </xsl:template>

  <xsl:template match="element" mode="poplist">
    <a href="{$thispage}?elt={@name}">
    <xsl:choose>
      <xsl:when test="@name = $name">
        <span style="background:#FFA">
        <b>
        <xsl:call-template name="tagname">
          <xsl:with-param name="title" select="'Y'"/>
        </xsl:call-template>
        </b>
        </span>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="tagname">
          <xsl:with-param name="title" select="'Y'"/>
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
    </a>
    <br/>
  </xsl:template>

  <xsl:template match="attribute">
    <tr>
      <td valign="top" align="right" nowrap="">
        <xsl:if test="position() mod 2 = 1">
          <xsl:attribute name="class">h</xsl:attribute>
        </xsl:if>
        <xsl:call-template name="attrname"/>
      </td>
      <td valign="top">
        <xsl:if test="position() mod 2 = 1">
          <xsl:attribute name="class">h</xsl:attribute>
        </xsl:if>        <xsl:text>"</xsl:text>
        <i><xsl:value-of select="@type"/></i>
        <xsl:text>"</xsl:text>
      </td>
      <td valign="top">
        <xsl:if test="position() mod 2 = 1">
          <xsl:attribute name="class">h</xsl:attribute>
        </xsl:if>
        <xsl:apply-templates select="help"/>
        <xsl:if test="@type='boolean'">
          <xsl:text>Valid values are 'yes' and 'no'.</xsl:text>
          <xsl:text>(Default value is '</xsl:text>
          <xsl:value-of select="@default"/>
          <xsl:text>')</xsl:text>
        </xsl:if>
      </td>
    </tr>
  </xsl:template>

  <xsl:template match="eg">
    <xsl:if test="position() > 1">
      <br/><br/>
    </xsl:if>
    <span class="code">
      <xsl:if test="@comment">
        <font class="comment">
          <xsl:text><![CDATA[<!-- ]]></xsl:text>
          <xsl:value-of select="@comment"/>
          <xsl:text> --></xsl:text>
        </font>
        <br/>
      </xsl:if>
      <xsl:call-template name="br-replace">
        <xsl:with-param name="word">
          <xsl:call-template name="sp-replace">
            <xsl:with-param name="word" select="."/>
          </xsl:call-template>
        </xsl:with-param>
      </xsl:call-template>
    </span>
  </xsl:template>

  <xsl:template name="attrname">
    <xsl:choose>
      <xsl:when test="@required='yes'">
       <b><xsl:value-of select="@name"/></b>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="@name"/>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:text> =</xsl:text>
  </xsl:template>

  <xsl:template name="tagname">
    <xsl:param name="title" select="'N'"/>
    <xsl:param name="element" select="."/>
      <xsl:text>&lt;xsql:</xsl:text>
      <xsl:value-of select="$element/@name"/>
    <xsl:choose>
      <xsl:when test="$title">
          <xsl:text>&gt;</xsl:text>
      </xsl:when>
      <xsl:when test="not($element/content)">
          <xsl:text>/&gt;</xsl:text>
      </xsl:when>
      <xsl:otherwise>
          <xsl:text>/&gt;</xsl:text>
        <br/>
        <xsl:value-of select="$element/content/@type"/><br/>
	      <xsl:text>&lt;/xsql:</xsl:text>
	      <xsl:value-of select="$element/@name"/>
	      <xsl:text>&gt;</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>


  <xsl:template name="br-replace">
    <xsl:param name="word"/>
    <xsl:variable name="cr"><xsl:text>&#xa;</xsl:text></xsl:variable>
    <xsl:variable name="sp"><xsl:text> </xsl:text></xsl:variable>
    <xsl:choose>
      <xsl:when test="contains($word,$cr)">
        <xsl:value-of select="substring-before($word,$cr)"/>
        <br/>
        <xsl:call-template name="br-replace">
          <xsl:with-param name="word" select="substring-after($word,$cr)"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$word"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template name="sp-replace">
    <xsl:param name="word"/>
    <xsl:variable name="sp"><xsl:text> </xsl:text></xsl:variable>
    <xsl:choose>
      <xsl:when test="contains($word,$sp)">
        <xsl:value-of select="substring-before($word,$sp)"/>
        <xsl:text>&#160;</xsl:text>
        <xsl:call-template name="sp-replace">
          <xsl:with-param name="word" select="substring-after($word,$sp)"/>
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$word"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

</xsl:stylesheet>


