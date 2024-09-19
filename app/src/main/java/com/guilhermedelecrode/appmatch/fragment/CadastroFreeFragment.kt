package com.guilhermedelecrode.appmatch.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guilhermedelecrode.appmatch.R

class CadastroFreeFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_cadastro_free,
            container,
            false
        )
        //Processamento da visualização
        return view
    }
}