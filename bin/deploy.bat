@echo off
set APP=Hugb_TicTacToe
set LOCATION=C:\temp

REM Always deploy new version
call bin\package.bat

REM If app folder exists under C:\temp, remove it
if exist %LOCATION%\\%APP% rmdir /S /Q %LOCATION%\\%APP%

REM If destionation folder doesn't exist, create it
if not exist "%LOCATION%" mkdir %LOCATION%

xcopy /E build\install %LOCATION%

REM Run application once
%LOCATION%\\%APP%\\bin\\%APP%
