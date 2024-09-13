# Minikube

## Инструкция для быстрого воспроизведения:

```sh
k apply -f $HOME/manifests/deployment.yaml
```

expose:
```sh
k expose po js-server-danila-bratushka-ikbo-24-21-5b59978876-zn25l --target-port 8080
k get svc

minikube service "<svc>"
```

Дашборд:
```sh
minikube dashboard
# проброска портов
ssh -i .vagrant/machines/default/vmware_desktop/private_key -L 10100:localhost:41421  vagrant@192.168.31.102
echo "http://127.0.0.1:10100/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/"
```

ПР6 - ingress в браузере:
```sh
minikube dashboard
# проброска портов
ssh -i .vagrant/machines/default/vmware_desktop/private_key -L 10101:192.168.49.2:80  vagrant@192.168.31.102
echo "http://127.0.0.1:10100/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/"
```



## Misc

stop vm:
```sh
vagrant halt
```

Connect to VM:
```sh
ssh -i .vagrant/machines/default/vmware_desktop/private_key vagrant@192.168.31.102
```

rsync loop:
```sh
while true; do rsync -avz -e "ssh -i .vagrant/machines/default/vmware_desktop/private_key" ./manifests vagrant@192.168.31.102:/home/vagrant && sleep 1.0; done
```

before building image - sync server/Dockerfile files:
```sh
while true; do rsync -avz -e "ssh -i .vagrant/machines/default/vmware_desktop/private_key" ./server vagrant@192.168.31.102:/home/vagrant && sleep 1.0; done
```

minikube with docker images (it works!):
```sh
eval $(minikube docker-env)
```

build image:
```sh
docker build . -t js-server:0.0.1
```

TODO: https://stackoverflow.com/questions/30075461/how-do-i-add-my-own-public-key-to-vagrant-vm

**NOTE: try `--mode=docker` next time**

## Install deps

1. Configure repositories
```bash
# Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
```

2. Install the docker packages

```bash
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

3. Add user to `docker` group:
```bash
sudo usermod -aG docker $USER
```

4. reboot
```bash
reboot
```

## Install kubernetes dependencies

1. kubectl
```bash
curl -LO "https://dl.k8s.io/release/$(curl -LS https://dl.k8s.io/release/stable.txt)/bin/linux/arm64/kubectl"
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl
kubectl version --client

```

2. alias kubectl:
```bash
echo "alias k=kubectl" >> ~/.bashrc
source ~/.bashrc
```

3. libraries/tools
```bash
sudo apt install -y conntrack
```

4. crictl
```bash
curl -LO "https://github.com/kubernetes-sigs/cri-tools/releases/download/v1.31.1/crictl-v1.31.1-linux-arm64.tar.gz" && \
    tar -xvf crictl-v1.31.1-linux-arm64.tar.gz && \
    sudo mv crictl /usr/bin/
    rm crictl-v1.31.1-linux-arm64.tar.gz
```

5. cri-dockerd
```bash
curl -LO "https://github.com/Mirantis/cri-dockerd/releases/download/v0.3.15/cri-dockerd-0.3.15.arm64.tgz" && \
    tar -xvf cri-dockerd-0.3.15.arm64.tgz && \
    sudo mv ./cri-dockerd/cri-dockerd /usr/local/bin/ && \
    rm -rf ./cri-dockerd cri-dockerd-0.3.15.arm64.tgz
```

start the service:
```bash
wget https://raw.githubusercontent.com/Mirantis/cri-dockerd/master/packaging/systemd/cri-docker.service
wget https://raw.githubusercontent.com/Mirantis/cri-dockerd/master/packaging/systemd/cri-docker.socket
sudo mv cri-docker.socket cri-docker.service /etc/systemd/system/
sudo sed -i -e 's,/usr/bin/cri-dockerd,/usr/local/bin/cri-dockerd,' /etc/systemd/system/cri-docker.service
sudo systemctl daemon-reload
sudo systemctl enable cri-docker.service
sudo systemctl enable --now cri-docker.socket
```

6. cni-plugins
```bash
CNI_PLUGIN_VERSION="v1.5.1"
CNI_PLUGIN_TAR="cni-plugins-linux-arm64-$CNI_PLUGIN_VERSION.tgz" # change arch if not on amd64
CNI_PLUGIN_INSTALL_DIR="/opt/cni/bin"
curl -LO "https://github.com/containernetworking/plugins/releases/download/$CNI_PLUGIN_VERSION/$CNI_PLUGIN_TAR"
sudo mkdir -p "$CNI_PLUGIN_INSTALL_DIR"
sudo tar -xf "$CNI_PLUGIN_TAR" -C "$CNI_PLUGIN_INSTALL_DIR"
rm "$CNI_PLUGIN_TAR"
```

7. cni dir
```bash
sudo mkdir -p /etc/cni/net.d
```

## Start with no driver

```sh
minikube start --vm-driver=docker
```

