package com.example.parstergram

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.parstergram.fragments.ComposeFragment
import com.example.parstergram.fragments.FeedFragment
import com.example.parstergram.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseFile
import com.parse.ParseQuery
import com.parse.ParseUser
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {

            item ->

            var fragmentToShow: Fragment? = null
            when (item.itemId) {
                R.id.action_home -> {
                    fragmentToShow = FeedFragment()
                }
                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()
                }
                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }

            }

            if (fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }
            true
            // Return true to say that we have handled this user interaction on the item
        }

        // set default selection
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home

        findViewById<Button>(R.id.logout_button).setOnClickListener {
            ParseUser.logOut()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        //queryPosts()
    }

    companion object {
        const val TAG = "MainActivity"
    }

}