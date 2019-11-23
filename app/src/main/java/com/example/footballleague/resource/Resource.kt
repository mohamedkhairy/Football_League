package com.example.footballleague.resource

import kotlinx.coroutines.Job

class Resource<T>(val status: AuthStatus, val data: T?, val message: String?, val job: MutableList<Job>?) {


    enum class AuthStatus {
        SUCCESS, ERROR, LOADING
    }

    companion object {

        fun <T> success(data: T?, job: MutableList<Job>?): Resource<T> {
            return Resource(AuthStatus.SUCCESS, data, null, job)
        }

        fun <T> error(msg: String, data: T?, job: MutableList<Job>?): Resource<T> {
            return Resource(AuthStatus.ERROR, data, msg, job)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(AuthStatus.LOADING, data, null, null)
        }
    }

}
