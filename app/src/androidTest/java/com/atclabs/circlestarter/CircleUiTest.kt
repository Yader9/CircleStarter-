package com.atclabs.circlestarter

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test
import kotlin.math.PI

class CircleUiTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    //Caso 1 = Radio, r = 0, respuesta degenerada
    @Test
    fun testRadiusZero() {

        onView(withId(R.id.rbRadius)).perform(click())

        onView(withId(R.id.etRadius)).perform(typeText("0"), closeSoftKeyboard())
        onView(withId(R.id.etDiameter)).perform(typeText("5"), closeSoftKeyboard())
        onView(withId(R.id.etCircumference)).perform(typeText("5"), closeSoftKeyboard())
        onView(withId(R.id.btnCalculate)).perform(click())

        //Verifica el texto
        onView(withId(R.id.tvType)).check(matches(withText(containsString("Degenerada"))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString("0.00"))))
    }

    //Caso 2 = Radio, r = 5, repuesta valida
    @Test
    fun testRadius_TypeValid() {

        onView(withId(R.id.rbRadius)).perform(click())

        onView(withId(R.id.etRadius)).perform(typeText("5"), closeSoftKeyboard())
        onView(withId(R.id.etDiameter)).perform(typeText("5"), closeSoftKeyboard())
        onView(withId(R.id.etCircumference)).perform(typeText("5"), closeSoftKeyboard())

        val radius = 5.0
        val diameter = radius * 2
        val circumference = 2 * PI * radius
        val area = PI * radius * radius

        onView(withId(R.id.btnCalculate)).perform(click())

        //Verifica el texto
        onView(withId(R.id.tvType)).check(matches(withText(containsString("Válida"))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString(String.format("%.2f", radius)))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString(String.format("%.2f", diameter)))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString(String.format("%.2f", circumference)))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString(String.format("%.2f", area)))))
    }

    //Caso 3 = Diametro, d = 0, respuesta degenerada
    @Test
    fun testDiameterZero() {

        onView(withId(R.id.rbDiameter)).perform(click())

        onView(withId(R.id.etRadius)).perform(typeText("5"), closeSoftKeyboard())
        onView(withId(R.id.etDiameter)).perform(typeText("0"), closeSoftKeyboard())
        onView(withId(R.id.etCircumference)).perform(typeText("5"), closeSoftKeyboard())
        onView(withId(R.id.btnCalculate)).perform(click())

        //Verifica el texto
        onView(withId(R.id.tvType)).check(matches(withText(containsString("Degenerada"))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString("0.00"))))
    }

    //Caso 4 = Diametro, d = 5, respuesta valida
    @Test
    fun testDiameter_TypeValid() {

        onView(withId(R.id.rbDiameter)).perform(click())

        onView(withId(R.id.etRadius)).perform(typeText("5"), closeSoftKeyboard())
        onView(withId(R.id.etDiameter)).perform(typeText("5"), closeSoftKeyboard())
        onView(withId(R.id.etCircumference)).perform(typeText("5"), closeSoftKeyboard())

        val diameter = 5.0
        val radius = diameter / 2
        val circumference = 2 * PI * radius
        val area = PI * radius * radius

        onView(withId(R.id.btnCalculate)).perform(click())

        //Verifica el texto
        onView(withId(R.id.tvType)).check(matches(withText(containsString("Válida"))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString(String.format("%.2f", radius)))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString(String.format("%.2f", diameter)))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString(String.format("%.2f", circumference)))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString(String.format("%.2f", area)))))
    }

    //Caso 5 = Circunferencia, c = 0, respuesta = degenerada
    @Test
    fun testCircumferenceZero() {

        onView(withId(R.id.rbCircumference)).perform(click())

        onView(withId(R.id.etRadius)).perform(typeText("5"), closeSoftKeyboard())
        onView(withId(R.id.etDiameter)).perform(typeText("5"), closeSoftKeyboard())
        onView(withId(R.id.etCircumference)).perform(typeText("0"), closeSoftKeyboard())
        onView(withId(R.id.btnCalculate)).perform(click())

        //Verifica el texto
        onView(withId(R.id.tvType)).check(matches(withText(containsString("Degenerada"))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString("0.00"))))
    }

    //Caso 6 = Circunferencia, c = 5, respuesta = valida
    @Test
    fun testCircumference_TypeValid() {

        onView(withId(R.id.rbCircumference)).perform(click())

        onView(withId(R.id.etRadius)).perform(typeText("5"), closeSoftKeyboard())
        onView(withId(R.id.etDiameter)).perform(typeText("5"), closeSoftKeyboard())
        onView(withId(R.id.etCircumference)).perform(typeText("5"), closeSoftKeyboard())

        val circumference = 5.0
        val radius = circumference / (2 * PI)
        val diameter = radius * 2
        val area = PI * radius * radius

        onView(withId(R.id.btnCalculate)).perform(click())

        //Verifica el texto
        onView(withId(R.id.tvType)).check(matches(withText(containsString("Válida"))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString(String.format("%.2f", radius)))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString(String.format("%.2f", diameter)))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString(String.format("%.2f", circumference)))))
        onView(withId(R.id.tvResults)).check(matches(withText(containsString(String.format("%.2f", area)))))
    }
}