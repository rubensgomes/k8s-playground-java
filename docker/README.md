# Docker

Docker is a tool that allows for the creation of containerized images, and then
running those images as container processes on the local machine.  The docker
most recent version is able to create [OCI - Open Container Initiative](https://opencontainers.org/) containerized images.

## Installing Docker Engine

Since my application is to be deployed/run on Kubernetes which requires a containerized
image, I had to install docker to create a standard [OCI - Open Container Initiative](https://opencontainers.org/) containerized image.  I followed the steps on 
[Install Docker Engine on Ubuntu](https://docs.docker.com/engine/install/ubuntu/)
to install docker on my Ubuntu 22.04 VM server.

Once docker is installed,  I followed steps on 
[Post-installation steps for Linux](https://docs.docker.com/engine/install/linux-postinstall/)
for proper configuration of docker on my Ubuntu Linux VM.

## Running Docker Service

In order to ensure that docker is runnin, which is required to create the 
containerized images and run image locally, I followed the steps highlighed 
in [Control Docker with systemd](https://docs.docker.com/config/daemon/systemd/).

## Building Containerized Image

I wrote a shell script [bulid.sh](./build.sh) to create a containerized
image off of [Dockerfile](./ctx/Dockerfile).  My Dockerfile is using a layered 
approach to containerize the Spring Boot application into an image file.  This 
layered image built approach allows for the separation of the libraries, 
Spring Boot classes,  meta-data, and application .class files into separate 
image layers.

First I had to ensure that docker was running, and that I had logged into my 
"rubensgomes" dockerhub registry.  (Note: I do not allow others to publish to 
my docker.io repository at this time.)  I ran the following commands in a
bash shell to start docker, login to dockerhub, and create and publish my
containerized application image to "rubensgomes" dockerhub repository.

```bash
sudo systemctl start docker
docker login -urubensgomes docker.io
cd ${HOME}/github/k8s-learning/docker
./build.sh -b -c -d -p -r k8s-learning-ms
```

## Start/Stop Containerized Application

Prior to deploying to my single node MicroK8s server I ensure that my containerized
application image woked by running the image as a container using local docker.
In order to faciliate the starting/stopping of the containerized application, I 
created a [docker-compose.yml](./docker-compose.yml).  And then I ran the 
following commands to start/stop my containerized application.


```bash
cd ${HOME}/github/k8s-learning/docker
docker compose up --detach --no-recreate --remove-orphans
docker compose down
```

Once the application was running as a container process I was able to get to the
application Swagger RESTful API page by accessing it from a browser connecting to
 localhost 8080 port at <http://localhost:8080>.
 
 ---
[Rubens Gomes](https://rubensgomes.com/)
 
