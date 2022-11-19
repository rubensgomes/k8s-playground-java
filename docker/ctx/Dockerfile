# authoer     : Rubens Gomes
# email       : rubens.s.gomes@gmail.com
# description : Dockerfile to create containerized image for application
# docker repo : https://hub.docker.com/repository/docker/rubensgomes/

FROM eclipse-temurin:11-jre-alpine
ARG BUILD_DATE
ARG DOCKER_VERSION
ARG IMAGE_NAME
ARG REPO
ARG TAG

# create group/user to run java spring boot backend "daemon" application
RUN addgroup -S "spring" && adduser -S "spring" -G "spring"
USER spring:spring

# workdir to store application files (libraries, resources, classes....)
WORKDIR /opt/${IMAGE_NAME}

# copy jar extracted layered folder to improve build/startup performance
COPY ${IMAGE_NAME}/dependencies .
COPY ${IMAGE_NAME}/spring-boot-loader .
COPY ${IMAGE_NAME}/application .
COPY ${IMAGE_NAME}/snapshot-dependencies .
RUN true

LABEL Container="${IMAGE_NAME}" \
      DockerVersion="${DOCKER_VERSION}" \
      Maintainer="rubens.s.gomes@gmail.com" \
      org.opencontainers.image.authors="rubens.s.gomes@gmail.com" \
      org.label-schema.build-date="${BUILD_DATE}" \
      org.label-schema.name="${IMAGE_NAME}" \
      org.label-schema.schema-version="1.0" \
      org.label-schema.version="${TAG}" \
      org.label-schema.url="https://rubensgomes.com" \
      org.label-schema.vcs-url="https://github.com/rubensgomes/k8s-learning"

# just download and instantiate docker image running command in cmd tag below
LABEL Installation="Simply instantiate and run image on container"

LABEL org.label-schema.description="A customized jar application"

# sample command to run image on an isolated (not composed) container
# Binds port 80 on node to port 8080 on the container.
LABEL org.label-schema.docker.cmd="\
docker run --detach  \
--name ${IMAGE_NAME} \
--publish 80:8080  \
--rm \
${REPO}/${IMAGE_NAME}:${TAG}"

# The port the container listens on
EXPOSE 8080/tcp

ENTRYPOINT [ "java", "-Xdebug", "-Dspring.profiles.active=${PROFILE}", "org.springframework.boot.loader.JarLauncher" ]
