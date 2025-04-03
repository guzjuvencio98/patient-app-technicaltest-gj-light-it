# 🩺 Patient Registration API

Aplicación para registrar pacientes de manera segura y escalable, desarrollada en **Java 21 con Spring Boot 3**, siguiendo principios **SOLID**, arquitectura limpia y validaciones robustas.

> Desarrollado como parte de un challenge técnico. Proyecto listo para producción, desplegable vía Docker, con persistencia en MySQL y envío de emails simulados con Mailtrap.

---

## 🚀 Funcionalidades principales

- ✅ Alta de pacientes con validación (`name`, `email`, `phone`)
- ✅ Subida de documento (foto del documento)
- ✅ Confirmación de registro por email (con Mailtrap)
- ✅ Validaciones automáticas con `@Valid`
- ✅ Detección de duplicados (`409 Conflict`)
- ✅ Arquitectura escalable (notificaciones SMS futuras)
- ✅ Docker & Docker Compose para entorno replicable

---

## ⚙️ Tecnologías utilizadas

- Java 21 + Spring Boot 3
- Maven + MapStruct + Hibernate (JPA)
- MySQL 8 + Flyway
- Mailtrap (entorno seguro para mails)
- Docker & Docker Compose
- Postman (testeo de endpoints)

---

## 📦 Endpoints principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST   | `/api/patient` | Registra un nuevo paciente (JSON) |
| POST   | `/api/patient/{id}/document` | Sube documento del paciente (form-data con archivo) |

---

##  Cómo levantar el proyecto con Docker

### ✅ Requisitos
- Docker & Docker Compose
- Cuenta en [Mailtrap](https://mailtrap.io) para simular envíos de email

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/patient-registration-app.git
cd patientapp/docker
```

### 2. Configurar credenciales Mailtrap

Editá el archivo `docker-compose.yml` y reemplazá las credenciales:

Se dejan por default las generadas para esta prueba. 

```yaml
SPRING_MAIL_USERNAME: tu_username_mailtrap
SPRING_MAIL_PASSWORD: tu_password_mailtrap
```

### 3. Levantar el proyecto

```bash
docker-compose down -v
docker-compose up --build 
mvn spring-boot:run
```

La app estará disponible en: [http://localhost:8080](http://localhost:8080)


---

##  Cómo testear los endpoints

Se deja en carpeta "postman" un .json para importar a Postman los endpoints explicados a continuación.

### 🔹 Alta de paciente (válido)

```http
POST /api/patients
Content-Type: application/json
```
Body:
```json
{
  "name": "Lucía",
  "email": "lucia@example.com",
  "phone": "+5491122334455"
}
```
✅ Devuelve: `201 Created`

### 🔹 Validaciones fallidas
```json
{
  "name": "",
  "email": "",
  "phone": ""
}
```
✅ Devuelve: `400 Bad Request` con detalle de errores

### 🔹 Email duplicado
✅ Devuelve: `409 Conflict` con mensaje como:
```json
{
  "error": "Patient with email lucia@example.com already exists"
}
```

### 🔹 Upload de documento

```http
POST /api/patients/{id}/document
```
Body (form-data):
- key: `file`, type: file

✅ Devuelve: `200 OK` → "Document uploaded successfully"

---

##  Estructura del proyecto

```
patient-registration-app/
├── src/
│   └── main/java/com/lightit/patientapp/
│       ├── controller/
│       ├── service/

│       ├── dto/
│       ├── entity/
│       ├── mapper/
│       └── exception/
├── docker/
│   ├── Dockerfile
│   └── docker-compose.yml
├── pom.xml
```

---

## 🐳 Dockerfile y sincronización con MySQL

> Para evitar errores por sincronización temprana con la base de datos, se incorporó un `sleep` temporal.

```dockerfile
ENTRYPOINT ["sh", "-c", "sleep 20 && java -jar app.jar"]
```

Esto garantiza que MySQL esté operativo antes de que Spring Boot intente conectarse.

---

## 📬 Configuración Mailtrap

1. Crear una cuenta en [https://mailtrap.io](https://mailtrap.io)
2. Ir a tu Inbox de testing
3. Copiar usuario y contraseña SMTP
4. Reemplazar en el `docker-compose.yml`:

```yaml
SPRING_MAIL_USERNAME: TU_USERNAME
SPRING_MAIL_PASSWORD: TU_PASSWORD
```

Los correos de confirmación aparecerán en tu inbox virtual.

---

## 📚 Notas finales

- ✔️ Código estructurado con principios SOLID
- ✔️ Validaciones automáticas con Jakarta Validation
- ✔️ Arquitectura preparada para ampliaciones futuras (SMS, S3, etc.)

---

## 👨‍💻 Autor

Desarrollado por [Tu Nombre]  
[GitHub](https://github.com/tu-usuario) • [LinkedIn](https://linkedin.com/in/tu-linkedin)

