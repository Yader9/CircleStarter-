package com.atclabs.circlestarter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.atclabs.circlestarter.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale
import kotlin.math.PI

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    /**
     * Acepta números con punto decimal (.) como separador.
     * Ejemplos válidos: 2 | 2. | 2.50 | .5 | 0.5 | -2 (se detecta como negativo -> RFU-06)
     * Ejemplos inválidos: abc | 1,2,3 | --5 | 2..5
     */
    private val numberRegex = Regex("^-?(\\d+(\\.\\d*)?|\\.\\d+)$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        if (savedInstanceState != null) {
            restoreUiState(savedInstanceState)
        }

        setupListeners()
    }

    private fun setupListeners() {
        b.btnCalculate.setOnClickListener { onCalculateClicked() }

        // RFU-14: cambio de modo -> limpiar errores y limpiar resultados (política consistente).
        b.rgMode.setOnCheckedChangeListener { _, _ ->
            clearAllFieldErrors()
            clearResults()

            // Opcional: guiar al usuario al campo requerido
            val (_, et) = requiredFieldForMode(currentMode())
            et.requestFocus()
        }

        // RFU-13: al corregir el campo requerido, remover error si ya es válido.
        b.etRadius.doAfterTextChanged {
            if (currentMode() == Mode.RADIUS) clearErrorIfNowValid(b.tilRadius, b.etRadius)
        }
        b.etDiameter.doAfterTextChanged {
            if (currentMode() == Mode.DIAMETER) clearErrorIfNowValid(b.tilDiameter, b.etDiameter)
        }
        b.etCircumference.doAfterTextChanged {
            if (currentMode() == Mode.CIRCUMFERENCE) clearErrorIfNowValid(b.tilCircumference, b.etCircumference)
        }
    }

    private fun onCalculateClicked() {
        // Limpia errores previos
        clearAllFieldErrors()

        // ✅ MUY IMPORTANTE: si la validación falla, no se recalcula (return),
        // así que limpiamos resultados aquí para que no quede “pegado” un resultado anterior.
        clearResults()

        try {
            val mode = currentMode()

            // RFU-02/03/04/05/06/09: validar campo requerido y obtener radio final
            val radius = validateAndGetRadius(mode) ?: return

            val result = CircleCalculator.computeFromRadius(radius)
            showResult(result)
        } catch (e: Exception) {
            // RFU-12: no crashear ante errores inesperados.
            showGeneralError()
        }
    }

    private fun currentMode(): Mode = when (b.rgMode.checkedRadioButtonId) {
        b.rbDiameter.id -> Mode.DIAMETER
        b.rbCircumference.id -> Mode.CIRCUMFERENCE
        else -> Mode.RADIUS
    }

    /**
     * RFU-02: solo se valida el campo requerido.
     * RFU-04/05/06: obligatorio, numérico válido y no negativo.
     * RFU-09: conversión a radio según modo.
     */
    private fun validateAndGetRadius(mode: Mode): Double? {
        val (til, et) = requiredFieldForMode(mode)
        val raw = et.text?.toString().orEmpty().trim()

        return when (val validation = validateRawRequiredValue(raw)) {
            is ValidationResult.Invalid -> {
                til.error = getString(validation.errorResId)
                et.requestFocus()
                null
            }
            is ValidationResult.Valid -> {
                til.error = null
                val value = validation.value
                when (mode) {
                    Mode.RADIUS -> value
                    Mode.DIAMETER -> value / 2.0
                    Mode.CIRCUMFERENCE -> value / (2.0 * PI)
                }
            }
        }
    }

    private fun requiredFieldForMode(mode: Mode): Pair<TextInputLayout, TextInputEditText> =
        when (mode) {
            Mode.RADIUS -> b.tilRadius to b.etRadius
            Mode.DIAMETER -> b.tilDiameter to b.etDiameter
            Mode.CIRCUMFERENCE -> b.tilCircumference to b.etCircumference
        }

    private fun validateRawRequiredValue(raw: String): ValidationResult {
        // RFU-04: requerido no vacío (incluye espacios).
        if (raw.isBlank()) return ValidationResult.Invalid(R.string.error_required)

        // RFU-05: formato numérico válido (solo punto decimal).
        if (!numberRegex.matches(raw)) return ValidationResult.Invalid(R.string.error_invalid_number)

        val value = raw.toDoubleOrNull()
        if (value == null || value.isNaN() || value.isInfinite()) {
            return ValidationResult.Invalid(R.string.error_invalid_number)
        }

        // RFU-06: no negativo.
        if (value < 0.0) return ValidationResult.Invalid(R.string.error_negative)

        return ValidationResult.Valid(value)
    }

    private fun showResult(result: CircleResult) {
        // RFU-11: 2 decimales, etiquetas y valor.
        b.tvType.text = getString(R.string.type_value, result.type)
        b.tvResults.text = getString(
            R.string.results_template,
            format2(result.radius),
            format2(result.diameter),
            format2(result.circumference),
            format2(result.area)
        )
    }

    private fun format2(value: Double): String = String.format(Locale.US, "%.2f", value)

    private fun clearAllFieldErrors() {
        b.tilRadius.error = null
        b.tilDiameter.error = null
        b.tilCircumference.error = null
    }

    private fun clearResults() {
        // Estos strings ya existen en tu strings.xml (“Tipo: -” / “Resultados: -”)
        b.tvType.text = getString(R.string.tipo)
        b.tvResults.text = getString(R.string.resultados)
    }

    private fun clearErrorIfNowValid(til: TextInputLayout, et: TextInputEditText) {
        if (til.error.isNullOrBlank()) return
        val raw = et.text?.toString().orEmpty().trim()
        if (validateRawRequiredValue(raw) is ValidationResult.Valid) {
            til.error = null
        }
    }

    private fun showGeneralError() {
        Snackbar.make(b.root, getString(R.string.error_general), Snackbar.LENGTH_LONG).show()
    }

    // RFU-15 (opcional): persistencia en rotación.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_MODE_ID, b.rgMode.checkedRadioButtonId)
        outState.putString(KEY_RADIUS_TEXT, b.etRadius.text?.toString().orEmpty())
        outState.putString(KEY_DIAMETER_TEXT, b.etDiameter.text?.toString().orEmpty())
        outState.putString(KEY_CIRCUMFERENCE_TEXT, b.etCircumference.text?.toString().orEmpty())
        outState.putString(KEY_TV_TYPE_TEXT, b.tvType.text?.toString().orEmpty())
        outState.putString(KEY_TV_RESULTS_TEXT, b.tvResults.text?.toString().orEmpty())
    }

    private fun restoreUiState(state: Bundle) {
        b.rgMode.check(state.getInt(KEY_MODE_ID, b.rbRadius.id))
        b.etRadius.setText(state.getString(KEY_RADIUS_TEXT, ""))
        b.etDiameter.setText(state.getString(KEY_DIAMETER_TEXT, ""))
        b.etCircumference.setText(state.getString(KEY_CIRCUMFERENCE_TEXT, ""))
        b.tvType.text = state.getString(KEY_TV_TYPE_TEXT, getString(R.string.tipo))
        b.tvResults.text = state.getString(KEY_TV_RESULTS_TEXT, getString(R.string.resultados))
    }

    private sealed class ValidationResult {
        data class Valid(val value: Double) : ValidationResult()
        data class Invalid(val errorResId: Int) : ValidationResult()
    }

    companion object {
        private const val KEY_MODE_ID = "key_mode_id"
        private const val KEY_RADIUS_TEXT = "key_radius_text"
        private const val KEY_DIAMETER_TEXT = "key_diameter_text"
        private const val KEY_CIRCUMFERENCE_TEXT = "key_circumference_text"
        private const val KEY_TV_TYPE_TEXT = "key_tv_type_text"
        private const val KEY_TV_RESULTS_TEXT = "key_tv_results_text"
    }
}

enum class Mode { RADIUS, DIAMETER, CIRCUMFERENCE }
