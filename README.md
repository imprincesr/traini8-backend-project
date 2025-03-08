# Traini8 Backend Project

## Overview
Traini8 is a Minimum Viable Product (MVP) for a **registry of government-funded training centers**, developed using **Spring Boot**. It provides a REST API to manage training center details with two main endpoints:
- **POST `/api/v1/training-centers`** – Create a new training center with validated input.
- **GET `/api/v1/training-centers`** – Retrieve a list of training centers (optionally filtered by `centerName`).

The project includes robust features like field validation, unique center code enforcement, logging to `logs/traini8.log`, and custom exception handling for reliable error management.

---

## Setup Instructions

### Prerequisites
Ensure the following are installed:
- **Java 17+**
- **Maven 3.6+**
- **Git** (optional, for cloning)
- A terminal or IDE (e.g., IntelliJ IDEA, VS Code)

### Clone the Repository
```bash
    git clone https://github.com/your-username/traini8-backend-project.git
    cd traini8-backend-project
```

### Build and Run the Project
1. Verify prerequisites:
   ```bash
   java -version
   mvn -version
   ```
2. Build and start the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. Access the app:
    - **Base URL**: `http://localhost:8080`
    - **H2 Console**: `http://localhost:8080/h2-console`
        - **JDBC URL**: `jdbc:h2:mem:traini8`
        - **Username**: `sa`
        - **Password**: (blank)

---

## API Usage

### 1. POST /api/v1/training-centers
Creates a new training center.

#### Request
```bash
    curl -X POST "http://localhost:8080/api/v1/training-centers" \
    -H "Content-Type: application/json" \
    -d '{
        "centerName": "Tech Training Hub",
        "centerCode": "TTH123456789",
        "address": {
            "detailedAddress": "123 Tech Street",
            "city": "Bangalore",
            "state": "Karnataka",
            "pincode": "560001"
        },
        "studentCapacity": 100,
        "coursesOffered": ["Java", "Python"],
        "contactEmail": "info@techhub.com",
        "contactPhone": "9876543210"
    }'
```
- **Note**: `centerCode` must be 12 alphanumeric characters, `contactPhone` must be 10 digits.

#### Success Response (201 Created)
```json
    {
        "id": 1,
        "centerName": "Tech Training Hub",
        "centerCode": "TTH123456789",
        "address": {
            "detailedAddress": "123 Tech Street",
            "city": "Bangalore",
            "state": "Karnataka",
            "pincode": "560001"
        },
        "studentCapacity": 100,
        "coursesOffered": ["Java", "Python"],
        "createdOn": 1649234567890,
        "contactEmail": "info@techhub.com",
        "contactPhone": "9876543210"
    }
```

#### Error Responses
- **400 Bad Request**: Invalid input (e.g., missing fields, wrong format).
  ```json
  {
      "message": "Validation failed",
      "details": "{centerName=Center name is mandatory}",
      "timestamp": "2025-03-07T10:00:00Z"
  }
  ```
- **409 Conflict**: Duplicate `centerCode`.
  ```json
  {
      "message": "Duplicate center code",
      "details": "Center code already exists: TTH123456789",
      "timestamp": "2025-03-07T10:00:00Z"
  }
  ```

### 2. GET /api/training-centers
Retrieves all training centers or filters by `centerName`.

#### Request (All Centers)
```bash
  curl -X GET "http://localhost:8080/api/v1/training-centers"
```

#### Request (Filtered by Name)
```bash
  curl -X GET "http://localhost:8080/api/v1/training-centers?centerName=Tech"
```

#### Response (200 OK)
```json
[
    {
        "id": 1,
        "centerName": "Tech Training Hub",
        "centerCode": "TTH123456789",
        "address": {
            "detailedAddress": "123 Tech Street",
            "city": "Bangalore",
            "state": "Karnataka",
            "pincode": "560001"
        },
        "studentCapacity": 100,
        "coursesOffered": ["Java", "Python"],
        "createdOn": 1649234567890,
        "contactEmail": "info@techhub.com",
        "contactPhone": "9876543210"
    }
]
```
- **Note**: Returns `[]` if no centers exist or no matches are found.

---

## Features
- **Field Validation**: Ensures all required fields meet constraints (e.g., 12-char `centerCode`, 10-digit `contactPhone`).
- **Unique Center Code**: Prevents duplicates with a custom exception.
- **Logging**: Records operations and errors in `logs/traini8.log`.
- **Global Exception Handling**: Returns structured JSON error responses.
- **Server-Side Timestamp**: `createdOn` is set automatically on creation.
- **H2 Database**: In-memory storage for simplicity.

---

## Project Structure
```
traini8/
├── src/
│   ├── main/
│   │   ├── java/com/traini8/
│   │   │   ├── Traini8Application.java  # App entry point
│   │   │   ├── controller/              # REST API endpoints
│   │   │   ├── exception/               # Custom exceptions & handler
│   │   │   ├── model/                   # JPA entities
│   │   │   ├── repository/              # Data access layer
│   │   │   ├── service/                 # Business logic
│   │   ├── resources/
│   │   │   ├── application.properties   # Config (H2, logging)
│   ├── test/                            # Unit tests
├── pom.xml                              # Maven dependencies
├── logs/                                # Log files
├── README.md                            # This file
```

---

## Logs and Debugging
- **Log File**: `logs/traini8.log` contains detailed logs of API calls and errors.
- **Clear logs manually if needed**:
    - **Linux/Mac**:
      ```bash
      > logs/traini8.log
      ```
    - **Windows (Command Prompt - CMD)**:
      ```bash
      echo. > logs\traini8.log
      ```
    - **Windows (PowerShell)**:
      ```bash
      Clear-Content logs\traini8.log
      ```

- **Run Tests**: Execute unit tests with:
  ```bash
  mvn test




## Notes
- Built with **Spring Boot 3.x** and **Java 17**.
- Uses **H2 in-memory database**; can be adapted to a persistent DB (e.g., PostgreSQL) for production.

---

## Contributing
1. Fork the repository.
2. Create a branch: `git checkout -b feature-branch`.
3. Commit changes: `git commit -m 'Add feature'`.
4. Push: `git push origin feature-branch`.
5. Open a Pull Request.

---

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Contact
For issues or suggestions, open a GitHub issue or email `princesinghraajput.2000@gmail.com`.