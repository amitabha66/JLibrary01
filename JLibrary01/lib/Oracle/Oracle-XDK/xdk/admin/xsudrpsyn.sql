Rem
Rem $Header: xsudrpsyn.sql 21-feb-2003.16:59:15 nkandalu Exp $
Rem
Rem xsudrpsyn.sql
Rem
Rem Copyright (c) 2003, Oracle Corporation.  All rights reserved.  
Rem
Rem    NAME
Rem      xsudrpsyn.sql - Drop XSU synonyms
Rem
Rem    DESCRIPTION
Rem      Drop XSU synonyms
Rem
Rem    NOTES
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    nkandalu    02/21/03 - nkandalu_bug-2679130
Rem    nkandalu    02/05/03 - Created
Rem

SET ECHO ON
SET FEEDBACK 1
SET NUMWIDTH 10
SET LINESIZE 80
SET TRIMSPOOL ON
SET TAB OFF
SET PAGESIZE 100

-- drop synonyms created for the XSU PL/SQL packages
drop public synonym dbms_xmlsave;
drop public synonym dbms_xmlquery;
drop public synonym xmlgen;

exit;
