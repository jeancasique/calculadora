package com.example.calculadora
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private var peso: Int = 50
    private var altura: Int = 150
    private lateinit var seekBar: SeekBar
    private lateinit var textViewPeso: TextView
    private lateinit var textViewAltura: TextView
    private lateinit var miBotonCualquiera: Button
    private lateinit var textViewResultado: TextView

    private var isSubirPesoPressed = false
    private var isBajarPesoPressed = false
    private var isSubirAlturaPressed = false
    private var isBajarAlturaPressed = false

    @SuppressLint("ClickableViewAccessibility", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewPeso = findViewById(R.id.textViewPeso)
        actualizarTextoPeso()
        textViewAltura = findViewById(R.id.textViewAltura)
        seekBar = findViewById(R.id.seekBar)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                altura = progress
                actualizarTextoAltura()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        miBotonCualquiera = findViewById(R.id.buttonCalcular)
        textViewResultado = findViewById(R.id.textViewResultado)

        miBotonCualquiera.setOnClickListener {
            val resultado = calcularIMC()
            textViewResultado.text = "Tu IMC es de $resultado"
        }

        val btnSubirPeso = findViewById<ImageButton>(R.id.buttonSubir)
        val btnBajarPeso = findViewById<ImageButton>(R.id.buttonBajar)
        val btnSubirAltura = findViewById<ImageButton>(R.id.buttonSubirAltura)
        val btnBajarAltura = findViewById<ImageButton>(R.id.buttonBajarAltura)

        btnSubirPeso.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                isSubirPesoPressed = true
                subirPeso(null)
            } else if (event.action == MotionEvent.ACTION_UP) {
                isSubirPesoPressed = false
            }
            true
        }

        btnBajarPeso.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                isBajarPesoPressed = true
                bajarPeso(null)
            } else if (event.action == MotionEvent.ACTION_UP) {
                isBajarPesoPressed = false
            }
            true
        }

        btnSubirAltura.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                isSubirAlturaPressed = true
                subirAltura(null)
            } else if (event.action == MotionEvent.ACTION_UP) {
                isSubirAlturaPressed = false
            }
            true
        }

        btnBajarAltura.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                isBajarAlturaPressed = true
                bajarAltura(null)
            } else if (event.action == MotionEvent.ACTION_UP) {
                isBajarAlturaPressed = false
            }
            true
        }
    }

    fun subirPeso(view: View?) {
        if (isSubirPesoPressed) {
            peso++
            actualizarTextoPeso()
            view?.postDelayed({ subirPeso(view) }, 100) // Se llama a sí mismo cada 100 milisegundos mientras el botón está presionado
        }
    }

    fun bajarPeso(view: View?) {
        if (isBajarPesoPressed) {
            peso--
            actualizarTextoPeso()
            view?.postDelayed({ bajarPeso(view) }, 100) // Se llama a sí mismo cada 100 milisegundos mientras el botón está presionado
        }
    }

    fun subirAltura(view: View?) {
        if (isSubirAlturaPressed) {
            altura++
            actualizarTextoAltura()
            seekBar.progress = altura
            view?.postDelayed({ subirAltura(view) }, 100) // Se llama a sí mismo cada 100 milisegundos mientras el botón está presionado
        }
    }

    fun bajarAltura(view: View?) {
        if (isBajarAlturaPressed) {
            altura--
            actualizarTextoAltura()
            seekBar.progress = altura
            view?.postDelayed({ bajarAltura(view) }, 100) // Se llama a sí mismo cada 100 milisegundos mientras el botón está presionado
        }
    }

    private fun actualizarTextoPeso() {
        textViewPeso.text = "Peso: $peso kg"
    }

    private fun actualizarTextoAltura() {
        textViewAltura.text = "Altura: ${(altura / 100f)} m"
    }

    private fun calcularIMC(): String {
        val resultado = peso / (altura / 100f).pow(2)
        return resultado.toString()
    }
}
