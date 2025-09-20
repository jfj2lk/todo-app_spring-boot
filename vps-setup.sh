#!/bin/bash

set -e

# env
source ./env.sh
cat << EOF >> ~/.profile
export DB_USERNAME=${DB_USERNAME}
export DB_PASSWORD=${DB_PASSWORD}
EOF
source ~/.profile

# install
sudo apt update
sudo apt install -y nginx openjdk-21-jre-headless mysql-server

# mysql
sudo mysql <<EOF
-- mysql_secure_installation
ALTER USER 'root'@'localhost' IDENTIFIED WITH 'auth_socket';
DELETE FROM mysql.user WHERE User='';
DELETE FROM mysql.user WHERE User='root' AND Host NOT IN ('localhost', '127.0.0.1', '::1');
DROP DATABASE IF EXISTS test;
DELETE FROM mysql.db WHERE Db='test' OR Db='test\\_%';
-- app-setting
CREATE DATABASE IF NOT EXISTS \`${DB_NAME}\`;
CREATE USER IF NOT EXISTS '${DB_USERNAME}'@'localhost' IDENTIFIED BY '${DB_PASSWORD}';
GRANT ALL PRIVILEGES ON \`${DB_NAME}\`.* TO '${DB_USERNAME}'@'localhost';
FLUSH PRIVILEGES;
EOF

# nginx
sudo chmod +x /home/ubuntu
sudo rm -f /etc/nginx/sites-enabled/default
sudo tee /etc/nginx/sites-available/${APP_NAME} > /dev/null <<EOF
server {
    listen 80;
    listen [::]:80;

    server_name ${DOMAIN};

    root /home/ubuntu/todo-app_spring-boot/vite/dist;
    index index.html;

    location / {
        try_files \$uri \$uri/ /index.html;
    }

    location /api {
        proxy_pass http://localhost:8080;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }
}
EOF
sudo ln -sf /etc/nginx/sites-available/${APP_NAME} /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl reload nginx

# https
sudo snap install --classic certbot
sudo ln -sf /snap/bin/certbot /usr/bin/certbot
sudo certbot --nginx --non-interactive --agree-tos --domains ${DOMAIN} --redirect

# tmux
echo "set -g mouse on" >> ~/.tmux.conf
