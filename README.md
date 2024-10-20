# E Commerce

## Prerequisites
	- Java 17
	- Maven
	- Sql Server
	
## Installation
	- Clone the repository
	```bash
	$ git clone 
	```
	
	- Install dependencies
	```bash
	$ cd [project_name]
	$ mvn install
	```
	- Create the configuration file and update with your local config
	```bash
	$ cd src/main/resources
	$ cp application-example.properties application.properties
	$ nano application.properties
	```
	- Start Application
	```bash
	$ mvn spring-boot:run
	```
	
	```
	# MySQL Database Configuration
	spring.datasource.url=DB url
	spring.datasource.username=username
	spring.datasource.password=password
	spring.datasource.driver-class-name=DB driver
	
	# JPA / Hibernate Configuration
	spring.jpa.hibernate.ddl-auto=update
	spring.jpa.show-sql=true
	
	# Optional HikariCP configuration (default connection pool)
	spring.datasource.hikari.connection-timeout=30000
	spring.datasource.hikari.maximum-pool-size=10
	```
	
	## Jar build
	```
	mvn clean package
	
	docker build -t [your-image-name] .
	
	docker run -p 8080:8080 your-image-name
	
	docker-compose up
	```
	
	Note: An IDE like **IntelliJ** can perform these tasks for you automatically



# Product API Documentation
# Overview
The Product API allows users to manage products in the eCommerce application. It provides endpoints for creating, retrieving, updating, and deleting products.

Base URL
##### http://<your-server>/api/products

##### Endpoints
##### 1. Create a Product
Endpoint: POST /api/products
Description: Creates a new product.
Request Body: JSON object with fields: name, description, price, and imageUrl.
Response: On success, returns the created product with a status of 201 Created.

##### 2. Retrieve All Products
Endpoint: GET /api/products
Description: Retrieves a list of all products.
Response: On success, returns an array of products with a status of 200 OK.
##### 3. Retrieve a Product by ID
Endpoint: GET /api/products/{id}
Description: Retrieves a product by its ID.
Parameters: id (path) - The ID of the product to retrieve.
Response: On success, returns the product with a status of 200 OK. If not found, returns a 404 Not Found error.
##### 4. Update a Product
Endpoint: PUT /api/products/{id}
Description: Updates an existing product.
Parameters: id (path) - The ID of the product to update.
Request Body: JSON object with fields: name, description, price, and imageUrl.
Response: On success, returns the updated product with a status of 200 OK.
##### 5. Delete a Product
Endpoint: DELETE /api/products/{id}
Description: Deletes a product by its ID.
Parameters: id (path) - The ID of the product to delete.
Response: On success, returns a status of 204 No Content.
##### Error Handling
All endpoints return appropriate error responses in case of issues, including 400 Bad Request for invalid input data and 404 Not Found when a requested product does not exist.




# User API Documentation
## Overview
The User API allows users to manage their profiles and shopping carts in the eCommerce application. It provides endpoints for adding users, managing cart items, and placing orders.

#### Base URL
```http://<your-server>/api/user```

#### Endpoints
##### 1. Add a New User
Endpoint: POST /api/user
Description: Creates a new user.
Request Body: JSON object representing the user with fields: name and phoneNumber.
Response: On success, returns a confirmation message with a status of 201 Created.
##### 2. Add a Product to Cart
Endpoint: POST /api/user/{userId}/cart
Description: Adds a specified product to the user's cart.
Parameters: userId (path) - The ID of the user.
Request Body: JSON object containing the productId (Long).
Response: On success, returns a confirmation message with a status of 201 Created.
##### 3. Remove a Product from Cart
Endpoint: DELETE /api/user/{userId}/cart
Description: Removes a specified product from the user's cart.
Parameters: userId (path) - The ID of the user.
Request Body: JSON object containing the productId (Long).
Response: On success, returns a confirmation message with a status of 201 Created.
##### 4. Retrieve User's Cart
Endpoint: GET /api/user/{userId}/cart
Description: Retrieves the contents of the user's cart.
Parameters: userId (path) - The ID of the user.
Response: On success, returns a set of products in the cart with a status of 200 OK.
##### 5. Place an Order
Endpoint: POST /api/user/{userId}/order
Description: Places an order based on the contents of the user's cart.
Parameters: userId (path) - The ID of the user.
Response: On success, returns a confirmation message with a status of 201 Created.
##### Error Handling
All endpoints return appropriate error responses for issues, such as 400 Bad Request for invalid input and 404 Not Found when a user or product does not exist.