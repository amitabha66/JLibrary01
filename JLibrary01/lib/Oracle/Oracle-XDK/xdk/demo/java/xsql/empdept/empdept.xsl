<?xml version="1.0"?>
<html xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xsl:version="1.0" >
  <!-- This is an example of a single-template stylesheet -->
  <head><link rel="stylesheet" type="text/css" href="coolcolors.css" />
  </head>
  <body class="page">
    <center>
      <table border="0" class="title">
	<xsl:for-each select="/ROWSET/ROW">
	  <tr>
	      <td><xsl:value-of select="DEPTNO"/></td>
	      <td><xsl:value-of select="DNAME"/></td>
	      <td><xsl:value-of select="LOC"/></td>
	  </tr>
	  <xsl:choose>
	    <xsl:when test="not(EMPLOYEES/EMPLOYEES_ITEM)">
	     <tr>
               <td colspan="3" width="100%">
		 <table border="0" width="100%" class="bl">
		   <tr>
		     <td align="center" width="100%"><i>No Employees</i></td>
		   </tr>
		 </table>
               </td>
    	     </tr>
	    </xsl:when>
	    <xsl:otherwise>
	      <xsl:for-each select="EMPLOYEES/EMPLOYEES_ITEM">
	      <tr>
		  <td colspan="3" width="100%">
		      <table border="0" width="100%" class="bl">
			  <tr>
			      <td><xsl:value-of select="EMPNO"/></td>
			      <td align="left"><xsl:value-of select="ENAME"/></td>
			      <td align="right"><xsl:value-of select="SAL"/></td>
			  </tr>
		      </table>
		  </td>
	      </tr>
	      </xsl:for-each>
	    </xsl:otherwise>
	  </xsl:choose>
	</xsl:for-each>
      </table>
    </center>
  </body>
</html>
