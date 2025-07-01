#!/bin/bash
set -e

# env
export HOST_NAME="kagoya"
export APP_NAME=$(basename "$(pwd)")
export FRONTEND_DIR="${APP_NAME}/vite"
export BACKEND_DIR="${APP_NAME}/spring"
export LOCAL_APP_DIR=$(dirname "$(pwd)")
export LOCAL_FRONTEND_DIR="${LOCAL_APP_DIR}/${FRONTEND_DIR}"
export LOCAL_BACKEND_DIR="${LOCAL_APP_DIR}/${BACKEND_DIR}"
export DEPLOY_APP_DIR="~"
export DEPLOY_FRONTEND_DIR="${DEPLOY_APP_DIR}/${FRONTEND_DIR}"
export DEPLOY_BACKEND_DIR="${DEPLOY_APP_DIR}/${BACKEND_DIR}"
export JAR_NAME="spring-0.0.1-SNAPSHOT.jar"
export JAR_OPTION="--spring.profiles.active=prod"
export TMUX_BACKEND_SESSION="backend"

# function
# アプリを停止する
stop_app() {
  ssh -Tq $HOST_NAME << EOF
    if tmux has -t $TMUX_BACKEND_SESSION 2>/dev/null; then
      tmux send -t $TMUX_BACKEND_SESSION C-c
      sleep 2
    fi
EOF
}

# アプリを開始する
start_app() {
  ssh -Tq $HOST_NAME << EOF
    if ! tmux has -t $TMUX_BACKEND_SESSION 2>/dev/null; then
      tmux new -d -s $TMUX_BACKEND_SESSION
    fi

    tmux send -t $TMUX_BACKEND_SESSION "cd $DEPLOY_BACKEND_DIR && java -jar ${JAR_NAME} ${JAR_OPTION}" C-m
EOF
}


# build
## frontend
cd $LOCAL_FRONTEND_DIR
# npm run build

## backend
cd $LOCAL_BACKEND_DIR
# ./gradlew clean build

# deploy
## app stop
stop_app

## frontend
ssh ${HOST_NAME} "mkdir -p ${DEPLOY_FRONTEND_DIR}"
scp -r ${LOCAL_FRONTEND_DIR}/dist/ ${HOST_NAME}:${DEPLOY_FRONTEND_DIR}

## backend
ssh ${HOST_NAME} "mkdir -p ${DEPLOY_BACKEND_DIR}"
scp ${LOCAL_BACKEND_DIR}/build/libs/${JAR_NAME} ${HOST_NAME}:${DEPLOY_BACKEND_DIR}

## app start
start_app
