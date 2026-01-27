# CircleStarter — Actividad 2 (Validación completa de entradas)

Este proyecto starter se corrigió para cumplir con los RFU relacionados a **validación de entradas**, **formato de salida**, **manejo de errores** y **estado**.

## Política de estado al cambiar modo (RFU-14)
Al cambiar entre **Radio / Diámetro / Circunferencia**, la app:

- Limpia mensajes de error previos.
- Limpia los resultados mostrados (vuelve a “Tipo: -” y “Resultados: -”).

Si prefieres **conservar** los resultados al cambiar modo, elimina la llamada a `clearResults()` dentro del listener `rgMode.setOnCheckedChangeListener` en `MainActivity.kt`.
