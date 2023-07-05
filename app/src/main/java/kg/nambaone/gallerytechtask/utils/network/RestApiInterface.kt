package kg.nambaone.gallerytechtask.utils.network

import kg.nambaone.gallerytechtask.utils.network.response.PhotoResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApiInterface {

    @GET("/v1/curated")
    suspend fun getPhotoList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<PhotoResponse>

    companion object {
        private const val BASE_URL = "https://api.pexels.com"
        private const val API_KEY = "3I0snobuoINf2J7AMsTT6gYKuaKHG2xP0keC8kUvxTxK1CLb04c0PDBV"

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val url = originalRequest.url.newBuilder().build()

                val modifierRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", API_KEY)
                    .url(url)
                    .build()
                chain.proceed(modifierRequest)
            }
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        operator fun invoke(): RestApiInterface {
            return retrofit.create(RestApiInterface::class.java)
        }
    }
}
