title bp
@echo off
if "%OS%" == "Windows_NT" setlocal
rem ---------------------------------------------------------------------------
rem Start script for the FAS Gate Server
rem ---------------------------------------------------------------------------

set CURRENT_DIR=%cd%
set APP_HOME=%CURRENT_DIR%

set JAVA_HOME=%java_home%
set LIBS_HOME=%APP_HOME%\libs
:gotJavaHome
set CLASSPATH=.;%JAVA_HOME%\lib\tools.jar;%APP_HOME%\libs\fep-common.jar;%APP_HOME%\fep-bp.jar
set _RUNJAVA="%JAVA_HOME%\bin\java"

echo Using APP_HOME=%CURRENT_DIR%
echo Using JAVA_HOME=%JAVA_HOME%
echo CLASSPATH=%CLASSPATH%

set MAINCLASS=com.hzjbbis.fk.bp.Application
set JAVA_OPTS=-Xms256m -Xmx1024m

%_RUNJAVA% -version
%_RUNJAVA% %JAVA_OPTS% -classpath %CLASSPATH% %MAINCLASS% 

:end
