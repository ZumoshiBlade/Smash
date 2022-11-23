package com.zushi.smash

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.zushi.smash.databinding.ActivityInicioBinding


class InicioActivity : AppCompatActivity() {

    lateinit var binding: ActivityInicioBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(News())

        binding.apply {
            toggle = ActionBarDrawerToggle(this@InicioActivity,drawerLayout,R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.itemAdminNews -> navegateTo(3)
                    R.id.itemAdminCharacters -> navegateTo(1)
                    R.id.itemAdminTips -> navegateTo(2)
                }
                true
            }

        }

        //Fragment y Bottom Nav Bar
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.itemNews -> replaceFragment(News())
                R.id.itemCharacters -> replaceFragment(Characters())
                R.id.itemTips -> replaceFragment(Tips())
                R.id.itemSettings -> replaceFragment(Settings())

                else ->{

                }

            }
            true
        }

    }

    // Reemplazar fragmentos
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }

    // Navegar a la siguiente actividad
    private fun navegateTo(number: Number){
        if (number == 1){
            val intent = Intent(this, Adm_Characters::class.java)
            startActivity(intent)
        }else if(number == 2){
            val intent = Intent(this, Adm_Tips::class.java)
            startActivity(intent)
        }else if (number == 3){
            Toast.makeText(this, "Próximamente en la versión 2.1", Toast.LENGTH_SHORT).show()
        }

    }


}