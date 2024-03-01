package br.com.alugames

import br.com.alugames.entities.InfoJogo
import br.com.alugames.entities.Jogo
import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.util.Scanner

fun main() {
    val sc = Scanner(System.`in`)
    println("Digite o nome do jogo: ")
    val busca = sc.nextLine()
    val endereco = "https://www.cheapshark.com/api/1.0/games?id=$busca"
    val client: HttpClient = HttpClient.newHttpClient()
    val request: HttpRequest = HttpRequest.newBuilder()
        .uri(URI.create(endereco))
        .build()
    val response = client.send(request, BodyHandlers.ofString())

    var json = response.body()
    println(json)

    val gson = Gson()
    val meuInfoJogo = gson.fromJson(json, InfoJogo::class.java)

    var meuJogo: Jogo? = null

    val resultado = runCatching {
        meuJogo = Jogo(
            meuInfoJogo.info.title,
            meuInfoJogo.info.thumb
        )
    }
    resultado.onFailure {
        println("Jogo não encontrado!")
    }

    resultado.onSuccess {
        println("Deseja inserir uma descrição personalizada? (S/N)")
        val opcao = sc.nextLine()
        if (opcao.equals("s", true)) {
            println("Digite a descrição: ")
            var descricaoPersonalizada = sc.nextLine()
            meuJogo?.descricao = descricaoPersonalizada
        } else {
            meuJogo?.descricao = meuJogo?.titulo
        }
        println(meuJogo)
    }
    resultado.onSuccess {
        println("Busca finalizada com sucesso!")
    }
}
