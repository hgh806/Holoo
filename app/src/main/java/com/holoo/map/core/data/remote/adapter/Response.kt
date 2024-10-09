package com.borna.dotinfilm.core.data.remote.adapter

import com.holoo.map.core.data.remote.adapter.GeneralError

sealed class Response<out V>

data class Success<out V>(val value: V) : Response<V>()

data class Failure(val error: GeneralError) : Response<Nothing>()

fun <V> Response<V>.isSuccessful(): Boolean {
    return this is Success
}

inline fun Failure?.otherwise(failure: (GeneralError) -> Unit) {
    if (this is Failure) {
        failure.invoke(this.error)
    }
}

inline fun <V> Response<V>.ifSuccessful(success: (V) -> Unit): Failure? {
    if (this is Success) {
        success.invoke(value)
        return null
    }
    return this as Failure
}

inline fun <V> Response<V>.ifNotSuccessful(failure: (GeneralError) -> Unit) {
    if (this is Failure) {
        failure.invoke(this.error)
    }
}