/** Simple example on using Oracle XMLSQL API; this class queries the database with "select * from emp" in scott/tiger schema; then from the results of query it generates an XML document.  Here we also demonstrate the API's features enabeling the user to customize/shape the generated XML doc.*/


import java.sql.*;
import java.math.*;
import oracle.xml.sql.query.*;
import oracle.jdbc.*;
import oracle.jdbc.driver.*;

public class samp2
{

  //========================================
  //  main()  -  public static void
  public static void main(String args[]) throws SQLException
  {

    String tabName = "emp";
    String user = "scott/tiger";

    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

    //init a JDBC connection
    Connection conn = 
      DriverManager.getConnection("jdbc:oracle:oci8:"+user+"@");

    // init the OracleXMLQuery
    OracleXMLQuery qry =  new OracleXMLQuery(conn,"select * from "+tabName );

    //------------
    // shape the XML doc generated

    qry.setMaxRows(2);	       // set the maximum number of rows to be returned
    qry.setSkipRows(3);				  // numbers of rows to skipped
    qry.setRowsetTag("MYDOC");	    // set the tags encapsulating the whole doc
    qry.setRowTag("RECORD");			  // sets the row separator tag
    qry.setStylesheetHeader("my.xsl");			// the stylesheet spec.
    qry.setRowIdAttrName("cnt");    // sets the id attribute of the row element
    qry.useNullAttributeIndicator(true);      // use attr. to indicate nullness
    qry.useUpperCaseTagNames();			    // use upper case tag names
    qry.setErrorTag("ERR");		    // tag for errors writen to XML doc
    



    // get the XML document is the string format
    String xmlString = qry.getXMLString();
    
    // print out the result
    System.out.println(" OUPUT IS:\n"+xmlString); 
  }

} 
