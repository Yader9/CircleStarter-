package com.atclabs.circlestarter

import kotlin.math.PI

object CircleCalculator {

    fun computeFromRadius(r: Double): CircleResult {

        require(r >= 0.0) { "El radio no puede ser negativo." }

        val type = when {
            r == 0.0 -> "Degenerada"
            else -> "Válida"
        }

        val diameter = 2.0 * r
        val circumference = 2.0 * PI * r
        val area = PI * r * r

        return CircleResult(
            type = type,
            radius = r,
            diameter = diameter,
            circumference = circumference,
            area = area
        )
    }
}

data class CircleResult(
    val type: String,
    val radius: Double,
    val diameter: Double,
    val circumference: Double,
    val area: Double
)
