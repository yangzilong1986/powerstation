@echo off

cd %~dp0
call mvn clean install -Dmaven.test.skip=true
call mvn dependency:copy-dependencies -DoutputDirectory=lib -DincludeScope=runtime -Dsilent=true


pause