@echo off
echo [INFO] 使用maven jetty plugin 在8081端口以node2节点配置运行项目.

cd ..
call mvn jetty:run-war -Dmaven.test.skip=true -Djetty.port=8081 -Dcluster.nodename=node2

pause