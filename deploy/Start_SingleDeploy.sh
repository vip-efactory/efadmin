#!/usr/bin/bash

if [ $# -ne 1 ]; then
  # 检查是否输入服务的名称，若没有提示用法，终止执行
  echo "您的输入不正确，用法如下："
  echo "用法：$0 ServiceName  例如： $0 efadmin-system"
  echo "部署过程因为参数错误而失败！"
  exit 1
fi

echo "您输入的服务模块名称是：$1"
# shellcheck disable=SC2046
# 过滤掉含有grep的行以及本shell执行的行，否则会把自己干掉
pids=$(ps -ef | grep $1.jar | grep -v grep | awk '{print $2 }')
echo "pids:$pids"

#杀掉对应的进程，如果pid不存在，则不执行
if [ -n "$pids" ]; then
  echo "现在关闭你输入的服务模块：$1 ..."
  kill -9 $pids
fi

if [ -f /root/efadmin/"$1".jar ]; then
  echo "对要启动的jar包赋予可执行权限..."
  chmod +x /root/efadmin/"$1".jar
  echo "现在启动你设定的模块..."
  java -Xmx1500m -Xms1500m -XX:MaxMetaspaceSize=256m -Xmn1024m -XX:InitiatingHeapOccupancyPercent=85 -jar /root/efadmin/"$1".jar --spring.profiles.active=prod >/dev/null 2>&1 &
  echo "检查服务模块是否启动："
  sleep 12s
  ps -ef | grep $1.jar | grep -v grep
else
  echo "要启动的jar包不存在，部署失败！程序退出！"
  exit 1
fi
echo "启动模块shell执行结束!"
