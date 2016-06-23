Rem
Rem $Header: xsupkg.sql 29-mar-2001.18:48:43 vnimani Exp $
Rem
Rem xsupkg.sql
Rem
Rem  Copyright (c) Oracle Corporation 2000. All Rights Reserved.
Rem
Rem    NAME
Rem      xsupkg.sql - Load XSU packages
Rem
Rem    DESCRIPTION
Rem      Load XSU packages
Rem
Rem    NOTES
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    vnimani     03/29/01 - update for the new directory structure
Rem    vnimani     07/24/00 - update xsupkg.sql with new file names
Rem    vnimani     05/22/00 - Created
Rem

-- load the XSU PL/SQL packages
@@xmlgen.sql
@@dbmsxsu.sql

exit;
