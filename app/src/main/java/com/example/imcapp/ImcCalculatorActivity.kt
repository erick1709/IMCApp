package com.example.imcapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat
import kotlin.math.pow

class ImcCalculatorActivity : AppCompatActivity() {

    private var imc:Double=0.0
    private var weight = 60
    private var age = 26
    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private var isMaleSelected: Boolean = true
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var cifraPeso: TextView
    private lateinit var addPeso: FloatingActionButton
    private lateinit var redPeso: FloatingActionButton
    private lateinit var cifraEdad: TextView
    private lateinit var addEdad: FloatingActionButton
    private lateinit var redEdad: FloatingActionButton
    private lateinit var btnCalcular: AppCompatButton
    companion object{
        const val IMC_KEY="RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
        initListeners()
        initUI()
    }

    private fun initUI() {
        setGenderColor()
    }

    private fun initListeners() {
        viewMale.setOnClickListener {
            isMaleSelected = true
            setGenderColor()
        }
        viewFemale.setOnClickListener {
            isMaleSelected = false
            setGenderColor()
        }
        rsHeight.addOnChangeListener { _, value, _ ->
            //tvHeight.text = value.toString()
            tvHeight.text = DecimalFormat("#.##").format(value) + " cm"
        }
        addPeso.setOnClickListener{
            setWeight(true)
        }
        redPeso.setOnClickListener{
            setWeight(false)
        }
        addEdad.setOnClickListener{
            setAge(true)
        }
        redEdad.setOnClickListener{
            setAge(false)
        }
        btnCalcular.setOnClickListener{
            calculateIMC()
            navigate2result(imc)
        }
    }

    private fun calculateIMC(): Double
    {
        val alturaEnMetros = tvHeight.text.toString().replace(" cm", "").toDouble() / 100
        imc = weight/(alturaEnMetros.pow(2))
        return imc
    }

    private fun navigate2result(imc: Double)
    {
        val intentGA = Intent(this,ImcResultActivity::class.java)
        intentGA.putExtra("IMC_KEY", imc)
        startActivity(intentGA)
    }

    private fun setAge(boolean: Boolean) {
        if (boolean){
            age++
        } else if (age>0){
            age--
        }
        cifraEdad.text="$age"
    }

    private fun setWeight(boolean: Boolean) {
        if (boolean){
            weight++
        } else if (weight>0){
            weight--
        }
        cifraPeso.text="$weight"
    }

    private fun setGenderColor() {
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(!isMaleSelected))
    }

    private fun getBackgroundColor(isComponentSelected: Boolean): Int {
        val colorReference = if (isComponentSelected) {
            R.color.bg_comp_sel
        } else {
            R.color.bg_comp
        }
        return ContextCompat.getColor(this, colorReference)
    }

    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        cifraPeso = findViewById(R.id.cifraPeso)
        addPeso = findViewById(R.id.btnAddWeight)
        redPeso = findViewById(R.id.btnSubtractWeight)
        cifraEdad = findViewById(R.id.cifraEdad)
        addEdad = findViewById(R.id.btnAddAge)
        redEdad = findViewById(R.id.btnSubtractAge)
        btnCalcular = findViewById(R.id.btnCalcular)
    }
}