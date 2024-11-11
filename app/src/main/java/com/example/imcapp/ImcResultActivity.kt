package com.example.imcapp

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ImcResultActivity : AppCompatActivity() {
    private lateinit var imcNum:TextView
    private lateinit var tvResultado: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var btnRecalcular: AppCompatButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ImcResultActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initComponents()
        initListeners()
        val resultado: Double = intent.extras!!.getDouble("IMC_KEY")
        imcNum.text=DecimalFormat("#,##").format(resultado)
        calcularIMC(resultado)
    }

    private fun initListeners()
    {
        btnRecalcular.setOnClickListener{
            val intent = Intent(this, ImcCalculatorActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initComponents()
    {
        imcNum= findViewById(R.id.tvNumImc)
        tvResultado= findViewById(R.id.tvResultado)
        tvDescripcion= findViewById(R.id.tvDescripcion)
        btnRecalcular= findViewById(R.id.btnRecalcular)
    }

    private fun calcularIMC(resultado: Double){
        if(resultado<18.5){
            tvResultado.text=getString(R.string.resultadoInferior)
            tvResultado.setTextColor(getColor(R.color.no_optimo))
            tvDescripcion.text=getString(R.string.descripcionInferior)
        }else if(resultado>=18.5 && resultado<24.9){
            tvResultado.text=getString(R.string.resultadoOptimo)
            tvResultado.setTextColor(getColor(R.color.optimo))
            tvDescripcion.text=getString(R.string.descripcionOptimo)
        }else if(resultado>=24.9 && resultado<29.9){
            tvResultado.text=getString(R.string.resultadoSobrepeso)
            tvResultado.setTextColor(getColor(R.color.no_optimo))
            tvDescripcion.text=getString(R.string.descripcionSobrepeso)
        }else
        {
            tvResultado.text=getString(R.string.resultadoObesidad)
            tvResultado.setTextColor(getColor(R.color.obesidad))
            tvDescripcion.text=getString(R.string.descripcionObesidad)
        }
    }
}