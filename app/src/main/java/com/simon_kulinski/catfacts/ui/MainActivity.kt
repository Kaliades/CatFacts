package com.simon_kulinski.catfacts.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.simon_kulinski.catfacts.R
import kotlinx.android.synthetic.main.app_bar.*

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.nav_host_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_bar)
        setSupportActionBar(toolbar)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.catFactDetailsFragment)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            else supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() or navController.navigateUp()
    }
}
