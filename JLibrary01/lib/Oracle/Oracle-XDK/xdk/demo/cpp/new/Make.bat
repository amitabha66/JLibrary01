:: ##########################################################################
:: # Batch script to build Oracle XML parser C++ sample programs
:: ##########################################################################
set opt_flg=-Ox -Oy-
if (%2) == (D) set opt_flg=-Z7 -Od
if (%2) == (D) set link_dbg=/debug /debugtype:both /pdb:none
 
if (%1) == () goto :DOMS
if (%1) == (all) goto :DOMS
if (%1) == (DOMSample) goto :DOMS
if (%1) == (SAXSample) goto :SAX
if (%1) == (XSLSample) goto :XSL
if (%1) == (XVMSample) goto :XVM
if (%1) == (XslXPathSample) goto :XSLXP
if (%1) == (XvmXPathSample) goto :XVMXP
if (%1) == (SchSample) goto :SCH
if (%1) == (FullDOM) goto :FDOMS
if (%1) == (clean) goto :CLEAN
if (%1) == (sure) goto :SURE
goto :EOF
 

:CLEAN
del *.obj
del *.out
del ..\..\..\..\bin\DOMSample.exe
del ..\..\..\..\bin\SAXSample.exe
del ..\..\..\..\bin\XSLSample.exe
del ..\..\..\..\bin\XVMSample.exe
del ..\..\..\..\bin\XslXPathSample.exe
del ..\..\..\..\bin\XvmXPathSample.exe
del ..\..\..\..\bin\SchSample.exe
del ..\..\..\..\bin\FullDOM.exe
goto :EOF
 
:DOMS
call :compile DOMSampleForce
call :compile DOMSampleMain
call :link DOMSample DOMSampleForce DOMSampleMain
if (%1) == (DOMSample) goto :EOF

:SAX
call :compile SAXSampleForce
call :compile SAXSampleMain
call :link SAXSample SAXSampleForce SAXSampleMain
if (%1) == (SAXSample) goto :EOF
 
:XSL
call :compile XSLSampleForce
call :compile XSLSampleMain
call :link XSLSample XSLSampleForce XSLSampleMain
if (%1) == (XSLSample) goto :EOF
 
:XVM
call :compile XVMSampleForce
call :compile XVMSampleMain
call :link XVMSample XVMSampleForce XVMSampleMain
if (%1) == (XVMSample) goto :EOF
 
:XSLXP
call :compile XslXPathSampleForce
call :compile XslXPathSampleMain
call :link XslXPathSample XslXPathSampleForce XslXPathSampleMain
if (%1) == (XslXPathSample) goto :EOF
 
:XVMXP
call :compile XvmXPathSampleForce
call :compile XvmXPathSampleMain
call :link XvmXPathSample XvmXPathSampleForce XvmXPathSampleMain
if (%1) == (XvmXPathSample) goto :EOF

:SCH
call :compile SchSampleForce
call :compile SchSampleMain
call :link SchSample SchSampleForce SchSampleMain
if (%1) == (SchSample) goto :EOF

:FDOMS
call :compile FullDOMForce
call :compile FullDOMMain
call :link FullDOM FullDOMForce FullDOMMain
if (%1) == (FullDOM) goto :EOF
 
:SURE
..\..\..\..\bin\DOMSample.exe cleo.xml > DOMSample.out
..\..\..\..\bin\SAXSample.exe class.xml > SAXSample.out
..\..\..\..\bin\FullDOM.exe class.xml > FullDOM.out
..\..\..\..\bin\SchSample.exe car.xml car.xsd > SchSample.out
..\..\..\..\bin\XSLSample.exe iden.xsl class.xml > XSLSample.out
..\..\..\..\bin\XVMSample.exe iden.xsl class.xml > XVMSample.out
..\..\..\..\bin\XslXPathSample.exe pantry.xml //child::* > XslXPathSample.out
..\..\..\..\bin\XvmXPathSample.exe pantry.xml //child::* > XvmXPathSample.out
comp *.out *.std < NUL:
goto :EOF
 
:COMPILE
set filename=%1
cl -c -Fo%filename%.obj %opt_flg%  /DCRTAPI1=_cdecl /DCRTAPI2=_cdecl /nologo /Zl /Gy /DWIN32 /D_WIN32 /DWIN_NT /DWIN32COMMON /D_DLL /D_MT /D_X86_=1 -I. -I..\..\..\include  %filename%.cpp
goto :EOF
 
:LINK 
set filename=%1
set filename2=%2
set filename3=%3
link %link_dbg% /out:..\..\..\..\bin\%filename%.exe /libpath:%ORACLE_HOME%\lib /libpath:..\..\..\..\lib  %filename2%.obj %filename3%.obj oraxml10.lib user32.lib kernel32.lib msvcrt.lib ADVAPI32.lib oldnames.lib winmm.lib
 
:EOF
