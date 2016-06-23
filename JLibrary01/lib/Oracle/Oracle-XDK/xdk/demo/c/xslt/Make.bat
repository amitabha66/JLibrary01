:: ##########################################################################
:: # Batch script to build Oracle XML parser C sample programs
:: ##########################################################################
set opt_flg=-Ox -Oy-
if (%2) == (D) set opt_flg=-Z7 -Od
if (%2) == (D) set link_dbg=/debug /debugtype:both /pdb:none

if (%1) == () goto :XSL
if (%1) == (all) goto :XSL
if (%1) == (XSLSample) goto :XSL
if (%1) == (XSLXPathSample) goto :XSLXPath
if (%1) == (clean) goto :CLEAN
if (%1) == (sure) goto :SURE
goto :EOF

:CLEAN
del *.obj
del *.out
del ..\..\..\..\bin\XSLSample.exe
del ..\..\..\..\bin\XSLXPathSample.exe
goto :EOF

:XSL
call :compile XSLSample
call :link XSLSample
if (%1) == (XSLSample) goto :EOF

:XSLXPath
call :compile XSLXPathSample
call :link XSLXPathSample
if (%1) == (XSLXPathSample) goto :EOF

:SURE
..\..\..\..\bin\XSLSample.exe class.xml iden.xsl > XSLSample.out
..\..\..\..\bin\XSLXPathSample.exe class.xml "/course/*" > XSLXPathSample.out
comp *.out *.std < NUL:
goto :EOF

:COMPILE
set filename=%1
cl -c -Fo%filename%.obj %opt_flg%  /DCRTAPI1=_cdecl /DCRTAPI2=_cdecl /nologo /Zl /Gy /DWIN32 /D_WIN32 /DWIN_NT /DWIN32COMMON /D_DLL /D_MT /D_X86_=1 /Doratext=OraText -I. -I..\..\..\include  %filename%.c
goto :EOF

:LINK 
set filename=%1
link %link_dbg% /out:..\..\..\..\bin\%filename%.exe /libpath:%ORACLE_HOME%\lib /libpath:..\..\..\..\lib  %filename%.obj oraxml10.lib user32.lib kernel32.lib msvcrt.lib ADVAPI32.lib oldnames.lib winmm.lib

:EOF
