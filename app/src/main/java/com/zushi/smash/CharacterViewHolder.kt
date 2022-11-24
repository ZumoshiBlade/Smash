package com.zushi.smash

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zushi.smash.databinding.ItemApiBinding
import com.zushi.smash.models.CharactersResponseItem

class CharacterViewHolder(view:View):RecyclerView.ViewHolder(view) {

    private val binding = ItemApiBinding.bind(view)

    val auxNombre = view.findViewById<TextView>(R.id.tvNombre)

    fun render(charactersResponseItemModel: CharactersResponseItem){
        auxNombre.text = charactersResponseItemModel.name
        Picasso.get().load(charactersResponseItemModel.images.icon).into(binding.imagen)
    }

}
