/* $RCSfile: xmlsch.h $ $Date: 29-oct-2003.18:35:07 $
   Copyright (c) 2001, 2003, Oracle.  All Rights Reserved.

   FILE NAME
        xmlsch.h - Oracle XML Schema APIs

   FILE DESCRIPTION
        This file defines the XML Schema validator interfaces.
*/


#ifndef XMLSCH_ORACLE
# define XMLSCH_ORACLE

#ifndef STDIO_ORACLE
# define STDIO_ORACLE
# include <stdio.h>
#endif

#ifndef STDARG_ORACLE
# define STDARG_ORACLE
# include <stdarg.h>
#endif

#ifndef ORATYPES_ORACLE
# define ORATYPES_ORACLE
# include <oratypes.h>
#endif

#ifndef XMLERR_ORACLE
# include <xmlerr.h>
#endif

#ifndef XMLPROC_ORACLE
# include <xmlproc.h>
#endif

/*---------------------------------------------------------------------------
                        Package Schema - Schema APIs

    This C implementation of the XML schema validator follows the W3C XML 
    Schema specification (rev REC-xmlschema-1-20010502) and implements the
    required behavior of an schema validator in terms of how multiple schema
    documents can be assembled into a schema and be used to validate a specific
    instance document.

  ---------------------------------------------------------------------------*/

/* DATATYPE xsdctx - XML Schema context
   SEE ALSO XmlSchemaCreate
    XML Schema validator context, created by XmlSchemaCreate and passed 
    to most Schema functions.
*/
#ifndef XSDCTX_DEFINED
# define XSDCTX_DEFINED
struct xsdctx; typedef struct xsdctx xsdctx;
#endif

/****************************************************************************
                           Interface Schema
 ****************************************************************************/

/*---------------------------------------------------------------------------
    NAME
        XmlSchemaVersion

    PURPOSE
        Return the version of this schema implementation

    DESCRIPTION
        Return the version of this schema implementation.

    RETURNS
        (oratext *) version string [compiler encoding]

    ARGUMENTS
        void
*/
oratext *XmlSchemaVersion(void);

/*---------------------------------------------------------------------------
    NAME
        XmlSchemaCreate

    PURPOSE
        Create and return a schema context

    DESCRIPTION
        Return a schema context to be used in other validator APIs.  This
        needs to be paired with an XmlSchemaDestroy.

    RETURNS
        (xsdctx *) schema context

    ARGUMENTS
        xctx (IN)  XML context 
        err  (OUT) returned error code
        ...  (IN)  variable arguments, with final NULL

    SEE ALSO
        XmlSchemaDestroy, XmlCreate
*/
xsdctx *XmlSchemaCreate(xmlctx *xctx, xmlerr *err, ...);

/*---------------------------------------------------------------------------
    NAME
        XmlSchemaSetErrorHandler

    FUNCTION
        Set error message handler

    DESCRIPTION
        Sets an error message handler and its associated context in a schema
        context.  To retrieve useful location information on errors, the 
        address of the schema context must be provided in the error handler 
        context.

    RETURNS
        (xmlerr) error code

    ARGUMENTS
        sctx    (IN)  schema context
        errhdl  (IN)  error message handler
        errctx  (IN)  error handler context

    SEE ALSO
        XmlSchemaCreate, XML_ERRMSG_F, 
        XmlSchemaErrorWhere

    EXAMPLE
        XML_ERRMSG_F(schema_err_handler, msgctx, msg, errcode)
        {
            xsdctx *sctx = (xsdctx *)msgctx;
            ub4 line;
            oratext *path;
            ...
            DISCARD XmlSchemaErrorWhere(sctx, &line, &path);
            DISCARD printf("Line Number=%d\n", line);
            DISCARD printf("Errcode=%d\n", errcode);
            DISCARD printf("Msg=%s\n", msg);
        }
        main(int argc, char *argv[])
        {
            ...
            if (!(scctx = XmlSchemaCreate(xctx, &xerr, NULL)))
            {
                DISCARD printf("Failed to initialize XML parser, error %d\n", 
                                xerr);
                XmlFreeDocument(xctx, docnode);
                XmlDestroy(xctx);
                return 3;
            }
            ...
            if (xerr = XmlSchemaSetErrorHandler(scctx, schema_err_handler, 
                                                scctx))
            {
                DISCARD printf("Failed to set error handler, error %d\n", 
                                xerr);
                XmlFreeDocument(xctx, docnode);
                XmlSchemaDestroy(scctx);
                XmlDestroy(xctx);
                return 4;
            }
            ...
        }
*/
xmlerr XmlSchemaSetErrorHandler(xsdctx *sctx, 
                      XML_ERRMSG_F((*errhdl), ectx, msg, err), void *errctx);

