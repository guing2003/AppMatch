package com.guilhermedelecrode.appmatch.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.guilhermedelecrode.appmatch.R

class CadastroEmpresaFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_cadastro_empresa,
            container,
            false
        )

        val spinner:Spinner = view.findViewById(R.id.spinner)

        //Inicializando spinner
        val spinnerManager = SpinnerManager(requireContext())
        //Definindo os items
        val items = listOf("Automóveis", "Tecnologia", "Medicina", "Alimenticio" )
        //Configurando o Spinner
        spinnerManager.setupSpinner(spinner,items)
        return view
    }
}
class SpinnerManager(private val context: Context){

    fun setupSpinner(spinner: Spinner, items: List<String>){
        val adapter =  ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

    }

}