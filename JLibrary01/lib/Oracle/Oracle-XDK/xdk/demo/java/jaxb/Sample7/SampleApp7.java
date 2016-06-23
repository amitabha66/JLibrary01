import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import java.net.URL;
import java.io.File;
import java.util.List;

public class SampleApp7
{
   public static void main(String args[]) throws Throwable
   {
     String fileName = "sample7.xml";
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
      generated.BindMixedContent elem = 
               (generated.BindMixedContent)obj;

      List elemList = elem.getContent();

      System.out.println("\n Contents of the List: " + "\n");
      String str1 = (String)elemList.get(0);
      generated.BindMixedContentType.Name name = 
          (generated.BindMixedContentType.Name)elemList.get(1);
      String str2 = (String)elemList.get(2);
      generated.BindMixedContentType.Quantity quantity =
          (generated.BindMixedContentType.Quantity)elemList.get(3);
      String str3 = (String)elemList.get(4);


      System.out.println("  Value of name: " + name.getValue() + "\n");
      System.out.println("  Value of str1: " + str1 + "\n" +
                         "  Value of name: " + name.getValue() + "\n" +
                         "  Value of str2: " + str2 + "\n" +
                         "  Value of quantity: " + quantity.getValue() + "\n" +
                         "  Value of str3: " + str3 + "\n");
      System.out.flush();
      String str4 = "A new string is added";
      elemList.add(str4);
      m.marshal(obj, System.out);
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
