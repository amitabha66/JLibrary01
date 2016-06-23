/** Simple example on using Oracle XMLSQL API; this class inserts the data from a XML document into the database*/


import oracle.xml.sql.dml.*;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.jdbc.*;
import java.net.*;

public class samp10
{

  //========================================
  //  main()  -  public static void
  public static void main(String args[]) throws SQLException
  {

    String tabName = "XMLTEST_TAB1";		  // table into which to insert
    String fileName = "sampdoc.xml";	    // file name containing the xml doc

    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

    //init a JDBC connection
    Connection conn = 
      DriverManager.getConnection("jdbc:oracle:oci8:scott/tiger@");

    OracleXMLSave sav = new OracleXMLSave(conn, tabName);

    URL url = sav.getURL(fileName);
    int rowCount = sav.insertXML(url);

    System.out.println(" successfully inserted "+rowCount+
		       " rows into "+ tabName);
    conn.close();
  }
}
