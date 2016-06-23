/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     FullDOMForce.cpp - Sample usage of all DOM functionality
 
   DESCRIPTION
     Sample usage of C++ interfaces of all DOM functionality
*/

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <FullDOMGen.cpp>

unsigned force()
{
  return full_dom< CXmlCtx, xmlnode>( (char*)"fake_file");
}
