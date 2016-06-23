# Batch file for sample java files
# ======================================================================

set MAKE_CLASSPATH=.;../common;%ORACLE_HOME%/lib/xmlparserv2.jar;%CLASSPATH%

if (%1) == () goto :DEMO
if (%1) == (all) goto :DEMO
if (%1) == (demo) goto :DEMO
if (%1) == (clean) goto :CLEAN
goto :EOF

:DEMO
javac -classpath %MAKE_CLASSPATH% *.java
goto :EOF

:CLEAN
del *.class

:EOF

