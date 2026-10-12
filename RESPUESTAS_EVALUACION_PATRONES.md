# Pregunta 3 ([6] puntos)

## Análisis de patrones de diseño aplicados al proyecto final

Sobre el **proyecto grupal final del curso**, que vienen desarrollando progresivamente, complete la siguiente tabla identificando los principales elementos de diseño utilizados en su solución, tales como **clases, clases derivadas, interfaces, implementaciones y otros componentes relevantes**.

Luego, **explique y justifique los patrones creacionales y estructurales** aplicados en su proyecto, indicando qué problema resuelven y cómo se relacionan con las clases o componentes identificados.

---

### Ejemplo de cómo completar la primera fila:

| N.° | Elemento identificado | Tipo | Responsabilidad dentro del proyecto | Patrón aplicado | Justificación |
| :---: | :--- | :--- | :--- | :--- | :--- |
| **Ejemplo** | ConexionBD | Clase | Se encarga de crear y proporcionar la conexión con la base de datos para los diferentes módulos del sistema. | Singleton – Creacional | Se aplica porque se necesita controlar la creación de la conexión y evitar que diferentes partes del sistema creen innecesariamente múltiples instancias de este componente. |
| **1** | EnvioFactory | Clase abstracta | Se encarga de definir el método de fabricación y el flujo general de procesamiento de envíos, delegando a las subclases la creación del servicio logístico específico. | Factory Method – Creacional | Se aplica porque se necesita desacoplar la creación de los distintos tipos de servicio (Express, Estándar, Programado) de la lógica de negocio, evitando condicionales rígidos y permitiendo agregar nuevos servicios sin modificar el código existente. |
| **2** | EnvioBuilder | Clase | Se encarga de construir paso a paso objetos complejos de envío, configurando atributos obligatorios y opcionales como seguro, empaque reforzado, prioridad y horarios preferidos. | Builder – Creacional | Se aplica porque se necesita construir envíos con múltiples atributos opcionales sin utilizar constructores sobrecargados con demasiados parámetros, garantizando la creación de objetos consistentes y legibles. |
| **3** | ILogisticaFactory | Interfaz | Se encarga de definir la creación de familias completas de componentes logísticos compatibles (transporte, empaque y ruta) según la zona geográfica de cobertura. | Abstract Factory – Creacional | Se aplica porque se necesita asegurar la coherencia entre los componentes logísticos de una misma zona (urbana o interprovincial), evitando que el sistema mezcle transportes, empaques y rutas incompatibles entre sí. |
| **4** | GoogleMapsAdapter | Clase | Se encarga de adaptar la API externa de Google Maps a la interfaz interna de geolocalización requerida por el sistema, traduciendo formatos de coordenadas y unidades de distancia. | Adapter – Estructural | Se aplica porque se necesita integrar un servicio externo con una interfaz incompatible sin acoplar la lógica del negocio al proveedor, permitiendo sustituir o añadir otros servicios de mapas en el futuro sin afectar el sistema. |
| **5** | PlataformaLogisticaFacade | Clase | Se encarga de proporcionar un punto de acceso unificado y simple para realizar el registro y despacho completo de envíos, orquestando los distintos subsistemas del proyecto. | Facade – Estructural | Se aplica porque se necesita ocultar la complejidad e interacción de múltiples subsistemas (geolocalización, cotización, ensamblado del paquete, servicios adicionales y guardado), facilitando el uso del sistema desde la interfaz de usuario. |
| **6** | SeguroPremiumDecorator | Clase derivada | Se encarga de agregar dinámicamente el costo y la cobertura de seguro a un servicio de envío base sin alterar la estructura ni el código de la clase original. | Decorator – Estructural | Se aplica porque se necesita añadir servicios adicionales y opcionales a los envíos en tiempo de ejecución de manera acumulativa y flexible, evitando crear múltiples subclases para cada combinación posible de servicios. |

---

