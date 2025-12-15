# Beer API Documentation

## Base URL
```
http://localhost:8080/api/v1
```

## Authentication
Currently, no authentication is required.

---

## Endpoints

### Beers

#### 1. Get All Beers (Paginated)
```http
GET /beers?page=0&size=20&sort=name,asc
```

**Query Parameters:**
- `page` (optional): Page number, zero-based (default: 0)
- `size` (optional): Items per page (default: 20)
- `sort` (optional): Sort field and direction (e.g., "name,asc", "abv,desc")

**Example using curl:**
```bash
curl -X GET "http://localhost:8080/api/v1/beers?page=0&size=10" \
     -H "Accept: application/json"
```

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Hocus Pocus",
      "abv": 4.5,
      "ibu": 0,
      "srm": 0,
      "upc": 0,
      "filepath": "",
      "descript": "",
      "breweryId": 812,
      "categoryId": 11,
      "styleId": 116
    }
  ],
  "pageable": {
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "pageNumber": 0,
    "pageSize": 10,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 5914,
  "totalPages": 592,
  "last": false,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "numberOfElements": 10,
  "first": true,
  "empty": false
}
```

---

#### 2. Count Beers
```http
HEAD /beers
```

Returns the total count of beers in the `X-Total-Count` header.

**Example using curl:**
```bash
curl -I -X HEAD "http://localhost:8080/api/v1/beers"
```

**Response Headers:**
```
HTTP/1.1 200
X-Total-Count: 5914
Content-Length: 0
```

---

#### 3. Get Beer by ID
```http
GET /beer/{id}
```

**Path Parameters:**
- `id` (required): Beer ID

**Example using curl:**
```bash
curl -X GET "http://localhost:8080/api/v1/beer/1" \
     -H "Accept: application/json"
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Hocus Pocus",
  "abv": 4.5,
  "ibu": 0,
  "srm": 0,
  "upc": 0,
  "filepath": "",
  "descript": "",
  "breweryId": 812,
  "categoryId": 11,
  "styleId": 116
}
```

**Error Response (404 Not Found):**
```json
{
  "timestamp": "2025-12-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Beer not found with id : '999'",
  "path": "/api/v1/beer/999",
  "details": null
}
```

---

#### 4. Search Beers
```http
GET /beers/search?name=ipa&minAbv=5.0&maxAbv=7.0&styleId=10&page=0&size=20
```

**Query Parameters:**
- `name` (optional): Filter by name (case-insensitive partial match)
- `styleId` (optional): Filter by style ID
- `minAbv` (optional): Minimum ABV (alcohol by volume)
- `maxAbv` (optional): Maximum ABV
- `minIbu` (optional): Minimum IBU (bitterness)
- `maxIbu` (optional): Maximum IBU
- `page`, `size`, `sort`: Pagination parameters (same as GET /beers)

**Example using curl:**
```bash
curl -X GET "http://localhost:8080/api/v1/beers/search?name=ipa&minAbv=5.0&maxAbv=7.0" \
     -H "Accept: application/json"
```

**Response (200 OK):**
Same structure as GET /beers (paginated list)

---

#### 5. Create Beer
```http
POST /beer
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "New IPA",
  "abv": 6.5,
  "ibu": 65.0,
  "srm": 12.0,
  "upc": 12345,
  "filepath": "",
  "descript": "A great American IPA with citrus notes",
  "breweryId": 1,
  "categoryId": 1,
  "styleId": 10
}
```

**Required Fields:**
- `name` (string, not blank)
- `breweryId` (integer, positive, must exist)
- `categoryId` (integer, positive, must exist)
- `styleId` (integer, positive, must exist)

**Example using curl:**
```bash
curl -X POST "http://localhost:8080/api/v1/beer" \
     -H "Content-Type: application/json" \
     -d '{
       "name": "New IPA",
       "abv": 6.5,
       "ibu": 65.0,
       "srm": 12.0,
       "upc": 12345,
       "filepath": "",
       "descript": "A great American IPA with citrus notes",
       "breweryId": 1,
       "categoryId": 1,
       "styleId": 10
     }'
