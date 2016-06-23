/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     SchSampleForce.cpp - Sample XML schema validator usage
 
   DESCRIPTION
     Forces explicit template instantiation
*/

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <SchSampleGen.cpp>

unsigned force()
{
  return sample_valid< CXmlCtx, xmlnode>( (char*)"fake_file", NULL);
}
