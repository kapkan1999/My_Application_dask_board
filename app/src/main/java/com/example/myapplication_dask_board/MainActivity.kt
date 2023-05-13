package com.example.myapplication_dask_board

import android.app.UiAutomation.OnAccessibilityEventListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.myapplication_dask_board.databinding.ActivityMainBinding
import com.example.myapplication_dask_board.dialoghelper.DialogConst
import com.example.myapplication_dask_board.dialoghelper.DialogHelper
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var rootElement:ActivityMainBinding
    private val dialogHelper = DialogHelper(this)
    val mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)
        init()
    }

    private fun  init(){

        var toggle = ActionBarDrawerToggle(this, rootElement.drawerLayout, rootElement.mainContent.toolbar,R.string.open, R.string.close)
        rootElement.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        rootElement.navView.setNavigationItemSelectedListener (this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

    when(item.itemId){

        R.id.id_my_ads ->{
            Toast.makeText(this,"Pressed id_ma_ads",Toast.LENGTH_LONG).show()
        }
        R.id.id_car ->{

        }
        R.id.id_pc ->{

        }
        R.id.id_smart ->{

        }
        R.id.id_sign_up ->{
            dialogHelper.createSignDialog(DialogConst.SIGN_UP_STATE)
        }
        R.id.id_sign_in ->{
            dialogHelper.createSignDialog(DialogConst.SIGN_IN_STATE)
        }
    }
        rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}