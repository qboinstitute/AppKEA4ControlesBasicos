package com.qbo.appkea4controlesbasicos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.qbo.appkea4controlesbasicos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : ActivityMainBinding
    private var estadoCivil = ""

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
}