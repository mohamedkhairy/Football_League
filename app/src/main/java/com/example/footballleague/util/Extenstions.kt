package com.example.footballleague.util

import android.view.View
import androidx.appcompat.app.ActionBar


fun Any?.isNotNull(): Boolean =
    this != null

fun Any?.isNull(): Boolean =
    this == null

fun List<*>?.isNotNullOrEmpty(): Boolean =
    this != null && this.isNotEmpty()

fun ActionBar?.setAction(title: String, enable: Boolean) {
    this.let { appBar ->
        appBar?.title = title
        appBar?.setDisplayHomeAsUpEnabled(enable)
        appBar?.setHomeButtonEnabled(enable)
    }
}


fun View.hideView() {
    this.visibility = View.GONE
}

fun View.showView() {
    this.visibility = View.VISIBLE
}