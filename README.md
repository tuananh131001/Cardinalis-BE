# Cardinalis-BE
## Get Started

# Run on local (New Method 27-12-2022)      

 
0. Run ```docker-compose up -d --build``` in folder of project
### MACOS
1. Open terminal and type ```ipconfig getifaddr en0``` to get ip
2. Go to k8s folder and find ```user-local-service.yaml``` ```tweet-local-service.yaml``` then goto ```change ip here``` line and modify it to
(READ:this is example change the yourPreviousIpHere only)```value: jdbc:postgresql://yourPreviousIPHere:3307/cardinalis # Change ip here```
3. Choose Local and run.     
4.
RUn in terminal these lines
```minikube addons enable ingress```

```minikube addons enable ingress-dns```

Wait until you see the ingress-nginx-controller-XXXX is up and running using ```Kubectl get pods -n ingress-nginx```

Create an ingress using the K8s example yaml file

Update the service section to point to the NodePort Service that you already created

Append 127.0.0.1 cardinalis-be.com to your /etc/hosts file on MacOS (NOTE: Do NOT use the Minikube IP) ```use sudo nano hosts```


Run ```minikube tunnel``` ( Keep the window open. After you entered the password there will be no more messages, and the cursor just blinks)

Hit the http://cardinalis-be.com/user ( or whatever host you configured in the yaml file) in a browser and it should work


<img width="375" alt="image" src="https://user-images.githubusercontent.com/67695658/209690474-d8188d1d-dbc1-41c7-b437-b1a47959670d.png">.  
### Windows.    
1. Open cmd and type ```ipconfig``` to get ip address    
2. Go to ```user-local-service.yaml``` ```tweet-local-service.yaml``` then goto change ip here line and modify it.  
3. Choose Local and run.     
```
I got Minikube on Windows 11 to work for me

minikube start --vm-driver=hyperv
Install minikube Ingress Controller

minikube addons enable ingress
minikube addons enable ingress-dns
Deploy Helm Chart

helm install ...
Get Kubernetes IP Address

nslookup <host-found-in-ingress> $(minikube ip)
Add to etc/host

<minikube-ip> <domain-url>
Live!

curl <domain-url>
```
<img width="720" alt="image" src="https://user-images.githubusercontent.com/67695658/209758185-01b7beeb-b4d3-4c49-a6f0-e44182bb72c1.png">

<img width="375" alt="image" src="https://user-images.githubusercontent.com/67695658/209690474-d8188d1d-dbc1-41c7-b437-b1a47959670d.png">.  




![System Design - Cardinalis](https://user-images.githubusercontent.com/67695658/204201001-31fae380-3132-4845-9307-07b08d4147d5.png)

# 1. User

## 1.1 POST ```/user/register```
Request:
```{
    "username": "jjeanjacques11",
    "email": "jjeanjacques11@gmail.com",
    "password": "1234225"
}
```

## 1.2 ```/fetch/{username}```

Reponse: 
```
{
    "data": {
        "id": "ccdd61fc-4034-460b-95bf-5fd8180f3e6f",
        "username": "jjeanjacques11",
        "email": "jjeanjacques11@gmail.com",
        "created_at": "2022-11-28T17:45:13.2486779",
        "last_login_time": "2022-11-28T17:45:13.2496875",
        "is_hot_user": true
    }
}
```
## 1.3 POST ```/user/login```
Request:
```
{
    "username": "jjeanjacques11",
    "password": "1234225"
}
```

#Diary
![image](https://user-images.githubusercontent.com/67695658/207519786-3c1d9086-4bd7-45e2-9992-fe383979e736.png)
wrong ip change to asia.gcr.io/

# Ref
https://dev.to/adafycheng/java-microservice-in-google-could-2nbf#containerize-the-microservice
