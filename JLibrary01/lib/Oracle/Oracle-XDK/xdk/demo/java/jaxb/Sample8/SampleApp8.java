import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

import generated.sample8.*;
import generated.sample81.*;

public class SampleApp8
{
   public static void main(String[] args)
   {
      try
      {
         JAXBContext jc = JAXBContext.newInstance("generated.sample8");

         Unmarshaller u = jc.createUnmarshaller();
         Marshaller m = jc.createMarshaller();

         PurchaseOrder po = 
           (PurchaseOrder)u.unmarshal(new FileInputStream("sample8.xml"));

         System.out.println("The Shipping Address:");
         Address saddr = po.getShipTo();
         System.out.println("\t" + saddr.getName() + "\n" + 
                            "\t" + saddr.getStreet() + "\n" +
                            "\t" + saddr.getCity() + "\n" +
                            "\t" + saddr.getState() + "\n" +
                            "\t" + saddr.getZip() + "\n" + 
                            "\t" + saddr.getCountry() + "\n");


         System.out.println("The Billing Address:");
         USAddress usaddr = po.getBillTo();
         System.out.println("\t" + usaddr.getName() + "\n" + 
                            "\t" + usaddr.getStreet() + "\n" +
                            "\t" + usaddr.getCity() + "\n" +
                            "\t" + usaddr.getState() + "\n" +
                            "\t" + usaddr.getZip() + "\n");
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }
   }
}
