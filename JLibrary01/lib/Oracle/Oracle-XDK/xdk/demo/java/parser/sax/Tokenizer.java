/* $Header: Tokenizer.java 08-mar-2001.10:37:14 kkarun Exp $ */
 
/* Copyright (c) Oracle Corporation 2000, 2001. All Rights Reserved. */

/**
 * DESCRIPTION
 * Demonstrates the use of tokenizer
 */

import java.net.URL;

import org.xml.sax.SAXException;

import oracle.xml.parser.v2.XMLToken;
import oracle.xml.parser.v2.XMLTokenizer;

public class Tokenizer implements XMLToken
{   
   static XMLTokenizer parser    = null;
   static int          num        = 0;
   static String       myTag;

   public static void main(String[] args) 
   {
      if (args.length > 1) {
         myTag = args[1];
      } else {
         myTag = new String (" ");
      }
      try 
      {
	 doParse (args[0]); 
      }
      catch (Exception e) 
      {
         System.out.println(e.toString());
      }
   }

   static void doParse (String arg) throws Exception
   {
      URL url = DemoUtil.createURL(arg);

      parser  = new XMLTokenizer ((XMLToken)new Tokenizer()); 

      parser.setErrorStream  (System.out);

      parser.setToken (STagName, true);
      parser.setToken (EmptyElemTag, true);
      parser.setToken (STag, true);
      parser.setToken (ETag, true);
      parser.setToken (ETagName, true);
      parser.setToken (Attribute, true);
      parser.setToken (AttName, true);
      parser.setToken (AttValue, true);
      parser.setToken (Reference, true);
      parser.setToken (Comment, true);
      parser.setToken (CharData, true);
      parser.setToken (CDSect, true);
      parser.setToken (PI, true);
      parser.setToken (PITarget, true);

      parser.setToken (XMLDecl, true);
      parser.setToken (TextDecl, true);

      parser.setToken (DTDName, true);
      parser.setToken (AttListDecl, true);
      parser.setToken (elementdecl, true);
      parser.setToken (ElemDeclName, true);
      parser.setToken (EntityDecl, true);
      parser.setToken (EntityDeclName, true);
      parser.setToken (EntityValue, true);
      parser.setToken (NotationDecl, true);  
      parser.setToken (ExternalID, true);  

      try {
         parser.tokenize (url);
      } catch (SAXException e) {
         System.out.println (e.getMessage());
      }
      System.out.println ("StartTagNames total: " + num);
   }

   public void token (int token, String value)
   {
      switch (token)
      {  
      case XMLToken.STag:   
         System.out.println ("STag: " + value);
         break;
      case XMLToken.ETag:
         System.out.println ("ETag: " + value);
         break;
      case XMLToken.EmptyElemTag:
         System.out.println ("EmptyElemTag: " + value);
         break;
      case XMLToken.AttValue:
         System.out.println ("AttValue: " + value);
         break;
      case XMLToken.AttName:
         System.out.println ("AttName: " + value);
         break;
      case XMLToken.Attribute:
         System.out.println ("Attribute: " + value);
         break;
      case XMLToken.CharData:
         System.out.println ("CharData: " + value);
         break;
      case XMLToken.Comment:
         System.out.println ("Comment: " + value);
         break;
      case XMLToken.Reference:
         System.out.println ("Reference: " + value);
         break;
      case XMLToken.CDSect:
         System.out.println ("CDSect: " + value);
         break;
      case XMLToken.PI:
         System.out.println ("PI: " + value);
         break;
      case XMLToken.PITarget:
         System.out.println ("PITarget: " + value);
         break;
      case XMLToken.ETagName:
         System.out.println ("ETagName: " + value);
         if (value.equals (myTag)) {
            parser.setToken (Comment, true);
            parser.setToken (CharData, true);
            parser.setToken (CDSect, true);
            parser.setToken (PI, true);
	 }
         break;
      case XMLToken.STagName:
         num++;
         System.out.println ("STagName: " + value);
         if (value.equals (myTag)) {
            parser.setToken (Comment, false);
            parser.setToken (CharData, false);
            parser.setToken (CDSect, false);
            parser.setToken (PI, false);
	 }
         break;
      case XMLToken.XMLDecl:
         System.out.println ("XMLDecl: " + value);
         break;
      case XMLToken.TextDecl:
         System.out.println ("TextDecl: " + value);
         break;
      case XMLToken.DTDName:
         System.out.println ("DTDName: " + value);
         break;
      case XMLToken.AttListDecl:
         System.out.println ("AttListDecl: " + value);
         break;
      case XMLToken.elementdecl:
         System.out.println ("elementdecl: " + value);
         break;
      case XMLToken.ElemDeclName:
         System.out.println ("ElemDeclName: " + value);
         break;
      case XMLToken.EntityDecl:
         System.out.println ("EntityDecl: " + value);
         break;
      case XMLToken.EntityDeclName:
         System.out.println ("EntityDeclName: " + value);
         break;
      case XMLToken.EntityValue:
         System.out.println ("EntityValue: " + value);
         break;
      case XMLToken.NotationDecl:
         System.out.println ("NotationDecl: " + value);
         break;
      case XMLToken.ExternalID:
         System.out.println ("ExternalID: " + value);
         break;

      default:
         break;
      }
   }
}
