package com.guilhermedelecrode.appmatch.ui.cadastro.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.ui.freelancer.feed.Feed_FreeActivity

class Cadastro_FreeFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cadastro_free, container, false)

        val btn_cadastro_free : Button = view.findViewById(R.id.btn_cadastro_free)

        btn_cadastro_free.setOnClickListener {
            val intent = Intent(requireActivity(), Feed_FreeActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}