```

**Response (201 Created):**
```json
{
  "id": 5915,
  "name": "New IPA",
  "abv": 6.5,
  "ibu": 65.0,
  "srm": 12.0,
  "upc": 12345,
  "filepath": "",
  "descript": "A great American IPA with citrus notes",
  "breweryId": 1,
  "categoryId": 1,
  "styleId": 10
}
```

**Validation Error Response (400 Bad Request):**
```json
{
  "timestamp": "2025-12-15T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid request body",
  "path": "/api/v1/beer",
  "fieldErrors": {
    "name": "Name is required",
    "breweryId": "Brewery ID is required",
    "abv": "ABV must be non-negative"
  }
}
```

**Resource Not Found Error (404 Not Found):**
```json
{
  "timestamp": "2025-12-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Brewery not found with id : '999'",
  "path": "/api/v1/beer",
  "details": null
}
```

---

#### 6. Update Beer (Full)
```http
PUT /beer/{id}
Content-Type: application/json
```

**Path Parameters:**
- `id` (required): Beer ID

**Request Body:**
All fields must be provided (full update).

```json
{
  "name": "Updated IPA",
  "abv": 7.0,
  "ibu": 70.0,
  "srm": 13.0,
  "upc": 12345,
  "filepath": "",
  "descript": "Updated description with more hops",
  "breweryId": 1,
  "categoryId": 1,
  "styleId": 10
}
```

**Example using curl:**
```bash
curl -X PUT "http://localhost:8080/api/v1/beer/1" \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Updated IPA",
       "abv": 7.0,
       "ibu": 70.0,
       "srm": 13.0,
       "upc": 12345,
       "filepath": "",
       "descript": "Updated description with more hops",
       "breweryId": 1,
       "categoryId": 1,
       "styleId": 10
     }'
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Updated IPA",
  "abv": 7.0,
  "ibu": 70.0,
  "srm": 13.0,
  "upc": 12345,
  "filepath": "",
  "descript": "Updated description with more hops",
  "breweryId": 1,
  "categoryId": 1,
  "styleId": 10
}
```

---

#### 7. Update Beer (Partial)
```http
PATCH /beer/{id}
Content-Type: application/json
```

**Path Parameters:**
- `id` (required): Beer ID

**Request Body:**
Only provide fields you want to update (partial update).

```json
{
  "name": "Partially Updated Name",
  "abv": 7.5
}
```

**Example using curl:**
```bash
curl -X PATCH "http://localhost:8080/api/v1/beer/1" \
     -H "Content-Type: application/json" \
     -d '{"name": "Partially Updated Name", "abv": 7.5}'
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Partially Updated Name",
  "abv": 7.5,
  "ibu": 65.0,
  "srm": 12.0,
  "upc": 12345,
  "filepath": "",
  "descript": "Original description",
  "breweryId": 1,
  "categoryId": 1,
  "styleId": 10
}
```

---

#### 8. Upload Beer Image
```http
POST /beer/{id}/image
Content-Type: multipart/form-data
```

**Path Parameters:**
- `id` (required): Beer ID

**Form Data:**
- `file` (required): Image file (JPG, PNG, GIF, etc.)

**Example using curl:**
```bash
curl -X POST "http://localhost:8080/api/v1/beer/1/image" \
     -F "file=@/path/to/beer-image.jpg"
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "IPA Suprema",
  "abv": 6.5,
  "ibu": 65.0,
  "srm": 12.0,
  "upc": 12345,
  "filepath": "/images/abc123-def456-ghi789.jpg",
  "descript": "A bold IPA",
  "breweryId": 1,
  "categoryId": 1,
  "styleId": 10
}
```

**Validation Error Response (400 Bad Request):**
```json
{
  "timestamp": "2025-12-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "File must be an image",
  "path": "/api/v1/beer/1/image",
  "details": null
}
```

**Image Access:**
After upload, the image can be accessed at:
```
http://localhost:8080/images/abc123-def456-ghi789.jpg
```

---

#### 9. Delete Beer
```http
DELETE /beer/{id}
```

**Path Parameters:**
- `id` (required): Beer ID

**Example using curl:**
```bash
curl -X DELETE "http://localhost:8080/api/v1/beer/1"
```

**Response (204 No Content):**
Empty body (successful deletion)

**Error Response (404 Not Found):**
```json
{
  "timestamp": "2025-12-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Beer not found with id : '999'",
  "path": "/api/v1/beer/999",
  "details": null
}
```

---

### Breweries

#### Get All Breweries
```http
GET /breweries
```

**Example using curl:**
```bash
curl -X GET "http://localhost:8080/api/v1/breweries" \
     -H "Accept: application/json"
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "(512) Brewing Company",
    "address1": "407 Radam, F200",
    "address2": "",
    "city": "Austin",
    "state": "Texas",
    "code": "78745",
    "country": "United States",
    "phone": "512.707.2337",
    "website": "http://512brewing.com/",
    "filepath": "",
    "descript": ""
  }
]
```

---

#### Get Brewery by ID
```http
GET /brewerie/{id}
```

**Example using curl:**
```bash
curl -X GET "http://localhost:8080/api/v1/brewerie/1" \
     -H "Accept: application/json"
