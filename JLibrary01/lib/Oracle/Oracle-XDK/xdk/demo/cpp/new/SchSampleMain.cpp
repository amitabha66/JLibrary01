/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     SchSampleMain.hpp - Sample XML schema validator usage
 
   DESCRIPTION
     Sample usage of C++ interfaces for XML schema validator
*/

extern "C" {

#include <stdio.h>
}

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <SchSampleGen.hpp>

int main( int argc, char* argv[])
{
  if (argc < 2)
    {
      printf( "Usage: SchSample <XML doc> [schema]\n");
      return 0;
    }

  char* schp = NULL;
  if (argc > 2)
    schp = argv[2];

  if (sample_valid< CXmlCtx, xmlnode>( argv[1], schp))
    return 1;
  return 0;
}
