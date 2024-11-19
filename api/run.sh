./build.sh
docker compose down
docker system prune -f -a
docker compose up -d