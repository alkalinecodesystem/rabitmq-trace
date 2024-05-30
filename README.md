# INSTALL
## Install docker
## Run rabbitmq container
sudo docker run -d --name rabbitmq --hostname rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management
## Access inside the container
sudo docker exec -ti rabbitmq /bin/bash <br />
## Create user test
rabbitmqctl add_user test test <br />
rabbitmqctl set_user_tags test administrator <br />
rabbitmqctl set_permissions -p / test ".*" ".*" ".*" <br />

## Delete user guest
rabbitmqctl delete_user guest <br />
## Access to management GUI
http://localhost:15672 <br />
### start stop container
sudo docker stop rabbitmq <br />
sudo docker start rabbitmq <br />
## Run the application
mvn spring-boot:run <br />
## Test the application
http://localhost:8080/rabbitmq
