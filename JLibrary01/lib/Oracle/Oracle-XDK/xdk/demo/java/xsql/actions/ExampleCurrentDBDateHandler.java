package oracle.xml.xsql.actions;

//--------------------------------------------------------
// ExampleCurrentDBDateHandler
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
// Example custom handler implementation
//
// $Author: kkarun $
// $Date: 21-apr-00.16:36:37 $
// $Source: /vobs/oracore3/demo/xdk/java/xsql/demo/actions/ExampleCurrentDBDateHandler.java.mkelem $
// $Revision: /main/0 $
//
// History
// -------------------------
// 26-NOV-99 smuench Created
//
//--------------------------------------------------------

import oracle.xml.xsql.*;
import org.w3c.dom.*;
import java.sql.*;

public class ExampleCurrentDBDateHandler extends XSQLActionHandlerImpl  {

  String statement    = null;
  ResultSet rs = null;
  Statement s  = null;

  public void handleAction( Node rootNode ) throws SQLException {

    statement = "select to_char(sysdate,'dd-Mon-yyyy hh24:mi:ss') from dual";

    if (!requiredConnectionProvided(rootNode)) {
      return;
    }

    try {
      s = getPageRequest().getJDBCConnection().createStatement();
      rs = s.executeQuery(statement);
      String curDate = "";
      if (rs.next()) {
        curDate = rs.getString(1);
      }

      addResultElement(rootNode,"Current-Date",curDate);

      rs.close();
      s.close();
    }
    catch (SQLException exs) {
      this.reportErrorIncludingStatement(rootNode,statement,exs.getMessage());
      rs.close();
      s.close();
    }
  }

}
