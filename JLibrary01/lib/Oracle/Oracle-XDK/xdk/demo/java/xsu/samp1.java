/** Simple example on using Oracle XMLSQL API; this class queries the database with "select * from emp" in scott/tiger schema; then from the results of query it generates an XML document */


import java.sql.*;
import java.math.*;
import oracle.xml.sql.query.*;
import oracle.jdbc.*;
import oracle.jdbc.driver.*;

public class samp1
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

    // create statement and execute it to get the ResultSet
    Statement stmt = conn.createStatement();
    ResultSet rset = stmt.executeQuery("select * from "+tabName );
    
    // init the OracleXMLQuery; note we could have passed the sql query string
    // instead of the ResultSet
    OracleXMLQuery qry =  new OracleXMLQuery(conn,rset);

    // get the XML document is the string format
    String xmlString = qry.getXMLString();
    
    // print out the result
    System.out.println(" OUPUT IS:\n"+xmlString); 
  }

} 
