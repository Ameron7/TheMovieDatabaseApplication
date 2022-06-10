package id.indocyber.common.base

import okhttp3.ResponseBody

sealed class ResponseApp<T>(
    val data : T?,
    val error : Exception?,
    val code: Int?,
    val responseBody: ResponseBody?
) {
    companion object {
        fun <T> success(data: T?) : ResponseApp<T> = ResponseSuccess(data)
        fun <T> error(error: Exception?, code: Int?, responseBody: ResponseBody?) : ResponseApp<T> = ResponseError(error, code, responseBody)
        fun <T> loading() : ResponseApp<T> = ResponseLoading()
    }
}

class ResponseSuccess<T>(data: T?) : ResponseApp<T>(data, null, null, null)
class ResponseError<T>(error: Exception?, code: Int?, responseBody: ResponseBody?) : ResponseApp<T>(null, error, code, responseBody)
class ResponseLoading<T> : ResponseApp<T>(null, null, null, null)


