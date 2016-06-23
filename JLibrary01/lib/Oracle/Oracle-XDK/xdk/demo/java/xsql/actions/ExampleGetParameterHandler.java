package oracle.xml.xsql.actions;

//--------------------------------------------------------
// ExampleGetParameterHandler
// ~~~~~~~~~~~~~~~~~~~~~~~~~~
//
// Example custom handler implementation for getting
// HTTP Request parameters
//
// $Author: kkarun $
// $Date: 21-apr-00.16:36:47 $
// $Source: /vobs/oracore3/demo/xdk/java/xsql/demo/actions/ExampleGetParameterHandler.java.mkelem $
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
import javax.servlet.http.*;

public class ExampleGetParameterHandler extends XSQLActionHandlerImpl  {

  String name         = null;

  public void init (XSQLPageRequest env, Element e ) {
    super.init(env,e);
    name = getAttributeAllowingParam("name", e );
  }

  public void handleAction( Node rootNode ) throws SQLException {
    if (name != null) {
      String val = variableValue(name, getActionElement());
      if (val != null) {
        this.addResultElement(rootNode,name,val);
      }
    }
  }
}
