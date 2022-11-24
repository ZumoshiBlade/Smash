package com.zushi.smash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zushi.smash.models.CharactersResponseItem

class CharactersAdapter(private val lista:List<CharactersResponseItem>): RecyclerView.Adapter<CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(layoutInflater.inflate(R.layout.item_api, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position:Int){
        val item = lista[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = lista.size
}