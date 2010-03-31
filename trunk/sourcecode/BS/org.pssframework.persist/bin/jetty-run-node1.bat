@echo off
echo [INFO] 使用maven jetty plugin 在8080端口以node1节点配置运行项目.

cd ..
call mvn jetty:run-war -Dmaven.test.skip=true -Djetty.port=8080 -Dcluster.nodename=node1

pause