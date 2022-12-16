# Cardinalis-BE
## Get Started


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
