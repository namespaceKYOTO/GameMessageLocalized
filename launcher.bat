@echo off
setlocal

rem set local environment path
set JAVAC_PATH=C:\"Program Files"\Java\jdk1.7.0_45\bin\
set JAVA_PATH=C:\"Program Files"\Java\jdk1.7.0_45\bin\
rem relative path to the c drive from javac path
set C_DRIVE_RELATIVE_PATH=.\..\..\..\..

rem set Root path
set ROOT_PATH=%CD%

rem relative path to the root path from javac path
set ROOT_RELATIVE_PATH=%C_DRIVE_RELATIVE_PATH%%~p0


rem launch
cd .\bat
cmd /K type launch_mem.txt

endlocal
