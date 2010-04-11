@echo off
echo [INFO] 使用maven运行功能测试, 将使用Jetty插件运行Web应用, 使用Selenium插件运行Selenium服务器, 使用DBUnit插件重新初始化数据.
echo [INFO] 请保证测试数据库已启动且表结构已更新.

cd ..
call mvn integration-test -Pfunctional
cd bin
pause