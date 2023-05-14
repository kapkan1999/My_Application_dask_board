package com.example.myapplication_dask_board

import android.app.UiAutomation.OnAccessibilityEventListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.myapplication_dask_board.databinding.ActivityMainBinding
import com.example.myapplication_dask_board.dialoghelper.DialogConst
import com.example.myapplication_dask_board.dialoghelper.DialogHelper
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var tvAccount: TextView //Тут мы инициализируем приватную переменную, которая будет обьявленна позже
    private lateinit var rootElement:ActivityMainBinding
    private val dialogHelper = DialogHelper(this)
    val mAuth = FirebaseAuth.getInstance() //видимо с помощью getInstance мы получаем возможность обращаться к его функциям, другого нормального описания не нашел
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)
        init()
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }

    private fun  init(){

        var toggle = ActionBarDrawerToggle(this, rootElement.drawerLayout, rootElement.mainContent.toolbar,R.string.open, R.string.close)
        rootElement.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        rootElement.navView.setNavigationItemSelectedListener (this)
        tvAccount = rootElement.navView.getHeaderView(0).findViewById(R.id.tvAccountEmail)
        // инициализируем переменную, сначала обращаемся к роот, там к наввью и смотрим на хедер,
        // индекс показывает какой по счету смотрим хеадер, дальше из этого хеадера берем только строку, находя ее по id
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
        R.id.id_sign_out ->{
            uiUpdate(null)
            mAuth.signOut()
        }
    }
        rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun uiUpdate(user: FirebaseUser?){ // создаем функцию, которая будет принимать пользователя, т.к. в нем прописано какая эл почта
        tvAccount.text = if(user == null){
           resources.getString(R.string.not_reg)
        } else{
            user.email
        }
    }
}