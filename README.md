# Vehicles Fleet manager

## Description
Manage Vehicles Fleet

### Assumption:
- Vehicles are identified by ```vin``` (Assumed: *Vehicle Identification Number*).
- System doesn't allow to modify the ```vin``` once the vehicle is created.
- ```vin``` and ```License Plate number``` are unique. So the system will not allow using ```VIN``` and  ```License Plate number``` more than once.
-

### Endpoints implemented (supported both JSON and XML input/output)
- Create new Vehicles.
- Read single Vehicle.
- Delete single Vehicles by ```vin```.
- Update single Vehicle.
- Get the list of Vehicles page by page, sort by field name.


## Setup and Run
### Create Docker Image for the application
- To build Docker image locally with the application:
  ```
  mvn jib:dockerBuild
  ```
- Verify the Docker image if created or not
  ```
  docker image ls | grep vehicles
  ```

### [Way 1] To very quick start:
- run the following shell script or follow the instructions below:
    ```
    sh quickSatrt.sh
    ```

###  [Way 2 - alternative] Prerequisite for running docker container manually
- Please create the volume manually using
    ```
    docker volume create --name=vehicle-mongo-data-volume
    ```
-  Please create the network manually using
   ```
   docker network create vehicle-net
   ```

### Setup and Run from the Docker container for both MongoDb and Application manually

- To start MongoDB (mapped on localhost port: 12345) and application container (mapped on localhost port: 8080)
    ```
    docker-compose up -d
    ```

**Alternative (skip it)**
- To start only MongoDB and mapped port on localhost port: 12345
    ```
    docker-compose up -d vehicle-db
    ```

- To run Docker Container with right version (i.e: 0.0.1-SNAPSHOT), check the available docker image list for the application (see above):
  ```
   docker run -p 8080:8080 --net=vehicle-net --env MONGO_DB_HOST=vehicle-db --env MONGO_DB_PORT=27017 --name vehicles_mgmt_manual_container  babuwant2do/vehicles-fleet-manager:0.0.1-SNAPSHOT
  ```

### Application run locally
NOTE: Start the MongoDB Docker container first using the following command, if already not running:
```
docker-compose up -d vehicle-db
```

- To run Unit/Integration tests from the project root directory run following command:
  ```
  mvn clean test
  ```

- To Run the application locally  from the project root directory run following command:
  ```
  mvn spring-boot:run 
  ```
  
### Play with the Application:
- A postman collection is provided in the root directory of the project:
```VehiclesFleetManagerApplication.postman_collection.json ```

## Info:
- Data validations are made and responded with managed error messages. Could be more precise, but leave it in this state as it is a Demo.
- Not All the test cases are implemented but try to demonstrate different ways of testing.

### Contribution:
- Mohammed Ali
- email [mohammed.ali.181008@gmail.com](mailto:mohammed.ali.181008@gmail.com)