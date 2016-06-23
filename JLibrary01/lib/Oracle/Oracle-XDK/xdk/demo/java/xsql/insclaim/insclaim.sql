set scan off
set echo on
set termout on
REM
REM $Author: smuench $
REM $Date: 2000/04/09 02:15:49 $
REM $Source: C:\\cvsroot/xsql/src/demo/insclaim/insclaim.sql,v $
REM $Revision: 1.4 $
REM

drop synonym claim;

drop table settlement_payments;

drop view insurance_claim_view;

drop table insurance_claim;

drop view policy_view;

drop table policy;

drop view policyholder_view;

drop table policyholder;

drop type insurance_claim_t;

drop type settlements_t;

drop type payment;

drop type policy_t;

drop type policyholder_t;

drop type address_t;

create type address_t as object( Street varchar2(80), City Varchar2(80), State VARCHAR2(80),
Zip NUMBER );
.
/
create type policyholder_t as object( CustomerId number,
    			   	    FirstName varchar2(80),
				    LastName varchar2(80),
                                    HomeAddress address_t);
.
/
create type policy_t as object( 
 policyID number, 
 primaryinsured policyholder_t);
.
/
create type payment as object( 
 PayDate DATE, 
 Amount NUMBER, 
 Approver VARCHAR2(8));
.
/
create type settlements_t as table of payment;
.
/
create type insurance_claim_t as object (
  claimid number, 
  filed   date,
  claimpolicy policy_t, 
  settlements settlements_t,
  damageReport varchar2(4000) /* XML */
);
.
/
create table policyholder( CustomerId number,
    			   FirstName varchar2(80),
			   LastName varchar2(80),
                           HomeAddress address_t,
  constraint policyholder_pk primary key (customerid) );

insert into policyholder values ( 1044, 'Paul','Astoria',
    ADDRESS_T('123 Cherry Lane','SF','CA','94132'));

insert into policyholder values ( 1045, 'Martina','Boyle',
    ADDRESS_T('55 Belden Place','SF','CA','94102'));

create or replace force view policyholder_view of policyholder_t
        with object OID (customerid)
        as select customerid, firstname, lastname, homeaddress
        from policyholder;

create table policy( 
 policyid number, 
 primarycustomerid number,
 constraint policy_pk primary key (policyid),
 constraint customer_fk foreign key (primarycustomerid) references policyholder
 );

insert into policy values ( 8895, 1044 );
insert into policy values ( 9054, 1045 );

create or replace force view policy_view of policy_t
        with object OID (policyid)
        as select p.policyid, 
           (SELECT value(phv)
             from policyholder_view phv
            WHERE phv.customerid = p.primarycustomerid) as primaryinsured
        from policy p;

create table insurance_claim(
  claimid number, 
  filed   date,
  claimpolicy number,
  damageReport varchar2(4000) /* XML */,
  constraint insurance_claim_pk primary key (claimid),
  constraint policy_fk foreign key (claimpolicy) references policy
);

insert into insurance_claim values (77804, TO_DATE('01-01-99','DD-MM-RR'),
8895,
'The insured''s <VEHICLE>car</VEHICLE> broke through the guard rail and '||
'plummeted into a ravine. The cause was determined to be <CAUSE>faulty '||
'brakes</CAUSE> Amazingly there were no casualties.');

insert into insurance_claim values (12345, TO_DATE('11-03-98','DD-MM-RR'),
8895,
'Officer George Lumden clocked the insured going 98 miles per hour in a '||
'45 zone. He wrote up the offender for an <CAUSE>excess speed</CAUSE> '||
'violation. Insured claims damage was done before he exceeded the speed '||
'limit.');

insert into insurance_claim values (90998, TO_DATE('18-06-97','DD-MM-RR'),
9054,
'San Francisco Fire Department arrived on scene at 9:08pm in response '||
'to an anonymous call. Fire went to four alarms. Fire Chief Rodriguez '||
'immediately suspected <CAUSE>arson</CAUSE> based on clues left at the '||
'scene.');

insert into insurance_claim values (11876, TO_DATE('20-04-98','DD-MM-RR'),
9054, 
'<SUSPECT>Colonel Mustard</SUSPECT> underwent DNA testing and was '||
'confirmed as the killer. The insured''s wife was '||
'<CAUSE>murdered</CAUSE> with a candlestick in the library.');

create table settlement_payments(
 claimid number,
 PayDate DATE, 
 Amount NUMBER, 
 Approver VARCHAR2(8),
 constraint claim_fk foreign key (claimid) references insurance_claim
);
insert into settlement_payments values (77804,TO_DATE('05-01-99','DD-MM-RR'),
7600,'JCOX');
insert into settlement_payments values (12345,TO_DATE('15-03-98','DD-MM-RR'),
1800,'MFOX');
insert into settlement_payments values (12345,TO_DATE('23-03-98','DD-MM-RR'),
7800,'ULOWE');
insert into settlement_payments values (90998,TO_DATE('01-07-97','DD-MM-RR'),
1500,'JCOX');
insert into settlement_payments values (11876,TO_DATE('05-01-99','DD-MM-RR'),
7600,'JCOX');

create or replace force view insurance_claim_view of insurance_claim_t
        with object OID (claimid)
        as select c.claimid,c.filed,
           (SELECT value(pv)
             from policy_view pv
            WHERE pv.policyid = c.claimpolicy),
                CAST(MULTISET(SELECT PAYMENT(sp.paydate,sp.amount,sp.approver) as Payment
                                from settlement_payments sp
                                WHERE sp.claimid = c.claimid) AS settlements_t),
           c.damagereport
        from insurance_claim c;

commit;

create index ctx_xml_i on insurance_claim(damagereport)
indextype is ctxsys.context
parameters('section group ctxsys.auto_section_group');

create synonym claim for insurance_claim_view;
