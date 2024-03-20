package br.com.alugames.models

data class Jogo(val titulo: String, val capa: String) {
    var descricao: String? = null

    override fun toString(): String {
        return "Titúlo: $titulo \n" +
                "Capa: $capa \n" +
                "Descrição: $descricao"
    }
}