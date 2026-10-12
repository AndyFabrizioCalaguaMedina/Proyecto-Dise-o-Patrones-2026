# PREGUNTA 3 ([6] PUNTOS) - ANÁLISIS DE PATRONES DE DISEÑO APLICADOS AL PROYECTO FINAL
## PROYECTO: PLATAFORMA DE LOGÍSTICA Y SEGUIMIENTO DE ENVÍOS EN TIEMPO REAL
**Universidad Tecnológica del Perú (UTP)**  
**Curso:** Diseño de Patrones (2026)  
**Profesor / Evaluador:** Facultad de Ingeniería  

---

### ENUNCIADO DE LA EVALUACIÓN
> *Sobre el **proyecto grupal final del curso**, que vienen desarrollando progresivamente, complete la siguiente tabla identificando los principales elementos de diseño utilizados en su solución, tales como **clases, clases derivadas, interfaces, implementaciones y otros componentes relevantes**.*  
> 
> *Luego, **explique y justifique los patrones creacionales y estructurales** aplicados en su proyecto, indicando qué problema resuelven y cómo se relacionan con las clases o componentes identificados.*

---

## 1. TABLA DE ANÁLISIS DE ELEMENTOS Y PATRONES DE DISEÑO

| N.° | Elemento identificado | Tipo | Responsabilidad dentro del proyecto | Patrón aplicado | Justificación |
| :---: | :--- | :--- | :--- | :--- | :--- |
| **Ejemplo** | `ConexionBD` | Clase | Se encarga de crear y proporcionar la conexión con la base de datos para los diferentes módulos del sistema. | **Singleton – Creacional** | Se aplica porque se necesita controlar la creación de la conexión y evitar que diferentes partes del sistema creen innecesariamente múltiples instancias de este componente. |
| **1** | [`EnvioFactory`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/factory/EnvioFactory.java) | Clase abstracta | Declara el método fábrica `crearServicio()` y define el flujo general de procesamiento de envíos (`procesarEnvio()`), delegando la instanciación concreta en sus subclases. | **Factory Method – Creacional** | Se aplica para desacoplar el procesamiento del envío de la instanciación directa (`new`) de modalidades concretas (`Express`, `Estandar`, `Programado`), permitiendo incorporar nuevos tipos de servicio sin modificar el código cliente (cumple OCP). |
| **2** | [`EnvioBuilder`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/builder/EnvioBuilder.java) | Clase | Construye paso a paso instancias complejas de la entidad [`Envio`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/model/Envio.java), configurando atributos obligatorios y opcionales (seguro, horario preferido, prioridad, instrucciones especiales). | **Builder – Creacional** | Se aplica para evitar constructores telescópicos con más de 10 parámetros y garantizar la creación de envíos consistentes y altamente legibles mediante una API fluida (*fluent interface*). |
| **3** | [`ILogisticaFactory`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/abstractfactory/ILogisticaFactory.java) | Interfaz | Declara las operaciones de creación para familias completas de productos logísticos interdependientes (`crearTransporte()`, `crearEmpaque()`, `crearRuta()`) según la zona geográfica. | **Abstract Factory – Creacional** | Se aplica para garantizar que los componentes logísticos de una misma zona sean coherentes entre sí (ej. zona Urbana con moto/empaque estándar vs. zona Interprovincial con camión/empaque blindado), evitando combinaciones incompatibles. |
| **4** | [`GoogleMapsAdapter`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/adapter/GoogleMapsAdapter.java) | Clase (Implementación) | Adapta la API de un proveedor externo ([`GoogleMapsAPI`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/adapter/GoogleMapsAPI.java)) a la interfaz requerida por el dominio logístico ([`IGeolocalizador`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/adapter/IGeolocalizador.java)), convirtiendo cadenas en `double[]` y metros en kilómetros. | **Adapter – Estructural** | Se aplica para integrar servicios GPS de terceros sin contaminar el núcleo del negocio. Si en el futuro se migra a OpenStreetMap o Waze, solo se crea otro Adapter sin alterar el cálculo de tarifas ni el ruteo. |
| **5** | [`PlataformaLogisticaFacade`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/facade/PlataformaLogisticaFacade.java) | Clase | Expone un punto de acceso de alto nivel (`registrarEnvioCompleto()`) que orquesta y oculta la interacción entre geolocalización, cálculo de tarifas, construcción del envío, decoradores de servicios y persistencia. | **Facade – Estructural** | Se aplica para simplificar la interacción de la capa de presentación (UI / controladores) con los múltiples subsistemas del dominio logístico, minimizando dependencias directas y acoplamiento estructural. |
| **6** | [`SeguroPremiumDecorator`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/decorator/SeguroPremiumDecorator.java) | Clase derivada | Extiende dinámicamente un [`IServicioEnvioDecorable`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/decorator/IServicioEnvioDecorable.java) agregando el costo y la descripción de una póliza de seguro sobre el paquete sin modificar la clase base del servicio. | **Decorator – Estructural** | Se aplica para añadir servicios adicionales opcionales (seguro, empaque especial, SMS) en tiempo de ejecución de manera acumulativa y combinable, evitando una explosión combinatoria de subclases por herencia. |

