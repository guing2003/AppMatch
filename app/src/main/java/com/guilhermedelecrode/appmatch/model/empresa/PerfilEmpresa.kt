package com.guilhermedelecrode.appmatch.model.empresa

data class PerfilEmpresa(
    val nome_empresa: String,
    val cnpj: String,
    val endereco: String,
    val numero: String,
    val telefone: String,
    val email: String,
    val seguimento: String
)

