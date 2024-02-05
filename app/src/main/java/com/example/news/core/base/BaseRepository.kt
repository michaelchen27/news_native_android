package com.example.news.core.base

import android.content.Context
import com.example.news.core.utility_class.GenericResponse
import com.example.news.core.utility_class.Resource
import com.example.news.core.utility_class.SendRequestException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository(protected val context: Context) {


    fun <T, R> createRequestWithFlow(
        request: suspend () -> Response<T>,
        onError: suspend (Int, String) -> Unit = { _, _ -> },
        mapper: (T) -> R,
        tag: String = "",
    ): Flow<R> = flow {
        when (val result = sendNetworkRequest(tag) { request() }) {
            is Resource.Error -> {
                if (result.code == OFFLINE_ERROR_CODE) onError.invoke(
                    OFFLINE_ERROR_CODE, OFFLINE_ERROR_MESSAGE
                )

                if (result.code == RESULT_NOT_FOUND_ERROR_CODE) onError.invoke(
                    RESULT_NOT_FOUND_ERROR_CODE, RESULT_NOT_FOUND_ERROR_MESSAGE
                )

                if (result.code == INTERNAL_SERVER_ERROR_CODE) onError.invoke(
                    INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR_MESSAGE
                )

                Timber.d("${result.errBody}")
                throw SendRequestException(result.message, result.tag, result.code, result.errBody)
            }

            is Resource.Success -> emit(mapper(result.data))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun <T, R> createRequest(
        request: suspend () -> Response<T>,
        onError: suspend (Int?) -> Unit = { },
        mapper: (T) -> R,
        tag: String = "",
    ): R = when (val result = sendNetworkRequest(tag) { request() }) {
        is Resource.Error -> {
            if (result.code == OFFLINE_ERROR_CODE) onError.invoke(OFFLINE_ERROR_CODE)
            if (result.code == RESULT_NOT_FOUND_ERROR_CODE) onError.invoke(
                RESULT_NOT_FOUND_ERROR_CODE
            )

            throw SendRequestException(result.message, result.tag, result.code, result.errBody)
        }

        is Resource.Success -> mapper(result.data)
    }


    private suspend fun <T> sendNetworkRequest(
        tag: String = "",
        call: suspend () -> Response<T>,
    ): Resource<T> {
        val response: Response<T>
        var code = 0
        var message = ""
        var errBody: ResponseBody? = null

        try {
            response = call.invoke()
            val responseCode = response.code()

            when {
                response.isSuccessful -> {
                    val body = response.body()!!
                    return Resource.Success(body)
                }

//                responseCode == RESULT_REFRESH_TOKEN_EXPIRED -> {
//                    code = RESULT_REFRESH_TOKEN_EXPIRED
//                    message = RESULT_REFRESH_TOKEN_EXPIRED_MESSAGE
//
//                }

                responseCode == RESULT_NOT_FOUND_ERROR_CODE -> {
                    code = RESULT_NOT_FOUND_ERROR_CODE
                    message = RESULT_NOT_FOUND_ERROR_MESSAGE
                }

                responseCode == INTERNAL_SERVER_ERROR_CODE -> {
                    code = INTERNAL_SERVER_ERROR_CODE
                    message = INTERNAL_SERVER_ERROR_MESSAGE
                }

                else -> {
                    val errorBody = response.errorBody()
                    val data = Gson().fromJson(errorBody?.string(), GenericResponse::class.java)

                    code = if (responseCode == 409) 409 else 1000
                    message = data.message

                }
            }
        } catch (e: JsonSyntaxException) {
            Timber.e("JsonSyntaxException Catch Block: ${e.message}")
            e.printStackTrace()

            code = PARSING_ERROR_CODE
            message = PARSING_ERROR_MESSAGE

        } catch (e: UnknownHostException) {
            code = OFFLINE_ERROR_CODE
            message = OFFLINE_ERROR_MESSAGE

        } catch (e: SocketTimeoutException) {
            code = SOCKET_TIMEOUT_ERROR_CODE
            message = SOCKET_TIMEOUT_ERROR_MESSAGE

        } catch (t: Exception) {
            t.printStackTrace()

//            FirebaseCrashlytics.getInstance().apply {
//                log(t.message.toString())
//                recordException(t)
//            }

            code = UNKNOWN_ERROR_CODE
            message = UNKNOWN_ERROR_MESSAGE
        }

        return Resource.Error(
            tag = tag, code = code, message = message, errBody = errBody
        )
    }


    companion object {
        const val RESULT_TOKEN_EXPIRED = 401
        const val RESULT_REFRESH_TOKEN_EXPIRED = 400
        const val RESULT_NOT_FOUND_ERROR_CODE = 404
        const val RESULT_ALREADY_EXIST_ERROR_CODE = 409
        const val INTERNAL_SERVER_ERROR_CODE = 500

        const val RESULT_NOT_FOUND_ERROR_MESSAGE = "Data Tidak ditemukan"
        const val RESULT_ALREADY_EXIST_ERROR_MESSAGE = "Data sudah ada"
        const val INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error"
        const val SOCKET_TIMEOUT_ERROR_MESSAGE = "Waktu tunggu terlalu lama"


        const val SOCKET_TIMEOUT_ERROR_CODE = 1004
        const val UNKNOWN_ERROR_CODE = 1003
        const val OFFLINE_ERROR_CODE = 1002
        const val PARSING_ERROR_CODE = 1001
        const val ID_CARD_VERIF_ERROR_CODE = 1005


        const val UNKNOWN_ERROR_MESSAGE = "Terjadi kesalahan sistem"
        const val OFFLINE_ERROR_MESSAGE = "Tidak ada Koneksi Internet"
        const val PARSING_ERROR_MESSAGE = "Terjadi kesalahan pengambilan data"
        const val RESULT_REFRESH_TOKEN_EXPIRED_MESSAGE = "Silakan login ulang"
    }
}