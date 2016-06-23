DROP SEQUENCE xmldocid_seq;
DROP TABLE xmldoc;
DROP TYPE xmldocfrag;
CREATE SEQUENCE xmldocid_seq;

CREATE TYPE XMLDOCFRAG AS OBJECT ( contents CLOB );
.
/
CREATE TABLE xmldoc (
docid NUMBER PRIMARY KEY,
author VARCHAR2(80),
title  VARCHAR2(80),
created DATE,
stylesheet VARCHAR2(200),
document XMLDOCFRAG /* CLOB */ );
CREATE TRIGGER befins_XMLDOC
BEFORE INSERT ON xmldoc FOR EACH ROW
BEGIN
  :new.created := sysdate;
  SELECT xmldocid_seq.nextval
    INTO :new.docid
    FROM DUAL;
END;
.
/
