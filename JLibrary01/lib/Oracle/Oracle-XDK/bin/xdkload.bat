@echo off
Rem 
Rem  xdkload.bat
Rem 
Rem Copyright (c) 1999, 2003, Oracle Corporation.  All rights reserved.  
Rem 
Rem     NAME
Rem       xsuload.bat
Rem 
Rem     DESCRIPTION
Rem       Loads the XDK into Oracle JServer.
Rem 

call env.bat

set XDKLOAD_FLAG=
set USER_PASSWORD=0
set help=0
set pubsyn=0

for %%i in (%*)  do call:parse %%i
goto :finish

:parse
Rem echo "--- In parse ---:  " %1
 if %help%==1 goto :end
 if %1==-h goto :help
 if %1==-help goto :help
 if %1 == -u goto :getuser %1
 if %USER_PASSWORD%==1 goto :getuser %1
 goto :getflags %1

:getuser
Rem echo "--- In getuser --- :  " %1
 if %USER_PASSWORD%==1 set USER_PASSWORD=%1
 if %1 == -u set USER_PASSWORD=1
 goto :end

:getflags
Rem echo "--- In getflags ---"
 if %1 == -s set pubsyn=1
 set XDKLOAD_FLAG=-s
 goto :end


:help
echo. 
echo ------------------------------------------------------------------------
echo Usage:  "xdkload -u <username/password> [-s]"  
echo.
echo -s         Creates public synonyms for the loaded packages; this can be 
echo            invoked only if the target user has dba privileges.          
echo.
echo Ex.:   "xdkload -u system/manager -s"
echo ------------------------------------------------------------------------
echo. 
set help=1
goto :end

:finish
if %help%==1 goto :end
if %USER_PASSWORD%==0 goto :help
if %USER_PASSWORD%==1 goto :help
echo on

Rem =============================
Rem Load the classes
@echo -----------------------------------------------------------------------
@echo "Loading the XDK libraries"
@echo.
if exist %INSTALL_ROOT%\lib\xmlplsql.jar call loadjava -r -v -g public -u %USER_PASSWORD% %XDKLOAD_FLAG% %INSTALL_ROOT%\lib\xmlparserv2.jar %INSTALL_ROOT%\lib\xmlplsql.jar %INSTALL_ROOT%\lib\xsu12.jar %INSTALL_ROOT%\lib\xdb.jar

if not exist %INSTALL_ROOT%\lib\xmlplsql.jar call loadjava -r -v -g public -u %USER_PASSWORD% %XDKLOAD_FLAG% %INSTALL_ROOT%\lib\xmlparserv2.jar %INSTALL_ROOT%\lib\xsu12.jar %INSTALL_ROOT%\lib\xdb.jar

call dropjava  -v -u %USER_PASSWORD% OracleXML

Rem =============================
Rem load the PL/SQL packages
@echo -----------------------------------------------------------------------
@echo "Loading PL/SQL packages..."
@echo.
call sqlplus %USER_PASSWORD% @%INSTALL_ROOT%\xdk\admin\xsupkg.sql
@echo.

if %pubsyn%==1 call sqlplus %USER_PASSWORD% @%INSTALL_ROOT%\xdk\admin\xsusyn.sql

if not exist %INSTALL_ROOT%\xdk\admin\xmlpkg.sql goto :testing

@echo.
call sqlplus %USER_PASSWORD% @%INSTALL_ROOT%\xdk\admin\xmlpkg.sql
@echo.

if %pubsyn%==1 call sqlplus %USER_PASSWORD% @%INSTALL_ROOT%\xdk\admin\xmlsyn.sql

:testing
Rem =============================
Rem Testing
echo "-----------------------------------------------------------------------"
echo "Testing..."
echo.
call sqlplus %USER_PASSWORD% @%INSTALL_ROOT%\xdk\admin\xsutest.sql
echo.
echo "Done.. "


@echo off
set XDKLOAD_FLAG=
set USER_PASSWORD=
set help=
set pubsyn=

:end
