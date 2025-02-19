# Toast Interview Test

A simple book management system built using Java with Spring Boot and MySQL. This application allows you to perform CRUD operations on books and authors.

## Requirements

- Java 11+ (For running the project)
- Spring Boot 2.x (For backend development)
- Maven (For managing dependencies)
- MySQL 5.x (For database)
## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/toast-interview-test.git
   cd toast-interview-test
Install dependencies using Maven:

bash
./mvnw clean install
Set up the database:

Create a MySQL database (e.g., book_management).
Run the migration scripts to create the necessary tables for books and authors.
Configure the application.properties (or application.yml) to set up your MySQL connection:

properties
spring.datasource.url=jdbc:mysql://localhost:3306/book_management
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
Run the project: ./mvnw spring-boot:run
API Endpoints
Create: POST /books/ - Add a new book
Read: GET /books/{id} - Fetch details of a specific book
GET /books/ - Fetch list of all books
Update: PUT /books/{id} - Modify an existing book's details
Delete:
DELETE /books/{id} - Remove a book by ID
DELETE /authors/{id} - Remove an author by ID (Optional)
Database Schema
Books Table
id (Primary Key)
title (String)
author_id (Foreign Key)
published_date (Date)
isbn (String, unique)
price (Decimal)
Authors Table (Optional)
id (Primary Key)
name (String)
nationality (String)
Validation and Error Handling
ISBN must be unique
Price must be positive
Error handling for non-existent books, invalid ISBN, and more
Evaluation Criteria
Code Quality: Clear, readable code with comments explaining major sections
Project Structure: Logical and organized project structure
Database Design: Efficient SQL usage with proper relations and constraints
API Functionality: All endpoints work correctly as specified
Error Handling and Validation: Proper error messages and validation checks
License
MIT License

Contact
If you have any questions or suggestions, feel free to reach out to me at tranngocdinhkhanh@gmail.com
