@echo off
echo [Step 1] 初始化数据库
cd ..
call mvn initialize -Pinitdb

echo [Step 2] 编译以Jetty运行
echo [Step 3] 请以浏览器访问 http://localhost:8080/pssframework-web
call mvn jetty:run-war -Dmaven.test.skip=true

