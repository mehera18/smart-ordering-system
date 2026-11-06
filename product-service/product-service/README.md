# Product Service

Spring Boot product microservice with layered architecture using DTO-driven CRUD APIs.

## Implemented structure

- `entity`: `ProductEntity`
- `dto`: `PoductRequest`, `ProductResponse`
- `repository`: `ProductRepository`
- `dao`: `ProductDao`, `ProductDaoImpl`
- `service`: `ProductService`, `ProductServiceImpl`
- `controller`: `ProductController`

## API endpoints

- `POST /api/products` - create product
- `GET /api/products` - list all products
- `GET /api/products/{id}` - get product by id
- `PUT /api/products/{id}` - update product
- `DELETE /api/products/{id}` - delete product

## Sample JSON

Create/update request (`PoductRequest`):

```json
{
  "name": "Laptop",
  "price": 999.99,
  "quantity": 5
}
```

Response (`ProductResponse`):

```json
{
  "id": 1,
  "name": "Laptop",
  "price": 999.99,
  "quantity": 5
}
```

## Run

```powershell
./gradlew.bat bootRun
```

## Test

```powershell
./gradlew.bat test
```
