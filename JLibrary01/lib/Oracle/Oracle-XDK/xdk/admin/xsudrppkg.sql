Rem
Rem $Header: xsudrppkg.sql 21-feb-2003.16:59:15 nkandalu Exp $
Rem
Rem xsudrppkg.sql
Rem
Rem Copyright (c) 2003, Oracle Corporation.  All rights reserved.  
Rem
Rem    NAME
Rem      xsudrppkg.sql - Drop XSU packages
Rem
Rem    DESCRIPTION
Rem      Drop XSU packages
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

-- Drop the XSU PL/SQL packages

drop package XMLGEN;
drop package DBMS_XMLQUERY;

exit;
