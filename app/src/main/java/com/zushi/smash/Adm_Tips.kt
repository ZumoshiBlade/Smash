package com.zushi.smash

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.zushi.smash.databinding.ActivityAdmTipsBinding

class Adm_Tips : AppCompatActivity() {

    private lateinit var binding: ActivityAdmTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdmTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Create_tip())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.create -> replaceFragment(Create_tip())
                R.id.list -> replaceFragment(List_tip())
                R.id.delete -> replaceFragment(Delete_tip())

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