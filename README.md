# User API - Registro de Usuarios

API REST desarrollada en **Spring Boot** para el registro de usuarios, cumpliendo reglas de validación, persistencia en base de datos en memoria y generación de token JWT.

---

## Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Validation
- Spring Security (configuración personalizada)
- H2 Database (en memoria)
- JWT (Json Web Token)
- Swagger / OpenAPI
- Lombok
- Gradle

---

## Arquitectura

La aplicación sigue una arquitectura en capas:
# User API - Registro de Usuarios

API REST desarrollada en **Spring Boot** para el registro de usuarios, cumpliendo reglas de validación, persistencia en base de datos en memoria y generación de token JWT.

---

## Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Validation
- Spring Security (configuración personalizada)
- H2 Database (en memoria)
- JWT (Json Web Token)
- Swagger / OpenAPI
- Lombok
- Gradle

---

## Arquitectura

La aplicación sigue una arquitectura en capas: Controller → Service → Repository → Database



## Capas:

- Controller: expone endpoints REST
- Service: lógica de negocio
- Repository: acceso a datos con JPA
- DTOs: contratos de entrada/salida
- Entity: modelo de base de datos
- Config: configuración (Security, Swagger, JWT)
- Exception Handler: manejo global de errores

---

## Modelo de Datos

### User
- id (UUID)
- name
- email (único)
- password (encriptado)
- created
- modified
- lastLogin
- token
- isActive

### Phone
- id
- number
- citycode
- contrycode
- user_id (FK)

Relación:
### Capas:

- **Controller**: expone endpoints REST
- **Service**: lógica de negocio
- **Repository**: acceso a datos con JPA
- **DTOs**: contratos de entrada/salida
- **Entity**: modelo de base de datos
- **Config**: configuración (Security, Swagger, JWT)
- **Exception Handler**: manejo global de errores

---

## 📊 Modelo de Datos

### User
- id (UUID)
- name
- email (único)
- password (encriptado)
- created
- modified
- lastLogin
- token
- isActive

### Phone
- id
- number
- citycode
- contrycode
- user_id (FK)

Relación:User 1 --- N Phone



---

## Seguridad

- Se utiliza `BCryptPasswordEncoder` para encriptar contraseñas
- Generación de token JWT al registrar usuario
- Seguridad HTTP deshabilitada para simplificar pruebas

---

## Configuración

### application.yaml

yaml
spring:
  datasource:
    url: jdbc:h2:mem:userdb

server:
  port: 8090


## Ejecución del proyecto

./gradlew bootRun


## Endpoint principal / Registrar usuario

POST /api/users

 - Request
{
"name": "Daniel Gallo",
"email": "daniel@gallo.org",
"password": "Abcde12",
"phones": [
{
"number": "1234567",
"citycode": "1",
"contrycode": "57"
}
]
}
Response
{
"id": "uuid",
"created": "fecha",
"modified": "fecha",
"last_login": "fecha",
"token": "jwt",
"isactive": true
}

### Validaciones

- Email formato válido
- Password: al menos 1 mayúscula letras minúsculas mínimo 2 números 

- Email único
 Manejo de errores

  - Código	Descripción
    - 400	Datos inválidos
    - 409	Email ya registrado
    - 500	Error interno

Ejemplo:

{
"mensaje": "El correo ya registrado"
}


## Testing

- Ejecutar: ./gradlew test

Incluye pruebas para:

- Registro exitoso
- Email duplicado
- Validaciones de email
- Validaciones de password



 ## Swagger

  Disponible en:

- http://localhost:8090/swagger-ui.html
  
H2 Console

- http://localhost:8090/h2-console


 - Credenciales:

   - JDBC: jdbc:h2:mem:userdb
   - user: sa
   - password: vacío

---

## Conclusión 

Esta API proporciona un sistema básico de registro de usuarios con validaciones, seguridad y persistencia en memoria. Es una base sólida para construir funcionalidades adicionales como autenticación, gestión de usuarios, etc.


## Diagrama de Architectura

- (docs/images/arquitectura.png)

-  Editable en Figma:
https://www.figma.com/board/1YrChYm1n06NpiHZZtKV7U/Arquitectura-API-Registro-Usuarios?node-id=0-1&t=qrhboQsMyWnWMI6w-1

---

##  Modelo de Datos

- (docs/images/modelo-datos.png)

## Editable en Figma:
https://www.figma.com/board/WBux4N9iKCb1d5NjbGYJwA/Relacion-Usuario-Telefonos?node-id=0-1&t=rGzVos8AC91c64WY-1

