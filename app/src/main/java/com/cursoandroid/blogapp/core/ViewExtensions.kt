package com.cursoandroid.blogapp.core

import android.view.View

/**
 * Created by Tomás Guzmán on 06/05/2021.
 */
fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}