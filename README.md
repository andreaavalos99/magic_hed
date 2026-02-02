# Magic Hed Store

Proyecto personal en desarrollo para construir una plataforma web de **Magic Hed** desde cero, combinando **Ingeniería en Sistemas** (backend + base de datos) con **Diseño Gráfico / UX-UI** (interfaz y experiencia).

**Estado actual:** Work in progress  
El proyecto se construye de forma incremental. La idea es mostrar el proceso y la evolución del sistema (no solo el resultado final).

---

## Objetivo

- Desarrollar una **tienda online** para vender productos de Magic Hed.
- Diseñar y gestionar una **base de datos propia** (modelo relacional + persistencia).
- Aplicar arquitectura en capas, buenas prácticas y evolución iterativa.
- Integrar decisiones de **UX/UI** con el funcionamiento real del sistema.

---

## Secciones del sitio (plan)

1. **Tienda online**  (principal)
   - Catálogo de productos
   - Detalle de producto
   - Estados (activo / preorden)
   - Persistencia en base de datos
   - Importación de productos desde archivo

2. **Comisiones** (próximamente)
   - Sección pensada como sistema de encargos
   - A futuro: turnos, solicitudes y seguimiento

3. **Información personal** (próximamente)
   - Presentación de la marca y la autora

---

## 🛠️ Tecnologías

**Backend**
- Java 17
- Spring Boot
- Spring Web (MVC)
- Spring Data JPA (Hibernate)
- Maven

**Base de datos**
- H2 (desarrollo local)
- PostgreSQL (planificado para un entorno más real)

**Frontend**
- HTML / CSS
- Thymeleaf (inicial)
- UX/UI diseñado por la autora

**Herramientas**
- Git / GitHub

---

## Base de datos propia (persistencia)

Este proyecto incluye un **modelo de datos propio** y persistencia real.

Se trabaja con:
- Modelado de entidades del dominio
- Relaciones y reglas de negocio
- ORM (JPA / Hibernate)
- Repositorios para acceso a datos
- Separación entre **dominio / servicio / controlador**

El objetivo no es solo “guardar datos”, sino **entender el diseño relacional** y cómo se conecta con la lógica del sistema.

---

## Modelo de datos (en evolución)

La base se construye de forma incremental:

### Iteración 1 (MVP)
- **Product**
  - `id`, `name`, `description`, `price`, `active`, `preorder`, `productionNote`, `imageUrl`

### Iteración 2 (estructura real de tienda)
- **Category**
  - `id`, `name`, `active`
- Relación: **Product → Category (Many-to-One)**

### Iteración 3 (nivel tienda completa)
- **Order**
  - `id`, `createdAt`, `status`, `total`
- **OrderItem**
  - `id`, `quantity`, `unitPrice`, `product`
- Relaciones: **Order → OrderItem**, **OrderItem → Product**

---

## Ciclo de desarrollo (metodología)

El proyecto avanza en ciclos cortos para mantener foco y generar progreso visible:

1) Definir una necesidad concreta (ej: “guardar productos”)  
2) Ajustar el modelo de datos (entidad/relación)  
3) Implementar persistencia (JPA + repositorio)  
4) Agregar lógica en servicio (reglas + validaciones)  
5) Exponerlo en la web (controller + view)  
6) Documentar el cambio (README + commits claros)
