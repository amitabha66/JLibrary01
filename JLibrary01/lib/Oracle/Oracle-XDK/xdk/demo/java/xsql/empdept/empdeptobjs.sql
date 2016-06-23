drop view department;
drop type dept_t;
drop type emp_list;
drop type emp_t;
create or replace type emp_t as object (
  empno number,
  ename varchar2(80),
  sal   number
);
.
/

create or replace type emp_list as table of emp_t;
.
/

create or replace type dept_t as object (
  deptno number, 
  dname varchar2(80),
  loc   varchar2(80),
  employees emp_list
);
.
/

create or replace view department of dept_t
with object OID (deptno)
as select deptno, dname, loc,
          cast(multiset(select empno, ename, sal 
                        from emp 
                        where emp.deptno = dept.deptno
                        ) as emp_list ) employees
     from dept;

create trigger department_ins
instead of insert on department
for each row 
declare
  emps emp_list;
  emp  emp_t;
begin
  -- Insert the master
  insert into dept( deptno, dname, loc ) 
  values (:new.deptno, :new.dname, :new.loc);

  -- Insert the details
  emps := :new.employees;
  for i in 1..emps.count loop
    emp := emps(i);
    insert into emp(deptno, empno, ename, sal)
    values (:new.deptno, emp.empno, emp.ename, emp.sal);
  end loop;
end;
.
/
REM 
REM 
REM <ROWSET>
REM    <ROW>
REM       <DEPTNO>99</DEPTNO>
REM       <DNAME>ACCOUNTING</DNAME>
REM       <LOC>NEW YORK</LOC>
REM       <EMPLOYEES>
REM          <EMPLOYEES_ITEM>
REM             <EMPNO>1111</EMPNO>
REM             <ENAME>CLARK</ENAME>
REM             <SAL>2450</SAL>
REM          </EMPLOYEES_ITEM>
REM          <EMPLOYEES_ITEM>
REM             <EMPNO>2222</EMPNO>
REM             <ENAME>KING</ENAME>
REM             <SAL>5000</SAL>
REM          </EMPLOYEES_ITEM>
REM          <EMPLOYEES_ITEM>
REM             <EMPNO>3333</EMPNO>
REM             <ENAME>MILLER</ENAME>
REM             <SAL>1300</SAL>
REM          </EMPLOYEES_ITEM>
REM       </EMPLOYEES>
REM    </ROW>
REM </ROWSET>

