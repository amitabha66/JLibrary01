/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     FullDOMMain.cpp - Sample usage of all DOM functionality
 
   DESCRIPTION
     Sample usage of C++ interfaces of all DOM functionality
*/

extern "C" {

#include <stdio.h>
}

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <FullDOMGen.hpp>

int main( int argc, char* argv[])
{
  if (argc < 2)
    {
      printf( "Usage: FullDOM XML_file_name\n");
      return 0;
    }

  if (full_dom< CXmlCtx, xmlnode>( argv[1]))
    return 1;
  return 0;
}
