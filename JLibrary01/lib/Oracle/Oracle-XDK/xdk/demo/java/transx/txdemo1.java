
// Copyright (c) 2001 All rights reserved Oracle Corporation

import oracle.xml.transx.*;

public class txdemo1 {

  /**
   * Constructor
   */
  public txdemo1() {
  }

  /**
   * main
   * @param args
   *
   * args[0] : connect string
   * args[1] : username
   * args[2] : password
   * args[3+] : xml file names
   */
  public static void main(String[] args) throws Exception {

    // instantiate a transx class
    TransX  transx = loader.getLoader();

    // start a data loading session
    transx.open( args[0], args[1], args[2] );

    // specify operation modes
    transx.setLoadingMode( LoadingMode.SKIP_DUPLICATES );
    transx.setValidationMode( false );

    // load the dataset(s)
    for ( int i = 3 ; i < args.length ; i++ )
    {
      transx.load( args[i] );
    }

    // cleanup
    transx.close();
  }
}
