package com.holoo.map.core.data.remote.adapter

import com.holoo.map.R


sealed class GeneralError {
    data class ApiError(val message: String?, val code: Int) : GeneralError()
    data object NetworkError : GeneralError()
    data class UnknownError(val error: Throwable) : GeneralError()
}

class GeneralErrorThrowable(
    private val generalError: GeneralError,
) : Throwable() {

    override val message: String
        get() = when (generalError) {
            is GeneralError.ApiError -> generalError.message ?: "Unknown API error"
            is GeneralError.NetworkError -> "Network error"
            is GeneralError.UnknownError -> generalError.error.message ?: "Unknown error"
        }
}

fun GeneralError.handleError(
    apiErrorCallback: (message: String?, messageId: Int?, code: Int?) -> Unit,
) {
    when (this) {
        is GeneralError.ApiError -> apiErrorCallback(message, null, code)

        is GeneralError.NetworkError -> apiErrorCallback(
            null,
            R.string.network_failure,
            null
        )

        is GeneralError.UnknownError -> apiErrorCallback(
            null,
            R.string.request_failed,
            null
        )
    }
}