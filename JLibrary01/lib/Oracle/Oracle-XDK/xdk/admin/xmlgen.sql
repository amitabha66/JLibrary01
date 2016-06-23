Rem
Rem $Header: xmlgen.sql 30-sep-2003.12:04:47 mjaeger Exp $
Rem
Rem xmlgen.sql
Rem
Rem Copyright (c) 1999, 2003, Oracle Corporation.  All rights reserved.  
Rem
Rem    NAME
Rem      xmlgen.sql - PL/SQL wrapper of Oracle XMLSQLUtility (XSU) (obsolete)
Rem
Rem    DESCRIPTION
Rem      Wraps the methods in the OracleXMLStore java class.
Rem
Rem    NOTES
Rem      Obsolete as of Oracle rdbms 10g.
Rem      As far as I know, it only gets used in regression testing.
Rem      Updated version of Steve Muench''s initial copy.
Rem
Rem MODIFIED (MM/DD/YY)
Rem mjaeger   09/30/03 - bug 3015638: move XSU source from RDBMS vob to XDK 
Rem mjaeger   09/25/03 - bug 3015638: copy from rdbms vob to xdk vob 
Rem mjaeger   09/25/03 - Created in xdk vob for bug 3015638
Rem vnimani   10/17/00 - ora600-kgmexchi11 workaround: declare all methods
Rem vnimani   10/11/00 - fix setQueryDateFormat
Rem vnimani   08/23/00 - revert to no encoding tag by default
Rem vnimani   07/24/00 - ren xmlgenpkg.sql to xmlgen.sql
Rem vnimani   06/26/00 - add setdateformat for query
Rem vnimani   06/23/00 - caching to true in dbms_lob.createtemporary
Rem vnimani   06/13/00 - update setStylesheet to setStylesheetHeader
Rem vnimani   05/31/00 - since create or replace -> don''t need drop
Rem vnimani   05/22/00 - remove the call to exit
Rem vnimani   05/10/00 - grant execute to public as part of script
Rem mkrishna  03/26/00 - change setInsertBatch
Rem mkrishna  03/24/00 - change insert batch to batchsize
Rem vnimani   03/09/00 - duplicate setLobDuration; fix it
Rem vnimani   01/20/00 - add setinsertbatch and setcommitbatch
Rem mkrishna  02/06/00 - add new api to xmlgen
Rem vnimani   12/10/99 - add back NO_DTD
Rem vnimani   11/11/99 - support lob duration setting
Rem vnimani   10/20/99 - add support for header dtd and xml doc
Rem vnimani   10/08/99 - stylesheet bug fix
Rem vnimani   10/05/99 - add support for bind values in sql query
Rem vnimani   07/22/99 - support collIdAttr option
Rem mkrishna  07/15/99 - add insertXML
Rem vnimani   06/17/99 - add the documentation
Rem mkrishna  06/13/99 - XML generation package (calls OracleXMLStore)
Rem mkrishna  06/13/99 - Created
Rem
Rem This creates the XMLGEN package -- XSU's PL/SQL API

rem SET ECHO ON
rem SET FEEDBACK 1
rem SET NUMWIDTH 10
rem SET LINESIZE 80
rem SET TRIMSPOOL ON
rem SET TAB OFF
rem SET PAGESIZE 100

