Rem
Rem $Header: xsusyn.sql 16-feb-2001.19:27:01 vnimani  Exp $
Rem
Rem xsusyn.sql
Rem
Rem  Copyright (c) Oracle Corporation 2001. All Rights Reserved.
Rem
Rem    NAME
Rem      xsusyn.sql - Create XSU synonyms
Rem
Rem    DESCRIPTION
Rem      Create XSU synonyms
Rem
Rem    NOTES
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    vnimani     05/02/01 - Created
Rem

-- create synonyms for the XSU PL/SQL packages
drop public synonym dbms_xmlsave;
drop public synonym dbms_xmlquery;
drop public synonym xmlgen;
create public synonym dbms_xmlsave for dbms_xmlsave;
create public synonym dbms_xmlquery for dbms_xmlquery;
create public synonym xmlgen for xmlgen;

exit;
