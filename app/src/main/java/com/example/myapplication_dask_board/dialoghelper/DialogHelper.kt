package com.example.myapplication_dask_board.dialoghelper

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.myapplication_dask_board.MainActivity
import com.example.myapplication_dask_board.R
import com.example.myapplication_dask_board.accounthelper.AccountHelper
import com.example.myapplication_dask_board.databinding.SignDialogBinding

class DialogHelper(act:MainActivity) {
    private val act = act
    val accHelper = AccountHelper(act)
    fun createSignDialog(index: Int){
        val builder = AlertDialog.Builder(act)
        val rootDialogElement = SignDialogBinding.inflate(act.layoutInflater) // переменная типа SignDialogBinding и сразу в нее передаем act.layoutInflater
                                                                            // , через нее получаем весь доступ к эементам на экране, который передали (передаем лист MainActivity)
        val view = rootDialogElement.root
        builder.setView(view)
        setDialogState (index, rootDialogElement)
        val dialog = builder.create() //создаем диалог, получается теперь черех переменную можем показывать, закрывать и т.д. окно
        rootDialogElement.btSignUpIn.setOnClickListener{
            SetOnClickSignUpIn(index,rootDialogElement,dialog)
        }
        rootDialogElement.btForgetP.setOnClickListener{
            SetOnClickResetPassword(rootDialogElement,dialog)
        }
        rootDialogElement.btGoogleSignIn.setOnClickListener{
            accHelper.signInWithGoogle()
        }
        dialog.show()
    }

    private fun SetOnClickResetPassword(rootDialogElement: SignDialogBinding, dialog: AlertDialog?) {//функция для сброса пароля

        if(rootDialogElement.edSignEmail.text.isNotEmpty()){//проверяем поле емайл, чтобы оно было не нул

            act.mAuth.sendPasswordResetEmail(rootDialogElement.edSignEmail.text.toString()).addOnCompleteListener{task->//тут отправляем сообщение на емайл
                if(task.isSuccessful){
                   Toast.makeText(act,R.string.email_reset_was_sent,Toast.LENGTH_LONG).show()
                }

            }
            dialog?.dismiss()//тут проверка, чтобы dialog был не null
        } else{
            rootDialogElement.tvDialogMessage.visibility = View.VISIBLE

        }

    }

    private fun SetOnClickSignUpIn(index: Int, rootDialogElement: SignDialogBinding, dialog: AlertDialog?) { //? означает, что переменная может быть null
                                                                                        // , kotlin работает с переменными со значением Null немного иначе
        dialog?.dismiss()//спрятать окошко регистрации
        if(index == DialogConst.SIGN_UP_STATE){//тут проверка просто по тексту, идет регистрация или вход
            accHelper.signUpWithEmail(rootDialogElement.edSignEmail.text.toString()
                ,rootDialogElement.edSignPassword.text.toString()) //мы регистрируемся и передаем еайл и пароль
        } else {

            accHelper.signInWithEmail(rootDialogElement.edSignEmail.text.toString()
                ,rootDialogElement.edSignPassword.text.toString()) //мы входим и передаем еайл и пароль
        }
    }

    private fun setDialogState(index: Int, rootDialogElement: SignDialogBinding) {
        if(index == DialogConst.SIGN_UP_STATE){
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.aс_sign_up)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_up_action)
        } else{
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.ac_sign_in)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_in_action)
            rootDialogElement.btForgetP.visibility = View.VISIBLE
        }
    }

}