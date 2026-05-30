# deploy.sh (in your project root on your laptop)
#!/bin/bash
set -e
APP=belote-game
SERVER=mamaafrica@192.168.1.167

mvn clean package -DskipTests

docker build -t registry.kosta-server.org/$APP:latest .
docker push registry.kosta-server.org/$APP:latest
ssh $SERVER "cd ~/apps/$APP && docker compose down && docker compose pull && docker compose up -d"
echo "✓ deployed"