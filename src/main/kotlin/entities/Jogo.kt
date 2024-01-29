package entities

data class Jogo(val titulo: String, val capa: String) {
    var descricao = ""

    override fun toString(): String {
        return "Titúlo: $titulo \n" +
                "Capa: $capa \n" +
                "Descrição: $descricao"
    }
}