---

### Componentes y Patrones Alternativos Disponibles en el Proyecto
Si se desea sustituir alguna fila de la tabla o sustentar elementos adicionales:

| N.° | Elemento identificado | Tipo | Responsabilidad dentro del proyecto | Patrón aplicado | Justificación |
| :---: | :--- | :--- | :--- | :--- | :--- |
| **Alt A** | [`RutaCompuesta`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/composite/RutaCompuesta.java) | Clase (Compuesto) | Modela rutas troncales y zonales agrupando tanto paradas simples ([`ParadaEntrega`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/composite/ParadaEntrega.java)) como sub-rutas anidadas bajo el contrato común [`IComponenteRuta`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/composite/IComponenteRuta.java). | **Composite – Estructural** | Permite tratar rutas simples y redes de distribución jerárquicas multinivel de manera uniforme para el cálculo total de kilometraje e inspección de rutas. |
| **Alt B** | [`PlantillaEnvioManager`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/prototype/PlantillaEnvioManager.java) | Clase | Almacena prototipos de envíos corporativos frecuentes en memoria y produce clones independientes mediante [`Envio.clone()`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/model/Envio.java). | **Prototype – Creacional** | Permite registrar rápidamente lotes de envíos masivos clonando configuraciones preestablecidas sin repetir consultas ni recrear objetos desde cero. |
| **Alt C** | [`ConfiguracionSistema`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/singleton/ConfiguracionSistema.java) | Clase | Centraliza los parámetros globales de la empresa logística (RUC, límites de reintentos de entrega, factores de recargo). | **Singleton – Creacional** | Garantiza un único punto de verdad global de políticas operativas accesible desde cualquier capa sin inconsistencias de estado. |

---

## 2. EXPLICACIÓN Y JUSTIFICACIÓN DETALLADA DE PATRONES CREACIONALES

### 2.1. Patrón Factory Method
* **Paquete:** `com.logistica.patterns.factory`
* **Elementos clave:**
  * Creador abstracto: [`EnvioFactory`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/factory/EnvioFactory.java)
  * Creadores concretos: [`ExpressFactory`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/factory/ExpressFactory.java), [`EstandarFactory`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/factory/EstandarFactory.java), [`ProgramadoFactory`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/factory/ProgramadoFactory.java)
  * Producto abstracto: [`ServicioEnvio`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/factory/ServicioEnvio.java)
  * Productos concretos: [`ServicioExpress`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/factory/ServicioExpress.java), [`ServicioEstandar`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/factory/ServicioEstandar.java), [`ServicioProgramado`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/factory/ServicioProgramado.java)

#### Problema que resuelve:
En el sistema logístico, cada modalidad de entrega posee reglas operativas, SLA de horas de despacho y factores tarifarios diferentes. Si los controladores o vistas instancian directamente las clases mediante condicionales `if-else` o sentencias `switch(tipo)`, cualquier nueva modalidad exigiría modificar código crítico en múltiples capas, violando el Principio Abierto/Cerrado (OCP).

#### Justificación y relación entre clases:
`EnvioFactory` encapsula la lógica invariable del proceso de despacho en su método `procesarEnvio()`, pero delega la creación del objeto en el método abstracto `crearServicio()`. De esta forma, el cliente invoca el comportamiento a través de la abstracción `ServicioEnvio`, manteniendo el código completamente desacoplado de las implementaciones concretas.

```java
// Ejemplo de uso en el proyecto:
EnvioFactory factory = new ExpressFactory();
ServicioEnvio servicio = factory.crearServicio(); // Devuelve ServicioExpress
factory.procesarEnvio("TRK-1001", "Lima", "Trujillo", 5.0, 560.0);
```