## Explicación y justificación de los patrones aplicados en el proyecto

A continuación se explica qué problema resuelve cada patrón y cómo se relacionan las clases y componentes identificados dentro de la arquitectura del proyecto.

---

### 1. Patrones Creacionales

#### 1.1. Factory Method (Creacional)
* **Clases y componentes relacionados:**
  * Clase abstracta creadora: `EnvioFactory`
  * Subclases creadoras concretas: `ExpressFactory`, `EstandarFactory`, `ProgramadoFactory`
  * Interfaz de producto: `ServicioEnvio`
  * Clases de productos concretos: `ServicioExpress`, `ServicioEstandar`, `ServicioProgramado`
* **¿Qué problema resuelve en el proyecto?**  
  Resuelve el problema del acoplamiento en la creación de los diferentes tipos de envíos. En una empresa logística cada servicio maneja tarifas, tiempos de entrega y prioridades distintas. Si los controladores o pantallas usaran `new ServicioExpress()` o estructuras de control como `switch/case`, cada nuevo servicio requeriría modificar clases existentes, violando el Principio Abierto/Cerrado (OCP).
* **Cómo se relacionan los componentes:**  
  `EnvioFactory` declara el método abstracto `crearServicio()` y centraliza el flujo en `procesarEnvio()`. Cada fábrica concreta (`ExpressFactory`, `EstandarFactory`, etc.) implementa `crearServicio()` devolviendo su respectiva instancia de `ServicioEnvio`. El cliente trabaja exclusivamente con las abstracciones sin conocer la clase concreta que se instancia.

---

#### 1.2. Builder (Creacional)
* **Clases y componentes relacionados:**
  * Clase constructora: `EnvioBuilder`
  * Clase directora: `EnvioDirector`
  * Clase del producto: `Envio`
* **¿Qué problema resuelve en el proyecto?**  
  Resuelve el problema de los constructores telescópicos y la creación de objetos inconsistentes. La entidad `Envio` contiene datos básicos obligatorios (remitente, destinatario, peso, costo) junto a una gran cantidad de datos opcionales (cobertura de seguro, empaque reforzado, retorno de cargo, prioridad e instrucciones de entrega). Un constructor con más de 10 parámetros resulta difícil de leer y propenso a errores de asignación.
* **Cómo se relacionan los componentes:**  
  `EnvioBuilder` recibe de manera encadenada (*fluent interface*) cada parámetro requerido, valida los datos y finalmente genera el objeto `Envio` mediante su método `build()`. A su vez, `EnvioDirector` utiliza el constructor para ensamblar configuraciones típicas predefinidas (por ejemplo, envíos urgentes o envíos corporativos estándar) facilitando su reutilización.

---

#### 1.3. Abstract Factory (Creacional)
* **Clases y componentes relacionados:**
  * Interfaz de la fábrica abstracta: `ILogisticaFactory`
  * Fábricas concretas: `LogisticaUrbanaFactory`, `LogisticaInterprovincialFactory`
  * Interfaces de productos: `ITransporteLogistico`, `IEmpaque`, `IRutaLogistica`
  * Productos concretos urbanos: `MotoReparto`, `EmpaqueEstandar`, `RutaLocal`
  * Productos concretos interprovinciales: `CamionInterprovincial`, `EmpaqueBlindado`, `RutaNacional`
* **¿Qué problema resuelve en el proyecto?**  
  Resuelve el riesgo de incompatibilidad operativa al asignar recursos logísticos según la zona geográfica. Una entrega local en ciudad requiere motocicletas, empaques livianos y rutas de corta distancia, mientras que un traslado nacional requiere camiones pesados, empaque reforzado y rutas troncales. Permitir que el sistema mezcle estos elementos causaría errores operativos graves.
