:: ##########################################################################
:: # Batch script to build Oracle XML C++ Class Generator sample
:: ##########################################################################

if (%1) == (clean) goto :CLEAN
if (%1) == (sure) goto :SURE

..\..\..\..\bin\xmlcg sample.xml
cl -c -Fosmain.obj /DCRTAPI1=_cdecl /DCRTAPI2=_cdecl /nologo /Zl /Gy /DWIN32 /D_WIN32 /DWIN_NT /DWIN32COMMON /D_DLL /D_MT /D_X86_=1 /DLUSEMFC -I. -I..\..\..\include smain.cpp
cl -c -Fosample.obj /DCRTAPI1=_cdecl /DCRTAPI2=_cdecl /nologo /Zl /Gy /DWIN32 /D_WIN32 /DWIN_NT /DWIN32COMMON /D_DLL /D_MT /D_X86_=1 /DLUSEMFC -I. -I..\..\..\include sample.cpp
link /out:..\..\..\..\bin\CG.exe /libpath:%ORACLE_HOME%\lib /libpath:..\..\..\..\lib smain.obj sample.obj oraxml10.lib user32.lib kernel32.lib ADVAPI32.lib oldnames.lib winmm.lib msvcrt.lib

..\..\..\..\bin\xmlcg -s queue queue.xsd
cl -c -Foqmain.obj /DCRTAPI1=_cdecl /DCRTAPI2=_cdecl /nologo /Zl /Gy /DWIN32 /D_WIN32 /DWIN_NT /DWIN32COMMON /D_DLL /D_MT /D_X86_=1 /DLUSEMFC -I. -I..\..\..\include qmain.cpp
cl -c -Foqueue.obj /DCRTAPI1=_cdecl /DCRTAPI2=_cdecl /nologo /Zl /Gy /DWIN32 /D_WIN32 /DWIN_NT /DWIN32COMMON /D_DLL /D_MT /D_X86_=1 /DLUSEMFC -I. -I..\..\..\include queue.cpp
link /out:..\..\..\..\bin\AQ.exe /libpath:%ORACLE_HOME%\lib /libpath:..\..\..\..\lib qmain.obj queue.obj oraxml10.lib user32.lib kernel32.lib ADVAPI32.lib oldnames.lib winmm.lib msvcrt.lib
goto :EOF

:SURE
..\..\..\..\bin\CG
..\..\..\..\bin\AQ
comp *.out *.std < NUL:
goto :EOF

:CLEAN
del sample.cpp
del sample.h
del sample.out
del queue.cpp
del queue.h
del queue.out
del *.obj
del ..\..\..\..\bin\CG.exe
del ..\..\..\..\bin\AQ.exe

:EOF
