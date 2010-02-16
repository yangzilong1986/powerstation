#!/bin/sh

prg="/usr/java/jdk1.5.0_10"
app=`pwd`
glib=$app/libs

mypath=.:$prg/lib/tools.jar:$app/libs/fep-common.jar:$app/fep-fe.jar:

opt="-Xms256m -Xmx960m"
main=com.hzjbbis.fk.fe.Application

$prg/bin/java -version

echo "fep fe startup.sh should like this ..."
echo "$prg/bin/java $opt -classpath $mypath $main"
echo "..................let's go......................"

export LANG=zh_CN.GBK
$prg/bin/java $opt -classpath $mypath $main -DFE >/dev/null 2>&1 
