/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     SAXSampleMain.hpp - Sample SAX usage
 
   DESCRIPTION
     Sample usage of C++ interfaces of XML SAX parser and SAX handler
*/

extern "C" {

#include <stdio.h>
}

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <SAXSampleGen.hpp>

int main( int argc, char* argv[])
{
  if (argc < 2)
    {
      printf( "Usage: SAXSample XML_file_name\n");
      return 0;
    }

  if (sample_sax< CXmlCtx, xmlnode>( argv[1]))
    return 1;
  return 0;
}
