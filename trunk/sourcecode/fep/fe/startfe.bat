title fe
@echo off
if "%OS%" == "Windows_NT" setlocal
rem ---------------------------------------------------------------------------
rem Start script for the FAS Gate Server
rem ---------------------------------------------------------------------------

set CURRENT_DIR=%cd%
set APP_HOME=%CURRENT_DIR%

set JAVA_HOME=C:\Java\jdk1.5.0_12\bin
set LIBS_HOME=%APP_HOME%\libs
:gotJavaHome
set CLASSPATH=.;%JAVA_HOME%\lib\tools.jar;%APP_HOME%\libs\fep-common.jar;%APP_HOME%\fep-fe.jar
set _RUNJAVA="%JAVA_HOME%\java"

echo Using APP_HOME=%CURRENT_DIR%
echo Using JAVA_HOME:   %JAVA_HOME%
echo CLASSPATH:   %CLASSPATH%

set MAINCLASS=com.hzjbbis.fk.fe.Application
set JAVA_OPTS=-Xms256m -Xmx960m

%_RUNJAVA% -version
%_RUNJAVA% %JAVA_OPTS% -classpath %CLASSPATH% %MAINCLASS% 

:end
