# Spring-Security-DB-JWT

A secure authentication system using **Spring Boot, Spring Security, JWT**, and **Spring Data JPA** for user authentication and role-based authorization. This repository includes different implementations of authentication and authorization mechanisms.

## 📌 Projects in This Repository

### **1️⃣ Spring Security JWT**
- **Description:**  
  Implements authentication and authorization using **JSON Web Tokens (JWT)**.  
  Users can log in, receive a JWT token, and use it to access secured endpoints.

- **Key Features:**
    - **JWT (JSON Web Token)**: Used to verify user authentication.
    - **Token Structure:**
        - **Header**: Contains token type (`JWT`) and signing algorithm.
        - **Payload**: Includes user claims (like roles and permissions).
        - **Signature**: Ensures the integrity of the token.
    - **Why Use JWT?**
        - Secure and scalable authentication.
        - No need for session storage on the server.
        - Used for **stateless authentication**.

---

### **2️⃣ Spring Security OAuth2**
- **Description:**  
  Implements **OAuth 2.0 authentication** using Spring Security.  
  Allows users to authenticate using third-party providers like **Google, GitHub, and Facebook**.

- **Key Features:**
    - Secure authentication using OAuth2.
    - Supports third-party login (Google, GitHub, etc.).
    - Eliminates the need for handling passwords directly.

---

### **3️⃣ Spring Security Roles & Permissions**
- **Description:**  
  Demonstrates **role-based access control (RBAC)** using Spring Security.  
  Defines different **roles (Admin, User)** and **permissions** for secured endpoints.

- **Key Features:**
    - **Role-based access control (RBAC)**.
    - Assign different roles to users (**Admin, User**).
    - Protect specific API endpoints based on user roles.
---

You can Review The Test of APIs: https://documenter.getpostman.com/view/26334403/2sAYXEExtG