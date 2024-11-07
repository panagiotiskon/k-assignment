# Backend

## User Authentication / Authorization

The application includes two roles: Administrator (Admin) and Customer (Client). Upon initializing the application, a default Admin and Client account are pre-configured with the following credentials:

- **Admin Account**  
  - **Email:** `admin@example.com`
  - **Password:** `password`

- **Client Account**  
  - **Email:** `client@example.com`
  - **Password:** `password`

User role verification is managed through JWT (JSON Web Tokens). Specifically, upon each successful user authentication, the application sends a cookie containing a JWT with the user's information. 

Role management is handled through Spring Security, with specific configurations to restrict actions based on user roles (Admin and Client). The setup includes both method-level and URL-based security.

## Product Filtering & Pagination 

The `ProductSpecifications` class in the backend uses JPA `Specification` to implement flexible product filtering based on specified criteria. This is achieved through the `searchFilterSpecification` method, which constructs a dynamic query depending on the filter parameters provided in `ProductSearchFilter`.

Additionally, the `ProductService` class supports pagination to manage large sets of product data efficiently. Pagination is implemented using Spring Data's `Pageable` interface.

### How Filtering Works

1. **Company Filtering**:  
   If a company (or list of companies) is specified, the query filters products by checking if the `company` field in the `Product` entity matches any of the values provided in the filter. This allows for multiple companies to be filtered at once.

2. **Price Range Filtering**:  
   The filter can apply a minimum and/or maximum price to the product search. 
   - If a minimum price is specified, the query will filter for products where the `price` field is greater than or equal to this minimum.
   - If a maximum price is specified, it will limit results to products with a `price` less than or equal to this maximum.

## Requests Validation & Exception Handling

### Validation

- **Custom Validators**: The `ProductValidator`, `ProductPatchValidator`, and `PurchaseValidator` classes handle validation for product and purchase creation and patch requests, ensuring all incoming data meets required constraints.
  
### Exception Handling

- **GlobalExceptionHandler**: A centralized exception handler annotated with `@ControllerAdvice` manages custom exceptions and validation errors.
  - **Custom Exceptions**: Specific handlers for return `404 Not Found` responses when resources are missing.
  - **Validation Errors**: Caught by a handler for `MethodArgumentNotValidException`, which collects field-specific errors into a `Map` and returns it in a `400 Bad Request` response for clear client feedback.

## Handling Purchase Creation

To prevent the N+1 problem in the process of creating a purchase and its associated purchase items, instead of eagerly fetching each product individually in separate queries, all the products were fetched at once. This approach minimizes the number of database queries and avoids unnecessary database hits.

## Rate Limiting
The application includes a `RateLimitingFilter` to restrict the number of requests an individual client can make within a specified time frame. 
The filter applies rate limits based on each client’s IP address, using a `Bucket` for each IP. The `ipBucketMap` stores these buckets in a thread-safe `ConcurrentHashMap`. Each IP can make up to 10 requests per minute. 
Each request consumes one token, and once tokens run out, no more requests are allowed until they are refilled returning a `429 Too Many Requests` status code.

## DB Schema 

![Screenshot from 2024-11-06 21-37-19](https://github.com/user-attachments/assets/6b28eb15-a4cf-43db-b802-717867a77fa4)

## How to Run:

1. Navigate to the `/backend` folder where the `Dockerfile` is located.

2. Run `mvn clean package` to compile the JAR file.

3. Ensure Docker is running.

4. Run `docker compose up --build` to build and start the containers.

5. There is also a Postman export JSON file available to test the API.


### Potential Improvement

A potential improvement in the database design would be to create an additional table for the product companies. 
This table would store information about each company and would be linked to the products table via a 1:N (one-to-many) relationship, where the company’s primary key would act as a foreign key in the products table.

#### Example of the "Companies" Table

| Column Name     | Data Type     | Description                              |
|-----------------|---------------|------------------------------------------|
| `id`            | INT           | Primary key,                             |
| `name`          | VARCHAR(255)   | Name of the company                     |
| `created_at`    | TIMESTAMP     | Timestamp of when the company record was created |
| `updated_at`    | TIMESTAMP     | Timestamp of when the company record was last updated |


# Frontend

## User Role Management and UI Controls

- **Admin**: Admin users can add, edit, or delete products using dedicated modals for creating and updating products. 
  
- **Client**: Clients can add products to their shopping cart and proceed to checkout. The cart is accessible via a modal, which shows an overview of selected items and allows clients to adjust quantities or remove items.

Both roles fetch their permissions and access rights upon login, enabling the application to present an interface for each role.

## Product Filtering and Infinite Scrolling

The product display page includes filtering options that allow users to narrow down products by company and price range. As users scroll, more products are fetched automatically based on the specified filters.

## Login Page

![Screenshot from 2024-11-06 23-32-57](https://github.com/user-attachments/assets/38ab9918-fec0-4332-afac-914f225ae491)

## Admin Product Page

![Screenshot from 2024-11-06 23-03-28](https://github.com/user-attachments/assets/57865e2d-67bb-4e2c-8658-9f7ea44fcd64)

## Client Product Page

![Screenshot from 2024-11-06 23-03-03](https://github.com/user-attachments/assets/04099324-db2d-49b5-8997-68e55cd2c15b)

## How to Run:

1. Navigate to the `/frontend` folder.
   
2. Run `npm build` to build the frontend project.
   
3. Run `npm start` to start the frontend application.

#### Due to time constraints, the frontend of the application requires further improvements for optimal functionality and appearance, specifically in the infinite scrolling functionality.
