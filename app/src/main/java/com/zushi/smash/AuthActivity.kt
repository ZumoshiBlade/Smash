package com.zushi.smash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.zushi.smash.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    // Binding para usar los elementos del layout (inputs, buttons, etc)
    private lateinit var binding: ActivityAuthBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Llamada a los elementos del layout a través de una variable
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Autenticación

        firebaseAuth = FirebaseAuth.getInstance()
        binding.signUpButton.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()){

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, InicioActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, it.exception.toString() , Toast.LENGTH_SHORT).show()
                    }
                    }
            }else{
                Toast.makeText(this, "¡Introduce tu email y contraseña!", Toast.LENGTH_SHORT).show()

            }
        }

        // SplashScreen
        Thread.sleep(1000)
        screenSplash.setKeepOnScreenCondition { false }
    }


}