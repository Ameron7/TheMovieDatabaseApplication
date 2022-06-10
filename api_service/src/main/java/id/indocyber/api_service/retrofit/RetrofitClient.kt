package id.indocyber.api_service.retrofit

import android.content.Context
import com.ashokvarma.gander.Gander
import com.ashokvarma.gander.GanderInterceptor
import com.ashokvarma.gander.imdb.GanderIMDB
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import id.indocyber.common.base.Constant
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object RetrofitClient {

    fun getRetrofit(context: Context): Retrofit {
        Gander.setGanderStorage(GanderIMDB.getInstance())
        val client =
            OkHttpClient().newBuilder().addInterceptor {
                try {
                    it.proceed(it.request())
                } catch (e: Exception) {
                    Response.Builder()
                        .request(it.request()).message(e.message ?: "")
                        .protocol(Protocol.HTTP_1_1).code(0).body(
                            JsonObject().apply {
                                addProperty("error", e.message)
                            }.toString().toResponseBody(
                                "application/json"
                                    .toMediaType()
                            )
                        ).build()
                }


            }.addInterceptor(GanderInterceptor(context).showNotification(true)).build()

        return Retrofit.Builder().client(client).baseUrl(Constant.BASE_URL_THE_MOVIE_DB)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }
}