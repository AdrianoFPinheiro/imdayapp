package br.com.adrianofpinheiro.imdayapp.presenter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.adrianofpinheiro.imdayapp.R
import br.com.adrianofpinheiro.imdayapp.model.Usuario
import kotlinx.android.synthetic.main.usuario_item.view.*

class UsuarioAdapter(private val usuarios: List<Usuario>,
                     private val context: Context,
                     val listener: (Usuario) -> Unit) : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

//Método que recebe o ViewHolder e a posição da lista.
//Aqui é recuperado o objeto da lista de Objetos pela posição e associado à ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario= usuarios[position]
        holder.let {
            it.bindView(usuario, listener)
        }
    }

    //Método que deverá retornar layout criado pelo ViewHolder já inflado em uma view.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.usuario_item, parent, false)
        return ViewHolder(view)
    }
    //Método que deverá retornar quantos itens há na lista.
    override fun getItemCount(): Int {
        return usuarios.size
    }
    /*
    Com o ViewHolder iremos relacionar o layout criado e adicionar os valores a ele*/
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(usuario: Usuario,
                     listener: (Usuario) -> Unit) = with(itemView) {
            val fieldUsuario = tvUsuario
            val fieldData = tvData
            val fieldUrl = tvURL
            fieldUsuario.text = usuario.usuario
            fieldData.text = usuario.data
            fieldUrl.text = usuario.url

//            if(tvUsuario == null){
//                fieldUrl.visibility = View.INVISIBLE
//            }else if(tvURL == null){
//                fieldUsuario.visibility = View.GONE
//            }

            setOnClickListener { listener(usuario) }
        }
    }

}