create or replace package xmlgen authid current_user as

  ----------------
  -- constants relevant to put and get XML
  DEFAULT_ROWTAG      CONSTANT VARCHAR2(3) := 'ROW';               /* rowtag */

  -- used to signal that the DB encoding is to be used
  DB_ENCODING          CONSTANT VARCHAR2(1) := '_';

  ----------------
  -- constants relevant to getXML
  ALL_ROWS            CONSTANT NUMBER      := -1;               /* ALL ROWS  */
  SKIP_NONE           CONSTANT NUMBER      := 0;                /* SKIP NONE */
  NONE                CONSTANT NUMBER      := 0;                  /* NO META */
  DTD                 CONSTANT NUMBER      := 1;                 /* NEED DTD */
  SCHEMA              CONSTANT NUMBER      := 2;              /* NEED SCHEMA */
  NO_DTD              CONSTANT NUMBER      := NONE;        /* NO DTD; legacy */
  DEFAULT_ROWSETTAG   CONSTANT VARCHAR2(6) := 'ROWSET';         /* rowsettag */
  DEFAULT_ERRORTAG    CONSTANT VARCHAR2(5) := 'ERROR';          /* error tag */
  DEFAULT_ROWIDATTR   CONSTANT VARCHAR2(3) := 'NUM';          /* Row id attr */

  -----------------
  -- constants relevant to insertXML/deleteXML/updateXML
  MATCH_CASE          CONSTANT NUMBER       := 0;               /* match case*/
  IGNORE_CASE         CONSTANT NUMBER       := 1;             /* ignore case */
  DEFAULT_DATE_FORMAT CONSTANT VARCHAR2(21) := 'MM/dd/yyyy HH:mm:ss';

  --------------------------------------------
  -- get and put xml relevant methods
  lobDuration  NUMBER :=  DBMS_LOB.SESSION;                /* local variable */
                                              /* default durations for lobs */
  PROCEDURE setRowTag(tag IN VARCHAR2 );
  PROCEDURE resetOptions;

  --------------------------------------------
  -- getXML relevant methods
  FUNCTION  getXML(query IN VARCHAR2, metaType IN NUMBER := NONE) RETURN CLOB;
  PROCEDURE getXML(query IN VARCHAR2, metaType IN NUMBER, xmlCLob IN CLOB);
  FUNCTION  getXML(query IN CLOB, metaType IN NUMBER := NONE) RETURN CLOB;
  PROCEDURE getXML(query IN CLOB, metaType IN NUMBER, xmlClob IN CLOB);

  -- functions and procedures to extract the DTD information
  FUNCTION  getDTD(query IN VARCHAR2) RETURN CLOB;
  FUNCTION  getDTD(query IN VARCHAR2, withVer IN BOOLEAN) RETURN CLOB;
  PROCEDURE getDTD(query IN VARCHAR2, withVer IN BOOLEAN, xmlClob IN CLOB);
  FUNCTION  getDTD(query IN CLOB) RETURN CLOB;
  FUNCTION  getDTD(query IN CLOB, withVer IN BOOLEAN) RETURN CLOB;
  PROCEDURE getDTD(query IN CLOB, withVer IN BOOLEAN, xmlClob IN CLOB);

  -- changes the row and rowset tag settings.
  PROCEDURE setRowSetTag(tag IN VARCHAR2 );
  PROCEDURE setErrorTag(tag IN VARCHAR2);

  PROCEDURE setRowIdAttrName(tag IN VARCHAR2);
  PROCEDURE setRowIdColumn(columnname IN VARCHAR2);

  PROCEDURE setCollIdAttr(attrname IN VARCHAR2);
  PROCEDURE useNullAttributeIndicator(flag IN BOOLEAN);

  PROCEDURE setQueryDateFormat(mask IN VARCHAR2);

  -- case for tag names..
  PROCEDURE useUpperCaseTagNames;
  PROCEDURE useLowerCaseTagNames;
  PROCEDURE useDefaultCaseTagNames;

  PROCEDURE setMaxRows(rows IN NUMBER);
  PROCEDURE setSkipRows(rows IN NUMBER);

  PROCEDURE setStylesheetType(type in VARCHAR2);
  PROCEDURE setStylesheet(uri IN VARCHAR2);
  PROCEDURE setStylesheet(uri IN VARCHAR2, type IN VARCHAR2);

  PROCEDURE setEncodingTag(enc IN VARCHAR2 := DB_ENCODING);

  PROCEDURE setLobDuration(duration IN NUMBER);

  -- bind values for SQL queries..
  PROCEDURE setBindValue(bName IN VARCHAR2, bValue IN VARCHAR2);
  PROCEDURE clearBindValues;

  -- header information to be appended to the result..
  -- metaheader refers to DTD headers.
  PROCEDURE setMetaHeader(header IN CLOB := null);

  -- data header portions appended to the data portion of the result..
  PROCEDURE setDataHeader(header IN CLOB := null, docTag IN VARCHAR2 := null);

  -- exception handling..
  PROCEDURE setRaiseException(flag IN BOOLEAN);
  PROCEDURE propagateOriginalException(flag IN BOOLEAN);
  PROCEDURE getExceptionContent(errNo OUT NUMBER, errMsg OUT VARCHAR2);

  --------------------------------------------------------------------------
  ----- INSERT/UPDATE/DELETE routines.

  PROCEDURE setIgnoreTagCase(ignore IN NUMBER);
  PROCEDURE setDateFormat(dateFormat IN VARCHAR2);

  -- set the columns to update. Relevant for insert and update routines..
  PROCEDURE setUpdateColumn(colName IN VARCHAR2);
  PROCEDURE clearUpdateColumnList;

  -- set the key column name to be used for updates and deletes.
  PROCEDURE setKeyColumn(colName IN VARCHAR2);
  PROCEDURE clearKeyColumnList;

  -- set the batch size for executing update, insert and delete routines..
  PROCEDURE setBatchSize(batchSize IN NUMBER);
  PROCEDURE setCommitBatch(batchSize IN NUMBER);

  -- insertXML relevant methods
  FUNCTION  insertXML(tablename IN VARCHAR2, xmldoc IN VARCHAR2) RETURN NUMBER;
  FUNCTION  insertXML(tablename IN VARCHAR2, xmldoc IN CLOB) RETURN NUMBER;

  -- updateXML relevant routines..
  FUNCTION  updateXML(tablename IN VARCHAR2, xmldoc IN VARCHAR2) RETURN NUMBER;
  FUNCTION  updateXML(tablename IN VARCHAR2, xmldoc IN CLOB) RETURN NUMBER;

  -- updateXML relevant routines..
  FUNCTION  deleteXML(tablename IN VARCHAR2, xmldoc IN VARCHAR2) RETURN NUMBER;
  FUNCTION  deleteXML(tablename IN VARCHAR2, xmldoc IN CLOB) RETURN NUMBER;

  -------private method declarations------------------------------------------
  -- we must do this as a bug workaround; otherwise we get ora-600 [kgmexchi11]
  PROCEDURE p_useNullAttrInd(flag IN number);
  PROCEDURE p_propOriginalExc(flag IN number);
  PROCEDURE p_setRaiseException(flag IN number);
  PROCEDURE p_setEncodingTag(enc IN VARCHAR2);
  PROCEDURE p_setMetaHeader(header IN CLOB);
  PROCEDURE p_setDataHeader(header IN CLOB, docTag IN VARCHAR2);
  PROCEDURE p_getDTD(query IN VARCHAR2, metaType IN NUMBER, withVer IN NUMBER, xmlClob IN CLOB);
  PROCEDURE p_getDTD(query IN CLOB, metaType IN NUMBER, withVer IN NUMBER, xmlClob IN CLOB);

