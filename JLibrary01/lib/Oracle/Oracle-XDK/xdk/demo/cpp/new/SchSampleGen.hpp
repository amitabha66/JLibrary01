/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     SchSampleGen.hpp - Sample XML schema validator usage
 
   DESCRIPTION
     Sample usage of C++ interfaces for XML schema validator
*/

#ifndef XML_CPP_ORACLE
#include <xml.hpp>
#endif

template< typename TCtx, typename Tnode>
unsigned sample_valid( char* fname, char* sch_name);
