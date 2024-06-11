package com.xidne.happyhealth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.xidne.happyhealth.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa con el primer fragmento si no hay un estado guardado
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Configura el listener para manejar la selección de elementos en el BottomNavigationView
        binding.bottomNavigationView3.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.calendar -> replaceFragment(CalendarFragment())
                R.id.doctor -> replaceFragment(DoctorFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else -> false
            }
            true
        }
    }

    // Reemplaza el fragmento actual con el fragmento proporcionado
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}

