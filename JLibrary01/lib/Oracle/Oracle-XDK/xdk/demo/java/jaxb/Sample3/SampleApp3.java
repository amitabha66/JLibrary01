import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import java.net.URL;
import java.io.File;

public class SampleApp3
{
   public static void main(String args[]) throws Throwable
   {
      String fileName = "sample3.xml";
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
      generated.MyAddress elem = (generated.MyAddress)obj;
      System.out.println();
      System.out.println(" My address is: ");
      System.out.println("  name:  "  + elem.getName() + "\n"  +
                         "  doorNumber " + elem.getDoorNumber() + "\n" +
                         "  street: " + elem.getStreet() + "\n" +
                         "  city:   " + elem.getCity() + "\n"  +
                         "  state:  " + elem.getState() + "\n" +
                         "  zip:  " + elem.getZip() + "\n" +
                         "  country:  " + elem.getCountry() + "\n" +
                         "\n" );
      System.out.println(" My address is changed now.");
      short num = 550;
      elem.setDoorNumber(num);
      elem.setCountry("India");
      num = 10100;
      elem.setZip(new java.math.BigInteger("100100"));
      elem.setCity("Noida");
      elem.setState("Delhi");

      System.out.println(" My changed address is:");
      System.out.println("  name:  "  + elem.getName() + "\n"  +
                         "  doorNumber " + elem.getDoorNumber() + "\n" +
                         "  street: " + elem.getStreet() + "\n" +
                         "  city:   " + elem.getCity() + "\n"  +
                         "  state:  " + elem.getState() + "\n" +
                         "  zip:  " + elem.getZip() + "\n" +
                         "  country:  " + elem.getCountry() + "\n" +
                         "\n" );
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

