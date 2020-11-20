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
#杀掉对应的进程，如果pid不存在，则不执行
if [ -n "$pids" ]; then
  echo "pids:$pids"
  echo "现在关闭你输入的服务模块：$1 ..."
  kill -9 $pids
fi

echo "检查要删除的的jar包/root/efadmin/$1.jar是否存在，存在则删除"
if [ -f /root/efadmin/"$1".jar ]; then
  echo "删除旧的jar包之前："
  ls /root/efadmin | grep "$1.jar"
  rm -rf /root/efadmin/"$1".jar
  echo "删除旧的jar包之后："
  ls /root/efadmin | grep "$1.jar"
fi

echo "停止模块shell执行结束!"
