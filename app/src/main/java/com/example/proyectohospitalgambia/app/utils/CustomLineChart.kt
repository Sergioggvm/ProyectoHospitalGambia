package com.example.proyectohospitalgambia.app.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.github.mikephil.charting.charts.LineChart

class CustomLineChart(context: Context, attrs: AttributeSet) : LineChart(context, attrs) {

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 40f
        textAlign = Paint.Align.LEFT
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val y = viewPortHandler.contentTop() + 40f

        // Dibujar "Inicio de datos" a la izquierda
        val xInicio = viewPortHandler.contentLeft()
        canvas.drawText("Inicio de datos", xInicio, y, textPaint)

        // Dibujar "Últimos datos" a la derecha
        val xFinal = viewPortHandler.contentRight() - textPaint.measureText("Últimos datos")
        canvas.drawText("Últimos datos", xFinal, y, textPaint)
    }
}