# Makefile for sample java files
#
# If not installed in ORACLE_HOME, set ORACLE_HOME to installation root
#
# ======================================================================

.SUFFIXES : .java .class

CLASSES = ViewSample.class  AsyncTransformSample.class XMLTransformPanelSample.class DBViewClaims.class DBViewFrame.class DBViewSample.class XMLDBAccessSample.class XMLDiffSample.class filepanel.class dbpanel.class compstreamdata.class xmlcompressutil.class compviewer.class TreeViewerValidate.class XMLSchemaTreeViewer.class  XMLSrcViewer.class XMLSrcViewPanel.class XSDValidatorSample.class

# Change it to the appropriate separator based on the OS 
PATHSEP = :

# Make sure that the following product jar/zip files are in the classpath:
# - Oracle JDBC driver for thin client (file classes12.zip)

# You can download this products from otn.oracle.com

#These parameters should be changed depending on you database and user information.
# These are used to run claim.sql using sqlplus (sample4) or to run sample5.
HOSTNAME=localhost
PORT=1521
SID=orcl
USERID=scott
PASSWORD=tiger


# By default the Makefile classpath points to xsu12.jar for JDK1.2.x.
#
# If you don't have an ORACLE_HOME set, set it to the root dir of your 
# XDK for Java Beans installation
#
# Running the xml differ demo using 'make sample6' might give a null 
# pointer exception if java runtime is jdk1.2.  It should work correctly 
# for jdk1.2_2*, jdk1.3*
#
MAKE_CLASSPATH = .$(PATHSEP)$(ORACLE_HOME)/lib/xmlparserv2.jar$(PATHSEP)$(ORACLE_HOME)/lib/xmlcomp.jar$(PATHSEP)$(ORACLE_HOME)/lib/jdev-rt.zip$(PATHSEP)$(ORACLE_HOME)/lib/xmlcomp2.jar$(PATHSEP)$(ORACLE_HOME)/lib/xsu12.jar$(PATHSEP)$(CLASSPATH)$(PATHSEP)$(ORACLE_HOME)/lib/xdb.jar$(PATHSEP)$(ORACLE_HOME)/rdbms/jlib/xdb.jar$(PATHSEP)$(ORACLE_HOME)/lib/xmldemo.jar

.java.class:
	javac -classpath "$(MAKE_CLASSPATH)" $<

# make all class files
all: $(CLASSES)


sample1: XMLTransformPanelSample.class
	java -classpath "$(MAKE_CLASSPATH)" XMLTransformPanelSample
sample2: ViewSample.class
	java -classpath "$(MAKE_CLASSPATH)" ViewSample
sample3: AsyncTransformSample.class
	java -classpath "$(MAKE_CLASSPATH)" AsyncTransformSample
sample4: DBViewSample.class
	sqlplus $(USERID)/$(PASSWORD) @claim.sql
	java -classpath "$(MAKE_CLASSPATH)" DBViewSample
sample5: XMLDBAccessSample.class
	java -classpath "$(MAKE_CLASSPATH)" XMLDBAccessSample ${HOSTNAME} ${PORT} ${SID} ${USERID} ${PASSWORD}
sample6: XMLDiffSample.class
	java -mx50m -classpath "$(MAKE_CLASSPATH)" XMLDiffSample XMLDiffData1.txt XMLDiffData2.txt
sample7:compstreamdata.class dbpanel.class filepanel.class xmlcompressutil.class compviewer.class
	java -classpath "$(MAKE_CLASSPATH)" compviewer
sample8:TreeViewerValidate.class XMLSchemaTreeViewer.class
	java -classpath "$(MAKE_CLASSPATH)" XMLSchemaTreeViewer
sample9: XMLSrcViewer.class XMLSrcViewPanel.class
	java -classpath "$(MAKE_CLASSPATH)" XMLSrcViewer
sample10: XSDValidatorSample.class
	java -classpath "$(MAKE_CLASSPATH)" XSDValidatorSample purchaseorder.xml purchaseorder.xsd



        #end of makefile

