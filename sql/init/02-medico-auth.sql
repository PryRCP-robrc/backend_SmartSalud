-- =====================================================================
--  02-medico-auth.sql
--  Habilita el login del medico y normaliza el password de prueba.
--  Se ejecuta automaticamente despues de 01-schema.sql en Docker.
--
--  Usa pgcrypto (crypt + gen_salt) que ya esta habilitado por el script
--  maestro, de modo que el hash BCrypt SIEMPRE corresponde a "Password123".
-- =====================================================================

-- 1) Columna password_hash en medico (idempotente)
ALTER TABLE medico
    ADD COLUMN IF NOT EXISTS password_hash VARCHAR(255);

-- 2) Password BCrypt real para "Password123" en TODOS los medicos
UPDATE medico
SET    password_hash = crypt('Password123', gen_salt('bf', 10));

-- 3) Password BCrypt real para "Password123" en TODOS los pacientes seed
--    (asi el login de paciente demo tambien funciona out-of-the-box)
UPDATE paciente
SET    password_hash = crypt('Password123', gen_salt('bf', 10));

-- 4) Asignar rol MEDICO a todos los medicos que aun no lo tengan
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

-- 5) Verificacion
DO $$
DECLARE
    total_medicos          INTEGER;
    medicos_con_password   INTEGER;
    total_pacientes        INTEGER;
BEGIN
    SELECT COUNT(*) INTO total_medicos        FROM medico;
    SELECT COUNT(*) INTO medicos_con_password FROM medico WHERE password_hash IS NOT NULL;
    SELECT COUNT(*) INTO total_pacientes      FROM paciente;

    RAISE NOTICE '-----------------------------------------------';
    RAISE NOTICE '  Auth configurada (medicos y pacientes)';
    RAISE NOTICE '-----------------------------------------------';
    RAISE NOTICE 'Medicos:           %  (con password: %)', total_medicos, medicos_con_password;
    RAISE NOTICE 'Pacientes:         %', total_pacientes;
    RAISE NOTICE '-----------------------------------------------';
    RAISE NOTICE 'Credenciales de prueba (password: Password123):';
    RAISE NOTICE '  Medico:   c.mendoza@vidasalud.pe';
    RAISE NOTICE '  Paciente: juan.perez@email.com';
    RAISE NOTICE '-----------------------------------------------';
END $$;
