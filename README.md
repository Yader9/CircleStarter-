# CircleStarter

![Android CI](https://github.com/Yader9/CircleStarter-/actions/workflows/android.yml/badge.svg)

# CircleStarter — Actividad 2 (Validación completa de entradas)

Este proyecto starter se corrigió para cumplir con los **RFU** relacionados a:
- Validación de entradas
- Formato de salida
- Manejo de errores
- Consistencia de estado (incluye rotación de pantalla)

La app calcula propiedades de una circunferencia (**radio, diámetro, circunferencia y área**) a partir de un valor ingresado por el usuario, según el modo seleccionado.

---

## Modos de cálculo (RFU-01 / RFU-02 / RFU-09)

El usuario puede seleccionar el modo de cálculo:

1. **Desde Radio (r)** *(modo por defecto)*
2. **Desde Diámetro (d)**
3. **Desde Circunferencia (C)**

Según el modo seleccionado:
- **Solo el campo correspondiente es requerido** y es el único que se valida/usa para el cálculo.
- Los otros campos pueden permanecer visibles, pero **se ignoran**.

Conversión a radio (r):
- Desde Radio: "r = valor"
- Desde Diámetro: "r = valor/2"
- Desde Circunferencia: "r = valor/(2π)"

---

## Validaciones implementadas (RFU-03 a RFU-06)

Al presionar **Calcular**, el sistema valida el campo requerido:

### RFU-04 — Campo requerido no vacío
- Si el campo requerido está vacío o solo contiene espacios:
  - Se bloquea el cálculo.
  - Se muestra error en el campo: **“Este campo es obligatorio.”**

### RFU-05 — Formato numérico válido
- Se acepta únicamente número válido con **punto decimal (.)** como separador.
- Ejemplos inválidos: "abc", "1,2,3", "--5", "2..5"
  - Se bloquea el cálculo.
  - Se muestra: **“Ingrese un número válido.”**

> Nota: algunos teclados (por "inputType") impiden escribir letras directamente, pero la validación sigue siendo necesaria por pegado, automatización, accesibilidad y casos edge.

### RFU-06 — Valor no negativo
- Si el valor es negativo:
  - Se bloquea el cálculo.
  - Se muestra: **“El valor no puede ser negativo.”**

---

## Reglas de clasificación y cálculo (RFU-07 / RFU-08 / RFU-10)

Con el radio final (r):

- **Tipo**:
  - Si "r > 0" → **“Válida”**
  - Si "r = 0" → **“Degenerada”**
  - Si "r < 0" → *no aplica* (se bloquea por RFU-06)

- **Fórmulas**:
  - Diámetro: "d = 2r"
  - Circunferencia: "C = 2πr"
  - Área: "A = πr²"

---

## Formato de salida (RFU-11)

Los resultados se muestran en pantalla con:
- **Redondeo a 2 decimales** para "r", "d", "C", "A".
- Etiquetas + valor (ejemplo: "Radio (r): 2.00").

---

## Manejo de errores (RFU-12)

Ante cualquier error no contemplado (conversión/cálculo):
- La app **no debe cerrarse**.
- Se muestra mensaje general:
  - **“Ocurrió un error. Revise los datos ingresados.”**

---

## Limpieza de errores (RFU-13)

Cuando el usuario modifica el **campo requerido** después de un error:
- El mensaje de error se elimina si el valor ya es válido (en tiempo real).

---

## Política de estado al cambiar modo (RFU-14)

Al cambiar entre **Radio / Diámetro / Circunferencia**, la app:

- Limpia mensajes de error previos.
- Limpia los resultados mostrados (vuelve a **“Tipo: -”** y **“Resultados: -”**).

---

## Persistencia en rotación (RFU-15 — opcional evaluable)

Si el usuario rota el dispositivo, la app mantiene:
- Modo seleccionado
- Valores ingresados
- Resultados mostrados (si existían)

Implementación: "onSaveInstanceState" (o alternativa con ViewModel).

---

## Cómo probar rápidamente (manual)

1. **Vacío** + Calcular → “Este campo es obligatorio.”
2. **-1** + Calcular → “El valor no puede ser negativo.”
3. **0** + Calcular → Tipo “Degenerada” y todos los resultados `0.00`
4. **2** + Calcular → Tipo “Válida” y valores positivos con 2 decimales
5. Cambiar modo → se limpian errores y resultados
6. Rotar pantalla → se conserva modo, entradas y resultados
