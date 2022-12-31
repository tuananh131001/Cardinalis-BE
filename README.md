# Cardinalis-BE
## Get Started



# Run on local (New Method 27-12-2022)      
## Requirement
Java 17.   
Intellij 2021.3+    
Docker.    
Cloud code plugin in Intellij     
<img width="212" alt="image" src="https://user-images.githubusercontent.com/67695658/209797868-73e07298-38f2-4ff8-abb2-b6b3587b2dc1.png">

## Running

### First Time Only
1. Run ```./run.sh``` in folder of project

2. Folder structure -> set java 17  

3. Choose Local and run  <img width="121" alt="image" src="https://user-images.githubusercontent.com/67695658/209936790-29aa12c0-bde1-4b1a-82ef-03c04efe12d3.png">

4. Append ```127.0.0.1 cardinalis-be.com``` to your ```sudo nano /etc/hosts``` file on MacOS (Windows : goto youtube/gg search: how to edit hosts file) (NOTE: Do NOT use the Minikube IP) 

5. Run ```minikube tunnel``` ( Keep the window open. After you entered the password there will be no more messages, and the cursor just blinks)   

### Later
1. Run ```./run.sh``` in folder of project
2. Choose Local and run  <img width="121" alt="image" src="https://user-images.githubusercontent.com/67695658/209936790-29aa12c0-bde1-4b1a-82ef-03c04efe12d3.png">
3.  Run ```minikube tunnel``` ( Keep the window open. After you entered the password there will be no more messages, and the cursor just blinks)   


# API
## User

### POST ```/user/register```
Request:
```{
    "username": "jjeanjacques11",
    "email": "jjeanjacques11@gmail.com",
    "password": "1234225"
}
```

### GET ```/fetch/{username}```

### POST ```/user/login```
Request:
```
{
    "username": "jjeanjacques11",
    "password": "1234225"
}
```

### PUT ```/{id}```
JSON nha ( tự hiểu lười viết) 
```
{
    private UUID id;
    private String avatar;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginTime;
    private Boolean isHotUser;
}
```
### GET ```/search?username=jjeanjacques10``` 

## Following

### GET ```{{base_url}}/user/follow/00cc8485-e281-4faf-b37b-0c34ed33da4e/following```

### GET ```{{base_url}}/user/follow/00cc8485-e281-4faf-b37b-0c34ed33da4e/followers```

### POST ```{{base_url}}/user/follow```
```

{
    "followerId": "401d1f45-8a24-407c-b93e-05eeab1282d6",
    "followedId": "a3423720-e87f-4002-bde4-525dd3320b36"
}

```

### DELETE ```{{base_url}}/user/follow```
```
{
    "followerId": "00cc8485-e281-4faf-b37b-0c34ed33da4e",
    "followedId": "2dd48fa5-c953-4546-8671-6448caa1b764"
}
```

#Diary
![image](https://user-images.githubusercontent.com/67695658/207519786-3c1d9086-4bd7-45e2-9992-fe383979e736.png)
wrong ip change to asia.gcr.io/

# Ref
https://dev.to/adafycheng/java-microservice-in-google-could-2nbf#containerize-the-microservice

![System Design - Cardinalis](https://user-images.githubusercontent.com/67695658/204201001-31fae380-3132-4845-9307-07b08d4147d5.png)

