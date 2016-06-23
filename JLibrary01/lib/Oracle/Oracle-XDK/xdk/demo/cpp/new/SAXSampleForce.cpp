/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     SAXSampleForce.cpp - Sample SAX usage
 
   DESCRIPTION
     Forces explicit template instantiation
*/

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <SAXSampleGen.cpp>

unsigned force()
{
  return sample_sax< CXmlCtx, xmlnode>( (char*)"fake_file");
}
