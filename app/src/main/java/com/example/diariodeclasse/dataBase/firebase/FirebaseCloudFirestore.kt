package com.example.diariodeclasse.dataBase.firebase

import android.content.Context
import android.widget.Toast
import androidx.core.net.toUri
import com.example.diariodeclasse.model.Aluno
import com.example.diariodeclasse.model.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FirebaseCloudFirestore {
    private val db = FirebaseFirestore.getInstance()

    private val _listaDeAlunos = MutableStateFlow<MutableList<Aluno>>(mutableListOf())
    private val listaDeAlunos: StateFlow<MutableList<Aluno>> = _listaDeAlunos.asStateFlow()
    fun carregarListaDeAlunos(): Flow<MutableList<Aluno>> {

        val carregaListaDeAlunos:MutableList<Aluno> = mutableListOf()

        db.collection("Alunos").get().addOnCompleteListener{querySnapshot ->
            if(querySnapshot.isSuccessful) {
                for (documento in querySnapshot.result)
                {
                    val aluno = documento.toObject(Aluno::class.java)
                    carregaListaDeAlunos.add(aluno)
                }
            }
            _listaDeAlunos.value = carregaListaDeAlunos
        }

        return listaDeAlunos
    }


    fun mensagemToast(
        localContext: Context,
        mensagem:String
    ){
        Toast.makeText(
            localContext,
            mensagem,
            Toast.LENGTH_SHORT
        ).show()
    }
}