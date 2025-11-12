# Farmacia Salud Total

Simulación de operaciones de una farmacia con múltiples empleados trabajando en paralelo.

## Descripción

Sistema de simulación que modela el funcionamiento de una farmacia durante una jornada laboral (10:00 a.m. - 10:00 p.m.) con tres tipos de empleados:

- **Cajera**: Atiende clientes y genera recetas
- **Farmacéutico**: Procesa y surte las recetas
- **Reponedor**: Revisa y reabastece el inventario

## Ejecución

```bash
kotlinc src/Main.kt -include-runtime -d pharmacy.jar
java -jar pharmacy.jar
```

O usando un IDE como IntelliJ IDEA, simplemente ejecuta la función `main()` en `Main.kt`.

## Estructura del Proyecto

```
src/
├── core/           # Lógica principal de la simulación
├── employees/      # Clases de empleados (Cajera, Farmacéutico, Reponedor)
├── models/         # Modelos de datos (Employee, Medicine, Recipe)
└── shared/         # Recursos compartidos (Warehouse, QueueRecipes)
```
