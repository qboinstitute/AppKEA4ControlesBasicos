package com.qbo.appkea4controlesbasicos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.qbo.appkea4controlesbasicos.databinding.ActivityListaBinding

class ListaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listaPersonas = intent.getSerializableExtra("listapersonas")
                as ArrayList<String>
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaPersonas
        )
        binding.lvpersonas.adapter = adapter
    }
}