package com.zushi.smash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.zushi.smash.databinding.FragmentListCharacterBinding
import com.zushi.smash.models.Personajes

class PersonajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class List_character : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentListCharacterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // super.onViewCreated(view, savedInstanceState)

        val query = db.collection("Personajes")
        val options = FirestoreRecyclerOptions.Builder<Personajes>().setQuery(query, Personajes::class.java).setLifecycleOwner(this).build()
        val adapter = object: FirestoreRecyclerAdapter<Personajes, PersonajeViewHolder>(options){
            // Acceder con los datos
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeViewHolder {
                val view = LayoutInflater.from(context).inflate(R.layout.row_characters, parent, false)
                return PersonajeViewHolder(view)
            }

            // Cargar los datos
            override fun onBindViewHolder(holder: PersonajeViewHolder, position: Int, model: Personajes) {
                val nombre: TextView = holder.itemView.findViewById(R.id.tvNombre)
                val titulo_juego: TextView = holder.itemView.findViewById(R.id.tvJuego)
                val descripcion: TextView = holder.itemView.findViewById(R.id.tvDescripcion)
                nombre.text = model.nombre
                titulo_juego.text = model.titulo_juego
                descripcion.text = model.descripcion
            }

        }

        binding.rvPersonajes.adapter = adapter
        binding.rvPersonajes.layoutManager = LinearLayoutManager(context)
    }
}