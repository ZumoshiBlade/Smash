package com.zushi.smash


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.zushi.smash.databinding.FragmentCreateCharacterBinding

class Create_character : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentCreateCharacterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateCharacterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup()
    }

    private fun setup(){
        binding.btnAdd.setOnClickListener{
            val nombre = binding.nombrePersonaje.text.toString()
            val titulo = binding.juegoPersonaje.text.toString()
            val descripcion = binding.descripcionPersonaje.text.toString()

            if(nombre.length >= 1 && titulo.length >= 1 && descripcion.length >=1){
                db.collection("Personajes").document(nombre).set(
                    hashMapOf(
                        "nombre" to nombre,
                        "titulo_juego" to titulo,
                        "descripcion" to descripcion
                    )
                ).addOnCompleteListener {
                    Toast.makeText(context, "¡Se ha agregado un personaje!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(context, "No se ha podido agregar el personaje :(", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "¡No pueden quedar campos vacios!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}