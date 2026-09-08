-- =============================================================
-- PROYECTO: PLATAFORMA DE LOGÍSTICA Y SEGUIMIENTO DE ENVÍOS
-- CURSO: DISEÑO DE PATRONES - PRIMER AVANCE (SEMANAS 1 A 4)
-- MOTOR: MySQL / MariaDB (Compatible con XAMPP / phpMyAdmin)
-- =============================================================

CREATE DATABASE IF NOT EXISTS db_logistica_envios
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE db_logistica_envios;

-- -------------------------------------------------------------
-- 1. TABLA: SERVICIOS_ENVIO (Catálogo de servicios y tarifas)
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS servicios_envio (
    id_servicio INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    tarifa_base DECIMAL(10,2) NOT NULL,
    factor_urgencia DECIMAL(4,2) DEFAULT 1.00,
    tiempo_estimado_horas INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- -------------------------------------------------------------
-- 2. TABLA: USUARIOS (Actores del sistema logístico)
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    documento VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    rol ENUM('ADMIN', 'OPERADOR_ALMACEN', 'REPARTIDOR', 'CLIENTE_CORP', 'DESTINATARIO') NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- -------------------------------------------------------------
-- 3. TABLA: ENVIOS (Registro de paquetes y guías de envío)
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS envios (
    id_envio INT AUTO_INCREMENT PRIMARY KEY,
    codigo_tracking VARCHAR(30) NOT NULL UNIQUE,
    id_servicio INT NOT NULL,
    id_remitente INT NOT NULL,
    nombre_destinatario VARCHAR(150) NOT NULL,
    direccion_destino VARCHAR(255) NOT NULL,
    telefono_destinatario VARCHAR(20) NOT NULL,
    email_destinatario VARCHAR(120),
    peso_kg DECIMAL(6,2) NOT NULL,
    volumen_m3 DECIMAL(6,3) DEFAULT 0.000,
    costo_total DECIMAL(10,2) NOT NULL,
    estado_actual ENUM(
        'REGISTRADO',
        'RECOGIDO',
        'EN_TRANSITO',
        'CLASIFICADO',
        'EN_RUTA',
        'ENTREGADO',
        'OBSERVADO',
        'REPROGRAMADO',
        'DEVUELTO'
    ) DEFAULT 'REGISTRADO',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_servicio) REFERENCES servicios_envio(id_servicio) ON UPDATE CASCADE,
    FOREIGN KEY (id_remitente) REFERENCES usuarios(id_usuario) ON UPDATE CASCADE
) ENGINE=InnoDB;

-- -------------------------------------------------------------
-- 4. TABLA: HISTORIAL_ESTADOS (Trazabilidad y eventos)
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS historial_estados (
    id_historial INT AUTO_INCREMENT PRIMARY KEY,
    id_envio INT NOT NULL,
    estado ENUM(
        'REGISTRADO',
        'RECOGIDO',
        'EN_TRANSITO',
        'CLASIFICADO',
        'EN_RUTA',
        'ENTREGADO',
        'OBSERVADO',
        'REPROGRAMADO',
        'DEVUELTO'
    ) NOT NULL,
    ubicacion VARCHAR(150) NOT NULL,
    observacion TEXT,
    id_usuario_responsable INT,
    fecha_evento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_envio) REFERENCES envios(id_envio) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario_responsable) REFERENCES usuarios(id_usuario) ON SET NULL
) ENGINE=InnoDB;

-- =============================================================
-- INSERCIÓN DE DATOS DE PRUEBA (SEEDING)
-- =============================================================

INSERT INTO servicios_envio (codigo, nombre, descripcion, tarifa_base, factor_urgencia, tiempo_estimado_horas) VALUES
('EXP', 'Entrega Express', 'Entrega prioritaria en menos de 24 horas', 25.00, 1.50, 12),
('STD', 'Envío Estándar', 'Entrega regular consolidada', 12.00, 1.00, 48),
('PRG', 'Mensajería Programada', 'Entrega en fecha y franja horaria agendada', 18.00, 1.20, 24),
('REC', 'Recojo en Tienda', 'Retiro en casillero o punto autorizado', 8.00, 0.80, 24),
('INT', 'Transporte Interprovincial', 'Envío a nivel nacional con consolidación', 35.00, 1.30, 72)
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

INSERT INTO usuarios (documento, nombre, apellidos, email, telefono, rol) VALUES
('70010020', 'Admin', 'Logístico', 'admin@logistica.com', '999111222', 'ADMIN'),
('45871234', 'Carlos', 'Mendoza', 'carlos.almacen@logistica.com', '988222333', 'OPERADOR_ALMACEN'),
('72345678', 'Jorge', 'Ríos', 'jorge.repartidor@logistica.com', '977333444', 'REPARTIDOR'),
('20601234567', 'TechCorp', 'S.A.C.', 'contacto@techcorp.pe', '014445555', 'CLIENTE_CORP'),
('41239876', 'Ana', 'Gómez', 'ana.gomez@gmail.com', '966444555', 'DESTINATARIO')
ON DUPLICATE KEY UPDATE nombre=VALUES(nombre);

INSERT INTO envios (codigo_tracking, id_servicio, id_remitente, nombre_destinatario, direccion_destino, telefono_destinatario, email_destinatario, peso_kg, costo_total, estado_actual) VALUES
('TRK-2026-0001', 1, 4, 'Ana Gómez', 'Av. Javier Prado Este 2500, San Borja, Lima', '966444555', 'ana.gomez@gmail.com', 2.50, 37.50, 'EN_RUTA'),
('TRK-2026-0002', 2, 4, 'Pedro Castillo', 'Calle Los Pinos 123, Trujillo', '955111333', 'pedro.c@gmail.com', 5.00, 60.00, 'CLASIFICADO'),
('TRK-2026-0003', 3, 4, 'Lucía Morales', 'Jr. Junín 450, Arequipa', '944888222', 'lucia.m@outlook.com', 1.20, 21.60, 'REGISTRADO')
ON DUPLICATE KEY UPDATE estado_actual=VALUES(estado_actual);

INSERT INTO historial_estados (id_envio, estado, ubicacion, observacion, id_usuario_responsable) VALUES
(1, 'REGISTRADO', 'Centro de Recepción Lima', 'Paquete registrado por cliente corporativo TechCorp', 1),
(1, 'RECOGIDO', 'Almacén Central', 'Recogido por móvil de ruta urbana', 3),
(1, 'CLASIFICADO', 'Hub Principal Lima Este', 'Paquete escaneado y clasificado en cinta transportadora', 2),
(1, 'EN_RUTA', 'San Borja', 'En unidad de reparto con destino al cliente final', 3),
(2, 'REGISTRADO', 'Centro de Recepción Lima', 'Guía generada por e-commerce', 1),
(2, 'CLASIFICADO', 'Hub Logístico Norte', 'Consolidado para carga interprovincial', 2),
(3, 'REGISTRADO', 'Plataforma Web', 'Solicitud de mensajería programada recibida', 1);
