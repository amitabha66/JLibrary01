import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import java.net.URL;
import java.io.File;
import java.util.Calendar;

public class SampleApp6
{
   public static void main(String args[]) throws Throwable
   {
     String fileName = "sample6.xml";
     String instancePath = "generated";
     JAXBContext jc = JAXBContext.newInstance(instancePath);
     Unmarshaller u = jc.createUnmarshaller();
     Object obj = u.unmarshal(fileToURL(fileName));

     Marshaller m = jc.createMarshaller();
     process(obj, m);
   }

   public static void process(Object obj, Marshaller m) throws Throwable
   {
      generated.Datatype elem =
            (generated.Datatype)obj;


      String duration = elem.getMyduration();
      Calendar dateTime = elem.getMydateTime();
      Calendar date = elem.getMydate();
      Calendar time = elem.getMytime();
      String gYearMonth = elem.getMygYearMonth();
      String gYear = elem.getMygYear();
      String gMonthDay = elem.getMygMonthDay();
      String gDay = elem.getMygDay();
      String gMonth = elem.getMygMonth();

      boolean b = elem.isMyboolean();
      byte[] b64 = elem.getMybase64Binary();
      byte[] hex = elem.getMyhexBinary();
      float f = elem.getMyfloat();
      double d = elem.getMydouble();
      String auri = elem.getMyanyURI();
      String qname = elem.getMyQName();

      System.out.println("\n The contents of the element: " );
      System.out.println("     duration: " + duration + "\n" +
                         "     dateTime: ");
      printCal(dateTime, "dateTime");
      System.out.println("\n");
      System.out.println("     date: ");
      printCal(date, "date");
      System.out.println("\n");
      System.out.println("     time: ");
      printCal(time, "time");
      System.out.println("\n");
      System.out.println("     gYearMonth: " + gYearMonth+ "\n" + 
                         "     gYear: " + gYear+ "\n" + 
                         "     gMonthDay: " + gMonthDay+ "\n" + 
                         "     gDay: " + gDay+ "\n" + 
                         "     gMonth: " + gMonth+ "\n" + 
                         "     boolean: " + b + "\n" + 
                         //"     base64Binary: " + b64 + "\n" + 
                         //"     hexBinary: " + hex + "\n" + 
                         "     float: " + f + "\n" + 
                         "     double: " + d + "\n" + 
                         "     anyURI: " + auri + "\n" + 
                         "     QName : " + qname + "\n" + 
 
                         "\n" );


   }

   private static void printCal(Calendar c, String val)
   {
      if (val.equals("dateTime") || val.equals("date"))
      {
         System.out.println("           ERA: " + c.get(Calendar.ERA));
         System.out.println("           YEAR: " + c.get(Calendar.YEAR));
         System.out.println("           MONTH: " + c.get(Calendar.MONTH));
         System.out.println("           WEEK_OF_DAY: " + c.get(Calendar.WEEK_OF_YEAR));
         System.out.println("           WEEK_OF_MONTH: " + c.get(Calendar.WEEK_OF_MONTH));
         System.out.println("           DATE: " + c.get(Calendar.DATE));
         System.out.println("           DAY_OF_MONTH: " + c.get(Calendar.DAY_OF_MONTH));
         System.out.println("           DAY_OF_YEAR: " + c.get(Calendar.DAY_OF_YEAR));
         System.out.println("           DAY_OF_WEEK_IN_MONTH : " + c.get(Calendar.DAY_OF_WEEK_IN_MONTH));
      }

      if (val.equals("dateTime") || val.equals("time"))
      {
         System.out.println("           HOUR: " + c.get(Calendar.HOUR));
         System.out.println("           HOUR_OF_DAY: " + c.get(Calendar.HOUR_OF_DAY));
         System.out.println("           MINUTE: " + c.get(Calendar.MINUTE));
      }

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
