echo "creating docker network:  vehicle-net ..."
echo "################################################################"
echo ""
docker network create vehicle-net

echo "creating docker volume:  vehicle-mongo-data-volume ..."
echo "################################################################"
echo ""
docker volume create vehicle-mongo-data-volume

echo "################################################################"
echo "Starting all the required docker containers:  [vehicle-service, vehicle-db] ..."
echo "################################################################"
echo ""

docker-compose up