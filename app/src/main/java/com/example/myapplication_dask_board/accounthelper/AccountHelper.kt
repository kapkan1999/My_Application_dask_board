package com.example.myapplication_dask_board.accounthelper

import android.widget.Toast
import com.example.myapplication_dask_board.MainActivity
import com.example.myapplication_dask_board.R
import com.google.firebase.auth.FirebaseUser

class AccountHelper(act:MainActivity) {//в этот класс передаем весь активити, потом из него можем брать любую информацию
    private val act = act // если не сделаем переменную, то Act не будет видно во вложенных функциях
    fun signUpWithEmail(email: String, password : String){
        if (email.isNotEmpty() && password.isNotEmpty()){
           act.mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->

               if(task.isSuccessful){
                   sendEmailVerification(task.result?.user!!)
                   act.uiUpdate(task.result?.user)
               } else{
                   Toast.makeText(act,act.resources.getString(R.string.sign_up_error),Toast.LENGTH_LONG).show()
               }

           }
        }
    }
    fun signInWithEmail(email: String, password : String){
        if (email.isNotEmpty() && password.isNotEmpty()){
            act.mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task->

                if(task.isSuccessful){
                    sendEmailVerification(task.result?.user!!)
                    act.uiUpdate(task.result?.user)
                } else{

                    Toast.makeText(act,act.resources.getString(R.string.sign_in_error),Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun sendEmailVerification(user:FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener{task->
            if(task.isSuccessful){
                Toast.makeText(act,act.resources.getString(R.string.send_verification_done),Toast.LENGTH_LONG).show()
            } else{
                Toast.makeText(act,act.resources.getString(R.string.send_verification_error),Toast.LENGTH_LONG).show()
            }
        }
    }
}