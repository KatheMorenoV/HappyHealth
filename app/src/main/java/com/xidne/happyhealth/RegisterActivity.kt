package com.xidne.happyhealth

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var reference: DatabaseReference
    private lateinit var  etName : EditText
    private lateinit var  etEmail : EditText
    private lateinit var  etPassword : EditText
    private lateinit var  btnRegister : Button
    private lateinit var  userType: Spinner

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        InicializarVariables()
        val optionsUser = arrayOf("¿Eres doctor o paciente?","Doctor","Paciente")
        userType.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsUser)

        btnRegister.setOnClickListener {
            validarDatos()
        }
    }


    private fun InicializarVariables(){
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        userType = findViewById(R.id.userType)
        auth = FirebaseAuth.getInstance()
    }

    private fun validarDatos() {
        val nombreUsuario : String = etName.text.toString()
        val email : String = etEmail.text.toString()
        val password : String = etPassword.text.toString()

        if (nombreUsuario.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese nombre de usuario",Toast.LENGTH_SHORT).show()
        } else if (email.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese un correo electrónico",Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese una contraseña",Toast.LENGTH_SHORT).show()
        } else if (userType.selectedItem == "¿Eres doctor o paciente?"){
            Toast.makeText(applicationContext, "Elija una opción de doctor o paciente",Toast.LENGTH_SHORT).show()
        } else {
            registrarUsuario(email, password)
        }
    }

    private fun registrarUsuario(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var uid : String = ""
                uid = auth.currentUser!!.uid
                reference = FirebaseDatabase.getInstance().reference.child("Users").child(uid)

                val hashmap = HashMap<String, Any>()
                val hEtName : String = etName.text.toString()
                val hEtEmail : String = etEmail.text.toString()
                val hEtPassword : String = etPassword.text.toString()
                val hUserType : String = userType.selectedItem.toString()

                hashmap["uid"] = uid
                hashmap["n_usuario"] = hEtName
                hashmap["email"] = hEtEmail
                hashmap["password"] = hEtPassword
                hashmap["userType"] = hUserType
                hashmap["image"] = ""
                hashmap["searchUser"] = hEtName.lowercase()

                /*Nuevos datos de usuario*/
                hashmap["nombres"]=""
                hashmap["apellidos"] = ""
                hashmap["edad"] = ""
                hashmap["profesion"] = ""

                reference.updateChildren(hashmap).addOnCompleteListener { task2->
                    if (task2.isSuccessful){
                        val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                        Toast.makeText(applicationContext, "Se ha registrado con éxito",Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }
                }.addOnFailureListener {e->
                    Toast.makeText(applicationContext, "${e.message}",Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Ha ocurrido un error",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {e->
            Toast.makeText(applicationContext, "${e.message}",Toast.LENGTH_SHORT).show()
        }
    }

}