end xmlgen;
/
show errors;


CREATE OR REPLACE PACKAGE BODY xmlgen AS

  /*==========================================================================
  // NAME
  //   insertXML()
  // PARAMETERS
  //   tableName     (IN) - the table into which it should be inserted
  //   xmlDoc        (IN) - the XML Document to be inserted
  // RETURNS
  //   the number of rows inserted into
  // DESCRIPTION
  //   This is the main function for insert which inserts a given document
  //-------------------------------------------------------------------------*/
  FUNCTION insertXML(tableName in varchar2, xmlDoc in varchar2) return number
  as LANGUAGE JAVA NAME
   'OracleXMLStore.insertXML(java.lang.String,java.lang.String) return int';

  FUNCTION insertXML(tableName in varchar2, xmlDoc in CLOB) return number
  as LANGUAGE JAVA NAME
   'OracleXMLStore.insertXML(java.lang.String,oracle.sql.CLOB) return int';

  /*==========================================================================
  // NAME
  //   updateXML()
  // PARAMETERS
  //   tableName     (IN) - the table against which it should be updated
  //   xmlDoc        (IN) - the XML Document to be inserted
  // RETURNS
  //   the number of rows inserted into
  // DESCRIPTION
  //   This is the main function for insert which inserts a given document
  //-------------------------------------------------------------------------*/
  FUNCTION updateXML(tableName in varchar2, xmlDoc in varchar2) return number
  as LANGUAGE JAVA NAME
   'OracleXMLStore.updateXML(java.lang.String,java.lang.String) return int';

  FUNCTION updateXML(tableName in varchar2, xmlDoc in CLOB) return number
  as LANGUAGE JAVA NAME
   'OracleXMLStore.updateXML(java.lang.String,oracle.sql.CLOB) return int';

  /*==========================================================================
  // NAME
  //   deleteXML()
  // PARAMETERS
  //   tableName     (IN) - the table into which it should be deleted
  //   xmlDoc        (IN) - the XML Document to be inserted
  // RETURNS
  //   the number of rows inserted into
  // DESCRIPTION
  //   This is the main function for insert which inserts a given document
  //-------------------------------------------------------------------------*/
  FUNCTION deleteXML(tableName in varchar2, xmlDoc in varchar2) return number
  as LANGUAGE JAVA NAME
   'OracleXMLStore.deleteXML(java.lang.String,java.lang.String) return int';

  FUNCTION deleteXML(tableName in varchar2, xmlDoc in CLOB) return number
  as LANGUAGE JAVA NAME
   'OracleXMLStore.deleteXML(java.lang.String,oracle.sql.CLOB) return int';

  /*==========================================================================
  // NAME
  //   setUpdateColumnName()
  // PARAMETERS
  //   colName (IN) - the column name to be part of the update list
  // RETURNS
  // DESCRIPTION
  //   This function makes the column passed to be used for updates
  //-------------------------------------------------------------------------*/
  -- set the columns to update. Relevant for insert and update routines..
  PROCEDURE setUpdateColumn(colName IN VARCHAR2)
  as LANGUAGE JAVA NAME
   'OracleXMLStore.setUpdateColumn(java.lang.String)';

  PROCEDURE clearUpdateColumnList
  as LANGUAGE JAVA NAME
   'OracleXMLStore.clearUpdateColumnList()';

 /*==========================================================================
  // NAME
  //   setUpdateColumnName()
  // PARAMETERS
  //   colName (IN) - the column name to be part of the update list
  // RETURNS
  // DESCRIPTION
  //   This function makes the column be used in the where clause for
  // deletes and updates..
  //-------------------------------------------------------------------------*/
  -- set the key column name to be used for updates and deletes.
  PROCEDURE setKeyColumn(colName IN VARCHAR2)
  as LANGUAGE JAVA NAME
   'OracleXMLStore.setKeyColumn(java.lang.String)';

  PROCEDURE clearKeyColumnList
   as LANGUAGE JAVA NAME
   'OracleXMLStore.clearKeyColumnList()';

  /*==========================================================================
  // NAME
  //   setRowsetTag()
  // PARAMETERS
  //   ROWSET    (IN) - the root tag of the document
  // DESCRIPTION
  //   setRowsetTag() sets the name of the root tag (default is "ROWSET").
  //   A string value of null or empty will cause the rowTag to take its
  //   place. If the rowTag is also suppressed then the column
  //   name is used as the root tag (note: in this last case, the result set
  //   produced by the query can contain no more than one data record
  //   which is one column wide).
  //-------------------------------------------------------------------------*/
  PROCEDURE setRowsetTag(tag VARCHAR2 )
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setRowsetTag(java.lang.String)';

  /*==========================================================================
  // NAME
  //   setRowTag()
  // PARAMETERS
  //   rowTagName   (IN) - tag to markup each resulting record
  // DESCRIPTION
  //   Sets the tag name which is to be used as markup for each resulting
  //   record/row. The default tag name is "ROW"; a null or empty string
  //   implies that each row is not to be markup encapsulated (i.e. no
  //   row separators are to be used. In this situation the result set
  //   produced by the query can either contain many record which are one
  //   column wide, or one record which is many columns wide.
  //-------------------------------------------------------------------------*/
  PROCEDURE setRowTag(tag VARCHAR2 )
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setRowTag(java.lang.String)';

  /*==========================================================================
  // NAME
  //   setRowIDAttrName()
  // PARAMETERS
  //   tag     (IN) - the row Id. tag
  // DESCRIPTION
  //   This sets the name of the attribute "ID" used in the row separator.
  //   By giving a value of null, you can turn off the ID printing.
  //-------------------------------------------------------------------------*/
  PROCEDURE setRowIdAttrName(tag VARCHAR2)
  AS LANGUAGE JAVA NAME
  'OracleXMLStore.setRowIdAttrName(java.lang.String)';

  /*==========================================================================
  // NAME
  //   setRowIdColumn()
  // PARAMETERS
  //   tag   (IN) - name of column whose value is to be used for id. attr.
  // DESCRIPTION
  //   This makes the ROW tag's id attribute to have the same value as that
  //   of the column name specified. The column must be a scalar column.
  //   A value of null would revert it to the default behaviour which is
  //   for the row id attribute to get the value of the row count.
  //-------------------------------------------------------------------------*/
  PROCEDURE setRowIdColumn(columnName IN VARCHAR2)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setRowIdColumn(java.lang.String)';

  /*===========================================================================
  // NAME
  //   setCollIdAttr() -- public void
  // PARAMETERS
  //   attrname    (IN) - collection element's id-attribute name
  // DESCRIPTION
  //   Sets the collection element's id-attribute name. Passing in a null,
  //   or an empty string with inhibit the printing of the collIDAttr
  //   which is also the default behaviour.
  //-------------------------------------------------------------------------*/
  PROCEDURE setCollIdAttr(attrname VARCHAR2)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setCollIdAttr(java.lang.String)';

  /*==========================================================================
  // NAME
  //   useNullAttributeIndicator()
  // PARAMETERS
  //   flag  (IN) - to use an attribute to denote NULLness?
  // DESCRIPTION
  //   Specified weather to use an XML attribute to indicate NULLness; or to
  //   do it by omitting the inclusion of the particular entity in the XML
  //   document.
  //-------------------------------------------------------------------------*/
  PROCEDURE p_useNullAttrInd(flag IN number)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.useNullAttributeIndicator(int)';

  PROCEDURE useNullAttributeIndicator(flag IN BOOLEAN) is
  begin
    if flag = true then
      p_useNullAttrInd(1);
    else
      p_useNullAttrInd(0);
    end if;
  end useNullAttributeIndicator;

  /*==========================================================================
  //------------------------------------------------------------------------*/
  PROCEDURE setQueryDateFormat(mask IN VARCHAR2)
  as LANGUAGE JAVA NAME
   'OracleXMLStore.setQueryDateFormat(java.lang.String)';

  /*==========================================================================
  // NAME
  //   propagateOriginalException()
  // DESCRIPTION
  //   Makes it so that the original exception is thrown rather than
  //   the OracleXMLSQLException wrapper.
  //-------------------------------------------------------------------------*/
  PROCEDURE p_propOriginalExc(flag IN number)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.propagateOriginalException(int)';

  PROCEDURE propagateOriginalException(flag IN BOOLEAN) is
  begin
    if flag = true then
      p_propOriginalExc(1);
    else
      p_propOriginalExc(0);
    end if;
  end propagateOriginalException;

  /*==========================================================================
  // NAME
  //   setRaiseException()
  // PARAMETERS
  //   flag  (IN) - to use an attribute to denote NULLness?
  // DESCRIPTION
  //   Makes so that the XSU does not create the error document when it
  //   catches an exception; but rather, it propagates the exception.
  //-------------------------------------------------------------------------*/
  PROCEDURE p_setRaiseException(flag IN number)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setRaiseException(int)';

  PROCEDURE setRaiseException(flag IN BOOLEAN) is
  begin
    if flag = true then
      p_setRaiseException(1);
    else
      p_setRaiseException(0);
    end if;
  end setRaiseException;

  /*==========================================================================
  // NAME
  //   useUpperCaseTagNames
  // PARAMETERS
  //   none
  // DESCRIPTION
  //   This will set the case to be upper for all tag names.
  //-------------------------------------------------------------------------*/
  PROCEDURE useUpperCaseTagNames
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.useUpperCaseTagNames()';

  /*==========================================================================
  // NAME
  //   useLowerCaseTagNames
  // DESCRIPTION
  //   This will set the case to be lower for all tag names.
  //-------------------------------------------------------------------------*/
  PROCEDURE useLowerCaseTagNames
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.useLowerCaseTagNames()';

  /*==========================================================================
  // NAME
  //   useDefaultCaseTagNames
  // DESCRIPTION
  //   This will make it so the tag names remain in their default case.
  //-------------------------------------------------------------------------*/
  PROCEDURE useDefaultCaseTagNames
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.useDefaultCaseTagNames()';

  /*==========================================================================
  // NAME
  //   setIgnoreTagCase  (for insertXML)
  // DESCRIPTION
  //   This will make make insertXML ignore the case of the tags
  //-------------------------------------------------------------------------*/
  PROCEDURE setIgnoreTagCase(ignore IN number)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.ignoreTagCase(int)';

  /*==========================================================================
  // NAME
  //   setErrorTag()
  // PARAMETERS
  //   tag         (IN) - the error tag name
  // DESCRIPTION
  //   setErrorTag() sets the name of the error tag (default "ERROR")
  //   in case an error is raised. Remember that when an error is raised
  //   NO DTD is generated.
  //-------------------------------------------------------------------------*/
  PROCEDURE setErrorTag(tag VARCHAR2 )
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setErrorTag(java.lang.String)';

  /*==========================================================================
  // NAME
  //   setMaxRows()
  // PARAMETERS
  //   rows      (IN) - max number of rows to be gotten from the query
  // DESCRIPTION
  //   This sets the maximum number of rows to be retreived from the
  //   query result after "skipRows" number of rows are skipped.
  // NOTE
  //   Setting rows to xmlgen.ALL_ROWS means no max on rows to be gotten.
  //-------------------------------------------------------------------------*/
  PROCEDURE setMaxRows(rows NUMBER)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setMaxRows(int)';

  /*==========================================================================
  // NAME
  //   setSkipRows()
  // PARAMETERS
  //   rows      (IN) - rows to skip
  // DESCRIPTION
  //  This sets the number of rows to skip.
  // NOTE
  //  Setting rows to xmlgen.SKIP_NONE means no rows to be skipped.
  //-------------------------------------------------------------------------*/
  PROCEDURE setSkipRows(rows NUMBER)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setSkipRows(int)';

  /*==========================================================================
  // NAME
  //   setDateFormat (for insertXML)
  // PARAMETERS
  //   dateFormat - date format
  // DESCRIPTION
  //   This will set the format for dates to be bound in insert
  //-------------------------------------------------------------------------*/
  PROCEDURE setDateFormat(dateFormat IN varchar2)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setDateFormat(java.lang.String)';

  /*==========================================================================
  // NAME
  //   setBatchSize
  // PARAMETERS
  //   dateFormat - date format
  // DESCRIPTION
  //   Sets the insert batch size.  The insert batch size refers to the
  //   number or records which are grouped together and then inserted
  //   together in a single db. roundrip.
  //   Note that the default insert batch size is 17.
  //-------------------------------------------------------------------------*/
  PROCEDURE setBatchSize(batchSize IN number)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setBatchSize(int)';

  /*==========================================================================
  // NAME
  //   setCommitBatch
  // PARAMETERS
  //   batchSize - commit batch size
  // DESCRIPTION
  //   Sets the commit batch size.  The commit batch size refers to the
  //   number or records inserted after which a commit should follow.
  //   Note that the default commit batch size is 51.
  //-------------------------------------------------------------------------*/
  PROCEDURE setCommitBatch(batchSize IN number)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setCommitBatch(int)';

  /*==========================================================================
  // NAME
  //   setStylesheetType()
  // PARAMETERS
  //   type   (IN) - the stylesheet type
  // DESCRIPTION
  //   Sets the type of the stylesheet.
  //-------------------------------------------------------------------------*/
  PROCEDURE setStylesheetType(type VARCHAR2)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setStylesheetHeaderType(java.lang.String)';

  /*==========================================================================
  // NAME
  //   setStylesheet()
  // PARAMETERS
  //   uri   (IN) -  the stylesheet URI
  //   type   (IN) - the stylesheet type
  // DESCRIPTION
  //   The stylesheet information to be appended to the XML doc.
  //-------------------------------------------------------------------------*/
  PROCEDURE setStylesheet( uri VARCHAR2)
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.setStylesheetHeader(java.lang.String)';

  PROCEDURE setStylesheet( uri VARCHAR2, type VARCHAR2) IS
  begin
    setStylesheet(uri);
    setStylesheetType(type);
  end setStylesheet;

  /*==========================================================================
  // NAME
  //   setEncodingTag
  // PARAMETERS
  //   enc       (IN) - IANA encoding name
  // DESCRIPTION
  //  This method only set the encoding tag in the XML PI (processing
  //  instruction), and does not cause any encoding conversions.
  //  If <code>null</code> is specified as the encoding then no encoding tag
  //  is specified in the PI.  If no value is passed as the encoding, then
  //  the database encoding is specified in the PI.
  //-------------------------------------------------------------------------*/
  PROCEDURE p_setEncodingTag(enc IN VARCHAR2)
  as LANGUAGE JAVA NAME
   'OracleXMLStore.setEncodingTag(java.lang.String)';

  PROCEDURE setEncodingTag(enc IN VARCHAR2 := DB_ENCODING) is
  begin
    p_setEncodingTag(enc);
  end setEncodingTag;

  /*==========================================================================
  // NAME
  //   setLobDuration
  // PARAMETERS
  //   duration    (IN) - lob duration
  // DESCRIPTION
  //   Sets the duration for the created temp lobs; legal values are
  //   DBMS_LOB.CALL and DBMS_LOB.SESSION
  //-------------------------------------------------------------------------*/
  PROCEDURE setLobDuration(duration IN NUMBER) is
  begin
    lobDuration := duration ;
  end setLobDuration;

  /*==========================================================================
  // NAME
  //   resetOptions
  // DESCRIPTION
  //   resetOptions procedure resets all the options back to their default
  //   values.
  //-------------------------------------------------------------------------*/
  PROCEDURE resetOptions
  AS LANGUAGE JAVA NAME
   'OracleXMLStore.resetOptions()';

  /*==========================================================================
  // NAME
  //   getExceptionContent
  // PARAMETERS
  //   errNo    (OUT) - error number
  //   errMsg   (OUT) - error message
  // DESCRIPTION
  //   If raiseOriginalE is true, this call returns the original
  //   exception's error code and error message (i.e. sql error code)
  //-------------------------------------------------------------------------*/
  PROCEDURE getExceptionContent(errNo OUT NUMBER, errMsg OUT VARCHAR2)
  as LANGUAGE JAVA NAME
   'OracleXMLStore.getExceptionContent(int[], java.lang.String[])';

  /*==========================================================================
  // NAME
  //   getXML()
  // PARAMETERS
  //   query         (IN) - the SQL query
  //   metaType       (IN) - NONE, DTD, SCHEMA
  // RETURNS
  //   CLOB containing the XML document.
  // DESCRIPTION
  //   This is the main function which given a SQL query it returns
  //   an XML document.
  //-------------------------------------------------------------------------*/
  FUNCTION getXML(query IN VARCHAR2,metaType IN NUMBER := NONE) return CLOB is
   clb CLOB;
  begin
    dbms_lob.createtemporary(clb, true, lobDuration);
    getXML(query, metaType, clb);
    return clb;
  end getXML;

  FUNCTION getXML(query IN CLOB, metaType IN NUMBER := NONE) return CLOB is
   clb CLOB;
  begin
    dbms_lob.createtemporary(clb, true, lobDuration);
    getXML(query, metaType, clb);
    return clb;
  end getXML;

  PROCEDURE getXML(query IN CLOB, metaType IN NUMBER, xmlClob IN CLOB)
  as LANGUAGE JAVA NAME
   'OracleXMLStore.getXML(oracle.sql.CLOB, int, oracle.sql.CLOB)';

  PROCEDURE getXML(query IN VARCHAR2, metaType IN NUMBER, xmlClob IN CLOB)
  as LANGUAGE JAVA NAME
   'OracleXMLStore.getXML(java.lang.String, int, oracle.sql.CLOB)';

  /*==========================================================================
  // NAME
  //   setBindValue
  // PARAMETERS
  //   bindName    (IN) - bind name
  //   bindValue   (IN) - bind value
  // DESCRIPTION
  //   Sets the value for a certain bind-name.
  //-------------------------------------------------------------------------*/
  PROCEDURE setBindValue(bName IN VARCHAR2, bValue IN VARCHAR2)
  as LANGUAGE JAVA NAME
   'OracleXMLStore.bindValue(java.lang.String, java.lang.String)';

  /*==========================================================================
  // NAME
  //   clearBindValues
  // PARAMETERS
  // DESCRIPTION
  //   clears the values for all the bind-names
  //-------------------------------------------------------------------------*/
  PROCEDURE clearBindValues
  as LANGUAGE JAVA NAME
   'OracleXMLStore.clearBindValues()';


  /*==========================================================================
  // NAME
  //   setMetaHeader
  // PARAMETERS
  //   header    (IN) - metadata to be prepended to XML doc's metadata
  // DESCRIPTION
  //  Sets the XML metadata header; which until unset is inserted at the
  //  begining of all XML metadata generated by this object (DTD or XMLSchema).
  //  To unset the header, call this function with null passed in for the
  //  "header" parameter.
  //-------------------------------------------------------------------------*/
  PROCEDURE p_setMetaHeader(header IN CLOB)
  as LANGUAGE JAVA NAME
   'OracleXMLStore.setMetaHeader(oracle.sql.CLOB)';

  PROCEDURE setMetaHeader(header IN CLOB := null) is
  begin
    p_setMetaHeader(header);
  end setMetaHeader;

  /*==========================================================================
  // NAME
  //   setDataHeader
  // PARAMETERS
  //   header    (IN) - XML to be prepended to XML doc's data part
  //   docTag    (IN) - tag to enclose XML data header + XML query data
  // DESCRIPTION
  //  Sets the XML data header, which until unset is inserted at the
  //  begining of all the XML data generated by this object.
  //  The docTag parameter specifies the tag name to be used to enclose
  //  the XML resulting from adding the "header" and the generated
  //  XML data.
  //  To unset the header, call this function with null passed in for the
  //  "header" parameter.
  //-------------------------------------------------------------------------*/
  PROCEDURE p_setDataHeader(header IN CLOB, docTag IN VARCHAR2)
  as LANGUAGE JAVA NAME
   'OracleXMLStore.setDataHeader(oracle.sql.CLOB, java.lang.String)';

  PROCEDURE setDataHeader(header IN CLOB := null,
                         docTag IN VARCHAR2 := null)  IS
  begin
    p_setDataHeader(header, docTag);
  end setDataHeader;


  /*==========================================================================
  // NAME
  //   getDTD
  // PARAMETERS
  //   query      (IN) - sql query
  //   withVer    (IN) - generate the xml doc version info?
  // DESCRIPTION
  //   Given a SQL query it generated the DTD only.  If the withVer
  //   parameter is true, then the version info is also generated along with
  //   the DTD.
  //-------------------------------------------------------------------------*/
  PROCEDURE p_getDTD(query IN VARCHAR2, metaType IN NUMBER,
                        withVer IN NUMBER, xmlClob IN CLOB)
  as LANGUAGE JAVA NAME
   'OracleXMLStore.getXMLMetaData(java.lang.String, int, int,oracle.sql.CLOB)';

  FUNCTION getDTD(query IN VARCHAR2) RETURN CLOB is
  begin
   return getDTD(query, false);
  end getDTD;

  FUNCTION getDTD(query IN VARCHAR2, withVer IN BOOLEAN) RETURN CLOB IS
   clb CLOB;
  begin

    dbms_lob.createtemporary(clb,true,lobDuration);
    getDTD(query, withVer, clb);
    return clb;

  end getDTD;

  PROCEDURE getDTD(query IN VARCHAR2, withVer IN BOOLEAN, xmlCLob IN CLOB) is
   a integer;
  begin

    if withVer = true then
      a:= 1;
    else
      a:=0;
    end if;

    p_getDTD(query,DTD,a,xmlCLob);
  end getDTD;

  PROCEDURE p_getDTD(query IN CLOB, metaType IN NUMBER,
                        withVer IN NUMBER, xmlClob IN CLOB)
  as LANGUAGE JAVA NAME
   'OracleXMLStore.getXMLMetaData(oracle.sql.CLOB, int, int, oracle.sql.CLOB)';

  PROCEDURE getDTD(query IN CLOB, withVer IN BOOLEAN, xmlCLob IN CLOB) is
   a integer;
  begin
    if withVer = true then
     a := 1;
    else
     a := 0;
    end if;

    p_getDTD(query,DTD,a,xmlCLob);
  end getDTD;

  FUNCTION getDTD(query IN CLOB) RETURN CLOB IS
  begin
    return getDTD(query, false);
  end getDTD;

  FUNCTION getDTD(query IN CLOB, withVer IN BOOLEAN) RETURN CLOB IS
    clb CLOB;
  begin

    dbms_lob.createtemporary(clb,true,lobDuration);
    getDTD(query,withVer,clb);
    return clb;

  end getDTD;

end xmlgen;
/
show errors;

grant execute on xmlgen to public;
create or replace public synonym xmlgen for xmlgen;

