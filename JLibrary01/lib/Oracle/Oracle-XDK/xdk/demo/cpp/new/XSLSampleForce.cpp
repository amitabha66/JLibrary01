/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     XSLSampleForce.cpp - Sample XSLT usage
 
   DESCRIPTION
     Forces explicit template instantiation
*/

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <XSLSampleGen.cpp>

unsigned force( char* fname, char* dname)
{
  return sample_xsl< CXmlCtx, xmlnode>( fname, dname);
}
