-- =====================================================================
--  03-seed-extra.sql
--  Datos adicionales: completa medicos en TODAS las especialidades
--  y agrega pacientes nuevos. Se ejecuta despues de 02-medico-auth.sql.
--
--  Passwords con crypt() -> "Password123" (login funcional out-of-the-box).
--  Idempotente: ON CONFLICT DO NOTHING / NOT EXISTS.
-- =====================================================================

-- ---------------------------------------------------------------------
-- 1) MEDICOS faltantes por especialidad (cada una queda con >= 2)
--    Especialidades: 1 Med.General 2 Cardio 3 Pediatria 4 Gineco
--    5 Trauma 6 Derma 7 Neuro 8 Oftalmo 9 Psico 10 Nutri 11 Endo 12 Gastro
-- ---------------------------------------------------------------------
INSERT INTO medico (dni, nombres, apellidos, cmp, especialidad_id, telefono, email, anios_experiencia, password_hash) VALUES
    -- Refuerzo de especialidades con un solo medico
    ('90000001','Carmen','Salazar Rios',   'CMP-90001', 3,  '900000001','c.salazar@vidasalud.pe',  11, crypt('Password123', gen_salt('bf',10))),
    ('90000002','Elena', 'Vega Campos',     'CMP-90002', 4,  '900000002','e.vega@vidasalud.pe',     13, crypt('Password123', gen_salt('bf',10))),
    ('90000003','Raul',  'Ibanez Cruz',     'CMP-90003', 5,  '900000003','r.ibanez@vidasalud.pe',    9, crypt('Password123', gen_salt('bf',10))),
    ('90000004','Diana', 'Ponce Lara',      'CMP-90004', 6,  '900000004','d.ponce@vidasalud.pe',    10, crypt('Password123', gen_salt('bf',10))),
    -- Neurologia
    ('90000005','Andres','Caceres Pinto',   'CMP-90005', 7,  '900000005','a.caceres@vidasalud.pe',  16, crypt('Password123', gen_salt('bf',10))),
    ('90000006','Monica','Reyes Salas',     'CMP-90006', 7,  '900000006','m.reyes@vidasalud.pe',     8, crypt('Password123', gen_salt('bf',10))),
    -- Oftalmologia
    ('90000007','Felipe','Aguirre Nunez',   'CMP-90007', 8,  '900000007','f.aguirre@vidasalud.pe',  12, crypt('Password123', gen_salt('bf',10))),
    ('90000008','Beatriz','Cordero Vila',   'CMP-90008', 8,  '900000008','b.cordero@vidasalud.pe',   7, crypt('Password123', gen_salt('bf',10))),
    -- Psicologia
    ('90000009','Sergio','Maldonado Ruiz',  'CMP-90009', 9,  '900000009','s.maldonado@vidasalud.pe',14, crypt('Password123', gen_salt('bf',10))),
    ('90000010','Valeria','Espinoza Leon',  'CMP-90010', 9,  '900000010','v.espinoza@vidasalud.pe',  6, crypt('Password123', gen_salt('bf',10))),
    -- Nutricion
    ('90000011','Tomas', 'Bravo Salas',     'CMP-90011', 10, '900000011','t.bravo@vidasalud.pe',    10, crypt('Password123', gen_salt('bf',10))),
    ('90000012','Gabriela','Mejia Rojas',   'CMP-90012', 10, '900000012','g.mejia@vidasalud.pe',     9, crypt('Password123', gen_salt('bf',10))),
    -- Endocrinologia
    ('90000013','Oscar', 'Lozano Diaz',     'CMP-90013', 11, '900000013','o.lozano@vidasalud.pe',   15, crypt('Password123', gen_salt('bf',10))),
    ('90000014','Natalia','Ferrer Soto',    'CMP-90014', 11, '900000014','n.ferrer@vidasalud.pe',    8, crypt('Password123', gen_salt('bf',10))),
    -- Gastroenterologia
    ('90000015','Hector','Palma Rios',      'CMP-90015', 12, '900000015','h.palma@vidasalud.pe',    13, crypt('Password123', gen_salt('bf',10))),
    ('90000016','Lorena','Quispe Mamani',   'CMP-90016', 12, '900000016','l.quispe@vidasalud.pe',    7, crypt('Password123', gen_salt('bf',10)))
ON CONFLICT DO NOTHING;

-- Asignar todos los medicos nuevos a la Sede Central (id 1)
INSERT INTO medico_sede (medico_id, sede_id)
SELECT m.id, 1 FROM medico m WHERE m.cmp LIKE 'CMP-9%'
ON CONFLICT DO NOTHING;

-- Tarifa de consulta para los medicos nuevos
INSERT INTO tarifa_consulta (medico_id, sede_id, monto)
SELECT m.id, 1, 100.00 FROM medico m
WHERE m.cmp LIKE 'CMP-9%'
  AND NOT EXISTS (
        SELECT 1 FROM tarifa_consulta t WHERE t.medico_id = m.id AND t.sede_id = 1
  );

