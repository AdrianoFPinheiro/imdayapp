package br.com.adrianofpinheiro.imdayapp.presenter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import br.com.adrianofpinheiro.imdayapp.R
import br.com.adrianofpinheiro.imdayapp.model.Usuario
import org.json.JSONArray

class UsuarioService {

    fun getUsuarios(context: Context): List<Usuario> {

        val raw = R.raw.imday
        val resources = context.resources
        val inputStream = resources.openRawResource(raw)
        inputStream.bufferedReader().use {
            //Lê e Cria a lista de usuarios
            val json = it.readText()
            val usuarios = parseJson(json)
            return usuarios
        }

    }

    private fun parseJson(json: String): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        //Cria um array com JSON
        val array = JSONArray(json)
        //Percorre cada usuario
        for (i in 0 until array.length()) {
            //JSON do usuario
            val jsonUsuario = array.getJSONObject(i)
            //Lê as informações de cada usuario
            val u = Usuario(data = jsonUsuario.optString("data"),
                            usuario = jsonUsuario.optString("usuario"),
                            url = jsonUsuario.optString("url"))
            usuarios.add(u)
        }
        Log.d(TAG, "${usuarios.size} usuarios encontrados")
        return usuarios
    }
}