@echo off
Rem
Rem
Rem  xdkdrop.bat
Rem
Rem Copyright (c) 2003, Oracle Corporation.  All rights reserved.  
Rem
Rem     NAME
Rem       xdkdrop.bat
Rem
Rem     DESCRIPTION
Rem       drops the XDK from Oracle JServer.
Rem

call env.bat

set help=0
set pubsyn=0
set XDKDROP_FLAG=
set USER_PASSWORD=0

for %%i in (%*)  do call:parse %%i
goto :finish

:parse
Rem echo "--- In parse ---:  " %1
 if %help% == 1 goto :end
 if %1 == -h goto :help
 if %1 == -help goto :help
 if %1 == -u goto :getuser %1
 if %USER_PASSWORD% == 1 goto :getuser %1
 goto :getflags %1

:getuser
Rem echo "--- In getuser --- :  " %1
 if %USER_PASSWORD% == 1 set USER_PASSWORD=%1
 if %1 == -u set USER_PASSWORD=1
 goto :end

:getflags
Rem echo "--- In getflags ---"
 if %1 == -s set XDKDROP_FLAG=-s
 set pubsyn=1
 goto :end

:help
echo. 
echo ------------------------------------------------------------------------
echo Usage:  "xdkdrop -u <username/password> [-s]" 
echo.
echo -s         Drops public synonyms for the packages being dropped; this
echo            can be invoked only if the target user has dba privileges. 
echo.
echo Ex.:   "xdkdrop -u system/manager -s"
echo ------------------------------------------------------------------------
echo. 
set help=1
goto :end

:finish
if %help% == 1 goto :end
if %USER_PASSWORD% == 0 goto :help
if %USER_PASSWORD% == 1 goto :help
echo on

Rem =============================
Rem Drop the classes
@echo -----------------------------------------------------------------------
@echo "Droping the XDK libraries"
@echo.

call dropjava -r -v -u %USER_PASSWORD% %XDKDROP_FLAG% %INSTALL_ROOT%\lib\xmlparserv2.jar %INSTALL_ROOT%\lib\xsu12.jar %INSTALL_ROOT%\lib\xdb.jar

Rem =============================
Rem drop the PL/SQL packages
@echo -----------------------------------------------------------------------
@echo "Droping PL/SQL packages..."
@echo.
call sqlplus %USER_PASSWORD% @%INSTALL_ROOT%\xdk\admin\xsudrppkg.sql
@echo.

if %pubsyn% == 1 call sqlplus %USER_PASSWORD% @%INSTALL_ROOT%\xdk\admin\xsudrpsyn.sql

@echo off
set help=
set pubsyn=
set XDKDROP_FLAG=
set USER_PASSWORD=

:end
