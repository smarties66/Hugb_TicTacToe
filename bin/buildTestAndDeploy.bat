@echo off
call bin/compile.bat

if not errorlevel 1 ( 
         Echo Compilation Success 
  ) else (
         echo Failure in compilation, terminating...
         exit /b
  )

call bin/unit_test.bat

if not errorlevel 1 ( 
         Echo Unit tests successfull 
  ) else (
         echo Failure in unit tests, terminating...
         exit /b
  )

call bin/deploy.bat
