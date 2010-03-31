@echo off
echo [INFO] 使用maven jetty plugin 运行项目.

cd ..
call mvn jetty:run-war -Dmaven.test.skip=true