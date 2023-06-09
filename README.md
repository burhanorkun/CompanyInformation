# Company Information API

--- 
### Option 1: start on local
* Type  ```mvn spring-boot:run``` to launch Rest API.
* Or you can type ```mvn clean package``` to generate a JAR file and then start server with ```java -jar path/to/jar/file``` (Normally in inside /target/)

--- 
### Option 2: Start by Docker and AWS serverless Postgresql DB
* Example using AWS Serverless Postgresql DB with docker container
```shell 
docker build -t department-app .
docker run -d -p 8090:8090 department-app
docker stop <imageID>  #if you want to stop
```

* in properties.yml file 
```yml
spring:
  datasource:
    url: jdbc:postgresql://food-tech.cfaqce9zt6sm.eu-central-1.rds.amazonaws.com:5432/postgres
    username: postgres
    password: burhanorkun
```

---
### Option 3: Start by Docker Compose with Postgresql docker image
* Launch the Postgresql DB Docker image and our Java application with 2 different docker images.
```shell 
docker-compose up -d
docker-compose down   #if you want to stop
```

this is docker-compose yml file;
```yml
version: '3.8'
services:
  app:
    build:
      context: .
      dockerfile: Dockerfile
    depends_on:
      - db
    ports:
      - "8090:8090"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/postgres
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: burhanorkun
  db:
    image: postgres
    restart: always
    environment:
      POSTGRES_PASSWORD: burhanorkun
      POSTGRES_USER: postgres
      POSTGRES_DB: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data

volumes:
  postgres-data:
```


--- 
## TODO-LIST
*  [x] Department and Employee information API for Company
    * [x] Entity Class
    * [x] Repositories
    * [x] Services
    * [x] Controllers
    * [x] Docker
    * [x] Docker Compose
    * [ ] Authentication and Authorization implementation (JWT or OAuth or SAML)
    * [ ] Swagger Implementation
    * [ ] Terraform for AWS Beanstalk
    * [ ] Deploy with CI/CD automation

---
#### Optional
* [ ] add HATEOAS
* [ ] Alias domain (DNS)

---
#### Examples

Rest API Test Sets for Insomnia; [Foottec_Insomnia_test_set.yaml](./Foottec_Insomnia_test_set.yaml)
<br/>
<br/>
Examples; <br/>
[localhost:8090/api/v1/employees](localhost:8090/api/v1/employees)<br/>
[localhost:8090/api/v1/employees/{id}](localhost:8090/api/v1/employees/1)<br/>
[localhost:8090/api/v1/departments](localhost:8090/api/v1/departments)<br/>
[localhost:8090/api/v1/departments/{id}](localhost:8090/api/v1/departments/1)<br/>


```shell
# get employees 
curl http://localhost:8090/api/v1/employees  
[{"id":5,"name":"Erkan","email":"erkan@gmail.com","department":{"id":2,"name":"VAS","description":"Value Added Services"}},{"id":1,"name":"Burhan Orkun","email":"burhanorkun@gmail.com","department":{"id":1,"name":"CRM","description":"Customer Relation Management2"}}]  
```
```shell
# findById employee
curl http://localhost:8090/api/v1/employees/5
{"id":5,"name":"Erkan","email":"erkan@gmail.com","department":{"id":2,"name":"VAS","description":"Value Added Services"}}
```
```shell
# post employee
curl -H "Content-Type: application/json" http://localhost:8090/api/v1/employees -d '{"name": "Cem","email": "cem@gmail.com","department": {"id": 2}}'
{"id":32,"name":"Cem","email":"cem@gmail.com","department":{"id":2,"name":"VAS","description":"Value Added Services"}}
```
```shell
# delete employee 
curl -XDELETE http://localhost:8090/api/v1/employees/3
```

--- 

```shell
# get department 
curl http://localhost:8090/api/v1/departments
[{"id":1,"name":"CRM","description":"Customer Relation Management2"},{"id":2,"name":"VAS","description":"Value Added Services"},{"id":17,"name":"IT","description":"Information Technology"}]%
```
```shell
# findById department
curl http://localhost:8090/api/v1/department/1
{"id":1,"name":"CRM","description":"Customer Relation Management2"}
```
```shell
# post department
curl -H "Content-Type: application/json" http://localhost:8090/api/v1/department -d '{"name": "CRM","description": "CRM desc"}'
{"name": "CRM","description": "CRM desc"}
```
```shell
# delete department 
curl -XDELETE http://localhost:8090/api/v1/department/3
```


