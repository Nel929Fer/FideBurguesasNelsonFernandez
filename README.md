# FideBurguesas

Sistema de punto de venta (POS) para un restaurante de hamburguesas, desarrollado como Proyecto Final del curso **SC-303 Programación Cliente/Servidor Concurrente** — Universidad Fidélitas.

**Autor:** Nelson Andrés Fernández Espinoza

## Descripción

Aplicación de escritorio en Java (Swing) que permite a un cajero iniciar sesión, gestionar productos y combos, crear órdenes, calcular totales automáticamente, generar facturas con impuesto y exportarlas a archivo.

## Estado del proyecto: Avance 2

Este avance cubre 6 de las 8 historias de usuario definidas en el Avance 1:

1. Iniciar sesión con usuario y contraseña
2. Registrar productos y combos
3. Crear una orden seleccionando productos y combos
4. Calcular el total de la orden automáticamente
5. Generar una factura a partir de una orden
6. Exportar la factura a un archivo de texto

Pendientes para el siguiente avance: monitor de cocina (historias 7 y 8), que se implementará junto con los temas de Multihilos y Redes del curso.

## Conceptos de POO demostrados

- **Clases y objetos:** Usuario, Producto, Combo, Orden, DetalleOrden, Factura, Cocina
- **Herencia:** `Producto` y `Combo` extienden de la clase abstracta `ItemMenu`
- **Polimorfismo:** `DetalleOrden` referencia un `ItemMenu` (Producto o Combo indistintamente); cada uno sobrescribe `toString()`
- **Excepciones propias:** `CredencialesInvalidasException`, `OrdenVaciaException`
- **Colecciones genéricas:** `ArrayList<Producto>`, `ArrayList<DetalleOrden>`
- **Interfaz gráfica:** Swing (JFrame, JTable, JOptionPane), siguiendo los prototipos de Figma del Avance 1
- **Manejo de archivos:** exportación de factura a `.txt` mediante `FileWriter`

## Cómo ejecutar

1. Abrir el proyecto en NetBeans IDE.
2. Verificar que la clase principal (Run → Set Project Configuration) sea `Main`.
3. Ejecutar.
4. Usuario de prueba: `nelson` / contraseña: `1234`.

## Estructura

Todas las clases están en el paquete por defecto (`src/`), incluyendo el modelo de datos, las excepciones, la clase `Sistema` (datos compartidos en memoria) y las ventanas Swing (`Ventana*.java`).
