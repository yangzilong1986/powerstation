@echo off
echo [INFO] ʹ��maven����pom.xml ��������jar����ӦĿ¼.
echo [INFO] compile,runtime����/webapp/WEB-INF/lib, test,provided����/lib

set local_driver=%cd:~0,2%
set local_path=%cd%

set exec_path=%0
set exec_path=%exec_path:~0,-13%"
set exec_driver=%exec_path:~1,2%

%exec_driver%
cd %exec_path%/..

call mvn dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime -Dsilent=true
call mvn dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib -DincludeScope=runtime -Dsilent=true

%local_driver%
cd %local_path%

pause