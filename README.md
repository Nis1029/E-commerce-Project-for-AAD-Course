
# 🛍️ E-Commerce Web Application

This project is a full-stack e-commerce web application developed as part of the **CSE 214 Advanced Application Development** course. The application is built using Angular for the frontend and Spring Boot for the backend, with MySQL as the database system.

---

## 📌 Technologies Used

- **Frontend**: Angular 17, TypeScript, HTML, SCSS, Bootstrap
- **Backend**: Spring Boot 3, Java 17, Spring Security, JWT, Stripe API
- **Database**: MySQL
- **Authentication**: JSON Web Token (JWT)
- **Version Control**: Git & GitHub

---

## 🎯 Key Features

### 👤 User Features
- Register and login
- Browse and search for products
- Add products to cart
- Checkout with address
- Stripe-based payment integration
- View past orders and delivery status
- Rate and review products

### 👑 Admin Features
- Access admin dashboard
- Add, edit, and delete products
- View and manage user accounts
- View and update orders

### 🧑‍💼 Seller Features
- Add/manage seller's own products
- View orders made for their products
- Track performance with basic stats

---

## 🧠 Architecture Overview

```
Angular Frontend
   ↕️  REST API
Spring Boot Backend
   ↕️
MySQL Database
```

---
# 🛍️ E-Commerce Web Application - Project Diagrams

This document provides a visual overview of the structure of both the frontend (Angular) and backend (Spring Boot) components of the e-commerce web application.

---

## 📐 Angular Module Diagram

![Angular Module Diagram](./angular_structure_full.png)

This diagram shows how the Angular app is modularized by feature (admin, seller, user, etc.) and wired into the AppModule.

---

## 🧩 Spring Boot Folder Structure

![Spring Folder Structure](./spring_folder_structure.png)

This diagram displays the folder layout and classes in the backend, demonstrating how components like services, controllers, and repositories interact.

---

## 📁 Project Structure

e-commerce/
├── backend-new/         # Spring Boot backend application
│   ├── controller/      # REST API endpoints (Admin, Seller, User)
│   ├── service/         # Business logic layer
│   ├── repository/      # JPA interfaces for database interaction
│   ├── model/           # Entity classes mapped to DB tables
│   ├── dto/             # Request and response objects
│   ├── security/        # JWT authentication & filters
│   ├── config/          # App-wide configurations (CORS, Stripe, etc.)
│   └── exception/       # Global exception handler
│
├── frontend-new/        # Angular frontend application
│   ├── admin/           # Admin panel (dashboard, user/product management)
│   ├── auth/            # Login & register components with guards
│   ├── seller/          # Seller panel (add/edit product, sales report)
│   ├── user/            # User-facing pages (home, cart, checkout, etc.)
│   ├── services/        # Angular services (product, order, auth, etc.)
│   ├── shared/          # Shared UI components (navbar, footer, etc.)
│   └── models/          # TypeScript interfaces for Product, Order, etc.
│
├── diagrams/            # Architecture, UML, and folder structure diagrams
│   ├── angular_structure_full.png
│   ├── spring_folder_structure.png
│   └── backend_uml.png
│
└── README.md            # Project documentation and submission report

---

## 🔗 GitHub Repository

Project Repository:  
[https://github.com/Nis1029/e-commerce](https://github.com/Nis1029/e-commerce)

Make sure the repository is public for grading.
