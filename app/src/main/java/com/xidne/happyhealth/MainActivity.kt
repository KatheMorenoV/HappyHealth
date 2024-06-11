package com.xidne.happyhealth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    //Declara los componentes
    var firebaseUser: FirebaseUser? = null
    private lateinit var etEmailMain: EditText
    private lateinit var etPasswordMain: EditText
    private lateinit var btnLoginMain: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InicializarVariables()

        btnLoginMain.setOnClickListener {
            ValidarDatos()
        }

        val btnRegister: Button = findViewById(R.id.btnRegisterMain)
        btnRegister.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Comprueba si hay una sesión activa
    private fun ComprobarSesion() {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Sesión activa", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onStart() {
        ComprobarSesion()
        super.onStart()
    }

    // Inicializa los componentes
    private fun InicializarVariables() {
        etEmailMain = findViewById(R.id.etEmailMain)
        etPasswordMain = findViewById(R.id.etPasswordMain)
        btnLoginMain = findViewById(R.id.btnLoginMain)
        auth = FirebaseAuth.getInstance()
    }

    // Valida los datos de entrada
    private fun ValidarDatos() {
        val email: String = etEmailMain.text.toString()
        val password: String = etPasswordMain.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(applicationContext, "Ingrese su correo electrónico", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(applicationContext, "Ingrese su contraseña", Toast.LENGTH_SHORT).show()
        } else {
            LoginUsuario(email, password)
        }
    }

    // Inicia sesión con Firebase Authentication
    private fun LoginUsuario(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                Toast.makeText(applicationContext, "Ha iniciado sesión", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(applicationContext, "Ingrese los datos correctos", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}

