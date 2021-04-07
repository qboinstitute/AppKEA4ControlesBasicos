package com.qbo.appkea4controlesbasicos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.qbo.appkea4controlesbasicos.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    private lateinit var binding : ActivityMainBinding
    private var estadoCivil = ""
    private val listaPreferencias = ArrayList<String>()
    private val listaPersonas = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Adapter es un elemento que nos permite añadir información
        //a los elementos de lista
        ArrayAdapter.createFromResource(
            this,
            R.array.estado_civil_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spestadocivil.adapter = adapter
        }
        binding.spestadocivil.onItemSelectedListener = this
        binding.btnregistrar.setOnClickListener(this)
        binding.btnlistarpersonas.setOnClickListener(this)
        binding.chkdeporte.setOnClickListener(this)
        binding.chkdibujo.setOnClickListener(this)
        binding.chkotros.setOnClickListener(this)

    }
    fun registrarPersona(vista : View){
        if(validarFormulario(vista)){
            val infoPersona = binding.etnombre.text.toString() + " " +
                    binding.etapellido.text.toString() + " " +
                    obtenerGeneroSeleccionado() + " " +
                    obtenerPreferenciasSeleccionadas() + " " +
                    estadoCivil + " " +
                    binding.swemail.isChecked
            listaPersonas.add(infoPersona)
            setearControles()
        }
    }

    fun setearControles(){
        listaPreferencias.clear()
        binding.etnombre.setText("")
        binding.etapellido.setText("")
        binding.swemail.isChecked = false
        binding.chkdeporte.isChecked = false
        binding.chkdibujo.isChecked = false
        binding.chkotros.isChecked = false
        binding.rggenero.clearCheck()
        binding.spestadocivil.setSelection(0)
        binding.etnombre.isFocusableInTouchMode = true
        binding.etnombre.requestFocus()
    }

    fun obtenerPreferenciasSeleccionadas(): String{
        var preferencias = ""
        for(pref in listaPreferencias){
            preferencias += "$pref -"
        }
        return preferencias
    }

    fun agregarQuitarPreferenciaSeleccionada(vista : View){
        val checkBox = vista as CheckBox
        if(checkBox.isChecked){
            listaPreferencias.add(checkBox.text.toString())
        }else{
            listaPreferencias.remove(checkBox.text.toString())
        }
    }

    fun obtenerGeneroSeleccionado(): String{
        var genero = ""
        when(binding.rggenero.checkedRadioButtonId){
            R.id.rbtnmasculino -> {
                genero = binding.rbtnmasculino.text.toString()
            }
            R.id.rbtnfemenino -> {
                genero = binding.rbtnfemenino.text.toString()
            }
        }
        return genero
    }

    fun validarFormulario(vista: View): Boolean{
        var respuesta = false
        if(!validarNombreApellido()){
            enviarMensajeError(vista, getString(R.string.erroNombreApellido))
        }else if(!validarGenero()){
            enviarMensajeError(vista, getString(R.string.errorgenero))
        }else if(!validarEstadoCivil()){
            enviarMensajeError(vista, getString(R.string.errorestadocivil))
        }else if(!validarPreferencias()){
            enviarMensajeError(vista, getString(R.string.errorpreferencias))
        }else{
            respuesta = true
        }
        return respuesta
    }

    fun validarPreferencias(): Boolean{
        var respuesta = false
        if(binding.chkdeporte.isChecked || binding.chkdibujo.isChecked ||
                binding.chkotros.isChecked){
            respuesta = true
        }
        return respuesta
    }

    fun validarEstadoCivil(): Boolean{
        var respuesta = true
        if(estadoCivil == ""){
            respuesta = false
        }
        return respuesta
    }

    fun validarGenero(): Boolean{
        var respuesta = true
        if(binding.rggenero.checkedRadioButtonId == -1){
            respuesta = false
        }
        return respuesta
    }

    fun validarNombreApellido():Boolean{
        var respuesta = true
        if(binding.etnombre.text.toString().trim().isEmpty()){
            binding.etnombre.isFocusableInTouchMode = true
            binding.etnombre.requestFocus()
            respuesta = false
        }else if(binding.etapellido.text.toString().trim().isEmpty()){
            binding.etapellido.isFocusableInTouchMode = true
            binding.etapellido.requestFocus()
            respuesta = false
        }
        return respuesta
    }

    fun enviarMensajeError(vista: View, mensajeError: String){
        val snackbar = Snackbar.make(vista, mensajeError, Snackbar.LENGTH_LONG)
        val snackBarView: View = snackbar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this,
            R.color.snackbarerror))
        snackbar.show()
    }

    fun irListaPersonas(){
        val intentLista = Intent(
            this, ListaActivity::class.java
        ).apply {
            putExtra("listapersonas", listaPersonas)
        }
        startActivity(intentLista)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        estadoCivil = if(position > 0){
            parent!!.getItemAtPosition(position).toString()
        }else{
            ""
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        if(v!! is CheckBox){
            agregarQuitarPreferenciaSeleccionada(v!!)
        }else{
            when(v!!.id){
                R.id.btnregistrar -> registrarPersona(v!!)
                R.id.btnlistarpersonas -> irListaPersonas()
            }
        }
    }
}