@echo off
echo Requesting administrative privileges...
echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
echo UAC.ShellExecute "%~dp0XDNS.exe", "", "", "runas", 1 >> "%temp%\getadmin.vbs"
"%temp%\getadmin.vbs"
if exist "%temp%\getadmin.vbs" (del "%temp%\getadmin.vbs")
exit 