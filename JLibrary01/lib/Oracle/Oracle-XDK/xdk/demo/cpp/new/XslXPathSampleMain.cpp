/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     XslXPathSampleMain.hpp - Sample XPath usage
 
   DESCRIPTION
     Sample usage of C++ interfaces of the XPath processor
*/

#include <iostream.h>
#include <string.h>

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <XslXPathSampleGen.hpp>

int main( int argc, char* argv[])
{
    if (argc < 3)
    {
	cout << "Usage is XslXPathSample <xmlfile> <xpath>\n";
	return 1;
    }
    if (sample_xpath< CXmlCtx, xmlnode>( argv[1], argv[2]))
        return 1;
}
