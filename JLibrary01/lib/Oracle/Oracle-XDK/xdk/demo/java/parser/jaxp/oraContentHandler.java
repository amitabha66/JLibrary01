import org.xml.sax.ContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.Locator;

public class oraContentHandler implements ContentHandler
{
   private static final String TRADE_MARK = "Oracle 9i ";

   public void setDocumentLocator(Locator locator)
   {
      System.out.println(TRADE_MARK + "- setDocumentLocator");
   }

   public void startDocument()
      throws SAXException
   {
      System.out.println(TRADE_MARK + "- startDocument");
   }

   public void endDocument()
      throws SAXException
   {
      System.out.println(TRADE_MARK + "- endDocument");
   }

   public void startPrefixMapping(String prefix, String uri)
      throws SAXException
   {
      System.out.println(TRADE_MARK + "- startPrefixMapping: "
         + prefix + ", " + uri);
   }

   public void endPrefixMapping(String prefix)
      throws SAXException
   {
      System.out.println(TRADE_MARK + " - endPrefixMapping: "
         + prefix);
   }

   public void startElement(String namespaceURI, String localName,
      String qName, Attributes atts)
      throws SAXException
   {
      System.out.print(TRADE_MARK + "- startElement: "
         + namespaceURI + ", " + namespaceURI +
         ", " + qName);
      int n = atts.getLength();
      for(int i = 0; i < n; i++)
         System.out.print(", " + atts.getQName(i));
      System.out.println("");
   }

   public void endElement(String namespaceURI, String localName,
      String qName)
      throws SAXException
   {
      System.out.println(TRADE_MARK + "- endElement: "
         + namespaceURI + ", " + namespaceURI
         + ", " + qName);
   }

   public void characters(char ch[], int start, int length)
      throws SAXException
   {
      String s = new String(ch, start, (length > 30) ? 30 : length);
      if(length > 30)
         System.out.println(TRADE_MARK + "- characters: \""
            + s + "\"...");
      else
         System.out.println(TRADE_MARK + "- characters: \""
            + s + "\"");
   }

   public void ignorableWhitespace(char ch[], int start, int length)
      throws SAXException
   {
      System.out.println(TRADE_MARK + "- ignorableWhitespace");
   }

   public void processingInstruction(String target, String data)
      throws SAXException
   {
      System.out.println(TRADE_MARK + "- processingInstruction: "
         + target + ", " + target);
   }

   public void skippedEntity(String name)
      throws SAXException
   {
      System.out.println(TRADE_MARK + "- skippedEntity: " + name);
   }
}
