# 🛍️ E-Commerce Web Application

This is a comprehensive full-stack e-commerce web application developed as part of the **CSE 214 Advanced Application Development** course. The application enables users, sellers, and administrators to interact with a modern online shopping platform, developed using **Angular** and **Spring Boot**.

---

## 📌 Technologies Used

### 🖥️ Frontend
- **Angular 17** — Component-based frontend framework
- **TypeScript, HTML, SCSS** — Styling and structure
- **Bootstrap** — Responsive design and prebuilt UI

### 🖧 Backend
- **Spring Boot 3** — Java-based backend framework
- **Spring Security & JWT** — Role-based secure authentication
- **Stripe API** — Payment gateway integration
- **Hibernate & JPA** — Object-relational mapping
- **MySQL** — Relational database system

### 🔧 Dev Tools
- **Postman** — API testing
- **GitHub & Git** — Version control
- **IntelliJ IDEA & VS Code** — IDEs

---

## 🎯 Key Features

### 👤 User Panel
- Account registration & login
- Browse categories & search products
- Add to cart & modify quantities
- Checkout with delivery info
- Stripe test payment integration
- Track orders & view history
- Rate and review products

### 🧑‍💼 Seller Panel
- Seller registration/login
- Add/manage own products
- View customer orders
- Access to sales statistics

### 👑 Admin Panel
- Full dashboard access
- Manage all users & sellers
- Add, edit, or delete products
- View, approve or cancel orders

---

## 🧠 System Architecture Overview

```
Frontend (Angular)
     ⬇️
Backend (Spring Boot)
     ⬇️
Database (MySQL)
```

- The frontend communicates via REST API with the backend.
- Backend handles all business logic, authentication, and database transactions.
- Database stores users, products, orders, and reviews.

---

## 📐 Angular Module Diagram

![Angular Module Diagram](./angular_structure_full.png)

Each core responsibility is split into its own Angular module:

- `auth/`: handles login/register with guards
- `user/`: main customer interaction layer
- `seller/`: seller dashboard and inventory control
- `admin/`: access to platform-wide management
- `services/`: communication with backend
- `shared/`: reusable UI components

---

## 🧩 Spring Boot Folder Structure

![Spring Folder Structure](./spring_folder_structure.png)

Spring Boot backend follows a layered architecture:

- `controller/`: exposes API endpoints
- `service/`: business logic processing
- `repository/`: data access layer
- `model/`: JPA entity mapping
- `dto/`: request and response objects
- `security/`: JWT token validation
- `config/`: application settings
- `exception/`: global error handling

---

## 📁 Project File Structure

```
e-commerce/
├── backend-new/
│   └── (Spring Boot Application)
├── frontend-new/
│   └── (Angular Application)
├── diagrams/
│   ├── angular_structure_full.png
│   ├── spring_folder_structure.png
│   └── backend_uml.png
└── README.md
```

---

## 💳 Stripe Payment Integration

- Used Stripe's test environment
- Example Card: `4242 4242 4242 4242`
- Allows secure test transactions during checkout

---

## ⚙️ How to Run Locally

### ▶️ Backend (Spring Boot)

1. Configure `application.properties`:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   ```
2. Run:
   ```bash
   ./mvnw spring-boot:run
   ```

### ▶️ Frontend (Angular)

```bash
cd frontend-new
npm install
ng serve
```

Visit: `http://localhost:4200`

---

## 🔐 Security

- Authentication uses stateless JWT
- `localStorage` is used to store tokens
- Role-based route guards for User, Admin, Seller

---

## 🗃️ Database Tables

- `users`
- `products`
- `orders`
- `order_items`
- `cart_items`
- `reviews`

Database schema is generated automatically using JPA annotations.

---

## 🔗 GitHub Repository

Project Repository:  
[https://github.com/Nis1029/e-commerce](https://github.com/Nis1029/e-commerce)

---

## 🧑‍🎓 Course Information

- **Course**: CSE 214 – Advanced Application Development  
- **Instructor**: [Your Instructor Name]  
- **Student**: Nisanur Uysal  
- **Term**: Spring 2025  

---

## 📝 Notes

- All diagrams are under `/diagrams` folder
- This project supports future extensions such as real-time chat, coupon system, or shipment tracking
