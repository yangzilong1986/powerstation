@echo off
echo [INFO] ʹ��maven jetty plugin ��8080�˿���node1�ڵ�����������Ŀ.

cd ..
call mvn jetty:run-war -Dmaven.test.skip=true -Djetty.port=8080 -Dcluster.nodename=node1

pause