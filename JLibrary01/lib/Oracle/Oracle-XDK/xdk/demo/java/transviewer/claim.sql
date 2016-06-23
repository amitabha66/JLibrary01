set scan off
set echo on
set termout on

drop synonym s_claim;

drop table s_settlement_payments;

drop view s_insurance_claim_view;

drop table s_insurance_claim;

drop view s_policy_view;

drop table s_policy;

drop view s_policyholder_view;

drop table s_policyholder;

drop type s_insurance_claim_t;

drop type s_settlements_t;

drop type s_payment;

drop type s_policy_t;

drop type s_policyholder_t;

drop type s_address_t;

drop table xslfiles;

drop table htmlfiles;

commit;

create type s_address_t as object( Street varchar2(80), City Varchar2(80), State VARCHAR2(80),
Zip NUMBER );
.
/
create table htmlfiles  (filename char (16) unique, filedata clob)
    lob(filedata) store as (disable storage in row);
.
/
create type s_policyholder_t as object( CustomerId number,
            FirstName varchar2(80),
				    LastName varchar2(80),
            Phone varchar2(80),
            HomeAddress s_address_t);
.
/
create type s_policy_t as object(
 policyID number,
 primaryinsured s_policyholder_t);
.
/
create type s_payment as object(
 PayDate DATE,
 Amount NUMBER,
 Approver VARCHAR2(8));
.
/
create type s_settlements_t as table of s_payment;
.
/
create type s_insurance_claim_t as object (
  claimid number,
  filed   date,
  claimpolicy s_policy_t,
  settlements s_settlements_t,
  damageReport varchar2(4000)
);
.
/
create table s_policyholder( CustomerId number,
         FirstName varchar2(80),
			   LastName varchar2(80),
         Phone varchar2(80),
         HomeAddress s_address_t,
  constraint s_policyholder_pk primary key (customerid) );

insert into s_policyholder values ( 1044, 'Paul','Astoria','650-111-1111',
    S_ADDRESS_T('123 Cherry Lane','SF','CA','94132'));

insert into s_policyholder values ( 1045, 'Martina','Boyle','650-222-333',
    S_ADDRESS_T('55 Belden Place','SF','CA','94102'));

create or replace force view s_policyholder_view of s_policyholder_t
        with object OID (customerid)
        as select customerid, firstname, lastname, phone, homeaddress
        from s_policyholder;

create table s_policy(
 policyid number,
 primarycustomerid number,
 constraint s_policy_pk primary key (policyid),
 constraint s_customer_fk foreign key (primarycustomerid) references s_policyholder
 );

insert into s_policy values ( 8895, 1044 );
insert into s_policy values ( 9054, 1045 );

create or replace force view s_policy_view of s_policy_t
        with object OID (policyid)
        as select p.policyid,
           (SELECT value(phv)
             from s_policyholder_view phv
            WHERE phv.customerid = p.primarycustomerid) as primaryinsured
        from s_policy p;

create table s_insurance_claim(
  claimid number,
  filed   date,
  claimpolicy number,
  damageReport varchar2(4000) /* XML */,
  constraint s_insurance_claim_pk primary key (claimid),
  constraint s_policy_fk foreign key (claimpolicy) references s_policy
);

insert into s_insurance_claim values (77804, '01-JAN-99',8895,
'The insured''s car broke through the guard rail and '||
'plummeted into a ravine. The cause was determined to be faulty '||
'brakes Amazingly there were no casualties.');

insert into s_insurance_claim values (12345, '11-MAR-98',8895,
'Officer George Lumden clocked the insured going 98 miles per hour in a '||
'45 zone. He wrote up the offender for an excess speed '||
'violation. Insured claims damage was done before he exceeded the speed '||
'limit.');

insert into s_insurance_claim values (90998, '18-JUN-97',9054,
'San Francisco Fire Department arrived on scene at 9:08pm in response '||
'to an anonymous call. Fire went to four alarms. Fire Chief Rodriguez '||
'immediately suspected arson based on clues left at the '||
'scene.');

insert into s_insurance_claim values (11876, '20-APR-98',9054,
'Colonel Mustard underwent DNA testing and was '||
'confirmed as the killer. The insured''s wife was '||
'murdered with a candlestick in the library.');

