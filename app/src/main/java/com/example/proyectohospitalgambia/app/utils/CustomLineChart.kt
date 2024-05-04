package com.example.proyectohospitalgambia.app.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.example.proyectohospitalgambia.R
import com.github.mikephil.charting.charts.LineChart

/**
 * Clase CustomLineChart que extiende de LineChart para personalizar el gráfico de líneas.
 *
 * @property textPaint Objeto Paint para dibujar el texto en el gráfico.
 * @constructor Crea una instancia de CustomLineChart.
 *
 * @param context Contexto en el que se utiliza la vista.
 * @param attrs Conjunto de atributos de estilo.
 */
class CustomLineChart(context: Context, attrs: AttributeSet) : LineChart(context, attrs) {

    /**
     * Objeto Paint para dibujar el texto en el gráfico.
     */
    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 40f
        textAlign = Paint.Align.LEFT
    }

    /**
     * Método onDraw que se llama cuando el sistema necesita dibujar el gráfico.
     *
     * @param canvas Lienzo en el que se dibuja el gráfico.
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val y = viewPortHandler.contentTop() + 40f

        // Dibujar "Inicio de datos" a la izquierda
        val xInicio = viewPortHandler.contentLeft()
        canvas.drawText(context.getString(R.string.inicio_de_datos), xInicio, y, textPaint)

        // Dibujar "Últimos datos" a la derecha
        val xFinal =
            viewPortHandler.contentRight() - textPaint.measureText(context.getString(R.string.Ultimos_datos))
        canvas.drawText(context.getString(R.string.Ultimos_datos), xFinal, y, textPaint)
    }
}