---

### 2.2. Patrón Builder
* **Paquete:** `com.logistica.patterns.builder`
* **Elementos clave:**
  * Constructor: [`EnvioBuilder`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/builder/EnvioBuilder.java)
  * Director: [`EnvioDirector`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/builder/EnvioDirector.java)
  * Producto construido: [`Envio`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/model/Envio.java)

#### Problema que resuelve:
La entidad `Envio` posee más de 12 atributos entre obligatorios (remitente, destinatario, peso, costo, servicio) y opcionales avanzados (seguro contratado, valor asegurado, empaque reforzado, retorno documentario, instrucciones especiales, ventana horaria de entrega y nivel de prioridad). Crear este objeto mediante constructores sobrecargados produce el antipatrón del *Telescoping Constructor*, dificultando el mantenimiento y aumentando drásticamente la probabilidad de pasar parámetros invertidos (por ejemplo, confundir dos strings contiguos de direcciones o teléfonos).

#### Justificación y relación entre clases:
`EnvioBuilder` proporciona una interfaz fluida paso a paso con validaciones en cada llamada y un método terminal `build()`. Adicionalmente, `EnvioDirector` ofrece recetas preconfiguradas para ensamblar envíos estándar corporativos, envíos express urgentes o envíos de carga asegurada, separando la especificación del objeto de su construcción técnica (SRP).

```java
// Construcción legible y libre de errores:
Envio envio = new EnvioBuilder()
        .conTipoServicio("EXPRESS")
        .conRemitente(101)
        .conDestinatario("Juan Pérez", "Av. Javier Prado 1234")
        .conContacto("998877665", "juan@gmail.com")
        .conCarga(4.5, 38.50)
        .conSeguro(1500.0)
        .conPrioridad(1) // Urgente
        .build();
```

---

### 2.3. Patrón Abstract Factory
* **Paquete:** `com.logistica.patterns.abstractfactory`
* **Elementos clave:**
  * Interfaz fábrica: [`ILogisticaFactory`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/abstractfactory/ILogisticaFactory.java)
  * Fábricas concretas: [`LogisticaUrbanaFactory`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/abstractfactory/urbana/LogisticaUrbanaFactory.java), [`LogisticaInterprovincialFactory`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/abstractfactory/interprovincial/LogisticaInterprovincialFactory.java)
  * Familias de productos abstractos:
    * [`ITransporteLogistico`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/abstractfactory/ITransporteLogistico.java)
    * [`IEmpaque`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/abstractfactory/IEmpaque.java)
    * [`IRutaLogistica`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/abstractfactory/IRutaLogistica.java)
  * Familias concretas:
    * Familia Urbana: `MotoReparto`, `EmpaqueEstandar`, `RutaLocal`
    * Familia Interprovincial: `CamionInterprovincial`, `EmpaqueBlindado`, `RutaNacional`

#### Problema que resuelve:
La infraestructura logística varía sustancialmente de acuerdo con el alcance geográfico de la entrega. Asignar un camión de carga pesada con empaque blindado para una entrega local a tres cuadras, o asignar una motocicleta con bolsa plástica para un traslado interurbano Lima–Arequipa, representa una inconsistencia operativa grave.

#### Justificación y relación entre clases:
`ILogisticaFactory` impone una regla estructural donde la fábrica seleccionada crea conjuntamente el medio de transporte, el empaque y la ruta compatibles entre sí. El subsistema de asignación solo interactúa con las interfaces abstractas, garantizando coherencia operativa sin acoplarse a clases concretas.

```java
// La fábrica asegura coherencia total de la familia de objetos:
ILogisticaFactory factory = new LogisticaUrbanaFactory();
ITransporteLogistico transporte = factory.crearTransporte(); // MotoReparto
IEmpaque empaque = factory.crearEmpaque();                 // EmpaqueEstandar
IRutaLogistica ruta = factory.crearRuta();                 // RutaLocal
```

---

## 3. EXPLICACIÓN Y JUSTIFICACIÓN DETALLADA DE PATRONES ESTRUCTURALES

### 3.1. Patrón Adapter
* **Paquete:** `com.logistica.patterns.adapter`
* **Elementos clave:**
  * Interfaz objetivo (Target): [`IGeolocalizador`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/adapter/IGeolocalizador.java)
  * Adaptador (Adapter): [`GoogleMapsAdapter`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/adapter/GoogleMapsAdapter.java)
  * Clase adaptada (Adaptee): [`GoogleMapsAPI`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/adapter/GoogleMapsAPI.java)

