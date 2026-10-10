# Plataforma de Logistica y Seguimiento de Envios
## Proyecto Diseno de Patrones - UTP 2026

---

## Descripcion
Sistema de logistica nacional para gestion de envios, tracking en tiempo real
y operaciones de distribucion. Implementado en Java 17 (Maven) con arquitectura
en capas y patrones de diseno GoF.

## Tecnologias
- **Lenguaje:** Java 17
- **Build:** Maven
- **BD:** MySQL / MariaDB (XAMPP)
- **UI:** Java Swing (Dark Mode) + Consola

---

## Patrones de Diseno Implementados

### Avance 1 — Creacionales Basicos + Observer
| Patron | Tipo | Ubicacion | Descripcion |
|--------|------|-----------|-------------|
| **Singleton** | Creacional | `patterns/singleton/ConfiguracionSistema` | Config global del sistema |
| **Factory Method** | Creacional | `patterns/factory/EnvioFactory` | Jerarquia Express/Estandar/Programado |
| **Observer** | Comportamiento | `patterns/observer/EnvioObservable` | Notificaciones reactivas de tracking |

### Avance 2 — Creacionales Restantes + Estructurales
| Patron | Tipo | Ubicacion | Descripcion |
|--------|------|-----------|-------------|
| **Prototype** | Creacional | `patterns/prototype/PlantillaEnvioManager` | Clonar envios recurrentes como plantillas |
| **Builder** | Creacional | `patterns/builder/EnvioBuilder + EnvioDirector` | Construccion paso a paso de envios complejos |
| **Abstract Factory** | Creacional | `patterns/abstractfactory/ILogisticaFactory` | Familias coherentes por zona (Urbana/Interprovincial) |
| **Adapter** | Estructural | `patterns/adapter/GoogleMapsAdapter` | Integrar API GPS externa al sistema interno |
| **Facade** | Estructural | `patterns/facade/PlataformaLogisticaFacade` | Punto de entrada simple para flujo completo |
| **Decorator** | Estructural | `patterns/decorator/SeguroPremium+Empaque+SMS` | Servicios adicionales dinamicos sobre envios |
| **Composite** | Estructural | `patterns/composite/RutaCompuesta+ParadaEntrega` | Rutas jerarquicas con sub-rutas y paradas |

---

## Estructura del Proyecto
```
src/main/java/com/logistica/
|-- AppLogistica.java              (Main + Demos de patrones)
|-- controller/                    (Controladores MVC)
|-- dao/                           (Interfaces + Impl MySQL)
|-- dto/                           (Data Transfer Objects)
|-- model/                         (Entidades de dominio)
|-- patterns/
|   |-- singleton/                 (ConfiguracionSistema)
|   |-- factory/                   (EnvioFactory + Servicios)
|   |-- observer/                  (EnvioObservable + Observers)
|   |-- prototype/                 (PlantillaEnvioManager)
|   |-- builder/                   (EnvioBuilder + EnvioDirector)
|   |-- abstractfactory/           (ILogisticaFactory + Familias)
|   |   |-- urbana/                (Moto + EmpaqueEstandar + RutaLocal)
|   |   |-- interprovincial/       (Camion + EmpaqueBlindado + RutaNacional)
|   |-- adapter/                   (IGeolocalizador + GoogleMapsAdapter)
|   |-- facade/                    (PlataformaLogisticaFacade)
|   |-- decorator/                 (Base + Seguro + Empaque + SMS)
|   |-- composite/                 (IComponenteRuta + Parada + RutaCompuesta)
|-- persistence/                   (ConexionBD Singleton)
|-- service/                       (Interfaces + Implementaciones)
|-- ui/                            (Swing + Consola)
|-- util/                          (GeneradorCodigo + ValidadorDatos)
```

## Principios SOLID Aplicados
- **SRP:** Cada clase tiene una unica responsabilidad
- **OCP:** Extension via interfaces/herencia sin modificar codigo existente
- **LSP:** Jerarquia de transportes sustituible sin romper contratos
- **ISP:** Interfaces segregadas por rol (IOperadorAlmacen, IRepartidorRuta)
- **DIP:** Inyeccion de dependencias via constructores, dependencia de abstracciones

## Compilacion y Ejecucion
```bash
# Compilar
mvn compile

# Ejecutar con interfaz grafica
mvn exec:java -Dexec.mainClass="com.logistica.AppLogistica"

# Ejecutar solo consola
mvn exec:java -Dexec.mainClass="com.logistica.AppLogistica" -Dexec.args="--console"
```

## Base de Datos
Importar el script `database/schema_logistica.sql` en phpMyAdmin (XAMPP).