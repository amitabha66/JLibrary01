import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import java.net.URL;
import java.io.File;
import java.util.List;

public class SampleApp5
{
   public static void main(String args[]) throws Throwable
   {
     String fileName = "sample5.xml";
     String instancePath = "generated";
     JAXBContext jc = JAXBContext.newInstance(instancePath);
     Unmarshaller u = jc.createUnmarshaller();
     Object obj = u.unmarshal(fileToURL(fileName));

     Marshaller m = jc.createMarshaller();
     m.marshal(obj, System.out);
     process(obj, m);
   }

   public static void process(Object obj, Marshaller m) throws Throwable
   {
      generated.Base elem = (generated.Base)obj;

      List elemList = elem.getAOrBOrC();

      System.out.println("\n Contents of the List: " + "\n");
     
      generated.BaseType.A a1 = 
               (generated.BaseType.A)elemList.get(0);
      generated.BaseType.B b1 = 
               (generated.BaseType.B)elemList.get(1);
      generated.BaseType.C c1 = 
               (generated.BaseType.C)elemList.get(2);
      generated.BaseType.C c2 = 
               (generated.BaseType.C)elemList.get(3);
      generated.BaseType.B b2 = 
               (generated.BaseType.B)elemList.get(4);
      generated.BaseType.B b3 = 
               (generated.BaseType.B)elemList.get(5);
      generated.BaseType.C c3 =
               (generated.BaseType.C)elemList.get(6);
      generated.BaseType.A a2 = 
               (generated.BaseType.A)elemList.get(7);

      System.out.println("    a: " + a1.getValue() + "\n" +
                         "    b: " + b1.getValue() + "\n" +
                         "    c: " + c1.getValue() + "\n" +
                         "    c: " + c2.getValue() + "\n" +
                         "    b: " + b2.getValue() + "\n" +
                         "    b: " + b3.getValue() + "\n" +
                         "    c: " + c3.getValue() + "\n" +
                         "    a: " + a2.getValue() + "\n" +
                         "\n"); 
      
      //System.out.println("\n The contents of the element are changed!\n");
      //m.marshal(elem, System.out);
   }
   /**
    * This method changes the file path to URL path
    */
   private static URL fileToURL(String sfile)
   {
      File file = new File(sfile);
      String path = file.getAbsolutePath();
      String fSep = System.getProperty("file.separator");
      if (fSep != null && fSep.length() == 1)
         path = path.replace(fSep.charAt(0), '/');
      if (path.length() > 0 && path.charAt(0) != '/')
        path = '/' + path;
      try
      {
         return new URL("file" , null, path);
      }
      catch (java.net.MalformedURLException e)
      {
         throw new Error("Error: Unexpected MalformedURLException");
      }
   }

}
