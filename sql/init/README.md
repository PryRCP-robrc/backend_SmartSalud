# Inicialización automática de PostgreSQL

Los archivos `.sql` en este directorio se ejecutan **automáticamente en orden
alfabético** la primera vez que se levanta el contenedor Postgres con
`docker compose up`.

## Setup en 2 pasos

### 1. Coloca el script maestro como `01-schema.sql`

Copia el archivo **`BD Clinica 2.sql`** (el que te compartieron) a este
directorio con el nombre `01-schema.sql`:

```bash
cp "BD Clinica 2.sql" sql/init/01-schema.sql
```

⚠️ El nombre debe empezar con `01-` para que se ejecute antes que el script
de auth del médico.

⚠️ Importante: dentro del script, **elimina o comenta** la línea inicial
`CREATE DATABASE policlinico_vidasalud;` porque Docker ya crea la BD desde
la variable `POSTGRES_DB` del `compose.yaml`. Si la dejas, no falla (Postgres
ignora el comando porque ya está conectado a esa BD), pero conviene quitarla.

### 2. Levanta todo

```bash
docker compose up -d
```

La primera vez tarda ~10 segundos:
1. Postgres arranca y crea la BD `policlinico_vidasalud`.
2. Ejecuta `01-schema.sql` (todas las tablas + datos seed).
3. Ejecuta `02-medico-auth.sql` (agrega `password_hash` y BCrypt para médicos).

A partir de ahí, los scripts NO se vuelven a correr (Postgres detecta que ya
existe un volumen inicializado). Si quieres re-ejecutarlos:

```bash
docker compose down -v   # borra el volumen
docker compose up -d     # vuelve a inicializar
```

## Archivos esperados

```
sql/init/
├── 01-schema.sql         ← debes copiar aquí "BD Clinica 2.sql"
├── 02-medico-auth.sql    ← incluido en el repo (no tocar)
└── README.md             ← este archivo
```
