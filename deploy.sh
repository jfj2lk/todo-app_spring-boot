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

# build
## frontend
cd $LOCAL_FRONTEND_DIR
npm run build

## backend
cd $LOCAL_BACKEND_DIR
./gradlew clean build

# deploy
## frontend
ssh ${HOST_NAME} "mkdir -p ${DEPLOY_FRONTEND_DIR}"
scp -r ${LOCAL_FRONTEND_DIR}/dist/ ${HOST_NAME}:${DEPLOY_FRONTEND_DIR}

## backend
ssh ${HOST_NAME} "mkdir -p ${DEPLOY_BACKEND_DIR}"
scp ${LOCAL_BACKEND_DIR}/build/libs/spring-0.0.1-SNAPSHOT.jar ${HOST_NAME}:${DEPLOY_BACKEND_DIR}
