# Batch file for sample java files
# ======================================================================

set MAKE_CLASSPATH=.;%ORACLE_HOME%/lib/xmlparserv2.jar;%ORACLE_HOME%/lib/xmlcomp.jar;%ORACLE_HOME%/lib/jdev-rt.zip;%ORACLE_HOME%/lib/xsu12.jar;%ORACLE_HOME%/lib/xsu12.jar;%ORACLE_HOME%/lib/xmlcomp2.jar;%ORACLE_HOME%/lib/xdb.jar;%ORACLE_HOME%/rdbms/jlib/xdb.jar;%ORACLE_HOME%/lib/xmldemo.jar;%CLASSPATH%

# Set the database information as required. Change userid & password if your 
# database doesn't have the default scott/tiger account.
set HOSTNAME=localhost
set PORT=1521
set SID=orcl
set USERID=scott
set PASSWORD=tiger

if (%1) == () goto :DEMO
if (%1) == (all) goto :DEMO
if (%1) == (demo) goto :DEMO
if (%1) == (sample1) goto :SMP1
if (%1) == (sample2) goto :SMP2
if (%1) == (sample3) goto :SMP3
if (%1) == (sample4) goto :SMP4
if (%1) == (sample5) goto :SMP5
if (%1) == (sample6) goto :SMP6
if (%1) == (sample7) goto :SMP7
if (%1) == (sample8) goto :SMP8
if (%1) == (sample9) goto :SMP9
if (%1) == (sample10) goto :SMP10
if (%1) == (clean) goto :CLEAN
goto :EOF

:DEMO
javac -classpath %MAKE_CLASSPATH% *.java
goto :EOF

:SMP1
javac -classpath %MAKE_CLASSPATH% XMLTransformPanelSample.java
java -classpath %MAKE_CLASSPATH% XMLTransformPanelSample
goto :EOF

:SMP2
javac -classpath %MAKE_CLASSPATH% ViewSample.java
java -classpath %MAKE_CLASSPATH% ViewSample
goto :EOF

:SMP3
javac -classpath %MAKE_CLASSPATH% AsyncTransformSample.java
java -classpath %MAKE_CLASSPATH% AsyncTransformSample
goto :EOF

:SMP4
sqlplus %USERID%/%PASSWORD% @claim.sql
javac -classpath %MAKE_CLASSPATH% DBViewSample.java
java -classpath %MAKE_CLASSPATH% DBViewSample
goto :EOF

:SMP5
javac -classpath %MAKE_CLASSPATH% XMLDBAccessSample.java
java -classpath %MAKE_CLASSPATH% XMLDBAccessSample %HOSTNAME% %PORT% %SID% %USERID% %PASSWORD%
goto :EOF

:SMP6
javac -classpath %MAKE_CLASSPATH% XMLDiffSample.java
java -classpath %MAKE_CLASSPATH% XMLDiffSample
goto :EOF

:SMP7
javac -classpath %MAKE_CLASSPATH% compstreamdata.java
javac -classpath %MAKE_CLASSPATH% filepanel.java
javac -classpath %MAKE_CLASSPATH% dbpanel.java
javac -classpath %MAKE_CLASSPATH% xmlcompressutil.java
java -classpath %MAKE_CLASSPATH% compviewer
goto :EOF

:SMP8
javac -classpath %MAKE_CLASSPATH% TreeViewerValidate.java 
javac -classpath %MAKE_CLASSPATH% XMLSchemaTreeViewer.java
java -classpath %MAKE_CLASSPATH% XMLSchemaTreeViewer
goto :EOF

:SMP9
javac -classpath %MAKE_CLASSPATH% XMLSrcViewPanel.java
javac -classpath %MAKE_CLASSPATH% XMLSrcViewer.java
java -classpath %MAKE_CLASSPATH% XMLSrcViewer
goto :EOF

:SMP10
javac -classpath %MAKE_CLASSPATH% XSDValidatorSample.java
java -classpath %MAKE_CLASSPATH% XSDValidatorSample  purchaseorder.xml purchaseorder.xsd
goto :EOF

:CLEAN
del *.class

:EOF

