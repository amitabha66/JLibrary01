:: ##########################################################################
:: # Batch script to build Oracle XML parser C sample programs
:: ##########################################################################
set opt_flg=-Ox -Oy-
if (%2) == (D) set opt_flg=-Z7 -Od
if (%2) == (D) set link_dbg=/debug /debugtype:both /pdb:none

if (%1) == () goto :DOMS
if (%1) == (all) goto :DOMS
if (%1) == (DOMSample) goto :DOMS
if (%1) == (DOMNamespace) goto :DOMN
if (%1) == (FullDOM) goto :FULL
if (%1) == (clean) goto :CLEAN
if (%1) == (sure) goto :SURE
goto :EOF

:CLEAN
del *.obj
del *.out
del ..\..\..\..\bin\DOMSample.exe
del ..\..\..\..\bin\DOMNamespace.exe
del ..\..\..\..\bin\FullDOM.exe
goto :EOF

:DOMS
call :compile DOMSample
call :link DOMSample
if (%1) == (DOMSample) goto :EOF

:DOMN
call :compile DOMNamespace
call :link DOMNamespace
if (%1) == (DOMNamespace) goto :EOF

:FULL
call :compile FullDOM
call :link FullDOM
if (%1) == (FullDOM) goto :EOF

:SURE
..\..\..\..\bin\DOMSample.exe > DOMSample.out
..\..\..\..\bin\DOMNamespace.exe > DOMNamespace.out
..\..\..\..\bin\FullDOM.exe > FullDOM.out
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
