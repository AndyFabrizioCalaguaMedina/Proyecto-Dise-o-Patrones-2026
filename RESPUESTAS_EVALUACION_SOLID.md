# PREGUNTA 3 ([5] PUNTOS) - EVALUACIÓN DE DISEÑO DE PATRONES
## SOBRE SU PROYECTO FINAL: PLATAFORMA DE LOGÍSTICA Y SEGUIMIENTO DE ENVÍOS
**Universidad Tecnológica del Perú (UTP)**  
**Curso:** Diseño de Patrones (2026)

---

### 1. SRP – Responsabilidad Única (Single Responsibility Principle)

#### Enunciado:
> *En tu proyecto, muéstrame una clase que aplique el principio de Responsabilidad Única. ¿Qué responsabilidad tiene esa clase y qué problema tendríamos si le agregáramos otras responsabilidades?*

#### Respuesta:
* **Clase del proyecto:**  
  [`TarifaCalculatorServiceImpl`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/service/impl/TarifaCalculatorServiceImpl.java)  
  *(Ubicación: `src/main/java/com/logistica/service/impl/TarifaCalculatorServiceImpl.java`)*

* **¿Qué responsabilidad tiene esa clase?**  
  Tiene la **única responsabilidad de calcular el costo total del envío** en función de la modalidad de servicio (`EXPRESS`, `ESTANDAR`, `PROGRAMADO`, `INTERPROVINCIAL`), el peso en kilogramos y la distancia en kilómetros.  
  Su **única razón para cambiar** es una modificación en las fórmulas comerciales o recargos tarifarios de la empresa.

* **¿Qué problema tendríamos si le agregáramos otras responsabilidades?**  
  Si le agregáramos la persistencia hacia la base de datos MySQL (JDBC) o el envío de notificaciones (correo/SMS):
  1. **Se violaría la alta cohesión:** La clase tendría tres motivos distintos para cambiar (cambio de tarifas, cambio de base de datos o cambio en el proveedor de correos).
  2. **Alto acoplamiento:** Cualquier modificación en la infraestructura de red o esquema SQL pondría en riesgo la lógica contable y de cálculo de precios.
  3. **Dificultad de testing:** No se podría probar unitariamente el cálculo de una tarifa sin depender de una base de datos activa o un servidor SMTP.

---

### 2. OCP – Abierto / Cerrado (Open/Closed Principle)

#### Enunciado:
> *En tu proyecto, ¿dónde aplicaste el principio Abierto/Cerrado? Si ahora te pido agregar un nuevo tipo de comportamiento o categoría, ¿qué clase modificarías y cuál no deberías modificar?*

#### Respuesta:
* **¿Dónde se aplicó en el proyecto?**  
  Se aplicó en dos componentes centrales:
  1. En el subsistema de notificaciones mediante la interfaz [`INotificacionService`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/service/INotificacionService.java) con sus implementaciones [`NotificacionEmailService`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/service/impl/NotificacionEmailService.java) y [`NotificacionWhatsAppService`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/service/impl/NotificacionWhatsAppService.java).
  2. En el catálogo de servicios de paquetería mediante el patrón Factory Method con la clase abstracta creadora [`EnvioFactory`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/factory/EnvioFactory.java) y el producto abstracto [`ServicioEnvio`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/factory/ServicioEnvio.java).

* **Si se pide agregar un nuevo tipo de comportamiento o categoría (ejemplo: "Entrega por Drones" o "Notificación por Telegram"):**
  * **¿Qué clase modificarías?**  
    **Ninguna clase existente se modifica.**  
    Únicamente se crea una **nueva clase** que extienda o implemente el contrato:
    * Para un nuevo servicio: crear `ServicioDronDelivery implements ServicioEnvio` y `DronDeliveryFactory extends EnvioFactory`.
    * Para un nuevo canal: crear `NotificacionTelegramService implements INotificacionService`.
  * **¿Cuál NO deberías modificar?**  
    No se modifica la clase orquestadora de negocio [`EnvioServiceImpl`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/service/impl/EnvioServiceImpl.java) ni el controlador [`EnvioController`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/controller/EnvioController.java), ya que interactúan contra la abstracción y permanecen cerrados a la modificación.

---

### 3. LSP – Sustitución de Liskov (Liskov Substitution Principle)

#### Enunciado:
> *Muéstrame dónde utilizas herencia o una abstracción en tu proyecto. ¿Cómo demuestras que una clase hija puede utilizarse en lugar de su clase padre sin romper el funcionamiento de la aplicación?*

#### Respuesta:
* **Jerarquía de herencia y abstracción en el proyecto:**  
  Ubicada en el paquete [`com.logistica.model.transporte`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/model/transporte):
  * Clase base abstracta: [`Transporte`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/model/transporte/Transporte.java) (`iniciarJornada()`, `trasladarPaquete()`).
  * Subclase intermedia: [`TransporteMotorizado`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/model/transporte/TransporteMotorizado.java) (agrega `recargarCombustible()`).
  * Clases concretas: [`CamionReparto`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/model/transporte/CamionReparto.java) (extiende de `TransporteMotorizado`) y [`BicicletaReparto`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/model/transporte/BicicletaReparto.java) (extiende directamente de `Transporte`).

