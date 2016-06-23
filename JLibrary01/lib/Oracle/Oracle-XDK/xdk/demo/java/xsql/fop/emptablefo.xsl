<?xml version="1.0"?>
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" xsl:version="1.0"
         xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


  <!-- defines the layout master -->
  <fo:layout-master-set>
    <fo:simple-page-master master-name="first" 
                           page-height="29.7cm" 
                           page-width="21cm" 
                           margin-top="1cm" 
                           margin-bottom="2cm" 
                           margin-left="2.5cm" 
                           margin-right="2.5cm">
      <fo:region-body margin-top="3cm"/>
    </fo:simple-page-master>
  </fo:layout-master-set>

  <!-- starts actual layout -->
  <fo:page-sequence master-reference="first">

  <fo:flow flow-name="xsl-region-body">

      <fo:block font-size="24pt" font-family="Garamond" line-height="24pt" space-after.optimum="3pt" font-weight="bold" start-indent="15pt">
        Total of All Salaries is $<xsl:value-of select="sum(/ROWSET/ROW/SAL)"/>
      </fo:block>

      <!-- Here starts the table -->
      <fo:block border-width="2pt">
        <fo:table>
          <fo:table-column column-width="4cm"/>
          <fo:table-column column-width="4cm"/>
          <fo:table-body font-size="10pt" font-family="sans-serif">
            <xsl:for-each select="ROWSET/ROW">
              <fo:table-row line-height="12pt">
                <fo:table-cell>
                  <fo:block><xsl:value-of select="ENAME"/></fo:block>
                </fo:table-cell>
                <fo:table-cell>
                  <fo:block><xsl:value-of select="SAL"/></fo:block>
                </fo:table-cell>
              </fo:table-row>
            </xsl:for-each>
          </fo:table-body>
        </fo:table>
      </fo:block>
    </fo:flow>
  </fo:page-sequence>
</fo:root>
