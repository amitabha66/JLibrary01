/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     XSLSampleMain.hpp - Sample XSLT usage
 
   DESCRIPTION
     Sample usage of C++ interfaces of the XSLT processor
*/

extern "C" {

#include <stdio.h>
}

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <XSLSampleGen.hpp>

int main( int argc, char* argv[])
{
    if (argc < 3)
    {
	printf( "Usage is XSLSample <stylesheet> <xmlfile>\n");
	return 1;
    }
    if (sample_xsl< CXmlCtx, xmlnode>( argv[1], argv[2]))
        return 1;
    return 0;
}
