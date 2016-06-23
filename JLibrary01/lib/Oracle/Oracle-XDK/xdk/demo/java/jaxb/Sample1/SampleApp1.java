import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import java.net.URL;
import java.io.File;

public class SampleApp1 
{
   public static void main(String args[]) throws Throwable
   {
      int aval ;
      String bval;
      int dval;
      String eval;
      String stypeValue;
      int fval;
      generated.AnElement elem;
      generated.CType ctype;
      generated.RType rtype;
      String refstype;
      generated.RType refctype;
      generated.ObjectFactory objfactory =
              new generated.ObjectFactory();

      String fileName = "sample1.xml";
      String instancePath = "generated";
      JAXBContext jc = JAXBContext.newInstance(instancePath);
      Unmarshaller u = jc.createUnmarshaller();
      Object obj = u.unmarshal(fileToURL(fileName));

      Marshaller m = jc.createMarshaller();
      m.marshal(obj, System.out);
      System.out.println();
      System.out.println();

      elem = (generated.AnElement)obj;

      // Test Getters
      System.out.println("Testing Getters: " );
      printContents(elem);

      // Test Setters
      aval = 110;
      elem.setA(aval);

      bval = "modified dataItem";
      elem.setB(bval);

      ctype = elem.getC();
      dval = 115;
      eval = null;
      ctype.setD(dval);
      ctype.setE(eval);
      elem.setC(ctype);

      refstype = elem.getRElemOfSTypeInSameNs();
      elem.setRElemOfSTypeInSameNs(refstype);

      refctype = elem.getRElemOfCTypeInSameNs();
      fval = 120;
      refctype.setF(fval);
      elem.setRElemOfCTypeInSameNs(refctype);
      System.out.println("Testing Setters: ");
      printContents(elem);

      // Test ObjectFactory Creation
      elem = objfactory.createAnElement();
      aval = 310;
      bval = "newly created string value for B";
      elem.setA(aval);
      elem.setB(bval);

      ctype = objfactory.createCType();
      ctype.setD(315);
      ctype.setE("Newly added field to C");
      elem.setC(ctype);


      refctype = objfactory.createRElemOfCTypeInSameNs();
      refctype.setF(320);
      elem.setRElemOfCTypeInSameNs(refctype);

      System.out.println("Testing ObjectFactory Creation: ");
      printContents(elem);
   }

   private static void printContents(generated.AnElement elem)
   {
      int aval = elem.getA();
      String bval = elem.getB();
      generated.CType ctypeval = elem.getC();
      int dval = ctypeval.getD();
      String eval = ctypeval.getE();
      String refstype = elem.getRElemOfSTypeInSameNs();
      generated.RType refctypeval =
                      elem.getRElemOfCTypeInSameNs();
      int fval = refctypeval.getF();

      System.out.println("Contents of the XML : ");
      System.out.println("   A: " + aval + "\n" +
                         "   B: " + bval + "\n" +
                         "   D: " + dval + "\n" +
                         "   E: " + eval + "\n" +
                         "   rElemOfSTypeInSameNs.val: " + refstype + "\n" +
                         "   F: " + fval + "\n"); 
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
