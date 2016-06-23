import java.sql.*;
import java.util.*;
import java.io.*;
import oracle.jdbc.driver.*;
import oracle.xml.sql.query.*;
import oracle.xml.xmldbaccess.*;
import oracle.xml.parser.v2.*;



/* This demo tests the xmldbaccess bean which uses 
   XMLType storage to store XML documents inside the database.
   To run this test a Oracle 9i database or higher is required along 
   with the xdb.jar file which defines the XMLType class 
*/

public class XMLDBAccessSample
{

  public static void main (String[] args) 
  {
    if (args.length < 5)
       System.err.println("Usage: \n java XMLDBAccessSample <hostname> <port> <sid> <userid> <passwd>");
    XMLDBAccessSample sample =  new XMLDBAccessSample(args[0], args[1], 
                                                    args[2], args[3], args[4]);
    sample.createXMLTypeTable("testxmltype");
    sample.listXMLTypeTables();
    sample.replaceXMLTypeData("testxmltype", "mybooklist", "booklist.xml");
    sample.getXMLTypeData("testxmltype", "mybooklist");
    sample.getXMLTypeXPathTextData("testxmltype", "mybooklist", "/booklist/book[3]");
    sample.finishDemo();
  }

  XMLDBAccessSample(String h, String p, String i, String u, String pw)
  {
    hostname = h;
    port = p;
    instancename = i;
    username = u;
    password = pw;  
    
    try
    {
       log = new PrintWriter (new FileWriter(logname));
       makeConnection();
    }catch(Exception e)
    {
       e.printStackTrace(System.out);
    }
    db = new XMLDBAccess();
  }

  public void finishDemo()
  {
     try
     {
        closeConnection();
        log.flush();
        log.close();
     }catch (Exception e)
     {
        e.printStackTrace(System.out);
     }
  }   

  void createXMLTypeTable(String tableName)
  {
      log.println("\nDemo for createXMLTypeTables():");
      try {
         db.createXMLTypeTable(con, tableName);
      } catch (Exception ex) {
          log.println("Error creating XMLType table: " + ex.getMessage()); 
      }
      log.println("Table +'" + tableName + "' successfully created.");
      return;
  }

  void replaceXMLTypeData( String tablename, String xmlname, String filename)
  {
      log.println("\nDemo for replaceXMLTypeData() (similar to insert):");
      String xmldata = loadFile(filename);
      try 
      {
         db.replaceXMLTypeData(con, tablename, xmlname, xmldata);
      } catch (Exception ex) {
          log.println("Error inserting into XMLType table: " + ex.getMessage());
      }
      log.println("XML Data from +'" + filename + "' successfully replaced in table '" 
                  + tablename + "'.");
      return;
  }

  void getXMLTypeData(String tablename, String xmlname)
  {
      log.println("\nDemo for getXMLTypeData():");
      String xmlText=null;
      try 
      {
         xmlText=db.getXMLTypeData(con, tablename, xmlname);
      } catch (Exception ex) 
      {
         log.println("Error getting XMLType data: " + ex.getMessage());
        return;
      }
      log.println("XMLType data fetched: ");
      log.println(xmlText);
      return;
  }

  
  void listXMLTypeTables() 
  {
      // list all tables that are in XMLType format recognized
      // by this tool
      log.println("\nDemo for listXMLTypeTables():");
      String tableNames[];
      int i;

      try 
      {
         tableNames=db.getXMLTypeTableNames(con,"");
         
         for (i=0;i<tableNames.length;i++) 
         {
            log.println("tablenamename="+tableNames[i]);
         }
      } catch (Exception ex) 
      {
         log.println("Error listing XMLType tables: " + ex.getMessage());   
      }
  }

  void getXMLTypeXPathTextData(String tablename, String xmlname, String xpathexp)
  {
     String text=null;
     log.println("\nDemo for getXMLTypeXPathTextData():");
     try
     {
        text = db.getXMLTypeXPathTextData(con, tablename, xmlname, xpathexp);
     }catch (Exception ex)
     {
        log.println("Error getting XPath data: " + ex.getMessage());
     }   
     log.println("Data fetched using XPath exp '" + xpathexp + "':");
     log.println(text);     

  }   
     
  void closeConnection() throws SQLException 
  {
    if  (con != null) {
        con.close();
        con=null;
    }
  }

  void makeConnection() throws SQLException 
  {

    String thinConn= "jdbc:oracle:thin:@"+
                               hostname+":"+
                               port+":"+
                               instancename;
    String defaultConn = "jdbc:oracle:kprb:";
    Driver drv;
    if  (con != null) {
        con.close();
        con=null;
    }
    // Load the Oracle JDBC driverm (alternative method)
    //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    try {
      drv = (Driver)Class.forName(driverclass).newInstance();
    }
    catch (Exception e) {
      throw new SQLException("Error Loading JDBC Driver "+driverclass);
    }
    if (insideOracle()) 
    {
         con=DriverManager.getConnection(defaultConn,username,password);
    } else 
    {
         con=DriverManager.getConnection(thinConn,username,password);
    }
  }


  boolean insideOracle() 
  {
    // If oracle.server.version is non null, we're running in the database
    String ver = System.getProperty("oracle.server.version");
    return (ver != null && !ver.equals(""));
  }

  String loadFile (String fileName) {
     StringBuffer textBuffer=new StringBuffer(1000);
     String line="";
     textBuffer.append("");
     try {
        BufferedReader reader=new BufferedReader(new FileReader(fileName));
        while (true) {
           line=reader.readLine();
           if (line==null) break;
           textBuffer.append(line);
        }
        return textBuffer.toString();
     } catch (Exception ex1) {
        return "";
     }
  }


  String username;
  String password;
  String hostname;
  String port;
  String instancename;
  XMLDocument xmlDoc=null;
  String xmlFileName=null;
  String SQLText="";
  String dataSourceString="";

  String driverclass   = "oracle.jdbc.driver.OracleDriver";
  Connection con=null;

  String logname = "xmldbaccess.log";
  PrintWriter log = null;
  XMLDBAccess db = null;
}
