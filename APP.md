# K8S Learning App

I wrote a simple Spring Boot RESTful microservice application to be deployed 
and run in my single node MicroK8s Kubernetes.   This application was developed
in Java using the Spring Boot frameworks and tools.  The application consists of:

- web/controller layer where a RestController implements the end-point API.
- model layer that defined a simple schema for  request/response objects.
- service layer that separates the front-end end web layer and backend.

The application also consists of unit tests, Spring Boot application.yml, Log4J2
log4j2.xml.  The build of the application is done using maven.

## Installing Docker

In order to created a containerized image for the application I had to install
docker first.  Docker is a tool that provides a way to create standard 
containerized images, and run those containerized images in the local machine.
Kubernetes is able to use docker created containerized images during deployment
and run of application in an isolated containerized environment.
For more details refer to [docker folder](./docker/README.md).

## Running App from IDE

I run my applications from within my Eclipse IDE environment.  Within eclipse
I use the Spring Boot IDE tools to run/debug the application as a Spring Boot
App.

The initial version of this application is not running as a TLS security end-poing, 
and not using Apche HTTP libraries and logging configured, However, I eventually
intend to add these features.  Therefore, I provide below some extra Java VM settings
that could be handy in providing more logs during runtime.

```
-Dspring.profiles.active=local
-Djavax.net.debug=all
-Dcom.sun.xml.ws.transport.http.HttpAdapter.dump=true
-Dcom.sun.xml.ws.transport.http.client.HttpTransportPipe.dump=true
-Djdk.internal.httpclient.debug=true
-Djdk.httpclient.HttpClient.log=errors,requests
```

## Build Application Using Maven

Once I was sure that application was built successfully using maven (notice that
maven 3.8+ is required to be installed.) I had to previously download and install
the latest version of maven in my local Ubuntu Linxu VM, then I had to appropriately
configure my PATH environment variable to point to my maven installation binary
path.  In summary the following steps are required to build the application:

- Install Latest [Apache Maven 3.8.6 or higher](https://maven.apache.org/).
- Configure my Linux PATH environment to include the apache maven binary folder.
- Copy the [settings.xml](./settings.xml) to your local maven .m2 repository folder.


```bash
mvn -U clean install
```

## Build Containerized Image

In order to deploy the application in Kubernetes we first need to create what is
called a "containerized application image" file.  This file is a package containing
the application code, libraries, Java VM, and minimum operating system files required
to run the Java VM and the application.  For more information about the steps to
create this containerized image go to [here](./docker/README.md).

---
[Rubens Gomes](https://rubensgomes.com/)

