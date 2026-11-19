# E-Iteam Cash

### A lightweight microservices backend for customer and account management

E-Iteam Cash is a Spring Boot project that demonstrates a service-oriented banking platform. Customer and account services expose REST APIs, register with a Eureka server, and are reached through a Spring Cloud Gateway.

<p align="center">
  <strong>Java 21</strong> &nbsp;·&nbsp;
  <strong>Spring Boot</strong> &nbsp;·&nbsp;
  <strong>Spring Cloud</strong> &nbsp;·&nbsp;
  <strong>Apache Maven</strong>
</p>

## Architecture

```text
                         ┌─────────────────────┐
                         │   Eureka Discovery  │
                         │     localhost:8761  │
                         └──────────┬──────────┘
                                    │
Client ──► API Gateway ─────────────┼──► Customer Service ──► H2
          localhost:8888            └──► Account Service  ──► H2
                                               │
                                               └──► Customer Service
                                                    (OpenFeign)
```

| Service | Purpose | Default port |
| --- | --- | ---: |
| `ms-discovery` | Service registry (Eureka) | `8761` |
| `ms-gateway` | Entry point; routes API requests to registered services | `8888` |
| `ms-customer` | Customer REST API and customer data | `8000` |
| `ms-account` | Account REST API; retrieves customer details with OpenFeign | `8100` |

## Get started

### Prerequisites

- Java 21
- No Maven installation required; each service includes the Maven Wrapper.

### Run the services

Start each service in its own terminal, from the repository root. Start discovery first, then the customer and account services, and finally the gateway.

**PowerShell**

```powershell
cd ms-discovery
.\mvnw.cmd spring-boot:run
```

```powershell
cd ms-customer
.\mvnw.cmd spring-boot:run
```

```powershell
cd ms-account
.\mvnw.cmd spring-boot:run
```

```powershell
cd ms-gateway
.\mvnw.cmd spring-boot:run
```

On macOS or Linux, use `./mvnw spring-boot:run` in each service directory.

Once running:

- **API Gateway:** <http://localhost:8888>
- **Eureka dashboard:** <http://localhost:8761>

## API at a glance

Requests can be sent through the gateway:

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/customers` | List customers |
| `GET` | `/customers/{id}` | Get a customer by ID |
| `POST` | `/customers` | Create a customer |
| `GET` | `/accounts` | List accounts |
| `GET` | `/accounts/{id}` | Get an account, including its customer details |
| `POST` | `/accounts` | Create an account |

Example: create a customer (first names must be 5–15 characters; mobile numbers must contain 8 digits).

```bash
curl -X POST http://localhost:8888/customers \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Amine",
    "lastName": "Khalfaoui",
    "email": "amine@example.com",
    "mobileNumber": "12345678"
  }'
```

## Build and test

Run the Maven tests from a service directory:

```powershell
.\mvnw.cmd test
```

To package a service:

```powershell
.\mvnw.cmd package
```

## Technology

- **Java** and **Spring Boot** for the independently runnable services
- **Spring Cloud Netflix Eureka** for service discovery
- **Spring Cloud Gateway** for request routing
- **Spring Cloud OpenFeign** for account-to-customer calls
- **Spring Data JPA** and **H2** for local data persistence

> **Development note:** Customer and account data use in-memory H2 databases. Data is reset when the corresponding service restarts.
