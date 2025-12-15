# Beer API - Spring Boot REST Application

API REST desarrollada con Spring Boot para la gestión de una base de datos de cervezas artesanales. El sistema permite realizar operaciones CRUD completas, búsquedas avanzadas con filtros múltiples, manejo de imágenes y relaciones entre entidades (cervezas, cervecerías, categorías y estilos).

## Características Principales

### Gestión de Cervezas
- **CRUD completo**: Crear, leer, actualizar (PUT y PATCH) y eliminar cervezas
- **Validación de datos**: Validaciones a nivel de campo con Jakarta Bean Validation
- **Relaciones**: Asociación obligatoria con cervecería, categoría y estilo
- **Carga de imágenes**: Subida de archivos con validación de tipo y almacenamiento local

### Búsqueda y Filtrado
- Búsqueda por nombre (case-insensitive)
- Filtrado por rango de ABV (Alcohol by Volume)
- Filtrado por rango de IBU (International Bitterness Units)
- Filtrado por estilo específico
- Combinación de múltiples filtros simultáneos
- Paginación en todos los endpoints de listado

### Paginación
- Soporte completo de paginación con Spring Data
- Endpoint HEAD para obtener el total de registros
- Personalización de tamaño de página y ordenamiento
- Respuestas con metadatos completos (totalElements, totalPages, number, etc.)

### Manejo de Errores
- Exception handling global con @RestControllerAdvice
- Respuestas de error estructuradas con timestamp, status, mensaje y path
- Códigos HTTP apropiados (404, 400, 500)
- Validación de existencia de entidades relacionadas

### Documentación
- Swagger UI interactivo en `/swagger-ui.html`
- Especificación OpenAPI 3.1.0 en `/api-docs`
- Anotaciones detalladas en controladores y DTOs
- Documentación completa en `/docs/API_DOCUMENTATION.md`
- Colección de requests HTTP en `requests.http`

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.7**
  - Spring Data JPA
  - Spring Web
  - Spring Validation
- **Hibernate Validator** - Implementación de Jakarta Bean Validation
- **MapStruct 1.5.5** - Mapeo entre entidades y DTOs
- **MariaDB** - Base de datos relacional
- **Lombok** - Reducción de código boilerplate
- **SpringDoc OpenAPI 2.8.3** - Documentación Swagger/OpenAPI
- **Docker Compose** - Orquestación de contenedores

## Requisitos Previos

- Java 21 o superior
- Maven 3.6+
- Docker y Docker Compose
- Puerto 8080 disponible (aplicación)
- Puerto 3306 disponible (MariaDB)
- Puerto 8081 disponible (Adminer - opcional)

## Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone <repository-url>
cd kata-api-cervezas-spring-boot-obezeq
```

### 2. Iniciar la base de datos

```bash
docker-compose up -d
```

Esto levantará:
- MariaDB en el puerto 3306 con la base de datos `kata-api` precargada (5,900+ cervezas)
- Adminer en el puerto 8081 para administración web de la base de datos

### 3. Compilar el proyecto

```bash
./mvnw clean compile
```

### 4. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## Configuración

La configuración de la aplicación se encuentra en `src/main/resources/application.properties`:

```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/kata-api
spring.datasource.username=root
spring.datasource.password=Super

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Subida de archivos
file.upload-dir=uploads/images
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB

# Swagger/OpenAPI
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

## Documentación de la API

### Swagger UI (Recomendado)

Acceder a la documentación interactiva:

