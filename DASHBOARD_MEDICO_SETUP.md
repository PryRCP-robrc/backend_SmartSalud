# Dashboard Médico — Guía de levantamiento

Esta rama (`edu/dashboard-medico`) agrega el módulo completo del **Portal Médico**:
endpoints REST con Spring Security + JWT y login específico para médicos.

## 1. Base de datos PostgreSQL

### Opción A — Docker Compose (RECOMENDADA, todo automático)

```bash
# 1. Copia el script maestro al directorio de inicialización
cp "BD Clinica 2.sql" sql/init/01-schema.sql

# 2. Levanta Postgres — ejecuta los scripts solos en orden
docker compose up -d

# Listo. La BD policlinico_vidasalud queda creada, poblada y con auth de médico.
```

`compose.yaml` ya define `POSTGRES_DB=policlinico_vidasalud`,
usuario `postgres` / password `root` y monta `sql/init/` en
`/docker-entrypoint-initdb.d/` para auto-ejecutar `01-schema.sql` y
`02-medico-auth.sql` solo la primera vez.

Para resetear desde cero:
```bash
docker compose down -v && docker compose up -d
```

### Opción B — psql nativo

```bash
psql -U postgres -c "CREATE DATABASE policlinico_vidasalud;"
psql -U postgres -d policlinico_vidasalud -f "BD Clinica 2.sql"
psql -U postgres -d policlinico_vidasalud -f sql/add-medico-auth.sql
```

## 2. Backend (Spring Boot)

```bash
./gradlew bootRun
```

Por defecto se conecta a `jdbc:postgresql://localhost:5432/policlinico_vidasalud`
con usuario `postgres` / password `root`. Puedes sobrescribir con variables de
entorno: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `CORS_ORIGINS`.

El backend queda en **http://localhost:8080**.

## 3. Endpoints del Dashboard Médico

| Método | Ruta | Auth | Descripción |
|--------|------|------|-------------|
| POST | `/api/auth/medico/login` | público | Login del médico — devuelve JWT + datos |
| GET | `/api/auth/medico/me` | `ROLE_MEDICO` | Perfil del médico autenticado |
| GET | `/api/medico/{id}` | MEDICO/RECEPCIONISTA/ADMIN | Datos básicos del médico |
| GET | `/api/medico/{id}/citas` | MEDICO/RECEPCIONISTA/ADMIN | Historial de citas |
| GET | `/api/medico/{id}/agenda?fecha=YYYY-MM-DD` | MEDICO/RECEPCIONISTA/ADMIN | Agenda de un día |
| GET | `/api/medico/{id}/agenda?desde=...&hasta=...` | MEDICO/RECEPCIONISTA/ADMIN | Agenda en rango |
| GET | `/api/medico/{id}/pacientes` | MEDICO/RECEPCIONISTA/ADMIN | Pacientes atendidos |

## 4. Credenciales de prueba (script seed)

| Email | Password | Rol |
|-------|----------|-----|
| `c.mendoza@vidasalud.pe` | `Password123` | Médico (Medicina General) |
| `l.paredes@vidasalud.pe` | `Password123` | Médico (Cardiología) |
| `m.flores@vidasalud.pe` | `Password123` | Médico (Pediatría) |

Todos los médicos seed quedaron con el mismo password BCrypt. Puedes cambiarlo
desde el endpoint `/api/auth/change-password` o directamente en la BD.

## 5. Probar con curl

```bash
# Login
curl -X POST http://localhost:8080/api/auth/medico/login \
  -H "Content-Type: application/json" \
  -d '{"email":"c.mendoza@vidasalud.pe","password":"Password123"}'

# Usar el accessToken devuelto
TOKEN="eyJhbGciOi..."
curl http://localhost:8080/api/medico/1/citas \
  -H "Authorization: Bearer $TOKEN"
```

## 6. Arquitectura

Arquitectura hexagonal estricta:

```
domain/model/medico          → Medico (POJO sin anotaciones JPA)
domain/model/cita            → Cita, EstadoCita
domain/ports/output          → MedicoRepositoryPort, CitaRepositoryPort
application/ports/input      → MedicoInputPort, MedicoAuthInputPort
application/service          → MedicoService, MedicoAuthService
infrastructure/adapters
  /input/rest/medico         → MedicoController
  /input/rest/auth           → MedicoAuthController
  /output/persistence
    /entity                  → MedicoEntity, CitaEntity, EspecialidadEntity
    /repository              → JpaMedicoRepository, JpaCitaRepository
    /mapper                  → MedicoMapper, CitaMapper
    /adapter                 → MedicoRepositoryAdapter, CitaRepositoryAdapter
shared/dto/response/medico   → MedicoResponse, CitaMedicoResponse, PacienteMedicoResponse
shared/dto/response/auth     → MedicoAuthResponse
```

## 7. Spring Security

- `CustomUserDetailsService` ahora es **polimórfico**: busca primero en paciente,
  luego en médico, y construye `UsuarioPrincipal` con el rol correspondiente.
- `JwtAuthenticationFilter` solo deja pasar sin token los endpoints listados en
  `PUBLIC_PATHS` (incluye `/api/auth/medico/login`).
- `SecurityConfig` declara reglas explícitas con `@PreAuthorize` por rol en
  controllers.
- `MedicoController.@PreAuthorize` exige rol `MEDICO`, `RECEPCIONISTA` o `ADMIN`.
