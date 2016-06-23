<?xml version="1.0" ?>
<!--
| $Author: kkarun $
| $Date: 20-apr-00.23:37:31 $
| $Source: /vobs/oracore3/demo/xdk/java/xsql/demo/airport/airport.xsl.mkelem $
| $Revision: /main/0 $
+-->
<html xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xsl:version="1.0">
<head>
<title>Airport</title>
</head>

<body>

<form action="airport.xsql" method="get">
<input type="hidden" name="xml-stylesheet" value="airport.xsl"/>
<span style="font-family:Arial; font-size: 18pt">Enter an airport code (or part of the city name): </span>

<input style="font-family:Arial; font-size: 18pt" name="airport" type="text" size="8" /> 

</form>


<table border="0" width="100%" cellspacing="5">
  
  <tr>
    <td width="17%" bgcolor="#FFFF80" align="left">
      <font face="Arial" size="5" color="#000080">
        <strong>Airport</strong>
      </font>
    </td>
    <td width="83%" bgcolor="#FFFF80" align="left">
      <font face="Arial" size="5" color="#000080">
        <strong>Description</strong>
      </font>
    </td>
  </tr>
  <xsl:for-each select="*/Airport">
  <tr>
    <td width="17%" bgcolor="#80FFFF">
      <font face="Arial" size="5" color="#FF0000">
        <strong>
          <xsl:value-of select="Code"/>
        </strong>
      </font>
    </td>
    <td width="83%" bgcolor="#80FFFF">
      <font face="Arial" size="5" color="#FF0000">
        <strong>
          <xsl:value-of select="Description"/>
        </strong>
      </font>
    </td>
  </tr>
  </xsl:for-each>
</table>
</body>
</html>
