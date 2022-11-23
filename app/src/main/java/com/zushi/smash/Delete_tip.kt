package com.zushi.smash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.zushi.smash.databinding.FragmentDeleteCharacterBinding
import com.zushi.smash.databinding.FragmentDeleteTipBinding

class Delete_tip : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentDeleteTipBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeleteTipBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup()
    }
    private fun setup(){
        binding.btnEliminar.setOnClickListener{
            val titulo = binding.tituloTip.text.toString()
            if(titulo.length >= 1){
                db.collection("Tips").document(titulo).delete().addOnCompleteListener {
                    Toast.makeText(context, "¡Se ha eliminado el consejo!", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(context, "No existe el consejo", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "¡No pueden quedar campos vacios!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}