```

---

### Categories

#### Get All Categories
```http
GET /categories
```

**Example using curl:**
```bash
curl -X GET "http://localhost:8080/api/v1/categories" \
     -H "Accept: application/json"
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "catName": "British Ale"
  },
  {
    "id": 2,
    "catName": "Irish Ale"
  }
]
```

---

#### Get Category by ID
```http
GET /categorie/{id}
```

**Example using curl:**
```bash
curl -X GET "http://localhost:8080/api/v1/categorie/1" \
     -H "Accept: application/json"
```

---

### Styles

#### Get All Styles
```http
GET /styles
```

**Example using curl:**
```bash
curl -X GET "http://localhost:8080/api/v1/styles" \
     -H "Accept: application/json"
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "styleName": "Classic English-Style Pale Ale",
    "categoryId": 1
  },
  {
    "id": 2,
    "styleName": "English-Style India Pale Ale",
    "categoryId": 1
  }
]
```

---

#### Get Style by ID
```http
GET /style/{id}
```

**Example using curl:**
```bash
curl -X GET "http://localhost:8080/api/v1/style/1" \
     -H "Accept: application/json"
```

---

## Error Response Formats

### Standard Error Format
```json
{
  "timestamp": "2025-12-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Descriptive error message",
  "path": "/api/v1/endpoint",
  "details": null
}
```

**Common HTTP Status Codes:**
- `200 OK` - Request succeeded
- `201 Created` - Resource created successfully
- `204 No Content` - Request succeeded with no response body
- `400 Bad Request` - Invalid request body or parameters
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Unexpected server error

---

### Validation Error Format
```json
{
  "timestamp": "2025-12-15T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid request body",
  "path": "/api/v1/beer",
  "fieldErrors": {
    "field1": "Error message 1",
    "field2": "Error message 2"
  }
}
```

---

## Swagger UI

Interactive API documentation is available at:
```
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON specification:
```
http://localhost:8080/api-docs
```

---

## Data Seeding

The database is pre-populated with:
- **5,914 beers** from the Open Beer Database
- **1,423 breweries** worldwide
- **141 beer styles** across 11 categories
- **11 beer categories** (British Ale, Irish Ale, American Ale, etc.)

All data is initialized on first Docker Compose startup from SQL scripts in `initSQL/` directory.

---

## Examples by Use Case

### Use Case 1: Creating a New Beer

1. **First, get a valid brewery ID:**
```bash
curl -X GET "http://localhost:8080/api/v1/breweries?size=1"
```

2. **Get a valid category ID:**
```bash
curl -X GET "http://localhost:8080/api/v1/categories?size=1"
```

3. **Get a valid style ID:**
```bash
curl -X GET "http://localhost:8080/api/v1/styles?size=1"
```

4. **Create the beer:**
```bash
curl -X POST "http://localhost:8080/api/v1/beer" \
     -H "Content-Type: application/json" \
     -d '{
       "name": "My Custom IPA",
       "abv": 6.5,
       "ibu": 65.0,
       "srm": 12.0,
       "upc": 0,
       "filepath": "",
       "descript": "A custom IPA recipe",
       "breweryId": 1,
       "categoryId": 1,
       "styleId": 10
     }'
```

---

### Use Case 2: Searching for IPAs

```bash
curl -X GET "http://localhost:8080/api/v1/beers/search?name=ipa&minAbv=5.0&maxAbv=8.0&page=0&size=20"
```

---

### Use Case 3: Updating Beer Information

**Partial update (only name and ABV):**
```bash
curl -X PATCH "http://localhost:8080/api/v1/beer/5915" \
     -H "Content-Type: application/json" \
     -d '{"name": "My Updated IPA", "abv": 7.0}'
```

---

### Use Case 4: Uploading and Viewing Beer Image

1. **Upload image:**
```bash
curl -X POST "http://localhost:8080/api/v1/beer/5915/image" \
     -F "file=@beer.jpg"
```

2. **View image in browser:**
```
http://localhost:8080/images/{filename-from-response}
```

---

## Best Practices

1. **Always validate brewery/category/style IDs before creating a beer** - Invalid IDs will return 404 errors
2. **Use pagination for large datasets** - Default page size is 20, adjust as needed
3. **Use PATCH for partial updates** - Only send fields you want to change
4. **Check validation errors** - 400 responses include field-level error details
5. **Handle 404 errors gracefully** - Check if resources exist before updating/deleting

---

## Rate Limiting

Currently, no rate limiting is implemented. For production use, consider implementing rate limiting.

---

## CORS

CORS is not configured by default. If you need to access the API from a web frontend, add CORS configuration.

---

## Database Connection

- **Host:** localhost:3306
- **Database:** kata-api
- **Username:** root
- **Password:** Super
- **Dialect:** MariaDB

Start the database with Docker Compose:
```bash
docker-compose up -d
```

---

## Support

For issues or questions, refer to:
- Swagger UI: http://localhost:8080/swagger-ui.html
- Source code: Check the repository README
