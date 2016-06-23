drop sequence newsstory_id_seq;
drop table newsstory;
create sequence newsstory_id_seq;
create table newsstory (
id number,
title varchar2(200),
url   varchar2(200),
source varchar2(80)
);
create trigger newsstory_before_insert
before insert on newsstory for each row
BEGIN
  SELECT newsstory_id_seq.nextval
    INTO :new.id
    FROM dual;
END;
.
/
insert into newsstory values
(null,
'Oracle Technet Goes Live!',
'http://otn.oracle.com',
'Oracle');
insert into newsstory values
(null,
'XSLT 1.0 Recommendation Released',
'http://www.w3.org/tr/xslt',
'W3C');
commit;

