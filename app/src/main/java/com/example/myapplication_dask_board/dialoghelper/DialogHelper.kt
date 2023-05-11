package com.example.myapplication_dask_board.dialoghelper

import android.app.AlertDialog
import com.example.myapplication_dask_board.MainActivity
import com.example.myapplication_dask_board.databinding.SignDialogBinding

class DialogHelper(act:MainActivity) {
    private val act = act

    fun createSignDialog(){
        val builder = AlertDialog.Builder(act)
        val rootDialogElement = SignDialogBinding.inflate(act.layoutInflater)
        val view = rootDialogElement.root
        builder.setView(view)
        builder.show()
    }
}