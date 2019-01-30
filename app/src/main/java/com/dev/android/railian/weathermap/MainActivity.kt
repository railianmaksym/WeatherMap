package com.dev.android.railian.weathermap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dev.android.railian.weathermap.view.MapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val fragments = nav_host_fragment.childFragmentManager.fragments

        if (fragments.last() is MapFragment) {
            if (backPressedOnce) {
                finish()
            } else {
                backPressedOnce = true
                Toast.makeText(this, "Press back twice to exit", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onBackPressed()
        }
    }
}
