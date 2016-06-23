import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import java.net.URL;
import java.io.File;

public class SampleApp2
{
   public static void main(String args[]) throws Throwable
   {
      String fileName = "sample2.xml";
      String instancePath = "generated";
      JAXBContext jc = JAXBContext.newInstance(instancePath);
      Unmarshaller u = jc.createUnmarshaller();
      Object obj = u.unmarshal(fileToURL(fileName));

      Marshaller m = jc.createMarshaller();
      m.marshal(obj, System.out);
      process(obj, m);
   }

   private static void process(Object obj, Marshaller m) throws Throwable
   {
      generated.AnElementWithInlineSimpleType elem =
                     (generated.AnElementWithInlineSimpleType)obj;
      System.out.println();
      System.out.println("\n Contents of the element: " + elem.getValue());
      System.out.println("\n The content is changed.");
      elem.setValue("The content is changed!");
      System.out.println("\n   changed value: " + elem.getValue());

      System.out.println("\n The marshalling of the elemnt: " );
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
