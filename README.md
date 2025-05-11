A personal project for preparation for a role where Spring Boot is used.
Built with spring boot (see dependencies).

# Api Documentation

### Library System API Documentation

Base URL

http://localhost:8080

## Endpoints

### Users

GET /users — List all users

GET /users/{id} — Get user by ID

POST /users — Create a new user

PUT /users/{id} — Update an existing user

DELETE /users/{id} — Delete a user

GET /users/{id}/loans — Get loans for a user

GET /users/search_name?name=... — Search users by name

GET /users/search_email?email=... — Search user by email

### Books

GET /books — List all books

GET /books/{id} — Get book by ID

POST /books — Create a new book

PUT /books/{id} — Update an existing book

DELETE /books/{id} — Delete a book

GET /books/search?title=...|author=... — Search books by title or author

PATCH /books/{id}/copies — Update available copies of a book

### Loans

GET /loans — List all loans

GET /loans/{id} — Get loan by ID

POST /loans — Create a new loan

PUT /loans/{id} — Update an existing loan

DELETE /loans/{id} — Delete a loan

PATCH /loans/{id} — Mark a loan as returned

GET /loans/search?userId=...|bookId=... — Search loans by user or book ID

## Validation Rules

### Book

title: Required (non-blank)

author: Required (non-blank)

availableCopies: Must be >= 0

loanType: Required (ENUM: SHORT_TERM or LONG_TERM)

### User

name: Required (non-blank)

email: Required (valid format, unique)

### Loan

bookId: Required (UUID)

userId: Required (UUID)

loanDate: Required (ISO Local Date format)