create table s_settlement_payments(
 claimid number,
 PayDate DATE,
 Amount NUMBER,
 Approver VARCHAR2(8),
 constraint s_claim_fk foreign key (claimid) references s_insurance_claim
);
insert into s_settlement_payments values (77804,'05-JAN-99',7600,'JCOX');
insert into s_settlement_payments values (12345,'15-MAR-98',1800,'MFOX');
insert into s_settlement_payments values (12345,'23-MAR-98',7800,'ULOWE');
insert into s_settlement_payments values (90998,'01-JUL-97',1500,'JCOX');
insert into s_settlement_payments values (11876,'05-JAN-99',7600,'JCOX');

create or replace force view s_insurance_claim_view of s_insurance_claim_t
        with object OID (claimid)
        as select c.claimid,c.filed,
           (SELECT value(pv)
             from s_policy_view pv
            WHERE pv.policyid = c.claimpolicy),
                CAST(MULTISET(SELECT S_PAYMENT(sp.paydate,sp.amount,sp.approver) as Payment
                                from s_settlement_payments sp
                                WHERE sp.claimid = c.claimid) AS s_settlements_t),
           c.damagereport
        from s_insurance_claim c;

create table xslfiles  (filename char (16) unique, filedata clob)
    lob(filedata) store as (disable storage in row);

insert into xslfiles values ('CLAIM.XSL',
'<?xml version = "1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   <xsl:output method="html" indent="yes"/>
   <xsl:template match="/">
      <html>
         <head>
            <title>Claim
            </title>
         </head>
         <body background="d:\demo\bg.gif">
            <xsl:apply-templates select="//row"/>
         </body>
      </html>
   </xsl:template>
   <xsl:template match="damagereport">
      <big>
         <font color="#ff0000">
            <p>Damage Report
            </p>
         </font>
      </big>
      <table border="1">
         <td width="100%">
            <xsl:value-of select="../damagereport"/>
         </td>
      </table>
   </xsl:template>
   <xsl:template match="homeaddress|address">
      <table border="1" cellspacing="0" cellpadding="10">
         <tr>
            <td width="100%" bgcolor="#FFFFFF">
               <xsl:value-of select="../firstname"/>
               <xsl:value-of select="../lastname"/>
               <br>
                  <xsl:value-of select="street"/>
               </br>
               <br>
                  <xsl:value-of select="city"/>
               </br>
               <xsl:text>,
               </xsl:text>
               <xsl:value-of select="state"/>
               <xsl:text>,
               </xsl:text>
               <xsl:value-of select="zip"/>
            </td>
         </tr>
      </table>
   </xsl:template>
   <xsl:template match="claimpolicy">
      <br>
         <xsl:apply-templates select="primaryinsured"/>
      </br>
   </xsl:template>
   <xsl:template match="primaryinsured|customer">
      <xsl:apply-templates select="homeaddress"/>
   </xsl:template>
   <xsl:template match="settlements">
      <table border="1" width="100%" cellpadding="5" cellspacing="0">
         <tr>
            <td width="33%" bgcolor="#400080">
               <strong>
                  <font color="#FFFFFF" face="Arial">Payment Date
                  </font>
               </strong>
            </td>
            <td align="right" width="33%" bgcolor="#400080">
               <strong>
                  <font color="#FFFFFF" face="Arial">Amount
                  </font>
               </strong>
            </td>
            <td width="34%" bgcolor="#400080">
               <strong>
                  <font color="#FFFFFF" face="Arial">Approved By
                  </font>
               </strong>
            </td>
         </tr>
         <xsl:for-each select="settlements_item">
            <tr style="background-color:white">
               <td bgcolor="white" width="33%">
                  <xsl:value-of select="paydate"/>
               </td>
               <td bgcolor="white" align="right" width="33%">$
                  <xsl:value-of select="amount"/>.00
               </td>
               <td bgcolor="white" width="34%">
                  <xsl:value-of select="approver"/>
               </td>
            </tr>
         </xsl:for-each>
      </table>
   </xsl:template>
   <xsl:template match="*"/>
   <xsl:template match="row">
      <table border="0" width="100%">
         <tr>
            <td width="40%">
               <big>
                  <big>
                     <font face="Arial">Oracle Insurance
                     </font>
                  </big>
               </big>
            </td>
            <td width="12%">
               <big>
                  <font face="Arial">Claim#
                     <xsl:value-of select="claimpolicy/policyid"/>
                  </font>
               </big>
            </td>
         </tr>
         <tr>
            <td colspan="3">
               <xsl:apply-templates/>
            </td>
         </tr>
      </table>
   </xsl:template>
</xsl:stylesheet>
');


commit;

create synonym s_claim for s_insurance_claim_view;
exit
/