/*---------------------------------------------------------------------------
    NAME
        XmlSchemaErrorWhere

    FUNCTION
        Return error location information

    DESCRIPTION
        Returns the location (line#, path) where an error occured.

    RETURNS
        (xmlerr) error code

    ARGUMENTS
        sctx    (IN)      schema context
        line    (IN/OUT)  line number where error occured
        path    (IN/OUT)  URL/filespec where error occured

    SEE ALSO
        XmlSchemaSetErrorHandler
*/
xmlerr XmlSchemaErrorWhere(xsdctx *sctx, ub4 *line, oratext **path);

/*---------------------------------------------------------------------------
    NAME
        XmlSchemaLoad

    PURPOSE
        Load a schema document

    DESCRIPTION
        Load up a schema document to be used in the next validation session.
        Schema documents can be incrementally loaded into a schema context
        as long as every loaded schema document is valid.  When the last 
        loaded schema turns out to be invalid, you need to clean up the 
        schema context by calling XmlSchemaClean and reload everyting all 
        over again including the last schema with appropriate correction.
 
    RETURNS
        (xmlerr) numeric error code, XMLERR_OK [0] on success

    ARGUMENTS
        sctx (IN) schema context
        uri  (IN) URL of schema document [compiler encoding]
        ...  (IN) variable arguments, with final NULL

    SEE ALSO
        XmlSchemaUnload, XmlSchemaLoadedList
*/
xmlerr XmlSchemaLoad(xsdctx *sctx, oratext *uri, ...);

/*---------------------------------------------------------------------------
    NAME
        XmlSchemaLoadedList

    PURPOSE
        Return the size and/or list of loaded schema documents. 

    DESCRIPTION
        Return only the size of loaded schema documents if {list} is NULL.
        If {list} is NOT NULL, a list of URL pointers are returned in the
        user-provided pointer buffer.  Note that its user's responsibility
        to provide a buffer with big enough size.  

    RETURNS
        (ub4) list size 

    ARGUMENTS
        sctx (IN)  schema context
        list (I/O) address of a pointer buffer

    SEE ALSO
        XmlSchemaLoad, XmlSchemaUnload

    EXAMPLE
        listSize = XmlSchemaLoadedList(scctx, (oratext **)NULL);
        DISCARD printf("There are %d target namespace(s) loaded.\n", listSize);
        DISCARD printf("And, they are:\n");

        urlList = (oratext **) malloc(sizeof(oratext *) * listSize);
        listSize = XmlSchemaLoadedList(scctx, urlList);

        for (idx = 0; idx < listSize; idx++)
        {
            DISCARD printf("  '%s'\n", urlList[idx]);
        }
*/
ub4 XmlSchemaLoadedList(xsdctx *sctx, oratext **list);

/*---------------------------------------------------------------------------
    NAME
        XmlSchemaUnload

    PURPOSE
        Unload a schema document

    DESCRIPTION
        Unload a schema document from the validator.  All previously loaded
        schema documents will remain loaded until they are unloaded.  To
        unload all loaded schema documents, set uri to be NULL (this is 
        equivalent to XmlSchemaClean).  Note that all children schemas 
        associated with the given schema are also unloaded.  In this 
        implementation, it only support the following scenarios: 
        1. load, load, ... 2. load, load, load, unload, unload, unload, clean,
        and then repeat.  It doesn't not support: load, load, unload, load, 
        ....

    RETURNS
        (xmlerr) numeric error code, XMLERR_OK [0] on success

    ARGUMENTS
        sctx (IN) schema context
        uri  (IN) URL of schema document [compiler encoding]
        ...  (IN) variable arguments, with final NULL

    SEE ALSO
        XmlSchemaLoad, XmlSchemaLoadedList

    EXAMPLE
        xerr = XmlSchemaLoad(scctx, schema1, NULL);
        ...
        xerr = XmlSchemaUnload(scctx, schema1, NULL);
*/
xmlerr XmlSchemaUnload(xsdctx *sctx, oratext *uri, ...);

/*---------------------------------------------------------------------------
    NAME
        XmlSchemaSetValidateOptions

    PURPOSE
        Set option(s) to be used in the next validation session

    DESCRIPTION
        Set options to be used in the next validation session.  Previously
        set options will remain effective until they are overwritten or reset. 

    RETURNS
        (xmlerr) numeric error code, XMLERR_OK [0] on success

    ARGUMENTS
        sctx (IN) schema context
        ...  (IN) variable argument list, with final NULL 

    SEE ALSO
        XmlSchemaValidate

    EXAMPLE
        XmlSchemaSetValidateOptions(scctx, "ignore_id_constraint",
                                    (boolean)TRUE, NULL);
*/
xmlerr XmlSchemaSetValidateOptions(xsdctx *sctx, ...);