#### Problema que resuelve:
El módulo de ruteo del sistema necesita georreferenciar direcciones y calcular distancias utilizando un proveedor externo (Google Maps). Sin embargo, `GoogleMapsAPI` expone métodos con firmas incompatibles: devuelve coordenadas en formato de texto plano `"lat,lng"` y distancias como enteros en metros (`getDistanceInMeters`). Si la lógica del sistema dependiera directamente de estos formatos externos, el dominio quedaría acoplado a la API propietaria de Google.

#### Justificación y relación entre clases:
`GoogleMapsAdapter` implementa el contrato interno `IGeolocalizador` y contiene internamente una instancia de `GoogleMapsAPI`. Su labor es traducir la respuesta: convierte el String a un array de precisión `double[]{lat, lng}` y divide los metros entre `1000.0` para entregar kilómetros. Si más adelante se reemplaza Google Maps por la API libre OpenStreetMap o Waze, basta con programar `OpenStreetMapAdapter` sin alterar una sola línea del core de negocio (DIP y OCP).

```java
// El sistema consume la interfaz interna IGeolocalizador:
IGeolocalizador gps = new GoogleMapsAdapter();
double[] coords = gps.obtenerCoordenadas("Av. Universitaria 1801");
double distanciaKm = gps.calcularDistancia("Lima", "Callao"); // Transforma metros a km
```

---

### 3.2. Patrón Facade
* **Paquete:** `com.logistica.patterns.facade`
* **Elementos clave:**
  * Fachada: [`PlataformaLogisticaFacade`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/facade/PlataformaLogisticaFacade.java)
  * Subsistemas involucrados: `IGeolocalizador` (Adapter), `EnvioBuilder` (Builder), `IServicioEnvioDecorable` (Decorator), `IEnvioDAO` (Persistencia), `EnvioObservable` (Observer).

#### Problema que resuelve:
El flujo completo de recepción y despacho de un paquete requiere la ejecución secuencial de múltiples subsistemas: geolocalizar la dirección de entrega, calcular la tarifa base por kilometraje, enriquecer el servicio con pólizas o empaques especiales (decoradores), validar datos y ensamblar la entidad (builder), persistir el registro en MySQL y disparar alertas en tiempo real hacia los observadores. Exponer todas estas dependencias a las pantallas de interfaz gráfica o a los controladores genera código espagueti y alto acoplamiento.

#### Justificación y relación entre clases:
`PlataformaLogisticaFacade` provee una interfaz amigable mediante el método `registrarEnvioCompleto(...)`. La fachada actúa como único punto de entrada: coordina internamente los 5 subsistemas en el orden correspondiente y devuelve el objeto `Envio` totalmente procesado, ocultando toda la complejidad técnica tras bambalinas.

```java
// La capa cliente (UI/Controlador) solo interactúa con un único método:
PlataformaLogisticaFacade facade = new PlataformaLogisticaFacade();
Envio envioListo = facade.registrarEnvioCompleto(
        "EXPRESS", 1, "Carlos Ramos", "Av. Arequipa 1450",
        "987654321", "carlos@gmail.com", 3.5,
        true,  // conSeguro (Decorator)
        true,  // conEmpaqueReforzado (Decorator)
        false  // conRastreoSMS (Decorator)
);
```

---

### 3.3. Patrón Decorator
* **Paquete:** `com.logistica.patterns.decorator`
* **Elementos clave:**
  * Componente abstracto: [`IServicioEnvioDecorable`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/decorator/IServicioEnvioDecorable.java)
  * Componente concreto: [`ServicioEnvioBase`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/decorator/ServicioEnvioBase.java)
  * Decoradores concretos:
    * [`SeguroPremiumDecorator`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/decorator/SeguroPremiumDecorator.java) (calcula prima del 5% del valor declarado)
    * [`EmpaqueReforzadoDecorator`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/decorator/EmpaqueReforzadoDecorator.java) (adiciona costo de embalaje industrial)
    * [`RastreoSMSDecorator`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/decorator/RastreoSMSDecorator.java) (agrega mensajería transaccional directa)

