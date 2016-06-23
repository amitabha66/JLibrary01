/* $Header: DemoUtil.java 21-mar-2001.15:49:42 kkarun Exp $ */
 
/* Copyright (c) Oracle Corporation 2000, 2001. All Rights Reserved. */

/**
 * DESCRIPTION
 * Some utility functions for the Demos
 */

import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;

public class DemoUtil
{
   // Helper method to create a URL from a file name
   static URL createURL(String fileName)
   {
      URL url = null;
      try 
      {
         url = new URL(fileName);
      } 
      catch (MalformedURLException ex) 
      {
         File f = new File(fileName);
         try 
         {
            String path = f.getAbsolutePath();
            // This is a bunch of weird code that is required to
            // make a valid URL on the Windows platform, due
            // to inconsistencies in what getAbsolutePath returns.
            String fs = System.getProperty("file.separator");
            if (fs.length() == 1)
            {
               char sep = fs.charAt(0);
               if (sep != '/')
                  path = path.replace(sep, '/');
               if (path.charAt(0) != '/')
                  path = '/' + path;
            }
            path = "file://" + path;
            url = new URL(path);
         } 
         catch (MalformedURLException e) 
         {
            System.out.println("Cannot create url for: " + fileName);
            System.exit(0);
         }
      }
      return url;
   }
}
