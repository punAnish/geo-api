# Geo API

Geo API is a Spring Boot application that provides geographical data services. It integrates with MySQL for data storage, supports asynchronous processing, and includes basic authentication.

## Features

- **Basic Authentication** – Secure API access with user authentication.  
- **RESTful API** – Provides JSON-formatted data.  
- **MySQL Integration** – Uses MySQL as the database.  
- **File Upload Handling** – Supports file processing.  
- **Asynchronous Processing** – Improves performance with background tasks.  
- **Data Export & Import** – Uses Apache POI for Excel file parsing.  

## Tech Stack

- **Java 17**  
- **Spring Boot**  
- **Hibernate & Spring Data**  
- **Gradle**  
- **MySQL**  
- **Apache POI**  

## Requirements

- Java 17  
- Gradle  
- MySQL  

## Getting Started

### 1. Clone the Repository  

```bash
git clone https://github.com/yourusername/geo-api.git
cd geo-api
```

### 2. Configure the Database  

Update `application.properties` with your database credentials:  

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/geo-db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

### 3. Build and Run the Application  

```bash
./gradlew build
./gradlew bootRun
```

---

[↑ Back to top](#geo-api)


