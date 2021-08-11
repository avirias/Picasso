package com.avirias.picasso.data.network.abstraction

import com.avirias.picasso.data.network.util.GsonFactory
import com.avirias.picasso.domain.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

abstract class DataSource {

    // A top level wrapper to call an API safely
    protected suspend inline fun <reified T> result(
        crossinline call: suspend () -> Response<T>
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = call.invoke()

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null)
                        Resource.Success(body)
                    // FIXME: There might be a case in which response will be successful but data can be null
                    else Resource.Failure(Exception("No data found"))
                } else {
                    if (response.code() == 401) throw Exception("Unauthorized")
                    val string = response.errorBody()?.getString() ?: throw NullPointerException()
                    val json = GsonFactory.getInstance().fromJson(string, T::class.java)
                    Resource.Success(json)
                }
            } catch (e: Exception) {
                Resource.Failure(e)
            }
        }
    }

    suspend fun ResponseBody.getString() = suspendCancellableCoroutine<String> {
        try {
            it.resume(string())
        } catch (e: Exception) {
            it.resumeWithException(e)
        } finally {
            close()
        }
        it.invokeOnCancellation { throwable ->
            Timber.e(throwable)
            close()
        }
    }
}