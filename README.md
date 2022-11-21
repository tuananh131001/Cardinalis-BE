# Cardinalis-BE
<img width="1042" alt="image" src="https://user-images.githubusercontent.com/67695658/202983565-df89c02f-f349-462e-b497-9f343b74acc1.png">

# Register ```http://localhost:8080/api/auth/register```
## Need to have role first
JSON Form :
```
{
    "username": "sir",
    "password": "1234552dss"
}
```
# Login ```http://localhost:8080/api/auth/login```
JSON Form :
```
{
    "username": "sir",
    "password": "1234552dss"
}
```
Received: 
```
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzaXIiLCJpYXQiOjE2NjkwMzAzMTIsImV4cCI6MTY2OTAzMDM4Mn0.70gL1ial_bic0hbbA0kAlISQc9Mv7QQW_Wcn98PREESvauGkf9Fk5R8C5FjevR_0FymVO15iBqFmYJAR0jAcyQ",
    "tokenType": "Bearer "
}
```
