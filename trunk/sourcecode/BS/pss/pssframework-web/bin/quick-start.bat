@echo off
echo [Step 1] ��ʼ�����ݿ�
cd ..
call mvn initialize -Pinitdb

echo [Step 2] ������Jetty����
echo [Step 3] ������������� http://localhost:8080/pssframework-web
call mvn jetty:run-war -Dmaven.test.skip=true

