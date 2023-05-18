package com.example.myapplication_dask_board.accounthelper

import android.util.Log
import android.widget.Toast
import com.example.myapplication_dask_board.MainActivity
import com.example.myapplication_dask_board.R
import com.example.myapplication_dask_board.constants.FirebaseAuthConstants
import com.example.myapplication_dask_board.dialoghelper.GoogleAccConst
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AccountHelper(act:MainActivity) {//в этот класс передаем весь активити, потом из него можем брать любую информацию
    private val act = act // если не сделаем переменную, то Act не будет видно во вложенных функциях
    val signInRequestCode = 132
    private lateinit var signInClient: GoogleSignInClient // сюда и будем передавать клиента от гугла
    fun signUpWithEmail(email: String, password : String){
        if (email.isNotEmpty() && password.isNotEmpty()){
           act.mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
               Log.d("MyLog","Exception : ${task.exception }")
               if(task.isSuccessful){
                   sendEmailVerification(task.result?.user!!)
                   act.uiUpdate(task.result?.user)
               } else{
                   //Toast.makeText(act,act.resources.getString(R.string.sign_up_error),Toast.LENGTH_LONG).show()
                   //Log.d("MyLog","Exception : ${exception.errorCode }") //показывает что за ошибка
                   if (task.exception is FirebaseAuthUserCollisionException){ // проверка на конкретную ошибку
                       val exception = task.exception as FirebaseAuthUserCollisionException
                       //as это кстаинг?? мы прост опеременной сообщаем конкретный тип ошибки, то есть он только о ней говорить будет
                       if (exception.errorCode == FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE){
                           Toast.makeText(act,FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE,Toast.LENGTH_LONG).show()
                       }
                   } else if (task.exception is FirebaseAuthInvalidCredentialsException){ // проверка на конкретную ошибку
                       val exception = task.exception as FirebaseAuthInvalidCredentialsException
                       //as это кстаинг?? мы прост опеременной сообщаем конкретный тип ошибки, то есть он только о ней говорить будет
                       if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL){
                           Toast.makeText(act,FirebaseAuthConstants.ERROR_INVALID_EMAIL,Toast.LENGTH_LONG).show()
                       }
                   }
                   if (task.exception is FirebaseAuthWeakPasswordException){ // проверка на конкретную ошибку
                       val exception = task.exception as FirebaseAuthWeakPasswordException
                       //as это кстаинг?? мы прост опеременной сообщаем конкретный тип ошибки, то есть он только о ней говорить будет
                       if (exception.errorCode == FirebaseAuthConstants.ERROR_WEAK_PASSWORD){
                           Toast.makeText(act,FirebaseAuthConstants.ERROR_WEAK_PASSWORD,Toast.LENGTH_LONG).show()
                       }
                   }
               }

           }
        }
    }
    private  fun getSignInClient (): GoogleSignInClient{ // это функция для соединения через гугл клиент, добавили в gradle module новую библиотеку
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) // переменная, указываем, что хотим обычную регистрацию
            .requestIdToken(act.getString(R.string.default_web_client_id)).requestEmail().build()
            // спец токен с приложения, чтобы система доверяла приложению, в этой строке наш токен

            //КАК ВСЕ ЭТО ЗАПОМИНАТЬ, УААААА
        return  GoogleSignIn.getClient(act,gso) // передаем контекст и самого клиента
    }
    fun signInWithGoogle(){
        signInClient = getSignInClient()
        val intent = signInClient.signInIntent // это нужно для входа, почему intent хз вообще. через интент мы видим все настройки!!! ЕПТА!
        act.startActivityForResult(intent, GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE)
    }
    fun signInFirebaseWithGoogle(token : String){
        val credential = GoogleAuthProvider.getCredential(token,null) //по токену у нас бурется полномочия??
        act.mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(act,"Sign in Done",Toast.LENGTH_LONG).show()
                act.uiUpdate(task.result?.user)
            } else {
                Log.d("MyLog","Google Sign In Exception")
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
                    Log.d("MyLog","Exception : ${task.exception }")

                    if (task.exception is FirebaseAuthInvalidCredentialsException){ // проверка на конкретную ошибку
                        val exception = task.exception as FirebaseAuthInvalidCredentialsException
                        //as это кстаинг?? мы прост опеременной сообщаем конкретный тип ошибки, то есть он только о ней говорить будет
                        if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL){
                            Toast.makeText(act,FirebaseAuthConstants.ERROR_INVALID_EMAIL,Toast.LENGTH_LONG).show()
                        }
                    }else if (task.exception is FirebaseAuthInvalidCredentialsException){ // проверка на конкретную ошибку
                        val exception = task.exception as FirebaseAuthInvalidCredentialsException
                        //as это кстаинг?? мы прост опеременной сообщаем конкретный тип ошибки, то есть он только о ней говорить будет
                        if (exception.errorCode == FirebaseAuthConstants.ERROR_WRONG_PASSWORD){
                            Toast.makeText(act,FirebaseAuthConstants.ERROR_WRONG_PASSWORD,Toast.LENGTH_LONG).show()
                        }
                    }
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