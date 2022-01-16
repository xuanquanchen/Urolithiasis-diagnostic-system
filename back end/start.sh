#####################################################################
########             自动化部署服务端运行代码             #############
#修改自https://blog.csdn.net/qshuangshuang/article/details/106015256#
#####################################################################

#!/bin/bash
#package
APP_FLAG='java -jar UCDAS'
APP_CURRENT_NAME=$2

#input param
usage() {
    echo "Usage: input param [start|stop|restart|status] APP_NAME"
    exit 1
}

#check server
is_exist(){
  pid=`ps -ef|grep "$APP_FLAG" |grep -v grep|awk '{print $2}' `
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

#start
start(){
   pushd /root/service/
   pid=`ps -ef|grep redis |grep -v grep|awk '{print $2}' `
   if [ -z "${pid}" ]; then
    echo "Starting redis..."
    service redis start
   else
    echo "Redis has been running."
   fi
   nohup java -jar $APP_CURRENT_NAME.jar >run.log 2>&1 &
   echo "App is running..."
}

#stop
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -9 $pid
  else
    echo "App is not running."
  fi
}

#status
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "App is running. Pid is ${pid}."
  else
    echo "App is NOT running."
  fi
}

#restart
restart(){
  stop
  echo "Sleep 5s and start..."
  sleep 5
  echo "Starting..."
  start
}

#param run method
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac