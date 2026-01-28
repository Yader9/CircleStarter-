# CircleStarter

[![Android CI](https://github.com/Yader9/CircleStarter-/actions/workflows/android.yml/badge.svg)](https://github.com/Yader9/CircleStarter-/actions/workflows/android.yml)

Proyecto Android (Kotlin + XML) para calcular propiedades de una circunferencia a partir de un valor ingresado por el usuario.
Este repositorio se utiliza como **starter académico** para prácticas de:
- Pruebas estáticas
- Validación de entradas y manejo de errores
- Pruebas unitarias (JUnit)
- Pruebas de instrumentación (Espresso)
- Automatización CI/CD con GitHub Actions

> **Nota:** El texto de la pantalla puede conservar “sin validación” por herencia del starter, pero en **Actividad 2** ya se implementaron validaciones completas.

---

## Equipo / Contexto académico

**Keiser University Latin American Campus**  
**Software Testing (Quality Assurance)**  
**Proyecto:** CircleStarter (Android – Kotlin/XML)  
**Fecha:** 27 de enero de 2026  
**Integrantes:** Yader Rafael Carrillo Jaime, Diego Narváez López, Zared Vega Zamora  
**Docente:** Msc. Walter de Jesús Marcia Lanzas

---

## Actividades implementadas

- **Actividad 1 — Prueba estática:** checklist RFU (01–15), hallazgos, severidad y recomendaciones.
- **Actividad 2 — Validación completa de entradas:** validación por modo, mensajes de error, formato de salida, manejo de errores, persistencia.
- **Actividad 3 — Pruebas unitarias (JUnit):** pruebas sobre el cálculo y clasificación.
- **Actividad 4 — Pruebas de instrumentación (Espresso):** pruebas UI por modo y verificación de resultados.
- **Actividad 5 — Automatización CI (GitHub Actions):** ejecución automática de pruebas en cada push y pull request.

---

## Objetivo de la aplicación

La app permite calcular:
- Tipo de circunferencia
- Radio (r)
- Diámetro (d)
- Circunferencia (C)
- Área (A)

Los cálculos se realizan siempre a partir del **radio final**, derivado del modo seleccionado.

---

## Modos de cálculo (RFU-01 / RFU-02 / RFU-09)

El usuario puede seleccionar el modo:
1. **Desde Radio (r)** *(modo por defecto)*
2. **Desde Diámetro (d)**
3. **Desde Circunferencia (C)**

Reglas:
- Solo el campo del modo activo es **requerido** y se valida/usa para el cálculo.
- Los otros campos pueden permanecer visibles, pero **se ignoran**.

Conversión a radio (r):
- Desde Radio: `r = valor`
- Desde Diámetro: `r = valor / 2`
- Desde Circunferencia: `r = valor / (2π)`

---

## Validaciones implementadas (RFU-03 a RFU-06)

Al presionar **Calcular**, el sistema valida el campo requerido:

### RFU-04 — Campo requerido no vacío
- Si está vacío o solo contiene espacios:
  - Se bloquea el cálculo.
  - Error en el campo: **“Este campo es obligatorio.”**

### RFU-05 — Formato numérico válido
- Se acepta únicamente número válido con **punto decimal (.)** como separador.
- Ejemplos inválidos: `abc`, `1,2,3`, `--5`, `2..5`
  - Se bloquea el cálculo.
  - Error: **“Ingrese un número válido.”**

> Nota: por `inputType="numberDecimal|numberSigned"` algunos teclados impiden escribir letras. Aun así, la validación se mantiene para pegado/autocompletado/automatización.

### RFU-06 — Valor no negativo
- Si el valor es negativo:
  - Se bloquea el cálculo.
  - Error: **“El valor no puede ser negativo.”**

---

## Reglas de clasificación y cálculo (RFU-07 / RFU-08 / RFU-10)

Con el radio final (r):

**Tipo**
- Si `r > 0` → **“Válida”**
- Si `r = 0` → **“Degenerada”**
- Si `r < 0` → no aplica (se bloquea por RFU-06)

**Fórmulas**
- Diámetro: `d = 2r`
- Circunferencia: `C = 2πr`
- Área: `A = πr²`

---

## Formato de salida (RFU-11)

Los resultados se muestran con:
- **Redondeo a 2 decimales** para `r`, `d`, `C`, `A`
- Etiqueta + valor (ej.: `Radio (r): 2.00`)

---

## Manejo de errores (RFU-12)

Ante cualquier error no contemplado (conversión/cálculo):
- La app **no debe cerrarse**
- Se muestra mensaje general:
  - **“Ocurrió un error. Revise los datos ingresados.”**

---

## Limpieza de errores (RFU-13)

Cuando el usuario modifica el campo requerido después de un error:
- El mensaje se elimina cuando el valor vuelve a ser válido (en tiempo real o al recalcular).

---

## Política de estado al cambiar modo (RFU-14)

Al cambiar entre **Radio / Diámetro / Circunferencia**, la app:
- Limpia mensajes de error previos.
- Limpia los resultados (vuelve a **“Tipo: -”** y **“Resultados: -”**).

---

## Persistencia en rotación (RFU-15 — opcional evaluable)

Al rotar el dispositivo, la app mantiene:
- Modo seleccionado
- Valores ingresados
- Resultados mostrados (si existían)

Implementación: `onSaveInstanceState` (o alternativa con ViewModel).

---

## Estructura relevante del proyecto (trazabilidad rápida)

- **UI + validaciones + estado:**
  - `app/src/main/java/com/atclabs/circlestarter/MainActivity.kt`
- **Cálculos (d, C, A) y tipo:**
  - `app/src/main/java/com/atclabs/circlestarter/CircleCalculator.kt`
- **Layout:**
  - `app/src/main/res/layout/activity_main.xml`
- **Mensajes y plantillas (incluye placeholders):**
  - `app/src/main/res/values/strings.xml`
- **CI (workflow):**
  - `.github/workflows/android.yml`

---

## Cómo ejecutar

1. Abrir el proyecto en **Android Studio**.
2. Sincronizar Gradle.
3. Ejecutar en emulador o dispositivo (**Run ▶ app**).
