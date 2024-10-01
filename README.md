# Find My Pet
Find My Pet is a Spring Boot API service. It has been designed to help pet owners reunite with lost pets, with the help of the wider community. 

## Getting Started
The following instructions will help you to run this project on your local machine for development and testing purposes:

1. Clone the repository:
```
    git clone https://github.com/Sunglow-nc/find-my-pet-api.git
```
2. Navigate to the project directory:
```
    cd find-my-pet-api
```
3. Build the project:
```shell
    mvn clean install
```
4. Run the application:
```shell
    mvn spring-boot:run
```
5. Visit http://localhost:8080/api/v1
6. To test the application:
```shell
    mvn test
```

## API Documentation
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Usage
Provide a few examples of how to use the application

## Configuration
1. create a .env file in the root of the project
2. create an account with [Cloudinary](https://cloudinary.com/) and in the .env file assign your API to the CLOUDINARY_URL
3. create an account with [Neon Postgres](https://neon.tech/) and in the .env file assign your API to the NEON_DB_URL

## Built With
- Spring Boot: Java-based framework for building stand-alone, production-grade Spring-based Applications.
- Maven: Build automation tool used primarily for Java projects.
- Google Maps API: Provides mapping functionality and location-based services for the application.
- Neon: Serverless Postgres database for scalable and efficient data storage.
- Cloudinary: Cloud-based image and video management solution for storing and manipulating media files.
- H2: Lightweight, in-memory database used for development and testing purposes.
- Lombok: Java library that reduces boilerplate code by automatically generating getters, setters, constructors, and more.
- Swagger: API documentation tool that helps design, build, document, and consume RESTful web services.

## Authors
[Andrei Vasiliu](https://github.com/andrei-vasiliu-coding)  
[Abi Gill](https://github.com/AbiPetheram)  
[Qiujuan Wang](https://github.com/QWang00)  
[Dmytro Shakhray](https://github.com/dimadeloseros1)  
[Oliver Arroyo](https://github.com/o-arroyo)  

## Acknowledgments
[Northcoders](https://northcoders.com/)  