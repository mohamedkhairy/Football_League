package com.example.footballleague.util




fun Any?.isNotNull(): Boolean =
    this != null

fun Any?.isNull(): Boolean =
    this == null

fun List<*>?.isNotNullOrEmpty(): Boolean =
    this != null && this.isNotEmpty()