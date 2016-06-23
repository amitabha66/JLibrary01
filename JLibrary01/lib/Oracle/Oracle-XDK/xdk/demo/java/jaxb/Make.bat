# Batch file for sample java files
#
# If not installed in ORACLE_HOME, set ORACLE_HOME to installation root
# Set JAVA_HOME to Java install home
#
# ======================================================================

set MAKE_CLASSPATH=.;%ORACLE_HOME%\lib\xmlparserv2.jar;%ORACLE_HOME%\lib\xml.jar;%CLASSPATH%

if (%1) == () goto :DEMO
if (%1) == (all) goto :DEMO
if (%1) == (demo) goto :DEMO
if (%1) == (clean) goto :CLEAN
if (%1) == (sample1) goto :SAMPLE1
if (%1) == (sample2) goto :SAMPLE2
if (%1) == (sample3) goto :SAMPLE3
if (%1) == (sample4) goto :SAMPLE4
if (%1) == (sample5) goto :SAMPLE5
if (%1) == (sample6) goto :SAMPLE6
if (%1) == (sample7) goto :SAMPLE7
if (%1) == (sample8) goto :SAMPLE8
goto :EOF

:DEMO

:SAMPLE1
cd .\Sample1 &  java -classpath %MAKE_CLASSPATH% oracle.xml.jaxb.orajaxb -schema sample1.xsd -targetPkg generated
cd .\generated & javac -classpath %MAKE_CLASSPATH% *.java 
cd ..\.
javac -classpath %MAKE_CLASSPATH% SampleApp1.java
java -classpath %MAKE_CLASSPATH% SampleApp1 > sample1.out
cd ..\.
if (%1) == (sample1) goto :EOF

:SAMPLE2
cd .\Sample2 &  java -classpath %MAKE_CLASSPATH% oracle.xml.jaxb.orajaxb -schema sample2.xsd -targetPkg generated
cd .\generated & javac -classpath %MAKE_CLASSPATH% *.java 
cd ..\.
javac -classpath %MAKE_CLASSPATH% SampleApp2.java
java -classpath %MAKE_CLASSPATH% SampleApp2 > sample2.out
cd ..\.
if (%1) == (sample2) goto :EOF

:SAMPLE3
cd .\Sample3 &  java -classpath %MAKE_CLASSPATH% oracle.xml.jaxb.orajaxb -schema sample3.xsd -targetPkg generated
cd .\generated & javac -classpath %MAKE_CLASSPATH% *.java 
cd ..\.
javac -classpath %MAKE_CLASSPATH% SampleApp3.java
java -classpath %MAKE_CLASSPATH% SampleApp3 > sample3.out
cd ..\.
if (%1) == (sample3) goto :EOF

:SAMPLE4
cd .\Sample4 &  java -classpath %MAKE_CLASSPATH% oracle.xml.jaxb.orajaxb -schema sample4.xsd -targetPkg generated
cd .\generated & javac -classpath %MAKE_CLASSPATH% *.java 
cd ..\.
javac -classpath %MAKE_CLASSPATH% SampleApp4.java
java -classpath %MAKE_CLASSPATH% SampleApp4 > sample4.out
cd ..\.
if (%1) == (sample4) goto :EOF

:SAMPLE5
cd .\Sample5 &  java -classpath %MAKE_CLASSPATH% oracle.xml.jaxb.orajaxb -schema sample5.xsd -targetPkg generated
cd .\generated & javac -classpath %MAKE_CLASSPATH% *.java 
cd ..\.
javac -classpath %MAKE_CLASSPATH% SampleApp5.java
java -classpath %MAKE_CLASSPATH% SampleApp5 > sample5.out
cd ..\.
if (%1) == (sample5) goto :EOF

:SAMPLE6
cd .\Sample6 &  java -classpath %MAKE_CLASSPATH% oracle.xml.jaxb.orajaxb -schema sample6.xsd -targetPkg generated
cd .\generated & javac -classpath %MAKE_CLASSPATH% *.java 
cd ..\.
javac -classpath %MAKE_CLASSPATH% SampleApp6.java
java -classpath %MAKE_CLASSPATH% SampleApp6 > sample6.out
cd ..\.
if (%1) == (sample6) goto :EOF

:SAMPLE7
cd .\Sample7 &  java -classpath %MAKE_CLASSPATH% oracle.xml.jaxb.orajaxb -schema sample7.xsd -targetPkg generated
cd .\generated & javac -classpath %MAKE_CLASSPATH% *.java 
cd ..\.
javac -classpath %MAKE_CLASSPATH% SampleApp7.java
java -classpath %MAKE_CLASSPATH% SampleApp7 > sample7.out
cd ..\.
if (%1) == (sample7) goto :EOF

:SAMPLE8
cd .\Sample8 &  java -classpath %MAKE_CLASSPATH% oracle.xml.jaxb.orajaxb -schema sample8.xsd
javac -classpath %MAKE_CLASSPATH% .\generated\sample8\*.java .\generated\sample81\*.java 
javac -classpath %MAKE_CLASSPATH% SampleApp8.java
java -classpath %MAKE_CLASSPATH% SampleApp8 > sample8.out
cd ..\.
if (%1) == (sample8) goto :EOF

:CLEAN
cd .\Sample1 
del SampleApp1.class; sample1.out;  rmdir /s /q generated
cd ..\.

cd .\Sample2 
del SampleApp2.class; sample2.out;  rmdir /s /q generated
cd ..\.

cd .\Sample3 
del SampleApp3.class; sample3.out;  rmdir /s /q generated
cd ..\.

cd .\Sample4 
del SampleApp4.class; sample4.out;  rmdir /s /q generated
cd ..\.

cd .\Sample5 
del SampleApp5.class; sample5.out;  rmdir /s /q generated
cd ..\.

cd .\Sample6 
del SampleApp6.class; sample6.out;  rmdir /s /q generated
cd ..\.

cd .\Sample7 
del SampleApp7.class; sample7.out;  rmdir /s /q generated
cd ..\.

cd .\Sample8 
del SampleApp8.class; sample8.out;  rmdir /s /q generated
cd ..\.


:EOF

