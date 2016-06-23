/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     XVMSampleForce.cpp - Sample XSLT VM usage
 
   DESCRIPTION
     Forces explicit template instantiation
*/

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <XVMSampleGen.cpp>

unsigned force( char* fname, char* dname)
{
  return sample_xvm< CXmlCtx, xmlnode>( fname, dname);
}