* **Cómo se relacionan los componentes:**  
  `ILogisticaFactory` agrupa los métodos `crearTransporte()`, `crearEmpaque()` y `crearRuta()`. Las fábricas concretas (`LogisticaUrbanaFactory` y `LogisticaInterprovincialFactory`) producen simultáneamente la familia completa de elementos correspondientes a su ámbito. El módulo de despacho interactúa únicamente a través de la fábrica abstracta, garantizando coherencia en todo momento.

---

### 2. Patrones Estructurales

#### 2.1. Adapter (Estructural)
* **Clases y componentes relacionados:**
  * Interfaz objetivo (Target): `IGeolocalizador`
  * Clase adaptadora (Adapter): `GoogleMapsAdapter`
  * Clase externa adaptada (Adaptee): `GoogleMapsAPI`
* **¿Qué problema resuelve en el proyecto?**  
  Resuelve la incompatibilidad de interfaces con servicios externos. El sistema necesita calcular distancias y coordenadas para el ruteo de entregas. Sin embargo, la API de Google Maps entrega datos en cadenas de texto (`String` con formato `"lat,lng"`) y distancias en metros (`int`). El modelo logístico interno requiere arrays numéricos `double[]` y distancias expresadas en kilómetros.
* **Cómo se relacionan los componentes:**  
  `GoogleMapsAdapter` implementa la interfaz del sistema `IGeolocalizador` y mantiene una referencia interna a `GoogleMapsAPI`. En sus métodos, invoca a la API externa, realiza la conversión de tipos y unidades requeridas, y devuelve los datos limpios al sistema. Si en el futuro se cambia a otra plataforma como Waze u OpenStreetMap, el resto del software no sufre ningún cambio.

---

#### 2.2. Facade (Estructural)
* **Clases y componentes relacionados:**
  * Clase fachada: `PlataformaLogisticaFacade`
  * Subsistemas que coordina: `GoogleMapsAdapter` (geolocalización), `EnvioBuilder` (construcción), `IServicioEnvioDecorable` (cálculo y adicionales), `ConexionBD` / `EnvioDAO` (persistencia)
* **¿Qué problema resuelve en el proyecto?**  
  Resuelve la complejidad de interacción entre la interfaz de usuario y los múltiples subsistemas del negocio. Registrar un envío completo implica validar información, consultar el GPS, calcular costos base, aplicar tarifas adicionales de seguro o empaque, ensamblar el envío y guardar en base de datos. Si la capa de presentación tuviera que conectarse con cada una de estas clases, el código sería complejo y difícil de mantener.
* **Cómo se relacionan los componentes:**  
  `PlataformaLogisticaFacade` expone un método simplificado `registrarEnvioCompleto(...)`. La fachada recibe los datos indispensables desde la vista, se comunica secuencialmente con los subsistemas internos y devuelve directamente el envío procesado, encapsulando y protegiendo la complejidad interna.

---

#### 2.3. Decorator (Estructural)
* **Clases y componentes relacionados:**
  * Interfaz componente: `IServicioEnvioDecorable`
  * Componente concreto base: `ServicioEnvioBase`
  * Decoradores concretos: `SeguroPremiumDecorator`, `EmpaqueReforzadoDecorator`, `RastreoSMSDecorator`
* **¿Qué problema resuelve en el proyecto?**  
  Resuelve el problema de la rigidez de la herencia frente a servicios complementarios opcionales. Los clientes pueden solicitar un envío con o sin seguro, con o sin empaque reforzado, o con notificaciones por SMS. Si intentáramos modelar todas las opciones mediante herencia fija, tendríamos una explosión de clases para cada combinación posible de servicios.
* **Cómo se relacionan los componentes:**  
  Tanto `ServicioEnvioBase` como los decoradores implementan `IServicioEnvioDecorable`. Cada decorador recibe en su constructor otro objeto `IServicioEnvioDecorable`, delegando la llamada anterior y agregando su propio costo y descripción. De esta manera, los servicios adicionales se pueden envolver dinámicamente en tiempo de ejecución en cualquier combinación deseada.
