package com.example.myapplication_dask_board.dialoghelper

import android.app.AlertDialog
import com.example.myapplication_dask_board.MainActivity
import com.example.myapplication_dask_board.R
import com.example.myapplication_dask_board.accounthelper.AccountHelper
import com.example.myapplication_dask_board.databinding.SignDialogBinding

class DialogHelper(act:MainActivity) {
    private val act = act
    private  val accHelper = AccountHelper(act)
    fun createSignDialog(index: Int){
        val builder = AlertDialog.Builder(act)
        val rootDialogElement = SignDialogBinding.inflate(act.layoutInflater)
        val view = rootDialogElement.root
        builder.setView(view)
        if(index == DialogConst.SIGN_UP_STATE){
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.aс_sign_up)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_up_action)
        } else{
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.ac_sign_in)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_in_action)
        }
        val dialog = builder.create() //создаем диалог, получается теперь черех переменную можем показывать, закрывать и т.д. окно
        rootDialogElement.btSignUpIn.setOnClickListener{
            dialog.dismiss()//спрятать окошко регистрации

            if(index == DialogConst.SIGN_UP_STATE){//тут проверка просто по тексту, идет регистрация или вход
                accHelper.signUpWithEmail(rootDialogElement.edSignEmail.text.toString()
                    ,rootDialogElement.edSignPassword.text.toString()) //мы регистрируемся и передаем еайл и пароль
            } else {

                accHelper.signInWithEmail(rootDialogElement.edSignEmail.text.toString()
                    ,rootDialogElement.edSignPassword.text.toString()) //мы входим и передаем еайл и пароль
            }
        }

        dialog.show()
    }
}