* **¿Cómo se demuestra la sustitución sin romper la aplicación?**  
  Mediante el despacho polimórfico en el cliente (demostrado en `ConsolaView.java` y `AppLogistica.java`):
  ```java
  public void asignarDespacho(Transporte vehiculo, String origen, String destino) {
      // Cualquier subclase de Transporte puede sustituir a la clase padre sin errores:
      vehiculo.iniciarJornada("Sector Metropolitano");
      vehiculo.trasladarPaquete(origen, destino);
  }
  ```
  **Demostración de cumplimiento de LSP:**  
  Si hubiésemos colocado el método `recargarCombustible()` en la clase base `Transporte`, la clase `BicicletaReparto` se habría visto obligada a lanzar una excepción (`throw new UnsupportedOperationException("No usa combustible")`), rompiendo el contrato del programa. Al segregar la jerarquía, `BicicletaReparto` cumple al 100% las expectativas de `Transporte` sin lanzar excepciones inesperadas.

---

### 4. ISP – Segregación de Interfaces (Interface Segregation Principle)

#### Enunciado:
> *En tu proyecto, ¿qué interfaces has creado? ¿Por qué decidiste separarlas en lugar de tener una única interfaz grande?*

#### Respuesta:
* **Interfaces creadas en la capa de servicios:**
  1. [`IOperadorAlmacenService`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/service/IOperadorAlmacenService.java): Define operaciones exclusivas de almacén (`recepcionarPaquete()`, `clasificarPaquete()`, `consolidarLote()`).
  2. [`IRepartidorRutaService`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/service/IRepartidorRutaService.java): Define operaciones móviles de ruta (`iniciarReparto()`, `registrarEntregaExitosa()`, `reportarIncidencia()`).
  3. [`IEnvioService`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/service/IEnvioService.java): Define operaciones de registro y consulta de envíos (`registrarEnvio()`, `consultarTracking()`, `listarTodos()`).

* **¿Por qué decidiste separarlas en lugar de una interfaz grande?**  
  Porque en la empresa logística cada actor interactúa con el sistema desde un contexto distinto.  
  Si hubiéramos creado una sola interfaz "gorda" (`IUsuarioLogisticoService` con todos los métodos agrupados):
  * La clase `RepartidorRutaServiceImpl` se vería obligada a implementar `clasificarPaquete()` o `consolidarLote()`, dejándolos vacíos o lanzando excepciones.
  * La clase `OperadorAlmacenServiceImpl` tendría que implementar `registrarEntregaExitosa()` con firma digital, cuando un operario de almacén jamás realiza entregas en domicilio.  
  Al segregarlas, **cada clase implementa única y exclusivamente lo que necesita**.

---

### 5. DIP – Inversión de Dependencias (Dependency Inversion Principle)

#### Enunciado:
> *Muéstrame dónde una clase depende de una interfaz o abstracción en lugar de depender directamente de una clase concreta. ¿Qué ventaja obtienes con esta implementación?*

#### Respuesta:
* **Dónde se aplica:**  
  En el servicio principal [`EnvioServiceImpl`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/service/impl/EnvioServiceImpl.java):
  ```java
  public class EnvioServiceImpl implements IEnvioService {

      // Dependencias de ABSTRACCIONES (Interfaces), NO de clases concretas
      private final IEnvioDAO envioDAO;
      private final ITarifaCalculatorService tarifaCalculator;
      private final INotificacionService notificador;

      // Inyección de Dependencias a través del constructor
      public EnvioServiceImpl(IEnvioDAO envioDAO, 
                              ITarifaCalculatorService tarifaCalculator, 
                              INotificacionService notificador) {
          this.envioDAO = envioDAO;
          this.tarifaCalculator = tarifaCalculator;
          this.notificador = notificador;
      }
  }
  ```
  Las clases concretas (`EnvioDAOMySQL`, `TarifaCalculatorServiceImpl`, `NotificacionEmailService`) son instanciadas en el punto de arranque [`AppLogistica.java`](file:///c:/Users/USUARIO/Downloads/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/AppLogistica.java) e inyectadas por constructor.

* **¿Qué ventajas se obtienen con esta implementación?**  
  1. **Independencia de la Base de Datos:** `EnvioServiceImpl` no conoce si los datos van a MySQL, Oracle o a una memoria RAM de pruebas. Si mañana se migra de base de datos, el servicio no sufre ningún cambio.
  2. **Facilidad para Pruebas Unitarias (Mocking):** Permite testear toda la lógica del negocio pasando implementaciones simuladas (*fakes* / *mocks*), sin necesidad de tener XAMPP/MySQL encendido.
  3. **Intercambiabilidad de canales:** Se puede cambiar de notificar por Email a notificar por WhatsApp en una sola línea de configuración al momento de iniciar la aplicación.
