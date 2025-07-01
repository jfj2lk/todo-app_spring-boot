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
