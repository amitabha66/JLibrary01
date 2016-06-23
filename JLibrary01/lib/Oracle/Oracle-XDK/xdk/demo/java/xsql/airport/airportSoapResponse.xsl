<SOAP-ENV:Envelope xsl:version="1.0" 
                   xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                   SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"
                   xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/"
                   xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
                   xmlns:xsd="http://www.w3.org/1999/XMLSchema"
                   xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance">
  <SOAP-ENV:Body>
    <xsl:variable name="result" select="/page/rowset/row"/>
    <xsl:choose>
      <!-- If a row was found, Include the SOAP encoded response in the body -->
      <xsl:when test="$result">
        <service:getAirportDescResponse xmlns:service="http://www.airport.com/services">
          <Result xsi:type="xsd:string">
            <xsl:value-of select="$result/description"/>
          </Result>
        </service:getAirportDescResponse>
      </xsl:when>
      <!-- Otherwise no row was found, so send a SOAP Fault -->
      <xsl:otherwise>
        <SOAP-ENV:Fault>
          <faultcode>AirportNotFound</faultcode>
          <faultstring>
            <xsl:text>The airport code '</xsl:text>
            <xsl:value-of select="/page/code"/>
            <xsl:text>' is invalid</xsl:text>
          </faultstring>
        </SOAP-ENV:Fault>
      </xsl:otherwise>
    </xsl:choose>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
