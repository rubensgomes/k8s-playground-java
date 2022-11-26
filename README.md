# kubernetes-learn-java

A Java learning playground project to learn and experiment with Kubernetes.

This learning project comprises basically of the following tasks:

- Set up an Ubuntu VM on Windows 10 using Hyper-V.
- Install a single node Kubernetes MicroK8s on Ubuntu VM. This steps creates a
  default cluster named "microk8s-cluster".
- Install Kubernetes kubectl CLI tools.
- Develop microservice to deploy and run on a single MicroK8s node.
- Build a containerized image of the application.
- Push containerized image to docker.io registry.
- Create a Kubernetes deployment manifest file.
- Create a Kubernetes service manifest file.
- Ensure Kubernetes processes are running.
- Ensure a cluster with 1 (one) node is created.
- Deploy application deployment.
- Set up corresponding Kubernetes service for the application.

## Set Up Ubuntu 22.04 LTS Linux VM Using Hyper-V

For further information about Hyper-V on Windows 10, refer to
[Hyper-V on Windows 10](https://learn.microsoft.com/en-us/virtualization/hyper-v-on-windows/?source=recommendations).

I have a 64-bit Windows 10 Enterprise laptop which already comes with the Hyper-V 
technology installed.  I had enable Hyper-V following [Install Hyper-V on Windows 10](https://learn.microsoft.com/en-us/virtualization/hyper-v-on-windows/quick-start/enable-hyper-v).

After Hyper-V was enabled, I ran the "Hyper-V Manager" Windows 10 GUI, and
followed prompts to install Ubuntu 22.04 LTS.  There are several online
documentation on how to install an Ubuntu VM on Windows 10 using Hyper-V.

After the installation, I configured several tools on my Ubuntu VM.  Also, I 
allocated extra disk space from Windows 10 to extend the size of one of the
main file system partitions in Ubuntu.  Because I was running low of disk space
in the "/" file system after installing Kubernetes.

## Installing Single Node Kubernetes MicroK8s

I followed the documentation at
[Install Kubernetes on Ubuntu](https://ubuntu.com/kubernetes/install)
to install a single node MicroK8s Kubernetes on my Ubuntu 22.04 Linux VM 
Windows 10 enterprise laptop. For further information about MicroK8s refer to 
[MicroK8s documentation](https://microk8s.io/docs?_ga=2.10857782.324099204.1668363292-1340609638.1668067881).  Noticed that a
default cluster named "microk8s-cluster" is created as part of the installation.

Once MicroK8s is installed it runs as a service backend process at the start of
Ubuntu.  In order to save my laptop battery or CPU cycles, I stopped/started 
MicroK8s as needed using the following commands:

```bash
sudo microk8s stop
sudo microk8s start
```

For handy Kubernetes "mkctl" CLI commands refer to [MicroK8s Commands](./k8s/README.md).

## Develop Simple Microservice Application

I created a simple Java Spring Boot microservice to deploy and run on my single
MicroK8s Kubernetes.   I built a docker containerized image using my own 
"build.sh" and Dockerfile.  For more information about the application files,
configuration and how the containerized image was created refer to the folder
[APP.md](./APP.md).

In addition to developing a simple Spring Boot microservice application, I also
wrote a [build.sh](./docker/build.sh) shell script to create a containerized
image.  This containerized image is created making use of a 
[Dockerfile](./docker/ctx/Dockerfile) which I wrote to make use of different
layers for Spring Boot classes, dependency libraries, meta-data files, and the
application Java .classes files.

## Deploying Containerized Application Image

Once a containerized image is created from running [build.sh](./docker/build.sh)
as "./build.sh -b -c -d -p -r k8s-learning-ms" following the steps in 
[Building Containerized Image](./docker/README.md), I then set up a deployment
file to deploy the containerized application to Kubernetes.



---
[Rubens Gomes](https://rubensgomes.com/)
