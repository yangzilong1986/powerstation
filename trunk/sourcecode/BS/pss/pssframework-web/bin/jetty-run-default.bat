@echo off
echo [INFO] ʹ��maven jetty plugin ������Ŀ.

cd ..
call mvn jetty:run-war -Dmaven.test.skip=true