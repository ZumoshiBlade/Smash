package com.zushi.smash

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zushi.smash.databinding.ActivityAdmCharactersBinding
class Adm_Characters : AppCompatActivity() {

    private lateinit var binding: ActivityAdmCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Create_character())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.create -> replaceFragment(Create_character())
                R.id.list -> replaceFragment(List_character())
                R.id.delete -> replaceFragment(Delete_character())

                else ->{

                }

            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }


}