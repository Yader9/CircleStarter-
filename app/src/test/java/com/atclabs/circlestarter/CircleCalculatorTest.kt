package com.atclabs.circlestarter

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.PI

class CircleCalculatorTest {

    @Test
    fun testcomputeFromRadius_Valid() {
        // Criculo valido con redio positivo
        val result = CircleCalculator.computeFromRadius(5.0)
        assertEquals("Válida", result.type)
    }

    @Test
    fun testcomputeFromRadius_Denerated() {
        //Circulo degenerado con radio cero
        val result = CircleCalculator.computeFromRadius(0.0)
        assertEquals("Degenerada", result.type)
    }

    @Test
    fun testcomputeFromRadius_RadiusStored() {
        //El radio se mantiene en el resultado
        val result = CircleCalculator.computeFromRadius(5.0)
        assertEquals(5.0, result.radius, 0.0)
    }

    @Test
    fun testcomputeFromRadius_CircumferenceCalculation() {
        //Verificar el calculo de la circunferencia
        val r = 2.0
        val result = CircleCalculator.computeFromRadius(r)
        assertEquals(2* PI * r, result.circumference, 0.0)
    }

    @Test
    fun testcomputeFromRadius_CircumferenceGreaterThanDiameter() {
        //La circunferencia debe ser mayor que el diametro para r > 0
        val result = CircleCalculator.computeFromRadius(3.0)
        assertTrue(result.circumference > result.diameter)
    }

    @Test
    fun testcomputeFromRadius_AreaCalculation() {
        //Verificar el calculo de area
        val r = 3.0
        val result = CircleCalculator.computeFromRadius(r)
        assertEquals(PI * r * r, result.area, 0.0)
    }

    @Test
    fun testcomputeFromRadius_AreaNeverNegative() {
        //El area nunca debe ser negativa
        val result = CircleCalculator.computeFromRadius(1.0)
        assertFalse(result.area < 0)
    }

    @Test
    fun testcomputeFromRadius_DiameterGreaterThanRadius() {
        //El diametro siempre debe ser mayor o igual al radio
        val result = CircleCalculator.computeFromRadius(4.0)
        assertTrue(result.diameter >= result.radius)
    }

    @Test
    fun testcomputeFromRadius_TypeIsValidOrDegenerated() {
        //El tipo solo puede ser valida o degenerada
        val valid = CircleCalculator.computeFromRadius(2.0)
        val degenerated = CircleCalculator.computeFromRadius(0.0)
        assertTrue(valid.type == "Válida")
        assertTrue(degenerated.type == "Degenerada")
        assertFalse(valid.type == "Degenerada")
    }

    @Test
    fun testcomputeFromRadius_ResultExists() {
        //El resultado no debe ser nulo
        val result = CircleCalculator.computeFromRadius(1.0)
        assertTrue(true)
        assertFalse(result.type.isEmpty())
    }
}