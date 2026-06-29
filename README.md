# Lost and Found System

A comprehensive, production-ready Full-Stack Web Application built with Spring Boot for securely managing lost and found items.

## 🚀 Features

* **User Authentication:** Secure sign up and login powered by Spring Security (with BCrypt password encoding).
* **Role-Based Authorization:** Distinct privileges for Standard Users and System Admins.
* **Item Management:** Full CRUD (Create, Read, Update, Delete) functionality for reporting items.
* **Image Uploads:** Server-side file upload handling for real item pictures.
* **Advanced Search & Filtering:** Quickly find items based on keywords, status (Lost/Found/Resolved), and dates.
* **Pagination:** Efficiently loads item lists for better UI performance and user experience.
* **Production-Ready Database:** Fully integrated with MySQL and Spring Data JPA.

## 🛠️ Technology Stack

* **Backend:** Java 17, Spring Boot 3.2.x, Spring Security, Spring Data JPA
* **Frontend:** Thymeleaf, HTML5, Vanilla CSS3 (Custom Glassmorphism & Responsive Design)
* **Database:** MySQL
* **Build Tool:** Maven

## 💻 How to Run Locally

Follow these precise steps to get the application running on your own machine.

### 1. Prerequisites

* **Java Development Kit (JDK) 17** or higher installed.
* **MySQL Server** installed and running on your local machine (e.g., via XAMPP, WAMP, or standalone MySQL).

### 2. Database Setup

1. Open your MySQL client or command line.
2. Create a new empty database exactly named `lost_and_found`:
   ```sql
   CREATE DATABASE lost_and_found;
   ```
3. The application is pre-configured to use the default `root` user with an empty password. 
   *(Note: If your local MySQL setup requires a different username or password, update lines 32 & 33 inside `src/main/resources/application.properties` before running).*

### 3. Starting the Server

1. Open a terminal or command prompt inside the root folder of this project.
2. Execute the included Maven wrapper command to download dependencies and boot up the server:

   **On Windows:**
   ```cmd
   .\mvnw.cmd spring-boot:run
   ```

   **On Mac/Linux:**
   ```bash
   ./mvnw spring-boot:run
   ```

3. The system will automatically connect to your MySQL database, create all the necessary schema tables, and seamlessly inject realistic dummy data for you to test with immediately.

### 4. Accessing the Application

Once the terminal confirms the Spring application has started, open your favorite web browser and navigate to:
**`http://localhost:8081`**

### 5. Default Test Accounts

You can log in instantly using the automatically generated accounts:

**Standard User:**
* **Email:** `john@example.com`
* **Password:** `password123`

**System Administrator:**
* **Email:** `admin@example.com`
* **Password:** `admin123`