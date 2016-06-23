import java.io.*;


/* This is just a simple class which pipes information from GUI to the bean */


class compstreamdata {

  // The input & output streams to be passed for the compression

  InputStream inputStream;
  OutputStream outputStream;
  static String statstr;
  static String compfactstr;
  static String inpfilestr;
  static String outpfilestr;
   
  String xmlData;
  
  // Java File objects :Used for calculating the compression amount

  File origFile,compFile;
  static long sizeComp=0,sizeOrig=0;

  compstreamdata() 
  {
    inputStream = null;
    outputStream = null;
    xmlData = new String();
    statstr = "Buffer Empty";
    compfactstr = "";
    inpfilestr = "";
    outpfilestr="";
  }

  
  public float getFileCompPercent() 
  {
    sizeComp = compFile.length();
    sizeOrig = origFile.length();

    float retValue;

    retValue = (float)(((float)sizeOrig - (float)sizeComp)/(float)sizeOrig); 

    // Round off to 3 decimals
    
    int tempsize = (int)(retValue *1000);
     
    retValue = (float)tempsize/(float)1000; 
    return retValue;
  }   



  public float getDBCompPercent() 
  {
    sizeComp = compFile.length();
    sizeOrig = xmlData.length();

    float retValue;

    retValue = (float)(((float)sizeOrig - (float)sizeComp)/(float)sizeOrig);

    // Round off to 3 decimals
    
    int tempsize = (int)(retValue *1000);
     
    retValue = (float)tempsize/(float)1000; 

    return retValue;
  }   
}
