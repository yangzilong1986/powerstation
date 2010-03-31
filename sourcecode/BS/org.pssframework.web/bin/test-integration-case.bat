@echo off
echo [INFO] 使用maven运行集成测试.
echo [INFO] 请保证测试数据库已启动且表结构已更新.

cd ..
call mvn test -Pintegration
cd bin
pause