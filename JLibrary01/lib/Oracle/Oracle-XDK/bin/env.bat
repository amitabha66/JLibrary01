@echo off
::  Copyright (c) Oracle Corporation 1999, 2000, 2001. All Rights Reserved.
::
::    NAME
::      env.bat
::
::    DESCRIPTION
::      environmental variables needed to use XDK (client side)
::

:: -------- XDK directory path	// MIGHT NEED UPDATE
:: You probably want to hard-code INSTALL_ROOT to point to the directory 
:: in which this file (env.csh) is located; on the other hand, if you are using
:: this script to setup the client env. to use XDK which was installed 
:: part of the database install, simply set the INSTALL_ROOT to $ORACLE_HOME
for /f %%a in ('cd') do set INSTALL_ROOT=%%a\..


:: ------- JDBC classes
:: If you are running the XSU on a system different then where
:: the Oracle RDBMS is installed, you will have to update CLASSPATHJ path with
:: the correct locations of the jdbc library (classes12.jar or ojdbc14.jar)
:: and the gss bundle (orai18n.jar -- needed to support certain charactersets)
:: Note that if you don't have these libraries on your system, these are both
:: available on OTN (http://otn.oracle.com) -- part of jdbc driver download

set CLASSPATHJ=%ORACLE_HOME%\jdbc\lib\ojdbc14.jar;%ORACLE_HOME%\jlib\orai18n.jar

:: ------- PATH settings
set PATH=%JAVA_HOME%\bin;%ORACLE_HOME%\bin;%PATH%;%INSTALL_ROOT%\bin

:: ------- CLASSPATH settings
set CLASSPATH=.;%CLASSPATHJ%;%INSTALL_ROOT%\lib\xmlparserv2.jar;%INSTALL_ROOT%\lib\xml.jar;%INSTALL_ROOT%\lib\xmlmesg.jar;%INSTALL_ROOT%\lib\xsu12.jar;%INSTALL_ROOT%\lib\oraclexsql.jar
