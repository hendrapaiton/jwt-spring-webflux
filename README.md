# jwt-spring-webflux

Sample project that uses Spring Webflux Kotlin with JWT Tokens and MongoDB using username/password login

## Run the application
```
gradlew bootRun
```

## Endpoints
All requests might need the media header
```header
Content-Type: application/json
```

### Tokens
POST http://localhost:8080/oauth/token

Body
```json
{
  "username": "hendra",
  "password": 1234
}
```

Sample response
```json
{
    "token": "ey..."
}
```

### Protected Routes
GET http://localhost:8080/secure

Headers
```headers
Authorization: Bearer {token}
```


Sample response
```
Hello hendra!
```
