import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import java.net.URL;
import java.io.File;

public class SampleApp4
{
   public static void main(String args[]) throws Throwable
   {
     String fileName = "sample4.xml";
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
      generated.PurchaseOrder elem = 
                 (generated.PurchaseOrder)obj;

      generated.USAddress saddr = elem.getShipTo();
      generated.USAddress baddr = elem.getBillTo();

      System.out.println("\n Contents of shipTo: " + "\n" +
                         "   name:  " + saddr.getName() + "\n" +
                         "   street:" + saddr.getStreet() + "\n" +
                         "   city:  " + saddr.getCity() + "\n" +
                         "\n");

      System.out.println("\n Contents of billTo: " + "\n" +
                         "   name:  " + baddr.getName() + "\n" +
                         "   street:" + baddr.getStreet() + "\n" +
                         "   city:  " + baddr.getCity() + "\n" +
                         "\n");
      
 
      System.out.println("\n The contents of the shipTo and billTo are changed!\n");

      saddr.setName("Divya Shekar");
      saddr.setStreet("R.A Puram");
      saddr.setCity("Madras");

      baddr.setName("Girija Shekah");
      baddr.setStreet("Grand Trunck Road");
      baddr.setCity("Calcutta");

      elem.setShipTo(saddr);
      elem.setBillTo(baddr);

      m.marshal(elem, System.out);

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
