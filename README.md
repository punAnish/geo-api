# Geo API

Geo API is a Spring Boot application that provides geographical data services. It uses MySQL for data storage and supports asynchronous processing and support basic authentication

## Features

- Basic user authentication
- RESTful API endpoints with JSON data format
- MySQL database integration
- File upload handling
- Asynchronous processing
- Export and import using Apache POI for parsing

## Tech Stack Used

- Java 17
- Spring Boot
- Hibernate
- Spring Data
- Gradle
- MySQL
- Apache POI

## Requirements

- Java 17
- Gradle
- MySQL

## Getting Started

### Clone the repository

```bash
git clone https://github.com/yourusername/geo-api.git
cd geo-api

### Configure the database
spring.datasource.url=jdbc:mysql://localhost:3306/geo-db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

### Build and run the application
./gradlew build
./gradlew bootRun

[↑ Back to top](#top)