**[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

### Documentación Completa

Consultar la guía completa en:

**[docs/API_DOCUMENTATION.md](docs/API_DOCUMENTATION.md)**

### OpenAPI JSON

Especificación OpenAPI disponible en:

**[http://localhost:8080/api-docs](http://localhost:8080/api-docs)**

## Endpoints Principales

### Base URL
```
http://localhost:8080/api/v1
```

### Cervezas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/beers` | Listar todas las cervezas (paginado) |
| HEAD | `/beers` | Obtener el total de cervezas en X-Total-Count |
| GET | `/beer/{id}` | Obtener una cerveza por ID |
| POST | `/beer` | Crear una nueva cerveza |
| PUT | `/beer/{id}` | Actualizar completamente una cerveza |
| PATCH | `/beer/{id}` | Actualizar parcialmente una cerveza |
| DELETE | `/beer/{id}` | Eliminar una cerveza |
| GET | `/beers/search` | Buscar cervezas con filtros |
| POST | `/beer/{id}/image` | Subir imagen de una cerveza |

### Cervecerías

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/breweries` | Listar todas las cervecerías |
| GET | `/brewerie/{id}` | Obtener una cervecería por ID |

### Categorías

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/categories` | Listar todas las categorías |
| GET | `/categorie/{id}` | Obtener una categoría por ID |

### Estilos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/styles` | Listar todos los estilos |
| GET | `/style/{id}` | Obtener un estilo por ID |

## Ejemplos de Uso

### Crear una cerveza

```bash
curl -X POST "http://localhost:8080/api/v1/beer" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "IPA Artesanal",
    "abv": 6.5,
    "ibu": 65.0,
    "srm": 12.0,
    "upc": 0,
    "filepath": "",
    "descript": "IPA con notas cítricas",
    "breweryId": 1,
    "categoryId": 1,
    "styleId": 10
  }'
```

### Buscar cervezas

```bash
# Por nombre
curl "http://localhost:8080/api/v1/beers/search?name=ipa&page=0&size=20"

# Por rango de ABV
curl "http://localhost:8080/api/v1/beers/search?minAbv=6.0&maxAbv=8.0"

# Filtros combinados
curl "http://localhost:8080/api/v1/beers/search?name=ale&minAbv=5.0&maxAbv=7.0&styleId=10"
```

### Obtener total de cervezas

```bash
curl -I "http://localhost:8080/api/v1/beers"
# Header: X-Total-Count: 5902
```

### Actualización parcial

```bash
curl -X PATCH "http://localhost:8080/api/v1/beer/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Nombre Actualizado",
    "abv": 7.5
  }'
```

### Subir imagen

```bash
curl -X POST "http://localhost:8080/api/v1/beer/1/image" \
  -F "file=@/path/to/image.jpg"
```

## Estructura del Proyecto

```
src/main/java/api/kata/cervezas/
├── config/               # Configuración (OpenAPI, archivos, CORS)
│   ├── OpenAPIConfig.java
│   ├── WebConfig.java
│   └── FileStorageProperties.java
├── dto/                  # Data Transfer Objects
│   ├── BeerDto.java
│   ├── BreweryDto.java
│   ├── CategoryDto.java
│   ├── StyleDto.java
│   ├── ErrorResponse.java
│   └── ValidationErrorResponse.java
├── exception/            # Manejo de excepciones
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── InvalidRelationshipException.java
│   └── ValidationException.java
├── mapper/               # MapStruct mappers
│   ├── BeerMapper.java
│   ├── BreweryMapper.java
│   ├── CategoryMapper.java
│   └── StyleMapper.java
├── model/                # Entidades JPA
│   ├── Beer.java
│   ├── Brewery.java
│   ├── Category.java
│   └── Style.java
├── repository/           # Spring Data JPA repositories
│   ├── BeerRepository.java
│   ├── BreweryRepository.java
│   ├── CategoryRepository.java
│   └── StyleRepository.java
├── service/              # Lógica de negocio
│   ├── BeerService.java
│   ├── BreweryService.java
│   ├── CategoryService.java
│   ├── StyleService.java
│   └── FileStorageService.java
├── specification/        # JPA Specifications para filtrado
│   └── BeerSpecification.java
└── web/                  # Controladores REST
    ├── BeerController.java
    ├── BreweryController.java
    ├── CategoryController.java
    └── StyleController.java
```

## Validaciones

### Campos Requeridos

- `name`: Nombre de la cerveza (no puede estar vacío)
- `breweryId`: ID de la cervecería (debe existir, valor positivo)
- `categoryId`: ID de la categoría (debe existir, valor positivo)
- `styleId`: ID del estilo (debe existir, valor positivo)

### Validaciones de Rango

- `abv`: Mayor o igual a 0
- `ibu`: Mayor o igual a 0
- `srm`: Mayor o igual a 0

### Validaciones de Relaciones

- Antes de crear/actualizar una cerveza, se valida la existencia de brewery, category y style
- Si alguna relación no existe, se devuelve un error 404 con mensaje descriptivo

### Validaciones de Archivos

- Solo se aceptan archivos de tipo imagen (image/*)
- Tamaño máximo: 5MB
- Los archivos se almacenan con nombres UUID para evitar colisiones

## Manejo de Errores

### Formato de Respuesta de Error

```json
{
  "timestamp": "2025-12-15T18:00:00.000000000",
  "status": 404,
  "error": "Not Found",
  "message": "Beer not found with id : '99999'",
  "path": "/api/v1/beer/99999",
  "details": null
}
```

### Errores de Validación

```json
{
  "timestamp": "2025-12-15T18:00:00.000000000",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid request body",
  "path": "/api/v1/beer",
  "fieldErrors": {
    "name": "Name is required",
    "breweryId": "Brewery ID is required",
    "categoryId": "Category ID is required",
    "styleId": "Style ID is required"
  }
}
```

## Testing

### Colección de Requests

El archivo `requests.http` contiene una colección completa de peticiones HTTP para probar todos los endpoints. Compatible con:
- VS Code REST Client extension
- IntelliJ IDEA HTTP Client

### Resultados de Tests

Los resultados de testing manual se encuentran en:
- `TEST_RESULTS.md` - Informe detallado de pruebas realizadas

### Ejecutar Tests Unitarios

```bash
./mvnw test
```

## Base de Datos

### Esquema Principal

- **beers** (5,902 registros): Información de cervezas
- **breweries** (1,423 registros): Cervecerías
- **categories** (11 registros): Categorías de cerveza
- **styles** (141 registros): Estilos de cerveza

### Relaciones

```
Beer *--1 Brewery
Beer *--1 Category
Beer *--1 Style
```

### Acceso a la Base de Datos

Adminer (interfaz web):
- URL: http://localhost:8081
- Sistema: MySQL
- Servidor: mariadb
- Usuario: root
- Contraseña: Super
- Base de datos: kata-api

## Arquitectura

### Patrón de Diseño

La aplicación sigue una arquitectura en capas:

1. **Controller Layer**: Recibe peticiones HTTP, valida entrada básica
2. **Service Layer**: Lógica de negocio, validación de relaciones
3. **Repository Layer**: Acceso a datos con Spring Data JPA
4. **DTO Layer**: Transferencia de datos entre capas
5. **Exception Handler**: Manejo centralizado de errores

### Flujo de una Petición

```
HTTP Request
    ↓
Controller (@Valid, @RequestBody)
    ↓
Service (validación de relaciones, lógica de negocio)
    ↓
Repository (JPA, Specifications)
    ↓
Database
    ↓
Entity → DTO (MapStruct)
    ↓
HTTP Response (JSON)
```

## Características Técnicas Destacadas

### MapStruct

Mapeo automático entre entidades y DTOs con generación de código en tiempo de compilación. Configurado con:
- Componente Spring automático
- Integración con Lombok
- Ignorar relaciones en toEntity (manejadas manualmente en servicios)

### JPA Specifications

Filtrado dinámico con JPA Criteria API:
```java
public static Specification<Beer> withFilters(
    String name,
    Integer styleId,
    Float minAbv,
    Float maxAbv,
    Float minIbu,
    Float maxIbu
)
```

### File Storage

Sistema de almacenamiento de archivos con:
- Nombres UUID para evitar colisiones
- Validación de tipo MIME
- Eliminación de archivos antiguos al actualizar
- Directorio configurable

### Global Exception Handling

Manejo centralizado con @RestControllerAdvice:
- ResourceNotFoundException → 404
- ValidationException → 400
- MethodArgumentNotValidException → 400 con fieldErrors
- Exception → 500

## Optimizaciones

- Paginación por defecto de 20 elementos
- Ordenamiento configurable por cualquier campo
- Lazy loading en relaciones JPA
- Cache de segundo nivel habilitado (Hibernate)
- Índices en campos de búsqueda frecuente

## Solución de Problemas Comunes

### Puerto 8080 en uso

```bash
# Linux/Mac
lsof -ti:8080 | xargs kill -9

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Base de datos no conecta

1. Verificar que Docker esté corriendo: `docker ps`
2. Verificar logs de MariaDB: `docker-compose logs mariadb`
3. Reiniciar contenedor: `docker-compose restart mariadb`

### Errores de compilación de MapStruct

```bash
./mvnw clean compile
```

## Contribución

Este proyecto utiliza:
- Convenciones de código de Spring Boot
- Nomenclatura RESTful para endpoints
- Semantic Versioning

## Licencia

Este proyecto es un ejercicio académico.

## Autor

Desarrollado como kata de Spring Boot para demostrar:
- Arquitectura REST completa
- Manejo de relaciones JPA
- Validación robusta
- Documentación con OpenAPI
- Buenas prácticas de Spring Boot

## Recursos Adicionales

- [Documentación completa de la API](docs/API_DOCUMENTATION.md)
- [Swagger UI](http://localhost:8080/swagger-ui.html)
- [Colección de requests HTTP](requests.http)
- [Resultados de testing](TEST_RESULTS.md)
