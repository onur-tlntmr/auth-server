﻿# Spring-boot Auth Server

This project is a simple auth server.\
It uses JWT as security mechanism.

## Used Technologies

* Spring Boot
* Spring Security
* Spring Jpa
* H2 Database

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/onurcantimur/auth-server
```

**4. Run the app using maven**

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost>

## End Points

### Auth

| Method | Url                  | Decription    | Sample Valid Request Body | 
| ------ |----------------------|---------------|---------------------------|
| POST   | /api/v1/auth/signup  | Sign up       | [JSON](#signup)           |
| POST   | /api/v1/auth/signin  | Log in        | [JSON](#signin)           |
| POST   | /api/v1/auth/signout | Sign out      | [JSON](#signout)          |
| POST   | /api/v1/auth/refresh | Refresh token | [JSON](#refresh)          |

### Users

| Method | Url                                  | Description                               | Sample Valid Request Body |
| ------ |--------------------------------------|-------------------------------------------| ------------------------- |
| GET    | /api/v1/users                        | Get all users (Only for admins)           | |
| GET    | /api/v1/users/{username}             | Get user information by username          | |
| POST   | /api/v1/users                        | Add user                                  | [JSON](#usercreate) |
| PUT    | /api/v1/users/{username}             | Update user (For logged in user or admin) | [JSON](#userupdate) |
| DELETE | /api/v1/users/{username}             | Delete user (For logged in user or admin) | |

### Roles

| Method | Url                     | Description                               | Sample Valid Request Body |
| ------ |-------------------------|-------------------------------------------|--------------------------|
| GET    | /api/v1/roles           | Get all roles (Only for admins)           |                          |
| POST   | /api/v1/roles           | Add role      (Only for admins)           | [JSON](#rolecreate)      |
| POST   | /api/v1/roles/addtouser | Add role      (Only for admins)           | [JSON](#roleaddtouser)   |

Test them using postman or any other rest client.

## Sample Valid JSON Request Bodies

##### <a id="signup">Sign Up -> /api/v1/auth/signup</a>

```json
{
  "fullName": "John Doe",
  "username": "john.doe",
  "email": "john.doe@testuser.com",
  "password": "john.doe@1234"
}
```

##### <a id="signin">Log In -> /api/v1/auth/signin</a>

```json
{
  "username": "john.doe",
  "password": "john.doe@1234"
}
```

##### <a id="signout">Log out -> /api/v1/auth/signout</a>

```json
{
  "token": "8f96as..."
}
```

##### <a id="refresh">Refresh -> /api/v1/auth/refresh</a>

```json
{
  "token": "761b7d..."
}
```

##### <a id="usercreate">Create User -> /api/v1/users</a>

```json
{
  "userName": "john.doe",
  "fullName": "John Doe",
  "email": "john.doe@testuser.com",
  "password": "john.doe@1234"
}
```

##### <a id="userupdate">Update User -> /api/v1/users</a>

```json
{
  "id": 1,
  "userName": "joh.doe",
  "fullName": "John Doe",
  "email": "john.doe@testuser.com",
  "password": "john.doe@1234"
}
```

##### <a id="rolecreate">Create Role -> /api/v1/roles</a>

```json
{
  "name": "ROLE_NEW_TEST"
}
```

##### <a id="roleaddtouser">Add Role To User -> /api/v1/roles/addtouser</a>

```json
{
  "userName": "john.doe",
  "roleName": "ROLE_ADMIN"
}
```

## Default Records

#### Roles

| Id       | Role Name        |
|----------|------------------|
| 1        | ROLE_SUPER_ADMIN |
| 2        | ROLE_ADMIN       |
| 3        | ROLE_USER        |

#### Users

| Username | Full Name   | Email                   | Roles                                   |
|----------|-------------|-------------------------|-----------------------------------------|
| root     | Super Admin | superadmin@testuser.com | ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_USER |
| admin    | Admin       | admin@testuser.com      | ROLE_ADMIN, ROLE_USER                   |
| user     | User        | user@testuser.com       | ROLE_USER                               |
| testuser | Test User   | testuser@testuser.com   | ROLE_USER                               |
