:: ##########################################################################
:: # Batch script to build Oracle XML parser C sample programs
:: ##########################################################################
set opt_flg=-Ox -Oy-
if (%2) == (D) set opt_flg=-Z7 -Od
if (%2) == (D) set link_dbg=/debug /debugtype:both /pdb:none

if (%1) == () goto :SAXS
if (%1) == (all) goto :SAXS
if (%1) == (SAXSample) goto :SAXS
if (%1) == (SAXNamespace) goto :SAXN
if (%1) == (clean) goto :CLEAN
if (%1) == (sure) goto :SURE
goto :EOF

:CLEAN
del *.obj
del *.out
del ..\..\..\..\bin\SAXSample.exe
del ..\..\..\..\bin\SAXNamespace.exe
goto :EOF

:SAXS
call :compile SAXSample
call :link SAXSample 
if (%1) == (SAXSample) goto :EOF

:SAXN
call :compile SAXNamespace
call :link SAXNamespace
if (%1) == (SAXNamespace) goto :EOF

:SURE
..\..\..\..\bin\SAXSample.exe > SAXSample.out
..\..\..\..\bin\SAXNamespace.exe > SAXNamespace.out
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
