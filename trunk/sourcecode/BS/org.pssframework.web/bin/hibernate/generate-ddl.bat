@echo off
echo [INFO] Generate database schema from entity defined to %cd%\generated dir.
call ant generate.ddl
echo [INFO] Artifact will generate  in %cd%\generated dir.
pause