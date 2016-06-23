/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     XSLSampleGen.hpp - Sample XSLT usage
 
   DESCRIPTION
     Sample usage of C++ interfaces of the XSLT processor
*/

#ifndef XML_CPP_ORACLE
#include <xml.hpp>
#endif

template< typename TCtx, typename Tnode> unsigned sample_xsl(
                                             char* fname, char* dname);
