# Arithmetics

This project entails the development of a straightforward application. It has been created as part of an assignment for
the role of Java Developer at an undisclosed company.

## Proposal

The central idea revolves around crafting a platform dedicated to providing fundamental arithmetic computations. These
include Addition, Subtraction, Multiplication, Division, Square Root calculations, as well as the generation of Random
Strings.

## Requirements

### Running the Application

### Project Structure

The project has been thoughtfully organized into two primary packages: `core` and `web`.

- The `core` package houses the core functionality of the application, encompassing repositories, services, and other
  pertinent components.
- The `web` package contains classes and configurations relevant to the APIs.

## Functionalities

### User API - Retrieve User Information

This API is designed to furnish the email address of the currently logged-in user, along with their account balance.

```http
GET http://localhost:8080/api/v1/users
Content-Type: application/json
Authorization: Bearer {{token}}
```

Result:

```json
{
  "email": "demouser@jlcorradi.dev",
  "userBalance": 20.00
}
```

- UserAPI - Add Balance: This api allows the loggedin user to add balance:

```http
PUT http://localhost:8080/api/v1/users/balance
Content-Type: application/json
Authorization: Bearer {{token}}

{
  "amount": 10.0
} 
```

- Operations API - Execute Operation: Allow the user to execute operations at a cost:

```http
POST http://localhost:8080/api/v1/executions
Content-Type: application/json
Authorization: Bearer {{token}}

{
  "type": "RANDOM_STRING",
  "params": [32]
} 
```

Returns:

```json
{
  "id": 13,
  "date": "2023-08-18T03:08:23.369+00:00",
  "operationDescription": "Random String",
  "price": 5.00,
  "userBalance": 15.00,
  "result": "bpwY#U?0}MDPuwObnaM{Pdwlgz#}QE)q"
}
```

### Soft Deletion

In compliance with the specified requirements, each entity within the application incorporates a mechanism for soft
deletion. This is facilitated through the utilization of the "status" attribute, which can take on values of either
ACTIVE or INACTIVE.

### Communicating with the frontend

To enhance communication with the frontend, a novel approach is introduced via custom headers. Currently, the
application extends support for the following headers:

- x-message-success: This header communicates success messages.
- x-message-error: This header conveys error messages. (The HttpUtils class plays a pivotal role in providing this
  functionality.)

## Technologies

- Spring Boot
- Spring Security
- Liquibase
- Feign Clients

## Planned Improvements

- Integrate Hystrix to ensure failfast integration with Random.org.
- Expand functionality by implementing support for OpenAPI. This enhancement will provide enhanced visibility into the
  implemented APIs.