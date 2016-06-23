/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     XslXPathSampleForce.cpp - Sample XPath usage
 
   DESCRIPTION
     Forces explicit template instantiation
*/

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <XslXPathSampleGen.cpp>

unsigned force( char* dname, char* xpath_exp)
{
  return sample_xpath< CXmlCtx, xmlnode>( dname, xpath_exp);
}
