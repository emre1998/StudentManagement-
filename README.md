## Student Management System API Documentation

**1. Introduction**

The Student Management System API allows users to register, login, manage their user information, create and view questions (lecturers only), answer questions (students only), and track scores.

**2. Endpoints**

### 2.1 User Management

| Endpoint | Method | Description | Authorization |
|---|---|---|---|
| `/api/register` | POST | Register a new user | None |
| `/api/login` | POST | Login a user | None |
| `/api/user/{userId}` | PUT | Update user information | Lecturer or Student |
| `/api/user/schoolNo/{schoolNo}` | GET | Get user by school number | Any User |
| `/api/users` | GET | Get all users (admin only) | Admin |
| `/api/user/{userId}` | DELETE | Delete user (admin only) | Admin |

### 2.2 Question Management

| Endpoint | Method | Description | Authorization |
|---|---|---|---|
| `/api/question` | POST | Create a new question | Lecturer |
| `/api/{questionId}` | GET | Get question by ID | Any User |

### 2.3 Answer and Score Management

| Endpoint | Method | Description | Authorization |
|---|---|---|---|
| `/{questionId}/answer` | POST | Answer a question | Student |
| `/api/score/{userId}` | GET | Get score by user ID | Any User |
| `/api/score/{questionId}` | GET | Get score by question ID | Any User |

**3. Request and Response Formats**

### 3.1 Request Formats

**User Registration:**

```json
{
  "name": "John Doe",
  "schoolNo": 12345,
  "password": "password123"
}
```

**Login:**

```json
{
  "username": "johndoe",
  "password": "password123"
}
```

**Update User:**

```json
{
  "name": "Jane Doe",
  "schoolNo": 67890
}
```

**Create Question:**

```json
{
  "questionText": "What is the capital of France?",
  "correctAnswer": "Paris",
  "score": 10
}
```

**Answer Question:**

```json
"Paris"
```

### 3.2 Response Formats

**Success:**

```json
{
  "message": "User registration successful"
}
```

**Login:**

```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOi"
}
```

**Get User by School Number:**

```json
{
  "name": "John Doe",
  "schoolNo": 12345,
  "role": "STUDENT"
}
```

**Get Question by ID:**

```json
{
  "questionText": "What is the capital of France?",
  "correctAnswer": "Paris",
  "score": 10
}
```

**Answer Question:**

```json
"Doğru!:10"
```

**Get Score by User ID:**

```json
10
```

**4. Authorization**

* Admin role is required for accessing `/api/users` and `/api/user/{userId}` (DELETE).
* Lecturer role is required for `/api/question` and updating user info (PUT).
* Student role is required for `/{questionId}/answer`.

**5. Additional Notes**

* All endpoints are accessible at `/api`.
* The `userId` and `questionId` in the endpoints are path variables.
* The `score` in the response for answering a question represents the score awarded for a correct answer.
* The `message` in the response for a successful registration is "User registration successful".
* The `token` in the response for a successful login is a JWT token.

 
