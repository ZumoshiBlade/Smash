package com.zushi.smash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.zushi.smash.databinding.FragmentCreateCharacterBinding
import com.zushi.smash.databinding.FragmentCreateTipBinding


class Create_tip : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentCreateTipBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateTipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup()
    }

    private fun setup(){
        binding.btnAdd.setOnClickListener{
            val titulo = binding.tituloTip.text.toString()
            val descripcion = binding.descripcionTip.text.toString()

            if(titulo.length >= 1 && descripcion.length >=1){
                db.collection("Tips").document(titulo).set(
                    hashMapOf(
                        "titulo" to titulo,
                        "descripcion" to descripcion
                    )
                ).addOnCompleteListener {
                    Toast.makeText(context, "¡Se ha agregado un consejo!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(context, "No se ha podido agregar el consejo :(", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "¡No pueden quedar campos vacios!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}