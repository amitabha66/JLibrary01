set scan off
set echo off
REM
REM $Author: kkarun $
REM $Date: 20-apr-00.23:38:40 $
REM $Source: /vobs/oracore3/demo/xdk/java/xsql/demo/classerr/invalidclasses.sql.mkelem $
REM $Revision: /main/0 $
REM
drop type "XSQLJavaClassErrors";
drop type "XSQLJavaClassError";
create type "XSQLJavaClassError" as object ( "Message" varchar2(4000) );
.
/
create type "XSQLJavaClassErrors" as table of "XSQLJavaClassError";
.
/
create view "XSQLJavaClassErrorView" as
  select replace(dbms_java.longname(uo.object_name),'/','.') "ClassName", 
         CAST(MULTISET(SELECT "XSQLJavaClassError"(ue.text) 
                         FROM user_errors ue 
                        WHERE name = uo.object_name
                       ORDER BY ue."SEQUENCE") AS "XSQLJavaClassErrors") AS "Errors"
    from user_objects uo
  where object_type = 'JAVA CLASS'
  and status = 'INVALID'
  order by replace(dbms_java.longname(object_name),'/','.')
.
/

