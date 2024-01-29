package entities

import com.google.gson.annotations.SerializedName

class Jogo(@SerializedName("title") val titulo: String, @SerializedName("thumb") val capa: String) {
    var descricao = ""

    override fun toString(): String {
        return "Titúlo: $titulo \n" +
                "Capa: $capa \n" +
                "Descrição: $descricao"
    }
}