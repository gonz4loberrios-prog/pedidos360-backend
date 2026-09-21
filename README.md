# 📦 Pedidos360 - Backend (BFF)

Servicio **Backend for Frontend** del proyecto Pedidos360: una arquitectura cloud native que expone APIs protegidas de gestión de pedidos, validando tokens JWT emitidos por **Microsoft Entra ID (Azure AD)**.

## 🏗️ Arquitectura de la Solución

La solución sigue un patrón BFF desacoplado en capas independientes:

| Capa | Tecnología | Rol |
|------|-----------|-----|
| Frontend | Angular 17 (SPA) | Interfaz de usuario + MSAL (Microsoft Authentication Library) |
| Backend (este repo) | Spring Boot 3.2 / Java 17 | Resource Server OAuth2 que valida firma, emisor y audiencia del JWT |
| Persistencia | H2 In-Memory | Base de datos relacional de pruebas (`jdbc:h2:mem:pedidosdb`) |

## 🔐 Configuración de Seguridad (Azure AD / Entra ID)

El backend actúa como **Resource Server OAuth2**: valida el JWT recibido contra el *issuer* y *audience* configurados.

| Configuración | Valor (UUID) |
|---------------|--------------|
| Tenant ID | `933fff9c-10ab-4e02-8b55-7683ea857d4b` |
| Backend Client ID (API) | `6f8c376e-3314-4fcf-a01d-97ba44c25721` |
| Backend Audience | `api://6f8c376e-3314-4fcf-a01d-97ba44c25721` |

### Flujo de Autenticación

```
[ Usuario ] ---> (1. Login Request) ---> [ Angular SPA ]
                                              |
                                     (2. Redirect Login)
                                              v
                                     [ Azure AD / Entra ]
                                              |
                                   (3. Retorna Token JWT)
                                              v
[ Backend Spring Boot ] <--- (4. HTTP + Bearer JWT) <--- [ Angular SPA ]
          |
  (5. Valida Issuer & Audience)
          v
[ Respuesta 200 OK / Datos ]
```

## 🗄️ Persistencia (H2)

Base de datos en memoria inicializada automáticamente al arrancar mediante `schema.sql` y `data.sql` (compatibilizados desde el script Oracle/PL-SQL original):

- **OT**: Órdenes de trabajo. `OT_ID` se autogenera con la secuencia `SEQ_OT` con formato `OT-YYYY-NNNNNN`.
- **OT_ITEM**: Ítems/repuestos por orden (subtotal calculado automáticamente).
- **OT_EVENT**: Eventos consumidos de Kafka (payload JSON de auditoría).
- **NOTIFY_LOG**: Registro de notificaciones consumidas de RabbitMQ (payload JSON; canal `email`/`sms`/`push`).
- **V_OT_RESUMEN**: Vista resumen con contador de ítems y subtotal calculado por orden.

Para reinicializar la base con datos de prueba basta con reiniciar la aplicación.

### Capas JPA (entidades y repositorios)

| Componente | Archivo | Descripción |
|-----------|---------|-------------|
| Entidad | `entity/OrdenTrabajo.java` | Mapea la tabla `OT` |
| Entidad | `entity/OtItem.java` | Mapea la tabla `OT_ITEM` (subtotal calculado en solo-lectura) |
| Repositorio | `repository/OrdenTrabajoRepository.java` | Acceso JPA a órdenes de trabajo (Spring Data JPA) |
| Repositorio | `repository/OtItemRepository.java` | Acceso JPA a ítems con `findByOtId()` |

El `PedidoController` consulta las órdenes desde el repositorio (`findAll()`), por lo que el endpoint devuelve datos reales de la base de datos H2.

### Configuración (`src/main/resources/application.yml`)

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:h2:mem:pedidosdb
    driverClassName: org.h2.Driver
    username: sa
    password:
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: none
  sql:
    init:
      mode: always
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://login.microsoftonline.com/933fff9c-10ab-4e02-8b55-7683ea857d4b/v2.0
          audiences: api://6f8c376e-3314-4fcf-a01d-97ba44c25721
```

## 🔌 Endpoints

| Método | Ruta | Descripción | Acceso |
|--------|------|-------------|--------|
| `GET` | `/api/pedidos` | Lista de pedidos del usuario autenticado, leídos desde el repositorio JPA | Bearer JWT (autenticado) |

## ▶️ Ejecución

**Requisitos:** Java 17, Maven 3.

```bash
mvn spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`.

## 🧑‍💻 Autores

- Gonzalo Berríos
- Cristian Cerda

**Asignatura:** Cloud Native