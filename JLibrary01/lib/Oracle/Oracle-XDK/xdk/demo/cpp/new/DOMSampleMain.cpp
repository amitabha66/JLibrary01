/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     DOMSampleMain.hpp - Sample DOM usage
 
   DESCRIPTION
     Sample usage of C++ interfaces of XML parser and DOM
*/

extern "C" {

#include <stdio.h>
}

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <DOMSampleGen.hpp>

int main( int argc, char* argv[])
{
  if (argc < 2)
    {
      printf( "Usage: DOMSample XML_file_name\n");
      return 0;
    }

  if (sample_dom< CXmlCtx, xmlnode>( argv[1]))
    return 1;
  return 0;
}


