<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:import href="../common/rowcol.xsl"/>
  
  <xsl:template match="TITLE">
    <a target="right" href="doc.xsql?id={../DOCID}"><xsl:value-of select="."/></a>
  </xsl:template>

  <xsl:template match="FORMATTED">
    <a target="right" href="docstyle.xsql?id={../DOCID}&amp;xml-stylesheet={.}"><xsl:value-of select="."/></a>
  </xsl:template>

  <xsl:template match="DOCID">
    <a target="right" href="doc.xsql?id={../DOCID}"><xsl:value-of select="."/></a>
  </xsl:template>

<xsl:template match="/">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../common/rowcol.css" />
<title>Enter a new XML Doc into my CLOB Table</title>
   <script>
    function f() {
      var val = theform.doc.value;
      var test = new ActiveXObject("Microsoft.XMLDOM");
      test.async = false;
      test.loadXML(val);
      err = test.parseError;
      if (err != 0) {
       ErrorMsg =  "Your XML Document is not Well Formed.\n" +
                    err.srcText + "\n" + "Line " + err.line + ", Pos " + 
                    err.linepos + "\n" + err.reason;
       alert(ErrorMsg);
       test = null;
       event.returnValue  = false;
      }
      else {
        if (val.length > 23500) {
          alert("Your document is " + val.length + " bytes long. "+
                "Please limit your posted XML document" +
                " to 20K for this demonstration. Thanks!");
          event.returnValue = false;
        }
      }
    }
   </script>
</head>

<body>
<xsl:if test="//xsql-error">
  <table style="background:yellow">
  <xsl:for-each select="//xsql-error">
    <tr>
      <td><b>Action</b></td>
      <td><xsl:value-of select="@action"/></td>
    </tr>
    <tr valign="top">
      <td><b>Message</b></td>
      <td><xsl:value-of select="message"/></td>
    </tr>
  </xsl:for-each>
  </table>
</xsl:if>
<form id="theform" method="POST" action="newclobins.xsql">
  <table border="0">
    <tr>
      <td align="right">&#160;</td>
      <td>  <input type="submit" value="Validate and Insert Document" onclick="f()"/></td>
    </tr>
    <tr>
      <td align="right"><strong>Author</strong></td>
      <td><input type="text" name="author" size="20"/></td>
    </tr>
    <tr>
      <td align="right"><strong>Title</strong></td>
      <td><input type="text" name="title" size="40"/></td>
    </tr>
    <tr>
      <td align="right"><strong>Stylesheet</strong></td>
      <td><input type="text" name="stylesheet" size="40"/></td>
    </tr>
    <tr>
      <td align="right"><strong>XML Document</strong></td>
      <td><textarea name="doc" size="20" rows="6" cols="35">Type Your XML Document Here...</textarea></td>
    </tr>
  </table>
</form>
<xsl:apply-templates select="/page/data/ROWSET"/>
</body>
</html>
</xsl:template>
</xsl:stylesheet>



