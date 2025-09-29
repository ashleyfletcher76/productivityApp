# API Flow

This document shows example API requests for **Productivity One**.  
Examples are given for both **curl** and **Postman**.

---

## Health checks
**Curl**
```bash
curl http://localhost:8081/health        # user-service
curl http://localhost:8082/todos/health  # todo-service
curl http://localhost:8083/health        # auth-service
```
**Postman**
* Method: GET
* URL: http://localhost:8081/health (adjust for each service)
* Auth: None

**Register**

**Postman**
* Method: POST http://localhost:8081/register
* Body:
```json
{"username": "David", "password": "pass1!A"}
```
**Curl**
```bash
curl -X POST http://localhost:8081/register \
  -H "Content-Type: application/json" \
  -d '{"username":"David","password":"pass1!A"}' 
```
**Login**

**Postman**
* Method: POST http://localhost:8081/login
* Body:
```json
{"username": "David", "password": "pass1!A"}
```
**Curl**
```bash
curl -X POST http://localhost:8081/login \
  -H "Content-Type: application/json" \
  -d '{"username":"David","password":"pass1!A"}'
```
* Response:
```json
{"token": "your-token-here"}
```

**Will soon be updated**

This login process will be connected to auth service. Will automatically trigger the **/token** endpoint. But below is example of how to trigger this endpoint manually.

**Get a JWT (auth-service)**

**Postman**
* Method: POST
* URL: http://localhost:8083/token
* Auth: Basic Auth
    * Username: admin
    * Password: adminone
      **Curl**
```bash
curl -X POST http://localhost:8083/token \
  -u admin:adminone
```
* Response (JWT token string):
```bash
eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
```
**Add todo**

**Postman**
* Method: POST http://localhost:8082/todos
* Auth: Bearer Token (use token from login)
* Body:
```json
{"title": "Swim", "description": "Go swimming on tuesday", "finishBy": "2024-06-15"}
```
**Curl**
```bash
curl -X POST http://localhost:8082/todos \
  -H "Authorization: Bearer your-token-here" \
  -H "Content-Type: application/json" \
  -d '{"title":"Swim","description":"Go swimming","finishBy":"2024-06-15"}' 
```

**Get todos**

**Postman**
* Method: GET http://localhost:8082/todos
* Auth: Bearer Token (use token from login)
  **Curl**
```bash
curl -X GET http://localhost:8082/todos \
  -H "Authorization: Bearer your-token-here"
```

**Search todos by user (restricted)**

**Postman**
* Method GET http://localhost:8082/todos/search?name=David
* Auth: Bearer Token (use token from login)

**Curl**
```bash
curl -X GET "http://localhost:8082/todos/search?name=David" \
  -H "Authorization: Bearer your-token-here"
```

## Notes on responses
* 401 Unauthorized -> Missing or invalid token (handled by requireAuth)
* 403 Forbidden -> If you try to search using another users name it will trigger this response.
* 200 OK -> Successful request
* 201 Created -> Post request successfully