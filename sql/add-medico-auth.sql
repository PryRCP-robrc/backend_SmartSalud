-- =====================================================================
--  add-medico-auth.sql
--  Habilita el login del médico añadiendo la columna password_hash
--  y poblando los médicos de prueba con una contraseña conocida.
--
--  Requiere haber ejecutado primero: BD Clinica 2.sql
--
--  Ejecutar:
--    psql -U postgres -d policlinico_vidasalud -f add-medico-auth.sql
-- =====================================================================

-- 1) Agregar columna password_hash a la tabla medico (idempotente)
ALTER TABLE medico
    ADD COLUMN IF NOT EXISTS password_hash VARCHAR(255);

-- 2) Poblar password_hash en los médicos seed con BCrypt de "Password123"
--    (mismo hash que ya usan los pacientes seed)
UPDATE medico
SET    password_hash = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'
WHERE  password_hash IS NULL;

-- 3) Asegurar que los médicos 4 al 8 también tengan rol MEDICO asignado
--    (el script base solo asigna a los IDs 1, 2 y 3)
INSERT INTO usuario_rol (entidad, entidad_id, rol_id)
SELECT 'MEDICO', m.id, r.id
FROM   medico m
CROSS  JOIN rol r
WHERE  r.nombre = 'MEDICO'
  AND  NOT EXISTS (
         SELECT 1 FROM usuario_rol ur
         WHERE  ur.entidad = 'MEDICO'
           AND  ur.entidad_id = m.id
           AND  ur.rol_id    = r.id
       );

-- 4) Verificación
DO $$
DECLARE
    total_medicos          INTEGER;
    medicos_con_password   INTEGER;
    medicos_con_rol        INTEGER;
BEGIN
    SELECT COUNT(*) INTO total_medicos        FROM medico;
    SELECT COUNT(*) INTO medicos_con_password FROM medico WHERE password_hash IS NOT NULL;
    SELECT COUNT(DISTINCT entidad_id) INTO medicos_con_rol
      FROM usuario_rol WHERE entidad = 'MEDICO';

    RAISE NOTICE '-----------------------------------------------';
    RAISE NOTICE '  Auth medico configurada';
    RAISE NOTICE '-----------------------------------------------';
    RAISE NOTICE 'Medicos totales:           %', total_medicos;
    RAISE NOTICE 'Con password_hash:         %', medicos_con_password;
    RAISE NOTICE 'Con rol MEDICO asignado:   %', medicos_con_rol;
    RAISE NOTICE '-----------------------------------------------';
    RAISE NOTICE 'Credenciales de prueba:';
    RAISE NOTICE '  Email:    c.mendoza@vidasalud.pe';
    RAISE NOTICE '  Password: Password123';
    RAISE NOTICE '-----------------------------------------------';
END $$;
