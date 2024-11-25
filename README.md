# E-Commerce Microservices
## Overview

This project implements a set of microservices for an e-commerce platform. The services include customer management, product management, order management, payment processing, and notification services. The system is built using **Spring Boot** with **Spring Cloud** for service discovery and **Kafka** for messaging.

---

## Table of Contents

1. [Cloning the Repository](#cloning-the-repository)
2. [Running the Services](#running-the-services)
3. [Testing the Services](#testing-the-services)

---

## Cloning the Repository

1. First, clone the repository to your local machine using the following command:

```bash
git clone https://github.com/Madhusanka-slc/ecommerce-microservices.git
```

2. Change to the project directory:

```bash
cd ecommerce-microservices
```

---

## Running the Services

Follow these steps to run the application locally.

### 1. **Config Server**

The **Config Server** provides configuration properties to all other microservices. Start this service first.

```bash
# Navigate to the Config Server service folder
cd config-server
mvn spring-boot:run
```

### 2. **Discovery Server (Eureka)**

Next, start the **Eureka Server**, which is responsible for service registration and discovery.

```bash
# Navigate to the Eureka Server service folder
cd discovery-server
mvn spring-boot:run
```

You can access the Eureka dashboard at: `http://localhost:8761`

### 3. **API Gateway**

Start the **API Gateway**, which handles routing requests to the appropriate services based on the URLs and load balancing.

```bash
# Navigate to the API Gateway service folder
cd gateway
mvn spring-boot:run
```

The API Gateway runs on port `8222`.

### 4. **Microservices**

Now that the **Config Server**, **Discovery Server**, and **API Gateway** are up and running, you can start the following microservices:

- **Customer Service**: Handles customer operations.
  
  ```bash
  # Navigate to the Customer Service folder
  cd customer-service
  mvn spring-boot:run
  ```

- **Order Service**: Handles order-related operations.
  
  ```bash
  # Navigate to the Order Service folder
  cd order-service
  mvn spring-boot:run
  ```

- **Product Service**: Handles product-related operations.
  
  ```bash
  # Navigate to the Product Service folder
  cd product-service
  mvn spring-boot:run
  ```

- **Payment Service**: Handles payment-related operations.
  
  ```bash
  # Navigate to the Payment Service folder
  cd payment-service
  mvn spring-boot:run
  ```

- **Notification Service**: Handles notifications such as emails for various events.
  
  ```bash
  # Navigate to the Notification Service folder
  cd notification-service
  mvn spring-boot:run
  ```

---

## Testing the Services

Once all services are up and running, you can test the APIs using tools like **Postman** or **curl**.

### Test Endpoints Using `curl`

You can use `curl` to make HTTP requests to the services. Here are some example commands for testing:

#### 1. **Customer Service**
- **Create a new customer**:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"name": "John Doe", "email": "john.doe@example.com"}' http://localhost:8090/api/v1/customers
```

- **Get all customers**:

```bash
curl -X GET http://localhost:8090/api/v1/customers
```

#### 2. **Order Service**
- **Create a new order**:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"customerId": "123", "productId": "456", "quantity": 1}' http://localhost:8070/api/v1/orders
```

- **Get all orders**:

```bash
curl -X GET http://localhost:8070/api/v1/orders
```

#### 3. **Product Service**
- **Create a new product**:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"name": "Laptop", "price": 1200}' http://localhost:8050/api/v1/products
```

- **Get all products**:

```bash
curl -X GET http://localhost:8050/api/v1/products
```

#### 4. **Payment Service**
- **Create a new payment**:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"orderId": "1", "amount": 1200}' http://localhost:8040/api/v1/payments
```

---

## Additional Information

- **Eureka Dashboard**: Once the services are running, you can view the Eureka dashboard at `http://localhost:8761`. Here, you can see all the registered services.
  
- **API Gateway**: The **API Gateway** will route requests to the appropriate microservices. You can test routing by sending requests to `http://localhost:8222` (API Gateway's base URL).

---

## Contributing

Feel free to fork the repository and submit issues or pull requests for improvements or new features.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
