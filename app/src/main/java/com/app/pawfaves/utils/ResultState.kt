package com.app.pawfaves.utils
sealed class ResultState<out T : Any?> {
data class Success<T : Any?>(val data: T) : ResultState<T>()
data class Error(val message: String) : ResultState<Nothing>()

}