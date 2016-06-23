
-- txdemo1.sql - a transx demo

CREATE TABLE i18n_messages (
  message_id number,
  message_code number,
  message_name char(32),
  message_description varchar2(300),
  version_created number,
  version_updated number,
  message_type_id number
);

CREATE SEQUENCE i18n_message_seq;

CREATE TABLE i18n_message_types (
  message_type_code varchar2(10),
  message_type_id   number(5)
);

INSERT INTO i18n_message_types VALUES( 'SUCCESS', 1000 );
INSERT INTO i18n_message_types VALUES( 'INFO', 2000 );
INSERT INTO i18n_message_types VALUES( 'WARNING', 3000 );
INSERT INTO i18n_message_types VALUES( 'ERROR', 4000 );

commit;
