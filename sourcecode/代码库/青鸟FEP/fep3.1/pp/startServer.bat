title protocolParser
@echo off
if "%OS%" == "Windows_NT" setlocal
rem ---------------------------------------------------------------------------
rem Start script for the Mas Client
rem ---------------------------------------------------------------------------

set CURRENT_DIR=%cd%
set APP_HOME=%CURRENT_DIR%

set JAVA_HOME=E:\developmenttools\Java\jdk1.5.0_04\bin
set LIBS_HOME=%APP_HOME%\libs
:gotJavaHome
set CLASSPATH=.;%JAVA_HOME%\lib\tools.jar;%APP_HOME%\fep-proparser.jar
set _RUNJAVA="%JAVA_HOME%\java"

echo Using APP_HOME=%CURRENT_DIR%
echo Using JAVA_HOME:   %JAVA_HOME%
echo CLASSPATH:   %CLASSPATH%

set MAINCLASS=com.cw.pp.startup.Application
set JAVA_OPTS=-Xms128m -Xmx256m

%_RUNJAVA% -version
%_RUNJAVA% %JAVA_OPTS% -classpath %CLASSPATH% %MAINCLASS% 

:end
