#!/bin/bash
set -e

# env
source ./env.sh

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
npm run build

## backend
cd $LOCAL_BACKEND_DIR
./gradlew clean build

# deploy
## app stop
stop_app

## frontend
ssh ${HOST_NAME} "mkdir -p ${DEPLOY_FRONTEND_DIR}"
scp -r ${LOCAL_FRONTEND_BUILD_DIR} ${HOST_NAME}:${DEPLOY_FRONTEND_DIR}

## backend
ssh ${HOST_NAME} "mkdir -p ${DEPLOY_BACKEND_DIR}"
scp ${LOCAL_BACKEND_BUILD_DIR}/${JAR_NAME} ${HOST_NAME}:${DEPLOY_BACKEND_DIR}

## app start
start_app
