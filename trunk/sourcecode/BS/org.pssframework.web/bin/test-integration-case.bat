@echo off
echo [INFO] ʹ��maven���м��ɲ���.
echo [INFO] �뱣֤�������ݿ��������ұ�ṹ�Ѹ���.

cd ..
call mvn test -Pintegration
cd bin
pause