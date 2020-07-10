#!/bin/sh
#重启服务脚本
#作者：崔辉
#编写日期：2020年05月20日11:15:45

echo "正在关闭order服务"
ps -ef |grep commoncurd | grep -v "grep" | awk '{print $2}' | xargs kill -9
sleep 1
echo "正在启动order服务"
cd /root/commoncurd
nohup java -jar commoncurd-0.0.1-SNAPSHOT.jar -c application.properties &
