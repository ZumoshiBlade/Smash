package com.zushi.smash

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.zushi.smash.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var googleSignInClient : GoogleSignInClient

    private val channelID = "channelID"
    private val channelName = "channelName"
    private val notificationID = 1
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

        // Autenticación con google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleButton.setOnClickListener {
            signInGoogle()
        }


        //Creando la notificación
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, channelID).also {
            it.setContentTitle("¡Bienvenido a SSB Tips!")
            it.setContentText("En esta aplicación podrás ver todo tipo de tips, noticias y datos de tus personajes favoritos de SSB Ultimate")
            it.setSmallIcon(R.drawable.ic_stat_name)
            it.setPriority(NotificationCompat.PRIORITY_HIGH)
        }.build()

        val notificationManager = NotificationManagerCompat.from(this)



        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val pass = binding.passwordEditText.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()){

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, InicioActivity::class.java)
                        notificationManager.notify(notificationID, notification)
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

    // Notificaciones

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelID, channelName, importance).apply {
                lightColor = Color.RED
                enableLights(true)
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)
        }
    }

    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
            if (result.resultCode == Activity.RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleReults(task)
            }
    }

    private fun handleReults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        }else{
            Toast.makeText(this, "No se ha podido iniciar sesión con google", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { 
            if (it.isSuccessful){
                val intent : Intent = Intent(this, InicioActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "No se ha podido iniciar sesión con google", Toast.LENGTH_SHORT).show()
            }
        }
    }
}