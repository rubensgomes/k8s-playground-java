# MicroK8s Mkctl and Kubectl

This page describes "kubectl" and "mkctl" commands that I commonly use while
administrating my MicroK8s Kubernetes server.

## Handy Kubernetes MicroK8s Commands

The following are some handy commands that I use in my Ubuntu Linux VM Single 
Node MicroK8s Kubernetes installation:

```bash
microk8s config
microk8s config --help
microk8s dashboard-proxy
microk8s disable dns
microk8s disable ingress
microk8s enable dashboard dns ingress
microk8s inspect
microk8s status --wait-ready
microk8s start
microk8s stop
```

## Install and Set Up kubectl on Linux

In the process of setting up applications to run on Linux servers it is common to 
have a need to install several tools (e.g., curl.)  On Ubuntu these tools are 
installed using an Ubuntu native package manager program called "snap". To
install Kubernetes client CLI tool names "kubectl" refer to
[Install and Set Up kubectl on Linux](https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/).

In my case, I installed "kubectl" on my Ubuntu 22.04 VM as follows:

```bash
sudo snap install kubectl --classic
sudo kubectl version --client
```
Note that after having installed "kubectl" I found out that MicroK8s comes with 
its own version of "kubectl" which is accessed running "microk8s kubectl".  I 
create the following alias in my .bash_aliases file: "alias mkctl='microk8s kubectl'"

## Handy MicroK8s Mkctl Commands

Handy kubectl (e.g., mkctl='microk8s kubectl') commands:

```bash
mkctl apply -f ./deployment.yaml
mkctl cluster-info
mkctl cluster-info dump
mkctl delete deploy <k8slearn-deployment>
mkctl delete -f deployment.yaml
mkctl describe deployment -A
mkctl describe node
mkctl get all --all-namespaces
mkctl get deploy -A
mkctl get pods -A
mkctl get pods --show-labels -A
mkctl get rs
mkctl get services -A
mkctl logs <k8s-learning-ms-deployment-65d97d95c4-m2ktj>
mkctl rollout history deployment/<k8s-learning-ms-deployment>
mkctl rollout status deployment/<k8s-learning-ms-deployment>
mkctl scale deployment/<k8s-learning-ms-deployment> --replicas=2
mkctl version --client
mkctl version --client --output=yaml
```

## Build and Deploy k8s-learning-ms Microservice

- In order to run a build you would need to be able to first login to my
  docker.io dockerhub repository. I zm not allowing logins to my dockerhub.

```bash
sudo docker login -urubensgomes docker.io
```
  
- Build and publish containerized image as follows:

```bash
./docker/build.sh -b -c -d -p -r k8s-learning-ms
```

- Deploy application as follows:

```bash
mkctl apply -f ./deployment.yaml
```

- Set up a service for the application as follows:

```bash
mkctl apply -f ./service.yaml
```

- Set up a ingress for the service as follows:

```bash
mkctl apply -f ./ingress.yaml
```


---
[Rubens Gomes](https://rubensgomes.com/)
