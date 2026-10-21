# platon-config-server

Config Server para centralizar la configuracion de los microservicios de Platon.

## Requisitos

- Java 21 o superior
- Maven 3.9 o superior
- `platon-eureka-server` ejecutandose para registrarse en Eureka

## Ejecutar

```bash
.\mvnw.cmd spring-boot:run
```

El Config Server queda disponible en:

```text
http://localhost:8888
```

## Configuracion

- Nombre de aplicacion: `platon-config-server`
- Puerto: `8888`
- Perfil activo: `native`
- Repositorio local de configuraciones: `./config-repo`
- Eureka Server: `http://localhost:8761/eureka/`

## Configuraciones centralizadas

Los clientes solo conservan su `spring.application.name` y la ruta del Config Server. El resto de configuracion vive en:

- `config-repo/application.yml`: configuracion comun, como Eureka y Actuator
- `config-repo/platon-api-gateway.yml`: puerto, rutas, R2DBC y JWT del gateway
- `config-repo/platon-microservice-account.yml`: puerto y datasource del microservicio account
- `config-repo/platon-microservice-payments.yml`: puerto, PostgreSQL, JPA, RabbitMQ y flujo de compras de Payments

Puedes cambiar la ruta del repositorio de configuraciones sin editar el codigo:

```bash
$env:CONFIG_REPOSITORY_PATH="file:C:/config/platon"
.\mvnw.cmd spring-boot:run
```

## Orden recomendado

1. Levantar `platon-eureka-server`
2. Levantar `platon-config-server`
3. Levantar los microservicios
4. Levantar `platon-api-gateway`

## Prueba rapida

Con un archivo `config-repo/application.yml`, puedes consultar la configuracion con:

```bash
curl http://localhost:8888/application/default
```

## Configuracion de Payments

Payments usa `spring.application.name=platon-microservice-payments` e importa obligatoriamente este Config Server mediante `CONFIG_SERVER_URL` (por defecto `http://localhost:8888`). Su YAML local no contiene ajustes de infraestructura.

```bash
curl http://localhost:8888/platon-microservice-payments/default
```

Su archivo especifico conserva Eureka desactivado por defecto, Hibernate en modo validacion y Spring SQL Init desactivado. Las tablas y migraciones pertenecen a `../DATABASE`. Los valores de conexion pueden ajustarse con `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `RABBITMQ_HOST`, `RABBITMQ_PORT`, `RABBITMQ_USERNAME` y `RABBITMQ_PASSWORD`; el timeout usa `PURCHASE_VALIDATION_TIMEOUT_MS`.