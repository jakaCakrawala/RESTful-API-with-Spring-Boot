# Inventory API

A simple Spring Boot REST API for managing inventory using SQLite as the database.

## Requirements

* Java 17 or higher (this project uses Java 24)
* Maven 3.8+
* Git (optional, for cloning the repository)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/inventory-api.git
cd inventory-api
```

### 2. Build the project

Make sure you have Java and Maven installed, then run:

```bash
./mvnw clean install
```

### 3. Run the application

```bash
./mvnw spring-boot:run
```

The application will start on:
📍 `http://localhost:8080`

### 4. Database Configuration

This project uses SQLite as its database. The database file will be automatically created in the project root directory as `inventory.db`.

Make sure the following is set in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:sqlite:inventory.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=com.example.inventory.config.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 5. API Endpoints

You can test the API using Postman or any HTTP client.

Example endpoint (GET all items):

```http
GET http://localhost:8080/api/items
```

> Add your own controllers and endpoints as needed.

### 6. Notes

* Tables are automatically created on application startup using Hibernate based on your `@Entity` classes.
* No external database installation is required.
