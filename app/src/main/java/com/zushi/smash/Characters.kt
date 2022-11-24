package com.zushi.smash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.zushi.smash.databinding.FragmentCharactersBinding
import com.zushi.smash.models.CharactersResponseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Characters : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var adapter: CharactersAdapter
    private var _binding: FragmentCharactersBinding? = null
    private var lista = mutableListOf<CharactersResponseItem>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchName.setOnQueryTextListener(this)
        initRecycle()
        // search()
    }

    // Inicio recycleview
    private fun initRecycle(){
        adapter = CharactersAdapter(lista)
        binding.rvApi.layoutManager = LinearLayoutManager(context)
        binding.rvApi.adapter = adapter
    }

    // Llamamos al api con retrofit
    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://smashbros-unofficial-api.vercel.app/api/v1/ultimate/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun search(nombre:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getCharacters("characters")
            val characters = call.body()!!
            activity?.runOnUiThread(){
                if(call.isSuccessful){
                    // Mostramos el RecycleView
                    lista.clear()
                    for (aux in characters){
                        if(aux.name.equals(nombre))
                        lista.add(aux)
                        adapter.notifyDataSetChanged()
                    }
                }else{
                    // Mostramos un error
                    Toast.makeText(context, "Ha ocurrido un error al cargar los personajes", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun hideKeyboard(){
        val imn = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(view?.windowToken,0)
    }

    override fun onQueryTextSubmit(query: String?): Boolean{
        if(!query.isNullOrEmpty()){
            search(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean{
        return true
    }
}