# Cardinalis-BE
## Get Started

Running Databases:

``` bash
docker-compose up -d --build
```

- Access DynamoDb Admin: http://localhost:8001/
- Access Database Tweet: http://localhost:3306/
- Access Database User: http://localhost:3307/

Running applications:

``` bash
## == Build ==
run.sh 
# or 
run.bat

## == Running ==
docker-compose -f docker-compose-app.yml up --build -d
```

- Gateway Service: http://localhost:9002
  - Tweet Service: [/tweet](http://localhost:9002)
  - User Service: [/user](http://localhost:9002)
  - Timeline Service: [/timeline](http://localhost:9002)
- Eureka Service: http://localhost:9000
![System Design - Cardinalis](https://user-images.githubusercontent.com/67695658/204201001-31fae380-3132-4845-9307-07b08d4147d5.png)

# 1. User

## 1.1 ```/user/register```
Request:
```{
    "username": "jjeanjacques11",
    "email": "jjeanjacques11@gmail.com",
    "password": "1234225"
}
```

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
## 1.3 ```/auth/login```
Request:
```
{
    "username": "jjeanjacques11",
    "password": "1234225"
}
```
Reponse: 
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlZWJmZTRkOS1mYzk2LTRiNTktYWI4Yi02YTViZDJjNDJkN2QiLCJpYXQiOjE2Njk2NDYwOTYsImV4cCI6MTY2OTczMjQ5Nn0.s848MeTQqBpdao1PJ4RJXNEUgOjuKhLlPBouX25c_uU",
    "bearer": "Bearer"
}
```

