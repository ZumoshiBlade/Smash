package com.zushi.smash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.zushi.smash.databinding.FragmentListCharacterBinding
import com.zushi.smash.databinding.FragmentListTipBinding
import com.zushi.smash.models.Consejos
import com.zushi.smash.models.Personajes

class tipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class List_tip : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var _binding: FragmentListTipBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListTipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // super.onViewCreated(view, savedInstanceState)

        val query = db.collection("Tips")
        val options = FirestoreRecyclerOptions.Builder<Consejos>().setQuery(query, Consejos::class.java).setLifecycleOwner(this).build()
        val adapter = object: FirestoreRecyclerAdapter<Consejos, tipViewHolder>(options){
            // Acceder con los datos
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tipViewHolder {
                val view = LayoutInflater.from(context).inflate(R.layout.row_tips, parent, false)
                return tipViewHolder(view)
            }

            // Cargar los datos
            override fun onBindViewHolder(holder: tipViewHolder, position: Int, model: Consejos) {
                val titulo: TextView = holder.itemView.findViewById(R.id.tvTitulo)
                val descripcion: TextView = holder.itemView.findViewById(R.id.tvDescripcion)
                titulo.text = model.titulo
                descripcion.text = model.descripcion
            }

        }

        binding.rvTips.adapter = adapter
        binding.rvTips.layoutManager = LinearLayoutManager(context)
    }
}