-- Rol MEDICO para los medicos nuevos
INSERT INTO usuario_rol (entidad, entidad_id, rol_id)
SELECT 'MEDICO', m.id, r.id
FROM   medico m CROSS JOIN rol r
WHERE  r.nombre = 'MEDICO' AND m.cmp LIKE 'CMP-9%'
  AND  NOT EXISTS (
         SELECT 1 FROM usuario_rol ur
         WHERE ur.entidad='MEDICO' AND ur.entidad_id=m.id AND ur.rol_id=r.id
       );

-- ---------------------------------------------------------------------
-- 2) PACIENTES nuevos (12) — password "Password123"
-- ---------------------------------------------------------------------
INSERT INTO paciente (dni, nombres, apellidos, fecha_nacimiento, sexo, email, telefono, password_hash) VALUES
    ('70000001','Lucia',    'Ramirez Soto',    '1992-04-12','F','lucia.ramirez@email.com',   '900111001', crypt('Password123', gen_salt('bf',10))),
    ('70000002','Diego',    'Fernandez Castro','1988-09-23','M','diego.fernandez@email.com', '900111002', crypt('Password123', gen_salt('bf',10))),
    ('70000003','Valentina','Ortiz Rojas',     '1995-01-30','F','valentina.ortiz@email.com', '900111003', crypt('Password123', gen_salt('bf',10))),
    ('70000004','Andres',   'Gutierrez Diaz',  '1990-07-05','M','andres.gutierrez@email.com','900111004', crypt('Password123', gen_salt('bf',10))),
    ('70000005','Camila',   'Vargas Leon',     '1998-11-18','F','camila.vargas@email.com',   '900111005', crypt('Password123', gen_salt('bf',10))),
    ('70000006','Sebastian','Romero Paz',      '1985-03-14','M','sebastian.romero@email.com','900111006', crypt('Password123', gen_salt('bf',10))),
    ('70000007','Daniela',  'Castro Nunez',    '1993-06-27','F','daniela.castro@email.com',  '900111007', crypt('Password123', gen_salt('bf',10))),
    ('70000008','Mateo',    'Salazar Ruiz',    '1991-12-02','M','mateo.salazar@email.com',   '900111008', crypt('Password123', gen_salt('bf',10))),
    ('70000009','Fernanda', 'Rios Campos',     '1996-08-09','F','fernanda.rios@email.com',   '900111009', crypt('Password123', gen_salt('bf',10))),
    ('70000010','Joaquin',  'Medina Flores',   '1987-10-21','M','joaquin.medina@email.com',  '900111010', crypt('Password123', gen_salt('bf',10))),
    ('70000011','Antonella','Cruz Vega',       '1999-02-16','F','antonella.cruz@email.com',  '900111011', crypt('Password123', gen_salt('bf',10))),
    ('70000012','Nicolas',  'Herrera Lima',    '1994-05-08','M','nicolas.herrera@email.com', '900111012', crypt('Password123', gen_salt('bf',10)))
ON CONFLICT DO NOTHING;

-- Rol PACIENTE para los pacientes nuevos
INSERT INTO usuario_rol (entidad, entidad_id, rol_id)
SELECT 'PACIENTE', p.id, r.id
FROM   paciente p CROSS JOIN rol r
WHERE  r.nombre = 'PACIENTE' AND p.dni LIKE '7000%'
  AND  NOT EXISTS (
         SELECT 1 FROM usuario_rol ur
         WHERE ur.entidad='PACIENTE' AND ur.entidad_id=p.id AND ur.rol_id=r.id
       );

-- ---------------------------------------------------------------------
-- 3) Verificacion
-- ---------------------------------------------------------------------
DO $$
DECLARE
    total_medicos    INTEGER;
    total_pacientes  INTEGER;
    esp_sin_medico   INTEGER;
BEGIN
    SELECT COUNT(*) INTO total_medicos   FROM medico;
    SELECT COUNT(*) INTO total_pacientes FROM paciente;
    SELECT COUNT(*) INTO esp_sin_medico
      FROM especialidad e
      WHERE NOT EXISTS (SELECT 1 FROM medico m WHERE m.especialidad_id = e.id);

    RAISE NOTICE '-----------------------------------------------';
    RAISE NOTICE '  Seed extra aplicado';
    RAISE NOTICE '-----------------------------------------------';
    RAISE NOTICE 'Medicos totales:           %', total_medicos;
    RAISE NOTICE 'Pacientes totales:         %', total_pacientes;
    RAISE NOTICE 'Especialidades sin medico: %  (debe ser 0)', esp_sin_medico;
    RAISE NOTICE '-----------------------------------------------';
END $$;