/*---------------------------------------------------------------------------
    NAME
        XmlSchemaValidate

    PURPOSE
        Validate an element node against a schema

    DESCRIPTION
        Validates an element node against a schema.  Schemas used in current
        session consists of all schema documents specified through 
        XmlSchemaLoad and provided as hint(s) through schemaLocation or 
        noNamespaceSchemaLocation in the instance document. After the 
        invokation of this routine, all loaded schema documents remain loaded
        and can be queried by XmlSchemaLoadedList.  However, they will remain
        inactive. In the next validation session, inactive schema documents
        can be activated by specifying them via XmlSchemaLoad or providing
        them as hint(s) via schemaLocation or noNamespaceSchemaLocation in
        the new instance document.  To unload a schema document and all
        its descendants (documents included or imported nestedly), use
        XmlSchemaUnload.

    RETURNS
        (xmlerr) numeric error code, XMLERR_OK [0] on success

    ARGUMENTS
        sctx (IN) schema context
        xctx (IN) XML top-level context
        elem (IN) an element node in "doc" to be validated

    SEE ALSO
        XmlSchemaSetValidateOptions
*/
xmlerr XmlSchemaValidate(xsdctx *sctx, xmlctx *xctx, xmlelemnode *elem);

/*---------------------------------------------------------------------------
    NAME
        XmlSchemaClean

    FUNCTION
        Clean up loaded schemas in a schema context

    DESCRIPTION
        Clean up loaded schemas in a schema context and recycle the schema
        context.

    RETURNS
        void

    ARGUMENTS
        sctx   (IN)  schema context to be cleaned

    SEE ALSO
        XmlSchemaCreate, XmlSchemaDestroy

    EXAMPLE
        ...
        xctx = XmlCreate(&xerr, (oratext *) "tlsx2",
                         "data_encoding", outcoding, NULL);
        if (xerr)
        {
            DISCARD printf("Failed to initialze XML meta context, error %u\n",
                           (unsigned) xerr);
            return 1;
        }
        ...
        if (!(scctx = XmlSchemaCreate(xctx, &xerr, NULL)))
        {
            DISCARD printf("Failed, code %u!\n", xerr);
            return 2;
        }
        for (i = startNo; i <= endNo; i++)
        {
            sprintf(schema, "tlsx%d.xsd", i);
            sprintf(doc, "tlsx%d.xml", i);
            if (xerr = XmlSchemaLoad(scctx, (oratext *)schema, NULL))
            {
                DISCARD printf("Assessement of %s failed, error %u\n", schema,
                               (unsigned) xerr);
                XmlSchemaClean(scctx);
                continue;
            }
            if (!(docnode = XmlLoadDom(xctx, &xerr, "uri", doc,
                                  "default_input_encoding", incoding, NULL)))
            {
                DISCARD printf("Parse of %s failed, error %u\n", doc,
                               (unsigned) xerr);
                XmlFreeDocument(xctx, docnode);
                XmlSchemaClean(scctx);
                continue;
            }
            root = XmlDomGetDocElem(xctx, docnode);
            if (xerr = XmlSchemaValidate(scctx, xctx, root))
            {
                DISCARD printf("Validation of %s failed, error %u\n", doc,
                                (unsigned) xerr);
            }
            else
            {
                DISCARD stlptf("Document %s is valid.\n", doc);
            }
            XmlFreeDocument(xctx, docnode);
            XmlSchemaClean(scctx);
        }
        XmlSchemaDestroy(scctx);
        XmlDestroy(xctx);
        return 0;
*/
void XmlSchemaClean(xsdctx *sctx);

/*---------------------------------------------------------------------------
    NAME
        XmlSchemaDestroy

    PURPOSE
        Destroy a schema context

    DESCRIPTION
        Destroy a schema context and free up all its resources.

    RETURNS
        (void)

    ARGUMENTS
        sctx   (IN)  schema context to be freed

    SEE ALSO
        XmlSchemaCreate
*/
void XmlSchemaDestroy(xsdctx *sctx);

/*---------------------------------------------------------------------------
    NAME
        XmlSchemaTargetNamespace

    PURPOSE
        Return target namespace of a given schema document

    DESCRIPTION
        Return target namespace of a given schema document identified by its
        URI.  All currently loaded schema documents can be queried.  Currently
        loaded schema documents include the ones loaded via XmlSchemaLoad's and
        the ones loaded via schemaLocation or noNamespaceSchemaLocation hints.

    RETURNS
        (oratext *) target namespace string; NULL if given document not
                    loaded.  Returned string is in schema's data encoding.

    ARGUMENTS
        sctx (IN) schema context to be queried
        uri  (IN) URL of the schema document to be queried

    SEE ALSO
        XmlSchemaLoadedList
*/
oratext *XmlSchemaTargetNamespace(xsdctx *sctx, oratext *uri);

#endif                                              /* XMLSCH_ORACLE */
