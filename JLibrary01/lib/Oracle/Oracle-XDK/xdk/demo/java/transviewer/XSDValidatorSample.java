import oracle.xml.parser.v2.*;
import oracle.xml.parser.schema.XMLSchema;
import oracle.xml.parser.schema.XSDBuilder;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.*;
import java.util.*;
import java.util.Stack;
import java.util.Vector;
import java.util.Enumeration;
import oracle.xml.schemavalidator.*;
import oracle.xml.parser.schema.XSDException;

class XSDValidatorSample 
{

  public static void main(String[] args)
  {   
        
    URL xmlinstanceurl, schemaurl;

    XMLDocument xmldoc1,xmldoc2;

    //Two arguments required: The Instance document & the XML Schema document
    if (args.length < 2)
      {
        System.err.println("Usage: java XSDValidatorSample <xml-file> <schema-file>)");
        return;
      }

    try 
      {
        // Set properties to turn on the error messaging
        Properties p = new Properties(System.getProperties());
        p.load(new FileInputStream("demo.properties"));
        System.setProperties(p);

        XMLSchema xmlschema = new XMLSchema();   
        XSDValidator xsdval = new XSDValidator();
        XMLError err = new XMLError();
   
        // Set the error Handler
        err.setErrorHandler(new DocErrorHandler());
    
        XSDBuilder xsdbuild =  new XSDBuilder();

        // get the URL for the input files
        xmlinstanceurl = createURL(args[0]);


        // Parse the XML Instance document first
        xmldoc1 = parseXMLDocument(xmlinstanceurl);

        // Parse the schema document
        schemaurl = createURL(args[1]);
        xmldoc2 = parseXMLDocument(schemaurl);

        // Build the schema object from the parsed document
        xmlschema = (XMLSchema)xsdbuild.build(xmldoc2,
                                              createURL(args+"builder"));

        // Now do the validation

        // Set the error object
        xsdval.setError(err);  

        // Set the XMLSchema
        xsdval.setSchema(xmlschema);

        // Do the validation
        xsdval.validate(xmldoc1);

        // Now print the paths from the root to the error nodes

        printError(xsdval);
      }
    catch (Exception e) {
      e.printStackTrace();
    }

  }



  /* Method to create URL for the file. Used for Parsing */


  static URL createURL(String fileName)
  {
    URL url = null;
    try
      {
        url = new URL(fileName);
      }
    catch (MalformedURLException ex)
      {
        File f = new File(fileName);
        try
          {
            String path = f.getAbsolutePath();
            String fs = System.getProperty("file.separator");
            if (fs.length() == 1)
              {
                char sep = fs.charAt(0);
                if (sep != '/')
                  path = path.replace(sep, '/');
                if (path.charAt(0) != '/')
                  path = '/' + path;
              }
            path = "file://" + path;
            url = new URL(path);
          }
        catch (MalformedURLException e)
          {
            System.out.println("Cannot create url for: " + fileName);
            System.exit(0);
          }
      }
    return url;
  }



  /* This method dumps all the info with regard to the validation errors */

  static void printError(XSDValidator xsdval)
  {
    Vector vectPath = xsdval.getStackList();
    DocErrorHandler errHndl = (DocErrorHandler)xsdval.getError().getErrorHandler();
    Vector errlist =  errHndl.getErrorList();
    if (vectPath.isEmpty())
    {
       System.out.println("Schema validation successful! No errors.");
    }
    if (!(vectPath.isEmpty()))
      {
        Stack tempStack ;
        XMLNode xnode;
        Enumeration enum1 = vectPath.elements();
        Enumeration enum2 = errlist.elements();

        while (enum1.hasMoreElements() && enum2.hasMoreElements())
          {
            System.out.println(enum2.nextElement());
     
            tempStack = (Stack)enum1.nextElement();
            while (!(tempStack.empty()))
              {
                xnode = (XMLNode)((tempStack).pop());
                System.out.print(xnode.getNodeName());
                if (!(tempStack.empty()))
                  System.out.print("->");
              }
            System.out.println();
          }
      } 
  }


  /* Parse the XML Document */


  static XMLDocument parseXMLDocument(URL xmlurl)
  {
    try {
      DOMParser parser = new DOMParser();
      parser.parse(xmlurl);
      return parser.getDocument();
    }
    catch (Exception e)
      {
        return null;
      }
    
  }
}
