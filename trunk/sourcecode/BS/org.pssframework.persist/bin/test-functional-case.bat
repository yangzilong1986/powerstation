@echo off
echo [INFO] ʹ��maven���й��ܲ���, ��ʹ��Jetty�������WebӦ��, ʹ��Selenium�������Selenium������, ʹ��DBUnit������³�ʼ������.
echo [INFO] �뱣֤�������ݿ��������ұ�ṹ�Ѹ���.

cd ..
call mvn integration-test -Pfunctional
cd bin
pause