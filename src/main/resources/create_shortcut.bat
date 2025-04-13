@echo off
echo Creating XDNS shortcut on desktop...

:: Create a VBS script to create the shortcut
echo Set oWS = WScript.CreateObject("WScript.Shell") > "%temp%\shortcut.vbs"
echo sLinkFile = oWS.SpecialFolders("Desktop") ^& "\XDNS.lnk" >> "%temp%\shortcut.vbs"
echo Set oLink = oWS.CreateShortcut(sLinkFile) >> "%temp%\shortcut.vbs"
echo oLink.TargetPath = "%~dp0launcher.bat" >> "%temp%\shortcut.vbs"
echo oLink.WorkingDirectory = "%~dp0" >> "%temp%\shortcut.vbs"
echo oLink.Description = "XDNS - DNS Configuration Utility" >> "%temp%\shortcut.vbs"
echo oLink.IconLocation = "%~dp0XDNS.exe" >> "%temp%\shortcut.vbs"
echo oLink.Save >> "%temp%\shortcut.vbs"

:: Run the VBS script
cscript //nologo "%temp%\shortcut.vbs"

:: Clean up
del "%temp%\shortcut.vbs"

echo Shortcut created successfully.
echo Press any key to exit...
pause > nul 