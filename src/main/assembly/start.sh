#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
SERVICE_HOME=`pwd`
echo $SERVICE_HOME
LOGS_DIR=${SERVICE_HOME}/logs
APP_NAME=hotel.jar

usage(){
  echo "Usage: sh start.sh [start|stop|restart|status]"
  exit 1
}

is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}'`
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    java -Xms512m -Xmx512m -XX:NewSize=64m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=78 -XX:+CMSClassUnloadingEnabled -XX:ThreadStackSize=256 -XX:+PrintGC -Xloggc:${SERVICE_HOME}/logs/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${SERVICE_HOME}/logs/heap_dump.hprof -jar $SERVICE_HOME/$APP_NAME >/dev/null &
    echo "service is starting..."
  fi
}

stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill -15 $pid
    echo "$pid is killed!"
    LOGS_DIR=${SERVICE_HOME}/logs
    ARCHIVE_SUFFIX=`date +%Y%m%d-%H%M`
    mv ${LOGS_DIR}/stdout.log ${LOGS_DIR}/stdout.log.${ARCHIVE_SUFFIX}
    mv ${LOGS_DIR}/stderr.log ${LOGS_DIR}/stderr.log.${ARCHIVE_SUFFIX}
    mv ${LOGS_DIR}/gc.log ${LOGS_DIR}/gc.log.${ARCHIVE_SUFFIX}
  else
    echo "${APP_NAME} is not running"
  fi
}

status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

restart(){
  stop
  start
}

start
