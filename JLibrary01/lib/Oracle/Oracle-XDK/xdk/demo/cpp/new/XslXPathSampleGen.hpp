/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     XslXPathSampleGen.hpp - Sample XPath usage
 
   DESCRIPTION
     Sample usage of C++ interfaces of the XPath processors
*/

#ifndef XML_CPP_ORACLE
#include <xml.hpp>
#endif

template< typename TCtx, typename Tnode> unsigned sample_xpath(
                                             char* dname, char* xpath_exp);
