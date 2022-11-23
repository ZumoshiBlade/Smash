package com.zushi.smash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.zushi.smash.databinding.FragmentCreateCharacterBinding
import com.zushi.smash.databinding.FragmentDeleteCharacterBinding

class Delete_character : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentDeleteCharacterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeleteCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup()
    }

    private fun setup(){
        binding.btnEliminar.setOnClickListener{
            val nombre = binding.nombrePersonaje.text.toString()
            if(nombre.length >= 1){
                db.collection("Personajes").document(nombre).delete().addOnCompleteListener {
                    Toast.makeText(context, "¡Se ha eliminado el campo!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(context, "No existe el personaje", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "¡No pueden quedar campos vacios!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}