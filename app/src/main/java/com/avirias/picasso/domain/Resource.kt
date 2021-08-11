package com.avirias.picasso.domain

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val throwable: Throwable) : Resource<T>()

    inline fun <R> map(crossinline mapper: T.() -> R): Resource<R> {
        return when (this) {
            is Failure -> Failure(throwable)
            is Loading -> Loading()
            is Success -> Success(mapper(this.data))
        }
    }

    suspend fun peek(call: suspend T.() -> Unit): Resource<T> {
        if (this is Success) call(this.data)
        return this
    }

}