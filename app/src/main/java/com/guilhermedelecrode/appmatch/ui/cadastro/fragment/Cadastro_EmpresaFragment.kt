package com.guilhermedelecrode.appmatch.ui.cadastro.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.ui.empresa.feed.Feed_EmpresaActivity

class Cadastro_EmpresaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_cadastro_empresa, container, false)

        val btn_cadastro_empresa : Button = view.findViewById(R.id.btn_cadastro_empresa)

        btn_cadastro_empresa.setOnClickListener {
            val intent = Intent(requireActivity(), Feed_EmpresaActivity::class.java)
            startActivity(intent)
        }

        val spinner: Spinner = view.findViewById(R.id.spinner)
        //Inicializando spinner
        val spinnerManager = SpinnerManager(requireContext())
        //Definindo os items
        val items = listOf("Automóveis", "Tecnologia", "Medicina", "Alimenticio")
        //Configurando o Spinner
        spinnerManager.setupSpinner(spinner, items)

        return view
    }


    class SpinnerManager(private val context: Context) {
        fun setupSpinner(spinner: Spinner, items: List<String>) {
            val adapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, items)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

        }
    }
}