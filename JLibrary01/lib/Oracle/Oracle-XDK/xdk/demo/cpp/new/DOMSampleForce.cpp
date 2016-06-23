/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     DOMSampleForce.cpp - Sample DOM usage
 
   DESCRIPTION
     Forces explicit template instantiation
*/

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <DOMSampleGen.cpp>

unsigned force()
{
  return sample_dom< CXmlCtx, xmlnode>( (char*)"fake_file");
}
