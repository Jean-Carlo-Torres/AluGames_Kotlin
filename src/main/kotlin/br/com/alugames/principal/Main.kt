package br.com.alugames.principal

import br.com.alugames.models.Gamer
import br.com.alugames.models.Jogo
import br.com.alugames.services.ConsumoApi
import java.util.*

fun main() {
    val leitura = Scanner(System.`in`)
    val gamer = Gamer.criarGamer(leitura)
    println("Cadastro realizado com sucesso!")
    println(gamer)

    do {
        println("Digite um código de jogo para buscar:")
        val busca = leitura.nextLine()

        val buscaApi = ConsumoApi()
        val informacaoJogo = buscaApi.buscaJogo(busca)

        var meuJogo: Jogo? = null

        val resultado = runCatching {
            meuJogo = Jogo(
                informacaoJogo.info.title,
                informacaoJogo.info.thumb
            )
        }

        resultado.onFailure {
            println("Jogo inexistente. Tente outro id.")
        }

        resultado.onSuccess {
            println("Deseja inserir uma descrição personalizada? S/N")
            val opcao = leitura.nextLine()
            if (opcao.equals("s", true)) {
                println("Insira a descrição personalizado para o jogo:")
                val descricaoPersonalizada = leitura.nextLine()
                meuJogo?.descricao = descricaoPersonalizada
            } else {
                meuJogo?.descricao = meuJogo?.titulo
            }
            gamer.jogosBuscados.add(meuJogo)
        }

        println("Deseja buscar um novo jogo? S/N")
        val resposta = leitura.nextLine()
    } while (resposta.equals("s", true))

    println("Jogos buscados: ")
    gamer.jogosBuscados.forEach {
        println(it)
        println("------------------------------------")
    }
    println("Busca finalizada com sucesso.")
}
