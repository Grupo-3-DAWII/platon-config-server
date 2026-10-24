# platon-config-server

Config Server que centraliza la configuración de los microservicios de Platon.

## Requisitos

- **Local:** Java 25 (Maven no hace falta, el proyecto incluye `mvnw`).
- **Docker:** Docker con Compose.
- `platon-eureka-server` en ejecución, para que el Config Server se registre.

## Ejecutar

Desde la carpeta del proyecto:

```bash
# Con Maven Wrapper (Linux/macOS)
./mvnw spring-boot:run

# Con Maven Wrapper (Windows)
.\mvnw.cmd spring-boot:run

# Con Docker
docker compose up -d --build
```

Disponible en: <http://localhost:8888>

Con Docker, `config-repo/` se monta dentro del contenedor y Eureka se busca en el host (`host.docker.internal:8761`).

## Configuración

| Dato | Valor | Variable para cambiarlo |
| --- | --- | --- |
| Puerto | `8888` | `SERVER_PORT` |
| Eureka | `http://localhost:8761/eureka/` | `EUREKA_SERVER_URL` |
| Repositorio de configuración | `./config-repo` (perfil `native`) | `CONFIG_REPOSITORY_PATH` |

## Archivos en `config-repo/`

Cada cliente conserva solo su `spring.application.name` y la URL del Config Server; todo lo demás vive aquí:

| Archivo | Contenido |
| --- | --- |
| `application.yml` | Común a todos: Eureka y Actuator |
| `platon-api-gateway.yml` | Puerto, rutas, R2DBC y JWT |
| `platon-microservice-account.yml` | Puerto y datasource |
| `platon-microservice-products.yml` | Puerto, datasource, RabbitMQ y usuarios de seguridad |
| `platon-microservice-payments.yml` | Puerto, datasource, JPA, RabbitMQ y flujo de compras |

Los valores usan el formato `${VARIABLE:valor_por_defecto}`, así que se pueden sobrescribir con variables de entorno (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `RABBITMQ_HOST`, etc.). Los valores por defecto coinciden con el proyecto `DATABASE`.

Los microservicios apuntan al Config Server con `CONFIG_SERVER_URL` (por defecto `http://localhost:8888`).

## Verificar

```bash
curl http://localhost:8888/platon-microservice-payments/default
```

Cambia el nombre por el de cualquier otro servicio para ver su configuración.

## Orden recomendado

1. `platon-eureka-server`
2. `platon-config-server`
3. Microservicios
4. `platon-api-gateway`
