# 🐳 SmartSalud en Docker — Stack completo

Levanta **PostgreSQL + Backend (Spring Boot) + Frontend (Next.js)** con un solo comando.
No necesitas instalar Java, Node ni Postgres en tu máquina — solo Docker Desktop.

---

## Requisitos

- **Docker Desktop** corriendo (la ballena verde en la bandeja del sistema).
- Los **dos repos clonados como carpetas hermanas**:

```
una-carpeta/
├── backend_SmartSalud/      ← este repo
└── Proy.Integrador_Front/   ← repo del frontend
```

Si aún no los tienes así:
```bash
git clone -b edu https://github.com/PryRCP-robrc/backend_SmartSalud.git
git clone -b edu https://github.com/Apuentecaa/Proy.Integrador_Front.git
```

---

## Paso único de preparación

Copia el script maestro de la base de datos (el archivo **`BD Clinica 2.sql`** que te
compartieron) dentro de `sql/init/` con el nombre `01-schema.sql`:

```bash
# Windows (PowerShell), desde la carpeta backend_SmartSalud
copy "C:\ruta\donde\tengas\BD Clinica 2.sql" sql\init\01-schema.sql

# Mac / Linux
cp "/ruta/BD Clinica 2.sql" sql/init/01-schema.sql
```

> El nombre debe empezar con `01-` para que se ejecute antes que `02-medico-auth.sql`.

---

## Arrancar TODO

Desde la carpeta `backend_SmartSalud`:

```bash
docker compose up -d --build
```

La primera vez tarda unos minutos (descarga imágenes, compila backend y frontend).
Cuando termine, abre el navegador:

| Servicio | URL |
|----------|-----|
| **Frontend** | http://localhost:3000 |
| **Login médico** | http://localhost:3000/medico/login |
| **Login paciente** | http://localhost:3000/login |
| **Backend (API)** | http://localhost:8080 |

### Credenciales de prueba (password: `Password123`)

| Rol | Email |
|-----|-------|
| Médico | `c.mendoza@vidasalud.pe` |
| Paciente | `juan.perez@email.com` |

---

## Comandos útiles

```bash
# Ver el estado de los 3 contenedores
docker compose ps

# Ver logs en vivo (todos)
docker compose logs -f

# Ver logs de un servicio
docker compose logs -f backend
docker compose logs -f frontend
docker compose logs -f postgres

# Detener todo (conserva los datos)
docker compose stop

# Apagar y borrar contenedores (conserva el volumen de datos)
docker compose down

# Apagar y BORRAR TAMBIÉN la base de datos (empezar de cero)
docker compose down -v

# Reconstruir tras cambios de código
docker compose up -d --build
```

---

## ¿Cómo está armado?

```
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│  frontend         │     │  backend          │     │  postgres         │
│  Next.js          │────▶│  Spring Boot      │────▶│  PostgreSQL 15    │
│  puerto 3000      │ API │  puerto 8080      │ JDBC│  puerto 5432      │
└──────────────────┘     └──────────────────┘     └──────────────────┘
        ▲                                                   ▲
        │ navegador (host)                                  │ sql/init/*.sql
        │ http://localhost:8080                             │ (auto-ejecutados)
```

- **postgres**: arranca primero, ejecuta `01-schema.sql` + `02-medico-auth.sql` la
  primera vez. Tiene healthcheck para que el backend espere a que esté listo.
- **backend**: espera a que Postgres esté sano, luego se conecta vía
  `jdbc:postgresql://postgres:5432/...`. `ddl-auto=none`, el esquema lo maneja el SQL.
- **frontend**: el navegador (en tu máquina) llama al backend en
  `http://localhost:8080`, que está publicado por compose.

---

## Problemas comunes

| Síntoma | Solución |
|---------|----------|
| `no such file or directory: ../Proy.Integrador_Front` | Los dos repos no están como hermanos. Clónalos juntos. |
| Login dice "Email o contraseña incorrectos" | Recrea la BD: `docker compose down -v && docker compose up -d --build` |
| El frontend no carga datos | Revisa `docker compose logs backend` — quizá Postgres aún no terminaba de iniciar |
| `port is already allocated` | Tienes otro Postgres/servicio en 5432/8080/3000. Ciérralo o cambia el puerto en compose.yaml |
| Cambié código y no se refleja | `docker compose up -d --build` para reconstruir |

---

## Nota: Docker vs. modo local

- **Docker** (este archivo): todo aislado, no instalas nada, BD `policlinico_vidasalud`.
- **Local** (`start.bat` + Postgres nativo): tu setup actual con BD `policlinico_prueba`.

Ambos modos son independientes y no se pisan. Usa el que prefieras.
