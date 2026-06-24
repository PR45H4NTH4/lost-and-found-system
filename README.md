# Lost and Found System

A full-stack Spring Boot web application built for university students to report and find lost items on campus.

## Features
- **User Authentication**: Session & cookie-based login, signup, logout using Spring Security.
- **Role-based Access Control**: Standard users and admins.
- **Domain Features**: Users can report items as "Lost" or "Found" with descriptions, locations, and dates.
- **Pagination & Filtering**: Items list is paginated and supports advanced filtering by keyword and status.
- **File Uploads**: Users can optionally upload images of the items.
- **Security**: Password hashing (BCrypt), CSRF protection enabled.
- **Validation**: Server-side bean validation for forms with user-friendly error messages.

## Tech Stack
- **Backend**: Spring Boot 3.2.x, Spring MVC, Spring Data JPA, Spring Security
- **Frontend**: Thymeleaf, HTML5, Vanilla CSS
- **Database**: H2 Database (File-based, persists data across restarts). Configurable to MySQL.

## Prerequisites
- Java 17+ installed
- Maven 3.6+ installed

## Setup & Run Instructions
1. Clone the repository to your local machine.
2. Open a terminal in the project root directory.
3. Build the project using Maven Wrapper:
   ```bash
   .\mvnw clean install
   ```
4. Run the application:
   ```bash
   .\mvnw spring-boot:run
   ```
5. Access the application in your browser at `http://localhost:8081`

## H2 Database Console
The application uses a file-based H2 database for ease of use. You can access the console here:
- **URL**: `http://localhost:8081/h2-console`
- **JDBC URL**: `jdbc:h2:file:./data/lostandfound`
- **Username**: `sa`
- **Password**: *(leave blank)*

*(Note: Data is saved to the `./data` directory in your project root.)*

## Screenshots
*(Add screenshots of your UI here before final submission)*