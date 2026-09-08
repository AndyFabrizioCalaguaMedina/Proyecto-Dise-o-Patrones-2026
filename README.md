# Plataforma de Logística y Seguimiento de Envíos en Tiempo Real
**Curso:** Diseño de Patrones (UTP)  
**Entregable:** Primer Avance (Unidad de Aprendizaje 1 - Semanas 1 a 4)

---

## 🏗️ Arquitectura y Estructura del Proyecto

El proyecto está diseñado siguiendo las mejores prácticas de **Programación Orientada a Objetos (POO)**, los **5 Principios SOLID** y los **Patrones de Diseño GoF (Creacionales y de Comportamiento)**, organizados en una arquitectura empresarial por capas:

```
src/main/java/com/logistica/
│
├── controller/         # Capa de Controladores (Coordina las peticiones de UI hacia los servicios)
│   ├── EnvioController.java
│   └── TrackingController.java
│
├── dao/                # Data Access Object (Abstracción de acceso a datos - Principio DIP)
│   ├── IEnvioDAO.java
│   ├── IUsuarioDAO.java
│   └── impl/           # Implementaciones con JDBC para MySQL / XAMPP
│       ├── EnvioDAOMySQL.java
│       └── UsuarioDAOMySQL.java
│
├── dto/                # Data Transfer Objects (Transferencia de datos limpia y tipada)
│   ├── EnvioRegistroDTO.java
│   ├── TrackingEventoDTO.java
│   └── RespuestaOperacionDTO.java
│
├── model/              # Entidades del Dominio del Negocio Logístico
│   ├── Envio.java
│   ├── EstadoEnvio.java (Enum de estados del ciclo de vida)
│   ├── Usuario.java
│   ├── RolUsuario.java
│   └── transporte/     # Jerarquía polimórfica que cumple LSP (Liskov Substitution Principle)
│       ├── Transporte.java (Clase base abstracta)
│       ├── TransporteMotorizado.java
│       ├── CamionReparto.java
│       └── BicicletaReparto.java
│
├── patterns/           # Especificación de Patrones de Diseño GoF (Semanas 1 a 4)
│   ├── singleton/      # Patrón Singleton (Configuración global del sistema)
│   │   └── ConfiguracionSistema.java
│   ├── factory/        # Patrón Factory Method (Creación de servicios Express, Estándar, Programado)
│   │   ├── ServicioEnvio.java (Product)
│   │   ├── ServicioExpress.java
│   │   ├── ServicioEstandar.java
│   │   ├── ServicioProgramado.java
│   │   ├── EnvioFactory.java (Creator)
│   │   ├── ExpressFactory.java
│   │   ├── EstandarFactory.java
│   │   └── ProgramadoFactory.java
│   └── observer/       # Patrón Observer (Trazabilidad y notificaciones reactivas)
│       ├── ITrackingObserver.java
│       ├── EnvioObservable.java (Subject)
│       ├── DestinatarioNotificacionObserver.java
│       ├── CentroMonitoreoObserver.java
│       └── AuditoriaTrackingObserver.java
│
├── persistence/        # Manejo de conexión JDBC a MySQL (Patrón Singleton)
│   └── ConexionBD.java
│
├── service/            # Capa de Lógica de Negocio (Donde viven los principios SOLID)
│   ├── IEnvioService.java
│   ├── ITarifaCalculatorService.java (SRP / OCP)
│   ├── INotificacionService.java     (DIP / OCP)
│   ├── IOperadorAlmacenService.java  (ISP - Segregación para operadores)
│   ├── IRepartidorRutaService.java   (ISP - Segregación para repartidores)
│   └── impl/
│       ├── EnvioServiceImpl.java
│       ├── TarifaCalculatorServiceImpl.java
│       ├── NotificacionEmailService.java
│       ├── NotificacionWhatsAppService.java
│       ├── OperadorAlmacenServiceImpl.java
│       └── RepartidorRutaServiceImpl.java
│
├── ui/                 # Capa de Presentación (Interfaz Gráfica Swing + Consola)
│   ├── Theme.java      # Paleta de estilos minimalistas (Slate/Sky)
│   ├── VentanaPrincipal.java # Ventana gráfica interactiva con pestañas y tracking en vivo
│   └── ConsolaView.java # Vista interactiva por consola
│
├── util/               # Utilidades y validaciones
│   ├── GeneradorCodigo.java
│   └── ValidadorDatos.java
│
└── AppLogistica.java   # Clase Principal (Main) con Inversión de Control / Orquestación
```

---

## 💡 Cómo se Aplican los Principios SOLID en el Código

Los principios SOLID **no son clases aisladas**, sino fundamentos que estructuran toda la aplicación:

1. **SRP (Responsabilidad Única):**
   - `TarifaCalculatorServiceImpl` solo calcula precios.
   - `EnvioDAOMySQL` solo interactúa con la base de datos MySQL.
   - `EnvioServiceImpl` orquesta el flujo de negocio sin acoplarse a detalles técnicos.
2. **OCP (Abierto / Cerrado):**
   - Nuevos canales de notificación (`NotificacionWhatsAppService`, `NotificacionEmailService`) se agregan implementando `INotificacionService` sin tocar código existente.
   - Nuevos tipos de envío se añaden en `patterns/factory` mediante subclases de `EnvioFactory`.
3. **LSP (Sustitución de Liskov):**
   - `CamionReparto` y `BicicletaReparto` extienden de `Transporte` de manera limpia; `BicicletaReparto` no es obligada a heredar métodos de combustible inexistentes que arrojarían excepciones.
4. **ISP (Segregación de Interfaces):**
   - `IOperadorAlmacenService` e `IRepartidorRutaService` definen interfaces específicas para cada actor del negocio en lugar de una interfaz general recargada.
5. **DIP (Inversión de Dependencias):**
   - Los servicios de alto nivel (`EnvioServiceImpl`, `EnvioController`) dependen exclusivamente de abstracciones (`IEnvioDAO`, `ITarifaCalculatorService`, `INotificacionService`), las cuales son inyectadas por constructor.

---

## 🗄️ Configuración de la Base de Datos MySQL (XAMPP)

1. Abre el panel de **XAMPP** e inicia los módulos **Apache** y **MySQL**.
2. Abre tu navegador web en `http://localhost/phpmyadmin`.
3. Ve a la pestaña **Importar** (Import).
4. Selecciona el archivo [`database/schema_logistica.sql`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/database/schema_logistica.sql).
5. Haz clic en **Continuar** (Go). La base de datos `db_logistica_envios`, sus tablas (`servicios_envio`, `usuarios`, `envios`, `historial_estados`) y datos de prueba quedarán listos.

> **Nota:** Si ejecutas el programa sin iniciar MySQL, el sistema cuenta con un mecanismo de *fallback* automático en memoria para que la aplicación funcione y muestre todos los patrones sin detenerse.

---

## 🚀 Compilación y Ejecución

### Opción 1: Con PowerShell / CMD (Directo con `javac` y `java`)
```powershell
# 1. Compilar todas las clases
javac -encoding UTF-8 -d bin (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })

# 2. Ejecutar con Interfaz Gráfica Minimalista (Swing):
java -cp bin com.logistica.AppLogistica

# O si deseas ejecutar solo en Consola:
java -cp bin com.logistica.AppLogistica --console
```

### Opción 2: Con Apache Maven (NetBeans / IntelliJ / VS Code)
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.logistica.AppLogistica"
```

---

## 📄 Informe Técnico de Avance
Consulta el informe académico completo en formato Markdown listo para entrega:
- [AVANCE_1_INFORME.md](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/AVANCE_1_INFORME.md)
