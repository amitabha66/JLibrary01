:: ##########################################################################
:: # Batch script to build Oracle XML parser C sample programs
:: ##########################################################################
set opt_flg=-Ox -Oy-
if (%2) == (D) set opt_flg=-Z7 -Od
if (%2) == (D) set link_dbg=/debug /debugtype:both /pdb:none

if (%1) == () goto :XSDTEST
if (%1) == (all) goto :XSDTEST
if (%1) == (xsdtest) goto :XSDTEST
if (%1) == (clean) goto :CLEAN
if (%1) == (sure) goto :SURE
goto :EOF

:CLEAN
del *.obj
del *.out
del ..\..\..\..\bin\xsdtest.exe
goto :EOF

:XSDTEST
call :compile xsdtest
call :link xsdtest
if (%1) == (xsdtest) goto :EOF

:SURE
..\..\..\..\bin\xsdtest.exe car.xml > car.out
comp car.std car.out < NUL:
..\..\..\..\bin\xsdtest.exe pub.xml > pub.out
comp pub.std pub.out < NUL:
..\..\..\..\bin\xsdtest.exe aq.xml > aq.out
comp aq.std aq.out < NUL:
goto :EOF

:COMPILE
set filename=%1
cl -c -Fo%filename%.obj %opt_flg%  /DCRTAPI1=_cdecl /DCRTAPI2=_cdecl /nologo /Zl /Gy /DWIN32 /D_WIN32 /DWIN_NT /DWIN32COMMON /D_DLL /D_MT /D_X86_=1 -I. -I..\..\..\include  %filename%.c
goto :EOF

:LINK 
set filename=%1
link %link_dbg% /out:..\..\..\..\bin\%filename%.exe /libpath:%ORACLE_HOME%\lib /libpath:..\..\..\..\lib  %filename%.obj oraxml10.lib user32.lib kernel32.lib msvcrt.lib ADVAPI32.lib oldnames.lib winmm.lib

:EOF
