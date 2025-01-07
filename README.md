# Coupon Management API

This API provides endpoints to manage coupons and apply them to shopping carts. Below are the details of each endpoint and their expected behavior.

## API Endpoints

### 1. **POST /coupons**
Create a new coupon.

**Request Body:**
```json
{
  "code": "STRING",          // Unique coupon code
  "discountType": "STRING",  // "flat" or "percentage"
  "discountValue": NUMBER,    // Discount value (e.g., 20 for 20% or 50 for flat 50 units off)
  "minCartValue": NUMBER,     // Minimum cart value required for coupon applicability
  "expiryDate": "STRING"     // Expiry date in ISO format (e.g., "2025-01-31T23:59:59Z")
}
```

**Response:**
```json
{
  "id": "STRING",           // Unique ID of the created coupon
  "message": "Coupon created successfully."
}
```

### 2. **GET /coupons**
Retrieve all coupons.

**Response:**
```json
[
  {
    "id": "STRING",
    "code": "STRING",
    "discountType": "STRING",
    "discountValue": NUMBER,
    "minCartValue": NUMBER,
    "expiryDate": "STRING"
  },
  ...
]
```

### 3. **GET /coupons/{id}**
Retrieve a specific coupon by its ID.

**Response:**
```json
{
  "id": "STRING",
  "code": "STRING",
  "discountType": "STRING",
  "discountValue": NUMBER,
  "minCartValue": NUMBER,
  "expiryDate": "STRING"
}
```

### 4. **PUT /coupons/{id}**
Update a specific coupon by its ID.

**Request Body:**
```json
{
  "code": "STRING",          // Optional
  "discountType": "STRING",  // Optional
  "discountValue": NUMBER,    // Optional
  "minCartValue": NUMBER,     // Optional
  "expiryDate": "STRING"     // Optional
}
```

**Response:**
```json
{
  "id": "STRING",
  "message": "Coupon updated successfully."
}
```

### 5. **DELETE /coupons/{id}**
Delete a specific coupon by its ID.

**Response:**
```json
{
  "message": "Coupon deleted successfully."
}
```

### 6. **POST /applicable-coupons**
Fetch all applicable coupons for a given cart and calculate the total discount for each coupon.

**Request Body:**
```json
{
  "cartItems": [
    {
      "productId": "STRING",
      "price": NUMBER,
      "quantity": NUMBER
    },
    ...
  ]
}
```

**Response:**
```json
[
  {
    "couponId": "STRING",
    "code": "STRING",
    "totalDiscount": NUMBER
  },
  ...
]
```

### 7. **POST /apply-coupon/{id}**
Apply a specific coupon to the cart and return the updated cart with discounted prices.

**Request Body:**
```json
{
  "cartItems": [
    {
      "productId": "STRING",
      "price": NUMBER,
      "quantity": NUMBER
    }
  ]
}
```

**Response:**
```json
{
  "cartItems": [
    {
      "productId": "STRING",
      "price": NUMBER,
      "quantity": NUMBER,
      "discountedPrice": NUMBER
    },
    ...
  ],
  "totalDiscount": NUMBER,
  "finalPrice": NUMBER
}
```

## Requirements
- **Node.js** or equivalent backend framework
- **Database**: Use any database to store coupon and cart information (e.g., MongoDB, MySQL, PostgreSQL).

## Error Handling
- All endpoints return appropriate HTTP status codes.
  - `400`: Bad Request
  - `404`: Not Found
  - `500`: Internal Server Error

**Error Response:**
```json
{
  "error": "Error message detailing the issue."
}
```

## License
This project is licensed under the MIT License.