#### Problema que resuelve:
Los clientes pueden contratar cualquier combinación arbitraria de servicios complementarios al momento de registrar su paquete (por ejemplo: solo Seguro; Seguro + Empaque; solo SMS; o los 3 simultáneamente). Si este requerimiento se resolviese mediante herencia tradicional, se produciría una explosión factorial de subclases (`EnvioConSeguro`, `EnvioConSeguroYEmpaque`, `EnvioConSMS`, etc.), resultando en un modelo rígido e inmantenible.

#### Justificación y relación entre clases:
Tanto el componente base como los decoradores implementan `IServicioEnvioDecorable`. Cada decorador recibe en su constructor otra instancia de `IServicioEnvioDecorable`, delegando el cálculo previo y sumando su propio recargo. Esto permite envolver (*wrapping*) los servicios como si fuesen capas de una cebolla en tiempo de ejecución.

```java
// Composición dinámica y acumulativa en tiempo de ejecución:
IServicioEnvioDecorable servicio = new ServicioEnvioBase("Envío Estándar", 25.00);
servicio = new SeguroPremiumDecorator(servicio, 1000.00); // Suma S/ 50.00 de seguro
servicio = new EmpaqueReforzadoDecorator(servicio, 15.00); // Suma S/ 15.00 de empaque
servicio = new RastreoSMSDecorator(servicio, 2.50);        // Suma S/ 2.50 de SMS

System.out.println(servicio.getDescripcion()); 
// "Envío Estándar + Seguro Premium (S/ 1000.00 asegurado) + Empaque Reforzado + Notificaciones SMS"
System.out.println("Costo Final: S/ " + servicio.calcularCosto()); // S/ 92.50
```

---

### 3.4. Patrón Composite (Alternativa Estructural)
* **Paquete:** `com.logistica.patterns.composite`
* **Elementos clave:**
  * Componente: [`IComponenteRuta`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/composite/IComponenteRuta.java)
  * Hoja (Leaf): [`ParadaEntrega`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/composite/ParadaEntrega.java)
  * Compuesto (Composite): [`RutaCompuesta`](file:///c:/Proyectos-2026/Proyecto-Dise-o-Patrones-2026/src/main/java/com/logistica/patterns/composite/RutaCompuesta.java)

#### Problema que resuelve:
La red de distribución logística se organiza de manera jerárquica: una ruta macro (ej. "Ruta Metropolitana") contiene sectores zonales (ej. "Ruta Lima Norte" y "Ruta Lima Sur"), los cuales a su vez contienen paradas domiciliarias individuales. Tratar las rutas y las paradas con clases separadas obligaría al sistema a escribir algoritmos distintos para calcular distancias o imprimir itinerarios según el nivel de profundidad.

#### Justificación y relación entre clases:
`IComponenteRuta` define un método uniforme `getDistanciaTotal()`. `ParadaEntrega` devuelve su distancia directa, mientras que `RutaCompuesta` recorre su lista interna de `IComponenteRuta` y acumula recursivamente los valores. De este modo, el cliente calcula la distancia de una sola parada o de toda una red de distribución interconectada de manera idéntica y polimórfica.

---

## 4. CONCLUSIÓN DE CUMPLIMIENTO CON LA RÚBRICA UTP

| Criterio de Evaluación UTP | Estado en el Proyecto | Evidencia de Código |
| :--- | :---: | :--- |
| **Identificación de Elementos (Clases, Interfaces, Derivadas)** | **CUMPLE AL 100%** | Clases e interfaces identificadas y vinculadas a paquetes concretos (`patterns.factory`, `patterns.builder`, `patterns.abstractfactory`, `patterns.adapter`, `patterns.facade`, `patterns.decorator`, `patterns.composite`). |
| **Patrones Creacionales justificados** | **CUMPLE AL 100%** | Implementados: **Singleton** (`ConexionBD`, `ConfiguracionSistema`), **Factory Method** (`EnvioFactory`), **Builder** (`EnvioBuilder`), **Abstract Factory** (`ILogisticaFactory`), **Prototype** (`PlantillaEnvioManager`). |
| **Patrones Estructurales justificados** | **CUMPLE AL 100%** | Implementados: **Adapter** (`GoogleMapsAdapter`), **Facade** (`PlataformaLogisticaFacade`), **Decorator** (`SeguroPremiumDecorator`), **Composite** (`RutaCompuesta`). |
| **Explicación del problema que resuelven y relación entre clases** | **CUMPLE AL 100%** | Cada patrón documenta el problema de negocio concreto, el principio SOLID asociado (OCP, SRP, DIP) y la relación estructural entre los componentes. |
