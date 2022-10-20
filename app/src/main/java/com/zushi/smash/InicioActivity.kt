package com.zushi.smash

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zushi.smash.databinding.ActivityInicioBinding


class InicioActivity : AppCompatActivity() {

    lateinit var binding: ActivityInicioBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(News())

        //NavBar
        binding.apply {
            toggle = ActionBarDrawerToggle(this@InicioActivity, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.itemNews -> {
                        Toast.makeText(this@InicioActivity, "Disponible en la versión 2.0", Toast.LENGTH_SHORT).show()
                    }
                    R.id.itemCharacters -> {
                        Toast.makeText(this@InicioActivity, "Disponible en la versión 2.1", Toast.LENGTH_SHORT).show()
                    }
                    R.id.itemTips -> {
                        Toast.makeText(this@InicioActivity, "Disponible en la versión 2.2", Toast.LENGTH_SHORT).show()
                    }
                    R.id.itemSettings -> {
                        Toast.makeText(this@InicioActivity, "Disponible en la versión 2.3", Toast.LENGTH_SHORT).show()
                    }
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
}