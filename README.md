# Carlos Verbel Garcia - 2462 
# Jose Herazo Dovale - 2462
# Jorge Gaitan - 2461
# Megaferia - Refactorización a Arquitectura MVC con SOLID

## Descripción del Proyecto
Este proyecto implementa un sistema de gestión para la primera gran feria del libro en Barranquilla, con una arquitectura Model-View-Controller (MVC) siguiendo los principios SOLID.

## Estructura del Proyecto

### 1. **Models (Modelos)**
Los modelos representan las entidades del sistema:

- **`Person.java`** - Clase abstracta base para todas las personas
  - **`Author.java`** - Autores de libros
  - **`Manager.java`** - Gerentes de editoriales
  - **`Narrator.java`** - Narradores de audiolibros

- **`Book.java`** - Clase abstracta base para libros
  - **`PrintedBook.java`** - Libros impresos
  - **`DigitalBook.java`** - Libros digitales
  - **`Audiobook.java`** - Audiolibros

- **`Stand.java`** - Stands en la feria
- **`Publisher.java`** - Editoriales

### 2. **Response System**
- **`Response<T>.java`** - Clase genérica que encapsula respuestas del servidor
- **`StatusCode.java`** - Enumeración con códigos de estado HTTP

### 3. **Storage/Repository Pattern**
- **`StandStorage.java`** - Almacenamiento de stands
- **`PersonStorage.java`** - Almacenamiento de personas (autores, gerentes, narradores)
- **`PublisherStorage.java`** - Almacenamiento de editoriales
- **`BookStorage.java`** - Almacenamiento de libros

Todos los Storage usan el patrón Singleton y soportan el patrón Observer.

### 4. **Controllers (Controladores)**
- **`StandController.java`** - Gestión de stands
- **`AuthorController.java`** - Gestión de autores
- **`ManagerController.java`** - Gestión de gerentes
- **`NarratorController.java`** - Gestión de narradores
- **`PublisherController.java`** - Gestión de editoriales (con validación de NIT)
- **`BookController.java`** - Gestión de libros (impreso, digital, audio)
- **`StandPurchaseController.java`** - Gestión de compras de stands
- **`QueryController.java`** - Consultas adicionales (búsqueda por autor, formato, etc.)

Todos los controladores retornan `Response<T>` con validaciones completas.

### 5. **View (Vista)**
- **`MegaferiaFrame.java`** - Interfaz gráfica principal (mantiene el diseño visual existente)
- **`Main.java`** - Archivo principal para ejecutar la aplicación

### 6. **Observer Pattern**
- **`Observer.java`** - Interfaz para el patrón observador

## Validaciones Implementadas

### Stands
- ✅ ID único, >= 0, máximo 15 dígitos
- ✅ Precio superior a 0

### Personas (Autores, Gerentes, Narradores)
- ✅ ID único, >= 0, máximo 15 dígitos
- ✅ Nombre y apellido no vacíos

### Editoriales
- ✅ NIT único con formato XXX.XXX.XXX-X
- ✅ Gerente válido (debe existir previamente)
- ✅ Nombre y dirección no vacíos

### Libros
- ✅ ISBN único con formato XXX-X-XX-XXXXXX-X
- ✅ Autores válidos (deben existir previamente)
- ✅ Sin autores duplicados en el mismo libro
- ✅ Editorial válida (debe existir previamente)
- ✅ Narrador válido para audiolibros
- ✅ Valor superior a 0
- ✅ Campos no vacíos (excepto hipervínculo)

### Compras de Stands
- ✅ Stands y editoriales válidos
- ✅ Sin stands duplicados
- ✅ Sin editoriales duplicadas

### Ordenamiento
- ✅ Stands ordenados por ID
- ✅ Personas ordenadas por ID
- ✅ Editoriales ordenadas por NIT
- ✅ Libros ordenados por ISBN
- ✅ Búsquedas por autor/formato ordenadas por ISBN

## Principios SOLID Implementados

### Single Responsibility Principle (SRP)
- Cada controlador tiene una única responsabilidad
- Los Storage manejan la persistencia
- Los Models contienen solo la lógica de dominio
- La View solo maneja la presentación

### Open/Closed Principle (OCP)
- Las clases están abiertas para extensión pero cerradas para modificación
- Nuevos tipos de libros o personas pueden añadirse sin modificar código existente
- Uso de herencia (Book, Person) para extensibilidad

### Liskov Substitution Principle (LSP)
- PrintedBook, DigitalBook, Audiobook pueden usarse donde se espere Book
- Author, Manager, Narrator pueden usarse donde se espere Person
- Métodos en subclases mantienen contrato de clases base

### Interface Segregation Principle (ISP)
- Observer contiene solo un método (update())
- Controllers especializados en operaciones específicas
- No hay métodos innecesarios en interfaces

### Dependency Inversion Principle (DIP)
- Controllers dependen de abstracciones (Storage)
- Storage usa Singleton para inversión de control
- Response<T> es una abstracción genérica

## Patrones de Diseño Implementados

### 1. Patrón Prototype
Los modelos implementan Cloneable para permitir copias de objetos cuando se retornan en Response.

### 2. Patrón Observer
Los Storage notifican a los observadores cuando hay cambios, permitiendo actualización automática de la UI.

### 3. Patrón Singleton
Todos los Storage usan Singleton para garantizar una única instancia en la aplicación.

### 4. Patrón Repository
Storage actúan como repositorios centralizados para acceso a datos.

## Cómo Ejecutar

```bash
java core.Main
```

El archivo `Main.java` inicia la aplicación con la interfaz gráfica.

## Integrantes
[Añadir nombres de integrantes y NRC aquí]

## Notas sobre la Refactorización

1. **Vista**: Se mantiene el diseño visual original sin cambios
2. **Lógica**: Toda la lógica se movió a controladores
3. **Validaciones**: Implementadas completamente en los controladores
4. **Respuestas**: Sistema de Response para manejar éxito/error
5. **Almacenamiento**: Patrón Repository para acceso a datos
6. **Separación de preocupaciones**: Cada capa tiene responsabilidades claras

## Avance de la Refactorización

### Completado
- ✅ Crear Response y StatusCode
- ✅ Refactorizar modelos con clone()
- ✅ Crear Storage/Repository para todos los tipos
- ✅ Crear todos los controladores con validaciones
- ✅ Crear QueryController para consultas
- ✅ Crear Main.java
- ✅ Refactorizar métodos de evento para Stands, Autores, Gerentes, Narradores y Editoriales

### En Progreso
- 🔄 Refactorizar MegaferiaFrame completamente para usar controladores

### Pendiente
- ⏳ Integración completa de observadores en UI
- ⏳ Pruebas unitarias
