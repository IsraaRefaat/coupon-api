# Coupon API Service

A robust Spring Boot REST API for managing digital coupons with features like creation, validation, consumption tracking, and expiry management.

## 🚀 Features

- **Coupon Management**: Create, retrieve, and manage digital coupons
- **Validation & Consumption**: Validate coupon eligibility and track usage
- **Flexible Discounts**: Support for both fixed amount and percentage-based discounts
- **Usage Tracking**: Monitor coupon consumption history per customer
- **Expiry Management**: Automatic expiration handling with timestamp validation
- **Database Migrations**: Liquibase-managed schema versioning
- **Data Auditing**: Automatic creation and update timestamp tracking

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.5.3
- **Language**: Java 17
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Migration**: Liquibase
- **Build Tool**: Maven
- **Logging**: SLF4J with Logback

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL database
- Git

## 🔧 Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/coupon-api.git
   cd coupon-api
   ```

2. **Configure Database**
   Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build the project**
   ```bash
   ./mvnw clean install
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

The API will be available at `http://localhost:8080`

## 📖 API Documentation

### Base URL
```
http://localhost:8080/api/coupons
```

### Endpoints

#### Coupon Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/coupons` | Create a new coupon |
| GET | `/api/coupons` | Get all coupons |
| GET | `/api/coupons/valid` | Get all valid (active, not expired, with remaining usages) coupons |
| GET | `/api/coupons/{code}` | Get coupon by code |

#### Coupon Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/coupons/validate` | Validate a coupon for a customer |
| POST | `/api/coupons/consume` | Consume a coupon |

#### Consumption Tracking

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/coupons/{couponId}/consumptions` | Get consumption history for a specific coupon |
| GET | `/api/coupons/customers/{customerId}/consumptions` | Get consumption history for a specific customer |

### Request/Response Examples

#### Create Coupon
```json
POST /api/coupons
{
    "code": "SAVE20",
    "totalUsages": 100,
    "expiryDate": "2024-12-31T23:59:59",
    "discountType": "PERCENTAGE",
    "discountValue": 20.00
}
```

#### Validate Coupon
```json
POST /api/coupons/validate
{
    "couponCode": "SAVE20",
    "customerId": "customer123"
}
```

#### Consume Coupon
```json
POST /api/coupons/consume
{
    "couponCode": "SAVE20",
    "customerId": "customer123"
}
```

## 🗄️ Database Schema

### Main Entities

- **Coupons**: Core coupon information with discount details
- **Coupon Consumptions**: Usage tracking per customer
- **Discount Types**: FIXED or PERCENTAGE discount types

### Key Features
- Automatic auditing with creation and update timestamps
- Database sequences for ID generation
- Proper indexing for performance optimization
- Foreign key constraints for data integrity

### Postman Collection
Import the provided Postman collection from `postman/coupon-api-fawry.postman_collection.json` for API testing.

## 🔍 Business Logic

### Coupon Validation Rules
- Coupon must be active (`isActive = true`)
- Coupon must not be expired
- Coupon must have remaining usages
- Customer can consume the same coupon multiple times (if usages allow)

### Discount Types
- **FIXED**: Fixed amount discount (e.g., $10 off)
- **PERCENTAGE**: Percentage-based discount (e.g., 20% off)

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/esraa/couponapi/
│   │   ├── controller/          # REST controllers
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── entity/              # JPA entities
│   │   ├── exception/           # Custom exceptions and handlers
│   │   ├── repository/          # Data repositories
│   │   └── service/             # Business logic services
│   └── resources/
│       ├── db/changelog/        # Liquibase migration files
│       └── application.properties
└── test/                        # Unit and integration tests
```

## 🌐 CORS Configuration
The API is configured to accept requests from `http://localhost:4200` for frontend integration.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- **Esraa Refaat** 
