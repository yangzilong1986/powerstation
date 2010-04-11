@echo off
echo [INFO] 使用maven sql plugin 创建数据库,使用dbunit初始化测试数据.

cd ..
call mvn initialize -Pinitdb
pause