/* Copyright (c) 2003, Oracle Corporation.  All rights reserved.  */
 
/*
   NAME
     XVMSampleMain.hpp - Sample XVM usage
 
   DESCRIPTION
     Sample usage of C++ interfaces of the XVM XSLT processor
*/

#include <iostream.h>
#include <string.h>

#ifndef XMLCTX_CPP_ORACLE
#include <xmlctx.hpp>
#endif

#include <XVMSampleGen.hpp>

int main( int argc, char* argv[])
{
    if (argc < 3)
    {
	cout << "Usage is XVMSample <stylesheet> <xmlfile>\n";
	return 1;
    }
    if (sample_xvm< CXmlCtx, xmlnode>( argv[1], argv[2]))
        return 1;
}
